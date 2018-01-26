package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;


//import com.maohx2.ina.MySprite;
// import com.maohx2.ina.waste.MySprite;

import java.util.Random;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.Graphic;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapEnemy extends MapUnit {

    MapPlayer player;
    int STEP = 20;
    int chase_count;
    double dst_x, dst_y;

    int CHASE_STEPS = 6;//名前は仮 / EnemyはPlayerの{現在座標ではなく}CHASE_STEPS歩前の座標を追いかける
    double[] chase_w_x = new double[CHASE_STEPS];
    double[] chase_w_y = new double[CHASE_STEPS];

    double clock_rad;

    public MapEnemy(Graphic graphic, MapObjectAdmin map_object_admin, MapAdmin _map_admin, Camera _camera) {
        super(graphic, map_object_admin, _map_admin, _camera);

        player = map_object_admin.getPlayer();

        Random random = new Random();
        w_x = random.nextDouble() * 1000;
        w_y = random.nextDouble() * 1000;

//        w_x = 800;
//        w_y = 500;

        dst_x = w_x;
        dst_y = w_y;

        chase_count = 0;
        for (int i = 0; i < CHASE_STEPS; i++) {
            chase_w_x[i] = 0.0;
            chase_w_y[i] = 0.0;
        }

        draw_object = "ゴキ太郎";

        clock_rad = 0.0;
    }

    public void init() {
        super.init();
    }

    @Override
    public void update() {

        double dx, dy;

        //●CHASE_STEPS歩前のプレイヤー座標を目標座標とする
//        chase_w_x[chase_count] = player.getPlayerWorldX();
//        chase_w_y[chase_count] = player.getPlayerWorldY();
//        chase_count = (chase_count + 1) % CHASE_STEPS;
//        dst_x = chase_w_x[chase_count];
//        dst_y = chase_w_y[chase_count];

        //●プレイヤーがタッチしている間だけ移動する
        //プレイヤーのタッチ座標をそのまま目標座標とする
//        if (player.getIsMoving() == true) {
//            dst_x = player.getTouchWouldX();
//            dst_y = player.getTouchWouldY();
//        } else {
//            dst_x = w_x;
//            dst_y = w_y;
//        }

        //くるくる回る
        clock_rad = (clock_rad + PI / 30.0) % (2 * PI);
        System.out.println(clock_rad);
        double clock_w_x = w_x + 10 * cos(clock_rad);
        double clock_w_y = w_y + 10 * sin(clock_rad);
        updateDirOnMap(clock_w_x, clock_w_y);
        //自身の視界（角度cos(PI/6.0)の範囲）にプレイヤーが入るとprintする
//        double length = myDistance(player.getWorldX(), player.getWorldY(), w_x, w_y);
//        double inner_prod = cos(dir_on_map) * (player.getWorldX() - w_x) / length + sin(dir_on_map) * (player.getWorldY() - w_y) / length;
//        System.out.println("uncchi" + inner_prod);
//        if (inner_prod > cos(PI / 6.0)) {
//            System.out.println("●uncchi" + inner_prod);
//        }
        //
        //[0, 7]のint_dir_on_mapが
        if (convertDirToIntDir(8, dir_on_map) == convertDirToIntDir(8, calcDirOnMap(player.getWorldX(), player.getWorldY()))) {
            System.out.println("wonchi");
            dst_x = player.getWorldX();
            dst_y = player.getWorldY();
        }

        dst_steps = (int) (myDistance(w_x, w_y, dst_x, dst_y) / (double) STEP);
        dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので

        dx = (dst_x - w_x) / dst_steps;
        dy = (dst_y - w_y) / dst_steps;
        walkOneStep(dx, dy);//一歩進む

        updateDirOnMap(w_x + dx, w_y + dy);
    }

    private double myDistance(double x1, double y1, double x2, double y2) {
        return (pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5));
    }

}