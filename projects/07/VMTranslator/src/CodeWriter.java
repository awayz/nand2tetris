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

    // 栈指针自增
    private static final String SP_PLUS;

    // 将 SP 指向的栈区域设为 -1 （1111..1）
    private static final String SET_TRUE;

    // 将 SP 指向的栈区域设为 0 （0000..0）
    private static final String SET_FALSE;

    // 将类型转换为寄存器名称
    private static final Map<String, String> SEG_TO_REG;

    static {
        POP = "@SP\n" +
                "M=M-1\n" +
                "A=M\n";

        SP_PLUS = "@SP\n" +
                "M=M+1\n";

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
        SEG_TO_REG.put("pointer0", "THIS");
        SEG_TO_REG.put("pointer1", "THAT");
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
        writeToRegister(5, "5");
        setFileName(path);
    }

    private void setFileName(String path) {
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
    private void writeToRegister(int value, String register) {
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

        switch (cmd) {
            case "add":
                result += "M=D+M\n";
                break;
            case "sub":
                result += "M=M-D\n";
                break;
            case "neg":
                result += "M=-M\n";
                break;
            case "eq":
                result += cmpProcess("EQ");
                break;
            case "gt":
                result += cmpProcess("GT");
                break;
            case "lt":
                result += cmpProcess("LT");
                break;
            case "and":
                result += "M=D&M\n";
                break;
            case "or":
                result += "M=D|M\n";
                break;
            case "not":
                result += "M=!M\n";
                break;
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
                    reg = SEG_TO_REG.get("pointer" + idx);
                    result = "@" + reg + "\n" +
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

            result += "@SP\n" +
                    "A=M\n" +
                    "M=D\n" +
                    SP_PLUS;

        } else if (cmd.equals("pop")) {
            switch (segment) {
                case "static":
                    result = POP +
                            "D=M\n" +
                            "@" + fileName + "." + idx + "\n" +
                            "M=D\n";
                    break;
                case "pointer":
                    reg = SEG_TO_REG.get("pointer" + idx);
                    result = POP +
                            "D=M\n" +
                            "@" + reg + "\n" +
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

    public void close() {
        try {
            bufWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String s) {
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
