import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 书上完全没讲怎么写。。。崩溃了
 * 这里是输出
 * Created by Away
 * 2016/2/23
 */

public class VMWriter {

    private BufferedWriter writer;

    public VMWriter(String path) {
        try {
            writer = new BufferedWriter(new FileWriter(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePush(Segment segment, int idx) {

    }

    public void writePop(Segment segment, int idx) {

    }

    public void writeArithmetic(Command command) {

    }

    public void writeLabel(String label) {

    }

    public void writeGoto(String label) {

    }

    public void writeIf(String label) {

    }

    public void writeCall(String name, int nArgs) {

    }

    public void writeFunction(String name, int nLocals) {

    }

    public void writeReturn() {

    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String s) {
        try {
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

