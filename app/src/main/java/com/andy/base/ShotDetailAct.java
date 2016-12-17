package com.andy.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotDetailAct extends BaseActivity {

    public static Intent getIntent(Context context) {
        return new Intent(context, ShotDetailAct.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shot_detail);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        TestAdapter adapter = new TestAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.updateData(getData());
//        adapter.setFooterView(LayoutInflater.from(this).inflate(R.layout.item_test, null));
//        adapter.setHeaderView(LayoutInflater.from(this).inflate(R.layout.item_test, null));
    }

    List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("item"+(i+1));
        }
        return data;
    }
}
