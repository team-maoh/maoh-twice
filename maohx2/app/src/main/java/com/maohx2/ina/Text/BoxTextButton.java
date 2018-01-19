package com.maohx2.ina.Text;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2018/01/14.
 */

public class BoxTextButton extends BoxButton{

    String text;
    Paint text_paint;

    BoxTextButton(Graphic _graphic, Paint _paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way, int[] position, String _text, Paint _text_paint){
        super(_graphic, _paint, _judge_way, _feedback_way, position[0], position[1], position[2], position[3]);

        text = _text;
        text_paint = new Paint();
        text_paint.set(_text_paint);

    }

    @Override
    public void draw(){
        super.draw();
        //TODO:テキスト中央ぞろえ
        button_paint.setColor(Color.argb(255, 0, 0, 0));
        graphic.bookingDrawText(text, left, up + (down - up)*3/4, text_paint);
    }
}
