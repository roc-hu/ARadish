package com.hcp.aradish.newwork;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.volley.VolleyGlideModule;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;

/**
 * Created by hcp on 15/6/26.
 */
public class GlideConfig extends VolleyGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 50 * 1024 * 1024));
        // or:
//        builder.setDiskCache(new ExternalDiskCacheFactory(context, "cache_dir_h", 50 * 1024 * 1024));
        /**
         * 外部存储 sdcard/Android/data/包名/cache/cache_dir_hcp
         */
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache_dir_hcp", 50 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        super.registerComponents(context, glide);
//        glide.register(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(context));
    }

    /**
     * 其他 外部存储 sdcard/cache_dir_hcp
     */
    public class ExternalDiskCacheFactory implements DiskCache.Factory{
        Context mContext;
        String cacheName;
        int maxSize;
        public ExternalDiskCacheFactory(Context context,String cacheFileName,int fileMaxSize){
            mContext=context;
            cacheName=cacheFileName;
            maxSize=fileMaxSize;
        }
        @Override
        public DiskCache build() {
            // Careful: the external cache directory doesn't enforce permissions
            File cacheLocation = new File(Environment.getExternalStorageDirectory(), cacheName);
            cacheLocation.mkdirs();
            return DiskLruCacheWrapper.get(cacheLocation, maxSize);
        }
    }
}
