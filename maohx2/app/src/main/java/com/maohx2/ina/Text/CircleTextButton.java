package com.maohx2.ina.Text;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2018/01/14.
 */

public class CircleTextButton extends CircleButton{

    String text;
    Paint text_paint;

    CircleTextButton(Graphic _graphic, Paint _button_paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way, int[] position, String _text, Paint _text_paint){
        super(_graphic, _button_paint, _judge_way, _feedback_way, position[0], position[1], position[2]);

        text = _text;
        text_paint = new Paint();
        text_paint.set(_text_paint);

    }

    @Override
    public void draw() {
        super.draw();
        button_paint.setColor(Color.argb(255, 0, 0, 0));
        //TODO:テキスト中央ぞろえ
        //graphic.bookingDrawText(content, left, up + (down - up)*3/4, paint);
    }
}
