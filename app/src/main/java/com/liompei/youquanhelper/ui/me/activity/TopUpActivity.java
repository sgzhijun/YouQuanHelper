package com.liompei.youquanhelper.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyWalletBean;

/**
 * Created by Liompei
 * Time 2017/10/1 18:11
 * 1137694912@qq.com
 * remark:账户充值
 */
public class TopUpActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup radio_group;
    private TextView tv_recharge;  //充值

    public static void start(BaseActivity activity, MyWalletBean myWalletBean) {
        Intent intent = new Intent();
        intent.setClass(activity, TopUpActivity.class);
        intent.putExtra("bean", myWalletBean);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_top_up;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("账户充值", true);
        radio_group = findView(R.id.radio_group);
        tv_recharge = findView(R.id.tv_recharge);
    }

    @Override
    public void initData() {
        tv_recharge.setOnClickListener(this);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            }
        });
    }

    @Override
    public void onEvent() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                if (radio_group.getCheckedRadioButtonId() == R.id.rb_weChat) {
                    //微信
                    toast("微信支付");
                } else if (radio_group.getCheckedRadioButtonId() == R.id.rb_aliPay) {
                    //支付宝
                    toast("支付宝支付");
                }
                break;
        }
    }
}
