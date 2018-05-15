package com.maohx2.kmhanko.itemshop;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.sound.SoundAdmin;

/**
 * Created by user on 2017/11/12.
 */

public class ExpendItemShop extends ItemShop {

    public ExpendItemShop(UserInterface _userInterface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, WorldModeAdmin _worldModeAdmin, InventryS _itemInventry, PlayerStatus _playerStatus, ItemShopAdmin _itemShopAdmin, SoundAdmin _soundAdmin) {
        super(_userInterface, _graphic, _databaseAdmin, _textBoxAdmin, _worldModeAdmin, _itemInventry, _playerStatus, _itemShopAdmin, _soundAdmin);
    }

    @Override
    public void buyItem(ItemData _itemData) {
        ExpendItemData buyItemData = (ExpendItemData)_itemData;
        if (playerStatus.getMoney() >= buyItemData.getPrice()) {
            System.out.println("shop :" + buyItemData.getName()+ " を ¥" + buyItemData.getPrice() + " で購入した");
            itemInventry.addItemData(buyItemData);
            playerStatus.subMoney(buyItemData.getPrice());
            itemShopAdmin.moneyTextBoxUpdate();
        } else {
            //TODO お金足りない
        }
    }

    @Override
    public void explainItem(ItemData _itemData) {
        ExpendItemData buyItemData = (ExpendItemData)_itemData;
        String text1 = "shop :" + buyItemData.getName()+ " : ¥" + buyItemData.getPrice() + ", HP : "+ buyItemData.getHp();
        String text2 = buyItemData.getExpline();
    }

}
