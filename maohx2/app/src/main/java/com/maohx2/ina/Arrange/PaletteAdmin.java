package com.maohx2.ina.Arrange;

        import com.maohx2.ina.Draw.Graphic;
        import com.maohx2.ina.ItemData.EquipmentInventrySaver;
        import com.maohx2.ina.ItemData.EquipmentItemData;
        import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
        import com.maohx2.ina.UI.BattleUserInterface;
        import com.maohx2.kmhanko.Arrange.InventryS;
        import com.maohx2.kmhanko.itemdata.ExpendItemData;

        import static com.maohx2.ina.Constants.Inventry.INVENTRY_DATA_MAX;

/**
 * Created by ina on 2017/11/10.
 */

public class PaletteAdmin {

    Palette palettes[] = new Palette[2];
    EquipmentItemDataAdmin equipmentItemDataAdmin;

    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic){
        palettes[0] = new Palette(_battle_user_interface, _graphic, 1400,750,0);
        palettes[1] = new Palette(_battle_user_interface, _graphic,  200,750,1);
        //palettes[0].palette_elements[0].setItemData(EquipmentItemDataAdmin.getDebugItem());
    }

    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic, InventryS equipmentInventry, InventryS expendInventry){
        palettes[0] = new Palette(_battle_user_interface, _graphic, 1400,750,0);
        palettes[1] = new Palette(_battle_user_interface, _graphic,  200,750,1);

        for(int i = 0; i < INVENTRY_DATA_MAX; i++){
            EquipmentItemData checkEquipmentItem = ((EquipmentItemData)(equipmentInventry.getItemData(i)));
            if(checkEquipmentItem != null) {
                if (checkEquipmentItem.getPalettePosition() != 0) {
                    palettes[0].palette_elements[checkEquipmentItem.getPalettePosition()-1].setItemData(checkEquipmentItem);
                }
            }
        }


        for(int i = 0; i < INVENTRY_DATA_MAX; i++){
            ExpendItemData checkExpendItem = ((ExpendItemData)(expendInventry.getItemData(i)));
            if(checkExpendItem != null) {
                if (checkExpendItem.getPalettePosition() != 0) {
                    int boolCheck = 1;
                    for(int j = 0; j < 8; j++) {
                        if((checkExpendItem.getPalettePosition()&boolCheck) != 0) {
                            palettes[1].palette_elements[j].setItemData(checkExpendItem);
                        }
                        boolCheck *= 2;
                    }
                }
            }
        }

        /*
        palettes[0].palette_elements[0].setItemData(equipmentItemDataAdmin.getOneDataByName("素手"));
        palettes[0].palette_elements[1].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ剣"));
        palettes[0].palette_elements[2].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ杖"));
        palettes[0].palette_elements[3].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ斧"));
        palettes[0].palette_elements[4].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ槍"));
        palettes[0].palette_elements[5].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ弓"));
        palettes[0].palette_elements[6].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ銃"));
        palettes[0].palette_elements[7].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグナックル"));
        */
    }


    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic, EquipmentItemDataAdmin equipmentItemDataAdmin){
        palettes[0] = new Palette(_battle_user_interface, _graphic, 1400,750,0);
        palettes[1] = new Palette(_battle_user_interface, _graphic,  200,750,1);
        palettes[0].palette_elements[0].setItemData(equipmentItemDataAdmin.getOneDataByName("素手"));
        palettes[0].palette_elements[1].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ剣"));
        palettes[0].palette_elements[2].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ杖"));
        palettes[0].palette_elements[3].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ斧"));
        palettes[0].palette_elements[4].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ槍"));
        palettes[0].palette_elements[5].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ弓"));
        palettes[0].palette_elements[6].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ銃"));
        palettes[0].palette_elements[7].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグナックル"));
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
