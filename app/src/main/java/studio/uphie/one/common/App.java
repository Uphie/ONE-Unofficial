package studio.uphie.one.common;

import android.app.Application;
import android.util.DisplayMetrics;

import com.facebook.FacebookSdk;
import com.umeng.fb.push.FeedbackPush;

import io.paperdb.Paper;
import studio.uphie.one.utils.ConfigUtil;
import studio.uphie.one.utils.CrashHandler;
import studio.uphie.one.utils.ImageUtil;
import studio.uphie.one.utils.NetworkUtil;
import studio.uphie.one.utils.TextToast;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public class App extends Application {

    public static DisplayMetrics displayMetrics;

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);

        CrashHandler.getInstance().init(this);
        ImageUtil.init(this);
        NetworkUtil.init(this);
        ConfigUtil.init(this);
        TextToast.init(this);

        //Facebook分享初始化
        FacebookSdk.sdkInitialize(getApplicationContext());
        //友盟意见反馈初始化
        FeedbackPush.getInstance(this).init(false);
    }
}
