package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapPlayer extends MapUnit {

    int ENCOUNT_STEPS;
    int total_steps;
    int STEP;

    @Override
    public void init(SurfaceHolder holder, Bitmap draw_object, MapObjectAdmin map_object_admin) {
        super.init(holder, draw_object, map_object_admin);

        total_steps = 0;//アプリ起動から現在までの総歩数
        ENCOUNT_STEPS = 100;//この歩数ごとにエンカウントする
        STEP = 20;//歩幅( (dx)^2 + (dy)^2 = (STEP)^2 )
        x = 500;
        y = 500;
    }

    @Override
    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin map_object_admin) {
        super.init(gl, draw_object, map_object_admin);

        total_steps = 0;//アプリ起動から現在までの総歩数
        ENCOUNT_STEPS = 100;//この歩数ごとにエンカウントする
        STEP = 20;//歩幅( (dx)^2 + (dy)^2 = (STEP)^2 )
        x = 500;
        y = 500;
    }

    @Override
    public void update(double touch_x, double touch_y, int touch_state) {

        if (touch_state == 0 || touch_state == 1) {
            dst_steps = (int)(Math.pow( Math.pow(touch_x - x, 2.0) + Math.pow(touch_y - y, 2.0) , 0.5) / (double)STEP);
            dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
            dx = (int) ((touch_x - x) / dst_steps);
            dy = (int) ((touch_y - y) / dst_steps);
            moving = true;
            now_steps = 0;
        }
        if (moving == true) {
            x += dx;
            y += dy;
            now_steps++;
            total_steps++;

            if(now_steps == dst_steps) {
                now_steps = 0;
                moving = false;
            }
            if(total_steps % ENCOUNT_STEPS == 0){
                System.out.println("一定歩数歩いたので敵と遭遇");
            }
        }
    }
}