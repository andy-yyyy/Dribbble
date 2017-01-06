/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixn on 2017/1/6.
 */

public class TestListFrag extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter = new MyAdapter(getContext());

    public static TestListFrag getInstance() {
        return new TestListFrag();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_frag_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new MyAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.updateData(getData(60));
    }

    public void updateData(List<String> data) {
        mAdapter.updateData(data);
    }

    List<String> getData(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("item-->"+(i));
        }
        return list;
    }
}
