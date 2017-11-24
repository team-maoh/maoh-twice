package com.maohx2.ina;

import android.graphics.PointF;

/**
 * Created by ina on 2017/11/05.
 */

public final class GlobalConstants {

    public final int DISP_X;
    public final int DISP_Y;
    public final PointF NORMARIZED_DISP_RATE;
    public final PointF DISP_NORMARIZED_RATE;
    public final float DENSITY;


    public GlobalConstants(int disp_x, int disp_y){
        DISP_X = disp_x;
        DISP_Y = disp_y;

        NORMARIZED_DISP_RATE = new PointF((float)DISP_X/1600, (float)DISP_Y/900);
        DISP_NORMARIZED_RATE = new PointF((float)1600/DISP_X, (float)900/DISP_Y);

        DENSITY = Math.min(NORMARIZED_DISP_RATE.x, NORMARIZED_DISP_RATE.y);
    }
}
