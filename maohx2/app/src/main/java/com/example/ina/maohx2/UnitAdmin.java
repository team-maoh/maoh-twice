package com.example.ina.maohx2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import static java.lang.Math.abs;

/*
 * Created by ina on 2017/09/05.
 */

public class UnitAdmin {

    double x, y;
    int STEP, ENCOUNT_STEPS;
    int dx, dy, dst_steps, now_steps, total_steps;


    Paint paint = new Paint();
    SurfaceHolder holder;
    Bitmap neco;
    boolean moving;

    public void Init(SurfaceHolder m_holder, Bitmap m_neco) {
        holder = m_holder;
        neco = m_neco;

        x = 0;//現在座標
        y = 0;
        dx = 0;//移動距離(differential x)
        dy = 0;
        STEP = 20;//歩幅( (dx)^2 + (dy)^2 = (STEP)^2 )
        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        total_steps = 0;//アプリ起動から現在までの総歩数
        ENCOUNT_STEPS = 100;//この歩数ごとにエンカウントする
        moving = false;
    }

    public void Update(double touch_x, double touch_y, int touch_state) {

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
