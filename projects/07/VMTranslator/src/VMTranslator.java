/**
 * 将虚拟机语言转换成汇编语言
 * Created by Away
 * 2016/2/6
 */

public class VMTranslator {

    public static void main(String[] args) {
        Parser p = new Parser("C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\07\\StackArithmetic\\StackTest\\StackTest.vm");
        CodeWriter w = new CodeWriter("C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\07\\StackArithmetic\\StackTest\\StackTest.asm");
        while (p.hasNextCmd()) {
            p.advance();
            String cmd = p.getCmd();
            CmdType type = CmdType.getType(cmd);
            if (type != null) {
                if (type.equals(CmdType.C_ARITHMETIC)) {
                    w.writeArithmetic(cmd);
                } else if (type.equals(CmdType.C_POP) || type.equals(CmdType.C_PUSH)) {
                    w.PushPop(cmd, p.getArg1(), p.getArg2());
                }
            }
        }
        p.close();
        w.close();
    }
}
