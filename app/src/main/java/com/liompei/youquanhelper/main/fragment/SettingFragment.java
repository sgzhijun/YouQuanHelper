package com.liompei.youquanhelper.main.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.liompei.youquanhelper.App;
import com.liompei.youquanhelper.R;
import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.bean.MyUser;
import com.liompei.youquanhelper.main.activity.SplashActivity;

/**
 * Created by Liompei
 * Time 2017/9/14 0:15
 * 1137694912@qq.com
 * remark:
 */

public class SettingFragment extends PreferenceFragment {

    private Preference app_signOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preferences);
        app_signOut = findPreference("app_signOut");
        app_signOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                MyUser.logOut();//清除缓存用户对象
                App.getInstance().finishAllActivity();
                SplashActivity.start((BaseActivity) getActivity());
                return false;
            }
        });
    }
}
