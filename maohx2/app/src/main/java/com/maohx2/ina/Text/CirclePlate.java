package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2018/01/14.
 */

abstract public class CirclePlate extends Plate {

    protected int x,y,radius;

    CirclePlate(Graphic _graphic, UserInterface _user_interface, TouchWay _judge_way, TouchWay _feedback_way, int _x, int _y, int _radius){
        super(_graphic, _user_interface, _judge_way, _feedback_way);
        x = _x;
        y = _y;
        radius = _radius;

        touch_id = user_interface.setCircleTouchUI(x, y, radius);
    }

    abstract public void draw();

    public void releaseTouchID() {
        user_interface.resetCircleUI(touch_id);
    }

}