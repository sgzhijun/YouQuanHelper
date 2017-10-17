package com.liompei.youquanhelper.ui.home.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.base.RxBaseActivity;
import com.liompei.youquanhelper.bean.CircleListBean;
import com.liompei.youquanhelper.network.download.DownLoadManager;
import com.liompei.youquanhelper.network.download.DownloadProgressListener;
import com.liompei.youquanhelper.network.download.OnDownloadListener;
import com.liompei.youquanhelper.ui.home.adapter.TemplateImageAdapter;
import com.liompei.youquanhelper.widget.DownloadProgress;
import com.liompei.zxlog.Zx;
import com.zhihu.matisse.internal.ui.widget.MediaGridInset;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;

/**
 * Created by Liompei
 * Time 2017/10/2 23:31
 * 1137694912@qq.com
 * remark:模板详情
 */
public class TemplateDetailsActivity extends RxBaseActivity implements View.OnClickListener {

    private TextView tv_content;  //内容文字
    private RecyclerView mRecyclerView;
    private LinearLayout ll_collect;  //收藏
    private TextView tv_collect;  //收藏文字
    private TextView tv_rePost;  //转发到朋友圈

    private TemplateImageAdapter mTemplateImageAdapter;

    private CircleListBean mCircleListBean;
    private int spanCount;  //根据图片数量适配不同列数


    public static void start(BaseActivity activity, CircleListBean circleListBean) {
        Intent intent = new Intent();
        intent.setClass(activity, TemplateDetailsActivity.class);
        intent.putExtra("bean", circleListBean);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_template_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("模板详情", true);
        mCircleListBean = getIntent().getParcelableExtra("bean");
        mRecyclerView = findView(R.id.recycler);
        tv_content = findView(R.id.tv_content);
        ll_collect = findView(R.id.ll_collect);
        tv_collect = findView(R.id.tv_collect);
        tv_rePost = findView(R.id.tv_rePost);
        mTemplateImageAdapter = new TemplateImageAdapter();
        setSpanCount();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        int spacing = getResources().getDimensionPixelSize(R.dimen.media_grid_spacing);
        mRecyclerView.addItemDecoration(new MediaGridInset(spanCount, spacing, false));
        mTemplateImageAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    public void initData() {
        for (int i = 0; i < mCircleListBean.getFiles().size(); i++) {
            Zx.d(mCircleListBean.getFiles().get(i));
        }

        tv_content.setText(mCircleListBean.getStringContent());
        mTemplateImageAdapter.addData(mCircleListBean.getFiles());
        tv_rePost.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
    }

    @Override
    public void onEvent() {
        mDownloadProgress = new DownloadProgress(this);
        mDownloadProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (!mDisposable.isDisposed()) {
                    Zx.d("onCancel");
                    mDisposable.dispose();
                }
            }
        });


        mTemplateImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Zx.d(adapter.getData());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_collect:  //收藏

                break;
            case R.id.tv_rePost:  //转发
                for (int i = 0; i < mCircleListBean.getFiles().size(); i++) {
                    netDownload(i, mCircleListBean.getFiles().get(i));
                }
//                ShareUtils.share9PicsToWXCircle(mBaseActivity, mCircleListBean.getStringContent(), mCircleListBean.getFiles());
                break;
        }
    }

    DownloadProgress mDownloadProgress;
    Disposable mDisposable;

    private void netDownload(final int i, final String url) {
        Zx.d("开始启动netDownload");
        if (!mDownloadProgress.isShowing()) {
            Zx.e("show");
            mDownloadProgress.show();
        }
        DownLoadManager.getInstance(new DownloadProgressListener() {
            @Override
            public void update(final long read, final long count, boolean done) {
                Zx.d("当前进度: " + read + "/" + count);
            }
        }).download(this, url, new OnDownloadListener<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                Zx.d("onSubscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                Zx.i("onNext" + i);
                Zx.d("长度: " + responseBody.contentLength());
                Zx.d("类型" + responseBody.contentType());
                Zx.d("开始下载");
                mDownloadProgress.setTitle("正在下载第" + i+1 + "张");
//                        Response response = client.newCall(request).execute();
                BufferedSink sink = null;
                try {
                    String cache = Environment.getExternalStorageDirectory() + File.separator + "youCircle/";
                    File dirFirstFolder = new File(cache);
                    if (!dirFirstFolder.exists()) { //如果该文件夹不存在，则进行创建
                        Zx.d("创建文件夹");
                        dirFirstFolder.mkdirs();//创建文件夹
                    }
                    String path = Environment.getExternalStorageDirectory() + File.separator + "youCircle/" + url.substring(url.lastIndexOf("/"));
                    sink = Okio.buffer(Okio.sink(new File(path)));
                    sink.writeAll(responseBody.source());
                    sink.close();

                    if (i + 1 == mCircleListBean.getFiles().size()) {
                        toast("下载完成");
                        Zx.d("下载完成");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mDownloadProgress.isShowing()) {
                                    Zx.e("隐藏progress");
                                    mDownloadProgress.dismiss();
                                }
                            }
                        });

                    }
                    Zx.e("结束");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    //设置列数
    private void setSpanCount() {
        int size = mCircleListBean.getFiles().size();
        if (size == 0) {
            toast("没有图片的模板");
        } else if (size == 1) {
            spanCount = 2;
        } else if (size == 2) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
    }


}
