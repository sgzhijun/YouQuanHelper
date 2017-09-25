package com.liompei.youquanhelper.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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


    public GvPictureAdapter(Context context, List<String> pictureList) {
        mContext = context;
        mPictureList = pictureList;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    class MyHolder{

    }

}
