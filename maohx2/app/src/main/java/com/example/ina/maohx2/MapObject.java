package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Created by Fuusya on 2017/09/11.
 */

abstract public class MapObject {

    double x, y;
    SurfaceHolder holder;
    Bitmap draw_object;
    Paint paint = new Paint();

    public void init(SurfaceHolder _holder, Bitmap _draw_object) {
        holder = _holder;
        draw_object = _draw_object;
    }

    public void update(double touch_x, double touch_y, int touch_state) {
    }

    public void draw(double touch_x, double touch_y, int touch_state, Canvas canvas) {
        //Canvas canvas = null;
        //canvas = holder.lockCanvas(null);

        synchronized (holder) {
            //canvas.drawColor(Color.WHITE);
            if(x > 0 && y > 0){//画像を消したいときは x=-1,y=-1 を指定する
                canvas.drawBitmap(draw_object, (int) x, (int) y, paint);
            }
            //holder.unlockCanvasAndPost(canvas);
        }
    }

    public double getMapX() {
        return x;
    }

    public double getMapY() {
        return y;
    }

}
