import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 将命令转化为实际的汇编语言
 * Created by Away
 * 2016/2/6
 */

public class CodeWriter {

    private BufferedWriter bufWriter;

    private String result;

    public CodeWriter(String path) {
        try {
            bufWriter = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
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
        result = "";
        switch (cmd) {
            case "add":
                result += "@SP\n" +
                          "A=M-1\n" +
                          "D=M\n" +
                          "A=A-1\n" +
                          "D=D+M\n" +
                          "M=D\n" +
                          "D=A+1\n" +
                          "@SP\n" +
                          "M=D\n";
            case "sub":
                result += "@SP\n" +
                        "A=M-1\n" +
                        "D=M\n" +
                        "A=A-1\n" +
                        "D=M-D\n" +
                        "M=D\n" +
                        "D=A+1\n" +
                        "@SP\n" +
                        "M=D\n";
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
        }

        write(result);
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
                            "D=A+1\n" +
                            "@SP\n" +
                            "M=D\n";
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

    /**
     * 写 A 指令
     */
    private void aInstruction(String s) {
        s += "@" + s + "\n";
    }

    private void getSP(String s) {

    }

    private void write(String s) {
        try {
            bufWriter.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
