package com.liompei.youquanhelper.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.liompei.youquanhelper.R;

/**
 * Created by Liompei
 * time : 2017/10/16 16:48
 * 1137694912@qq.com
 * remark:
 */

public class DownloadProgress extends Dialog {

    private TextView message;

    public DownloadProgress(@NonNull Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.http_dialog, null);
        this.setContentView(contentView);

        message = (TextView) contentView.findViewById(R.id.message);
        message.setText("正在下载");
        message.setTextColor(Color.WHITE);
        message.setTextSize(16);

        setCancelable(true);
        setCanceledOnTouchOutside(false);

    }

    @Override
    public void setTitle(@Nullable CharSequence title) {
        message.setText(title);
    }

    @Override
    public void show() {
        super.show();
    }
}
