package com.example.q.cs496_pj5_sandart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by q on 2017-07-31.
 */

public class MyView extends View {
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Paint mPaint;
    int width, height;
    ArrayList<Sand> sandArrayList = new ArrayList<>();

    public MyView(Context context){
        super(context);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Sand sand = new Sand(i, j, 0);
                sandArrayList.add(sand);
            }
        }

        mPaint = new Paint();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        createCacheBitmap(w, h);
    }

    protected void createCacheBitmap(int w, int h){
        cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
    }

    protected void onDraw(Canvas canvas){
        if(cacheBitmap != null)
            canvas.drawBitmap(cacheBitmap, 0, 0, null);
    }

    public void locate(Sand sand){
        int height = sand.getHeight();
        switch(height){
            case 0:
                sand.setHeight(1);
                mPaint.setColor(getResources().getColor(R.color.stage1));
                cacheCanvas.drawPoint(sand.getX(), sand.getY(), mPaint);
            case 1:
                double random1 = Math.random();
                if(random1 <= 0.8){
                    sand.setHeight(2);
                    mPaint.setColor(getResources().getColor(R.color.stage2));
                    cacheCanvas.drawPoint(sand.getX(), sand.getY(), mPaint);
                }
                else
                    select(sand);
            case 2:
                double random2 = Math.random();
                if(random2 <= 0.4){
                    sand.setHeight(3);
                    mPaint.setColor(getResources().getColor(R.color.stage3));
                    cacheCanvas.drawPoint(sand.getX(), sand.getY(), mPaint);
                }
                else
                    select(sand);
            case 3:
                select(sand);
        }
    }

    public void select(Sand sand){
        if(sand.getX() != 0 && sand.getX() != width && sand.getY() != 0 && sand.getY() != height){
            double random = Math.random();
            Sand sand1;
            if(random <= 0.08){
                sand1 = sandArrayList.get((sand.getY()-1)*width+(sand.getX()-1));
                locate(sand1);
            }
            else if(random <= 0.16){
                sand1 = sandArrayList.get((sand.getY()-1)*width+(sand.getX()+1));
                locate(sand1);
            }
            else if(random <= 0.24){
                sand1 = sandArrayList.get((sand.getY()+1)*width+(sand.getX()-1));
                locate(sand1);
            }
            else if(random <= 0.32){
                sand1 = sandArrayList.get((sand.getY()+1)*width+(sand.getX()+1));
                locate(sand1);
            }
            else if(random <= 0.48){
                sand1 = sandArrayList.get((sand.getY()-1)*width+sand.getX());
                locate(sand1);
            }
            else if(random <= 0.64){
                sand1 = sandArrayList.get(sand.getY()*width+(sand.getX()-1));
                locate(sand1);
            }
            else if(random <= 0.80){
                sand1 = sandArrayList.get(sand.getY()*width+(sand.getX()+1));
                locate(sand1);
            }
            else{
                sand1 = sandArrayList.get((sand.getY()+1)*width+(sand.getX()+1));
                locate(sand1);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        invalidate();
        return true;
    }
}
