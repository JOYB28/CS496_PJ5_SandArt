package com.example.q.cs496_pj5_sandart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

import java.util.ArrayList;

/**
 * Created by q on 2017-07-31.
 */

public class MyView extends View implements OnTouchListener {
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Paint mPaint;
    int width, height;
    ArrayList<Sand> sandArrayList = new ArrayList<>();
    ArrayList<Sand> enabledSandArrayList = new ArrayList<>();
    public static int MODE = 0;
    int total = 0;
    private Context mContext;
    // Mode value
    public static final int DRAW = 0;
    public static final int ERASE = 1;
    // Default value
    public static final int DEFAULT_DRAW_NUMBER = 80;
    public static final int DEFAULT_DRAW_SIZE = 50;

    private float drawNumber = DEFAULT_DRAW_NUMBER;
    private float eraserSize = DEFAULT_DRAW_SIZE;

    private int mode = DRAW;

    public MyView(Context context, AttributeSet attr){
        super(context, attr);
        this.mContext = context;
        this.setOnTouchListener(this);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
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

    public void locate1(Sand sand){
        int height = sand.getHeight();
        switch(height){
            case 0:
                double random0 = Math.random();
                if(random0 <= 1 && sand.getEnabled()){
                    try{
                        for(int i = sand.getX()-1; i <= sand.getX()+1; i++){
                            for(int j = sand.getY()-1; j <= sand.getY()+1; j++){
                                Sand sand1 = sandArrayList.get(j*width+i);
                                sand1.setHeight(1);
                                mPaint.setColor(getResources().getColor(R.color.stage2));
                                cacheCanvas.drawPoint(sand1.getX(), sand1.getY(), mPaint);
                                invalidate();
                            }
                        }
                    }
                    catch(Exception e){
                        select1(sand);
                    }
                }
                else
                    select1(sand);
                break;
            case 1:
                double random1 = Math.random();
                if(random1 <= 0.8 && sand.getEnabled()){
                    try{
                        for(int i = sand.getX()-1; i <= sand.getX()+1; i++){
                            for(int j = sand.getY()-1; j <= sand.getY()+1; j++){
                                Sand sand1 = sandArrayList.get(j*width+i);
                                sand1.setHeight(2);
                                mPaint.setColor(getResources().getColor(R.color.stage4));
                                cacheCanvas.drawPoint(sand1.getX(), sand1.getY(), mPaint);
                                invalidate();
                            }
                        }
                    }
                    catch (Exception e){
                        select1(sand);
                    }
                }
                else
                    select1(sand);
                break;
            case 2:
                double random2 = Math.random();
                if(random2 <= 0.3 && sand.getEnabled()){
                    try{
                        for(int i = sand.getX()-1; i <= sand.getX()+1; i++){
                            for(int j = sand.getY()-1; j <= sand.getY()+1; j++){
                                Sand sand1 = sandArrayList.get(j*width+i);
                                sand1.setHeight(3);
                                mPaint.setColor(getResources().getColor(R.color.stage8));
                                cacheCanvas.drawPoint(sand1.getX(), sand1.getY(), mPaint);
                                invalidate();
                            }
                        }
                    }
                    catch (Exception e){
                        select1(sand);
                    }
                }
                else
                    select1(sand);
                break;
            default:
                select1(sand);
            case 3:
                select1(sand);
        }
    }

    public void select1(Sand sand){
        try{
            double random = Math.random();
            Sand sand1;
            if(random <= 0.08333){
                sand1 = sandArrayList.get((sand.getY()-2)*width+(sand.getX()-2));
                locate1(sand1);
            }
            else if(random <= 0.16667){
                sand1 = sandArrayList.get((sand.getY()-2)*width+(sand.getX()+2));
                locate1(sand1);
            }
            else if(random <= 0.25){
                sand1 = sandArrayList.get((sand.getY()+2)*width+(sand.getX()-2));
                locate1(sand1);
            }
            else if(random <= 0.33333){
                sand1 = sandArrayList.get((sand.getY()+2)*width+(sand.getX()+2));
                locate1(sand1);
            }
            else if(random <= 0.5){
                sand1 = sandArrayList.get((sand.getY()-2)*width+sand.getX());
                locate1(sand1);
            }
            else if(random <= 0.66667){
                sand1 = sandArrayList.get(sand.getY()*width+(sand.getX()-2));
                locate1(sand1);
            }
            else if(random <= 0.83333){
                sand1 = sandArrayList.get(sand.getY()*width+(sand.getX()+2));
                locate1(sand1);
            }
            else{
                sand1 = sandArrayList.get((sand.getY()+2)*width+(sand.getX()));
                locate1(sand1);
            }
        }
        catch (Exception e){
            return;
        }
    }

    public void locate(Sand sand){
        int height = sand.getHeight();
        switch(height){
            case 0:
                double random0 = Math.random();
                if(random0 <= 0.001){
                    try{
                        for(int i = sand.getX()-1; i <= sand.getX()+1; i++){
                            for(int j = sand.getY()-1; j <= sand.getY()+1; j++){
                                Sand sand1 = sandArrayList.get(j*width+i);
                                sand1.setHeight(1);
                                mPaint.setColor(getResources().getColor(R.color.stage1));
                                cacheCanvas.drawPoint(sand1.getX(), sand1.getY(), mPaint);
                                invalidate();
                            }
                        }
                    }
                    catch(Exception e){
                        select(sand);
                    }
                }
                else
                    select(sand);
                break;
            case 1:
                double random1 = Math.random();
                if(random1 <= 0.001){
                    try{
                        for(int i = sand.getX()-1; i <= sand.getX()+1; i++){
                            for(int j = sand.getY()-1; j <= sand.getY()+1; j++){
                                Sand sand1 = sandArrayList.get(j*width+i);
                                sand1.setHeight(2);
                                mPaint.setColor(getResources().getColor(R.color.stage2));
                                cacheCanvas.drawPoint(sand1.getX(), sand1.getY(), mPaint);
                                invalidate();
                            }
                        }
                    }
                    catch (Exception e){
                        select(sand);
                    }
                }
                else
                    select(sand);
                break;
            case 2:
                double random2 = Math.random();
                if(random2 <= 0.0001){
                    try{
                        for(int i = sand.getX()-1; i <= sand.getX()+1; i++){
                            for(int j = sand.getY()-1; j <= sand.getY()+1; j++){
                                Sand sand1 = sandArrayList.get(j*width+i);
                                sand1.setHeight(3);
                                mPaint.setColor(getResources().getColor(R.color.stage3));
                                cacheCanvas.drawPoint(sand1.getX(), sand1.getY(), mPaint);
                                invalidate();
                            }
                        }
                    }
                    catch (Exception e){
                        select(sand);
                    }
                }
                else
                    select(sand);
                break;
            default:
                select(sand);
        }
    }

    public void select(Sand sand){
        try{
            if(sand.getX() != 0 && sand.getX() != width-1 && sand.getY() != 0 && sand.getY() != height-1){
                double random = Math.random();
                Sand sand1;
                if(random <= 0.08333){
                    sand1 = sandArrayList.get((sand.getY()-1)*width+(sand.getX()-1));
                    locate(sand1);
                }
                else if(random <= 0.16667){
                    sand1 = sandArrayList.get((sand.getY()-1)*width+(sand.getX()+1));
                    locate(sand1);
                }
                else if(random <= 0.25){
                    sand1 = sandArrayList.get((sand.getY()+1)*width+(sand.getX()-1));
                    locate(sand1);
                }
                else if(random <= 0.33333){
                    sand1 = sandArrayList.get((sand.getY()+1)*width+(sand.getX()+1));
                    locate(sand1);
                }
                else if(random <= 0.5){
                    sand1 = sandArrayList.get((sand.getY()-1)*width+sand.getX());
                    locate(sand1);
                }
                else if(random <= 0.66667){
                    sand1 = sandArrayList.get(sand.getY()*width+(sand.getX()-1));
                    locate(sand1);
                }
                else if(random <= 0.83333){
                    sand1 = sandArrayList.get(sand.getY()*width+(sand.getX()+1));
                    locate(sand1);
                }
                else{
                    sand1 = sandArrayList.get((sand.getY()+1)*width+(sand.getX()));
                    locate(sand1);
                }
            }
            else if(sand.getX() == 0 && sand.getY() == 0){
                Sand sand1 = sandArrayList.get(1);
                Sand sand2 = sandArrayList.get(width);
                Sand sand3 = sandArrayList.get(width+1);
                double random = Math.random();
                if(random <= 0.4){
                    locate(sand1);
                }
                else if(random <= 0.8){
                    locate(sand2);
                }
                else{
                    locate(sand3);
                }
            }
            else if(sand.getX() == 0 && sand.getY() == height-1){
                Sand sand1 = sandArrayList.get((height-2)*width);
                Sand sand2 = sandArrayList.get((height-2)*width+1);
                Sand sand3 = sandArrayList.get((height-1)*width+1);
                double random = Math.random();
                if(random <= 0.4){
                    locate(sand1);
                }
                else if(random <= 0.8){
                    locate(sand2);
                }
                else{
                    locate(sand3);
                }
            }
            else if(sand.getX() == width-1 && sand.getY() == 0){
                Sand sand1 = sandArrayList.get(width-2);
                Sand sand2 = sandArrayList.get(2*width-2);
                Sand sand3 = sandArrayList.get(2*width-1);
                double random = Math.random();
                if(random <= 0.4){
                    locate(sand1);
                }
                else if(random <= 0.8){
                    locate(sand2);
                }
                else{
                    locate(sand3);
                }
            }
            else if(sand.getX() == width-1 && sand.getY() == height-1){
                Sand sand1 = sandArrayList.get((height-2)*width+(width-2));
                Sand sand2 = sandArrayList.get((height-2)*width+(width-1));
                Sand sand3 = sandArrayList.get((height-1)*width+(width-2));
                double random = Math.random();
                if(random <= 0.4){
                    locate(sand1);
                }
                else if(random <= 0.8){
                    locate(sand2);
                }
                else{
                    locate(sand3);
                }
            }
            else if(sand.getX() == 0){
                int y = sand.getY();
                Sand sand1 = sandArrayList.get((y-1)*width);
                Sand sand2 = sandArrayList.get(y*width+1);
                Sand sand3 = sandArrayList.get((y+1)*width);
                Sand sand4 = sandArrayList.get((y-1)*width+1);
                Sand sand5 = sandArrayList.get((y+1)*width+1);
                double random = Math.random();
                if(random <= 0.25){
                    locate(sand1);
                }
                else if(random <= 0.5){
                    locate(sand2);
                }
                else if(random <= 0.75){
                    locate(sand3);
                }
                else if(random <= 0.875){
                    locate(sand4);
                }
                else
                    locate(sand5);
            }
            else if(sand.getX() == width-1){
                int y = sand.getY();
                Sand sand1 = sandArrayList.get((y-1)*width+(width-1));
                Sand sand2 = sandArrayList.get(y*width+(width-2));
                Sand sand3 = sandArrayList.get((y+1)*width+(width-1));
                Sand sand4 = sandArrayList.get((y-1)*width+(width-2));
                Sand sand5 = sandArrayList.get((y+1)*width+(width-2));
                double random = Math.random();
                if(random <= 0.25){
                    locate(sand1);
                }
                else if(random <= 0.5){
                    locate(sand2);
                }
                else if(random <= 0.75){
                    locate(sand3);
                }
                else if(random <= 0.875){
                    locate(sand4);
                }
                else
                    locate(sand5);
            }
            else if(sand.getY() == 0){
                int x = sand.getX();
                Sand sand1 = sandArrayList.get(x-1);
                Sand sand2 = sandArrayList.get(x+1);
                Sand sand3 = sandArrayList.get(width+x);
                Sand sand4 = sandArrayList.get(width+(x-1));
                Sand sand5 = sandArrayList.get(width+(x+1));
                double random = Math.random();
                if(random <= 0.25){
                    locate(sand1);
                }
                else if(random <= 0.5){
                    locate(sand2);
                }
                else if(random <= 0.75){
                    locate(sand3);
                }
                else if(random <= 0.875){
                    locate(sand4);
                }
                else
                    locate(sand5);
            }
            else {
                int x = sand.getX();
                Sand sand1 = sandArrayList.get((height-2)*width+x);
                Sand sand2 = sandArrayList.get((height-1)*width+(x-1));
                Sand sand3 = sandArrayList.get((height-1)*width+(x+1));
                Sand sand4 = sandArrayList.get((height-2)*width+(x-1));
                Sand sand5 = sandArrayList.get((height-2)*width+(x+1));
                double random = Math.random();
                if(random <= 0.25){
                    locate(sand1);
                }
                else if(random <= 0.5){
                    locate(sand2);
                }
                else if(random <= 0.75){
                    locate(sand3);
                }
                else if(random <= 0.875){
                    locate(sand4);
                }
                else
                    locate(sand5);
            }
        }
        catch (Exception e){
            return;
        }
    }

    public void setMode(int mode) {
        if (mode == DRAW || mode == ERASE) {
            this.mode = mode;
        }
    }

    public int getMode() {
        return this.mode;
    }

    public boolean onTouch(View arg0, MotionEvent event){
        int action = event.getAction();
        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (action){
            case MotionEvent.ACTION_UP:
                if(MODE != 0){
                    mPaint.setStrokeWidth(0f);
                    final Sand sand1 = sandArrayList.get(Y*width+X);
                    for(int i = 0 ; i <= Math.min(100, total/100); i++){
                        locate(sand1);
                        invalidate();
                    }
                    Log.e("hello", ""+total);
                    total = 0;
                    for(int i = 0; i < enabledSandArrayList.size(); i++){
                        enabledSandArrayList.get(i).setEnabled(true);
                    }
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(MODE == 0){
                    try{
                        final Sand sand1 = sandArrayList.get(Y*width+X);
                        for(int i = 0; i <= 80; i++){
                            locate(sand1);
                            invalidate();
                        }
                    }
                    catch (Exception e){

                    }
                }
                else {
                    mPaint.setColor(Color.WHITE);
                    mPaint.setStrokeWidth(30f);
                    cacheCanvas.drawPoint(X, Y, mPaint);
                    for(int i = -15; i <= 15; i++){
                        for(int j = -15; j <= 15; j++){
                            Sand sand = sandArrayList.get((Y+i)*width+(X+j));
                            enabledSandArrayList.add(sand);
                            sand.setEnabled(false);
                            total += sand.getHeight();
                        }
                    }
                    /*
                    for(int i = 0; i < sand.getHeight(); i++){
                        sand.setEnabled(false);
                        locate1(sand);
                    }
                    */
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(MODE == 0){
                    try{
                        Sand sand2 = sandArrayList.get(Y*width+X);
                        for(int i = 0; i < 80; i++){
                            locate(sand2);
                            invalidate();
                        }
                    }
                    catch(Exception e){

                    }
                }
                else{
                    mPaint.setColor(Color.WHITE);
                    mPaint.setStrokeWidth(30f);
                    cacheCanvas.drawPoint(X, Y, mPaint);
                    for(int i = -15; i <= 15; i++){
                        for(int j = -15; j <= 15; j++){
                            Sand sand = sandArrayList.get((Y+i)*width+(X+j));
                            enabledSandArrayList.add(sand);
                            sand.setEnabled(false);
                            total += sand.getHeight();
                        }
                    }
                    /*
                    for(int i = 0; i < sand.getHeight(); i++){
                        sand.setEnabled(false);
                        locate1(sand);
                    }
                    */
                }
                break;
        }
        invalidate();
        return true;
    }

    /**
     *  Returns a new bitmap associated with drawed canvas
     *
     * @return
     */
    public Bitmap getBitmap() {
        // TODO : we need to make bitmap??? I don't know
        return cacheBitmap;
    }

    public void trash() {
        Log.e("qqqq", "clear array");
        sandArrayList.clear();
        createCacheBitmap(width, height);
        for(int j = 0; j < height; j++){
            for(int i = 0; i < width; i++){
                Sand sand = new Sand(i, j, 0);
                sandArrayList.add(sand);
            }
        }
        Log.e("qqqq", "!!!");

        invalidate();
    }

}
