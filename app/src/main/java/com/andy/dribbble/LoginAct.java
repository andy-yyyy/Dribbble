package com.andy.dribbble;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
        Button login = (Button) findViewById(R.id.login);
        token = (TextView) findViewById(R.id.token);
        if (login != null) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String params = "?client_id=" + ApiConstants.CLIENT_ID
                            + "&redirect_uri=" + ApiConstants.REDIRECT_URI
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
}
