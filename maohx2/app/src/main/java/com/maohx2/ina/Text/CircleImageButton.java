package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2018/01/14.
 */

public class CircleImageButton extends CircleButton {

    BitmapData button_image;

    CircleImageButton(Graphic _graphic, Paint _paint, TouchWay _judge_way, TouchWay _feedback_way, int[] position, BitmapData _button_image) {
        super(_graphic, _paint, _judge_way, _feedback_way, position[0], position[1], position[2]);

        button_image = _button_image;
    }

    @Override
    public void draw() {
        super.draw();
        graphic.bookingDrawBitmapData(button_image, x, y);
    }
}

