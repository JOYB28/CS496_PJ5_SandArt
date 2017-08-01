package com.example.q.cs496_pj5_sandart;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // Top menu layout
    LinearLayout topMenu;
    // Top menu buttons
    ImageView thick;
    ImageView erase;
    ImageView undo;
    ImageView redo;
    ImageView save;
    ImageView trash;


    private int seekBarThickProgress, seekBarEraseProcess;
    private View popupLayout, popupEraseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 어플 가로 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // Add views to linear layout
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activityMain);
        final MyView myView = new MyView(this, getApplicationContext());
        layout.addView(myView, 0);

        // Binding
        erase = (ImageView) this.findViewById(R.id.erase);
        thick = (ImageView) this.findViewById(R.id.thick);
        trash = (ImageView) this.findViewById(R.id.trash);
        topMenu = (LinearLayout) this.findViewById(R.id.topMenu);

        erase.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.MODE = 1;
            }
        }));

        thick.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hello", "thick");
                myView.shuffle();
            }
        }));

        trash.setOnClickListener((new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                myView.trash();
                Log.e("hello", "trash");
            }
        }));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
