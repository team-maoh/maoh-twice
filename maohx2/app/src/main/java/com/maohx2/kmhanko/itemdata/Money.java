package com.maohx2.kmhanko.itemdata;

import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by user on 2017/12/15.
 */

public class Money extends ItemData {
    int money;

    public Money(int _money) {
        money = _money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int _money) {
        money = _money;
    }
}
