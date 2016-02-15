import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 完全不按剧本来写了。。。=_=
 * Created by Away
 * 2016/2/14
 */

public class CompilationEngine {

    private BufferedWriter writer;

    public CompilationEngine(List<String> tokens, List<TokenType> types, String path) {
        try {
            writer = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        process(tokens, types);
    }

    /**
     * 处理解析后的文本
     * @param tokens token
     * @param types token 的类型
     */
    private void process(List<String> tokens, List<TokenType> types) {
        for (String s : tokens) {
            
        }
    }

    private void compileClass() {

    }

    private void compileClassVarDec() {

    }

    private void compileSubroutine() {

    }

    private void compileParameterList() {

    }

    private void write(String s) {
        try {
            writer.write(s + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
