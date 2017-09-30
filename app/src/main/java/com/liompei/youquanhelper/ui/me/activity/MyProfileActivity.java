package com.liompei.youquanhelper.ui.me.activity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.util.GlideUtils;
import com.liompei.youquanhelper.widget.EditUpdateDialog;
import com.liompei.zxlog.Zx;
import com.vondear.rxtools.RxImageUtils;
import com.vondear.rxtools.RxPhotoUtils;
import com.vondear.rxtools.view.dialog.RxDialogChooseImage;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static com.vondear.rxtools.view.dialog.RxDialogChooseImage.LayoutType.TITLE;

/**
 * Created by Liompei
 * Time 2017/9/11 1:20
 * 1137694912@qq.com
 * remark:个人信息
 */
public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    public static final int MY_PROFILE = 3032;

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

    private boolean needChanged = false;  //是否需要返回刷新
    private Uri resultUri;

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MyProfileActivity.class);
        activity.startActivityForResult(intent, MY_PROFILE);
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
                Zx.d(MyUser.getCurrentUser(MyUser.class).getProfilePhoto().getUrl());
                RxImageUtils.showBigImageView(mBaseActivity, Uri.parse(MyUser.getCurrentUser(MyUser.class).getProfilePhoto().getUrl()));
                break;
            case R.id.ll_username:  //用户名
                final EditUpdateDialog dialogUsername = new EditUpdateDialog(mBaseActivity);
                dialogUsername.setTitle("修改用户名");
                dialogUsername.setEditText(MyUser.getCurrentUser().getUsername());
                dialogUsername.setOnSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ("".equals(dialogUsername.getStringContent())) {
                            Zx.show("请输入内容");
                            return;
                        }
                        if (MyUser.getObjectByKey("username").equals(dialogUsername.getStringContent())) {
                            dialogUsername.dismiss();
                            return;
                        }
                        dialogUsername.dismiss();
                        netUpdateUsername(dialogUsername.getStringContent());
                    }
                });
                dialogUsername.show();
                break;
            case R.id.ll_qr_code:  //二维码
                break;
            case R.id.ll_sex:  //性别
                AlertDialog.Builder sexDialog = new AlertDialog.Builder(mBaseActivity);
                sexDialog.setItems(new String[]{"女", "男"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {  //女
                            netUpdateSex(false);
                        } else if (i == 1) {  //男
                            netUpdateSex(true);
                        }
                    }
                });
                sexDialog.show();
                break;
            case R.id.ll_location:  //地址
                break;
            case R.id.ll_whats_up:  //个性签名
                EditWhatsUpActivity.startForResult(mBaseActivity);
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

    private void netUpdateProfile(final File profilePhoto) {
        showProgress();
        Zx.d("图片路径: " + profilePhoto.getAbsolutePath());
        final BmobFile bmobFile = new BmobFile(profilePhoto);
        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Zx.d("上传文件成功: " + bmobFile.getFileUrl());
                    final MyUser myUser = new MyUser();
                    myUser.setProfilePhoto(bmobFile);
                    myUser.update(MyUser.getCurrentUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            dismissProgress();
                            if (e == null) {
                                Zx.show("头像上传成功");
                                Zx.d("头像上传成功");
                                needChanged = true;
                                resultUri = Uri.fromFile(profilePhoto);
                                GlideUtils.loadHead(iv_head, myUser.getProfilePhoto().getFileUrl());
                            } else {
                                Zx.show("头像上传失败");
                                Zx.d("头像上传失败");
                            }
                        }
                    });
                } else {
                    dismissProgress();
                    Zx.show("头像上传失败" + e.getMessage());
                    Zx.e("头像上传失败" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                Zx.d("上传进度: " + value.toString());
            }
        });

    }

    private void netUpdateSex(Boolean sex) {
        showProgress();
        final MyUser myUser = new MyUser();
        myUser.setSex(sex);
        myUser.update(MyUser.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dismissProgress();
                if (e == null) {
                    //设置性别
                    if (null != myUser.getSex() && myUser.getSex()) {
                        tv_sex.setText("男");
                    } else {
                        tv_sex.setText("女");
                    }
                    needChanged = true;
                    Zx.e("修改成功");
                    Zx.show("修改成功");
                } else {
                    Zx.e(e.getErrorCode() + e.getMessage());
                    Zx.show(e.getErrorCode() + e.getMessage());
                }
            }
        });
    }

    private void netUpdateUsername(String username) {
        showProgress();
        final MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.update(MyUser.getCurrentUser().getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dismissProgress();
                if (e == null) {
                    needChanged = true;
                    tv_username.setText(myUser.getUsername());
                    Zx.show("修改成功");
                    Zx.d("修改成功");
                } else {
                    Zx.show(e.getMessage());
                    Zx.e(e.getMessage());
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
//                    resultUri = UCrop.getOutput(data);
                    netUpdateProfile(new File(RxPhotoUtils.getImageAbsolutePath(this, UCrop.getOutput(data))));
                    //从Uri中加载图片 并将其转化成File文件返回
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
                if (resultCode == RESULT_OK) {
                    Zx.d("UCrop裁剪错误之后的处理");
                    final Throwable cropError = UCrop.getError(data);
                }
                break;
            case EditWhatsUpActivity.EDIT_WHATSUP:  //设置签名
                if (resultCode == RESULT_OK) {
                    needChanged = true;
                    if (null == MyUser.getCurrentUser(MyUser.class).getWhatsUp() || "".equals(MyUser.getCurrentUser(MyUser.class).getWhatsUp())) {
                        tv_whats_up.setText("未设置");
                    } else {
                        tv_whats_up.setText(MyUser.getCurrentUser(MyUser.class).getWhatsUp());
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (needChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}
