package com.liompei.youquanhelper.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.liompei.youquanhelper.MainActivity;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.zxlog.Zx;

/**
 * Created by Liompei
 * Time 2017/9/12 20:37
 * 1137694912@qq.com
 * remark:
 */

public class SplashActivity extends BaseActivity {

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, SplashActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //去掉标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉信息栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyUser myUser = MyUser.getCurrentUser(MyUser.class);
                if (myUser != null) {
                    // 允许用户使用应用
                    Zx.v("已有用户登录");
                    MainActivity.start(SplashActivity.this);
                } else {
                    //缓存用户对象为空时,可打开用户注册界面
                    Zx.v("无用户");
                    SignInActivity.start(SplashActivity.this);
                }
                finish();
            }
        }, 1000);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }
}
