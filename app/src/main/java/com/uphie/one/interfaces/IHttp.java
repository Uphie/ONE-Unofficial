package com.uphie.one.interfaces;

import com.loopj.android.http.RequestParams;
import com.uphie.one.common.HttpError;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public interface IHttp {

    void getHttpData(String url, RequestParams param);

    void onDataOk(String url, String data);

    void onDataError(String url, HttpError error);
}
