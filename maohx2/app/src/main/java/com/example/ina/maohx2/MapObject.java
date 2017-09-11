package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by Fuusya on 2017/09/11.
 */

abstract public class MapObject {

    double x, y;
    SurfaceHolder holder;
    Bitmap draw_object;
    Paint paint = new Paint();

    public void init(SurfaceHolder m_holder, Bitmap _draw_object) {
        holder = m_holder;
        draw_object = _draw_object;
        x = 0;//現在座標
        y = 0;
    }


    public void update(double touch_x, double touch_y, int touch_state) {}

    public void draw(double touch_x, double touch_y, int touch_state) {
        Canvas canvas = null;
        canvas = holder.lockCanvas(null);
        synchronized (holder) {
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(draw_object, (int) x, (int) y, paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
