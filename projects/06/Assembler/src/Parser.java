/**
 * 将C指令拆分成不同的部分
 * Created by Away
 * 2016/2/2
 */

public class Parser {

    // 计算部分
    private String comp;

    // 目的地
    private String dest;

    // 跳转条件
    private String jump;

    public void parse(String instruction) {
        int t1 = instruction.indexOf('=');
        int t2 = instruction.indexOf(';');

        int idx1 = (t1 == -1) ? t2 : t1;
        int idx2 = (t2 == -1) ? instruction.length() : t2;
        dest = (t1 == -1) ? null : instruction.substring(0, idx1);
        comp = (t1 == -1) ? instruction.substring(0, idx1) : instruction.substring(idx1 + 1, idx2);
        jump = (t2 == -1) ? null : instruction.substring(idx2 + 1);
    }

    public String getComp() {
        return comp;
    }

    public String getDest() {
        return dest;
    }

    public String getJump() {
        return jump;
    }

}
