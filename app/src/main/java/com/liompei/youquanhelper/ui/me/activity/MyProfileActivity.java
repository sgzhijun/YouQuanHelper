package com.liompei.youquanhelper.ui.me.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.util.GlideUtils;
import com.liompei.zxlog.Zx;
import com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

/**
 * Created by Liompei
 * Time 2017/9/11 1:20
 * 1137694912@qq.com
 * remark:个人信息
 */
public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout ll_head;  //头像
    private ImageView iv_head;
    private LinearLayout ll_username;  //用户名
    private TextView tv_username;
    private LinearLayout ll_qr_code;  //二维码
    private ImageView iv_qr_code;
    private LinearLayout ll_sex;  //性别
    private TextView tv_sex;
    private LinearLayout ll_location;  //地址
    private TextView tv_location;
    private LinearLayout ll_whats_up;  //签名
    private TextView tv_whats_up;


    private Uri resultUri;

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MyProfileActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_profile;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("个人信息", true);
        ll_head = findView(R.id.ll_head);
        iv_head = findView(R.id.iv_head);
        ll_username = findView(R.id.ll_username);
        tv_username = findView(R.id.tv_username);
        ll_qr_code = findView(R.id.ll_qr_code);
        iv_qr_code = findView(R.id.iv_qr_code);
        ll_sex = findView(R.id.ll_sex);
        tv_sex = findView(R.id.tv_sex);
        ll_location = findView(R.id.ll_location);
        tv_location = findView(R.id.tv_location);
        ll_whats_up = findView(R.id.ll_whats_up);
        tv_whats_up = findView(R.id.tv_whats_up);
    }

    @Override
    public void initData() {
        setUserData();
    }

    @Override
    public void onEvent() {
        ll_head.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        ll_username.setOnClickListener(this);
        ll_qr_code.setOnClickListener(this);
        ll_sex.setOnClickListener(this);
        ll_location.setOnClickListener(this);
        ll_whats_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:  //头像
                RxDialogChooseImage dialogChooseImage = new RxDialogChooseImage(mBaseActivity, TITLE);
                dialogChooseImage.show();
                break;
            case R.id.iv_head:  //查看头像
                RxImageUtils.showBigImageView(mBaseActivity, resultUri);
                break;
            case R.id.ll_username:  //用户名
                break;
            case R.id.ll_qr_code:  //二维码
                break;
            case R.id.ll_sex:  //性别
                break;
            case R.id.ll_location:  //地址
                break;
            case R.id.ll_whats_up:  //个性签名
                break;
        }
    }

    private void setUserData() {
        Zx.d("设置资料");
        MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        if (null == myUser.getProfilePhoto()) {
            Resources r = mBaseActivity.getResources();
            resultUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + r.getResourcePackageName(R.drawable.ic_default_head_24dp) + "/"
                    + r.getResourceTypeName(R.drawable.ic_default_head_24dp) + "/"
                    + r.getResourceEntryName(R.drawable.ic_default_head_24dp));
        } else {
            String fileUrl = myUser.getProfilePhoto().getUrl();
            resultUri = Uri.parse(fileUrl);
        }
        GlideUtils.loadHead(iv_head, resultUri);

        if (null == myUser.getUsername()) {
            tv_username.setText("未设置昵称");
        } else {
            tv_username.setText(myUser.getUsername());
        }

        if (null != myUser.getSex() && myUser.getSex()) {
            tv_sex.setText("男");
        } else {
            tv_sex.setText("女");
        }

        if (null == myUser.getLocation() || "".equals(myUser.getLocation())) {
            tv_location.setText("未设置");
        } else {
            tv_location.setText(myUser.getLocation());
        }

        if (null == myUser.getWhatsUp() || "".equals(myUser.getWhatsUp())) {
            tv_whats_up.setText("未设置");
        } else {
            tv_whats_up.setText(myUser.getWhatsUp());
        }
    }

    private void netUpdateProfile(File profilePhoto) {
        showProgress();
    }

    private void netUpdateSex(Boolean sex) {
        final MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        myUser.put("sex", sex);
        myUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    MyUser.getCurrentUser().get("sex");
                } else {
                    Zx.e(e.getCode() + e.getMessage());
                    Zx.show(e.getCode() + e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RxPhotoUtils.GET_IMAGE_FROM_PHONE://选择相册之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("选择相册之后的处理");
                    GlideUtils.initUCrop(this, data.getData());  //裁剪图片
                }
                break;
            case RxPhotoUtils.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("选择照相机之后的处理");
                    Zx.d(RxPhotoUtils.imageUriFromCamera.toString());
                    GlideUtils.initUCrop(this, RxPhotoUtils.imageUriFromCamera);  //裁剪图片
                }
                break;
            case RxPhotoUtils.CROP_IMAGE://普通裁剪后的处理
                Zx.d("普通裁剪后的处理");
                Zx.d(RxPhotoUtils.cropImageUri);
                GlideUtils.loadHead(iv_head, RxPhotoUtils.cropImageUri);  //加载图片
                break;
            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("UCrop裁剪之后的处理");
                    resultUri = UCrop.getOutput(data);
                    netUpdateProfile(new File(RxPhotoUtils.getImageAbsolutePath(this, resultUri)));
//                    netUpdateProfile(new File(RxPhotoUtils.getImageAbsolutePath(this, resultUri)));
                    //从Uri中加载图片 并将其转化成File文件返回
                    Zx.d(new File(RxPhotoUtils.getImageAbsolutePath(this, resultUri)));
                    Zx.d("裁剪后图片地址" + resultUri.toString());
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                Zx.d("UCrop裁剪错误之后的处理");
                final Throwable cropError = UCrop.getError(data);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
