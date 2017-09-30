package com.liompei.youquanhelper.main.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.liompei.youquanhelper.MainActivity;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.util.MyPermissionUtil;
import com.liompei.zxlog.Zx;

import java.util.List;

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
                if (MyPermissionUtil.checkPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                    toStart();
                } else {
                    MyPermissionUtil.requestPermission(SplashActivity.this, mPermissionCallbacks, Manifest.permission.READ_PHONE_STATE);
                }
            }
        }, 500);
    }

    private MyPermissionUtil.PermissionCallbacks mPermissionCallbacks = new MyPermissionUtil.PermissionCallbacks() {
        @Override
        public void onPermissionsGranted(List<String> list) {
            toStart();
        }

        @Override
        public void onPermissionsDenied(List<String> list) {
            Zx.show("拒绝权限将导致软件功能不可用,请谨慎选择");
            finish();
        }
    };


    private void toStart() {
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

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MyPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
