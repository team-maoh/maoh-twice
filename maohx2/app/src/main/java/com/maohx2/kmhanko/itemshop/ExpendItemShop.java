package com.maohx2.kmhanko.itemshop;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.itemdata.ExpendItemData;

/**
 * Created by user on 2017/11/12.
 */

public class ExpendItemShop extends ItemShop {

    public ExpendItemShop(UserInterface _userInterface, Graphic _graphic, TextBoxAdmin _textBoxAdmin) {
        super(_userInterface, _graphic, _textBoxAdmin);
    }

    @Override
    public void buyItem(ItemData _itemData) {
        ExpendItemData buyItemData = (ExpendItemData)_itemData;
        System.out.println("shop :" + buyItemData.getName()+ " を ¥" + buyItemData.getPrice() + " で購入した");
        //TODO 購入処理
    }

    @Override
    public void explainItem(ItemData _itemData) {
        ExpendItemData buyItemData = (ExpendItemData)_itemData;
        String text1 = "shop :" + buyItemData.getName()+ " : ¥" + buyItemData.getPrice() + ", HP : "+ buyItemData.getHp();
        String text2 = buyItemData.getExpline();


    }

}
