import java.io.File;

/**
 * 将虚拟机语言转换成汇编语言
 * Created by Away
 * 2016/2/6
 */

public class VMTranslator {

    public static void main(String[] args) {
        String path = "C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\08\\FunctionCalls\\StaticsTest";
        File root = new File(path);
        File[] files = root.listFiles();
        if (null == files) {
            return;
        }

        CodeWriter w = new CodeWriter(path + "\\StaticsTest.asm");

        boot(w);

        for (File file : files) {
            if (!isValidFile(file)) {
                continue;
            }
            Parser p = new Parser(file.getAbsolutePath());
            while (p.hasNextCmd()) {
                p.advance();
                if (!p.isOK()) {
                    break;
                }

                act(p, w);
            }
            p.close();
        }
        w.close();
    }

    /**
     * 初始化内容
     */
    private static void boot(CodeWriter w) {
        w.writeToRegister(256, "SP");
        w.writeToRegister(5, "5");
        w.writeCall("Sys.init", 0);
    }

    private static boolean isValidFile(File file) {
        String tmp = file.getAbsolutePath();
        String[] choose = tmp.split("\\.");
        return choose.length == 2 && choose[1].equals("vm");
    }

    private static void act(Parser p, CodeWriter w) {
        String cmd = p.getCmd();
        CmdType type = CmdType.getType(cmd);

        if (type != null) {
            switch (type) {
                case C_ARITHMETIC:
                    w.writeArithmetic(cmd);
                    break;
                case C_POP:
                case C_PUSH:
                    w.PushPop(cmd, p.getArg1(), p.getArg2());
                    break;
                case C_LABEL:
                    w.writeLabel(p.getArg1());
                    break;
                case C_GOTO:
                    w.writeGoto(p.getArg1());
                    break;
                case C_IF:
                    w.writeIf(p.getArg1());
                    break;
                case C_CALL:
                    w.writeCall(p.getArg1(), p.getArg2());
                    break;
                case C_RETURN:
                    w.writeReturn();
                    break;
                case C_FUNCTION:
                    w.writeFunction(p.getArg1(), p.getArg2());
                    break;
            }
        }
    }
}
