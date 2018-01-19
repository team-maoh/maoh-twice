package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2018/01/14.
 */

public class BoxImageButton extends BoxButton{

    BitmapData button_image;

    int draw_center_x, draw_center_y;

    BoxImageButton(Graphic _graphic, Paint _paint, TouchWay _judge_way, TouchWay _feedback_way, int[] position, BitmapData _button_image){
        super(_graphic, _paint, _judge_way, _feedback_way, position[0], position[1], position[2], position[3]);

        button_image = _button_image;

        draw_center_x = (position[0]+position[2])/2;
        draw_center_y = (position[1]+position[3])/2;
    }

    @Override
    public void draw() {
        super.draw();
        graphic.bookingDrawBitmapData(button_image, draw_center_x, draw_center_y);
    }
}
