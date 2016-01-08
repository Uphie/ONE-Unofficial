package studio.uphie.one.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public class NetworkUtil {
    private static ConnectivityManager conManager;
    private static NetworkUtil instance;
    private static Context context;

    private NetworkUtil(Context context) {
        NetworkUtil.conManager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
    }

    public static void init(Context ctx) {
        NetworkUtil.context = ctx;
    }

    public static NetworkUtil getInstance() {
        if (NetworkUtil.instance == null) {
            NetworkUtil.instance = new NetworkUtil(context);
        }
        return NetworkUtil.instance;
    }

    /**
     * @return boolean
     * @description 检测服务端是否连接正常
     * @date 2014年11月9日
     */
    public boolean checkNetworkAvailable() {
        return checkMobileActive() || checkWifiActive();
    }

    /**
     * @param context
     * @param
     * @return
     * @description 判断网络是否是漫游
     * @date 2014-12-5
     * @author 史永飞
     */
    public boolean isNetworkRoaming(Context context) {
        if (conManager != null) {
            NetworkInfo info = conManager.getActiveNetworkInfo();
            if (info != null
                    && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.isNetworkRoaming()) {
                    Log.d("Tag", "network is roaming");
                    return true;
                } else {
                    Log.d("Tag", "network is not roaming");
                }
            }
        }
        return false;
    }

    /**
     * @return boolean
     * @description 检测Wifi是否可用
     * @date 2014年11月9日
     */
    public boolean checkWifiActive() {
        return conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * @return boolean
     * @description 检测移动网络是否可用
     * @date 2014年11月9日
     */
    public boolean checkMobileActive() {
        return conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED;
    }
}
