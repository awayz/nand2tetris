import java.io.*;
import java.util.*;

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
        SYMBOL_MAP = new HashMap<>();

        for (String s : KEYWORDS) {
            KEYWORD_MAP.put(s, "keyword");
        }

        for (String s : SYMBOLS) {
            SYMBOL_MAP.put(s, "symbol");
        }

    }

    public class Token {
        public String token;
        public TokenType type;

        public Token(String token, TokenType type) {
            this.token = token;
            this.type = type;
        }
    }

    private List<Token> tokens;

    public JackTokenizer(String path) {
        process(delComment(path));
    }

    /**
     * 得到 token
     * @param strList 要处理的字符串
     */
    private void process(List<String> strList) {
        tokens = new ArrayList<>();
        String token;

        for (String str : strList) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                int j;

                if (c == '"') { // 字符串
                    for (j = i + 1; j < str.length(); j++) {
                        if (str.charAt(j) == '"' && str.charAt(j - 1) != '\\') {
                            break;
                        }
                    }

                    token = str.substring(i+1, j);
                    tokens.add(new Token(token, TokenType.STRING_CONSTANT));
                    i = j;
                }
                else if (isIdentifierStart(c)) { // identifier
                    for (j = i + 1; j < str.length(); j++) {
                        if (isIdentifierEnd(str.charAt(j))) {
                            break;
                        }
                    }

                    token = str.substring(i, j);
                    if (isKeyword(token)) {
                        tokens.add(new Token(token, TokenType.KEYWORD));
                    } else {
                        tokens.add(new Token(token, TokenType.IDENTIFIER));
                    }

                    i = j - 1;
                }
                else if (Character.isDigit(c)) { // 数字
                    for (j = i + 1; j < str.length(); j++) {
                        if (!Character.isDigit(str.charAt(j))) {
                            break;
                        }
                    }
                    token = str.substring(i, j);
                    tokens.add(new Token(token, TokenType.INTEGER_CONSTANT));
                    i = j - 1;
                }
                else if (isSymbol(c)) { // symbol
                    token = c + "";
                    tokens.add(new Token(token, TokenType.SYMBOL));
                }
            }
        }
    }

    private boolean isSymbol(char c) {
        return SYMBOL_MAP.containsKey(c + "");
    }

    private boolean isKeyword(String s) {
        return KEYWORD_MAP.containsKey(s);
    }

    private boolean isIdentifierStart(char c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isIdentifierEnd(char c) {
        return !Character.isDigit(c) && !Character.isLetter(c) && c != '_';
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
                int idx = line.indexOf("//");
                if (idx == -1) {
                    idx = line.indexOf("/*");
                }
                if (idx != -1) {
                    line = line.substring(0, idx);
                }
                if (line.length() != 0 && !line.startsWith("*")) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
