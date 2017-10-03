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
    private String location;
    private String whatsUp;
    private boolean sex;
    private AVFile profilePhoto;


    public String getLocation() {
        return getString("location");
    }

    public void setLocation(String location) {
        put("location", location);
    }

    public String getWhatsUp() {
        return getString("whatsUp");
    }

    public void setWhatsUp(String whatsUp) {
        put("whatsUp", whatsUp);
    }

    public Boolean getSex() {
        return getBoolean("sex");
    }

    public void setSex(boolean sex) {
        put("sex", sex);
    }

    public AVFile getProfilePhoto() {
        return getAVFile("profilePhoto");
    }

    public void setProfilePhoto(AVFile profilePhoto) {
        put("profilePhoto", profilePhoto);
    }

    public static MyUser getMyUser() {
        return getCurrentUser(MyUser.class);
    }
}
