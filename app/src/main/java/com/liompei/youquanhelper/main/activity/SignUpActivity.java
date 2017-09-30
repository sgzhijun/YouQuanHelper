package com.liompei.youquanhelper.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.zxlog.Zx;

/**
 * Created by Liompei
 * Time 2017/9/13 2:14
 * 1137694912@qq.com
 * remark:注册
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_phone;  //Email/手机号
    private EditText et_username;  //用户名
    private EditText et_password;  //密码
    private TextView tv_sign_up;  //注册
    private TextView tv_sign_in;  //登录
    private ImageView iv_weChat;  //微信

    private ImageView iv_agree_clause;  //勾选条款
    private TextView tv_clause;  //服务条款
    private boolean isAgreeClause = true;  //是否同意条款

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, SignUpActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("注册账号", true);
        et_phone = findView(R.id.et_phone);
        et_username = findView(R.id.et_username);
        et_password = findView(R.id.et_password);
        tv_sign_up = findView(R.id.tv_sign_up);
        tv_sign_in = findView(R.id.tv_sign_in);
        iv_agree_clause = findView(R.id.iv_agree_clause);
        tv_clause = findView(R.id.tv_clause);
        iv_weChat = findView(R.id.iv_weChat);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        tv_sign_up.setOnClickListener(this);
        tv_sign_in.setOnClickListener(this);
        iv_agree_clause.setOnClickListener(this);
        tv_clause.setOnClickListener(this);
        iv_weChat.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_up:  //注册
                String username = et_phone.getText().toString();
                String nickname = et_username.getText().toString();
                String password = et_password.getText().toString();
                if ("".equals(username)) {
                    Zx.show("用户名不能为空");
                    return;
                } else if (username.length() < 6 || username.length() > 16) {

                }
                netSignUp(username, nickname, password);
                break;
            case R.id.tv_sign_in:  //登录
                onBackPressed();
                break;
            case R.id.iv_agree_clause:  //是否选择条款
                if (isAgreeClause) {
                    Zx.d("不可选");
                    tv_sign_in.setEnabled(false);
                    isAgreeClause = false;
                    iv_agree_clause.setImageResource(R.drawable.ic_check_no_24dp);
                } else {
                    Zx.d("可选");
                    tv_sign_in.setEnabled(true);
                    isAgreeClause = true;
                    iv_agree_clause.setImageResource(R.drawable.ic_check_24dp);
                }
                break;
            case R.id.tv_clause:  //服务条款内容
                break;
            case R.id.iv_weChat:  //微信
                break;
        }
    }

    private void netSignUp(String phoneNumber, String nickname, String password) {
        Zx.i(phoneNumber);
        Zx.i(nickname);
        Zx.i(password);
        MyUser myUser = new MyUser();
        myUser.setMobilePhoneNumber(phoneNumber);
//        myUser.signUpInBackground(
//
//                new SignUpCallback() {
//                    @Override
//                    public void done(AVException e) {
//                        if (e == null) {
//                            Zx.show("注册成功");
//                            Zx.d(e);
//                        } else {
//                            Zx.show(e.getCode() + e.getMessage());
//                        }
//                    }
//                });
//        App.getInstance().finishAllActivity();
//        MainActivity.start(this);
    }
}
