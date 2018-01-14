package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.MySprite;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapUnit extends MapObject {

    double dx, dy, x_reach, y_reach;
    int dst_steps, now_steps;
    boolean moving;
    MapObjectAdmin map_object_admin;

    //デバッグ用
    int time_count;

    public MapUnit(Graphic _graphic, MapObjectAdmin _map_object_admin, Camera _camera) {

        super(_graphic, _map_object_admin, _camera);

        map_object_admin = _map_object_admin;

        draw_object = "ゴキ魅";

        dx = 0.0;//移動距離(differential x)
        dy = 0.0;
        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        moving = false;
        x_reach = 0.0;
        y_reach = 0.0;

        time_count = 0;
    }

    public void init() {
        super.init();
    }

    public void update() {
    }

    public void updateDirOnMap() {

        dir_on_map = (dir_on_map + PI / 10);
        if (dir_on_map >=  PI) {
            dir_on_map = 0;
        }

//        dir_on_map = atan2(dy, dx) + PI;

    }
}