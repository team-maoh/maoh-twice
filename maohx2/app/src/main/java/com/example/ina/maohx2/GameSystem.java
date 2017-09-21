package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

/**
 * Created by ina on 2017/09/05.
 */

public class GameSystem {

    MapObjectAdmin map_object_admin;

    public void init(SurfaceHolder holder, Bitmap neco, Bitmap apple,  Bitmap banana,Bitmap slime) {

        map_object_admin = new MapObjectAdmin();
        map_object_admin.init(holder, neco, apple, banana, slime);//MapObjectAdmin.javaのinitを実行

    }

    public void update(double touch_x, double touch_y, int touch_state) {

        map_object_admin.update(touch_x, touch_y, touch_state);

    }

    public void draw(double touch_x, double touch_y, int touch_state) {

        map_object_admin.draw(touch_x, touch_y, touch_state);
    }

    public GameSystem() {
    }
}
