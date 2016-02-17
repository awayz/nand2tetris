/**
 * Created by Away
 * 2016/2/14
 */

public class JackAnalyzer {

    public static void main(String[] args) {
        String path = "C:\\Users\\jone\\Desktop\\nand2tetris\\projects\\10\\ArrayTest";

        JackTokenizer jackTokenizer = new JackTokenizer(path + "\\Main.jack");
        CompilationEngine compilationEngine = new CompilationEngine(
                jackTokenizer.getTokens().iterator(), path + "\\MainOut.xml");
    }
}
