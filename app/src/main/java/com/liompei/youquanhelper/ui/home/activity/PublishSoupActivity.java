package com.liompei.youquanhelper.ui.home.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.ui.home.adapter.GvPictureAdapter;
import com.liompei.youquanhelper.util.MyGlideEngine;
import com.liompei.youquanhelper.util.MyPermissionUtil;
import com.liompei.zxlog.Zx;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;

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
                shareToWeChat(et_input.getText().toString(), mGvPictureAdapter.getPictureList());
                break;
        }
    }

    private void shareToWeChat(String content, ArrayList<Uri> uris) {
        try {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
            intent.putExtra("Kdescription", content);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            mBaseActivity.startActivityForResult(intent, 10);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(mBaseActivity.getApplicationContext(), "未检测到微信，请先安装微信！", Toast.LENGTH_SHORT).show();
        }
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
            Zx.d();
            mGvPictureAdapter.addDataList(Matisse.obtainResult(data));
            mGvPictureAdapter.setListViewHeightBasedOnChildren(mGvPictureView);
        }
    }


}
