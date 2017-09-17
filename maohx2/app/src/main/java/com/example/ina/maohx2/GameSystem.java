package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

import com.example.horie.map.MapAdmin;

/**
 * Created by ina on 2017/09/05.
 */

public class GameSystem {

    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;
    SurfaceHolder holder;


    public void init(SurfaceHolder _holder, Bitmap neco, Bitmap apple, Bitmap slime) {

        holder = _holder;

        map_object_admin = new MapObjectAdmin();
        map_object_admin.init(holder, neco, apple, slime);//MapObjectAdmin.javaのinitを実行
        map_admin = new MapAdmin(holder);
    }

    public void update(double touch_x, double touch_y, int touch_state) {

        map_object_admin.update(touch_x, touch_y, touch_state);

    }

    public void draw(double touch_x, double touch_y, int touch_state) {

        Canvas canvas = null;
        canvas = holder.lockCanvas(null);
        canvas.drawColor(Color.BLACK);


        map_admin.drawMap(canvas);
        map_object_admin.draw(touch_x, touch_y, touch_state, canvas);

        holder.unlockCanvasAndPost(canvas);

    }

    public GameSystem() {
    }
}
