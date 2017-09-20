package com.liompei.youquanhelper.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.zxlog.Zx;

/**
 * Created by Liompei
 * time : 2017/9/20 16:29
 * 1137694912@qq.com
 * remark:编辑长文字
 */
public class EditWhatsUpActivity extends BaseActivity {

    public static final int EDIT_WHATSUP = 3031;

    private EditText et_content;

    public static void startForResult(BaseActivity baseActivity) {
        Intent intent = new Intent();
        intent.setClass(baseActivity, EditWhatsUpActivity.class);
        baseActivity.startActivityForResult(intent, EDIT_WHATSUP);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_whatsup;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        getToolbar("", true);
        et_content = findView(R.id.et_content);
    }

    @Override
    public void initData() {
        MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        if (null == myUser.getWhatsUp() || "".equals(myUser.getWhatsUp())) {
            et_content.setText("");
        } else {
            et_content.setText(myUser.getWhatsUp());
            et_content.setSelection(et_content.getText().length());
        }
    }

    @Override
    public void onEvent() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:  //完成
                final String mEtContent = et_content.getText().toString().trim();
                if (mEtContent.equals(MyUser.getCurrentUser(MyUser.class).getWhatsUp())) {
                    //签名没有改变的情况下不进行操作
                    finish();
                } else {
                    //进行网络请求保存数据
                    netUpdateWhatsUp(mEtContent);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final String mEtContent = et_content.getText().toString().trim();
        if (mEtContent.equals(MyUser.getCurrentUser(MyUser.class).getWhatsUp())) {
            //签名没有改变的情况下不进行操作
            super.onBackPressed();
        } else {
            //改变的情况
            new AlertDialog.Builder(mBaseActivity)
                    .setTitle("提醒")
                    .setMessage("是否要保存修改内容")
                    .setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //进行网络请求保存数据
                            netUpdateWhatsUp(mEtContent);
                        }
                    })
                    .show();
        }
    }


    private void netUpdateWhatsUp(String whatsUp) {
        showProgress();
        final MyUser myUser = MyUser.getCurrentUser(MyUser.class);
        myUser.setWhatsUp(whatsUp);
        myUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                hideProgress();
                if (e == null) {
                    Zx.show("修改成功");
                    Zx.d("修改成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Zx.show(e.getMessage());
                    Zx.e(e.getMessage());
                }
            }
        });
    }

}
