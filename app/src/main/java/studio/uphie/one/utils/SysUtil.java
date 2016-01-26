package studio.uphie.one.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class SysUtil {

    /**
     * 获得设备屏幕的相关信息
     *
     * @param context Activity
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 获得设备型号
     * @return 手机设备型号
     */
    public static String getPhoneModel(){
        return Build.MODEL;
    }

    /**
     * 获得设备序列号，但在某些山寨或定制设备上会得到垃圾数据
     * @return 手机设备序列号
     */
    public static String getPhoneSerial(){
        return Build.SERIAL;
    }

    /**
     * SD卡是否存在
     *
     * @return
     */
    public static boolean isSdExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
