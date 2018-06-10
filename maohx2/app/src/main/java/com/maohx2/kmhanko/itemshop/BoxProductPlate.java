package com.maohx2.kmhanko.itemshop;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Text.BoxItemPlate;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by user on 2018/02/02.
 */

//TODO Inventryにする
public class BoxProductPlate extends BoxItemPlate {

    static final float ICON_RATE = 2.0f;
    static final int FONT_SIZE = 40;
    static final int PRICE_PLECE = 200;

    public BoxProductPlate(Graphic _graphic, UserInterface _user_interface, Paint _button_paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way, int[] position, ItemData _content_item){
        super(_graphic, _user_interface, _button_paint, _judge_way, _feedback_way, position, _content_item);

        //大きさ変更
        image_context = graphic.makeImageContext(content_item.getItemImage(), position[0] + 10, position[1] + 10, ICON_RATE, ICON_RATE, 0, 255, true);
        text_paint.setTextSize(FONT_SIZE);
    }

    @Override
    public void draw() {
        //価格の表示を追加

        int iconWidth = (int)(content_item.getItemImage().getWidth() * ICON_RATE);
        int iconHeight = (int)(content_item.getItemImage().getHeight() * ICON_RATE);
        int boxWidth = right - left;
        int boxheight = down - up;

        graphic.bookingDrawRect(left, up, right, down, button_paint);
        graphic.bookingDrawText(
                content_item.getName(),
                left + iconWidth + (int)(boxheight - FONT_SIZE) / 2,
                up + (int)(boxheight + FONT_SIZE) / 2,
                text_paint
        );

        graphic.bookingDrawText(
                "¥ " + content_item.getPrice(),
                right - PRICE_PLECE,
                up + (int)(boxheight + FONT_SIZE) / 2,
                text_paint
        );

        graphic.bookingDrawBitmapData(image_context);
    }


}
