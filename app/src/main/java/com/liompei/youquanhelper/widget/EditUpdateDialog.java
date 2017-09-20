package com.liompei.youquanhelper.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.util.EditTextUtils;

/**
 * Created by Liompei
 * time : 2017/9/20 14:59
 * 1137694912@qq.com
 * remark:
 */

public class EditUpdateDialog extends Dialog implements View.OnClickListener {

    private TextView mTvTitle;
    private EditText mEditText;
    private TextView mTvCancel;
    private TextView mTvSure;

    public EditUpdateDialog(@NonNull Context context) {
        this(context, 0);
    }

    public EditUpdateDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_update, null);
        mTvTitle = view.findViewById(R.id.dialog_tv_title);
        mEditText = view.findViewById(R.id.dialog_editText);
        mTvSure = view.findViewById(R.id.dialog_tv_sure);
        mTvCancel = view.findViewById(R.id.dialog_tv_cancel);
        mTvCancel.setOnClickListener(this);
        EditTextUtils.setEditTextInhibitInputSpace(mEditText);  //禁止键入空格
        setContentView(view);
    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
//        super.setTitle(title);
        mTvTitle.setText(title);
    }

    public void setEditText(@Nullable CharSequence title) {
        mEditText.setText(title);
    }

    public void setOnSureListener(@Nullable View.OnClickListener l) {
        mTvSure.setOnClickListener(l);
    }

    public String getStringContent() {
        return mEditText.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_tv_cancel:  //取消
                dismiss();
                break;
        }
    }
}
