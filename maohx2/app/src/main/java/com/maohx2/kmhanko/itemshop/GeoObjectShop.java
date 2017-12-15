package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.itemdata.GeoObjectData;

/**
 * Created by user on 2017/11/12.
 */

public class GeoObjectShop extends ItemShop {

    @Override
    public void buyItem(ItemData _itemData) {
        GeoObjectData buyItemData = (GeoObjectData)_itemData;
        System.out.println("shop :" + buyItemData.getName()+ " を ¥" + buyItemData.getPrice() + " で購入した");
    }

    @Override
    public void explainItem(ItemData _itemData) {
        GeoObjectData buyItemData = (GeoObjectData)_itemData;
        //System.out.println("shop :" + buyItemData.getName()+ " : ¥" + buyItemData.getPrice());
        int textBoxID = textBoxAdmin.createTextBox(100.0, 400.0, 900.0, 800, 5);

        textBoxAdmin.bookingDrawText(textBoxID,"名称 : " + buyItemData.getName());
        textBoxAdmin.bookingDrawText(textBoxID,"\n");
        textBoxAdmin.bookingDrawText(textBoxID,"HP : " + buyItemData.getHp());
        textBoxAdmin.bookingDrawText(textBoxID,"\n");
        textBoxAdmin.bookingDrawText(textBoxID,"Atk : " + buyItemData.getAttack());
        textBoxAdmin.bookingDrawText(textBoxID,"\n");
        textBoxAdmin.bookingDrawText(textBoxID,"Def : " + buyItemData.getDefence());
        textBoxAdmin.bookingDrawText(textBoxID,"\n");
        textBoxAdmin.bookingDrawText(textBoxID,"Luc : " + buyItemData.getLuck());
        textBoxAdmin.bookingDrawText(textBoxID,"MOP");

    }

}
