package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by ina on 2017/11/19.
 */

public class BookingRectData extends BookingTaskData{

//Rect rect = new Rect(up_left.x, up_left.y + content_height * i, down_right.x, up_left.y + content_height * (i + 1));
//canvas.drawRect(rect, paint);

    Rect rect;

    BookingRectData(){
        super();
        rect = new Rect(0,0,0,0);
    }

    public void update(int _draw_left, int _draw_up,int _draw_right, int _draw_down, Paint _paint){
        rect = new Rect(_draw_left, _draw_up, _draw_right, _draw_down);
        paint = _paint;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawRect(rect, paint);
    }

}
