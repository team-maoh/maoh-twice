package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

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
    int PLAYER_STEP = 20;
    int CHASE_STEPS = 10;//名前は仮/EnemyはPlayerの{現在座標ではなく}CHASE_STEPS歩前の座標を追いかける
    double REACH_FOR_WALL = 5.0;
    SoundAdmin sound_admin;

    BitmapData bitmap_data;//

    public MapUnit(Graphic _graphic, MapObjectAdmin _map_object_admin) {
        super(_graphic, _map_object_admin);

        map_object_admin = _map_object_admin;

        draw_object = "ゴキ魅";

        dx = 0.0;//移動距離(differential x)
        dy = 0.0;
        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        moving = false;
        x_reach = 0.0;
        y_reach = 0.0;
    }

//    public void init(SurfaceHolder holder, Bitmap draw_object, MapObjectAdmin map_object_admin, MapAdmin _map_admin, SoundAdmin _sound_admin) {
//    }

    public void init() {
        super.init();
    }

//    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin _map_object_admin) {
//        super.init(gl, draw_object);
//
//        map_object_admin = _map_object_admin;
//
//        dx = 0.0;//移動距離(differential x)
//        dy = 0.0;
//        dst_steps = 1;//今の目標地点までの歩数
//        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
//        moving = false;
//
//    }

//    public void update(){}

    public double getDirection() {
        return direction;
    }

    public void updateDirection() {

        direction = atan2(dx, dy) + PI;
    }

}