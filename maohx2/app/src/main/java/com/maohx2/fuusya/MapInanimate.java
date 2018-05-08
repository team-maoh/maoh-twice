package com.maohx2.fuusya;

import android.graphics.Point;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Draw.Graphic;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

//import com.maohx2.ina.MySprite;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapInanimate extends MapObject {

    public MapInanimate(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera) {
        super(graphic, _map_object_admin, _camera);

        id = _id;

        w_x = 0;
        w_y = 0;

    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

    }

    public void setId(int _id) {

        id = _id;
    }

    //0:壁なし, 1: －, 2: |
    protected int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection(x1, y1, x2, y2);
    }

    public void generatePosition(int _w_x, int _w_y, int magni) {

//        System.out.println("desuwanowa__" + _w_x);
//        System.out.println("desuwanowa__" + _w_y);

        for (int i = 0; i < 4; i++) {
            double iter_rad = i * PI / 2;
            if (detectWall(_w_x, _w_y, _w_x + magni * cos(iter_rad), _w_y + magni * sin(iter_rad)) != 0) {

                switch (i) {
                    case 3:
                        w_x = _w_x + magni * cos(iter_rad) / 2;
                        w_y = _w_y + magni * sin(iter_rad) / 2 * 1.5;
//                        System.out.println("desuwa__" + i);
                        break;
                    default:
                        w_x = _w_x + magni * cos(iter_rad) / 2;
                        w_y = _w_y + magni * sin(iter_rad) / 2;
//                        System.out.println("desuwa__" + i);
                        break;
                }
//                w_x = _w_x;
//                w_y = _w_y;

            }

        }

//        double random_rad = random.nextDouble() * 2 * PI;
//        double begin = (int) (random.nextDouble() * 4.0) / 4.0;// = 0 or 0.25 or 0.50 or 0.75 ( or 1.0)
//        double begin_rad = begin * 2 * PI;
//
//        double sign = 1.0;
//        if (random.nextBoolean()) {
//            sign = -1.0;
//        }
//
//        for (int i = 0; i < 4; i++) {
////        for (double i = 0; i <= 2 * PI; i += PI / 2) {
//            double i_rad = begin + i * sign * PI / 2.0;
//            if (detectWall(_w_x, _w_y, _w_x + magni * cos(i_rad), _w_y + magni + sin(i_rad)) != 0) {
//                w_x = _w_x + magni * cos(i_rad) / 2;
//                w_y = _w_y + magni * sin(i_rad) / 2;
//            }
//        }
//
//        System.out.println("_mine_point_desu_x__" + w_x);
//        System.out.println("_mine_point_desu_y__" + w_y);

//        //縦方向の壁と接触する場合
//        if (detectWall(hand_x, hand_y, hand_x + dx, hand_y) != 0) {
//            is_touching_x_wall = true;
//        }
//        //横方向の壁と接触する場合
//        if (detectWall(hand_x, hand_y, hand_x, hand_y + dy) != 0) {
//            is_touching_y_wall = true;
//        }
//
//
//        map_admin.detectWallDirection()

    }



}
