package com.liompei.youquanhelper.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Liompei
 * Time 2017/9/12 21:53
 * 1137694912@qq.com
 * remark:登录用户
 */

public class MyUser extends BmobUser {

    //个性签名
    //性别  男(true)  女(false)  默认为女
    //用户头像
    //地址
    private String location;
    private String whatsUp;
    private Boolean sex;
    private BmobFile profilePhoto;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWhatsUp() {
        return whatsUp;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public BmobFile getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(BmobFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
