package com.liompei.youquanhelper.util;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.liompei.youquanhelper.App;
import com.liompei.youquanhelper.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static cn.bmob.v3.Bmob.getCacheDir;

/**
 * Created by Liompei
 * Time 2017/9/13 23:42
 * 1137694912@qq.com
 * remark:图片加载框架
 */

public class GlideUtils {

    public static void loadHead(ImageView imageView, @Nullable Object model) {
        RequestOptions mOptionsHead = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_default_head_24dp)
                .error(R.drawable.ic_default_head_24dp)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);  //不缓存
        Glide.with(App.getInstance())
                .load(model)
                .apply(mOptionsHead)
                .into(imageView);
    }

    public static void initUCrop(Activity activity, Uri uri) {
        //Uri destinationUri = RxPhotoUtils.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.primary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.primary_dark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(666);
        //设置裁剪窗口是否为椭圆
//        options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
//        options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
//        options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
//        options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
//        options.setCropGridColumnCount(2);
        //设置横线的数量
//        options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(1000, 1000)
                .withOptions(options)
                .start(activity);
    }

}
