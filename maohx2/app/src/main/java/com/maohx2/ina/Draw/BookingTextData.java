package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by ina on 2017/11/19.
 */

public class BookingTextData extends BookingTaskData {

//canvas.drawText(content[i], up_left.x, up_left.y + content_height * (i + 1) - content_height/4, paint);

    String draw_string;
    int draw_left;
    int draw_up;

    BookingTextData(){
        super();
        draw_string = "";
        draw_left = 0;
        draw_up = 0;
    }

    public void update(String _draw_string, int _draw_left, int _draw_up, Paint _paint){
        draw_string = _draw_string;
        draw_left = _draw_left;
        draw_up = _draw_up;
        paint = _paint;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawText(draw_string, draw_left, draw_up, paint);
    }

}
