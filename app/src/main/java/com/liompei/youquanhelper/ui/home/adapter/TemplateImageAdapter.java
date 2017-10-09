package com.liompei.youquanhelper.ui.home.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.util.GlideUtils;

/**
 * Created by Liompei
 * Time 2017/10/9 20:07
 * 1137694912@qq.com
 * remark:模板详情中的图片
 */

public class TemplateImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TemplateImageAdapter() {
        super(R.layout.item_template_details_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtils.loadPicture((ImageView) helper.getView(R.id.iv_picture), item);
    }
}
