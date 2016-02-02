import java.util.HashMap;
import java.util.Map;

/**
 * 将 C 指令变为机器码
 * Created by Away
 * 2016/2/2
 */

public class Code {

    public static final Map<String, String> COMP;

    public static final Map<String, String> DEST;

    public static final Map<String, String> JUMP;

    static {
        COMP = new HashMap<>();
        DEST = new HashMap<>();
        JUMP = new HashMap<>();

        COMP.put("0", "0101010");
        COMP.put("1", "0111111");
        COMP.put("-1", "0111010");
        COMP.put("D", "0001100");
        COMP.put("A", "0110000");
        COMP.put("M", "1110000");
        COMP.put("!D", "0001101");
        COMP.put("!A", "0110001");
        COMP.put("!M", "1110001");
        COMP.put("-D", "0001111");
        COMP.put("-A", "0110011");
        COMP.put("-M", "1110011");
        COMP.put("D+1", "0011111");
        COMP.put("A+1", "0110111");
        COMP.put("M+1", "1110111");
        COMP.put("D-1", "0001110");
        COMP.put("A-1", "0110010");
        COMP.put("M-1", "1110010");
        COMP.put("D+A", "0000010");
        COMP.put("D+M", "1000010");
        COMP.put("D-A", "0010011");
        COMP.put("D-M", "1010011");
        COMP.put("A-D", "0000111");
        COMP.put("M-D", "1000111");
        COMP.put("D&A", "0000000");
        COMP.put("D&M", "1000000");
        COMP.put("D|A", "0010101");
        COMP.put("D|M", "1010101");

        DEST.put(null, "000");
        DEST.put("M", "001");
        DEST.put("D", "010");
        DEST.put("MD", "011");
        DEST.put("A", "100");
        DEST.put("AM", "101");
        DEST.put("AD", "110");
        DEST.put("AMD", "111");

        JUMP.put(null, "000");
        JUMP.put("JGT", "001");
        JUMP.put("JEQ", "010");
        JUMP.put("JGE", "011");
        JUMP.put("JLT", "100");
        JUMP.put("JNE", "101");
        JUMP.put("JLE", "110");
        JUMP.put("JMP", "111");
    }

    public String comp(String c) {
        return COMP.get(c);
    }

    public String dest(String d) {
        return DEST.get(d);
    }

    public String jump(String j) {
        return JUMP.get(j);
    }
}
