package com.liompei.youquanhelper.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;

/**
 * Created by Liompei
 * Time 2017/9/12 21:53
 * 1137694912@qq.com
 * remark:登录用户
 */

@AVClassName("MyUser")
public class MyUser extends AVUser {

    //个性签名
    //性别  男(true)  女(false)  默认为女
    //用户头像
    //地址

    public String getLocation() {
        return this.getString("location");
    }

    public void setLocation(String location) {
        this.put("location", location);
    }

    public String getWhatsUp() {
        return this.getString("location");
    }

    public void setWhatsUp(String whatsUp) {
        this.put("whatsUp", whatsUp);
    }

    public Boolean getSex() {
        return this.getBoolean("sex");
    }

    public void setSex(Boolean sex) {
        this.put("sex", sex);
    }


    public AVFile getProfilePhoto() {
        return this.getAVFile("profilePhoto");
    }

    public void setProfilePhoto(AVFile profilePhoto) {
        this.put("profilePhoto", profilePhoto);
    }
}
