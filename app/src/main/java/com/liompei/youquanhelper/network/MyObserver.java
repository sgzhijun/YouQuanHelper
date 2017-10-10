package com.liompei.youquanhelper.network;

import android.content.Context;
import android.content.DialogInterface;

import com.liompei.youquanhelper.network.base.HttpDialog;
import com.liompei.youquanhelper.network.base.HttpResult;
import com.liompei.zxlog.Zx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Liompei
 * Time 2017/10/6 16:47
 * 1137694912@qq.com
 * remark:
 */

public class MyObserver<T> implements Observer<HttpResult<T>> {

    private HttpCallback<T> mHttpCallback;
    private HttpDialog mDialog;
    private Disposable mDisposable;
    private boolean isLoading = false;

    //默认不显示dialog
    public MyObserver(HttpCallback<T> httpCallback) {
        mHttpCallback = httpCallback;
    }

    //传入context则显示dialog
    public MyObserver(Context context, boolean isLoading, HttpCallback<T> httpCallback) {
        mHttpCallback = httpCallback;
        this.isLoading = isLoading;
        if (null != context || isLoading) {
            mDialog = new HttpDialog(context);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(true);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    if (!mDisposable.isDisposed()) {
                        mDisposable.dispose();  //释放资源
                    }
                }
            });
        }


    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        mHttpCallback.onStart(d);
        if (isLoading && null != mDialog) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
    }

    @Override
    public void onNext(HttpResult<T> value) {
        mHttpCallback.onNext(value);
    }


    @Override
    public void onError(Throwable e) {
        Zx.show(e.getMessage());
        if (null != mDialog) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
        mHttpCallback.onError(e);
        mHttpCallback.onFinish();
    }

    @Override
    public void onComplete() {
        if (null != mDialog) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
        mHttpCallback.onFinish();
    }
}
