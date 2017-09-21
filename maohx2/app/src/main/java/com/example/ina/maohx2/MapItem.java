package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapItem extends MapObject {

    MapUnit player;
    MapObjectAdmin map_object_admin;

    public void init(SurfaceHolder _holder, Bitmap _draw_item, MapObjectAdmin _map_object_admin) {
        super.init(_holder, _draw_item);

        map_object_admin = _map_object_admin;

        Random random = new Random();
        x = random.nextDouble() * 1000;//アイテムが発生する座標
        y = random.nextDouble() * 600;
    }

    @Override
    public void update(double touch_x, double touch_y, int touch_state) {
        super.update(touch_x, touch_y, touch_state);
    }
}
