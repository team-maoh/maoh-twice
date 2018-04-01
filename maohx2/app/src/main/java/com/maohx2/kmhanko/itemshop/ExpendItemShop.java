package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.itemdata.ExpendItemData;

/**
 * Created by user on 2017/11/12.
 */

public class ExpendItemShop extends ItemShop {

    @Override
    public void buyItem(ItemData _itemData) {
        ExpendItemData buyItemData = (ExpendItemData)_itemData;
        System.out.println("shop :" + buyItemData.getName()+ " を ¥" + buyItemData.getPrice() + " で購入した");
    }

    @Override
    public void explainItem(ItemData _itemData) {
        ExpendItemData buyItemData = (ExpendItemData)_itemData;
        String text1 = "shop :" + buyItemData.getName()+ " : ¥" + buyItemData.getPrice() + ", HP : "+ buyItemData.getHp();
        String text2 = buyItemData.getExpline();


    }

}
