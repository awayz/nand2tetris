import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * 完全不按剧本来写了。。。=_=
 * Created by Away
 * 2016/2/14
 */

public class CompilationEngine {

    private static final String OP = "+, -, *, /, &, |, &lt;, &gt;, &amp";

    private BufferedWriter writer;

    // 缩进
    private int space;

    private Iterator<JackTokenizer.Token> tokens;

    // 正在处理的 token
    private JackTokenizer.Token token;

    public CompilationEngine(Iterator<JackTokenizer.Token> tokens, String path) {
        try {
            writer = new BufferedWriter(new FileWriter(path));
            this.tokens = tokens;

            compileClass();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void compileClass() {
        String name = "class";
        markStart(name);

        while (tokens.hasNext()) {
            token = tokens.next();
            switch (token.token) {
                case "field":
                    compileClassVarDec();
                    break;
                case "constructor":
                case "method":
                case "function":
                    compileSubroutine();
                    break;
                default:
                    output(token);
                    break;
            }
        }

        markEnd(name);
    }

    /**
     * 处理成员变量
     */
    private void compileClassVarDec() {
        String name = "classVarDec";
        markStart(name);

        while (tokens.hasNext()) {
            output(token);

            if (token.token.equals(";")) {
                break;
            }

            token = tokens.next();
        }
        markEnd(name);
    }

    /**
     * 处理函数
     */
    private void compileSubroutine() {
        String name = "subroutineDec";
        String n = "subroutineBody";
        markStart(name);

        output(token);          // 函数类型
        output(tokens.next());  // 返回类型
        output(tokens.next());  // 函数名
        compileParameterList(); // 处理参数
        markStart(n);           // <sub..Body>
        output(tokens.next());  // {

        while (tokens.hasNext()) {
            token = tokens.next();

            if (token.token.equals("var")) {
                compileVarDec();
            } else {
                break;
            }
        }

        compileStatements();

        output(token);          // }
        markEnd(n);             // </sub..Body>
        markEnd(name);
    }

    private void compileParameterList() {
        output(tokens.next());  // (

        String name = "parameterList";
        markStart(name);

        while (tokens.hasNext()) {
            token = tokens.next();

            if (token.token.equals(")")) {
                break;
            }

            output(token);
        }

        markEnd(name);
        output(token);   // )
    }

    /**
     * 局部变量
     */
    private void compileVarDec() {
        String name = "varDec";
        markStart(name);

        while (tokens.hasNext()) {
            output(token);

            if (token.token.equals(";")) {
                break;
            }

            token = tokens.next();
        }

        markEnd(name);
    }

    private void compileStatements() {
        String name = "statements";
        markStart(name);

        while (tokens.hasNext()) {
            switch (token.token) {
                case "new":
                    compileParameterList();
                    break;
                case "let":
                    compileLet();
                    break;
                case "do":
                    compileDo();
                    break;
                case "var":
                    compileVarDec();
                    break;
                case "while":
                    compileWhile();
                    break;
                case "if":
                    compileIf();
                    break;
                case "return":
                    compileReturn();
                    break;
            }

            if (token.token.equals("}")) {
                break;
            }

            token = tokens.next();
        }

        markEnd(name);
    }

    private void compileDo() {

    }

    private void compileLet() {
        String name = "letStatement";
        markStart(name);

        output(token); // let
        output(tokens.next()); // xxx
        output(tokens.next()); // =
        compileExpression();

        markEnd(name);
    }

    private void compileWhile() {

    }

    private void compileIf() {

    }

    private void compileReturn() {

    }

    /**
     * term (op term)*
     */
    private void compileExpression() {
        String name = "expression";
        markStart(name);

        while (tokens.hasNext()) {
            compileTerm(); // term 遇到 op 或 ; 结束

            output(token); // 输出 op 或 ;

            if (token.token.equals(";")) {
                break;
            }
        }

        markEnd(name);
    }

    private boolean isOP(String s) {
        return OP.contains(s);
    }

    private void compileTerm() {
        String name = "term";
        markStart(name);

        while (tokens.hasNext()) {


        }

        markEnd(name);
    }

    private void compileExpressionList() {

    }

    private void markStart(String name) {
        write("<" + name + ">");
        space += 2;
    }

    private void markEnd(String name) {
        space -= 2;
        write("</" + name + ">");
    }

    private String getSpace() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < space; i++) {
            s.append(" ");
        }

        return s.toString();
    }

    private void write(String s) {
        try {
            writer.write(getSpace() + s + "\n");
            System.out.print(getSpace() + s + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void output(JackTokenizer.Token token) {
        write("<" + token.type + "> " + token.token + " </" + token.type + ">");
    }

}
