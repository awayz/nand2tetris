/**
 * 命令种类
 * Created by Away
 * 2016/2/6
 */

public enum CmdType {
    C_ARITHMETIC("add sub neg eq gt lt and or not"),
    C_PUSH("push"),
    C_POP("pop"),
    C_LABEL("label"),
    C_GOTO("goto"),
    C_IF("if-goto"),
    C_FUNCTION("function"),
    C_RETURN("return"),
    C_CALL("call");

    private String cmd;

    CmdType(String cmd) {
        this.cmd = cmd;
    }

    public static CmdType getType(String cmd) {
        for (CmdType t : CmdType.values()) {
            if (t.getCmd().contains(cmd)) {
                return t;
            }
        }

        return null;
    }

    public String getCmd() {
        return cmd;
    }
}
