package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ina on 2017/11/19.
 */

public class BookingCircleData extends BookingTaskData{

//canvas.drawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 10.0f, paint);

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
        paint = _paint;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawCircle(draw_x, draw_y, radius, paint);
    }
}
