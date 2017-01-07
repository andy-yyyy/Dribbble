package com.andy.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by andy on 2017/1/7.
 */
public class TestAct extends BaseActivity {
    private android.support.v7.widget.RecyclerView recyclerview;
    private android.support.v4.widget.NestedScrollView scrollview;
    private android.support.v4.widget.SwipeRefreshLayout refreshlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_act);
        this.refreshlayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
//        this.scrollview = (NestedScrollView) findViewById(R.id.scroll_view);
        this.recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setNestedScrollingEnabled(false);
        TestAdapter adapter = new TestAdapter(this);
        recyclerview.setAdapter(adapter);

        adapter.updateData(TestUtil.getData(100));

    }
}
