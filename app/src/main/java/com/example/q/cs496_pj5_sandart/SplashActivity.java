package com.example.q.cs496_pj5_sandart;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.os.Handler;
import android.os.Message;

/**
 * Created by q on 2017-07-30.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 어플 가로 고정
        Log.e("qwer", "1");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_splash);
        Log.e("qwer", "2");

        Handler handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0,2000);

    }
}
