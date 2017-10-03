package com.liompei.youquanhelper.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.bean.MyWalletBean;
import com.liompei.zxlog.Zx;

/**
 * Created by Liompei
 * Time 2017/9/11 5:42
 * 1137694912@qq.com
 * remark:钱包
 */
public class MyWalletActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_balance;  //余额
    private TextView tv_topUp;  //充值
    private TextView tv_withdraw;  //提现
    private MyWalletBean mMyWalletBean;

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
        tv_topUp = findView(R.id.tv_topUp);
        tv_withdraw = findView(R.id.tv_withdraw);
    }

    @Override
    public void initData() {
        tv_topUp.setOnClickListener(this);
        tv_withdraw.setOnClickListener(this);
    }

    @Override
    public void onEvent() {
        netBalance();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_topUp:  //充值
                TopUpActivity.start(mBaseActivity, mMyWalletBean);
                break;
            case R.id.tv_withdraw:  //提现

                break;
        }
    }

    private void netBalance() {
        showProgress();
        AVQuery<MyWalletBean> beanAVQuery = AVQuery.getQuery(MyWalletBean.class);
        beanAVQuery.whereEqualTo("author", MyUser.getMyUser());
        beanAVQuery.getFirstInBackground(new GetCallback<MyWalletBean>() {
            @Override
            public void done(MyWalletBean myWalletBean, AVException e) {
                if (e == null) {
                    if (myWalletBean == null) {
                        //插入余额表
                        Zx.d("需要创建表");
//                        netSaveBalanceTable();
                    } else {
                        dismissProgress();
                        //有余额表,则显示
                        mMyWalletBean = myWalletBean;
                        tv_balance.setText(String.valueOf(mMyWalletBean.getBalance()));
                    }
                } else {
                    dismissProgress();
                    Zx.e(e.getMessage());
                    Zx.show(e.getMessage());
                    finish();
                }
            }
        });
    }

    //没有表时创建一个表格
    private void netSaveBalanceTable() {
        final MyWalletBean myWalletBean = new MyWalletBean();
        myWalletBean.setAuthor(MyUser.getMyUser());
        myWalletBean.setBalance(0);
        myWalletBean.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                dismissProgress();
                if (e == null) {
                    mMyWalletBean = myWalletBean;
                    tv_balance.setText(String.valueOf(mMyWalletBean.getBalance()));
                    Zx.d("添加表格成功" + myWalletBean.getObjectId());
                } else {
                    Zx.e(e.getMessage());
                    Zx.show(e.getMessage());
                    finish();
                }
            }
        });
    }

}
