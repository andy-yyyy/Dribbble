package com.andy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.base.beans.ShotInfo;

/**
 * Created by andy on 2016/12/20.
 */
public class ShotDetailFrag extends BaseFragment {

    private ShotInfo mShotInfo;

    public static ShotDetailFrag getInstance(ShotInfo shotInfo) {
        ShotDetailFrag frag = new ShotDetailFrag();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ShotDetailAct.SHOT_INFO, shotInfo);
        frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_shot_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    private void initView() {

    }
    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShotInfo = (ShotInfo) bundle.getSerializable(ShotDetailAct.SHOT_INFO);
        }
        Log.d("TAG","shot info>>"+mShotInfo);
    }
}
