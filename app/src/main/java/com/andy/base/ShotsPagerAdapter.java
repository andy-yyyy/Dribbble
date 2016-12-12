package com.andy.base;

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
    public ShotsPagerAdapter(FragmentManager fm, List<ShotsListFrag> frags) {
        super(fm);
        this.mFrags = frags;
    }

    @Override
    public Fragment getItem(int position) {
        return mFrags.get(position);
    }

    @Override
    public int getCount() {
        return mFrags.size();
    }
}
