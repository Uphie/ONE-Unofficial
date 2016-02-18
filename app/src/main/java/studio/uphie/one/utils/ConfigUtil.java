package studio.uphie.one.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Uphie on 2015/12/17 0017.
 * Email:uphie7@gmail.com
 */
public class ConfigUtil {

    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    /**
     * 写String到本地
     * @param file 存储的文件名
     * @param key key
     * @param value 结果
     */
    public static void writeString(String file, String key, String value) {
        Editor editor = context.getSharedPreferences(file, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 读取String
     * @param file 存储的文件名
     * @param key key
     * @return 结果
     */
    public static String readString(String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    /**
     * 写boolean配置到本地
     * @param file 存储的文件名
     * @param key key
     * @param value 结果
     */
    public static void writeBoolean(String file, String key, boolean value) {
        Editor editor = context.getSharedPreferences(file, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 读取boolean配置,若没有该配置，则返回true
     * @param file 存储的文件名
     * @param key key
     * @return 结果
     */
    public static boolean readBoolean(String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    /**
     * 写int到本地
     * @param file 存储的文件名
     * @param key key
     * @param value 写入的值
     */
    public static void writeInt(String file, String key, int value) {
        Editor editor = context.getSharedPreferences(file, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 读取int
     * @param file 读取的文件名
     * @param key key
     * @return 读取的值，若Key对应的值不存在，则返回默认值0
     */
    public static int readInt(String file, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * 删除配置
     * @param file 配置文件名
     */
    public static void removeConfig(String file){
        SharedPreferences sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}

