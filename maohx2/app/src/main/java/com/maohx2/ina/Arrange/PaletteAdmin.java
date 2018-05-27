package com.maohx2.ina.Arrange;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.kmhanko.itemdata.MiningItemData;
import com.maohx2.kmhanko.itemdata.MiningItemDataAdmin;
import java.util.List;

import static com.maohx2.ina.Constants.Inventry.INVENTRY_DATA_MAX;

/**
 * Created by ina on 2017/11/10.
 */

public class PaletteAdmin {

    Palette palettes[] = new Palette[3]; // [0] = 武器パレット, [1] = 消費アイテムパレット, [2] = ジオ採掘パレット

    //by kmhanko
    boolean palettesFlag[] = new boolean[palettes.length]; //palettesより下に配置すること
    EquipmentItemDataAdmin equipmentItemDataAdmin;

    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic){
        palettes[0] = new Palette(_battle_user_interface, _graphic, 1400,750,0);
        palettes[1] = new Palette(_battle_user_interface, _graphic,  200,750,1);
        //palettes[0].palette_elements[0].setItemData(EquipmentItemDataAdmin.getDebugItem());
        //by kmhanko ジオ採掘パレット
        palettes[2] = new Palette(_battle_user_interface, _graphic,  1400,750,2);
    }

    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic, InventryS equipmentInventry, InventryS expendInventry, EquipmentItemDataAdmin _equipmentItemDataAdmin){
        palettes[0] = new Palette(_battle_user_interface, _graphic, 1400,750,0);
        palettes[1] = new Palette(_battle_user_interface, _graphic,  200,750,1);
        //by kmhanko ジオ採掘パレット
        palettes[2] = new Palette(_battle_user_interface, _graphic,  1400,750,2);
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

            palettesFlag[0] = true;
            palettesFlag[1] = true;
            palettesFlag[2] = false;

        }

        palettes[0].palette_elements[7].setItemData(_equipmentItemDataAdmin.getOneDataByName("素手"));
        palettes[0].palette_elements[6].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ盾"));



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
        //by kmhanko ジオ採掘パレット
        palettes[2] = new Palette(_battle_user_interface, _graphic,  1400,750,2);
        palettes[0].palette_elements[0].setItemData(equipmentItemDataAdmin.getOneDataByName("素手"));
        palettes[0].palette_elements[1].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ剣"));
        palettes[0].palette_elements[2].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ杖"));
        palettes[0].palette_elements[3].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ斧"));
        palettes[0].palette_elements[4].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ槍"));
        palettes[0].palette_elements[5].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ弓"));
        palettes[0].palette_elements[6].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグ銃"));
        palettes[0].palette_elements[7].setItemData(equipmentItemDataAdmin.getOneDataByName("デバッグナックル"));

    }

    public void setMiningItems(MiningItemDataAdmin miningItemDataAdmin) {
        //by kmhanko 採掘パレットへ道具を格納
        List<MiningItemData> tmpItemData = miningItemDataAdmin.getItemDatas();
        for(int j = 0; j < 5; j++) {// まだ5種類しか用意して居ないため
            palettes[2].palette_elements[j].setItemData(tmpItemData.get(j));
        }
    }

    public void update(boolean battle_flag){

        if(battle_flag == true){
            /*
            palettes[0].update();
            palettes[1].update();
            */
            for (int i = 0; i < palettes.length; i++) {
                if (palettesFlag[i]) { palettes[i].update(); }
            }
        } else {
            /*
            palettes[0].updateSetting();
            palettes[1].updateSetting();
            */
            for (int i = 0; i < palettes.length; i++) {
                if (palettesFlag[i]) { palettes[i].updateSetting(); }
            }
        }
    }

    public void draw(){
        for (int i = 0; i < palettes.length; i++) {
            if (palettesFlag[i]) { palettes[i].draw(); }
        }
    }

    public void drawOnly(){
        for (int i = 0; i < palettes.length; i++) {
            if (palettesFlag[i]) { palettes[i].drawOnly(); }
        }
    }

    //パレットを押せる状態かどうか
    public boolean doUsePalette(){

        /*
        for(int i = 0; i < 2; i++) {
            if (palettes[i].getPaletteMode() != 0) {
                return true;
            }
        }
        */

        //by kmhanko 2→palettes.length
        for(int i = 0; i < palettes.length; i++) {
            //by kmhank　フラグ確認追加
            if (palettes[i].getPaletteMode() != 0 && palettesFlag[i]) {
                return true;
            }
        }
        return false;
    }

    public EquipmentItemData getEquipmentItemData(){
        return (EquipmentItemData) palettes[0].getSelectedItemData();
    }

    public ExpendItemData checkSelectedExpendItemData(){
        //palettes[0].getPalettePrePos();
        return (ExpendItemData) palettes[1].getSelectedItemData();
    }

    //by kmhanko
    public MiningItemData getMiningItemData(){
        return (MiningItemData) palettes[2].getSelectedItemData();
    }

    public void deleteExpendItemData(){
        palettes[1].setPaletteCenter(null);
    }

    //by kmhanko
    // *** パレットのフラグ関係
    public void setPalettesFlags(boolean[] _flags) {
        palettesFlag = _flags;
    }
    public void setPalettesFlag(int i, boolean _flag) {
        palettesFlag[i] = _flag;
    }
    public boolean[] getPalettesFlags() {
        return palettesFlag;
    }
    public boolean getPalettesFlag(int i) {
        return palettesFlag[i];
    }

    public void resetDungeonUseNum() {
        for(int i = 0; i < 8; i++) {
            if(palettes[0].getItemData(i) != null) {
                ((EquipmentItemData) (palettes[0].getItemData(i))).setDungeonUseNum(((EquipmentItemData) (palettes[0].getItemData(i))).getUseNum());
            }
        }
    }

}
