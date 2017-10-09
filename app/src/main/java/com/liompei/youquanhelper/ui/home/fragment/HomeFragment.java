package com.liompei.youquanhelper.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseFragment;
import com.liompei.youquanhelper.bean.CircleListBean;
import com.liompei.youquanhelper.ui.home.activity.PublishSoupActivity;
import com.liompei.youquanhelper.ui.home.activity.TemplateDetailsActivity;
import com.liompei.youquanhelper.ui.home.adapter.HomeAdapter;
import com.liompei.zxlog.Zx;

import java.util.List;

/**
 * Created by Liompei
 * Time 2017/9/7 20:43
 * 1137694912@qq.com
 * remark:首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private int count = 0;

    private FloatingActionButton mFab;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        getToolbar("首页", false);
        mRecyclerView = findView(R.id.recycler);
        mRefreshLayout = findView(R.id.refresh);
        mFab = findView(R.id.fab);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        mHomeAdapter = new HomeAdapter();
        mHomeAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    protected void initData() {
        mFab.setOnClickListener(this);
        mHomeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TemplateDetailsActivity.start(getBaseActivity(), mHomeAdapter.getItem(position));
            }
        });
    }

    @Override
    protected void onEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                count = 0;
                Zx.d("onRefresh: " + count);
                netGetMainList();
            }
        });
        mHomeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                count++;
                Zx.d("onLoadMore: " + count);
                netGetMainList();
            }
        }, mRecyclerView);
        toRefresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:  //发布
                PublishSoupActivity.start(getBaseActivity());
                break;
        }
    }

    //刷新
    private void toRefresh() {
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                Zx.d("刷新");
                count = 0;
                mRefreshLayout.setRefreshing(true);
                netGetMainList();
            }
        });
    }

    private void netGetMainList() {
        AVQuery<CircleListBean> avQuery = AVQuery.getQuery(CircleListBean.class);
        avQuery.setLimit(15);
        avQuery.setSkip(count * 15);
        avQuery.include("author");
        avQuery.findInBackground(new FindCallback<CircleListBean>() {
            @Override
            public void done(List<CircleListBean> list, AVException e) {
                if (e == null) {
                    Zx.d("请求结束" + list.size());
                    if (count == 0) {
                        if (list.size() == 0) {
                            toast("没有数据了");
                        } else {
                            Zx.d("刷新: 共有" + list.size() + "行数据");
                            Zx.d(list.get(0).getAuthor().getUsername() + "");
                            Zx.d("共有" + list.get(0).getFiles().size());
                            Zx.d("" + list.get(0).getFiles().get(0));
                        }
                        mRefreshLayout.setRefreshing(false);
                        mHomeAdapter.setNewData(list);
                    } else {
                        Zx.d("上拉");
                        if (list.size() == 0) {
                            toast("没有更多数据了");
                            Zx.d("没有更多数据了");
                            mHomeAdapter.loadMoreEnd();
                        } else {
                            Zx.d("加载" + count + "行数据");
                            mHomeAdapter.loadMoreComplete();
                            mHomeAdapter.addData(list);
                        }
                    }
                    if (list.size() < 15) {
                        Zx.d("list小于15,显示没有更多数据");
                        mHomeAdapter.loadMoreEnd();
                    }
                } else {
                    Zx.d("请求失败" + e.getMessage());
                    Zx.show("请求失败" + e.getMessage());
                    Zx.e("BmobException: " + count);
                    if (count == 0) {
                        mRefreshLayout.setRefreshing(false);
                    } else {
                        count--;
                        mHomeAdapter.loadMoreFail();
                    }
                }
            }
        });
    }


}
