package com.maohx2.fuusya;


import android.graphics.Bitmap;
import android.view.SurfaceHolder;
import static com.maohx2.ina.Constants.Touch.TouchState;



//import com.maohx2.ina.MySprite;

import com.maohx2.ina.waste.MySprite;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

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
        y = random.nextDouble() * 1300;
    }

    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin _map_object_admin) {
        super.init(gl, draw_object);

        map_object_admin = _map_object_admin;

        Random random = new Random();
        x = random.nextDouble() * 1000;//アイテムが発生する座標
        y = random.nextDouble() * 1300;
    }

    @Override
    public void update(double touch_x, double touch_y, TouchState touch_state) {
        super.update(touch_x, touch_y, touch_state);
    }
}
