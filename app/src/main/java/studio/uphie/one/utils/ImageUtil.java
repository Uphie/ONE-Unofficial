package studio.uphie.one.utils;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

/**
 * Created by Uphie on 2015/9/10.
 * Email: uphie7@gmail.com
 */
public class ImageUtil {

    /**
     * 最大图片缓存大小，100M
     */
    private static final long MAX_IMG_CACHE_SIZE = 1024 * 1024 * 100;

    public static void init(Context context) {
        File cacheDir = new File(FileManager.getAppDir());

        //Fresco配置和初始化
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
                .setBaseDirectoryPath(cacheDir)                     //缓存文件夹所在的路径
                .setBaseDirectoryName(FileManager.DIR_IMG_CACHE)    //缓存文件夹名称
                .setMaxCacheSize(MAX_IMG_CACHE_SIZE).build();       //最大缓存文件大小，必须设置
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(context).setMainDiskCacheConfig(diskCacheConfig).build();
        Fresco.initialize(context, imagePipelineConfig);
    }
}
