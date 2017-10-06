package com.maohx2.ina.Text;


import android.graphics.Canvas;

import com.maohx2.ina.UI.BattleUserInterface;

/**
 * Created by ina on 2017/10/01.
 */

public class TextBoxAdmin {

    TextBox text_box[] = new TextBox[100];
    BattleUserInterface battle_user_interface;

    public void init(BattleUserInterface _battle_user_interface) {
        battle_user_interface = _battle_user_interface;
        text_box[0] = new TextBox();

        text_box[0].init(battle_user_interface.setBoxTouchUI(100.0, 550.0, 800.0, 750.0));
    }


    public void update() {
        text_box[0].update(battle_user_interface.checkUI(text_box[0].getTouchID()));
    }

    public void draw(Canvas canvas) {
        text_box[0].draw(canvas);
    }

}
