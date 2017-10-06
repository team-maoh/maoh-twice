package com.maohx2.ina.Battle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

/**
 * Created by ina on 2017/09/20.
 */

public class LayerAdmin implements Runnable {

    RelativeLayout layout;

  //  BaseSurfaceView[] layer = new BaseSurfaceView[11];
    Thread thread;
    int x;


    public void init(Context context) {
        //TouchSV touch = new TouchSV(context);
        //BaseSurfaceView[] circle = new BaseSurfaceView[10];

        /*
        for (int i = 0; i < 10; i++)
            circle[i] = new BaseSurfaceView(context);
        */

        //layer[0] = touch;
        /*
        for (int i = 1; i < 11; i++){
            layer[i] = circle[i - 1];
        }
*/
        //x= 0;

    }

/*
    public SurfaceView getLayer(int layer_num) {

        return layer[layer_num];
    }

    public void setLayer(int layer_num, BaseSurfaceView add_layer) {

        layer[layer_num] = add_layer;
    }

*/

    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }

    public void run() {

/*
        while (true) {

            //System.out.println("check created");
            boolean all_created_check = true;


            for (int i = 0; i < 8; i++) {
                if (layer[i].isCreated() == false) {
                    all_created_check = false;
                }
            }

            if (all_created_check == true) {
                break;
            }
        }


        System.out.println("success created!!");


        Canvas canvas;
        Paint paint = new Paint();
        paint.setColor(Color.RED);



        while(thread != null) {
            x++;




            for(int i = 1; i < 4; i++) {

                canvas = null;
                //描画をロックして、すぐ描画されないようにする
                SurfaceHolder draw_holder = layer[i].getHolder();
                canvas = draw_holder.lockCanvas();

                //キャンバスの内容をクリアする。処理としては、透明色で上書きしている。
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                //円を描画
                canvas.drawCircle(x, i*100, 100, paint);

                //描画ロックを解除する。この時点で今まで描いたものが一斉に描かれる。予約みたいなもん。
                draw_holder.unlockCanvasAndPost(canvas);

            }




        }
*/
    }


    public RelativeLayout getLayout() {

        return layout;
    }


    LayerAdmin(Context context) {
        x = 0;

//        layout.addView(layer[0]);

    }

}


