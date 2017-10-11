package com.liompei.youquanhelper.network.download;

import android.os.AsyncTask;
import android.os.Environment;

import com.liompei.youquanhelper.network.APIFunction;
import com.liompei.youquanhelper.network.config.HttpConfig;
import com.liompei.zxlog.Zx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

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
                Call<ResponseBody> bodyCall = mAPIFunction.downloadPicture(pictureUrl);
                bodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Zx.d("onResponse");
                        Zx.d("下载成功");
                        writeResponseBodyToDisk(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });


                return null;
            }
        }.execute();

    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File dirFirstFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "youQuan/downloadPicture/");
            if (!dirFirstFolder.exists()) {
                dirFirstFolder.mkdirs();//创建文件夹
            }
            File futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + File.separator + "youQuan/downloadPicture/aaaaa.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Zx.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
