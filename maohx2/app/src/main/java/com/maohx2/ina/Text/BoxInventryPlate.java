package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.kmhanko.itemdata.GeoObjectData;

import static com.maohx2.ina.Constants.Palette.CIRCLE_COLOR;

/**
 * Created by ina on 2018/02/09.
 */

public class BoxInventryPlate extends BoxPlate {

    protected Paint text_paint;
    protected Paint button_paint;
    protected InventryData inventry_data;
    protected ImageContext image_context;

    public BoxInventryPlate(Graphic _graphic, UserInterface _user_interface, Paint _button_paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way, int[] position, InventryData _inventry_data){
        super(_graphic, _user_interface, _judge_way, _feedback_way, position[0], position[1], position[2], position[3]);
        button_paint = new Paint();
        button_paint.set(_button_paint);
        text_paint = new Paint();
        text_paint.setARGB(255,0,255,255);
        text_paint.setTextSize(28);
        inventry_data = _inventry_data;

        if(inventry_data != null) {
            if(inventry_data.getItemData() != null) {
                if(inventry_data.getItemData().getItemImage() !=null) {
                    image_context = graphic.makeImageContext(inventry_data.getItemData().getItemImage(), left + 50, (up + down) / 2, 1.7f, 1.7f, 0, 255, false);
                } else {
                    System.out.println("takano: BoxInventryPlate getItemImage == null");
                }
            }
        }
    }

    @Override
    public void update() {
        if (update_flag == false){
            return;
        }
        if (user_interface.checkUI(touch_id, judge_way) == true) {
            button_paint.setAlpha(255);
        } else if (user_interface.checkUI(touch_id, feedback_way) == true) {
            button_paint.setAlpha(255);
        } else {
            button_paint.setAlpha(100);
        }
    }

    @Override
    public void drawExceptEquip() {
        draw(false);
    }

    @Override
    public void draw() {
        draw(true);
    }

    public void draw(boolean equipFlag) {
        if (draw_flag == false){
            return;
        }
        graphic.bookingDrawRect(left, up, right, down, button_paint);

        if(inventry_data != null) {
            if(inventry_data.getItemData() != null) {
                text_paint.setARGB(255,0,255,255);
                switch(inventry_data.getItemData().getItemKind()){
                    case GEO:
                        graphic.bookingDrawText(inventry_data.getItemData().getName(), left + (int) (inventry_data.getItemData().getItemImage().getWidth() * 2.0 + (int) ((down - up) * (1.0 / 5))), up + (int) ((down - up + text_paint.getTextSize()) / 2), text_paint);
                        break;

                    case EQUIPMENT:
                        graphic.bookingDrawText(inventry_data.getItemData().getName()+" +"+String.valueOf(((EquipmentItemData)(inventry_data.getItemData())).getAttack()), left + (int) (inventry_data.getItemData().getItemImage().getWidth() * 2.0 + (int) ((down - up) * (1.0 / 5))), up + (int)((down - up + text_paint.getTextSize()) / 2), text_paint);
                        break;

                    case EXPEND:
                        graphic.bookingDrawText(inventry_data.getItemData().getName(), left + (int) (inventry_data.getItemData().getItemImage().getWidth() * 2.0 + (int) ((down - up) * (1.0 / 5))), up + (int)((down - up) / 2 + text_paint.getTextSize()), text_paint);
                        break;
                }

                if((inventry_data.getItemData().getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) || (inventry_data.getItemData().getItemKind() == Constants.Item.ITEM_KIND.GEO)) {
                    if((inventry_data.getItemData().getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT)) {
                        if(((EquipmentItemData)(inventry_data.getItemData())).getPalettePosition() != 0 && equipFlag) {
                            text_paint.setColor(CIRCLE_COLOR[((EquipmentItemData)(inventry_data.getItemData())).getPalettePosition()-1]);
                            text_paint.setAlpha(255);
                            graphic.bookingDrawText("E", left + (int) (inventry_data.getItemData().getItemImage().getWidth() * 1 + (int) ((down - up) * (1.0 / 5))) + 200, up + (int)((down - up + text_paint.getTextSize()) / 2), text_paint);
                        }
                    }else if(inventry_data.getItemData().getItemKind() == Constants.Item.ITEM_KIND.GEO){
                        if(((GeoObjectData)(inventry_data.getItemData())).getSlotSetName().equals("noSet") == false && equipFlag) {
                            text_paint.setARGB(255,255,255,255);
                            graphic.bookingDrawText("E", right - 50, up + (int)((down - up + text_paint.getTextSize()) / 2), text_paint);
                        }
                    }
                }else{
                    String itemText;
                    if (equipFlag) {
                        itemText = String.valueOf(inventry_data.getItemNum() - ((ExpendItemData)inventry_data.getItemData()).getPalettePositionNum());
                    } else {
                        itemText = String.valueOf(inventry_data.getItemNum());
                    }
                    graphic.bookingDrawText(itemText, left + (int) (inventry_data.getItemData().getItemImage().getWidth() + (int) ((down - up) * (1.0 / 5))) + 270, up + (int)((down - up + text_paint.getTextSize()) / 2), text_paint);
                    if(((ExpendItemData)(inventry_data.getItemData())).getPalettePosition() != 0 && equipFlag) {
                        for(int i = 0; i < ((ExpendItemData)(inventry_data.getItemData())).getPalettePositionNum(); i++) {
                            int offSet = i*30;
                            int palette_position = ((ExpendItemData)(inventry_data.getItemData())).getPalettePosition(i+1);
                            text_paint.setColor(CIRCLE_COLOR[palette_position]);
                            text_paint.setAlpha(255);
                            graphic.bookingDrawText("E", left + (int) (inventry_data.getItemData().getItemImage().getWidth() + (int) ((down - up) * (1.0 / 5))) + 50 + offSet, up + (int)((down - up) / 2), text_paint);
                        }
                    }
                }
                graphic.bookingDrawBitmapData(image_context);
            }
        }
    }

    @Override
    public InventryData getInventryData(){
        return inventry_data;
    }

    @Override
    public void setInventryData(InventryData _inventry_data){
        inventry_data = _inventry_data;

        if(inventry_data != null) {
            if(inventry_data.getItemData() != null) {
                image_context = graphic.makeImageContext(inventry_data.getItemData().getItemImage(), left+50, (up+down)/2, 1.7f, 1.7f, 0, 255, false);
            }
        }
    }

    @Override
    public void changeInventryData(){
        image_context = graphic.makeImageContext(inventry_data.getItemData().getItemImage(), left+50, (up+down)/2, 1.7f, 1.7f, 0, 255, false);
    }

    @Override
    public void release() {
        System.out.println("takanoRelease : BoxInventryPlate");

        super.release();
        text_paint = null;
        button_paint = null;
        image_context = null;

    }

}