package com.liompei.youquanhelper.ui.home.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.bean.CircleListBean;

/**
 * Created by Liompei
 * Time 2017/9/30 20:33
 * 1137694912@qq.com
 * remark:发表的内容列表
 */

public class CircleListAdapter extends BaseQuickAdapter<CircleListBean, BaseViewHolder> {


    public CircleListAdapter() {
        super(R.layout.item_circle_list);
    }


    @Override
    protected void convert(BaseViewHolder helper, CircleListBean item) {
        helper.setText(R.id.tv_content, item.getStringContent());
    }

    @Nullable
    @Override
    public View getViewByPosition(int position, int viewId) {
        return super.getViewByPosition(position, viewId);
    }
}
