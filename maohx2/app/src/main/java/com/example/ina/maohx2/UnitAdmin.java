package com.example.ina.maohx2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/*
 * Created by ina on 2017/09/05.
 */

public class UnitAdmin {

    double x, y;
    int dx, dy, N;
    int move_frame;


    Paint paint = new Paint();
    SurfaceHolder holder;
    Bitmap neco;
    boolean moving;

    public void Init(SurfaceHolder m_holder, Bitmap m_neco) {
        holder = m_holder;
        neco = m_neco;

        x = 0;
        y = 0;
        dx = 0;
        dy = 0;
        N = 100;
        move_frame = 0;
        moving = false;
    }


    public void Update(double touch_x, double touch_y, int touch_state) {


        if (touch_state == 0) {
            dx = (int) ((touch_x - x) / N);
            dy = (int) ((touch_y - y) / N);
            moving = true;
            move_frame = 0;
        }

        if(touch_state == 1){

            System.out.println("move_frame:" + move_frame);

        }


            /*
            try {

                dx = (int) ((next_x - x) / N);
                dy = (int) ((next_y - y) / N);

                for (int i = 0; i < N; i++) {
                    if (reset == true) {
                        //System.out.println(x);
                        break;
                    }

                  //System.out.println(x);
                  //System.out.println(y);
                    //キャンバスに図形を描画
                    x += dx;
                    y += dy;
                }
            } finally {
                //moving = false;
                //reset = false;
            }

        }
        */




        if (moving == true) {
            x += dx;
            y += dy;
            move_frame++;

            if(move_frame == N) {
                move_frame = 0;
                moving = false;
            }
        }


    }

    public void Draw(double touch_x, double touch_y, int touch_state) {
        Canvas canvas = null;

        canvas = holder.lockCanvas(null);
        synchronized (holder) {
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(neco, (int) x, (int) y, paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }

}
