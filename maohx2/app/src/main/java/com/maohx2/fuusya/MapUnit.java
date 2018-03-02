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
import static java.lang.Math.max;
import static java.lang.Math.pow;
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
    double step;
    double dst_w_x, dst_w_y;
    double pre_random_rad;

    //デバッグ用
    int time_count;

    //移動が遅くなる、移動が逆向きになる、移動が不可能になる、移動方向がランダムになる
    int frames_walking_slowly, frames_walking_inversely, frames_unable_to_walk, frames_being_drunk;

    public MapUnit(Graphic _graphic, MapObjectAdmin _map_object_admin, Camera _camera) {
        super(_graphic, _map_object_admin, _camera);

        map_object_admin = _map_object_admin;

        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        x_reach = 0.0;
        y_reach = 0.0;

        time_count = 0;

        pre_random_rad = random.nextDouble() * 2 * PI;

        frames_walking_slowly = 0;
        frames_walking_inversely = 0;
        frames_unable_to_walk = 0;
        //
        frames_being_drunk = 0;

    }

    public void init() {
        super.init();
    }

    public void update() {
    }

    public void updateDirOnMap(double next_w_x, double next_w_y) {

        if (next_w_x != w_x || next_w_y != w_y) {
            //dir_on_map = [0, 2*PI)
            dir_on_map = calcDirOnMap(next_w_x, next_w_y);
        }
    }

    //[0, 2*PI) のdoubleを返す
    //自分の座標[w_x, w_y]を原点として、引数[]がどちらの方角にあるか
    public double calcDirOnMap(double next_w_x, double next_w_y) {
        return (2 * PI - atan2(next_w_y - w_y, next_w_x - w_x)) % (2 * PI);
    }

    //dx, dyを正して(壁に近すぎたらdx, dyをゼロにして)から、
    //xとyを更新する(一歩進む)
//    public void walkOneStep(double dx, double dy, boolean hit_against_entrance) {

    public void walkOneStep(double dst_x, double dst_y, double input_step, boolean hit_against_entrance) {

        int dst_steps = (int) myDistance(dst_x, dst_y, w_x, w_y) / (int) input_step;
        dst_steps++;//ゼロ除算対策
        double dx = (dst_x - w_x) / dst_steps;
        double dy = (dst_y - w_y) / dst_steps;

        boolean is_touching_x_wall = false;
        boolean is_touching_y_wall = false;

        double hand_x;
        double hand_y;

        for (double i = 0.0; i < 360.0; i += ANGLE_FOR_WALL) {

            //壁との衝突を判定する座標(プレイヤー座標(w_x, w_y)の周囲に円状に張り巡らされる)
            hand_x = w_x + REACH_FOR_WALL * cos(i);
            hand_y = w_y - REACH_FOR_WALL * sin(i);

            if (hit_against_entrance == true) {
                if (map_admin.isEntrance(map_admin.worldToMap((int) (w_x + dx)), map_admin.worldToMap((int) w_y))) {
                    is_touching_x_wall = true;
                }
                if (map_admin.isEntrance(map_admin.worldToMap((int) w_x), map_admin.worldToMap((int) (w_y + dy)))) {
                    is_touching_y_wall = true;
                }
            }

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

    //0:壁なし, 1: －, 2: |
    protected int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection(x1, y1, x2, y2);
    }

    public double getStep() {
        return step;
    }

    public void setFramesWalkingSlowly(int _frames_walking_slowly) {
        frames_walking_slowly = _frames_walking_slowly;
    }

    public void setFramesWalkingInversely(int _frames_walking_inversely) {
        frames_walking_inversely = _frames_walking_inversely;
    }

    public void setFramesUnableToWalk(int _frames_unable_to_walk) {
        frames_unable_to_walk = _frames_unable_to_walk;
    }

    public void setFramesBeingDrunk(int _frames_being_drunk) {
        frames_being_drunk = _frames_being_drunk;
    }

    public double checkBadState(double raw_step) {

        double residual_x = dst_w_x - w_x;
        double residual_y = dst_w_y - w_y;

        if (frames_unable_to_walk > 0) {
            residual_x = 0;
            residual_y = 0;

            frames_unable_to_walk--;
        } else {

            if (frames_walking_inversely > 0) {
                residual_x = -residual_x;
                residual_y = -residual_y;

                frames_walking_inversely--;
            }
            if (frames_walking_slowly > 0) {
                raw_step = max(raw_step / 2.0, 1.0);

                frames_walking_slowly--;
            }

//            if ((is_buzzed || frames_being_drunk) == true) {
            if (frames_being_drunk > 0) {

//                double dst_rad = calcDirOnMap(dst_w_x, dst_w_y);
                double dst_amp = myDistance(dst_w_x, dst_w_y, w_x, w_y);

                double random_double = makeNormalDist() * 0.08;
                //念のため
                while (!(-1.0 <= random_double && random_double < 1.0)) {
                    random_double = makeNormalDist() * 0.08;
                }

                //pre_random_radを平均とした正規分布
                double random_rad = random_double * PI + pre_random_rad;

                residual_x =  raw_step * cos(random_rad);
                residual_y =  raw_step * sin(random_rad);

                switch (detectWall(w_x, w_y, w_x + residual_x, w_y + residual_y)) {
                    case 1://横向きの壁と衝突
                        random_rad = 2 * PI - random_rad;
                        residual_x = raw_step * cos(random_rad);
                        residual_y = raw_step * sin(random_rad);
                        break;
                    case 2://縦向きの壁と衝突
                        random_rad = (3 * PI - random_rad) % (2 * PI);
                        residual_x = raw_step * cos(random_rad);
                        residual_y = raw_step * sin(random_rad);
                        break;
                    default:
                        break;
                }

                pre_random_rad = random_rad;

                System.out.println("drunk    "+frames_being_drunk);
                frames_being_drunk--;
            }

        }

        dst_w_x = w_x + residual_x;
        dst_w_y = w_y + residual_y;

        return raw_step;

    }


}