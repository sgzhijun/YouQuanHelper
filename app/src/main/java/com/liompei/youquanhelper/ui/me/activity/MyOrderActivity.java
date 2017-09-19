package com.liompei.youquanhelper.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;

/**
 * Created by Liompei
 * Time 2017/9/11 20:19
 * 1137694912@qq.com
 * remark:我的订单
 */
public class MyOrderActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("订单",true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MyOrderActivity.class);
        activity.startActivity(intent);
    }
}
