package com.liompei.youquanhelper.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
    private List<String> mPictureList;


    public GvPictureAdapter(Context context) {
        mContext = context;
        mPictureList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mPictureList.size();
    }

    @Override
    public String getItem(int i) {
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


    public List<String> getPictureList() {
        return mPictureList;
    }

    public void addData(String path) {
        mPictureList.add(path);
        notifyDataSetChanged();
    }

    public void addDataList(List<String> pathList) {
        Zx.d("addDataList");
        mPictureList.addAll(pathList);
        notifyDataSetChanged();
    }

    public void deleteData(int position) {
        Zx.d("当前长度:"+mPictureList.size());
        mPictureList.remove(position);
        notifyDataSetChanged();
    }








   protected class MyHolder {
        ImageView mIvPicture;
    }

}
