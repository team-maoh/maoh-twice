package com.maohx2.ina.Text;


import android.graphics.Canvas;

import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2017/10/01.
 */

public class ListBoxAdmin {

    ListBox list_box[] = new ListBox[100];
    UserInterface user_interface;

    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;
        list_box[0] = new ListBox();

        list_box[0].init(_user_interface);
    }


    public void update() {

        list_box[0].update();
    }

    public void draw(Canvas canvas) {
        list_box[0].draw(canvas);
    }


}
