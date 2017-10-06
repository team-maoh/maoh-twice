package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.maohx2.ina.MySprite;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

abstract public class MapObject {

    double x, y;
    SurfaceHolder holder;
    Bitmap draw_object;
    int icon_width, icon_height;
    Paint paint = new Paint();
    boolean exists;//自身がマップ上に存在しているかどうか
    int id;

    MySprite sprite_object;
    GL10 gl;

    public void init(SurfaceHolder _holder, Bitmap _draw_object) {
        holder = _holder;
        draw_object = _draw_object;
        icon_width = draw_object.getWidth();
        icon_height = draw_object.getHeight();
        exists = true;
        id = -1;
    }

    public void init(GL10 _gl, MySprite draw_object) {
        //icon_width = draw_object.getWidth();
        //icon_height = draw_object.getHeight();

        gl = _gl;
        sprite_object = draw_object;

        exists = true;
        id = -1;
    }

    public void update(double touch_x, double touch_y, int touch_state) {
    }

    public void draw(double touch_x, double touch_y, int touch_state, Canvas canvas) {

        synchronized (holder) {
            if (exists == true) {
                canvas.drawBitmap(draw_object, (int) x - icon_width / 2 , (int) y - icon_height / 2, paint);
            }
        }
    }

    public void draw(GL10 _gl) {

            if (exists == true) {
                sprite_object.draw(_gl,(float) x,(float) y);
            }

    }

    public double getMapX() {
        return x;
    }

    public double getMapY() {
        return y;
    }

    public boolean exists() { return exists; }

    public void setExists(boolean _exists){
        exists = _exists;
    }

    public int getId(){return id;}

    public void setId(int _id){
        id = _id;
    }

}


