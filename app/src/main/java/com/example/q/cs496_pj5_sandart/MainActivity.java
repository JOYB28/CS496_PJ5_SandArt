package com.example.q.cs496_pj5_sandart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  private final static String FRAGMENT_TAG = "SketchFragmentTag";
  private final static String TAG = "AppPermission";

  private final int MY_PERMISSION_REQUEST_STORAGE = 100;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // 어플 가로 고정
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    setContentView(R.layout.new_activity_main);

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

    ft.add(R.id.main, new MainFragment(), FRAGMENT_TAG).commit();

    checkPermission();

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    super.onActivityResult(requestCode, resultCode, data);

    MainFragment f = (MainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    f.onActivityResult(requestCode, resultCode, data);
  }

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
