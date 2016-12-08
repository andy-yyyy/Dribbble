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
import com.andy.baesapp.api.ShotsService;
import com.andy.baesapp.beans.GetShotsListResult;
import com.andy.baesapp.beans.ShotInfo;
import com.andy.baesapp.beans.User;
import com.andy.baesapp.commom_utils.ToastUtil;

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
        getShotInfo();
    }

    private void initView() {
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void getShotsList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShotsService api = retrofit.create(ShotsService.class);
        Call<GetShotsListResult> call = api.getShotsList("animated", "week", "2016-12-06", "recent");
        call.enqueue(new Callback<GetShotsListResult>() {
            @Override
            public void onResponse(Call<GetShotsListResult> call, Response<GetShotsListResult> response) {
                ToastUtil.show(response.toString());
                Log.d("tag", "response:"+response.toString());
            }

            @Override
            public void onFailure(Call<GetShotsListResult> call, Throwable t) {
                ToastUtil.show("error:"+t.toString());
            }
        });
    }

    private void getShotInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShotsService api = retrofit.create(ShotsService.class);
        Call<ShotInfo> call = api.getShotInfo(ApiConstants.CLIENT_ACCESS_TOKEN);
        call.enqueue(new Callback<ShotInfo>() {
            @Override
            public void onResponse(Call<ShotInfo> call, Response<ShotInfo> response) {
                ShotInfo info = response.body();
                ToastUtil.show(response.toString());
            }

            @Override
            public void onFailure(Call<ShotInfo> call, Throwable t) {
                ToastUtil.show(t.toString());
            }
        });
    }
    private void getUserInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShotsService api = retrofit.create(ShotsService.class);
        Call<User> call = api.getuserInfo(ApiConstants.CLIENT_ACCESS_TOKEN);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                ToastUtil.show(response.toString());
                Log.d("tag", "response:"+response.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ToastUtil.show("error:"+t.toString());
            }
        });
    }
}
