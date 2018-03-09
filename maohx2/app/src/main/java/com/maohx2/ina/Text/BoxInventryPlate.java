package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;

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
                image_context = graphic.makeImageContext(inventry_data.getItemData().getItemImage(), left, up, 1.7f, 1.7f, 0, 255, true);
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
    public void draw() {
        if (draw_flag == false){
            return;
        }


            graphic.bookingDrawRect(left, up, right, down, button_paint);
        if(inventry_data != null) {
            if(inventry_data.getItemData() != null) {
                graphic.bookingDrawText(inventry_data.getItemData().getName(), left + (int) (inventry_data.getItemData().getItemImage().getWidth() * 1.7 + (int) ((down - up) * (1.0 / 5))), (int) (down - (down - up) * (1.0 / 5)), text_paint);
                graphic.bookingDrawText(String.valueOf(inventry_data.getItemNum()), left + (int) (inventry_data.getItemData().getItemImage().getWidth() * 1.7 + (int) ((down - up) * (1.0 / 5))) + 270, (int) (down - (down - up) * (1.0 / 5)), text_paint);
                //graphic.bookingDrawText(content_item.getName(), left+(int)(content_item.getItemImage().getWidth()*1.7), 100, text_paint);
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
            image_context = graphic.makeImageContext(inventry_data.getItemData().getItemImage(), left, up, 1.7f, 1.7f, 0, 255, true);
        }
    }

    @Override
    public void changeInventryData(){
        image_context = graphic.makeImageContext(inventry_data.getItemData().getItemImage(), left, up, 1.7f, 1.7f, 0, 255, true);
    }

}