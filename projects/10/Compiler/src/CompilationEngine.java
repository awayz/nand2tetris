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

    private BufferedWriter writer;

    // 缩进
    private int space;

    private Iterator<JackTokenizer.Token> tokens;

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
            JackTokenizer.Token token = tokens.next();
            switch (token.token) {
                case "field":
                    compileClassVarDec(token);
                    break;
                case "constructor":
                case "method":
                case "function":
                    compileSubroutine(token);
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
    private void compileClassVarDec(JackTokenizer.Token t) {
        String name = "classVarDec";
        markStart(name);

        output(t);

        while (tokens.hasNext()) {
            JackTokenizer.Token token = tokens.next();
            output(t);
            if (token.token.equals(";")) {
                break;
            }
        }
        markEnd(name);
    }

    /**
     * 处理函数
     */
    private void compileSubroutine(JackTokenizer.Token t) {
        String name = "subroutineDec";
        String n = "subroutineBody";
        markStart(name);

        output(t);              // 函数类型
        output(tokens.next());  // 返回类型
        output(tokens.next());  // 函数名
        compileParameterList(); // 处理参数
        markStart(n);           // sub..Body
        compileStatements();
        markEnd(n);             // /sub..Body
        markEnd(name);
    }

    private void compileParameterList() {
        output(tokens.next());  // (

        String name = "parameterList";
        markStart(name);
        JackTokenizer.Token token = null;

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

    private void compileVarDec() {

    }

    private void compileStatements() {
        output(tokens.next()); // {

        String name = "statements";
        markStart(name);
        JackTokenizer.Token token = null;

        while (tokens.hasNext()) {
            token = tokens.next();

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
                case "}":
                    break;
            }
        }

        markEnd(name);
        output(token); // }
    }

    private void compileDo() {

    }

    private void compileLet() {

    }

    private void compileWhile() {

    }

    private void compileIf() {

    }

    private void compileReturn() {

    }

    private void compileExpression() {

    }

    private void compileTerm() {

    }

    private void compileExpressionList() {

    }

    private void markStart(String name) {
        write("<" + name + ">");
        space++;
    }

    private void markEnd(String name) {
        space--;
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
