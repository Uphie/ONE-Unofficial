package studio.uphie.one.interfaces;

import com.loopj.android.http.RequestParams;
import studio.uphie.one.common.HttpData;
import studio.uphie.one.common.HttpError;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public interface IHttp {

    void getHttpData(String url, RequestParams param,HttpData httpData);

    void onDataOk(String url, String data);

    void onDataError(String url, HttpError error);
}
