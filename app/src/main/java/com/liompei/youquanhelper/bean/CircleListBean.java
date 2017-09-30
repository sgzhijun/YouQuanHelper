package com.liompei.youquanhelper.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Liompei
 * time : 2017/9/30 13:21
 * 1137694912@qq.com
 * remark:
 */

public class CircleListBean extends BmobObject {

    private String stringContent;  //文字内容
    private List<BmobFile> mBmobFileList;  //图片内容
    private MyUser author;  //作者  一对一
    private BmobRelation likes;  //多对多关系,用于储存转发的用户

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

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
