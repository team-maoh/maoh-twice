package com.maohx2.ina.Arrange;

import com.maohx2.ina.UI.BattleUserInterface;

/**
 * Created by ina on 2017/11/10.
 */

public class PaletteAdmin {

    Palette palette;

    public PaletteAdmin(BattleUserInterface _battle_user_interface){

        palette = new Palette(_battle_user_interface);
    }

    public void update(){

        palette.update();
    }

    public void draw(){

        palette.draw();
    }

}
