package com.liompei.youquanhelper.ui.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.util.GlideUtils;

import java.util.ArrayList;

/**
 * Created by Liompei
 * time : 2017/9/25 17:57
 * 1137694912@qq.com
 * remark:
 */

public class GvPictureAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Uri> mPictureList;


    public GvPictureAdapter(Context context, ArrayList<Uri> pictureList) {
        mContext = context;
        mPictureList = pictureList;
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
        GlideUtils.loadPicture(myHolder.mIvPicture,mPictureList.get(position));
        return view;
    }



    class MyHolder {
        ImageView mIvPicture;
    }

}
