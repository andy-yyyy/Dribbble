package com.andy.dribbble;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2016/12/12.
 */
public class ShotsPagerAdapter extends FragmentPagerAdapter {

    private List<ShotsListFrag> mFrags = new ArrayList<>();
    private List<String> mTabNames;

    public ShotsPagerAdapter(FragmentManager fm, List<ShotsListFrag> frags, List<String> tabNames) {
        super(fm);
        this.mFrags = frags;
        this.mTabNames = tabNames;
    }

    @Override
    public Fragment getItem(int position) {
        return mFrags.get(position);
    }

    @Override
    public int getCount() {
        return mFrags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTabNames != null && position < mTabNames.size()) {
            return mTabNames.get(position);
        }
        return super.getPageTitle(position);
    }
}
