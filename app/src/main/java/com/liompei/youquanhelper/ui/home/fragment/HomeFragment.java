package com.liompei.youquanhelper.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseFragment;

/**
 * Created by Liompei
 * Time 2017/9/7 20:43
 * 1137694912@qq.com
 * remark:首页
 */

public class HomeFragment extends BaseFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getToolbar("首页", false);
        mRefreshLayout = findView(R.id.refresh);
        mRecyclerView = findView(R.id.recycler);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                netGetMainList();
            }
        });
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                netGetMainList();
            }
        });
    }



    private void netGetMainList() {
        mRefreshLayout.setRefreshing(false);
    }
}
