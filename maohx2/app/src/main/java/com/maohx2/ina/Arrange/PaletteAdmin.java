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
import com.maohx2.kmhanko.sound.SoundAdmin;

import java.util.List;

import static com.maohx2.ina.Constants.Inventry.INVENTRY_DATA_MAX;
import static com.maohx2.ina.Constants.Palette.PALETTE_DEFAULT_X_LEFT;
import static com.maohx2.ina.Constants.Palette.PALETTE_DEFAULT_Y_LEFT;
import static com.maohx2.ina.Constants.Palette.PALETTE_DEFAULT_X_RIGHT;
import static com.maohx2.ina.Constants.Palette.PALETTE_DEFAULT_Y_RIGHT;

/**
 * Created by ina on 2017/11/10.
 */

public class PaletteAdmin {

    Palette palettes[] = new Palette[3]; // [0] = 武器パレット, [1] = 消費アイテムパレット, [2] = ジオ採掘パレット

    //by kmhanko
    boolean palettesFlag[] = new boolean[palettes.length]; //palettesより下に配置すること
    EquipmentItemDataAdmin equipmentItemDataAdmin;

    InventryS expendInventry;
    Graphic graphic;

    /*
    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic){
        palettes[0] = new Palette(_battle_user_interface, _graphic, 1400,750,0);
        palettes[1] = new Palette(_battle_user_interface, _graphic,  200,750,1);
        //palettes[0].palette_elements[0].setItemData(EquipmentItemDataAdmin.getDebugItem());
        //by kmhanko ジオ採掘パレット
        palettes[2] = new Palette(_battle_user_interface, _graphic,  1400,750,2);
    }
*/
    public PaletteAdmin(BattleUserInterface _battle_user_interface, Graphic _graphic, InventryS equipmentInventry, InventryS _expendInventry, EquipmentItemDataAdmin _equipmentItemDataAdmin, SoundAdmin _soundAdmin){
        palettes[0] = new Palette(_battle_user_interface, _graphic, PALETTE_DEFAULT_X_RIGHT,PALETTE_DEFAULT_Y_RIGHT,0, _soundAdmin);
        palettes[1] = new Palette(_battle_user_interface, _graphic, PALETTE_DEFAULT_X_LEFT,PALETTE_DEFAULT_Y_LEFT,1, _soundAdmin);
        //by kmhanko ジオ採掘パレット
        palettes[2] = new Palette(_battle_user_interface, _graphic, PALETTE_DEFAULT_X_RIGHT,PALETTE_DEFAULT_Y_RIGHT,2, _soundAdmin);

        expendInventry = _expendInventry;
        graphic = _graphic;

        for(int i = 0; i < INVENTRY_DATA_MAX; i++){
            EquipmentItemData checkEquipmentItem = ((EquipmentItemData)(equipmentInventry.getItemData(i)));
            if(checkEquipmentItem != null) {
                if (checkEquipmentItem.getPalettePosition() != 0) {
                    palettes[0].palette_elements[checkEquipmentItem.getPalettePosition()-1].setItemData(checkEquipmentItem, false);
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
                            palettes[1].palette_elements[j].setItemData(checkExpendItem, false);
                        }
                        boolCheck *= 2;
                    }
                }
            }

            palettesFlag[0] = true;
            palettesFlag[1] = true;
            palettesFlag[2] = false;

        }

        palettes[0].palette_elements[7].setItemData(_equipmentItemDataAdmin.getOneDataByName("素手"), false);
        palettes[0].palette_elements[6].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ盾"), false);

//        palettes[0].palette_elements[0].setItemData(_equipmentItemDataAdmin.getOneDataByName("素手"), false);
//        palettes[0].palette_elements[1].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ剣"), false);
//        palettes[0].palette_elements[2].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ杖"), false);
//        palettes[0].palette_elements[3].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ斧"), false);
//        palettes[0].palette_elements[4].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ槍"), false);
//        palettes[0].palette_elements[5].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ弓"), false);
//        palettes[0].palette_elements[6].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグ銃"), false);
//        palettes[0].palette_elements[7].setItemData(_equipmentItemDataAdmin.getOneDataByName("デバッグナックル"), false);
    }


    /*
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
    */

    public void setDebugEquipment(ItemData _itemData, int position){
        palettes[0].setDebugEquipment(_itemData, position);
    }

    public void setDebugExpend(ItemData _itemData, int position){
        palettes[1].setDebugExpend(_itemData, position);
    }

    //by kmhanko
    public void setPalletPosition(int id, int x, int y) {
        palettes[id].setPosition(x, y);
    }

    //by kmhanko
    public void setPalletPosition(int id) {
        if (id == 0 || id == 2) {
            palettes[id].setPosition(PALETTE_DEFAULT_X_RIGHT, PALETTE_DEFAULT_Y_RIGHT);
        } else {
            palettes[id].setPosition(PALETTE_DEFAULT_X_LEFT, PALETTE_DEFAULT_Y_LEFT);
        }
    }

    public void setPalletPosition() {
        palettes[0].setPosition(PALETTE_DEFAULT_X_RIGHT, PALETTE_DEFAULT_Y_RIGHT);
        palettes[1].setPosition(PALETTE_DEFAULT_X_LEFT, PALETTE_DEFAULT_Y_LEFT);
        palettes[2].setPosition(PALETTE_DEFAULT_X_RIGHT, PALETTE_DEFAULT_Y_RIGHT);
    }

    public void setMiningItems(MiningItemDataAdmin miningItemDataAdmin) {
        //by kmhanko 採掘パレットへ道具を格納
        List<MiningItemData> tmpItemData = miningItemDataAdmin.getItemDatas();
        for(int j = 0; j < 5; j++) {// まだ5種類しか用意して居ないため
            palettes[2].palette_elements[j].setItemData(tmpItemData.get(j), false);
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

        if (palettesFlag[2]) {
            //強さゲージの表示
            graphic.bookingDrawBitmapName(
                    "miningArrow",
                    palettes[2].getPositionX(),
                    palettes[2].getPositionY() - 100,
                    1.35f, 1.35f, 0.0f,
                    255, false
            );
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
        int prePos = palettes[1].getPalettePrePos();
        ItemData itemdata = palettes[1].getSelectedItemData();
        ExpendItemData expendItemData = (ExpendItemData)itemdata;
        expendItemData.setPalettePosition(prePos,false);
        //回りくどいが、inventryのitemdataを確実に変更したいため
        ExpendItemData expendItemData2 = (ExpendItemData)(expendInventry.searchInventryData(itemdata).getItemData());
        expendItemData2.setPalettePosition(prePos,false);
        expendInventry.subItemData(itemdata);

        palettes[1].setPaletteCenter(null, false);
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

    public Palette getPalettes(int i) {
        return palettes[i];
    }

    public void resetDungeonUseNum() {
        for(int i = 0; i < 8; i++) {
            if(palettes[0].getItemData(i) != null) {
                ((EquipmentItemData) (palettes[0].getItemData(i))).setDungeonUseNum(((EquipmentItemData) (palettes[0].getItemData(i))).getUseNum());
            }
        }
    }

    public void release() {
        System.out.println("takanoRelease : PaletteAdmin");
        if (palettes != null) {
            for (int i = 0; i < palettes.length; i++) {
                if (palettes[i] != null) {
                    palettes[i].release();
                }
            }
            palettes = null;
        }
        palettesFlag = null;

    }

    public PaletteCenter getPaletteCenter(int i) {
        return palettes[i].getPaletteCenter();
    }

}
