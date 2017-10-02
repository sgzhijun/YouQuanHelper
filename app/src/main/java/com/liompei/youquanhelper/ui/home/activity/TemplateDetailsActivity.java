package com.liompei.youquanhelper.ui.home.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.CircleListBean;

/**
 * Created by Liompei
 * Time 2017/10/2 23:31
 * 1137694912@qq.com
 * remark:模板详情
 */
public class TemplateDetailsActivity extends BaseActivity {

    private Toolbar mToolbar;
    private XRecyclerView mXRecyclerView;

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
        mToolbar = getToolbar("模板详情", true);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.weChatPrimary));
        mCircleListBean = (CircleListBean) getIntent().getSerializableExtra("bean");
        mXRecyclerView = findView(R.id.x_recycler);
    }

    @Override
    public void initData() {
        setSpanCount();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        mXRecyclerView.setLayoutManager(gridLayoutManager);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.shape_color_cursor);
        mXRecyclerView.addItemDecoration(mXRecyclerView.new DividerItemDecoration(dividerDrawable));
        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    public void onEvent() {

    }

    //设置列数
    private void setSpanCount() {
        int size = mCircleListBean.getBmobFileList().size();
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
