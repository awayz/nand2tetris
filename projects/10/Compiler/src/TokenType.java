/**
 * Created by Away
 * 2016/2/15
 */

public enum TokenType {
    KEYWORD("keyword"), SYMBOL("symbol"), INTEGER_CONSTANT("integerConstant"),
    STRING_CONSTANT("stringConstant"), IDENTIFIER("identifier");

    private String name;

    TokenType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
