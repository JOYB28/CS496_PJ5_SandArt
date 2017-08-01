package com.example.q.cs496_pj5_sandart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.q.cs496_pj5_sandart.view.MyView;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment extends Fragment {

  // Top menu layout
  LinearLayout topMenu;
  // Top menu buttons
  @Bind(R.id.thick)
      ImageView thick;
  @Bind(R.id.erase)
      ImageView erase;
  @Bind(R.id.save)
      ImageView save;
  @Bind(R.id.trash)
      ImageView trash;

  // Where we draw
  @Bind(R.id.drawing)
  MyView myView;


  //
  private Context mContext;

  private int seekBarThickProgress, seekBarEraseProgress;
  private View popupThickLayout, popupEraseLayout;
  private ImageView thickImageView, eraseImageView;
  private MaterialDialog dialog;
  private Bitmap bitmap;

  private int mScreenWidth;
  private int mScreenHeight;
  private float density;
  private int size;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setHasOptionsMenu(true);
    setRetainInstance(false);
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_main, container, false);
    ButterKnife.bind(this, view);

    density = getResources().getDisplayMetrics().density;

    DisplayMetrics dm = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

    mScreenWidth = dm.widthPixels;
    mScreenHeight = dm.heightPixels;

    FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) myView.getLayoutParams();
    p.width = mScreenWidth;
    p.height = mScreenHeight;
    myView.setLayoutParams(p);

    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    //myView.setOnDr

    // TODO: Thick button

    // TODO: Erase button
    erase.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(myView.getMode() == MyView.ERASE) {
          showPopup(view, MyView.ERASE);
        } else {
          myView.setMode(myView.ERASE);
          setAlpha(thick, 0.4f);
          setAlpha(erase, 1f);
        }
      }
    });
    // TODO: Save button

    // Trash button
    trash.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        askForTrash();
      }

      private void askForTrash() {
        new MaterialDialog.Builder(getActivity())
            .content("모래를 지웁니다")
            .positiveText("확인")
            .callback(new MaterialDialog.ButtonCallback() {
              @Override
              public void onPositive(MaterialDialog dialog) {
                myView.trash();
              }
            }).build().show();
      }
    });
    // TODO: Shake button


    // Inflate the popup_layout.xml for thick
    LayoutInflater inflaterThick = (LayoutInflater) getActivity().getSystemService(ActionBarActivity
        .LAYOUT_INFLATER_SERVICE);
    popupThickLayout = inflaterThick.inflate(R.layout.popup_erase, null);
    // And one for erase
    LayoutInflater inflaterErase = (LayoutInflater) getActivity().getSystemService(ActionBarActivity
        .LAYOUT_INFLATER_SERVICE);
    popupEraseLayout = inflaterErase.inflate(R.layout.popup_erase, null);

  }

  void setAlpha(View v, float alpha) {
    if (Build.VERSION.SDK_INT < 11) {
      AlphaAnimation animation = new AlphaAnimation(1.0F, alpha);
      animation.setFillAfter(true);
      v.startAnimation(animation);
    } else {
      v.setAlpha(alpha);
    }

  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getActivity().onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    //ButterKnife.unbind(this);
  }

  // Displays the popup
  private void showPopup(View anchor, final int eraseOrThick) {
    boolean isErasing = eraseOrThick == MyView.ERASE;

    DisplayMetrics metrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

    // Creating the PopupWindow
    PopupWindow popup = new PopupWindow(getActivity());
    popup.setContentView(popupEraseLayout);
    popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
    popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    popup.setFocusable(true);
    popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
      @Override
      public void onDismiss() {

      }
    });

    // Clear the default translucent background
    popup.setBackgroundDrawable(new BitmapDrawable());

    // Displaying the popup at the specified location, + offsets (transformed
    // dp to pixel to support multiple screen sizes)
    popup.showAsDropDown(anchor);

    SeekBar mSeekBar;
    mSeekBar = (SeekBar) (isErasing? popupEraseLayout.findViewById(R.id.eraseSeekbar) :
        popupEraseLayout.findViewById(R.id.eraseSeekbar));
    mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        // When the seekbar is moved a new size is calculated and the new shape
        // is positioned centrally into the ImageView
        setSeekbarProgress(i, eraseOrThick);
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {

      }
    });
    int progress = isErasing ? seekBarEraseProgress : seekBarThickProgress;
    mSeekBar.setProgress(progress);
  }

  protected void setSeekbarProgress(int progress, int eraseOrThick) {
    int calcProgress = progress > 1 ? progress : 1;

    int newSize = Math.round((size / 100f) * calcProgress);
    int offset = Math.round((size - newSize) / 2);


    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(newSize, newSize);
    lp.setMargins(offset, offset, offset, offset);
    if (eraseOrThick == MyView.THICK) {
      thickImageView.setLayoutParams(lp);
      seekBarThickProgress = progress;
    } else {
      eraseImageView.setLayoutParams(lp);
      seekBarEraseProgress = progress;
    }

    myView.setSize(newSize, eraseOrThick);
  }

}
