package com.liompei.youquanhelper.network.download;

import android.os.AsyncTask;

import com.liompei.youquanhelper.network.APIFunction;
import com.liompei.youquanhelper.network.config.HttpConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
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
                     /*指定线程*/
                bodyCall.subscribeOn(Schedulers.io());
                bodyCall.unsubscribeOn(Schedulers.io());

                return null;
            }
        }.execute();

    }

    public void writeCaches(ResponseBody responseBody, File file, DownInfo info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getCountLength() ? responseBody.contentLength() : info.getReadLength() + responseBody
                        .contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void startDownload() {

    }

}
