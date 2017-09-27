package com.liompei.youquanhelper.ui.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.util.GlideUtils;
import com.liompei.zxlog.Zx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liompei
 * time : 2017/9/25 17:57
 * 1137694912@qq.com
 * remark:
 */

public class GvPictureAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Uri> mPictureList;


    public GvPictureAdapter(Context context) {
        mContext = context;
        mPictureList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mPictureList.size();
    }

    @Override
    public Uri getItem(int i) {
        return mPictureList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MyHolder myHolder = null;
        if (view == null) {
            myHolder = new MyHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gv_picture_list, viewGroup, false);
            myHolder.mIvPicture = view.findViewById(R.id.iv_picture);
            view.setTag(myHolder);
        } else {
            myHolder = (MyHolder) view.getTag();
        }
        GlideUtils.loadPicture(myHolder.mIvPicture, mPictureList.get(position));
        return view;
    }


    public ArrayList<Uri> getPictureList() {
        return mPictureList;
    }

    public void addData(Uri uri) {
        mPictureList.add(uri);
        notifyDataSetChanged();
    }

    public void addDataList(List<Uri> uriList) {
        Zx.d("addDataList");
        mPictureList.addAll(uriList);
        notifyDataSetChanged();

    }

    public void deleteData(int position) {
        mPictureList.remove(position);
        notifyDataSetChanged();
    }


    public void setListViewHeightBasedOnChildren(GridView gridView) {
        //获取gridView的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        //固定列宽,有多少列
        int col = 3; //gridView每四个为一列
        int totalHeight = 0;
        int totalWidth = 0;
        //i每次加4,相当于count小于4时,循环一次,计算一次item高度
        //小于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            //获取gridView的每一个item
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            //获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
            Zx.d(totalHeight);
        }
        if (listAdapter.getCount() < col) {
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0);
                totalWidth += listItem.getMeasuredWidth();
            }
        } else {
            View listItem = listAdapter.getView(0, null, gridView);
            listItem.measure(0, 0);
            totalWidth += (listItem.getMeasuredWidth() * 3);
        }

        //获取gridView的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        //设置高度
        params.height = totalHeight;
        params.width = totalWidth;
        //设置margin
//        ((ViewGroup.MarginLayoutParams) params).setMargins(40, 40, 40, 40);
        //设置参数
        gridView.setLayoutParams(params);
        notifyDataSetChanged();
    }


    class MyHolder {
        ImageView mIvPicture;
    }

}
