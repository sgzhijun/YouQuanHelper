package com.liompei.youquanhelper.network.download;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liompei
 * Time 2017/10/11 21:10
 * 1137694912@qq.com
 * remark:
 */

public class DownloadObserver<T> implements Observer<T> ,DownloadProgressListener {

    private OnDownloadListener<T> mOnDownloadListener;

    public DownloadObserver(Context context, OnDownloadListener<T> onDownloadListener) {
        mOnDownloadListener = onDownloadListener;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mOnDownloadListener.onSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        mOnDownloadListener.onNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        mOnDownloadListener.onError(e);
        mOnDownloadListener.onFinish();
    }

    @Override
    public void onComplete() {
        mOnDownloadListener.onFinish();
    }

    @Override
    public void update(long read, long count, boolean done) {

    }
}
