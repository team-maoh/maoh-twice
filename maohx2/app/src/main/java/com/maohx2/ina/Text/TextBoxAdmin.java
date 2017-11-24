package com.maohx2.ina.Text;


import android.graphics.Canvas;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2017/10/01.
 */

public class TextBoxAdmin {

    TextBox text_box[] = new TextBox[100];
    UserInterface user_interface;

    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;
        text_box[0] = new TextBox();

        text_box[0].init(user_interface.setBoxTouchUI(100.0, 550.0, 800.0, 750.0));
    }


    public void update() {
        text_box[0].update(user_interface.checkUI(text_box[0].getTouchID(), Constants.Touch.TouchWay.MOVE));
    }

    public void draw(Canvas canvas) {text_box[0].draw(canvas);}

}
