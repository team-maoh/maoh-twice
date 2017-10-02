package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import com.example.horie.map.MapAdmin;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapUnit extends MapObject {

    double dx, dy, x_reach, y_reach;
    int dst_steps, now_steps;
    boolean moving;
    MapObjectAdmin map_object_admin;
    double REACH_FOR_WALL = 25.0;

    public void init(SurfaceHolder holder, Bitmap draw_object, MapObjectAdmin map_object_admin, MapAdmin _map_admin) {
    }

    public void init(SurfaceHolder _holder, Bitmap _draw_object, MapObjectAdmin _map_object_admin) {
        super.init(_holder, _draw_object);

        map_object_admin = _map_object_admin;

        dx = 0.0;//移動距離(differential x)
        dy = 0.0;
        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        moving = false;
        x_reach = 0.0;
        y_reach = 0.0;
    }

    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin _map_object_admin) {
        super.init(gl, draw_object);

        map_object_admin = _map_object_admin;

        dx = 0.0;//移動距離(differential x)
        dy = 0.0;
        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        moving = false;

    }

    public void update(){


    }


}