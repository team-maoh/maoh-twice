package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.MySprite;
import static com.maohx2.ina.Constants.Touch.TouchState;


import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.StrictMath.signum;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapPlayer extends MapUnit {

    int ENCOUNT_STEPS = 500;//この歩数ごとに敵とエンカウントする
    int steps_count;//歩数（敵とのエンカウントに使う）
    MapAdmin map_admin;
    int chase_num;
    double[] chase_x = new double[CHASE_STEPS];
    double[] chase_y = new double[CHASE_STEPS];
    DungeonUserInterface dungeon_user_interface;

    TouchState touch_state;

    public MapPlayer(Graphic graphic, MapObjectAdmin _map_object_admin, MapAdmin _map_admin, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin) {
        super(graphic, _map_object_admin);

        dungeon_user_interface = _dungeon_user_interface;

        touch_state = dungeon_user_interface.getTouchState();

        map_admin = _map_admin;
        sound_admin = _sound_admin;

        steps_count = 0;//歩数
        x = 50;
        y = 50;
        chase_num = 0;

        for (int i = 0; i < 10; i++) {
            chase_x[i] = x;
            chase_y[i] = y;
        }
        chase_num++;

        draw_object = "ゴキ魅";
    }

    public void init() {
        super.init();

    }

    @Override
    public void update() {

        //TouchState touch_state = dungeon_user_interface.getTouchState();// nullが出る
        double touch_x = dungeon_user_interface.getTouchX();
        double touch_y = dungeon_user_interface.getTouchY();

        if (touch_state == TouchState.DOWN || touch_state == TouchState.DOWN_MOVE || touch_state == TouchState.MOVE) {
            dst_steps = (int) myDistance(touch_x, touch_y, x, y) / PLAYER_STEP;
            dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
            dx = ((touch_x - x) / dst_steps);
            dy = ((touch_y - y) / dst_steps);

            x_reach = signum(dx) * REACH_FOR_WALL;
            y_reach = signum(dy) * REACH_FOR_WALL;

            moving = true;
        }
        if (moving == true) {

//            /*
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
//            */
//
//                        /*
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
//            */
//
//                        /*
//            for (double theta = 0.0; theta < 360.0; theta += 3.0) {
//                if (hasUpdateDxDy(theta) == true) {
//                    break;
//                }
//            }*/
            x += dx;
            y += dy;

//            //(x, y)を格納
//            chase_x[chase_num] = x;
//            chase_y[chase_num] = y;
//            chase_num++;
//            if (chase_num >= CHASE_STEPS) {
//                chase_num = 0;
//        }

            //自分と目標点の距離がPLAYER_STEP未満になる or 次ステップで自分のx(またはy)座標が目標点のそれを通り過ぎる
            if (PLAYER_STEP > myDistance(touch_x, touch_y, x, y) || 0 > (touch_x - (x + dx)) * dx || 0 > (touch_y - (y + dy)) * dy) {
                moving = false;
            }

            steps_count++;
            if (steps_count >= ENCOUNT_STEPS) {
                System.out.println("一定歩数歩いたので敵と遭遇");
                steps_count = 0;
            }
            if (steps_count % 20 == 0) {
                sound_admin.play("walk");//足音SE
            }

        }
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

    boolean hasUpdateDxDy(double theta) {

        double wx = 0.0, wy = 0.0;//壁ベクトル(wx, wy) ...

//        if (detectWall(x + _dx, y + _dy, x + _dx + REACH_FOR_WALL * cos(theta), y + _dy + REACH_FOR_WALL * sin(theta)) != 0) {
        if (detectWall(x + REACH_FOR_WALL * cos(theta), y + REACH_FOR_WALL * sin(theta), x + dx + REACH_FOR_WALL * cos(theta), y + dy + REACH_FOR_WALL * sin(theta)) != 0) {
            System.out.println("hasUpdate");

            //速度ベクトルとの内積が正になるような壁ベクトルを選ぶ
            wx = cos(theta + 90.0);
            wy = sin(theta + 90.0);
            if (wx * dx + wy * dy < 0) {
                wx = -wx;
                wy = -wy;
            }

            dx = (dx * wx + dy * wy) * wx;
            dy = (dx * wx + dy * wy) * wy;

            return true;
        }

        return false;
    }

    private int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection(x1, y1, x2, y2);
    }

    public double getPlayerWorldX() {
        return x;
    }

    public double getPlayerWorldY() {
        return y;
    }

//    public double getChaseWorldX() {
//        return chase_x[chase_num];
//    }
//
//    public double getChaseWorldY() {
//        return chase_y[chase_num];
//    }

}