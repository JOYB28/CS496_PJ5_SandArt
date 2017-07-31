package com.example.q.cs496_pj5_sandart;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import butterknife.ButterKnife;
import butterknife.Bind;


public class MainFragment extends Fragment {

    // Top menu layout
    LinearLayout topMenu;
    // Top menu buttons
    ImageView thick;
    @Bind(R.id.erase)
    ImageView erase;

    ImageView save;
    @Bind(R.id.trash)
    ImageView trash;

    // Where we draw
    @Bind(R.id.drawing)

    MyView myView;


    //
    private Context mContext;

    private int seekBarThickProgress, seekBarEraseProcess;
    private View popupLayout, popupEraseLayout;

    private int mScreenWidth;
    private int mScreenHeight;
    private float density;

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
                    //showPopup(viewm MyView.ERASE);
                } else {
                    myView.setMode(myView.ERASE);
                    //setAlpha(draw, 0.4f);
                    myView.MODE = 1;
                    setAlpha(erase, 1f);
                }
            }
        });
        // TODO: Save button

        // TODO: Trash button
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

}
