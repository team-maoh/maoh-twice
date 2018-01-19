package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2018/01/14.
 */

public class BoxButton extends Button{

    int left, up, right, down;

    BoxButton(Graphic _graphic, Paint _paint, TouchWay _judge_way, TouchWay _feedback_way, int _left, int _up, int _right, int _down){
        super(_graphic, _paint, _judge_way, _feedback_way);
        left = _left;
        up = _up;
        right = _right;
        down = _down;

        touch_id = user_interface.setBoxTouchUI(left, up, right, down);
    }

    public void draw(){
        graphic.bookingDrawRect(left, up, right, down, button_paint);
    }
}