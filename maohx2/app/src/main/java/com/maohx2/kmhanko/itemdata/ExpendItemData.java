package com.maohx2.kmhanko.itemdata;

import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by user on 2017/11/05.
 */

public class ExpendItemData extends ItemData {
    int hp;
    String expline;

    public ExpendItemData() {
        super();
    }

    public int getHp() {
        return hp;
    }

    public String getExpline() {
        return expline;
    }

    public void setHp(int _hp) {
        hp = _hp;
    }
    public void setExpline(String _expline) {
        expline = _expline;
    }
}

