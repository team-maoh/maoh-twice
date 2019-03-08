package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Math.COS;
import static com.maohx2.ina.Constants.Math.SIN;
import static com.maohx2.ina.Constants.Palette.CIRCLE_COLOR;
import static com.maohx2.ina.Constants.Palette.PALETTE_ARRANGE_RADIUS;
import static com.maohx2.ina.Constants.Palette.PALETTE_CENTER_RADIUS_BIG;
import static com.maohx2.ina.Constants.Palette.PALETTE_ELEMENT_RADIUS_BIG;

/**
 * Created by ina on 2017/11/10.
 */

public class Palette {

    private BattleUserInterface battle_user_interface;

    int battle_palette_mode;
    double direction_section_check[] = new double[8];
    PaletteElement palette_elements[] = new PaletteElement[8];
    PaletteCenter palette_center;
    Paint paint;
    double direction_check;
    int select_circle_num;
    int selected_circle_num;
    Graphic graphic;
    int position_x;
    int position_y;
    int paletteNum;
    SoundAdmin soundAdmin;
    boolean isSound;


    public Palette(BattleUserInterface _battle_user_interface, Graphic _graphic, int _position_x, int _position_y, int _paletteNum, SoundAdmin _soundAdmin) {
        graphic = _graphic;
        battle_user_interface = _battle_user_interface;
        paint = new Paint();
        paletteNum = _paletteNum;
        soundAdmin = _soundAdmin;
        //inventry = _inventry;

        position_x = _position_x;
        position_y = _position_y;
        isSound = false;

        for (int i = -3; i <= 4; i++) {
            if (i < 0) {
                direction_section_check[8 + i] = Math.PI * (2 * i - 1) / 8;
            } else {
                direction_section_check[i] = Math.PI * (2 * i - 1) / 8;
            }
        }

        for (int i = 0; i < 8; i++) {
            //int center_x = position_x + (int) (PALETTE_ARRANGE_RADIUS * Math.cos(2.0*Math.PI*(double)i/8.0));
            //int center_y = position_y - (int) (PALETTE_ARRANGE_RADIUS * Math.sin(2.0*Math.PI*(double)i/8.0));
            int center_x = position_x + (int) (PALETTE_ARRANGE_RADIUS * COS[i]);
            int center_y = position_y - (int) (PALETTE_ARRANGE_RADIUS * SIN[i]);
            palette_elements[i] = new PaletteElement(center_x, center_y, i + 1, battle_user_interface.setCircleTouchUI(center_x, center_y, PALETTE_ELEMENT_RADIUS_BIG), soundAdmin);
            //palette_elements[i].setItemData();
        }

        palette_center = new PaletteCenter(position_x, position_y, 0, battle_user_interface.setCircleTouchUI(position_x, position_y, PALETTE_CENTER_RADIUS_BIG), soundAdmin);

        //palette_center.setItemData();
    }

    //by kmhanko
    public void setPosition(int x, int y) {
        position_x = x;
        position_y = y;
        for (int i = 0; i < 8; i++) {
            //int center_x = position_x + (int) (PALETTE_ARRANGE_RADIUS * Math.cos(2.0*Math.PI*(double)i/8.0));
            //int center_y = position_y - (int) (PALETTE_ARRANGE_RADIUS * Math.sin(2.0*Math.PI*(double)i/8.0));
            int center_x = position_x + (int) (PALETTE_ARRANGE_RADIUS * COS[i]);
            int center_y = position_y - (int) (PALETTE_ARRANGE_RADIUS * SIN[i]);
            palette_elements[i].setPosition(center_x, center_y, battle_user_interface.setCircleTouchUI(center_x, center_y, PALETTE_ELEMENT_RADIUS_BIG));
        }
        palette_center.setPosition(position_x, position_y, battle_user_interface.setCircleTouchUI(position_x, position_y, PALETTE_CENTER_RADIUS_BIG));
    }

    public void update() {

        //小→展開、小さな円をタッチした
        Constants.Touch.TouchState touch_state = battle_user_interface.getTouchState();
        double touch_x = battle_user_interface.getTouchX();
        double touch_y = battle_user_interface.getTouchY();

        double judge_x = touch_x - position_x;
        double judge_y = touch_y - position_y;

        judge_x *= judge_x;
        judge_y *= judge_y;

        //if (touch_state != Constants.Touch.TouchState.AWAY && battle_palette_mode == 0 && judge_x + judge_y < 30 * 30) {
        if (touch_state != Constants.Touch.TouchState.AWAY && battle_palette_mode == 0 && battle_user_interface.checkUI(palette_center.getTouchID(), Constants.Touch.TouchWay.MOVE) == true) {
            battle_palette_mode = 1;
            //展開→方向的大、大きな円からはみ出た
        } else if (touch_state != Constants.Touch.TouchState.AWAY && battle_palette_mode == 1 && battle_user_interface.checkUI(palette_center.getTouchID(), Constants.Touch.TouchWay.MOVE) == false) {
            battle_palette_mode = 2;
            //方向的大→展開、大きな円に戻った
        } else if (touch_state != Constants.Touch.TouchState.AWAY && battle_palette_mode == 2 && battle_user_interface.checkUI(palette_center.getTouchID(), Constants.Touch.TouchWay.MOVE) == true) {
            battle_palette_mode = 1;
        }

        if(battle_palette_mode == 2) {

            direction_check = Math.atan2((position_y - touch_y), (touch_x - position_x));

            for (int i = 0; i < 8; i++) {
                if (i != 4) {
                    if (direction_check >= direction_section_check[i] && direction_check < direction_section_check[(i + 1) % 8]) {

                        if(select_circle_num != i) {
                            select_circle_num = i;
                            isSound = true;
                        }
                    }
                } else {
                    if ((direction_check >= direction_section_check[i] && direction_check <= Math.PI) || (direction_check < direction_section_check[(i + 1) % 8] && direction_check >= -Math.PI)) {

                        if(select_circle_num != i) {
                            select_circle_num = i;
                            isSound = true;
                        }
                    }
                }
            }

            if(palette_elements[select_circle_num].getItemData() != null) {
                palette_center.changeElement(select_circle_num + 1);//by kmhanko 0731
                palette_center.setItemData(palette_elements[select_circle_num].getItemData(), select_circle_num, isSound);
                isSound = false;
                if (palette_elements[select_circle_num].getItemData().getItemKind() == Constants.Item.ITEM_KIND.EXPEND) {
                    //消費アイテム
                    palette_elements[select_circle_num].setItemData(null, false);
                }
            }
        }

        if (touch_state == Constants.Touch.TouchState.UP) {

            if (battle_palette_mode == 2) {
                selected_circle_num = select_circle_num;
            }
            battle_palette_mode = 0;
        }
    }

    public PaletteCenter getPaletteCenter() {
        return palette_center;
    }

    public int getPalettePrePos(){return palette_center.getPrePos();}


    public void updateSetting() {
        battle_palette_mode = 1;

        //指がパレットの上に乗った際のチェック
        if (battle_user_interface.checkUI(palette_center.getTouchID(), Constants.Touch.TouchWay.DOWN_MOMENT) == true){
            battle_user_interface.setPaletteElement(palette_center);
            System.out.println("notta");
        }


        if(paletteNum == 0){
            for (int i = 0; i < 6; i++) {
                if (battle_user_interface.checkUI(palette_elements[i].getTouchID(), Constants.Touch.TouchWay.DOWN_MOMENT) == true) {
                    battle_user_interface.setPaletteElement(palette_elements[i]);
                    System.out.println("notta");
                }
            }
        }else{
            for (int i = 0; i < 8; i++) {
                if (battle_user_interface.checkUI(palette_elements[i].getTouchID(), Constants.Touch.TouchWay.DOWN_MOMENT) == true) {
                    battle_user_interface.setPaletteElement(palette_elements[i]);
                    System.out.println("notta");
                }
            }
        }


        //指が離れた際のパレットのチェック(パレット間)
        if (battle_user_interface.getTouchState() == Constants.Touch.TouchState.UP) {
            InventryData inventry_data = null;

            if (battle_user_interface.getPaletteElement() != null) {
                if (battle_user_interface.getPaletteElement().getItemData() != null) {
                    if (checkPaletteNumAndItemKind(battle_user_interface.getPaletteElement().getItemData().getItemKind()) == false) {return;}
                }
            }


            if(battle_user_interface.getPaletteElement() == null) {
                if (battle_user_interface.getInventryData() != null) {
                    inventry_data = battle_user_interface.getInventryData();

                    if (inventry_data.getItemData() != null) {
                        if (checkPaletteNumAndItemKind(inventry_data.getItemData().getItemKind()) == false) {return;}

                        if(inventry_data.getItemData().getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT){
                            EquipmentItemData checkNumItemData = (EquipmentItemData)(inventry_data.getItemData());
                            if(checkNumItemData.getPalettePosition() > 0){return;}
                        }

                        if(inventry_data.getItemData().getItemKind() == Constants.Item.ITEM_KIND.EXPEND){
                            ExpendItemData checkNumItemData = (ExpendItemData)(inventry_data.getItemData());
                            if(inventry_data.getItemNum() - checkNumItemData.getPalettePositionNum() <= 0){return;}
                        }
                    }
                }
            }

            //アイテム移動中だったら
            if (battle_user_interface.getPaletteElement() != null) {


                if (battle_user_interface.checkUI(palette_center.getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true) {
                    ItemData a = palette_center.getItemData();
                    //palette_center.setItemData(battle_user_interface.getPaletteElement().getItemData());

                    palette_center.setItemData(battle_user_interface.getPaletteElement().getItemData(),battle_user_interface.getPaletteElement().getElementNum(), true);
                    battle_user_interface.getPaletteElement().setItemData(a, false);
                    battle_user_interface.setPaletteElement(null);
                    System.out.println("remove_ui_erement");
                }


                 if(paletteNum == 0) {//武器の場合
                    for (int i = 0; i < 6; i++) {//素手と盾は固定なので6
                        if (battle_user_interface.checkUI(palette_elements[i].getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true) {
                            ItemData a = palette_elements[i].getItemData();
                            //palette_elements[i].setItemData(battle_user_interface.getPaletteElement().getItemData());
                            palette_elements[i].setItemData(battle_user_interface.getPaletteElement().getItemData(), battle_user_interface.getPaletteElement().getElementNum(),true);
                            battle_user_interface.getPaletteElement().setItemData(a, false);
                            battle_user_interface.setPaletteElement(null);
                            System.out.println("remove_ui_erement");
                        }
                    }
                }else{//武器以外

                    for (int i = 0; i < 8; i++) {
                        if (battle_user_interface.checkUI(palette_elements[i].getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true) {
                            ItemData a = palette_elements[i].getItemData();
                            //palette_elements[i].setItemData(battle_user_interface.getPaletteElement().getItemData());
                            palette_elements[i].setItemData(battle_user_interface.getPaletteElement().getItemData(), battle_user_interface.getPaletteElement().getElementNum(), true);
                            battle_user_interface.getPaletteElement().setItemData(a, false);
                            battle_user_interface.setPaletteElement(null);
                            System.out.println("remove_ui_erement");
                        }
                    }
                }
            }


            //指が離れた際のインベントリチェック
            if (inventry_data != null) {
                palette_center.setItemData(inventry_data.getItemData(), true);
            }

            if (battle_user_interface.getPaletteElement() != null) {
                if (battle_user_interface.getPaletteElement().getItemData() != null) {
                    if (battle_user_interface.getPaletteElement().getItemData().getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) {
                        EquipmentItemData putOffItem = (EquipmentItemData) (battle_user_interface.getPaletteElement().getItemData());
                        putOffItem.setPalettePosition(0);
                    }
                    if (battle_user_interface.getPaletteElement().getItemData().getItemKind() == Constants.Item.ITEM_KIND.EXPEND) {
                        ExpendItemData putOffItem = (ExpendItemData) (battle_user_interface.getPaletteElement().getItemData());
                        putOffItem.setPalettePosition((int)Math.pow(2,battle_user_interface.getPaletteElement().getElementNum()-1),false);
                    }
                }
                battle_user_interface.getPaletteElement().setItemData(null, false);
                battle_user_interface.setPaletteElement(null);
                System.out.println("remove_ui_erement");
            }

            if (battle_user_interface.getInventryData() != null) {
                battle_user_interface.setInventryData(null);
            }
        }
    }


    public void setDebugEquipment(ItemData itemData, int position){
        palette_elements[position].setItemData(itemData, 0, false);
    }

    public void setDebugExpend(ItemData itemData, int position){
        palette_elements[position].setItemData(itemData, 0, false);
    }

    public void draw() {

        if (battle_palette_mode == 0) {
            if(palette_center.getItemData() != null) {
                palette_center.drawBigAndItem();
            }else{
                palette_center.drawSmall();
            }
        }else{
            for (int i = 0; i < 8; i++) {
                if (battle_user_interface.getPaletteElement() != null) {
                    if (battle_user_interface.isUIPaletteDraw() == true && battle_user_interface.getPaletteElement().getElementNum() == i + 1) {
                        palette_elements[i].drawBig();
                    } else {
                        palette_elements[i].drawBigAndItem();
                    }
                } else {
                    palette_elements[i].drawBigAndItem();
                }
            }

            if (battle_user_interface.getPaletteElement() != null) {
                if (battle_user_interface.isUIPaletteDraw() == true && battle_user_interface.getPaletteElement().getElementNum() == 0 && checkPaletteNumAndItemKind(battle_user_interface.getPaletteElement().getItemData().getItemKind())) {
                    palette_center.drawBig();
                } else {
                    palette_center.drawBigAndItem();
                }
            } else {
                palette_center.drawBigAndItem();
            }
        }
    }

    public void drawOnly(){

        if (battle_user_interface.getPaletteElement() != null) {
            if (battle_user_interface.isUIPaletteDraw() == true && battle_user_interface.getPaletteElement().getElementNum() == 0 && checkPaletteNumAndItemKind(battle_user_interface.getPaletteElement().getItemData().getItemKind())) {
                palette_center.drawBig();
            } else {
                palette_center.drawBigAndItem();
            }
        } else {
            palette_center.drawBigAndItem();
        }

        for (int i = 0; i < 8; i++) {
            if (battle_user_interface.getPaletteElement() != null) {
                if (battle_user_interface.isUIPaletteDraw() == true && battle_user_interface.getPaletteElement().getElementNum() == i + 1) {
                    palette_elements[i].drawBig();
                } else {
                    palette_elements[i].drawBigAndItem();
                }
            } else {
                palette_elements[i].drawBigAndItem();
            }
        }
    }


    boolean checkPaletteNumAndItemKind(Constants.Item.ITEM_KIND _itemKind){
        if(paletteNum == 0 && _itemKind == Constants.Item.ITEM_KIND.EQUIPMENT) return true;
        if(paletteNum == 1 && _itemKind == Constants.Item.ITEM_KIND.EXPEND) return true;

        //by kmhanko
        if(paletteNum == 2 && _itemKind == Constants.Item.ITEM_KIND.MINING) return true;

        return false;
    }

    public void save(){}

    public int getPaletteMode() {
        return battle_palette_mode;
    }

    public ItemData getSelectedItemData() {
        return palette_center.getItemData();
    }

    public void setPaletteCenter(ItemData _item_data, boolean isSound){palette_center.setItemData(_item_data, isSound);}

    public ItemData getItemData(int _paletteIndex){
        return palette_elements[_paletteIndex].getItemData();
    }

    public int getPositionX() {
        return position_x;
    }
    public int getPositionY() {
        return position_y;
    }


    public void release() {
        System.out.println("takanoRelease : Pallete");
        direction_section_check = null;
        if (palette_center != null) {
            palette_center.release();
            palette_center = null;
        }
        if (palette_elements != null) {
            for (int i = 0; i < palette_elements.length; i++) {
                if (palette_elements != null) {
                    palette_elements[i].release();
                }
            }
            palette_elements = null;
        }
        paint = null;
    }
}



