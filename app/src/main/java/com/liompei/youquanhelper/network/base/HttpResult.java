package com.liompei.youquanhelper.network.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Liompei
 * Time 2017/10/5 20:00
 * 1137694912@qq.com
 * remark:
 */

public class HttpResult<T> {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return code==1;
    }

    @Override
    public String toString() {

        return "code: " + code + "\nmessage: " + message + "\nresult: " + result;
    }
}
