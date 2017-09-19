package com.liompei.youquanhelper.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;

/**
 * Created by Liompei
 * Time 2017/9/11 20:35
 * 1137694912@qq.com
 * remark:赏金任务
 */
public class MyTaskActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_task;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("赏金任务", true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MyTaskActivity.class);
        activity.startActivity(intent);
    }
}
