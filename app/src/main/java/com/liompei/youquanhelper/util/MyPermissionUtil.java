package com.liompei.youquanhelper.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.liompei.zxlog.Zx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Liompei
 * Time 2017/9/7 20:08
 * 1137694912@qq.com
 * remark:请求权限工具
 */

public class MyPermissionUtil {

    public static final int MY_PERMISSIONS_REQUEST = 1001;

    //检查是否拥有单一权限
    public static boolean checkPermission(Activity activity, String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            return false;
        }
        return false;
    }

    //请求返回接口
    private static PermissionCallbacks mPermissionCallbacks;

    public interface PermissionCallbacks {
        void onPermissionsGranted(List<String> list);  //已有权限

        void onPermissionsDenied(List<String> list);  //没有权限
    }

    //请求多个权限
    public static void requestPermission(Activity activity, PermissionCallbacks permissionCallbacks, @NonNull String... permissions) {
        List<String> permissionList = new ArrayList<>();
        mPermissionCallbacks = permissionCallbacks;
        for (int i = 0; i < permissions.length; i++) {
            int permissionCheck = ContextCompat.checkSelfPermission(activity, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                permissionList.add(permissions[i]);
            }
        }
        if (permissionList.size() > 0) {
            Zx.d(Arrays.toString(permissionList.toArray(new String[permissionList.size()])));
            String[] arr = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(activity, arr, MY_PERMISSIONS_REQUEST);
        }
    }

    //请求权限返回接口
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                //如果请求被取消，结果数组为空。
                if (grantResults.length > 0) {
                    List<String> mGrantedList = new ArrayList<>();
                    List<String> mDeniedList = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {  //同意的权限
                            mGrantedList.add(permissions[i]);
                            Zx.d(permissions[i]);
                        } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {  //取消的权限
                            mDeniedList.add(permissions[i]);
                            Zx.d(permissions[i]);
                        }
                    }

                    if (mPermissionCallbacks != null) {
                        if (mGrantedList.size() > 0) {
                            mPermissionCallbacks.onPermissionsGranted(mGrantedList);
                        }
                        if (mDeniedList.size() > 0) {
                            mPermissionCallbacks.onPermissionsDenied(mDeniedList);
                        }
                    }

                } else {
                    Zx.d();

                }
                break;
        }
    }

    //打开系统设置
    public static void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }


}
