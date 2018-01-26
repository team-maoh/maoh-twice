package com.maohx2.ina.Text;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2018/01/14.
 */

public class BoxTextPlate extends BoxPlate {

    String text;
    Paint text_paint;
    Paint button_paint;

    public BoxTextPlate(Graphic _graphic, UserInterface _user_interface, Paint _button_paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way, int[] position, String _text, Paint _text_paint){
        super(_graphic, _user_interface, _judge_way, _feedback_way, position[0], position[1], position[2], position[3]);

        button_paint = new Paint();
        button_paint.set(_button_paint);
        text = _text;
        text_paint = new Paint();
        text_paint.set(_text_paint);
    }

    @Override
    public void update(){
        super.update();
        button_paint.setAlpha(alpha);
    }

    @Override
    public void draw(){
        //TODO:テキスト中央ぞろえ
        graphic.bookingDrawRect(left, up, right, down, button_paint);
        graphic.bookingDrawText(text, left, up + (down - up)*3/4, text_paint);
    }
}