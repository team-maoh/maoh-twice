package com.maohx2.ina;

import android.graphics.PointF;

/**
 * Created by ina on 2017/11/05.
 */

public final class GlobalConstants {

    public final int DISP_X;
    public final int DISP_Y;
    public final int NORMARIZED_X = 1600;
    public final int NORMARIZED_Y = 900;
    public final PointF NORMARIZED_DISP_RATE;
    public final PointF DISP_NORMARIZED_RATE;
    public final PointF NORMARIZED_DRAW_RATE;
    //public final PointF DISP_NORMARIZED_RATE;
    public final float DENSITY;
    public final int DRAW_LEFT_END;
    public final int DRAW_UP_END;
    public final int DRAW_RIGHT_END;
    public final int DRAW_DOWN_END;

    public GlobalConstants(int disp_x, int disp_y){
        DISP_X = disp_x;
        DISP_Y = disp_y;

        NORMARIZED_DISP_RATE = new PointF((float)DISP_X/1600, (float)DISP_Y/900);
        DISP_NORMARIZED_RATE = new PointF((float)1600/DISP_X, (float)900/DISP_Y);

        DENSITY = Math.min(NORMARIZED_DISP_RATE.x, NORMARIZED_DISP_RATE.y);
        NORMARIZED_DRAW_RATE = new PointF((float)(DENSITY*0.98), ((float)(DENSITY*0.98)));

/*
        //16:9よりも縦長で、正方形などを考えるとよい
        // NORMARIZED_DISP_RATE.x=1.2,NORMARIZED_DISP_RATE.y=2.1などを考える。
        if(NORMARIZED_DISP_RATE.x < NORMARIZED_DISP_RATE.y){
            //NORMARIZED_DRAW_RATE = new PointF((float)((NORMARIZED_X*NORMARIZED_DISP_RATE.x*0.98)/1600), ((float)(NORMARIZED_Y*NORMARIZED_DISP_RATE.x*0.98))/900);
            NORMARIZED_DRAW_RATE = new PointF((float)(NORMARIZED_DISP_RATE.x*0.98), ((float)(NORMARIZED_DISP_RATE.x*0.98)));
        } else{
            NORMARIZED_DRAW_RATE = new PointF(((float)(NORMARIZED_DISP_RATE.y*0.98)), ((float)(NORMARIZED_DISP_RATE.y*0.98)));
        }
*/

        int vartical_margin = 0;
        int horizontal_margin = 0;

        // NORMARIZED_DISP_RATE.x=1.2,NORMARIZED_DISP_RATE.y=2.1などを考える。
        vartical_margin = (int) ((DISP_Y - NORMARIZED_Y*DENSITY*0.98)/2);
        horizontal_margin = (int) ((DISP_X - NORMARIZED_X*DENSITY*0.98)/2);


        DRAW_LEFT_END = horizontal_margin;
        DRAW_UP_END = vartical_margin;
        DRAW_RIGHT_END = DISP_X - horizontal_margin;
        DRAW_DOWN_END = DISP_Y - vartical_margin;

    }
}
