package com.liompei.youquanhelper.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Liompei
 * time : 2017/9/30 13:21
 * 1137694912@qq.com
 * remark:
 */

public class CircleListBean extends BmobObject {
    private String stringContent;  //文字内容
    private List<BmobFile> mBmobFileList;

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public List<BmobFile> getBmobFileList() {
        return mBmobFileList;
    }

    public void setBmobFileList(List<BmobFile> bmobFileList) {
        mBmobFileList = bmobFileList;
    }
}
