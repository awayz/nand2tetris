import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 只做最最简单的处理，省略了很多情况。QAQ
 * Created by Away
 * 2016/2/14
 */

public class JackTokenizer {

    public static final String[] KEYWORDS = { "class", "constructor", "function", "method", "field",
            "static", "var", "int", "char", "boolean", "void", "true", "false", "null", "this", "let",
            "do", "if", "else", "while", "return" };

    public static final String[] SYMBOLS = { "{", "}", "(", ")", "[", "]", ".", ",", ";", "+", "-",
            "*", "/", "&", "|", "<", ">", "=", "~" };

    public static final Map<String, String> KEYWORD_MAP;

    public static final Map<String, String> SYMBOL_MAP;

    static {
        KEYWORD_MAP = new HashMap<>();

        for (String s : KEYWORDS) {
            KEYWORD_MAP.put(s, "keyword");
        }

        SYMBOL_MAP = new HashMap<>();

        for (String s : SYMBOLS) {
            SYMBOL_MAP.put(s, "symbol");
        }
    }

    private List<String> tokens;

    public JackTokenizer(String path) {
        process(delComment(path));
    }

    private void process(List<String> strList) {
        tokens = new ArrayList<>();
        String token;

        for (String str : strList) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                int j = 0;

                if (c == '"') { // 字符串
                    for (j = i + 1; j < str.length(); j++) {
                        if (str.charAt(j) == '"' && str.charAt(j - 1) != '\\') {
                            break;
                        }
                    }

                    token = str.substring(i+1, j);
                    i = j;
                }
                else if (Character.isLetter(c)) {
                    for (j = i + 1; j < str.length(); j++) {
                        if (!Character.isLetter(str.charAt(j))) {
                            break;
                        }
                    }

                    token = str.substring(i, j);
                    i = j - 1;
                }
                else if (c != ' ' && c != '\t') {
                    token = c + "";
                }
                else {
                    continue;
                }

                System.out.println(token);
            }
        }
    }

    /**
     * 删去注释
     * 待完善
     * @param path 文件路径
     */
    private List<String> delComment(String path) {
        String line;
        List<String> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.length() != 0 && !line.startsWith("/") && !line.startsWith("*")) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
//        String path = "C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\10\\ArrayTest\\Main.jack";
        String path = "C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\10\\ExpressionlessSquare\\SquareGame.jack";

        JackTokenizer jackTokenizer = new JackTokenizer(path);

    }
}
