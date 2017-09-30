package com.liompei.youquanhelper.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseFragment;
import com.liompei.youquanhelper.bean.CircleListBean;
import com.liompei.youquanhelper.ui.home.activity.PublishSoupActivity;
import com.liompei.zxlog.Zx;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Liompei
 * Time 2017/9/7 20:43
 * 1137694912@qq.com
 * remark:首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private FloatingActionButton mFab;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getToolbar("首页", false);
        mRefreshLayout = findView(R.id.refresh);
        mRecyclerView = findView(R.id.recycler);
        mFab = findView(R.id.fab);
    }

    @Override
    protected void initData() {
        mFab.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:  //发布
                PublishSoupActivity.start(getBaseActivity());
                break;
        }
    }

    private void netGetMainList() {
        BmobQuery<CircleListBean> bmobQuery=new BmobQuery<>();
        bmobQuery.setLimit(6);  //查询6条数据
        bmobQuery.findObjects(new FindListener<CircleListBean>() {
            @Override
            public void done(List<CircleListBean> list, BmobException e) {
                mRefreshLayout.setRefreshing(false);
                // object 就是 id 为 558e20cbe4b060308e3eb36c 的 对象实例
                if (e == null) {
                    Zx.d("请求结束"+list.size());
                } else {
                    Zx.d("请求失败" + e.getErrorCode() + e.getMessage());
                    Zx.show("请求失败"+e.getErrorCode() + e.getMessage());
                }
            }
        });

    }


}
