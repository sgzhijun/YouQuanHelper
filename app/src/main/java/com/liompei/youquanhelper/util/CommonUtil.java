package com.liompei.youquanhelper.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Liompei
 * Time 2017/10/2 12:04
 * 1137694912@qq.com
 * remark:通用工具类
 */

public class CommonUtil {

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    public static boolean checkPackageInstalled(Activity activity,String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            activity.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                activity.startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    activity.startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(activity,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }
}
