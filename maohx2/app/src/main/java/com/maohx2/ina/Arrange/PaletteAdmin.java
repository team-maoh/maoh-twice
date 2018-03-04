package com.maohx2.ina.Arrange;

        import com.maohx2.ina.Draw.Graphic;
        import com.maohx2.ina.UI.BattleUserInterface;

/**
 * Created by ina on 2017/11/10.
 */

public class PaletteAdmin {

    Palette palettes[] = new Palette[2];

    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic, Inventry _inventry){
        palettes[0] = new Palette(_battle_user_interface, _graphic, _inventry,1000,600);
        palettes[1] = new Palette(_battle_user_interface, _graphic, _inventry, 200,600);
    }

    public void update(){
        palettes[0].updateSetting();
        palettes[1].updateSetting();
    }

    public void draw(){
        palettes[0].draw();
        palettes[1].draw();
    }
}
