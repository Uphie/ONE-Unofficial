package com.uphie.one.utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;

/**
 * Created by Uphie on 2015/9/10.
 * Email: uphie7@gmail.com
 */
public class ImageUtil {

    private static ImageLoader imageLoader;
    private static ImageLoaderConfiguration config;
    private static DisplayImageOptions options;

    public static void init(Context context, String cachePath) {
        File cacheDir = new File(cachePath);
        imageLoader = ImageLoader.getInstance();
        config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(4)
                .diskCache(new UnlimitedDiskCache(cacheDir)).build();
        //考虑Exif信息，磁盘缓存，内存缓存，图片质量为considerExifParams，300ms渐进显示
        options = new DisplayImageOptions.Builder().considerExifParams(true).cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).displayer(new FadeInBitmapDisplayer(300)).build();
        imageLoader.init(config);
    }

    public static void showImage(String url, ImageView imageView) {
        imageLoader.displayImage(url, imageView, options);
    }
    public static void showImageWithProgress(String url, ImageView imageView, SimpleImageLoadingListener simpleImageLoadingListener) {
        imageLoader.displayImage(url, imageView, options,simpleImageLoadingListener);
    }
}
