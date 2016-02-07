import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 将命令转化为实际的汇编语言
 * Created by Away
 * 2016/2/6
 */

public class CodeWriter {

    // 出栈，内容从 M 中读取
    private static final String POP;

    // 栈指针加 1
    private static final String SP_PLUS;

    // 栈指针减 1
    private static final String SP_SUB;

    // 将 SP 指向的栈区域设为 -1 （1111..1）
    private static final String SET_TRUE;

    // 将 SP 指向的栈区域设为 0 （0000..0）
    private static final String SET_FALSE;

    static {
        SP_SUB = "@SP\n" +
                "M=M-1\n";

        SP_PLUS = "@SP\n" +
                "M=M+1\n";

        POP = "@SP\n" +
                "M=M-1\n" +
                "A=M\n";

        SET_TRUE = "@SP\n" +
                "A=M\n" +
                "M=-1\n";

        SET_FALSE = "@SP\n" +
                "A=M\n" +
                "M=0\n";
    }


    private BufferedWriter bufWriter;

    private String result;

    private int count;

    public CodeWriter(String path) {
        try {
            bufWriter = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        count = 0;
        // 栈开始于 256
        write("@256\n" +
                "D=A\n" +
                "@SP\n" +
                "M=D\n");
    }

    /**
     * 解析运算部分
     * @param cmd 运算指令
     */
    public void writeArithmetic(String cmd) {
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
                result += booleanProcess("EQ");
                break;
            case "gt":
                result += booleanProcess("GT");
                break;
            case "lt":
                result += booleanProcess("LT");
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

    private String booleanProcess(String cmd) {
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
     * @param cmd 操作类型
     * @return 是 true 否 false
     */
    private boolean isUnary(String cmd) {
        return cmd.equals("not") || cmd.equals("neg");
    }

    /**
     * 解析栈部分
     * @param cmd 出入栈指令
     */
    public void PushPop(String cmd, String segment, int idx) {
        result = "";
        switch (cmd) {
            case "push":
                if (segment.equals("constant")) {
                    result += "@" + idx + "\n" +
                            "D=A\n" +
                            "@SP\n" +
                            "A=M\n" +
                            "M=D\n" +
                            SP_PLUS;
                }
            case "pop":
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
