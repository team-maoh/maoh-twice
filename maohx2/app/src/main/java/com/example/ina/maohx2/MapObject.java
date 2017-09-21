package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

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

    public void init(SurfaceHolder _holder, Bitmap _draw_object) {
        holder = _holder;
        draw_object = _draw_object;
        icon_width = draw_object.getWidth();
        icon_height = draw_object.getHeight();
        exists = true;
        id = -1;
    }

    public void update(double touch_x, double touch_y, int touch_state) {
    }

    public void draw(double touch_x, double touch_y, int touch_state, Canvas canvas) {

        synchronized (holder) {
            if (exists == true) {
                canvas.drawBitmap(draw_object, (int) x - icon_width / 2, (int) y - icon_height / 2, paint);
            }
        }
    }

    public double getMapX() {
        return x;
    }

    public double getMapY() {
        return y;
    }

    public boolean exists() { return exists; }

    public void setExists(boolean input_exists){
        exists = input_exists;
    }

    public int getId(){return id;}

    public void setId(int input_id){
        id = input_id;
    }

}


