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
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapUnit extends MapObject {

    double REACH_FOR_WALL = 10.0;//プレイヤーを中心とした半径 REACH_FOR_WALL[px] の円で壁との衝突を判定する
    double ANGLE_FOR_WALL = 11.25;//壁との衝突判定に使う角度の細かさ

    double x_reach, y_reach;
    int dst_steps, now_steps;
    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;

    //デバッグ用
    int time_count;

    public MapUnit(Graphic _graphic, MapObjectAdmin _map_object_admin, MapAdmin _map_admin, Camera _camera) {

        super(_graphic, _map_object_admin, _camera);

        map_object_admin = _map_object_admin;
        map_admin = _map_admin;

        draw_object = "ゴキ魅";

        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        x_reach = 0.0;
        y_reach = 0.0;

        time_count = 0;
    }

    public void init() {
        super.init();
    }

    public void update() {
    }

    public void updateDirOnMap(double next_w_x, double next_w_y) {

        if(next_w_x != w_x || next_w_y != w_y) {
            //dir_on_map = [0, 2*PI)
            dir_on_map = calcDirOnMap(next_w_x, next_w_y);
        }
    }

    //[0, 2*PI) のdoubleを返す
    //自分の座標[w_x, w_y]を原点として、引数[]がどちらの方角にあるか
    public double calcDirOnMap(double next_w_x, double next_w_y){
        return (2 * PI - atan2(next_w_y - w_y, next_w_x - w_x)) % (2 * PI);
    }

    //dx, dyを正して(壁に近すぎたらdx, dyをゼロにして)から、
    //xとyを更新する(一歩進む)
    public void walkOneStep(double dx, double dy) {

        boolean is_touching_x_wall = false;
        boolean is_touching_y_wall = false;

        double hand_x;
        double hand_y;

        for (double i = 0.0; i < 360.0; i += ANGLE_FOR_WALL) {

            //壁との衝突を判定する座標(プレイヤー座標(w_x, w_y)の周囲に円状に張り巡らされる)
            hand_x = w_x + REACH_FOR_WALL * cos(i);
            hand_y = w_y - REACH_FOR_WALL * sin(i);

            //縦方向の壁と接触する場合
            if (detectWall(hand_x, hand_y, hand_x + dx, hand_y) != 0) {
                is_touching_x_wall = true;
            }
            //横方向の壁と接触する場合
            if (detectWall(hand_x, hand_y, hand_x, hand_y + dy) != 0) {
                is_touching_y_wall = true;
            }
        }

        //一歩進む
        if (is_touching_x_wall == false) {
            w_x += dx;
        }
        if (is_touching_y_wall == false) {
            w_y += dy;
        }

    }
    //0:壁なし, 1:水平, 2:垂直
    private int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection(x1, y1, x2, y2);
    }

}