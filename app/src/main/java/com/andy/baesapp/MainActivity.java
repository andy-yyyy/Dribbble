package com.andy.baesapp;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameContent;
    private LinearLayout mFrameDrawer;
    private Button mCloseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ToastUtil.setContext(this);
        this.mCloseBtn = (Button) findViewById(R.id.btn);
        this.mFrameDrawer = (LinearLayout) findViewById(R.id.frame_drawer);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mFrameContent = (FrameLayout) findViewById(R.id.frame_content);
        initView();

        ShotsService.getShotById(471756, new Callback<ShotInfo>() {
            @Override
            public void onResponse(Call<ShotInfo> call, Response<ShotInfo> response) {
                ShotInfo info = response.body();
                Log.d("tag", "hello");
            }

            @Override
            public void onFailure(Call<ShotInfo> call, Throwable t) {
                Log.d("tag", "hello");
            }
        });
        ShotsService.getUserInfo(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                Log.d("tag", "hello");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("tag", "hello");
            }
        });

        ShotsService.getShotsList("animated", "week", "2016-12-06", "recent", new Callback<List<ShotInfo>>() {
            @Override
            public void onResponse(Call<List<ShotInfo>> call, Response<List<ShotInfo>> response) {
                List<ShotInfo> info = response.body();
                Log.d("tag", "hello");
            }

            @Override
            public void onFailure(Call<List<ShotInfo>> call, Throwable t) {
                Log.d("tag", "hello");
            }
        });

    }

    private void initView() {
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }
}
