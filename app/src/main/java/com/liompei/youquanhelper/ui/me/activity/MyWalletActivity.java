package com.liompei.youquanhelper.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.bean.MyWalletBean;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Liompei
 * Time 2017/9/11 5:42
 * 1137694912@qq.com
 * remark:钱包
 */
public class MyWalletActivity extends BaseActivity {

    private TextView tv_balance;  //余额

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MyWalletActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("钱包", true);
        tv_balance = findView(R.id.tv_balance);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:  //帮助

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void netBalance() {
        BmobQuery<MyWalletBean> bmobQuery = new BmobQuery<>();
        //查询包含我的余额
        bmobQuery.addWhereEqualTo("author", MyUser.getCurrentUser(MyUser.class));
    }

}
