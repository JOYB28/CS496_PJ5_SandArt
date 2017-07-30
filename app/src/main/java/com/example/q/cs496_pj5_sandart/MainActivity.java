package com.example.q.cs496_pj5_sandart;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 어플 가로 고정
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mainView);
    MyView myView = new MyView(getApplicationContext());
    setContentView(myView);
  }
}
