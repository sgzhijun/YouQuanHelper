package com.liompei.youquanhelper;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.liompei.youquanhelper.bean.CircleListBean;
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

public class App extends MultiDexApplication {

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
        AVObject.registerSubclass(CircleListBean.class);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"7NHNh8r3qmYShXBCqYriQjHF-gzGzoHsz","z499nWcuMIQdcRM2Lw8TeJNr");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }

}
