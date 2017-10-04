package com.liompei.youquanhelper.ui.home.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.CircleListBean;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.ui.home.adapter.GvPictureAdapter;
import com.liompei.youquanhelper.util.MyGlideEngine;
import com.liompei.youquanhelper.util.MyPermissionUtil;
import com.liompei.zxlog.Zx;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liompei
 * time : 2017/9/25 11:05
 * 1137694912@qq.com
 * remark:发表
 */
public class PublishSoupActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_input;  //输入内容
    private TextView tv_publish;  //发表
    private TextView tv_choose_photo;  //选择图片

    private GridView mGvPictureView;
    private GvPictureAdapter mGvPictureAdapter;


    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, PublishSoupActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_soup;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("", true);
        et_input = findView(R.id.et_input);
        tv_publish = findView(R.id.tv_publish);
        tv_choose_photo = findView(R.id.tv_choose_photo);
        mGvPictureView = findView(R.id.gv_picture);
        mGvPictureAdapter = new GvPictureAdapter(mBaseActivity);
        mGvPictureView.setAdapter(mGvPictureAdapter);
    }

    @Override
    public void initData() {

        tv_publish.setOnClickListener(this);
        tv_choose_photo.setOnClickListener(this);
    }

    @Override
    public void onEvent() {
        MyPermissionUtil.requestPermission(mBaseActivity, null, new String(Manifest.permission.READ_EXTERNAL_STORAGE));
        mGvPictureView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(mBaseActivity)
                        .setTitle("提醒")
                        .setMessage("是否移除当前图片?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("移除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Zx.d(position);
                                mGvPictureAdapter.deleteData(position);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choose_photo:  //选择图片
                choosePhoto();
                break;
            case R.id.tv_publish:  //发表
                String mEtInput = et_input.getText().toString().trim();
                List<String> uriArrayList = mGvPictureAdapter.getPictureList();

                if (uriArrayList.size() == 0) {
                    toast("请选择图片");
                    return;
                }
                uploadBatch(mEtInput, uriArrayList);
//                ShareUtils.share9PicsToWXCircle(mBaseActivity, mEtInput, uriArrayList);
                break;
        }
    }


    private void upload(final String stringContent, final List<String> pathList) {
        try {
            File file = new File(pathList.get(count));
            final AVFile avFile = AVFile.withFile(file.getName(), file);
            Zx.d(file.getName());
            avFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    // 成功或失败处理...
                    if (e == null) {
                        Zx.d("上传成功" + count + "   " + avFile.getUrl());
                        avFiles.add(avFile.getUrl());
                        if (count + 1 < pathList.size()) {
                            Zx.d("需要继续上传");
                            count++;
                            upload(stringContent, pathList);
                        } else {
                            //发表
                            Zx.d("全部上传完毕,发表" + avFiles.size());
                            netPublish(stringContent, avFiles);
                        }
                    } else {
                        dismissProgress();
                        Zx.e("错误码" + e.getCode() + ",错误描述" + e.getMessage());
                        Zx.show("错误码" + e.getCode() + ",错误描述" + e.getMessage());
                        Zx.d("上传失败");
                    }
                }

            }, new ProgressCallback() {
                @Override
                public void done(Integer integer) {
                    Zx.d("第" + count + "个,进度 " + integer.toString());
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //1.上传文件
    int count = 0;
    List<String> avFiles;

    private void uploadBatch(final String stringContent, final List<String> pathList) {
        showProgress();
        Zx.d("循环上传文件直至所有文件上传成功");
        count = 0;
        avFiles = new ArrayList<>();
        avFiles.clear();
        upload(stringContent, pathList);
    }


    //2.发表内容
    private void netPublish(String stringContent, List<String> avFiles) {
        CircleListBean circleListBean = new CircleListBean();
        circleListBean.setStringContent(stringContent);
        circleListBean.setAuthor(MyUser.getMyUser());
        circleListBean.setFiles(avFiles);
        //添加一对一关联
        circleListBean.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                dismissProgress();
                if (e == null) {
                    toast("发表成功");
                    Zx.d("发表成功");
                } else {
                    Zx.d(e.getMessage());
                    Zx.show(e.getMessage());
                }
            }
        });
    }


    private static final int REQUEST_CODE_CHOOSE = 23;

    private void choosePhoto() {
//        Matisse.from(PublishSoupActivity.this)
//                .choose(MimeType.ofAll(), false)
//                .countable(true)
//                .capture(true)
//                .captureStrategy(
//                        new CaptureStrategy(true, "com.liompei.youquanhelper.fileprovider"))
//                .maxSelectable(9)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(
//                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                .thumbnailScale(0.85f)
//                .imageEngine(new MyGlideEngine())
//                .forResult(REQUEST_CODE_CHOOSE);
        int hasPicture = 9 - mGvPictureAdapter.getCount();
        if (hasPicture <= 0) {
            toast("最多选选择9张图片");
            return;
        }
        Matisse.from(PublishSoupActivity.this)
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .maxSelectable(9 - mGvPictureAdapter.getCount())
                .imageEngine(new MyGlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mGvPictureAdapter.addDataList(Matisse.obtainPathResult(data));
            mGvPictureAdapter.setListViewHeightBasedOnChildren(mGvPictureView);
        }
    }


}
