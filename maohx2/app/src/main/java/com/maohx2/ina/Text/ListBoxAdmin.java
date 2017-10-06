package com.maohx2.ina.Text;


import android.graphics.Canvas;

import com.maohx2.ina.UI.BattleUserInterface;

/**
 * Created by ina on 2017/10/01.
 */

public class ListBoxAdmin {

    ListBox list_box[] = new ListBox[100];
    BattleUserInterface battle_user_interface;

    public void init(BattleUserInterface _battle_user_interface) {
        battle_user_interface = _battle_user_interface;
        list_box[0] = new ListBox();

        list_box[0].init(battle_user_interface);
    }


    public void update() {

        list_box[0].update();
    }

    public void draw(Canvas canvas) {
        list_box[0].draw(canvas);
    }


}
