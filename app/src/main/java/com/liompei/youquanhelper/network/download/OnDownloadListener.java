package com.liompei.youquanhelper.network.download;

import io.reactivex.disposables.Disposable;

/**
 * Created by Liompei
 * Time 2017/10/11 21:12
 * 1137694912@qq.com
 * remark:
 */

public interface OnDownloadListener<T> {

    void onSubscribe(Disposable d);

    void onNext(T t);

    void onError(Throwable e);

    void onFinish();

    //开始下载
    void onStart();

    //停止下载
    void onStop();


}
