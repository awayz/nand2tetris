/**
 * Created by Away
 * 2016/2/14
 */

public class JackAnalyzer {

    public static void main(String[] args) {
        String path = "C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\10\\Square";

        JackTokenizer jackTokenizer = new JackTokenizer(path + "\\Main.jack");

    }

    private static String output(String s, TokenType next) {
        return "<" + next + "> " + s + " </" + next + ">";
    }
}
