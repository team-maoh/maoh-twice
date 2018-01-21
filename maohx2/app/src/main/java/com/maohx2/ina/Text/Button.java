package com.maohx2.ina.Text;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;

import com.maohx2.ina.Constants.Touch.TouchState;
import com.maohx2.ina.Constants.Touch.TouchWay;
/**
 * Created by ina on 2018/01/12.
 */

public abstract class Button {

    Constants.Touch.TouchWay judge_way;
    Constants.Touch.TouchWay feedback_way;
    int touch_id;
    int alpha;
    UserInterface user_interface;
    Graphic graphic;
    Paint button_paint;

    Button(Graphic _graphic, Paint _paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way){
        graphic = _graphic;
        button_paint = new Paint();
        button_paint.set(_paint);
        judge_way = _judge_way;
        feedback_way = _feedback_way;

        alpha = 255;
    }

    abstract public void draw();

    public boolean checkTouchContent(){

        if(user_interface.checkUI(touch_id, judge_way) == true) {
            return true;
        }
        return false;
    }


    public void update(){

            if (user_interface.checkUI(touch_id, feedback_way) == true) {
                alpha = 255;
            } else {
                alpha = 100;
            }

        button_paint.setAlpha(alpha);
    }

    public void setPaint(Paint _paint){button_paint.set(_paint);}

}