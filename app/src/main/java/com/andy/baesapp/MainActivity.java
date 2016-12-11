package com.andy.baesapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.andy.baesapp.api.ApiConstants;
import com.andy.baesapp.api.ShotsInterface;
import com.andy.baesapp.api.ShotsService;
import com.andy.baesapp.beans.GetShotsListResult;
import com.andy.baesapp.beans.ShotInfo;
import com.andy.baesapp.beans.User;
import com.andy.baesapp.commom_utils.ToastUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameContent;
    private LinearLayout mFrameDrawer;
    private Button mCloseBtn;
    private ShotsListFrag mShotsListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ToastUtil.setContext(this);
        this.mCloseBtn = (Button) findViewById(R.id.btn);
        this.mFrameDrawer = (LinearLayout) findViewById(R.id.frame_drawer);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mFrameContent = (FrameLayout) findViewById(R.id.frame_content);
        initData();
        initView();
    }

    private void initData() {
        mShotsListFrag = new ShotsListFrag();
    }
    private void initView() {
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_content, mShotsListFrag).commitAllowingStateLoss();
    }

}
