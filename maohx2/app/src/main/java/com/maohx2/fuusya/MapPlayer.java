package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.icu.text.SymbolTable;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.MySprite;
import static com.maohx2.ina.Constants.Touch.TouchState;


import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.StrictMath.signum;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapPlayer extends MapUnit {

    int ENCOUNT_STEPS_PERIOD = 500;//この歩数ごとに敵とエンカウントする
    int SOUND_STEPS_PERIOD = 20;//この歩数ごとに足音SEが鳴る
    //
    int encount_steps;
    int sound_steps;

    MapAdmin map_admin;
    SoundAdmin sound_admin;
    DungeonUserInterface dungeon_user_interface;

    double REACH_FOR_WALL = 10.0;//プレイヤーを中心とした半径 REACH_FOR_WALL[px] の円で壁との衝突を判定する
    double ANGLE_FOR_WALL = 11.25;//壁との衝突判定に使う角度の細かさ

    int PLAYER_STEP = 20;//プレイヤーの歩幅
    double touch_w_x, touch_w_y, touch_n_x, touch_n_y, touch_x, touch_y;
    int chase_num;
    int CHASE_STEPS = 10;//名前は仮 / EnemyはPlayerの{現在座標ではなく}CHASE_STEP歩前の座標を追いかける
    double[] chase_x = new double[CHASE_STEPS];//敵はCHASE_STEPSフレーム前のプレイヤー座標めがけて移動する
    double[] chase_y = new double[CHASE_STEPS];

    int touching_frame_count;

    TouchState touch_state;

    public MapPlayer(Graphic graphic, MapObjectAdmin _map_object_admin, MapAdmin _map_admin, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, Camera _camera) {
        super(graphic, _map_object_admin, _camera);

        dungeon_user_interface = _dungeon_user_interface;

        touch_state = dungeon_user_interface.getTouchState();

        map_admin = _map_admin;
        sound_admin = _sound_admin;

        encount_steps = 0;
        sound_steps = 0;

        w_x = 850;
        w_y = 350;
        chase_num = 0;

        for (int i = 0; i < 10; i++) {
            chase_x[i] = w_x;
            chase_y[i] = w_y;
        }
        chase_num++;

        draw_object = "ゴキ魅";

        touching_frame_count = 0;
    }

    public void init() {
        super.init();

    }

    @Override
    public void update() {

        touch_state = dungeon_user_interface.getTouchState();

        if (touch_state == TouchState.DOWN || touch_state == TouchState.DOWN_MOVE || touch_state == TouchState.MOVE) {

            touch_n_x = dungeon_user_interface.getTouchX();
            touch_n_y = dungeon_user_interface.getTouchY();
            touch_w_x = camera.convertToWorldCoordinateX((int) touch_n_x);
            touch_w_y = camera.convertToWorldCoordinateY((int) touch_n_y);

            //デバッグ用
            touch_x = touch_w_x;
            touch_y = touch_w_y;

            touching_frame_count++;

            moving = true;
        } else if (touching_frame_count > 10) {//画面を10frames以上タッチした場合、

            touching_frame_count = 0;

            moving = false;//指を離した時点で歩行を中止する
        }

        if (moving == true) {

//            switch (detectWall(x, y, x + dx + x_reach, y + dy + y_reach)) {
//                case 0://壁なし
//                    x += dx;
//                    y += dy;
//                    break;
//                case 1://水平
//                    if (detectWall(x, y, x + dx + x_reach, y + dy) != 2) {
//                        x += dx;
//                    }
//                    break;
//                case 2://垂直
//                    if (detectWall(x, y, x + dx, y + dy + y_reach) != 1) {
//                        y += dy;
//                    }
//                    break;
//                default:
//                    break;
//            }
//
//            //自分と壁との距離を外積で求めようとした
//            double start_angle;
//            if (dy < 0) {
//                start_angle = atan(dy / dx);
//            } else {
//                start_angle = atan(dy / dx) + 180;
//            }
//            boolean isWall = false, pre_isWall;
//            double border_theta[] = new double[2];
//            int i = 0;
//            for (double theta = start_angle; theta < start_angle + 360; theta += 3) {
//                pre_isWall = isWall;
//                if (detectWall(x + dx, y + dy, x + dx + REACH_FOR_WALL * cos(theta), y + dy + REACH_FOR_WALL * sin(theta)) != 0) {
//                    isWall = true;
//                } else {
//                    isWall = false;
//                }
//                if (pre_isWall != isWall) {
//                    border_theta[i] = theta;
//                    i++;
//                }
//                if (i == 2) {
//                    break;
//                }
//            }
//            double sink = REACH_FOR_WALL - (pow(REACH_FOR_WALL, 2.0) * (cos(border_theta[0] * sin(border_theta[1]) - sin(border_theta[0] * cos(border_theta[1])))));
//            dx += sink*cos((border_theta[0]+border_theta[1])/2.0);
//            dy += sink*sin((border_theta[0]+border_theta[1])/2.0);
//
//            for (double theta = 0.0; theta < 360.0; theta += 3.0) {
//                if (hasUpdateDxDy(theta) == true) {
//                    break;
//                }
//        }

            dst_steps = (int) myDistance(touch_w_x, touch_w_y, w_x, w_y) / PLAYER_STEP;
            dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
            //一歩あたりの距離
            dx = (touch_w_x - w_x) / dst_steps;
            dy = (touch_w_y - w_y) / dst_steps;

            //壁との衝突を考慮した上で、１歩進む
            walkOneStep();

            updateDirOnMap(touch_w_x, touch_w_y);

            //(x, y)を格納
            chase_x[chase_num] = w_x;
            chase_y[chase_num] = w_y;
            chase_num = (chase_num + 1) % CHASE_STEPS;

            encount_steps = (encount_steps + 1) % (ENCOUNT_STEPS_PERIOD + 1);
            if (encount_steps == ENCOUNT_STEPS_PERIOD) {
                System.out.println("◆一定歩数 歩いたので敵と遭遇");
            }
            sound_steps = (sound_steps + 1) % (SOUND_STEPS_PERIOD + 1);
            if (sound_steps % SOUND_STEPS_PERIOD == 0) {
                sound_admin.play("walk");//足音SE
            }

            //タッチ座標との距離が一定未満になる ときに歩み止まる
            if (3 > myDistance(touch_w_x, touch_w_y, w_x, w_y)) {
                moving = false;
                System.out.println("●タッチ座標との距離が一定未満 1");
            }
            //タッチ座標を通過してしまう ときに歩み止まる
            if (0 > (touch_w_x - (w_x + dx)) * dx || 0 > (touch_w_y - (w_y + dy)) * dy) {
                moving = false;
                System.out.println("●タッチ座標との距離が一定未満 2");
            }

        } else {//moving == false のとき
            touching_frame_count = 0;
        }

//        if (pre_x != w_x || pre_y != w_y) {
//            System.out.println("■" + w_x + " , " + w_y);
//        }

        //デバッグ用
        touch_x = camera.convertToNormCoordinateX((int) touch_w_x);
        touch_y = camera.convertToNormCoordinateY((int) touch_w_y);

        camera.setCameraOffset(w_x, w_y);

    }

//    void defineDxDy(int now_x, int now_y, int dst_x, int dst_y) {
//
//        dst_steps = myDistance(dst_x, dst_y, now_x, now_y) / STEP;
//        dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
//        dx = ((dst_x - now_x) / dst_steps);
//        dy = ((dst_y - now_y) / dst_steps);
//
//    }

    //(x1, y1)と(x2, y2)の距離を返す
    private double myDistance(double x1, double y1, double x2, double y2) {
        return pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5);
    }

    //dx, dyを正して(壁に近すぎたらdx, dyをゼロにして)から、
    //xとyを更新する(一歩進む)
    private void walkOneStep() {

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

//    boolean hasUpdateDxDy(double theta) {
//
//        double wx = 0.0, wy = 0.0;//壁ベクトル(wx, wy) ...
//
////        if (detectWall(x + _dx, y + _dy, x + _dx + REACH_FOR_WALL * cos(theta), y + _dy + REACH_FOR_WALL * sin(theta)) != 0) {
//        if (detectWall(x + REACH_FOR_WALL * cos(theta), y + REACH_FOR_WALL * sin(theta), x + dx + REACH_FOR_WALL * cos(theta), y + dy + REACH_FOR_WALL * sin(theta)) != 0) {
//            System.out.println("hasUpdate");
//
//            //速度ベクトルとの内積が正になるような壁ベクトルを選ぶ
//            wx = cos(theta + 90.0);
//            wy = sin(theta + 90.0);
//            if (wx * dx + wy * dy < 0) {
//                wx = -wx;
//                wy = -wy;
//            }
//
//            dx = (dx * wx + dy * wy) * wx;
//            dy = (dx * wx + dy * wy) * wy;
//
//            return true;
//        }
//
//        return false;
//    }

    //0:壁なし, 1:水平, 2:垂直
    private int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection(x1, y1, x2, y2);
    }

    public double getPlayerWorldX() {
        return w_x;
    }

    public double getPlayerWorldY() {
        return w_y;
    }



}