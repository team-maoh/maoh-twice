package com.maohx2.kmhanko.plate;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.WindowPlate.WindowTextPlate;

/**
 * Created by user on 2019/01/18.
 */

public class BoxImageTextPlate extends BoxPlate {
    static final String DEFAULT_BUTTON_NAME = "baseButton00";
    static final String FEED_DEFAULT_BUTTON_NAME = "baseButton01";

    WindowTextPlate button;
    WindowTextPlate touchedButton;

    protected boolean touched;

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
                DEFAULT_BUTTON_NAME,
                FEED_DEFAULT_BUTTON_NAME
        );
    }

    public BoxImageTextPlate(
            Graphic _graphic,
            UserInterface _user_interface,
            Constants.Touch.TouchWay _judge_way,
            Constants.Touch.TouchWay _feedback_way,
            int[] _position
    ) {
        this(
                _graphic,
                _user_interface,
                _judge_way,
                _feedback_way,
                _position,
                DEFAULT_BUTTON_NAME,
                FEED_DEFAULT_BUTTON_NAME
        );
    }

    public BoxImageTextPlate(
            Graphic _graphic,
            UserInterface _user_interface,
            Constants.Touch.TouchWay _judge_way,
            Constants.Touch.TouchWay _feedback_way,
            int[] _position,
            String _buttomImageName,
            String _feedButtomImageName
    ) {
        super(
                _graphic,
                _user_interface,
                _judge_way,
                _feedback_way,
                _position[0], _position[1], _position[2], _position[3]);

        button = new WindowTextPlate(graphic, _position, _buttomImageName);
        touchedButton = new WindowTextPlate(graphic, _position, _feedButtomImageName);

        button.setAlpha(192);
        touchedButton.setAlpha(192);

        button.setDrawFlag(true);
        touchedButton.setDrawFlag(true);

        touched = false;
    }

    public BoxImageTextPlate(
            Graphic _graphic,
            UserInterface _user_interface,
            Constants.Touch.TouchWay _judge_way,
            Constants.Touch.TouchWay _feedback_way,
            int[] _position,
            String _text, Paint _text_paint,
            String _buttomImageName,
            String _feedButtomImageName
    ) {
        this(_graphic, _user_interface, _judge_way, _feedback_way, _position, _buttomImageName, _feedButtomImageName);
        setText(0, _text, _text_paint, WindowTextPlate.TextPosition.CENTER);
    }

    public BoxImageTextPlate(
            Graphic _graphic,
            UserInterface _user_interface,
            Constants.Touch.TouchWay _judge_way,
            Constants.Touch.TouchWay _feedback_way,
            int[] _position,
            String[] _text, Paint[] _text_paint, WindowTextPlate.TextPosition[] _textPosition,
            String _buttomImageName,
            String _feedButtomImageName
    ) {
        this(_graphic, _user_interface, _judge_way, _feedback_way, _position, _buttomImageName, _feedButtomImageName);
        for (int i = 0; i < _text.length; i++) {
            setText(i, _text[i], _text_paint[i], _textPosition[i]);
        }
    }

    public BoxImageTextPlate(
            Graphic _graphic,
            UserInterface _user_interface,
            Constants.Touch.TouchWay _judge_way,
            Constants.Touch.TouchWay _feedback_way,
            int[] _position,
            String[] _text, Paint[] _text_paint, WindowTextPlate.TextPosition[] _textPosition
    ) {
        this(_graphic, _user_interface, _judge_way, _feedback_way, _position, DEFAULT_BUTTON_NAME, FEED_DEFAULT_BUTTON_NAME);
        for (int i = 0; i < _text.length; i++) {
            setText(i, _text[i], _text_paint[i], _textPosition[i]);
        }
    }


    public void setText(int id, String _text, Paint _text_paint, WindowTextPlate.TextPosition _textPosition) {
        button.setText(id, _text, _text_paint, _textPosition);
        touchedButton.setText(id, _text, _text_paint, _textPosition);
    }

    @Override
    public void update() {
        if (update_flag == false){
            return;
        }
        if (user_interface.checkUI(touch_id, judge_way) == true) {
            touched = true;
            callBackEvent();
        } else if (user_interface.checkUI(touch_id, feedback_way) == true) {
            touched = true;
        } else {
            touched = false;
        }
    }

    @Override
    public void draw() {
        if (draw_flag == false) {
            return;
        }
        if (touched) {
            touchedButton.draw();
        } else {
            button.draw();
        }
    }


}
