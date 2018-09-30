package com.example.jiangwensai.modulebase.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jiangwensai.modulebase.util.ObjectUtils;

import java.util.List;

public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    private List<Class<Fragment>> fragmentList;
    private List<String> mTitles;

    public BaseFragmentStateAdapter(FragmentManager fm, List<Class<Fragment>> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Class<Fragment>> fragmentList, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
        this.fragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return !ObjectUtils.isEmpty(mTitles) ? mTitles.get(position) : "";
    }

    @Override
    public Fragment getItem(int position) {
        return getFragmentInstance(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    private Fragment getFragmentInstance(int position) {
        try {
            return fragmentList.get(position).newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
