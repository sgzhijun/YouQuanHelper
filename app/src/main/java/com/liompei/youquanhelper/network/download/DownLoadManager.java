package com.liompei.youquanhelper.network.download;

import android.os.AsyncTask;

import com.liompei.youquanhelper.network.APIFunction;
import com.liompei.youquanhelper.network.config.HttpConfig;

import io.reactivex.Observable;
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


    public static DownLoadManager getInstance() {

        if (instance == null) {
            synchronized (DownLoadManager.class) {
                instance = new DownLoadManager();
            }
        }
        return instance;
    }

    private Retrofit mRetrofit;
    private APIFunction mAPIFunction;

    private DownLoadManager() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mAPIFunction = mRetrofit.create(APIFunction.class);
    }

    public void download(final String pictureUrl) {
        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Observable<ResponseBody> bodyCall = mAPIFunction.downloadPicture(pictureUrl);

                return null;
            }
        }.execute();

    }

    public void startDownload(){

    }

}
