import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 初步解析单个文件的命令
 * 假设所有的命令都符合规范
 * Created by Away
 * 2016/2/6
 */

public class Parser {

    private BufferedReader bufReader;
    // 指令部分
    private String cmd;
    // 第一个参数
    private String arg1;
    // 第二个参数
    private int arg2;

    public Parser(String path) {
        try {
            bufReader = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取下一条非空指令，删去注释部分和前后空格，将指令分割
     */
    public void advance() {
        try {
            String tmp = bufReader.readLine();
            tmp = delComment(tmp);

            if (!isEmptyLine(tmp)) {
                process(tmp.trim());
                System.out.println(tmp.trim());
            } else {
                if (hasNextCmd()) {
                    advance();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否有下一条指令
     */
    public boolean hasNextCmd() {
        try {
            return bufReader.ready();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删去注释部分
     */
    private String delComment(String tmp) {
        if (null != tmp) {
            int idx = tmp.indexOf("//");
            if (idx != -1) {
                tmp = tmp.substring(0, idx);
            }
        }
        return tmp;
    }

    /**
     * 判断字符串是否为空或由空格组成
     * @param s 带判断字符串
     * @return 空 true，否则 false
     */
    private boolean isEmptyLine(String s) {
        if (null != s) {
            for (char c : s.toCharArray()) {
                if (c != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 分割指令
     * @param currentCmd 当前指令
     */
    private void process(String currentCmd) {
        String[] strs = currentCmd.split(" ");
        int len = strs.length;
        cmd = arg1 = null;
        arg2 = 0;

        cmd = strs[0];
        if (len > 1) {
            arg1 = strs[1];
        }
        if (len > 2) {
            arg2 = Integer.parseInt(strs[2]);
        }
    }

    public String getCmd() {
        return cmd;
    }

    public String getArg1() {
        return arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void close() {
        try {
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
