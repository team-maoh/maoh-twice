package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2018/01/26.
 */

public class BoxItemPlate extends BoxPlate {

    protected Paint text_paint;
    protected Paint button_paint;
    protected ItemData content_item;
    protected ImageContext image_context;

    public BoxItemPlate(Graphic _graphic, UserInterface _user_interface, Paint _button_paint, TouchWay _judge_way, TouchWay _feedback_way, int[] position, ItemData _content_item){
        super(_graphic, _user_interface, _judge_way, _feedback_way, position[0], position[1], position[2], position[3]);
        button_paint = new Paint();
        button_paint.set(_button_paint);
        text_paint = new Paint();
        text_paint.setARGB(255,0,255,255);
        text_paint.setTextSize(28);
        this.setContentItem(_content_item);
        //content_item = _content_item;
        //image_context = graphic.makeImageContext(content_item.getItemImage(), position[0], position[1], 1.7f, 1.7f, 0, 255, true);
    }

    @Override
    public void update() {
        if (update_flag == false){
            return;
        }

        if (user_interface.checkUI(touch_id, judge_way) == true) {
            button_paint.setAlpha(255);
            callBackEvent();
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
        if (content_item != null) {
            graphic.bookingDrawText(content_item.getName(), left + (int) (content_item.getItemImage().getWidth() * 1.7 + (int) ((down - up) * (1.0 / 5))), (int) (down - (down - up) * (1.0 / 5)), text_paint);
            //graphic.bookingDrawText(content_item.getName(), left+(int)(content_item.getItemImage().getWidth()*1.7), 100, text_paint);
            graphic.bookingDrawBitmapData(image_context);
        }
    }

    @Override
    public ItemData getContentItem(){
        return content_item;
    }

    @Override
    public void setContentItem(ItemData _content_item){
        content_item = _content_item;
        if (content_item != null) {
            image_context = graphic.makeImageContext(content_item.getItemImage(), left, up, 1.7f, 1.7f, 0, 255, true);
        }
    }

    @Override
    public void release() {
        System.out.println("takanoRelease : BoxItemPlate");
        super.release();
        text_paint = null;
        button_paint = null;
        image_context = null;

    }
}
