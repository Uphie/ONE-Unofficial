package com.uphie.one.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Uphie on 2015/9/10.
 * Email: uphie7@gmail.com
 */
public class FileManager {

    public static final String DIR_PIC_CACHE="img";
    private static String appDir;

    /**
     * 获得图片缓存路径
     * @return
     */
    public static String getAppDir() {
        if (appDir == null) {
            if (SysUtil.isSdExist()) {
                appDir = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/One";
            } else {
                appDir = Environment.getRootDirectory()
                        .getAbsolutePath() + "/One";
            }
        } else {
            File file = new File(appDir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return appDir;
    }

}
