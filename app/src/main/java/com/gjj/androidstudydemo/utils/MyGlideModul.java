package com.gjj.androidstudydemo.utils;


import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by 高娟娟 on 2018/4/2.
 */

public class MyGlideModul extends AppGlideModule{

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        //设置磁盘缓存大小
        int size = 100 * 1024 * 1024;
        String dirFolder = "xia";
        String dirName = "yu";
        //设置磁盘缓存
        builder.setDiskCache(new DiskLruCacheFactory(dirFolder, dirName,size));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
