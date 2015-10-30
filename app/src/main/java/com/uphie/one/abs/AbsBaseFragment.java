package com.uphie.one.abs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.uphie.one.R;
import com.uphie.one.common.HttpClient;
import com.uphie.one.common.HttpData;
import com.uphie.one.common.HttpError;
import com.uphie.one.interfaces.IHttp;
import com.uphie.one.interfaces.IInit;
import com.uphie.one.utils.NetworkUtil;
import com.uphie.one.utils.TextToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public abstract class AbsBaseFragment extends Fragment implements IInit, IHttp {

    private PopupWindow loadingWindow;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() == 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        rootView = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void getHttpData(final String url, RequestParams params, final HttpData httpData) {

        HttpClient.postByForm(url, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    if (responseString.contains("\"result\":\"FAIL\"")) {
                        //没有数据
                        HttpError error = new HttpError(statusCode, "", responseString);
                        onDataError(url, error);
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(responseString);

                        if (httpData == null) {
                            return;
                        }
                        String success = jsonObject.optString(httpData.result);
                        if (success.equals("SUCCESS")) {
                            String data = jsonObject.optString(httpData.data, "");
                            onDataOk(url, data);
                        } else {
                            HttpError error = new HttpError(statusCode, "", responseString);
                            onDataError(url, error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    HttpError error = new HttpError(statusCode, "", responseString);
                    onDataError(url, error);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                HttpError error = new HttpError(statusCode, throwable.toString(), responseString);
                onDataError(url, error);
            }
        });
    }

    /**
     * 显示和关闭加载界面
     *
     * @param show
     */
    public void toggleLoadingView(boolean show) {
        if (show) {
            //显示加载
            if (loadingWindow == null) {
                View view = View.inflate(getActivity(), R.layout.loading_window, null);

                loadingWindow = new PopupWindow(getActivity());
                loadingWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                loadingWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                loadingWindow.setContentView(view);
            }
            if (!loadingWindow.isShowing()) {
                loadingWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
            }
        } else {
            if (loadingWindow != null && loadingWindow.isShowing()) {
                loadingWindow.dismiss();
            }
        }
    }
}
