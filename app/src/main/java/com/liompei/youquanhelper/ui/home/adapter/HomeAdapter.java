package com.liompei.youquanhelper.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liompei.youquanhelper.R;

/**
 * Created by Liompei
 * Time 2017/9/7 23:13
 * 1137694912@qq.com
 * remark:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder> {



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder = new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fragment_list, parent, false));
        return myHolder;
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
