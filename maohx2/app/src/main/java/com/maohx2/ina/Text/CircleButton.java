package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2018/01/14.
 */

public class CircleButton extends Button{

    int x,y,radius;

    CircleButton(Graphic _graphic, Paint _paint, TouchWay _judge_way, TouchWay _feedback_way, int _x, int _y, int _radius){
        super(_graphic, _paint, _judge_way, _feedback_way);
        x = _x;
        y = _y;
        radius = _radius;

        touch_id = user_interface.setCircleTouchUI(x, y, radius);
    }

    public void draw(){
        graphic.bookingDrawCircle(x, y, radius, button_paint);
    }
}