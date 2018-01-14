package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;


//import com.maohx2.ina.MySprite;
// import com.maohx2.ina.waste.MySprite;

import java.util.Random;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Draw.Graphic;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapEnemy extends MapUnit {

    MapPlayer player;
    int STEP = 5;
    int chase_count;
    int PERIOD_FRAMES_OF_CHASE = 15;//[単位:フレーム]/Enemyはこの枚数ごとに目標座標を更新する
    double dst_x = 100, dst_y = 100;

    public MapEnemy(Graphic graphic, MapObjectAdmin map_object_admin, Camera _camera){
        super(graphic, map_object_admin, _camera);

        player = map_object_admin.getPlayer();

        Random random = new Random();
        w_x = random.nextDouble() * 1000;
        w_y = random.nextDouble() * 600;

        chase_count = 0;

        draw_object = "ゴキ太郎";
    }

    public void init() {
        super.init();
    }

    @Override
    public void update() {

//        if (myDistance(x, y, dst_x, dst_y) < PLAYER_STEP*CHASE_STEPS) {
//            dst_x = player.getPlayerWorldX();
//            dst_y = player.getPlayerWorldY();
//        } else {
//            dst_x = player.getChaseWorldX();
//            dst_y = player.getChaseWorldY();
//        }

        chase_count++;
        if(chase_count > PERIOD_FRAMES_OF_CHASE){
            dst_x = player.getPlayerWorldX();
            dst_y = player.getPlayerWorldY();
        }

        dst_steps = (int) (myDistance(w_x, w_y, dst_x, dst_y) / (double) STEP);
        dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので

        dx = (int) ((dst_x - w_x) / dst_steps);
        dy = (int) ((dst_y - w_y) / dst_steps);
        w_x += dx;
        w_y += dy;

        updateDirOnMap(w_x + dx, w_y + dy);
    }

    private double myDistance(double x1, double y1, double x2, double y2) {
        return (pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5));
    }

}