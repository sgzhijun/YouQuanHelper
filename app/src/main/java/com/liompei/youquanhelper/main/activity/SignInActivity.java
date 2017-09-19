package com.liompei.youquanhelper.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.LogInCallback;
import com.liompei.youquanhelper.MainActivity;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.util.EditTextUtils;
import com.liompei.zxlog.Zx;


/**
 * Created by Liompei
 * Time 2017/9/12 21:42
 * 1137694912@qq.com
 * remark:登录
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_username;  //账号
    private EditText et_password;  //密码
    private TextView tv_sign_in;  //登录
    private TextView tv_phone_sign_in;  //注册
    private TextView tv_forgot;  //忘记密码
    private ImageView iv_weChat;  //微信

    private ImageView iv_agree_clause;  //勾选条款
    private TextView tv_clause;  //服务条款
    private boolean isAgreeClause = true;  //是否同意条款

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, SignInActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("    登录", false);
        et_username = findView(R.id.et_username);
        et_password = findView(R.id.et_password);
        tv_sign_in = findView(R.id.tv_sign_in);
        iv_agree_clause = findView(R.id.iv_agree_clause);
        tv_clause = findView(R.id.tv_clause);
        tv_phone_sign_in = findView(R.id.tv_phone_sign_in);
        tv_forgot = findView(R.id.tv_forgot);
        iv_weChat = findView(R.id.iv_weChat);

        //用户名不允许有空格,密码不允许有空格
        EditTextUtils.setEditTextInhibitInputSpace(et_username);
        EditTextUtils.setEditTextInhibitInputSpace(et_password);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onEvent() {
        tv_sign_in.setOnClickListener(this);
        iv_agree_clause.setOnClickListener(this);
        tv_clause.setOnClickListener(this);
        tv_phone_sign_in.setOnClickListener(this);
        tv_forgot.setOnClickListener(this);
        iv_weChat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sign_in:  //登录
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if ("".equals(username)) {
                    et_username.setError("用户名不能为空");
                    return;
                } else if (username.length() < 3 || username.length() > 26) {
                    et_username.setError("用户名长度3-26");
                    return;
                }
                if ("".equals(password)) {
                    et_password.setError("密码不能为空");
                    return;
                } else if (password.length() < 6 || password.length() > 16) {
                    et_password.setError("密码长度6-16");
                    return;
                }
                netLogin(username, password);
                break;
            case R.id.iv_agree_clause:  //勾选条款
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
            case R.id.tv_clause:  //服务条款
                break;
            case R.id.tv_phone_sign_in:  //手机验证码登录
                SignInByPhoneActivity.start(this);
                break;
            case R.id.tv_forgot:  //忘记密码
                break;
            case R.id.iv_weChat:  //微信
                break;
        }
    }

    private void netLogin(String username, String password) {
        Zx.d("username->" + username);
        Zx.d("password->" + password);
        MyUser.logInInBackground(username, password, new LogInCallback<MyUser>() {
            @Override
            public void done(MyUser myUser, AVException e) {
                if (e == null) {
                    //登录成功
                    MainActivity.start(SignInActivity.this);
                    finish();
                } else {
                    //登录失败
                    Zx.i("用户名或密码不正确" + e.getCode() + e.getMessage());
                    Zx.show("用户名或密码不正确");
                }
            }
        }, MyUser.class);

    }
}
