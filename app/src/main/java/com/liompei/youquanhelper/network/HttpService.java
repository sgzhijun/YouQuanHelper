package com.liompei.youquanhelper.network;


import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.network.base.HttpResult;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Liompei
 * Time 2017/10/5 18:59
 * 1137694912@qq.com
 * remark:
 */

public interface HttpService {

    //模板
    @FormUrlEncoded
    @POST("aaaaaaaaaaaaa")
    Observable<HttpResult<String>> aaaaa(@Field("aaaaaa") String aaaaaa,
                                         @Field("aaaaaaa") String aaaaaaa);

    //登录
    @FormUrlEncoded
    @POST("login")
    Observable<HttpResult<MyUser>> login(@Field("telNumber") String telNumber, @Field("password") String password);


    @Streaming
    @GET
    Observable<ResponseBody> downloadPicture(@Url String pictureUrl);


}
