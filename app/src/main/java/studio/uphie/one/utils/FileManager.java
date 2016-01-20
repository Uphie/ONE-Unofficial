package studio.uphie.one.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Uphie on 2015/9/10.
 * Email: uphie7@gmail.com
 */
public class FileManager {

    public static final String DIR_IMG_CACHE = "img";
    private static String appDir;

    /**
     * 获得应用主目录
     *
     * @return 主目录路径
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
            //当appDir存在时，返回false；创建成功时，返回true
            file.mkdirs();
        }
        return appDir;
    }

}
