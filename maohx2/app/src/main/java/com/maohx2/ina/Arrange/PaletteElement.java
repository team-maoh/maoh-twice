package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemData;

import static com.maohx2.ina.Constants.Palette.CIRCLE_COLOR;
import static com.maohx2.ina.Constants.Palette.PALETTE_ELEMENT_RADIUS_BIG;
import static com.maohx2.ina.Constants.Palette.PALETTE_ELEMENT_RADIUS_SMALL;

/**
 * Created by ina on 2018/01/28.
 */

public class PaletteElement {

    Paint paint;
    int x,y;
    int element_num;
    static Graphic graphic;
    int touch_id;
    ItemData item_data;

    public PaletteElement(int _x, int _y, int _element_num, int _touch_id){
        x = _x;
        y = _y;
        element_num = _element_num;
        touch_id = _touch_id;
        paint = new Paint();
        if(element_num == 0) {
            paint.setColor(CIRCLE_COLOR[0]);
        }else {
            paint.setColor(CIRCLE_COLOR[element_num - 1]);
        }
    }

    public static void initStatic(Graphic _graphic){
        graphic = _graphic;
    }

    public void drawSmall(){
        graphic.bookingDrawCircle(x, y, PALETTE_ELEMENT_RADIUS_SMALL, paint);
    }


    public void drawSmallAndItem() {
        graphic.bookingDrawCircle(x, y, PALETTE_ELEMENT_RADIUS_SMALL, paint);
        if(item_data != null){
            graphic.bookingDrawBitmapData(item_data.getItemImage(),x,y,1,1,0,255,false);
        }
    }

    public void drawBig() {
        graphic.bookingDrawCircle(x, y, PALETTE_ELEMENT_RADIUS_BIG, paint);
    }

    public void drawBigAndItem(){
        graphic.bookingDrawCircle(x, y, PALETTE_ELEMENT_RADIUS_BIG, paint);
        if(item_data != null) {
            graphic.bookingDrawBitmapData(item_data.getItemImage(),x,y,1.5f,1.5f,0,255,false);
        }
    }


    public void setItemData(ItemData _item_data){
        item_data = _item_data;
        if(item_data.getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT){
            ((EquipmentItemData)(item_data)).setPalettePosition(element_num);
        }
    }

    public ItemData getItemData(){return item_data;}
    public int getTouchID(){return touch_id;}
    public int getElementNum(){return element_num;}

}