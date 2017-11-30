package com.andy.dribbble;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.andy.dribbble.api.ApiConstants;
import com.andy.dribbble.api.ApiUtil;
import com.andy.dribbble.api.OauthService;
import com.andy.dribbble.beans.Token;
import com.andy.dribbble.common_utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andy on 2017/1/15.
 */
public class LoginAct extends BaseActivity {

    public static final String KEY_USER_ACCESS_TOKEN = "key_access_token";
    private TextView token;

    public static Intent getIntent(Context context) {
        return new Intent(context, LoginAct.class);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        initView();
        Button login = (Button) findViewById(R.id.login);
        token = (TextView) findViewById(R.id.token);
        if (login != null) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String params = "?client_id=" + ApiConstants.CLIENT_ID
                            + "&redirect_uri=" + ApiConstants.URL_REDIRECT
                            + "&scope=" + ApiConstants.SCOPE
                            + "&state=" + ApiConstants.STATE;
                    Uri uri = Uri.parse(ApiConstants.URL_AUTHORIZE + params);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        String code = uri.getQueryParameter("code");
        OauthService.getUserToken(code, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token t = response.body();
                    // 存储token
                    if (t != null) {
                        token.setText(t.getAccessToken());
                        ApiUtil.saveTokenToSp(t.getAccessToken());
                        finish();
                    } else {
                        ToastUtil.show("get token failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                ToastUtil.show(t.getMessage());
            }
        });
        Log.d("TAG","code:"+code);
    }

    private void initView() {
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|lp.flags);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            // 返回箭头改成白色
            Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }
    }
}
