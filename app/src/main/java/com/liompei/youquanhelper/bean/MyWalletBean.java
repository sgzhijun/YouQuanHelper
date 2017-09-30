package com.liompei.youquanhelper.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Liompei
 * time : 2017/9/30 17:10
 * 1137694912@qq.com
 * remark:钱包
 */

public class MyWalletBean extends BmobObject {

    private Integer balance;  //余额
    private MyUser author;  //余额所有者

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }
}
