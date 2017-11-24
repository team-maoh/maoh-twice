package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.maohx2.ina.Constants.Draw.DRAW_TASK_KIND;

/**
 * Created by ina on 2017/11/19.
 */

public abstract class BookingTaskData{

    Paint paint;

    BookingTaskData(){
        paint = new Paint();
    }

    public abstract void draw(Canvas canvas);

}