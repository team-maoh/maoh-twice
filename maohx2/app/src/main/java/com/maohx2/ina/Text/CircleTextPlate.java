package com.maohx2.ina.Text;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2018/01/14.
 */

public class CircleTextPlate extends CirclePlate {

    protected String text;
    protected Paint text_paint;
    protected Paint button_paint;

    CircleTextPlate(Graphic _graphic, UserInterface _user_interface, Paint _button_paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way, int[] position, String _text, Paint _text_paint){
        super(_graphic, _user_interface, _judge_way, _feedback_way, position[0], position[1], position[2]);

        button_paint = new Paint();
        button_paint.set(_button_paint);
        text = _text;
        text_paint = new Paint();
        text_paint.set(_text_paint);

    }

    @Override
    public void update(){
        if (update_flag == false){
            return;
        }
        super.update();
        button_paint.setAlpha(alpha);
    }

    @Override
    public void draw() {
        if (draw_flag == false){
            return;
        }
        graphic.bookingDrawCircle(x, y, radius, button_paint);
        button_paint.setColor(Color.argb(255, 0, 0, 0));
        //TODO:テキスト中央ぞろえ
        //graphic.bookingDrawText(content, left, up + (down - up)*3/4, paint);
    }
}
