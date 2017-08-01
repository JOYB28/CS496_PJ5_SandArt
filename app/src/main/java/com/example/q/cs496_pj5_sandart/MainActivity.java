package com.example.q.cs496_pj5_sandart;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "AppPermission";
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    LinearLayout topMenu;
    ImageView thick, erase, save, trash, initialize;

    private MyView myView;

    private int thickMode = 0;
    private int eraseMode = 0;
    private SeekBar seekBarProgress, seekBarEraseProcess;
    private View popupLayout, popupEraseLayout;

    private Handler mHandler;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 어플 가로 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        checkPermission();
        // Add views to linear layout
        final LinearLayout layout = (LinearLayout) findViewById(R.id.activityMain);
        myView = new MyView(this, getApplicationContext());
        layout.addView(myView, 0);
        final LinearLayout thicknessLayout = (LinearLayout) findViewById(R.id.thickness);
        thicknessLayout.setVisibility(View.INVISIBLE);

        // Binding
        erase = (ImageView) this.findViewById(R.id.erase);
        thick = (ImageView) this.findViewById(R.id.thick);
        save = (ImageView) this.findViewById(R.id.save);
        trash = (ImageView) this.findViewById(R.id.trash);
        initialize = (ImageView) this.findViewById(R.id.initialize);
        topMenu = (LinearLayout) this.findViewById(R.id.topMenu);

        // TODO: Thick button
        thick.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.MODE = 0;
                if(thickMode == 0){
                    thicknessLayout.setVisibility(View.VISIBLE);
                    thickMode = 1;
                    seekBarProgress = (SeekBar) findViewById(R.id.seekBar);
                    seekBarProgress.setProgress(myView.THICKNESS);
                    TextView textView = (TextView) findViewById(R.id.text);
                    textView.setText("모래 굵기 조정");
                    seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            MyView.THICKNESS = i;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                }
                else{
                    thicknessLayout.setVisibility(View.INVISIBLE);
                    thickMode = 0;
                }
            }
        }));

        // TODO: Erase button
        erase.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myView.MODE == 0){
                    myView.MODE = 1;
                    /*
                    if(eraseMode == 0){
                        thicknessLayout.setVisibility(View.VISIBLE);
                        eraseMode = 1;
                        seekBarProgress = (SeekBar) findViewById(R.id.seekBar);
                        seekBarProgress.setProgress(myView.ERASENESS);
                        seekBarProgress.setProgress(myView.THICKNESS);
                        TextView textView = (TextView) findViewById(R.id.text);
                        textView.setText("지우개 굵기 조정");
                        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                MyView.ERASENESS = i;
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {

                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {

                            }
                        });
                    }
                    else{
                        thicknessLayout.setVisibility(View.INVISIBLE);
                        eraseMode = 0;
                    }
                    */
                }
                else{
                    myView.MODE = 0;
                    //thicknessLayout.setVisibility(View.INVISIBLE);
                    eraseMode = 0;
                }
            }
        }));

        // TODO: Save button
        save.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCanvasImage();
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();


            }
        }));

        // Trash button
        trash.setOnClickListener((new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                myView.trash();
            }
        }));

        // Initialize button (shuffle)
        initialize.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHandler = new Handler();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mProgressDialog = ProgressDialog.show(MainActivity.this,"",
                            "잠시 기다려 주세요.", true);
                        mHandler.postDelayed( new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                                        mProgressDialog.dismiss();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 5000);
                    }
                });
                myView.initialize();





            }
        }));

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //save image
    public void saveCanvasImage() {

        Log.d("bitmap","strm");
        myView.setDrawingCacheEnabled(true);
        Bitmap bm = myView.getDrawingCache();

        File fPath = Environment.getExternalStorageDirectory();
        File f = null;
        f = new File(fPath, "drawPic1.png");

        try {
            FileOutputStream strm = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 80, strm);
            strm.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }



    //permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    writeFile();

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d(TAG, "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Log.i(TAG, "CheckPermission : " + checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_STORAGE);

                // MY_PERMISSION_REQUEST_STORAGE is an
                // app-defined int constant

            } else {
                Log.e(TAG, "permission deny");
                writeFile();
            }
        } else {
            Log.e("asdf", "asdf");
        }
    }
    private void writeFile() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "temp.txt");
        try {
            Log.d(TAG, "create new File : " + file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
