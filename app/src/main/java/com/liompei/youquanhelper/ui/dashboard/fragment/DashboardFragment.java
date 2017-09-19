package com.liompei.youquanhelper.ui.dashboard.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseFragment;

/**
 * Created by Liompei
 * Time 2017/9/7 20:52
 * 1137694912@qq.com
 * remark:关注
 */
public class DashboardFragment extends BaseFragment {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getToolbar("关注", false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onEvent() {

    }
}
