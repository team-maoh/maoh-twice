package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;

import static com.maohx2.ina.Constants.Palette.CIRCLE_COLOR;
import static com.maohx2.ina.Constants.Palette.PALETTE_CENTER_RADIUS_BIG;
import static com.maohx2.ina.Constants.Palette.PALETTE_CENTER_RADIUS_SMALL;

/**
 * Created by ina on 2018/02/04.
 */

public class PaletteCenter extends PaletteElement{


    static Graphic graphic;

    public PaletteCenter(int _x, int _y, int _element_num, int _touch_id){
        super( _x, _y, _element_num, _touch_id);

    }

    public void changeElement(int _element_num){

        element_num = _element_num;
        paint.setColor(CIRCLE_COLOR[element_num]);
    }

    public static void initStatic(Graphic _graphic){
        graphic = _graphic;
    }

    @Override
    public void drawSmall() {

        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_SMALL, paint);
    }

    @Override
    public void drawSmallAndItem() {

        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_SMALL, paint);
        if(item_data != null) {
            graphic.bookingDrawBitmapData(item_data.getItemImage(), x, y);
        }
    }


    @Override
    public void drawBig(){
        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG, paint);
    }


    @Override
    public void drawBigAndItem(){
        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG, paint);
        if(item_data != null) {
            //graphic.bookingDrawBitmapData(item_data.getItemImage(), x, y);
            graphic.bookingDrawBitmapData(item_data.getItemImage(),x,y,2,2,0,255,false);
        }
    }

}
