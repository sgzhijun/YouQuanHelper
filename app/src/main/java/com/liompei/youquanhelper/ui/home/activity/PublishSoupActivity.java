package com.liompei.youquanhelper.ui.home.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

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

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

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
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Zx.d(i);
                Zx.d(l);
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

    //1.上传文件
    private void uploadBatch(final String stringContent, final List<String> pathList) {
        showProgress();
        BmobFile.uploadBatch(pathList.toArray(new String[pathList.size()]), new UploadBatchListener() {
            @Override
            //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
            //2、urls-上传文件的完整url地址
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //有多少个文件上传，onSuccess方法就会执行多少次
                //通过onSuccess回调方法中的files或urls集合的大小与上传的总文件个数比较，如果一样，则表示全部文件上传成功
                if (urls.size() == pathList.size()) {
                    //全部上传完成
                    Zx.d("全部上传完成");
                    //发表
                    netPublish(stringContent, files);
                }
            }

            @Override
            //1、curIndex--表示当前第几个文件正在上传
            //2、curPercent--表示当前上传文件的进度值（百分比）
            //3、total--表示总的上传文件数
            //4、totalPercent--表示总的上传进度（百分比）
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                Zx.d("第 " + curIndex + " 个文件正在上传");
                Zx.d("当前文件上传进度 " + curPercent);
                Zx.d("总上传文件数: " + total);
                Zx.d("总上传进度: " + totalPercent);
                Zx.e("#############");
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                dismissProgress();
                Zx.e("错误码" + statuscode + ",错误描述" + errormsg);
                Zx.show("错误码" + statuscode + ",错误描述" + errormsg);

            }
        });
    }


    //2.发表内容
    private void netPublish(String stringContent, List<BmobFile> bmobFiles) {
        CircleListBean circleListBean = new CircleListBean();
        circleListBean.setStringContent(stringContent);
        circleListBean.setBmobFileList(bmobFiles);
        //添加一对一关联
        circleListBean.setAuthor(MyUser.getCurrentUser(MyUser.class));
        circleListBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                dismissProgress();
                if (e == null) {
                    toast("发表成功");
                    Zx.d(s);
                } else {
                    Zx.d(e.getErrorCode() + e.getMessage());
                    Zx.show(e.getErrorCode() + e.getMessage());
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
