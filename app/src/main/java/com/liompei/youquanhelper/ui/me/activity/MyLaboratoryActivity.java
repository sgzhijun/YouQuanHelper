package com.liompei.youquanhelper.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;

/**
 * Created by Liompei
 * Time 2017/9/11 20:44
 * 1137694912@qq.com
 * remark:实验室
 */
public class MyLaboratoryActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_laboratory;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("实验室", true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MyLaboratoryActivity.class);
        activity.startActivity(intent);
    }
}
