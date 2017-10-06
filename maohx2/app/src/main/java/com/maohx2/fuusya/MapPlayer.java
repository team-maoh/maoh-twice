package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.waste.MySprite;
//import com.maohx2.ina.MySprite;
import static com.maohx2.ina.Constants.Touch.TouchState;


import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapPlayer extends MapUnit {

    int ENCOUNT_STEPS;
    int total_steps, STEP;
    MapAdmin map_admin;
    boolean kari = true;

    @Override
    public void init(SurfaceHolder holder, Bitmap draw_object, MapObjectAdmin map_object_admin, MapAdmin _map_admin) {
        super.init(holder, draw_object, map_object_admin);

        map_admin = _map_admin;//追加

        total_steps = 0;//アプリ起動から現在までの総歩数
        ENCOUNT_STEPS = 300;//この歩数ごとにエンカウントする
        STEP = 10;//歩幅( (dx)^2 + (dy)^2 = (STEP)^2 )
//        x = 900;
//        y = 1600;
        x = 10;
        y = 10;
    }

    @Override
    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin map_object_admin) {
        super.init(gl, draw_object, map_object_admin);

        total_steps = 0;//アプリ起動から現在までの総歩数
        ENCOUNT_STEPS = 300;//この歩数ごとにエンカウントする
        STEP = 10;//歩幅( (dx)^2 + (dy)^2 = (STEP)^2 )
        x = 10;
        y = 10;
    }

    @Override
    public void update(double touch_x, double touch_y, TouchState touch_state) {

        //仮
        /*
        if (kari == true) {
            x = +map_admin.getCameraOffset_x();
            y = +map_admin.getCameraOffset_y();
            kari = false;
        }
        */

        if (touch_state == TouchState.DOWN || touch_state == TouchState.DOWN_MOVE || touch_state == TouchState.MOVE) {
            dst_steps = (int) myDistance(touch_x, touch_y, x, y) / STEP;
            dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
            dx = ((touch_x - x) / dst_steps);
            dy = ((touch_y - y) / dst_steps);

            x_reach = Math.signum(dx) * REACH_FOR_WALL;
            y_reach = Math.signum(dy) * REACH_FOR_WALL;

            moving = true;
//            now_steps = 0;
        }
        if (moving == true) {

            switch (detectWall(x, y, x + dx + x_reach, y + dy + y_reach)) {
                case 0://壁なし
                    x += dx;
                    y += dy;
                    break;
                case 1://水平
                    if (detectWall(x, y, x + dx + x_reach, y + dy) != 2) {
                        x += dx;
                    }
                    break;
                case 2://垂直
                    if (detectWall(x, y, x + dx, y + dy + y_reach) != 1) {
                        y += dy;
                    }
                    break;
                default:
                    break;
            }
            /*
            x = +map_admin.getCameraOffset_x();
            y = +map_admin.getCameraOffset_y();
            */

                        /*
            //自分と壁との距離を外積で求めようとした
            double start_angle;
            if (dy < 0) {
                start_angle = atan(dy / dx);
            } else {
                start_angle = atan(dy / dx) + 180;
            }
            boolean isWall = false, pre_isWall;
            double border_theta[] = new double[2];
            int i = 0;
            for (double theta = start_angle; theta < start_angle + 360; theta += 3) {
                pre_isWall = isWall;
                if (detectWall(x + dx, y + dy, x + dx + REACH_FOR_WALL * cos(theta), y + dy + REACH_FOR_WALL * sin(theta)) != 0) {
                    isWall = true;
                } else {
                    isWall = false;
                }
                if (pre_isWall != isWall) {
                    border_theta[i] = theta;
                    i++;
                }
                if (i == 2) {
                    break;
                }
            }
            double sink = REACH_FOR_WALL - (pow(REACH_FOR_WALL, 2.0) * (cos(border_theta[0] * sin(border_theta[1]) - sin(border_theta[0] * cos(border_theta[1])))));
            dx += sink*cos((border_theta[0]+border_theta[1])/2.0);
            dy += sink*sin((border_theta[0]+border_theta[1])/2.0);
            */

                        /*
            for (double theta = 0.0; theta < 360.0; theta += 3.0) {
                if (hasUpdateDxDy(theta) == true) {
                    break;
                }
            }
            x += dx;
            y += dy;
            */

            System.out.println("dx" + dx);
            System.out.println("dy" + dy);

            //自分と目標点の距離がSTEP未満になる or 次ステップで自分のx(またはy)座標が目標点のそれを通り過ぎる
            if (STEP > myDistance(touch_x, touch_y, x, y) || 0 > (touch_x - (x + dx)) * dx || 0 > (touch_y - (y + dy)) * dy) {
                moving = false;
            }

            total_steps++;
            if (total_steps % ENCOUNT_STEPS == 0) {
                System.out.println("一定歩数歩いたので敵と遭遇");
            }

        }
    }

/*
    void defineDxDy(int now_x, int now_y, int dst_x, int dst_y) {

        dst_steps = myDistance(dst_x, dst_y, now_x, now_y) / STEP;
        dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
        dx = ((dst_x - now_x) / dst_steps);
        dy = ((dst_y - now_y) / dst_steps);

    }
    */

    //(x1, y1)と(x2, y2)の距離を返す
    public double myDistance(double x1, double y1, double x2, double y2) {
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

            dx = (dx * wx + dy + wy) * wx;
            dy = (dx * wx + dy + wy) * wy;

            return true;
        }

        return false;
    }

    private int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection( x1, y1, x2, y2);
    }

    public double getPlayerWorldX(){
        return x;
    }

    public double getPlayerWorldY(){
        return y;
    }

}