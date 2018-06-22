package com.maohx2.ina.Draw;

import android.graphics.Paint;

/**
 * Created by ina on 2018/06/12.
 */

public class Credit {

    Graphic graphic;
    String text;
    int position_x;
    int position_y;

    Paint paint;
    boolean endFlag;

    public Credit(Graphic _graphic, Paint _paint, String _text, int _position_x, int _position_y){
        graphic = _graphic;
        paint = new Paint();
        paint.set(_paint);

        text = _text;
        position_x = _position_x;
        position_y = _position_y;

        endFlag = false;
    }

    public void update(){
        if(endFlag == false) {
            if (position_y >= -100) {
                position_y -= 5;
            }else{
                endFlag = true;
            }
        }
    }

    public void draw(){
        if(endFlag == false) {
            if (position_y >= -100) {
                graphic.bookingDrawText(text, position_x, position_y, paint);
            }
        }
    }

    public boolean endCheck(){
        return endFlag;
    }
}
