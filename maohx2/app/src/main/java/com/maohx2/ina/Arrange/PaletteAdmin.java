package com.maohx2.ina.Arrange;

        import com.maohx2.ina.Draw.Graphic;
        import com.maohx2.ina.ItemData.EquipmentItemData;
        import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
        import com.maohx2.ina.UI.BattleUserInterface;

/**
 * Created by ina on 2017/11/10.
 */

public class PaletteAdmin {

    Palette palettes[] = new Palette[2];

    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic){
        palettes[0] = new Palette(_battle_user_interface, _graphic, 1400,750);
        palettes[1] = new Palette(_battle_user_interface, _graphic,  200,750);
        palettes[0].palette_elements[0].setItemData(EquipmentItemDataAdmin.getDebugItem());
    }

    public void update(boolean battle_flag){

        if(battle_flag == true){
            palettes[0].update();
            palettes[1].update();
        } else {
            palettes[0].updateSetting();
            palettes[1].updateSetting();
        }
    }

    public void draw(){
        palettes[0].draw();
        palettes[1].draw();
    }

    //パレットを押せる状態かどうか
    public boolean doUsePalette(){

        for(int i = 0; i < 2; i++) {
            if (palettes[i].getPaletteMode() != 0) {
                return true;
            }
        }
        return false;
    }

    public EquipmentItemData getEquipmentItemData(){
        return (EquipmentItemData) palettes[0].getSelectedItemData();
    }

}
