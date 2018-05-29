package com.maohx2.ina.Arrange;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;

/**
 * Created by ina on 2017/11/10.
 */

public class PaletteBattleAdmin {

    Palette palettes[] = new Palette[2];

    public PaletteBattleAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic, Inventry _inventry){
        //palettes[0] = new Palette(_battle_user_interface, _graphic,1000,600,0);
        //palettes[1] = new Palette(_battle_user_interface, _graphic,200,600,1);
    }

    public void update(){
        palettes[0].update();
        palettes[1].update();
    }

    public void draw(){
        palettes[0].draw();
        palettes[1].draw();
    }
}
