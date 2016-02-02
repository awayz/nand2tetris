import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Away
 * 2016/2/2
 */

public class Assembler {

    // 符号表
    public static Map<String, Integer> SYMBOL;

    static {
        SYMBOL = new HashMap<>();

        for (int i = 0; i < 16; i++) {
            SYMBOL.put("R" + i, i);
        }
        SYMBOL.put("SCREEN", 16384);
        SYMBOL.put("KBD", 24576);
        SYMBOL.put("SP", 0);
        SYMBOL.put("LCL", 1);
        SYMBOL.put("ARG", 2);
        SYMBOL.put("THIS", 3);
        SYMBOL.put("THAT", 4);
    }

    // 汇编
    private List<String> content;

    // 机器码
    private List<String> machine;

    public static void main(String[] args) {
        Assembler assembler = new Assembler();
        assembler.read("C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\06\\rect\\RectL.asm");
        assembler.scan1();
        assembler.scan2();
        assembler.write("C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\06\\rect\\RectL.hack");
    }

    /**
     * 第一遍扫描，将标记（XXX）转换为地址放入符号表
     */
    private void scan1() {
        int idx;
        int count = 0;

        for (String s : content) {
            idx = s.indexOf(')');
            if (idx != -1) {
                SYMBOL.put(s.substring(1, idx), count);
            } else {
                count++;
            }
        }
    }

    /**
     * 第二遍扫描，将临时变量转为寄存器，转换指令，得到机器码
     */
    private void scan2() {
        // 临时变量寄存器
        int n = 16;
        String result = null;
        Integer symbol;
        machine = new ArrayList<>();

        for (String s : content) {
            // a 指令
            if (s.startsWith("@")) {
                String t = s.substring(1);
                // 纯数字
                if (this.isNum(t)) {
                    symbol = Integer.parseInt(t);
                } else {
                    symbol = SYMBOL.get(t);
                    // 符号表找不到则为临时变量，写入符号表
                    if (symbol == null) {
                        SYMBOL.put(t, n);
                        symbol = n;
                        n++;
                    }
                }

                result = "0" + this.toBin(symbol, 15);
            }
            // c 指令
            else if (!s.startsWith("(")) {
                Parser parser = new Parser();
                Code code = new Code();
                parser.parse(s);

                String comp = code.comp(parser.getComp());
              //  System.out.println(parser.getComp());
                String dest = code.dest(parser.getDest());
                String jump = code.jump(parser.getJump());
                result = "111" + comp + dest + jump;
            }
            // (xxx)
            else {
                continue;
            }

            machine.add(result);
        }
    }

    /**
     * 读入文件，空格、空行、单行注释部分忽略。
     * @param path 路径
     */
    private void read(String path) {
        content = new ArrayList<>();
        try (BufferedReader bufReader = new BufferedReader(new FileReader(path))) {
            String s;
            while ((s = bufReader.readLine()) != null) {
                // 删除单行注释
                int idx = s.indexOf("//");
                if (idx != -1) {
                    s=s.substring(0, idx);
                }
                // 删去空格
                s=s.replace(" ", "");
                // 删去空行
                if (s.length() != 0) {
                    System.out.println(s);
                    content.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写出文件
     * @param path 路径
     */
    private void write(String path) {
        try (BufferedWriter bufWriter = new BufferedWriter(new FileWriter(path))) {
            for (String s : machine) {
                bufWriter.write(s + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将非负整数转换为字符串表示的二进制
     * @param num 十进制非负整数
     * @param len 字符串长度（输入保证大于等于应有的长度）
     * @return 字符串表示的二进制
     */
    private String toBin(int num, int len) {
        String result = "";
        if (num == 0) result += "0";
        while (num != 0 || result.length() != len) {
            result = num % 2 + result;
            num /= 2;
        }
        return result;
    }

    /**
     * 判断字符串是否是数字
     * @param s 待检验字符串
     * @return 是 true 否 false
     */
    private boolean isNum(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > '9' || c < '0') {
                return false;
            }
        }
        return true;
    }
}
