package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapPlayer extends MapUnit {

    int ENCOUNT_STEPS;
    int total_steps;

    public void init(SurfaceHolder _holder, Bitmap _draw_object) {
        super.init(_holder, _draw_object);

        total_steps = 0;//アプリ起動から現在までの総歩数
        ENCOUNT_STEPS = 100;//この歩数ごとにエンカウントする

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
/*
        if(touch_state == 1){
            System.out.println("move_frame:" + move_frame);
        }
*/
        if (moving == true) {
            x += dx;
            y += dy;
            now_steps++;
            total_steps++;

            if(now_steps == dst_steps) {
                now_steps = 0;
                moving = false;
                System.out.println("N="+dst_steps);//N=1のとき、到着すると画像が消える
            }
            if(total_steps % ENCOUNT_STEPS == 0){
                System.out.println("敵と遭遇");
            }
        }
    }


}