package com.maohx2.ina.Arrange;

import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2017/11/10.
 */

public class Palette {

    private BattleUserInterface battle_user_interface;
    private PaletteElement[] palette_element = new PaletteElement[8];

    public Palette(BattleUserInterface _battle_user_interface){
        battle_user_interface = _battle_user_interface;
    }

    public void update(){

        battle_user_interface.update();
    }

    public void draw(){

        battle_user_interface.drawBattlePalette();

    }

}
