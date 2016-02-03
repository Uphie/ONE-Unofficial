package studio.uphie.one.abs;

import android.app.Activity;
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

import studio.uphie.one.R;
import studio.uphie.one.common.HttpClient;
import studio.uphie.one.common.HttpData;
import studio.uphie.one.common.HttpError;
import studio.uphie.one.interfaces.IHttp;
import studio.uphie.one.interfaces.IInit;
import studio.uphie.one.utils.NetworkUtil;
import studio.uphie.one.utils.TextToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import studio.uphie.one.utils.TimeUtil;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public abstract class AbsBaseFragment extends Fragment implements IInit, IHttp {

    /**
     * 当前的日期
     */
    protected String curDate;
    /**
     * 当前的索引
     */
    protected int index;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() == 0) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        View rootView = inflater.inflate(getLayoutId(), null);
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

        if (!NetworkUtil.getInstance().checkNetworkAvailable()) {
            //网络不可用,使用本地缓存
            onRestoreData(url);
            return;
        }

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
     * 当前是否是第一页
     * @return
     */
    public boolean isFirstPage(){
        return index==1;
    }
    /**
     * 是否过期，限制查看7天内的往期内容
     *
     * @return
     */
    public boolean isExpired() {
        return TimeUtil.getDateDifference(curDate) > 7;
    }

    public abstract <T> T getContentData();

    public abstract void refreshUI(Object data);

    public abstract void finish();
}
