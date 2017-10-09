package com.liompei.youquanhelper.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVSMS;
import com.avos.avoscloud.AVSMSOption;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.liompei.youquanhelper.App;
import com.liompei.youquanhelper.MainActivity;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.zxlog.Zx;

/**
 * Created by Liompei
 * Time 2017/9/13 21:25
 * 1137694912@qq.com
 * remark:手机号验证码一键登录
 */
public class SignInByPhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_phone;  //手机号
    private EditText et_sms_code;  //验证码
    private TextView tv_get_code;  //获取验证码
    private ImageView iv_agree_clause;  //同意条款
    private TextView tv_clause;  //条款
    private TextView tv_sign_up;  //一键登录

    private boolean isAgreeClause = true;  //是否同意条款


    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, SignInByPhoneActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in_by_phone;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("    手机号一键登录", true);
        et_phone = findView(R.id.et_phone);
        et_sms_code = findView(R.id.et_sms_code);
        tv_get_code = findView(R.id.tv_get_code);
        iv_agree_clause = findView(R.id.iv_agree_clause);
        tv_clause = findView(R.id.tv_clause);
        tv_sign_up = findView(R.id.tv_sign_up);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        tv_get_code.setOnClickListener(this);
        iv_agree_clause.setOnClickListener(this);
        tv_clause.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        countDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:  //获取验证码
                if (et_phone.getText().length() != 11) {
                    toast("手机号格式不正确");
                    return;
                }
                tv_get_code.setEnabled(false);
                netGetCode(et_phone.getText().toString());
                break;
            case R.id.iv_agree_clause:  //同意条款
                if (isAgreeClause) {
                    Zx.d("不可选");
                    tv_sign_up.setEnabled(false);
                    isAgreeClause = false;
                    iv_agree_clause.setImageResource(R.drawable.ic_check_no_24dp);
                } else {
                    Zx.d("可选");
                    tv_sign_up.setEnabled(true);
                    isAgreeClause = true;
                    iv_agree_clause.setImageResource(R.drawable.ic_check_24dp);
                }
                break;
            case R.id.tv_clause:  //条款内容
                break;
            case R.id.tv_sign_up:  //一键登录
                String phoneNumber = et_phone.getText().toString();
                String smsCode = et_sms_code.getText().toString();
                if (phoneNumber.length() != 11) {
                    toast("手机号格式不正确");
                    return;
                }
                if (smsCode.length() != 6) {
                    toast("验证码不正确");
                    return;
                }
                netSignUp(phoneNumber, smsCode);
                break;
        }
    }

    private CountDownTimer countDownTimer = new CountDownTimer(120000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            if (tv_get_code != null)
                tv_get_code.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            if (tv_get_code != null) {
                tv_get_code.setText("重新发送");
                tv_get_code.setEnabled(true);
            }
        }
    };


    private void netGetCode(String phoneNumber) {
        //发送验证码
        AVSMSOption avsmsOption = new AVSMSOption();
        avsmsOption.setSignatureName("Normal");  //签名名称
        avsmsOption.setTtl(5);  //5分钟有效时间
//        avsmsOption.setTemplateName("Normal");  //模板名称
//        avsmsOption.setApplicationName("应用名称");

//        avsmsOption.setOperation("某种操作");
        AVSMS.requestSMSCodeInBackground(phoneNumber, avsmsOption, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {//验证码发送成功
                    Zx.show("验证码发送成功");
                    Zx.i("验证码发送成功");
                    tv_get_code.setEnabled(false);
                    countDownTimer.start();
                } else {
                    tv_get_code.setEnabled(true);
                    Zx.d(e.getMessage());
                    Zx.show(e.getMessage());
                }
            }
        });
    }

    private void netSignUp(String phoneNumber, String smsCode) {
        showProgress();
        MyUser.signUpOrLoginByMobilePhoneInBackground(phoneNumber, smsCode, MyUser.class, new LogInCallback<MyUser>() {
            @Override
            public void done(MyUser myUser, AVException e) {
                dismissProgress();
                if (e == null) {
                    Zx.i("用户登陆成功");
                    App.getInstance().finishAllActivity();
                    MainActivity.start(SignInByPhoneActivity.this);
                } else {
                    Zx.d(e.getMessage());
                    Zx.show(e.getMessage());
                }
            }
        });
    }
}
