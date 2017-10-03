package com.liompei.youquanhelper.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseFragment;
import com.liompei.youquanhelper.bean.CircleListBean;
import com.liompei.youquanhelper.listener.OnItemClickListener;
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

    private XRecyclerView mXRrecycler;
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
        mXRrecycler = findView(R.id.x_recycler);
        mFab = findView(R.id.fab);
    }

    @Override
    protected void initData() {
        mHomeAdapter = new HomeAdapter();
        mFab.setOnClickListener(this);
        mHomeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                TemplateDetailsActivity.start(getBaseActivity(), mHomeAdapter.getItemData(position));
            }
        });
    }

    @Override
    protected void onEvent() {
        mXRrecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXRrecycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRrecycler.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRrecycler.setAdapter(mHomeAdapter);
        mXRrecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                count = 0;
                Zx.d("onRefresh: " + count);
                netGetMainList();
            }

            @Override
            public void onLoadMore() {
                count++;
                Zx.d("onLoadMore: " + count);
                netGetMainList();
            }
        });
        mXRrecycler.refresh();
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
        AVQuery<CircleListBean> avQuery = AVQuery.getQuery(CircleListBean.class);
        avQuery.setLimit(15);
        avQuery.setSkip(count * 15);
        avQuery.findInBackground(new FindCallback<CircleListBean>()

        {
            @Override
            public void done(List<CircleListBean> list, AVException e) {
                if (e == null) {
                    Zx.d("请求结束" + list.size());
                    if (count == 0) {
                        Zx.d("刷新: 共有" + list.size() + "行数据");
                        mXRrecycler.refreshComplete();
                        mHomeAdapter.setNewData(list);
                    } else {
                        Zx.d("上拉");
                        if (list.size() == 0) {
                            toast("没有更多数据了");
                            Zx.d("没有更多数据了");
                            mXRrecycler.setNoMore(true);
                        } else {
                            Zx.d("加载" + count + "行数据");
                            mXRrecycler.loadMoreComplete();
                            mHomeAdapter.addData(list);
                        }
                    }
                    if (list.size() < 15) {
                        Zx.d("list小于15,显示没有更多数据");
                        mXRrecycler.setNoMore(true);
                    }
                } else {
                    Zx.d("请求失败" + e.getMessage());
                    Zx.show("请求失败" + e.getMessage());
                    Zx.e("BmobException: " + count);
                    if (count == 0) {
                        mXRrecycler.refreshComplete();
                    } else {
                        count--;
                        mXRrecycler.loadMoreComplete();
                    }
                }
            }
        });
    }


}
