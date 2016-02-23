import java.util.HashMap;

/**
 * 符号表
 * 分为 类范围 和 函数范围（局部范围）
 * Created by Away
 * 2016/2/23
 */

public class SymbolTable {

    private class TableLine {
        public String type;
        public SymbolKind kind;
        public int idx;

        public TableLine(String type, SymbolKind kind, int idx) {
            this.type = type;
            this.kind = kind;
            this.idx = idx;
        }
    }

    private HashMap<String, TableLine> classMap;

    private HashMap<String, TableLine> functionMap;

    private int classIdx;

    private int functionIdx;

    public SymbolTable() {
        classMap = new HashMap<>();
        functionMap = new HashMap<>();
        classIdx = functionIdx = 0;
    }

    /**
     * 在表中插入新的 symbol
     */
    public void define(String name, String type, SymbolKind kind) {
        if (isInClassScope(kind)) {
            classMap.put(name, new TableLine(type, kind, classIdx));
            classIdx++;
        } else {
            functionMap.put(name, new TableLine(type, kind, functionIdx));
            functionIdx++;
        }
    }

    private boolean isInClassScope(SymbolKind kind) {
        return kind.equals(SymbolKind.FIELD) || kind.equals(SymbolKind.STATIC);
    }

    public int varCount(SymbolKind kind) {
        int sum = 0;
        for (TableLine symbol : classMap.values()) {
            if (symbol.kind.equals(kind)) {
                sum++;
            }
        }

        for (TableLine symbol : functionMap.values()) {
            if (symbol.kind.equals(kind)) {
                sum++;
            }
        }

        return sum;
    }

    public SymbolKind kindOf(String name) {
        TableLine symbol = functionMap.get(name);
        if (symbol == null) {
            symbol = classMap.get(name);
        }

        return symbol.kind;
    }

    public int indexOf(String name) {
        TableLine symbol = functionMap.get(name);
        if (symbol == null) {
            symbol = classMap.get(name);
        }

        return symbol.idx;
    }
}
