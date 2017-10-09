package com.liompei.youquanhelper.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.bean.CircleListBean;

/**
 * Created by Liompei
 * Time 2017/9/7 23:13
 * 1137694912@qq.com
 * remark:
 */

public class HomeAdapter extends BaseQuickAdapter<CircleListBean, BaseViewHolder> {


    public HomeAdapter() {
        super(R.layout.item_home_fragment_list);
    }


    @Override
    protected void convert(BaseViewHolder helper, CircleListBean item) {
        helper.setText(R.id.tv_content, item.getStringContent());
    }
}
