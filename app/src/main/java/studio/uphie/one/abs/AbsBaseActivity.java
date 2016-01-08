package studio.uphie.one.abs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;
import studio.uphie.one.common.HttpClient;
import studio.uphie.one.common.HttpData;
import studio.uphie.one.common.HttpError;
import studio.uphie.one.interfaces.IHttp;
import studio.uphie.one.interfaces.IInit;
import studio.uphie.one.interfaces.OnNetConnChangeListener;
import studio.uphie.one.utils.NetworkUtil;
import studio.uphie.one.utils.TextToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public abstract class AbsBaseActivity extends Activity implements IInit, IHttp, OnNetConnChangeListener {
    private InputMethodManager softManager;
    private NetworkStateChangeReceiver mNetworkStateChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() == 0) {
            return;
        }
        setContentView(getLayoutId());

        //获得软键盘管理器
        softManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //注册监听器
        mNetworkStateChangeReceiver = new NetworkStateChangeReceiver();
        IntentFilter mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateChangeReceiver, mIntentFilter);

        ButterKnife.bind(this);
        init();
    }

    @Override
    public void getHttpData(final String url, RequestParams params, final HttpData httpData) {

        HttpClient.postByForm(url, params, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
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

    @Override
    public void onNetworkDisconnected() {
        TextToast.shortShow("网络连接异常");
    }

    /**
     * 是否应该隐藏软键盘
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (softManager != null) {
                    softManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销监听器
        unregisterReceiver(mNetworkStateChangeReceiver);
    }

    /**
     * 检测网络连接状态的广播接收器
     */
    private class NetworkStateChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isAvailable = NetworkUtil.getInstance()
                    .checkNetworkAvailable();
            if (!isAvailable) {
                onNetworkDisconnected();
            }
        }
    }
}
