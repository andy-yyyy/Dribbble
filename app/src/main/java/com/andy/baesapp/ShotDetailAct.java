package com.andy.baesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
    }
}
