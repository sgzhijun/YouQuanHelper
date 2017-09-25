package com.liompei.youquanhelper.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * Created by Liompei
 * time : 2017/9/25 15:43
 * 1137694912@qq.com
 * remark:
 */

public class MyGlideEngine implements ImageEngine {

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        GlideApp.with(context)
                .load(uri)
                .placeholder(placeholder)
                .override(resize, resize)
                .centerCrop()
                .into(imageView);

//        Glide.with(context)
//                .load(uri)
//                .asBitmap()  // some .jpeg files are actually gif
//                .placeholder(placeholder)
//                .override(resize, resize)
//                .centerCrop()
//                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {

        GlideApp.with(context)
                .load(uri)
                .placeholder(placeholder)
                .override(resize, resize)
                .centerCrop()
                .into(imageView);
//        Glide.with(context)
//                .load(uri)
//                .asBitmap()
//                .placeholder(placeholder)
//                .override(resize, resize)
//                .centerCrop()
//                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        GlideApp.with(context)
                .load(uri)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .into(imageView);

//        Glide.with(context)
//                .load(uri)
//                .override(resizeX, resizeY)
//                .priority(Priority.HIGH)
//                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        GlideApp.with(context)
                .load(uri)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .into(imageView);
//        Glide.with(context);
//                .load(uri)
//                .asGif()
//                .override(resizeX, resizeY)
//                .priority(Priority.HIGH)
//                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }

}