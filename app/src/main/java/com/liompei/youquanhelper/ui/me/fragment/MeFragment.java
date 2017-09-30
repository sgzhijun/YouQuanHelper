package com.liompei.youquanhelper.ui.me.fragment;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseFragment;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.main.activity.SettingActivity;
import com.liompei.youquanhelper.ui.me.activity.MyLaboratoryActivity;
import com.liompei.youquanhelper.ui.me.activity.MyOrderActivity;
import com.liompei.youquanhelper.ui.me.activity.MyProfileActivity;
import com.liompei.youquanhelper.ui.me.activity.MyTaskActivity;
import com.liompei.youquanhelper.ui.me.activity.MyWalletActivity;
import com.liompei.youquanhelper.util.GlideUtils;
import com.liompei.zxlog.Zx;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Liompei
 * Time 2017/9/7 20:53
 * 1137694912@qq.com
 * remark:
 */

public class MeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_username;  //用户名
    private TextView tv_whats_up;  //签名
    private ImageView iv_head;  //头像
    private ImageView iv_qr_code;  //我的二维码


    private RelativeLayout rl_head;
    private TextView tv_wallet;  //钱包
    private TextView tv_order;  //订单
    private TextView tv_task;  //赚币任务
    private TextView tv_soup;  //我的鸡汤
    private TextView tv_laboratory;  //实验室
    private TextView tv_feedback;  //用户反馈
    private TextView tv_setting;  //设置

    private Uri profilePhotoUri;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getToolbar("我的", false);
        mSwipeRefreshLayout = findView(R.id.swipe);
        rl_head = findView(R.id.rl_head);
        tv_wallet = findView(R.id.tv_wallet);
        tv_order = findView(R.id.tv_order);
        tv_task = findView(R.id.tv_task);
        tv_soup = findView(R.id.tv_soup);
        tv_laboratory = findView(R.id.tv_laboratory);
        tv_feedback = findView(R.id.tv_feedback);
        tv_setting = findView(R.id.tv_setting);
        tv_username = findView(R.id.tv_username);
        tv_whats_up = findView(R.id.tv_whats_up);
        iv_head = findView(R.id.iv_head);
        iv_qr_code = findView(R.id.iv_qr_code);

        rl_head.setOnClickListener(this);
        tv_wallet.setOnClickListener(this);
        tv_order.setOnClickListener(this);
        tv_task.setOnClickListener(this);
        tv_soup.setOnClickListener(this);
        tv_laboratory.setOnClickListener(this);
        tv_feedback.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        setUserData();

    }

    @Override
    protected void onEvent() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head:  //个人信息
                Zx.d();
                MyProfileActivity.start(getBaseActivity());
                break;
            case R.id.tv_wallet:  //钱包
                MyWalletActivity.start(getBaseActivity());
                break;
            case R.id.tv_order:  //订单
                MyOrderActivity.start(getBaseActivity());
                break;
            case R.id.tv_task:  //赚币任务
                MyTaskActivity.start(getBaseActivity());
                break;
            case R.id.tv_soup:  //我的鸡汤

                break;
            case R.id.tv_laboratory:  //实验室
                MyLaboratoryActivity.start(getBaseActivity());
                break;
            case R.id.tv_feedback:  //帮助与反馈

                break;
            case R.id.tv_setting:  //设置
                SettingActivity.start(getBaseActivity());
                break;

        }
    }

    //设置资料
    private void setUserData() {
        Zx.d("设置资料");
        MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        //昵称
        if (myUser.getUsername() == null) {
            tv_username.setText("未设置昵称");
        } else {
            Zx.v(myUser.getUsername());
            tv_username.setText(myUser.getUsername());
        }
        //签名
        if (myUser.getWhatsUp() == null || "".equals(myUser.getWhatsUp())) {
            tv_whats_up.setText("签名: 未设置");
        } else {
            tv_whats_up.setText("签名: " + myUser.getWhatsUp());
        }
        //头像
        if (myUser.getProfilePhoto() == null) {
            Resources r = getActivity().getResources();
            profilePhotoUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + r.getResourcePackageName(R.drawable.ic_default_head_24dp) + "/"
                    + r.getResourceTypeName(R.drawable.ic_default_head_24dp) + "/"
                    + r.getResourceEntryName(R.drawable.ic_default_head_24dp));
        } else {
            profilePhotoUri = Uri.parse(myUser.getProfilePhoto().getFileUrl());
            Zx.v(myUser.getProfilePhoto().getFilename());
            Zx.v(myUser.getProfilePhoto().getFileUrl());
        }
        GlideUtils.loadHead(iv_head, profilePhotoUri);
    }

    //从网络获取用户最新信息
    private void fetchUserJsonInfo() {
        Zx.d("从网络获取用户最新信息");
        MyUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String result, BmobException e) {
                if (e == null) {
                    Zx.d("获取完成: " + result);
                    MyUser myUser = new Gson().fromJson(result, MyUser.class);
                    if (MyUser.getObjectByKey("username").equals(myUser.getUsername())) {
                        myUser.setUsername(null);
                    }
                    myUser.update(MyUser.getCurrentUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            if (e == null) {
                                Zx.d("更新到服务器完成");
                                setUserData();
                            } else {
                                Zx.e(e.getErrorCode() + e.getMessage());
                                Zx.show(e.getErrorCode() + e.getMessage());
                            }
                        }
                    });
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Zx.e(e.getErrorCode() + e.getMessage());
                    Zx.show("失败: "+e.getErrorCode() + e.getMessage());
                }
            }
        });
    }

    //更新资料
    public void updateUserData() {
        Zx.d("个人信息更新");
        setUserData();
    }


    @Override
    public void onRefresh() {
        fetchUserJsonInfo();
    }

}
