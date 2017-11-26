package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.pow;


//import com.maohx2.ina.MySprite;
// import com.maohx2.ina.waste.MySprite;

import java.util.Random;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.waste.MySprite;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapEnemy extends MapUnit {

    MapPlayer player;
    int STEP = 10;
    int chase_count;
    int PERIOD_FRAMES_OF_CHASE = 15;//[単位:フレーム]/Enemyはこの枚数ごとに目標座標を更新する
    double dst_x = 100, dst_y = 100;

    public MapEnemy(Graphic graphic, MapObjectAdmin map_object_admin){
        super(graphic, map_object_admin);

        player = map_object_admin.getPlayer();

        Random random = new Random();
        x = random.nextDouble() * 1000;
        y = random.nextDouble() * 600;

        chase_count = 0;

        draw_object = "slime";

    }

//    @Override
    public void init() {
        super.init();
//        player = map_object_admin.getPlayer();
//
//        Random random = new Random();
//        x = random.nextDouble() * 1000;
//        y = random.nextDouble() * 600;
//
//        chase_count = 0;
    }

//    @Override
//    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin map_object_admin) {
//        super.init(gl, draw_object, map_object_admin);
//        player = map_object_admin.getPlayer();
//
//        Random random = new Random();
//        x = random.nextDouble() * 1000;
//        y = random.nextDouble() * 600;
//    }

    @Override
    public void update() {

        /*
        if (myDistance(x, y, dst_x, dst_y) < PLAYER_STEP*CHASE_STEPS) {
            dst_x = player.getPlayerWorldX();
            dst_y = player.getPlayerWorldY();
        } else {
            dst_x = player.getChaseWorldX();
            dst_y = player.getChaseWorldY();
        }
        */

        chase_count++;
        if(chase_count > PERIOD_FRAMES_OF_CHASE){
            dst_x = player.getPlayerWorldX();
            dst_y = player.getPlayerWorldY();
            chase_count = 0;
        }

        dst_steps = (int) (myDistance(x, y, dst_x, dst_y) / (double) STEP);
        dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので

        dx = (int) ((dst_x - x) / dst_steps);
        dy = (int) ((dst_y - y) / dst_steps);
        x += dx;
        y += dy;
    }

    private double myDistance(double x1, double y1, double x2, double y2) {
        return (pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5));
    }
}