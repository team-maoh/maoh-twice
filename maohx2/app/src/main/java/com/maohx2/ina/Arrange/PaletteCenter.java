package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;

import static com.maohx2.ina.Constants.Palette.CIRCLE_COLOR;
import static com.maohx2.ina.Constants.Palette.PALETTE_CENTER_RADIUS_BIG;
import static com.maohx2.ina.Constants.Palette.PALETTE_CENTER_RADIUS_SMALL;

/**
 * Created by ina on 2018/02/04.
 */

public class PaletteCenter {


    Paint paint;
    int x,y;
    int element_num;
    static Graphic graphic;

    public PaletteCenter(int _x, int _y){
        x = _x;
        y = _y;

        element_num = 0;
        paint = new Paint();
        paint.setColor(CIRCLE_COLOR[element_num]);
    }

    public void changeElement(int _element_num){

        element_num = _element_num;
        paint.setColor(CIRCLE_COLOR[element_num]);
    }

    public static void initStatic(Graphic _graphic){
        graphic = _graphic;
    }

    public void drawSmall(){
        graphic.bookingDrawCircle(x,y,PALETTE_CENTER_RADIUS_SMALL ,paint);
    }

    public void drawBig(){
        graphic.bookingDrawCircle(x,y,PALETTE_CENTER_RADIUS_BIG, paint);
    }
}
