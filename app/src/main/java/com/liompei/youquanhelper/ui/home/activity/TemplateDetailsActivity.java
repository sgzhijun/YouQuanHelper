package com.liompei.youquanhelper.ui.home.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.CircleListBean;
import com.liompei.zxlog.Zx;

/**
 * Created by Liompei
 * Time 2017/10/2 23:31
 * 1137694912@qq.com
 * remark:模板详情
 */
public class TemplateDetailsActivity extends BaseActivity {


    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private Toolbar mToolbar;

    private CircleListBean mCircleListBean;
    private int spanCount;  //根据图片数量适配不同列数


    public static void start(BaseActivity activity, CircleListBean circleListBean) {
        Intent intent = new Intent();
        intent.setClass(activity, TemplateDetailsActivity.class);
        intent.putExtra("bean", circleListBean);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_template_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mCircleListBean = getIntent().getParcelableExtra("bean");
        mRefreshLayout = findView(R.id.refresh);
        mRecyclerView = findView(R.id.recycler);
    }

    @Override
    public void initData() {
        setSpanCount();
        Zx.d(spanCount);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.shape_color_cursor);
    }

    @Override
    public void onEvent() {

    }

    //设置列数
    private void setSpanCount() {
        int size = mCircleListBean.getFiles().size();
        if (size == 0) {
            toast("没有图片的模板");
        } else if (size == 1) {
            spanCount = 1;
        } else if (size == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
    }
}
