package com.liompei.youquanhelper.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liompei.youquanhelper.R;

/**
 * Created by Liompei
 * Time 2017/9/30 20:33
 * 1137694912@qq.com
 * remark:发表的内容列表
 */

public class CircleListAdapter extends RecyclerView.Adapter<CircleListAdapter.MyHolder> {


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }

}
