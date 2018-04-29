package com.maohx2.kmhanko.itemshop;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.Arrange.InventryS;

/**
 * Created by user on 2017/11/12.
 */

public class GeoObjectShop extends ItemShop {

    public GeoObjectShop(UserInterface _userInterface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, WorldModeAdmin _worldModeAdmin, InventryS _itemInventry) {
        super(_userInterface, _graphic, _databaseAdmin, _textBoxAdmin, _worldModeAdmin, _itemInventry);
    }

    @Override
    public void buyItem(ItemData _itemData) {
        GeoObjectData buyItemData = (GeoObjectData)_itemData;
        System.out.println("shop :" + buyItemData.getName()+ " を ¥" + buyItemData.getPrice() + " で購入した");
    }

    @Override
    public void explainItem(ItemData _itemData) {
        GeoObjectData buyItemData = (GeoObjectData)_itemData;
        //System.out.println("shop :" + buyItemData.getName()+ " : ¥" + buyItemData.getPrice());
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
