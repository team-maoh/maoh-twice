package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Palette.CIRCLE_COLOR;
import static com.maohx2.ina.Constants.Palette.PALETTE_CENTER_RADIUS_BIG;
import static com.maohx2.ina.Constants.Palette.PALETTE_CENTER_RADIUS_SMALL;

/**
 * Created by ina on 2018/02/04.
 */

public class PaletteCenter extends PaletteElement{


    static Graphic graphic;
    int prePos;

    int offset_x = -25;
    int offset_y = 50;
    int offset_equip = -20;

    public PaletteCenter(int _x, int _y, int _element_num, int _touch_id, SoundAdmin _soundAdmin){
        super( _x, _y, _element_num, _touch_id, _soundAdmin);

    }

    public void changeElement(int _element_num){

        element_num = _element_num;
        paint.setColor(CIRCLE_COLOR[element_num]);
        paint.setAlpha(255);
    }

    public static void initStatic(Graphic _graphic){
        graphic = _graphic;
    }

    @Override
    public void drawSmall() {

        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_SMALL, paint);
        graphic.bookingDrawBitmapData(graphic.searchBitmap("kazariwaku9"),x,y,0.1f,0.1f,0,254,false);
    }

    @Override
    public void drawSmallAndItem() {

        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_SMALL, paint);
        if(item_data != null) {
            //graphic.bookingDrawBitmapData(item_data.getItemImage(), x, y);
            graphic.bookingDrawBitmapData(item_data.getItemImage(),x,y+offset_equip,2.0f,2.0f,0,254,false);
            if(item_data.getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) {
                if(item_data.getName().equals("素手") == false) {
                    graphic.bookingDrawText(String.valueOf(((EquipmentItemData) (item_data)).getDungeonUseNum()), x + offset_x, y + offset_y, textPaint);
                }
            }
        }
        graphic.bookingDrawBitmapData(graphic.searchBitmap("kazariwaku9"),x,y,0.1f,0.1f,0,254,false);
    }


    @Override
    public void drawBig(){
        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG, paint);

        //by kmhanko
        if (item_data.getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) {
            graphic.bookingDrawText(String.valueOf(((EquipmentItemData) (item_data)).getDungeonUseNum()), x + offset_x, y + offset_y, textPaint);
        }
    }

    @Override
    public void setItemData(ItemData _item_data, int preElementNum, boolean isSound){
        super.setItemData(_item_data,preElementNum, isSound);
        prePos = preElementNum;
    }


    @Override
    public void drawBigAndItem(){
        graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG, paint);
        if(item_data != null) {
            //graphic.bookingDrawBitmapData(item_data.getItemImage(), x, y);
            graphic.bookingDrawBitmapData(item_data.getItemImage(),x,y+offset_equip,2.0f,2.0f,0,254,false);
            if(item_data.getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) {
                if(item_data.getName().equals("素手") == false) {
                    graphic.bookingDrawText(String.valueOf(((EquipmentItemData) (item_data)).getDungeonUseNum()), x + offset_x, y + offset_y, textPaint);
                }
            }
        }
        graphic.bookingDrawBitmapData(graphic.searchBitmap("kazariwaku9"),x,y,0.2f,0.2f,0,254,false);
    }

    @Override
    public void drawBig(int _select_circle_num) {
        if(_select_circle_num == element_num) {
            graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG + 10, paint);
        }else{
            graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG, paint);
        }
        graphic.bookingDrawBitmapData(graphic.searchBitmap("kazariwaku9"),x,y,0.2f,0.2f,0,254,false);
    }

    @Override
    public void drawBigAndItem(int _select_circle_num){
        if(_select_circle_num == element_num) {
            graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG+10, paint);
            if (item_data != null) {
                graphic.bookingDrawBitmapData(item_data.getItemImage(), x, y+offset_equip, 2.0f, 2.0f, 0, 254, false);
                if(item_data.getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) {
                    if(item_data.getName().equals("素手") == false) {
                        graphic.bookingDrawText(String.valueOf(((EquipmentItemData) (item_data)).getDungeonUseNum()), x + offset_x, y + offset_y, textPaint);
                    }
                }
            }
        }else{
            graphic.bookingDrawCircle(x, y, PALETTE_CENTER_RADIUS_BIG, paint);
            if (item_data != null) {
                graphic.bookingDrawBitmapData(item_data.getItemImage(), x, y+offset_equip, 2.0f, 2.0f, 0, 254, false);
                if(item_data.getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) {
                    if(item_data.getName().equals("素手") == false) {
                        graphic.bookingDrawText(String.valueOf(((EquipmentItemData) (item_data)).getDungeonUseNum()), x + offset_x, y + offset_y, textPaint);
                    }
                }
            }
        }
        graphic.bookingDrawBitmapData(graphic.searchBitmap("kazariwaku9"),x,y,0.2f,0.2f,0,254,false);
    }



    public int getPrePos(){return prePos;}

    public void release() {
        System.out.println("takanoRelease : PaletteCenter");
    }

}
