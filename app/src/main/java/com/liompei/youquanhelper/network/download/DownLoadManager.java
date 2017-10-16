package com.liompei.youquanhelper.network.download;

import android.os.AsyncTask;

import com.liompei.youquanhelper.network.APIFunction;
import com.liompei.youquanhelper.network.config.HttpConfig;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liompei
 * Time 2017/10/10 22:03
 * 1137694912@qq.com
 * remark:
 */

public class DownLoadManager {
    private static DownLoadManager instance;


    public static DownLoadManager getInstance(DownloadProgressListener listener) {

        if (instance == null) {
            synchronized (DownLoadManager.class) {
                instance = new DownLoadManager(listener);
            }
        }
        return instance;
    }

    private Retrofit mRetrofit;
    private APIFunction mAPIFunction;

    private DownLoadManager(DownloadProgressListener listener) {
        DownloadInterceptor interceptor = new DownloadInterceptor(listener);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //手动创建一个OkHttpClient并设置超时时间
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mAPIFunction = mRetrofit.create(APIFunction.class);
    }

    public void download(final RxAppCompatActivity activity, final String pictureUrl, final OnDownloadListener<ResponseBody> downloadListener) {
        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Observable<ResponseBody> bodyCall = mAPIFunction.downloadPicture(pictureUrl);
                     /*指定线程*/
                bodyCall.subscribeOn(Schedulers.io());
                bodyCall.unsubscribeOn(Schedulers.io());
                bodyCall.compose(RxLifecycle.bindUntilEvent(activity.lifecycle(), ActivityEvent.DESTROY));
                bodyCall.observeOn(AndroidSchedulers.mainThread());
                bodyCall.subscribe(new DownloadObserver<ResponseBody>(activity, downloadListener));
                return null;
            }
        }.execute();

    }


}
