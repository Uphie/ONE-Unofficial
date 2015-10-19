package com.uphie.one.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Uphie on 2015/9/10.
 * Email: uphie7@gmail.com
 */
public class FileManager {

    private static String dir_imgCache;

    /**
     * 获得图片缓存路径
     * @return
     */
    public static String getImgTempDir() {
        if (dir_imgCache == null) {
            if (SysUtil.isSdExist()) {
                dir_imgCache = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/One/img";
            } else {
                dir_imgCache = Environment.getRootDirectory()
                        .getAbsolutePath() + "/One/img";
            }
        } else {
            File file = new File(dir_imgCache);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir_imgCache;
    }
}
