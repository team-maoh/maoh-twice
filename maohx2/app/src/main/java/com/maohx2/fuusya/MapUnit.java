package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Point;
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
    boolean has_bad_status, can_exit_room, can_walk;
    boolean collide_wall;

    //デバッグ用
    int time_count;

    //移動が遅くなる、移動が逆向きになる、移動が不可能になる
    int frames_walking_slowly, frames_walking_inversely, frames_cannot_walk;
    //酔歩する、駒のように回転した後に別の場所にテレポートする、玄関を通過できなくなる
    int frames_being_drunk, frames_waiting_teleported, frames_cannot_exit_room;
    //壁までふっ飛ばされる, ふっ飛ばされる前の待ち時間
    int frames_being_blown_away, frames_before_blown_away;

    double blown_rad;//ふっ飛ばされる方角

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
        frames_cannot_walk = 0;
        frames_being_drunk = 0;
        frames_waiting_teleported = 0;
        frames_cannot_exit_room = 0;
        frames_being_blown_away = 0;
        frames_before_blown_away = 0;
        //
        has_bad_status = false;

        can_walk = true;
        collide_wall = false;

        blown_rad = random.nextDouble() * 2 * PI;


    }

    public void init() {
        super.init();
    }

    public void update() {

    }

    //マップ上のUnitのアイコンの向き（dir_on_map）を更新する
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
    protected boolean walkOneStep(double dst_x, double dst_y, double input_step) {

        //walking_inversely を[input_step < 0]で表現するので、dst_steps = max(式, 1) では駄目
        int dst_steps = (int) myDistance(dst_x, dst_y, w_x, w_y) / (int) input_step;

        double dx, dy;

        if (dst_steps != 0) {
            dx = (dst_x - w_x) / dst_steps;
            dy = (dst_y - w_y) / dst_steps;
        } else {
            dx = 0.0;
            dy = 0.0;
        }

        boolean is_touching_x_wall = false;
        boolean is_touching_y_wall = false;

        double hand_x, hand_y;

        for (double i = 0.0; i < 360.0; i += ANGLE_FOR_WALL) {

            //壁との衝突を判定する座標(プレイヤー座標(w_x, w_y)の周囲に円状に張り巡らされる)
            hand_x = w_x + REACH_FOR_WALL * cos(i);
            hand_y = w_y - REACH_FOR_WALL * sin(i);

            if (can_exit_room == false) {
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

        return (is_touching_x_wall || is_touching_y_wall);
    }

    //0:壁なし, 1: －, 2: |
    protected int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection(x1, y1, x2, y2);
    }

    public double getStep() {
        return step;
    }

    public void setBadStatus(String status_name) {
        setBadStatus(status_name, 1);
    }

    public void setBadStatus(String status_name, int activating_frames) {

        if (status_name == null) {
            System.out.println("status_name is null");
        } else switch (status_name) {

            case "walking_slowly":
                frames_walking_slowly = activating_frames;
                break;

            case "walking_inversely":
                frames_walking_inversely = activating_frames;
                break;

            case "cannot_walk":
                frames_cannot_walk = activating_frames;
                break;

            case "being_drunk":
                frames_being_drunk = activating_frames;
                break;

            case "cannot_exit_room":
                frames_cannot_exit_room = activating_frames;
                break;

            case "being_teleported":
                frames_waiting_teleported = 20;
                break;

            case "found_by_enemy":
                map_object_admin.makeAllEnemiesFindPlayer();
                frames_cannot_walk = 15;
                break;

            case "being_blown_away":
                frames_being_blown_away = 30;
                frames_before_blown_away = 10;
                blown_rad = random.nextDouble() * 2 * PI;
                break;

            case "being_damaged":
                frames_cannot_walk = 10;
                //
                //Playerにダメージを与える文
                //
                break;

            default:
                System.out.println("setBadStatus()の引数がおかしい");

        }
    }

    public double checkBadStatus(double raw_step) {

        double residual_x = dst_w_x - w_x;
        double residual_y = dst_w_y - w_y;

        has_bad_status = false;
        can_walk = true;

        //■瞬間移動
        if (frames_waiting_teleported > 1) {
            has_bad_status = true;

            can_walk = false;

            double dst_rad = (PI / 4) * frames_waiting_teleported + PI / 2 + PI / 16;

            residual_x = raw_step * cos(dst_rad);
            residual_y = raw_step * sin(dst_rad);

            frames_waiting_teleported--;

        } else if (frames_waiting_teleported == 1) {

            Point teleported_point = map_admin.getRoomPoint();
            w_x = teleported_point.x;
            w_y = teleported_point.y;

            frames_waiting_teleported--;
        }

        if (frames_cannot_walk > 0) {
            has_bad_status = true;

            can_walk = false;

            frames_cannot_walk--;
        }

        if (frames_walking_inversely > 0) {
            has_bad_status = true;

            if (raw_step > 0) {
                raw_step = -raw_step;
            }

            frames_walking_inversely--;
        }
        if (frames_walking_slowly > 0) {
            has_bad_status = true;

            raw_step = max(raw_step / 4.0, 1);

            frames_walking_slowly--;
        }

        //■酔歩
        if (frames_being_drunk > 0) {
            has_bad_status = true;

            double random_double = makeNormalDist() * 0.08;
            //念のため
            while (!(-1.0 <= random_double && random_double < 1.0)) {
                random_double = makeNormalDist() * 0.08;
            }

            //pre_random_radを平均とした正規分布
            double random_rad = random_double * PI + pre_random_rad;

            residual_x = raw_step * cos(random_rad);
            residual_y = raw_step * sin(random_rad);

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

            raw_step = max(raw_step / 2.0, 1);

            System.out.println("drunk    " + frames_being_drunk);
            frames_being_drunk--;
        }//酔歩パートおわり

        if (frames_cannot_exit_room > 1) {
            can_exit_room = false;
            frames_cannot_exit_room--;
        } else if (frames_cannot_exit_room == 1) {
            can_exit_room = true;
            frames_cannot_exit_room--;
        }

        //■ふっ飛ばし
        if (frames_before_blown_away > 0) {

            has_bad_status = true;

            can_walk = false;
            raw_step = max(1, raw_step / 5);

            double dst_rad = (PI / 4) * frames_before_blown_away + PI / 2 + PI / 16;

            residual_x = raw_step * cos(dst_rad);
            residual_y = raw_step * sin(dst_rad);

            frames_before_blown_away--;

        } else if (frames_being_blown_away > 0) {

            has_bad_status = true;

            can_walk = true;

            residual_x = 10.0 * raw_step * cos(blown_rad);
            residual_y = 10.0 * raw_step * sin(blown_rad);

            frames_being_blown_away--;
        }

        dst_w_x = w_x + residual_x;
        dst_w_y = w_y + residual_y;

        return raw_step;

    }

    public int getFramesWaitingTeleported(){
        return frames_waiting_teleported;
    }

}