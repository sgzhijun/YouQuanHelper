package com.liompei.youquanhelper.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by Liompei
 * time : 2017/9/30 13:21
 * 1137694912@qq.com
 * remark:
 */
@AVClassName("CircleList")
public class CircleListBean extends AVObject {

    private String stringContent;  //文字内容
    private List<AVFile> mAVFileList;  //图片内容
    private MyUser author;  //作者  一对一

    public MyUser getAuthor() {
        return getAVUser("author", MyUser.class);
    }

    public void setAuthor(MyUser author) {
        put("author", author);
    }

    public String getStringContent() {
        return getString("stringContent");
    }

    public void setStringContent(String stringContent) {
        put("stringContent", stringContent);
    }

    public List<AVFile> getAVFileList() {
        return getAVFile("mAVFileList");
    }

    public void setAVFileList(List<AVFile> AVFileList) {
        put("mAVFileList", AVFileList);
    }
}
