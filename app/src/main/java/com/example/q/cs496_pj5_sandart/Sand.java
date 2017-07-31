package com.example.q.cs496_pj5_sandart;

/**
 * Created by q on 2017-07-31.
 */

public class Sand {
    private int x, y, height;
    private boolean enabled;

    public Sand(){
        this.enabled = true;
    }

    public Sand(int x, int y, int height){
        this.x = x;
        this.y = y;
        this.height = height;
        this.enabled = true;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getHeight(){
        return this.height;
    }

    public boolean getEnabled(){
        return this.enabled;
    }

}
