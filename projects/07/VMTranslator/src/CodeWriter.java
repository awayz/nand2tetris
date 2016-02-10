import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 将命令转化为实际的汇编语言
 * Created by Away
 * 2016/2/6
 */

public class CodeWriter {

    // 出栈，内容从 M 中读取
    private static final String POP;

    // 入栈，内容从 D 中读取
    private static final String PUSH;

    // 栈指针自增
    private static final String SP_PLUS;

    // 将 SP 指向的栈区域设为 -1 （1111..1）
    private static final String SET_TRUE;

    // 将 SP 指向的栈区域设为 0 （0000..0）
    private static final String SET_FALSE;

    // 将类型转换为寄存器名称
    private static final Map<String, String> SEG_TO_REG;

    // 将数学操作命令转化为表达式
    private static final Map<String, String> CMD_TO_FORMULA;

    static {
        POP = "@SP\n" +
                "AM=M-1\n";

        SP_PLUS = "@SP\n" +
                "M=M+1\n";

        PUSH = "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                SP_PLUS;

        SET_TRUE = "@SP\n" +
                "A=M\n" +
                "M=-1\n";

        SET_FALSE = "@SP\n" +
                "A=M\n" +
                "M=0\n";

        SEG_TO_REG = new HashMap<>();
        SEG_TO_REG.put("argument", "ARG");
        SEG_TO_REG.put("local", "LCL");
        SEG_TO_REG.put("this", "THIS");
        SEG_TO_REG.put("that", "THAT");
        SEG_TO_REG.put("temp", "R5");
        SEG_TO_REG.put("pointer", "THIS");

        CMD_TO_FORMULA = new HashMap<>();
        CMD_TO_FORMULA.put("add", "M=D+M\n");
        CMD_TO_FORMULA.put("neg", "M=M-D\n");
        CMD_TO_FORMULA.put("and", "M=D&M\n");
        CMD_TO_FORMULA.put("or", "M=D|M\n");
        CMD_TO_FORMULA.put("not", "M=!M\n");
        CMD_TO_FORMULA.put("sub", "M=M-D\n");
    }

    private BufferedWriter bufWriter;

    private int count;

    private String fileName;

    public CodeWriter(String path) {
        try {
            bufWriter = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        init(path);
    }

    private void init(String path) {
        count = 0;
    }

    public void setFileName(String path) {
        String[] str = path.split("\\.");
        String[] t = str[0].split("\\\\");
        fileName = t[t.length - 1];
    }

    /**
     * 将数字写入寄存器
     *
     * @param value    数字
     * @param register 寄存器
     */
    public void writeToRegister(int value, String register) {
        write("@" + value + "\n" +
                "D=A\n" +
                "@" + register + "\n" +
                "M=D\n");
    }

    /**
     * 解析运算部分
     *
     * @param cmd 运算指令
     */
    public void writeArithmetic(String cmd) {
        String result = "";
        if (isUnary(cmd)) {
            result = POP;
        } else {
            result = POP +
                    "D=M\n" +
                    POP;
        }

        if (cmd.equals("eq") || cmd.equals("gt") || cmd.equals("lt")) {
            result += cmpProcess(cmd.toUpperCase());

        } else {
            result += CMD_TO_FORMULA.get(cmd);
        }

        result += SP_PLUS;
        write(result);
    }

    private String cmpProcess(String cmd) {
        String mark = getMark(cmd);
        String end = getMark("END");
        return "D=M-D\n" +
                "@" + mark + "\n" +
                "D;J" + cmd + "\n" +
                SET_FALSE +
                "@" + end + "\n" +
                "0;JMP\n" +
                "(" + mark + ")\n" +
                SET_TRUE +
                "(" + end + ")\n";
    }

    /**
     * 判断是否是单目运算
     *
     * @param cmd 操作类型
     * @return 是 true 否 false
     */
    private boolean isUnary(String cmd) {
        return cmd.equals("not") || cmd.equals("neg");
    }

    /**
     * 解析栈部分
     *
     * @param cmd 出入栈指令
     */
    public void PushPop(String cmd, String segment, int idx) {
        String result = "";
        String reg = SEG_TO_REG.get(segment);

        if (cmd.equals("push")) {
            switch (segment) {
                case "static":
                    result = "@" + fileName + "." + idx + "\n" +
                            "D=M\n";
                    break;
                case "pointer":
                case "temp":
                    result = "@" + idx + "\n" +
                            "D=A\n";

                    result += "@" + reg + "\n" +
                            "A=D+A\n" +
                            "D=M\n";
                    break;
                default:
                    result = "@" + idx + "\n" +
                            "D=A\n";

                    if (!segment.equals("constant")) {
                        result += "@" + reg + "\n" +
                                "A=D+M\n" +
                                "D=M\n";
                    }
                    break;
            }

            result += PUSH;

        } else if (cmd.equals("pop")) {
            switch (segment) {
                case "static":
                    result = POP +
                            "D=M\n" +
                            "@" + fileName + "." + idx + "\n" +
                            "M=D\n";
                    break;
                case "pointer":
                case "temp":
                    result = "@" + idx + "\n" +
                            "D=A\n" +
                            "@" + reg + "\n" +
                            "D=D+A\n" +
                            "@R13\n" +
                            "M=D\n" +
                            POP +
                            "D=M\n" +
                            "@R13\n" +
                            "A=M\n" +
                            "M=D\n";
                    break;
                default:
                    result = "@" + idx + "\n" +
                            "D=A\n" +
                            "@" + reg + "\n" +
                            "D=D+M\n" +
                            "@R13\n" +
                            "M=D\n" +
                            POP +
                            "D=M\n" +
                            "@R13\n" +
                            "A=M\n" +
                            "M=D\n";
                    break;
            }
        }
        write(result);
    }

    /**
     * 解析标签部分
     * @param label 标签名称
     */
    public void writeLabel(String label) {
        write("(" + label + ")" + "\n");
    }

    /**
     * 解析标签跳转部分
     * @param label 跳转到的标签
     */
    public void writeGoto(String label) {
        write("@" + label + "\n" +
                "0;JMP\n");
    }

    /**
     * 解析条件判断部分
     * @param label 跳转到的标签
     */
    public void writeIf(String label) {
        write(POP +
                "D=M\n" +
                "@" + label + "\n" +
                "D;JNE\n");
    }

    /**
     * 解析函数跳转部分
     * @param functionName 函数名称
     * @param numArgs 参数个数
     */
    public void writeCall(String functionName, int numArgs) {
        String RET = getMark(functionName + "_return_address");
        write("@" + RET + "\n" +
                "D=A\n" +
                PUSH +
                saveReg("LCL") +    // PUSH LCL
                saveReg("ARG") +    // PUSH ARG
                saveReg("THIS") +   // PUSH THIS
                saveReg("THAT") +   // PUSH THAT
                "@5\n" +
                "D=A\n" +
                "@" + numArgs + "\n" +
                "D=D+A\n" +
                "@SP\n" +
                "D=M-D\n" +
                "@ARG\n" +
                "M=D\n" +     // ARG = SP - n - 5
                "@SP\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n" +        // LCL = SP
                "@" + functionName + "\n" +
                "0;JMP\n" +
                "(" + RET + ")\n");
    }

    /**
     * 将寄存器中的内容存入栈中
     * @param reg 寄存器
     */
    private String saveReg(String reg) {
        return "@" + reg + "\n" +
                "D=M\n" +
                PUSH;
    }

    /**
     * 解析函数初始化部分
     * @param functionName 函数名称
     * @param numLocals 局部变量个数
     */
    public void writeFunction(String functionName, int numLocals) {
        String mark = getMark(functionName + "$" + "INNER_LOOP");
        String endMark = getMark(functionName + "$" + "INNER_LOOP_END");
        write("(" + functionName + ")\n" +
                "@" + numLocals + "\n" +
                "D=A\n" +
                "@R13\n" +
                "M=D\n" +    // R13 = numLocals
                "(" + mark + ")\n" +
                "@R13\n" +
                "D=M\n" +
                "@" + endMark + "\n" +
                "D;JEQ\n" +
                "D=0\n" +
                PUSH +
                "@R13\n" +
                "M=M-1\n" +
                "@" + mark + "\n" +
                "0;JMP\n" +
                "(" + endMark + ")\n");   // PUSH 0
    }

    /**
     * 解析函数返回部分
     */
    public void writeReturn() {
        write(setRegWithPointer("LCL", 5, "R13") +     // RET = * (LCL - 5)
                POP +
                "D=M\n" +
                "@ARG\n" +
                "A=M\n" +
                "M=D\n" +      // * ARG = POP
                "@ARG\n" +
                "D=M\n" +
                "@SP\n" +
                "M=D+1\n" +    // SP = ARG + 1
                setRegWithPointer("LCL", 1, "THAT") +  // THAT
                setRegWithPointer("LCL", 2, "THIS") +  // THIS
                setRegWithPointer("LCL", 3, "ARG") +  // ARG
                setRegWithPointer("LCL", 4, "LCL") +  // LCL
                "@R13\n" +
                "A=M\n" +
                "0;JMP\n");     // goto RET
    }

    /**
     * 获得指针指向的区域的值, 放入寄存器中
     *   *reg = * ( *addr - idx)
     * @param addr 指针本身的地址
     * @param idx 偏移量
     * @return 字符串
     */
    private String setRegWithPointer(String addr, int idx, String reg) {
        return ("@" + idx + "\n" +
                "D=A\n" +
                "@" + addr + "\n" +
                "A=M-D\n" +
                "D=M\n" +
                "@" + reg + "\n" +
                "M=D\n");
    }

    public void close() {
        try {
            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String s) {
        try {
            bufWriter.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到标记的名称
     */
    private String getMark(String name) {
        String t = name + count;
        count++;
        return t;
    }
}
