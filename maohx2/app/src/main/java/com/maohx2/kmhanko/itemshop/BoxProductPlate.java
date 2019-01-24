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
    static final int PRICE_PLECE = 300;
    int price;
    int width;
    int height;

    BitmapData[][] flameElement;

    int position[];

    public BoxProductPlate(Graphic _graphic, UserInterface _user_interface, Paint _button_paint, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way, int[] _position, ItemData _content_item, int _price){
        super(_graphic, _user_interface, _button_paint, _judge_way, _feedback_way, _position, _content_item);

        //大きさ変更
        image_context = graphic.makeImageContext(content_item.getItemImage(), _position[0] + 10, _position[1] + 10, ICON_RATE, ICON_RATE, 0, 255, true);
        text_paint.setTextSize(FONT_SIZE);

        price = _price;

        position = _position;

        flameElement = new BitmapData[3][3];

        width = graphic.searchBitmap("ウィンドウ5_枠のみ").getWidth();
        height = graphic.searchBitmap("ウィンドウ5_枠のみ").getHeight();

        for(int i = 0; i <3; i++) {
            for(int j = 0; j < 3; j++) {
                flameElement[i][j] = graphic.processTrimmingBitmapData(graphic.searchBitmap("ウィンドウ5_枠のみ"), (int)((double)j*(double)width/3.0), (int)((double)i*(double)height/3.0), (int)((double)width/3.0), (int)((double)height/3.0));
            }
        }
    }

    @Override
    public void draw() {
        //価格の表示を追加

        int iconWidth = (int)(content_item.getItemImage().getWidth() * ICON_RATE);
        int iconHeight = (int)(content_item.getItemImage().getHeight() * ICON_RATE);
        int boxWidth = right - left;
        int boxheight = down - up;

        graphic.bookingDrawRect(left, up, right, down, button_paint);

        graphic.bookingDrawBitmapData(flameElement[0][0], position[0], position[1], 1.0f, 1.0f, 0, 255, true);
        graphic.bookingDrawBitmapData(flameElement[1][0], position[0], position[1] + height / 3, 1.0f, ((float)(position[3]-position[1]-(int)((double)height*2.0/3.0)))/((float)height/3.0f), 0, 255, true);
        graphic.bookingDrawBitmapData(flameElement[2][0], position[0], position[3] - height / 3, 1.0f, 1.0f, 0, 255, true);

        graphic.bookingDrawBitmapData(flameElement[0][1], position[0] + width / 3, position[1], ((float)(position[2]-position[0]-(int)((double)width*2.0/3.0)))/((float)width/3.0f), 1.0f, 0, 255, true);
        graphic.bookingDrawBitmapData(flameElement[2][1], position[0] + width / 3, position[3] - height / 3, ((float)(position[2]-position[0]-(int)((double)width*2.0/3.0)))/((float)width/3.0f), 1.0f, 0, 255, true);

        graphic.bookingDrawBitmapData(flameElement[0][2], position[2] - width / 3, position[1], 1.0f, 1.0f, 0, 255, true);
        graphic.bookingDrawBitmapData(flameElement[1][2], position[2] - width / 3, position[1] + height / 3, 1.0f, ((float)(position[3]-position[1]-(int)((double)height*2.0/3.0)))/((float)height/3.0f), 0, 255, true);
        graphic.bookingDrawBitmapData(flameElement[2][2], position[2] - width / 3, position[3] - height / 3, 1.0f, 1.0f, 0, 255, true);



        graphic.bookingDrawText(
                content_item.getName(),
                left + iconWidth + (int)(boxheight - FONT_SIZE) / 2,
                up + (int)(boxheight + FONT_SIZE) / 2,
                text_paint
        );

        graphic.bookingDrawText(
                price + " Maon",
                right - PRICE_PLECE,
                up + (int)(boxheight + FONT_SIZE) / 2,
                text_paint
        );

        graphic.bookingDrawBitmapData(image_context);
    }


}
