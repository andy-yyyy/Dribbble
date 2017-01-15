package com.andy.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andy.base.api.ApiConstants;
import com.andy.base.api.OauthService;
import com.andy.base.beans.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andy on 2017/1/15.
 */
public class LoginAct extends BaseActivity {

    public static final String KEY_USER_ACCESS_TOKEN = "key_access_token";
    private TextView token;
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
                    token.setText(t.getAccessToken());
                    // 存储token
                    SharedPreferences sp = getPreferences(MODE_PRIVATE);
                    sp.edit().putString(KEY_USER_ACCESS_TOKEN, t.getAccessToken()).apply();
                    finish();
                }
                Log.d("tag", "error->"+ response.toString());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("tag", "");
            }
        });
        Log.d("TAG","code:"+code);
    }
}
