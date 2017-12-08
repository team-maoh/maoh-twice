package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ina on 2017/11/19.
 */

public class BookingCircleData extends BookingTaskData{

    int draw_x;
    int draw_y;
    int radius;

    BookingCircleData(){
        super();
        draw_x = 0;
        draw_y = 0;
        radius = 0;
    }

    public void update(int _draw_x, int _draw_y, int _radius, Paint _paint){
        draw_x = _draw_x;
        draw_y = _draw_y;
        radius = _radius;
        paint.set(_paint);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawCircle(draw_x, draw_y, radius, paint);
    }
}
