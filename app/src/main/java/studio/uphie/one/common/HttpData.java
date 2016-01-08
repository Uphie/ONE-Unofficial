package studio.uphie.one.common;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class HttpData {
    public String result;
    public String data;

    public HttpData(String result, String data) {
        this.result = result == null ? "" : result;
        this.data = data == null ? "" : data;
    }
}
