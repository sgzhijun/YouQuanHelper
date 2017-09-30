package com.liompei.youquanhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.liompei.youquanhelper.base.BaseActivity;
import com.liompei.youquanhelper.ui.dashboard.fragment.DashboardFragment;
import com.liompei.youquanhelper.ui.home.fragment.HomeFragment;
import com.liompei.youquanhelper.ui.me.activity.MyProfileActivity;
import com.liompei.youquanhelper.ui.me.fragment.MeFragment;
import com.liompei.zxlog.Zx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liompei
 * Time 2017/9/7 20:07
 * 1137694912@qq.com
 * remark:友圈助手
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentList;

    private List<String> stringList;

    private HomeFragment mHomeFragment;
    private DashboardFragment mDashboardFragment;
    private MeFragment mMeFragment;

    public static void start(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBottomNavigationView = findView(R.id.design_navigation_view);
        mViewPager = findView(R.id.main_viewPager);
        mFragmentList = new ArrayList<>();
        stringList = new ArrayList<>();

        mHomeFragment = new HomeFragment();
        mDashboardFragment = new DashboardFragment();
        mMeFragment = new MeFragment();

    }

    @Override
    public void initData() {
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mDashboardFragment);
        mFragmentList.add(mMeFragment);

        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentPagerAdapter.getCount());  //预加载数量
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnNavigationItemReselectedListener(this);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);

    }

    @Override
    public void onEvent() {

    }

    FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    };


    /**
     * 滑动监听
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //正在滑动
    }

    @Override
    public void onPageSelected(int position) {
        //选择
        switch (position) {
            case 0:  //首页
                mBottomNavigationView.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:  //关注
                mBottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                break;
            case 2:  //我的
                mBottomNavigationView.setSelectedItemId(R.id.navigation_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 选中按钮事件
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:  //首页
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_dashboard:  //关注
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_me:  //我的
                mViewPager.setCurrentItem(2);
                return true;
        }
        return false;
    }

    /**
     * 按钮重复选中事件
     */
    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:  //首页
                Zx.d("重复点击首页");
                break;
            case R.id.navigation_dashboard:  //关注
                Zx.d("重复点击关注");
                break;
            case R.id.navigation_me:  //我的
                Zx.d("重复点击我的");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MyProfileActivity.MY_PROFILE:  //修改了用户资料
                    if (mMeFragment != null) {
                        mMeFragment.updateUserData();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
