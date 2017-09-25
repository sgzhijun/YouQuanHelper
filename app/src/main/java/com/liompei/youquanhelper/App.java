package com.liompei.youquanhelper;

import android.app.Activity;
import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.zxlog.Zx;
import com.vondear.rxtools.RxUtils;

import java.util.ArrayList;
import java.util.List;

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
        //为了防止 AVUser 子类在序列化与反序列化时丢失数据，需要在调用 AVOSCloud.initialize() 之前注册该子类
        AVUser.registerSubclass(MyUser.class);
        //当用户子类化 AVUser 后，如果希望以后查询 AVUser 所得到的对象会自动转化为用户子类化的对象，则需要在调用 AVOSCloud.initialize() 之前添加
        AVUser.alwaysUseSubUserClass(MyUser.class);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"Lp1eeWjNeYeqi1Qgxr75Et8h-gzGzoHsz","nBsW0IsYITzXTuLe9YWzbkKS");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        //在应用发布之前，请关闭调试日志，以免暴露敏感数据。
        AVOSCloud.setDebugLogEnabled(true);
    }

}
