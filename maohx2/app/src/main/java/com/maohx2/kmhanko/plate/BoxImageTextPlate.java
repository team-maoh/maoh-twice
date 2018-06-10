package com.maohx2.kmhanko.plate;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by user on 2018/06/10.
 */

public class BoxImageTextPlate extends BoxPlate {

    static final String DEFAULT_BUTTON_NAME = "baseButton";

    protected float width;
    protected float height;
    protected int[] position;
    protected BitmapData[][] buttonElement;

    protected String text;
    protected Paint text_paint;
    protected float textWidth;
    protected float textHeight;

    protected int arpla;

    protected int buttonImageWidth;
    protected int buttonImageHeight;

    protected String buttomImageName;

    //protected int[] buttonPositionX = new int[4];
    //protected int[] buttonPositionY = new int[4];

    public BoxImageTextPlate(
            Graphic _graphic,
            UserInterface _user_interface,
            Constants.Touch.TouchWay _judge_way,
            Constants.Touch.TouchWay _feedback_way,
            int[] _position,
            String _text, Paint _text_paint
    ) {
        this(
                _graphic,
                _user_interface,
                _judge_way,
                _feedback_way,
                _position,
                _text, _text_paint,
                DEFAULT_BUTTON_NAME
                );
    }

    public BoxImageTextPlate(
            Graphic _graphic,
            UserInterface _user_interface,
            Constants.Touch.TouchWay _judge_way,
            Constants.Touch.TouchWay _feedback_way,
            int[] _position,
            String _text, Paint _text_paint,
            String _buttomImageName
    ) {
        super(
                _graphic,
                _user_interface,
                _judge_way,
                _feedback_way,
                _position[0], _position[1], _position[2], _position[3]);

        position = _position;
        buttomImageName = _buttomImageName;
        width = position[2] - position[0];
        height = position[3] - position[1];

        text = _text;
        text_paint = new Paint();
        text_paint.set(_text_paint);
        textHeight = text_paint.getTextSize();
        textWidth = text_paint.measureText(text);

        arpla = 192;

        buttonElement = new BitmapData[3][3];

        buttonImageWidth = graphic.searchBitmap(buttomImageName).getWidth();
        buttonImageHeight = graphic.searchBitmap(buttomImageName).getHeight();

        for(int i = 0; i <3; i++) {
            for(int j = 0; j < 3; j++) {
                buttonElement[i][j] = graphic.processTrimmingBitmapData(graphic.searchBitmap(buttomImageName), (int)((double)j*(double)buttonImageWidth/3.0), (int)((double)i*(double)buttonImageHeight/3.0), (int)((double)buttonImageWidth/3.0), (int)((double)buttonImageHeight/3.0));
            }
        }

        /*
        buttonPositionX[0] = position[0];
        buttonPositionY[0] = position[1];

        buttonPositionX[1] = buttonPositionX[0] + width/3;
        buttonPositionY[1] = buttonPositionY[0] + height/3;

        buttonPositionX[3] = position[2];
        buttonPositionY[3] = position[3];
        */


    }

    @Override
    public void update() {
        if (update_flag == false){
            return;
        }
        if (user_interface.checkUI(touch_id, judge_way) == true) {
            arpla = 254;
            callBackEvent();
        } else if (user_interface.checkUI(touch_id, feedback_way) == true) {
            arpla = 254;
        } else {
            arpla = 192;
        }
    }

    @Override
    public void draw() {
        if (draw_flag == false){
            return;
        }

        graphic.bookingDrawBitmapData(buttonElement[0][0], position[0], position[1], 1.0f, 1.0f, 0, arpla, true);
        graphic.bookingDrawBitmapData(buttonElement[1][0], position[0], position[1] + buttonImageHeight / 3, 1.0f, (height - (buttonImageHeight * 2.0f/3.0f)) / (buttonImageHeight / 3.0f), 0, arpla, true);
        graphic.bookingDrawBitmapData(buttonElement[2][0], position[0], position[3] - buttonImageHeight / 3, 1.0f, 1.0f, 0, arpla, true);

        graphic.bookingDrawBitmapData(buttonElement[0][1], position[0] + buttonImageWidth / 3, position[1], (width-buttonImageWidth*2.0f/3.0f)/(buttonImageWidth/3.0f), 1.0f, 0, arpla, true);
        graphic.bookingDrawBitmapData(buttonElement[1][1], position[0] + buttonImageWidth / 3, position[1] + buttonImageHeight / 3, (width-(buttonImageWidth*2.0f/3.0f))/(buttonImageWidth/3.0f), (height - (buttonImageHeight * 2.0f/3.0f)) / (buttonImageHeight / 3.0f), 0, arpla, true);
        graphic.bookingDrawBitmapData(buttonElement[2][1], position[0] + buttonImageWidth / 3, position[3] - buttonImageHeight / 3, (width-(buttonImageWidth*2.0f/3.0f))/(buttonImageWidth/3.0f), 1.0f, 0, arpla, true);

        graphic.bookingDrawBitmapData(buttonElement[0][2], position[2] - buttonImageWidth / 3, position[1], 1.0f, 1.0f, 0, arpla, true);
        graphic.bookingDrawBitmapData(buttonElement[1][2], position[2] - buttonImageWidth / 3, position[1] + buttonImageHeight / 3, 1.0f, (height - (buttonImageHeight * 2.0f/3.0f)) / (buttonImageHeight / 3.0f), 0, arpla, true);
        graphic.bookingDrawBitmapData(buttonElement[2][2], position[2] - buttonImageWidth / 3, position[3] - buttonImageHeight / 3, 1.0f, 1.0f, 0, arpla, true);

        graphic.bookingDrawText(text,
                (int)(position[0] + (width - textWidth)/2.0f),
                (int)(position[1] + (height + textHeight)/2.0f),
                text_paint
        );
    }
}
