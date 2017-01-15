package com.andy.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andy.base.api.ApiConstants;

/**
 * Created by andy on 2017/1/15.
 */
public class LoginAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        Button login = (Button) findViewById(R.id.login);
        if (login != null) {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String params = "?client_id=" + ApiConstants.CLIENT_ID
                            + "&redirect_uri=" + ApiConstants.REDIRECT_URI
                            + "&scope=" + ApiConstants.SCOPE
                            + "&state=" + ApiConstants.STATE;
                    Uri uri = Uri.parse(ApiConstants.AUTHORIZE_URL + params);
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
        Log.d("TAG","code:"+code);
    }
}
