package com.hcp.aradish.newwork;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * 图片加载
 * Created by hcp on 15/6/17.
 */
public class ImageLoader {

    /**
     * 加载图片
     *
     * @param context
     * @param uri
     * @param view
     * @param listener Bitmap
     */
    public static void display(Context context, String uri, ImageView view, final ImageLoadingListener<Bitmap> listener) {
        if (context == null || !(context instanceof Activity) || ((Activity) context).isFinishing()) {
            return;
        }
        if (listener == null) {
            throw new IllegalArgumentException("You must pass in a non null ImageLoadingListener<Bitmap>");
        }
        Glide.with(context).load(uri).asBitmap().into(new BitmapImageViewTarget(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                listener.onLoadingComplete(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                listener.onLoadingFailed();
            }
        });
    }

    /**
     * 加载图片
     *
     * @param activity
     * @param uri
     * @param view
     * @param listener Bitmap
     */
    public static void display(Activity activity, String uri, ImageView view, final ImageLoadingListener<Bitmap> listener) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (listener == null) {
            throw new IllegalArgumentException("You must pass in a non null ImageLoadingListener<Bitmap>");
        }
        Glide.with(activity).load(uri).asBitmap().into(new BitmapImageViewTarget(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                listener.onLoadingComplete(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                listener.onLoadingFailed();
            }
        });
    }

    /**
     * 加载图片
     *
     * @param fragment
     * @param uri
     * @param view
     * @param listener Bitmap
     */
    public static void display(Fragment fragment, String uri, ImageView view, final ImageLoadingListener<Bitmap> listener) {
        if (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing()) {
            return;
        }
        if (listener == null) {
            throw new IllegalArgumentException("You must pass in a non null ImageLoadingListener<Bitmap>");
        }
        Glide.with(fragment).load(uri).asBitmap().into(new BitmapImageViewTarget(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                listener.onLoadingComplete(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                listener.onLoadingFailed();
            }
        });
    }

    /**
     * 加载图片
     *
     * @param context
     * @param resourceId
     * @param view
     */
    public static void display(Context context, Integer resourceId, ImageView view) {
        if (context == null || !(context instanceof Activity) || ((Activity) context).isFinishing()) {
            return;
        }
        Glide.with(context).load(resourceId).into(view);
    }

    /**
     * 加载图片
     *
     * @param activity
     * @param resourceId
     * @param view
     */
    public static void display(Activity activity, Integer resourceId, ImageView view) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Glide.with(activity).load(resourceId).into(view);
    }

    /**
     * 加载图片
     *
     * @param fragment
     * @param resourceId
     * @param view
     */
    public static void display(Fragment fragment, Integer resourceId, ImageView view) {
        if (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing()) {
            return;
        }
        Glide.with(fragment).load(resourceId).into(view);
    }

    public static void display(Fragment fragment, Integer resourceId, ImageView view, final ImageLoadingListener<Bitmap> listener) {
        if (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing()) {
            return;
        }
        Glide.with(fragment).load(resourceId).asBitmap().into(new BitmapImageViewTarget(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                listener.onLoadingComplete(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                listener.onLoadingFailed();
            }
        });
    }

    /**
     * 加载图片
     *
     * @param context
     * @param uri
     * @param view
     */
    public static void display(Context context, String uri, ImageView view) {
        if (context == null || !(context instanceof Activity) || ((Activity) context).isFinishing()) {
            return;
        }
        Glide.with(context).load(uri).into(view);
    }

    /**
     * 加载图片
     *
     * @param activity
     * @param uri
     * @param view
     */
    public static void display(Activity activity, String uri, ImageView view) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Glide.with(activity).load(uri).into(view);
    }

    /**
     * 加载图片
     *
     * @param fragment
     * @param uri
     * @param view
     */
    public static void display(Fragment fragment, String uri, ImageView view) {
        if (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing()) {
            return;
        }
        Glide.with(fragment).load(uri).into(view);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param uri
     * @param loadingResId
     * @param view
     */
    public static void display(Context context, String uri, int loadingResId, ImageView view) {
        if (context == null || !(context instanceof Activity) || ((Activity) context).isFinishing()) {
            return;
        }
        Glide.with(context).load(uri).placeholder(loadingResId).error(loadingResId).into(view);
    }

    /**
     * 加载图片
     *
     * @param activity
     * @param uri
     * @param loadingResId
     * @param view
     */
    public static void display(Activity activity, String uri, int loadingResId, ImageView view) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Glide.with(activity).load(uri).placeholder(loadingResId).error(loadingResId).into(view);
    }

    /**
     * 加载图片
     *
     * @param fragment
     * @param uri
     * @param loadingResId
     * @param view
     */
    public static void display(Fragment fragment, String uri, int loadingResId, ImageView view) {
        if (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing()) {
            return;
        }
        Glide.with(fragment).load(uri).placeholder(loadingResId).error(loadingResId).into(view);
    }

//    public static void displayThumb(Context context,int thumbId,ImageView view){
//        Glide.with(context).fromMediaStore().
//    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /**
     * 清除缓存 包含内存和磁盘缓存
     *
     * @param context
     */
    public static void clearCache(Context context) {
        Glide.get(context).clearMemory();
        Glide.get(context).clearDiskCache();
//        File cacheDir=Glide.getPhotoCacheDir(context);
//        if (cacheDir.isDirectory()) {
//            for (File child : cacheDir.listFiles()) {
//                if (!child.delete()) {
//                    Log.w(TAG, "cannot delete: " + child);
//                }
//            }
//        }
    }

    /**
     * 图片加载监听
     *
     * @param <T> Bitmap
     */
    public interface ImageLoadingListener<T> {
        void onLoadingComplete(T resource);

        void onLoadingFailed();
    }
}
