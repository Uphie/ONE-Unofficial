package studio.uphie.one.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Uphie on 2015/9/7.
 * Email: uphie7@gmail.com
 */
public class TextToast {

    private static Context context;

    public static void init(Context ctx){
        context=ctx;
    }

    public static void shortShow(String content){
        if (content==null){
            throw new IllegalStateException("TextToast未初始化");
        }
        Toast toast= Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void longShow(String content){
        if (content==null){
            throw new IllegalStateException("TextToast未初始化");
        }
        Toast toast= Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
