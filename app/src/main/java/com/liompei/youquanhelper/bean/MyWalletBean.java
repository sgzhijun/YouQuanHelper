package com.liompei.youquanhelper.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Liompei
 * time : 2017/9/30 17:10
 * 1137694912@qq.com
 * remark:钱包
 */
@AVClassName("MyWallet")
public class MyWalletBean extends AVObject {

    private int balance;  //余额
    private MyUser author;  //余额所有者

    public int getBalance() {
        return getInt("balance");
    }

    public void setBalance(int balance) {
        put("balance", balance);
    }

    public MyUser getAuthor() {
        return getAVUser("author", MyUser.class);
    }

    public void setAuthor(MyUser author) {
        put("author", author);
    }
}
