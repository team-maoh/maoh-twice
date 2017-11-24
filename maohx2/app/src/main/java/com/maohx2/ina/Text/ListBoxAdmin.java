package com.maohx2.ina.Text;


import android.graphics.Canvas;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.Constants.Touch.TouchWay;

/**
 * Created by ina on 2017/10/01.
 */

public class ListBoxAdmin {

    ListBox list_box[] = new ListBox[100];
    UserInterface user_interface;

    public void init(UserInterface _user_interface, Graphic graphic) {
        user_interface = _user_interface;
        list_box[0] = new ListBox();
        list_box[0].init(_user_interface, graphic, TouchWay.UP_MOMENT ,7, 850, 100, 1100, 400);
        list_box[0].setContent(0,"オラ");
        list_box[0].setContent(1,"オラオラ");
        list_box[0].setContent(2,"オラオラオラ");
        list_box[0].setContent(3,"オラオラオラオラ");
        list_box[0].setContent(4,"オラオラオラオラオラ");
        list_box[0].setContent(5,"オラオラオラオラオラオラ");
    }

    public void update() {list_box[0].update();}

    public void draw(Canvas canvas) {list_box[0].draw(canvas);}
}
