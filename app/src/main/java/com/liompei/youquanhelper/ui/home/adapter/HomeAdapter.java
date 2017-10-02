package com.liompei.youquanhelper.ui.home.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.bean.CircleListBean;
import com.liompei.youquanhelper.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liompei
 * Time 2017/9/7 23:13
 * 1137694912@qq.com
 * remark:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder> {

    private List<CircleListBean> mListBeanList;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public HomeAdapter() {
        mListBeanList = new ArrayList<>();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_fragment_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tv_content.setText(mListBeanList.get(position).getStringContent());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListBeanList.size();
    }

    public List<CircleListBean> getListBeanList() {
        return mListBeanList;
    }

    public CircleListBean getItemData(int position) {
        return mListBeanList.get(position);
    }

    //添加一条
    public void addData(CircleListBean circleListBean) {
        mListBeanList.add(circleListBean);
        notifyItemInserted(mListBeanList.size());
        notifyItemRangeChanged(mListBeanList.size(), getItemCount());
//        notifyDataSetChanged();
    }

    //添加多条
    public void addData(List<CircleListBean> circleListBeanList) {
        mListBeanList.addAll(circleListBeanList);
        notifyDataSetChanged();
    }

    //删除一条
    public void deletaData(int position) {
        mListBeanList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    //设置新数据
    public void setNewData(List<CircleListBean> circleListBeanList) {
        mListBeanList.clear();
        mListBeanList.addAll(circleListBeanList);
        notifyDataSetChanged();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView tv_content;

        public MyHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
