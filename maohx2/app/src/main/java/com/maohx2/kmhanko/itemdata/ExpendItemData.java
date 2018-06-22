package com.maohx2.kmhanko.itemdata;

import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;

/**
 * Created by user on 2017/11/05.
 */

public class ExpendItemData extends ItemData {
    int hp;
    String expline;
    boolean[] palettePositions = new boolean[8];

    public ExpendItemData() {
        super();
        for(int i = 0; i< 8; i++){
            palettePositions[i] = false;
        }
    }

    public int getHp() {
        return hp;
    }
    public void setHp(int _hp) {
        hp = _hp;
    }

    public String getExpline() {
        return expline;
    }
    public void setExpline(String _expline) {
        expline = _expline;
    }

    public int getPalettePosition(){
        int boolCheck = 1;
        int palettePosition = 0;
        for(int i = 0; i < 8; i++) {
            if(palettePositions[i] == true) {
                palettePosition += boolCheck;
            }
            boolCheck *= 2;
        }
        return palettePosition;
    }

    public void setPalettePosition(int _palettePosition, boolean _setBoolean) {
        int boolCheck = 1;
        for(int i = 0; i < 8; i++) {
            if((_palettePosition&boolCheck) != 0) {
                palettePositions[i] = _setBoolean;
            }
            boolCheck *= 2;
        }
    }

    public int getPalettePositionNum(){
        int palettePositionNum = 0;
        for(int i = 0; i < 8; i++) {
            if(palettePositions[i] == true) {
                palettePositionNum++;
            }
        }

        return palettePositionNum;
    }

    public int getPalettePosition(int index_num){
        int count = 0;
        for(int i = 0; i < 8; i++){
            if(palettePositions[i] == true){
                count++;
                if(count == index_num){
                    return i;
                }
            }
        }

        return -1;
    }

    public int getPriceByPlayerStatus(PlayerStatus playerStatus) {
        int tmpPrice = 0;
        tmpPrice = (int)((float)price * (float)(playerStatus.getBaseAttack() + playerStatus.getBaseDefence() + playerStatus.getBaseLuck())/60.0f);


        return tmpPrice;
    }

}

