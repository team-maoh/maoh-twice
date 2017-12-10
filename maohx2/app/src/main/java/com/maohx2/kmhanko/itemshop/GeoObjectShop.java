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
        System.out.println("shop :" + buyItemData.getName()+ " : ¥" + buyItemData.getPrice());
    }

}
