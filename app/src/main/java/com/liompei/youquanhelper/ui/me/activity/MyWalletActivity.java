package com.liompei.youquanhelper.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.bean.MyWalletBean;
import com.liompei.zxlog.Zx;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

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
        BmobQuery<MyWalletBean> bmobQuery = new BmobQuery<>();
        //查询包含我的余额
        bmobQuery.addWhereEqualTo("author", MyUser.getCurrentUser(MyUser.class));
        bmobQuery.findObjects(new FindListener<MyWalletBean>() {
            @Override
            public void done(List<MyWalletBean> list, BmobException e) {
                if (e == null) {
                    Zx.d(list.size());
                    if (list.size() == 0) {
                        //插入余额表
                        Zx.d("需要创建表");
                        netSaveBalanceTable();
                    } else {
                        dismissProgress();
                        //有余额表,则显示
                        mMyWalletBean = list.get(0);
                        tv_balance.setText(mMyWalletBean.getBalance().toString());
                    }
                } else {
                    dismissProgress();
                    Zx.e(e.getErrorCode() + e.getMessage());
                    Zx.show(e.getErrorCode() + e.getMessage());
                    finish();
                }
            }
        });
    }

    //没有表时创建一个表格
    private void netSaveBalanceTable() {
        final MyWalletBean myWalletBean = new MyWalletBean();
        myWalletBean.setAuthor(MyUser.getCurrentUser(MyUser.class));
        myWalletBean.setBalance(0);
        myWalletBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                dismissProgress();
                if (e == null) {
                    myWalletBean.setObjectId(s);
                    mMyWalletBean = myWalletBean;
                    tv_balance.setText(mMyWalletBean.getBalance().toString());
                    Zx.d("添加表格成功" + s);
                } else {
                    Zx.e(e.getErrorCode() + e.getMessage());
                    Zx.show(e.getErrorCode() + e.getMessage());
                    finish();
                }

            }
        });
    }

}
