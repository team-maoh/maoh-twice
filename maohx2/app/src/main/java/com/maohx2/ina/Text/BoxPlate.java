package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2018/01/14.
 */

abstract public class BoxPlate extends Plate {

    protected int left, up, right, down;

    public BoxPlate(Graphic _graphic, UserInterface _user_interface, TouchWay _judge_way, TouchWay _feedback_way, int _left, int _up, int _right, int _down){
        super(_graphic, _user_interface, _judge_way, _feedback_way);
        left = _left;
        up = _up;
        right = _right;
        down = _down;

        touch_id = user_interface.setBoxTouchUI(left, up, right, down);
    }

    abstract public void draw();

    public void releaseTouchID() {
        user_interface.resetBoxUI(touch_id);
    }

}