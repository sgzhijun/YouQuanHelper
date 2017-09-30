package com.liompei.youquanhelper;

import android.app.Activity;
import android.app.Application;

import com.liompei.zxlog.Zx;
import com.vondear.rxtools.RxUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by Liompei
 * Time 2017/8/9 21:56
 * 1137694912@qq.com
 * remark:
 */

public class App extends Application {

    //***全局***
    public static App instance;

    public static App getInstance() {
        return instance;
    }

    private List<Activity> activityList = new ArrayList<>();

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void deleteActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void finishAllActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }

    // 退出
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        System.exit(0);
    }
    //***end***

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initZx();
        initSDK();
    }

    private void initZx() {
        Zx.initLog("blm", true);
        Zx.initToast(getApplicationContext(), true);
        RxUtils.init(this);  //RxUtils工具包
    }

    private void initSDK() {
        //默认初始化
        BmobConfig config = new BmobConfig.Builder(this)
                .setApplicationId("74a55af111e97a23e3970cd05a4d3da0")
                //设置超时时间
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(500 * 1024)
                .build();
        Bmob.initialize(config);

    }

}
