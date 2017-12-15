package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.database.NamedDataAdmin;
import com.maohx2.ina.ItemData.ItemDataAdmin;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;

import android.graphics.Canvas;

import java.util.LinkedHashSet;

/**
 * Created by user on 2017/11/05.
 */

public abstract class ItemShop {

    ListBox listBox_Item;
    ListBox listBox_Select;

    boolean isListBoxItemActive = true;
    boolean isListBoxSelectActive = false;

    String buyItemName;

    ItemShopData itemShopData;
    Graphic graphic;
    UserInterface userInterface;
    TextBoxAdmin textBoxAdmin;


    public ItemShop() {
    }

    public void init(UserInterface _userInterface, Graphic _graphic, TextBoxAdmin _textBoxAdmin) {
        listBox_Item = new ListBox();
        listBox_Select = new ListBox();

        userInterface = _userInterface;
        graphic = _graphic;

        textBoxAdmin = _textBoxAdmin;
    }

    public void setItemShopData(ItemShopData _itemShopData) {
        itemShopData = _itemShopData;
    }

    public void loadShopData(String table_name) {
        try {
            itemShopData.loadShopData(table_name);
        } catch(NullPointerException e) {
            throw new Error("︎タカノ:ItemShop#loadShopData :" + e);
        }
    }

    public void setList() {
        int size = itemShopData.getItemDataSize();
        listBox_Item.init(userInterface, graphic, Constants.Touch.TouchWay.DOWN_MOMENT, size , 50, 50, 50 + 600, 50 + 80 * size);

        for (int i = 0; i < size; i++) {
            listBox_Item.setContent(i, itemShopData.getItemData(i).getName());

            //TODO:ItemDataを渡す予定
            //listBox_Item.setItemContent(i, itemShopData.getItemData(i).getName());
        }


        listBox_Select.init(userInterface, graphic, Constants.Touch.TouchWay.DOWN_MOMENT, 3 , 1200, 50, 1500, 50 + 100 * 2);
        listBox_Select.setContent(0, "購入する");
        listBox_Select.setContent(1, "詳細");
        listBox_Select.setContent(2, "キャンセル");

    }

    public void update() {
        if (isListBoxItemActive) {
            listBox_Item.update();
            int content = listBox_Item.checkTouchContent();
            if (content != -1) {
                //購入しますか？を出す処理
                buyItemName = listBox_Item.getContent(content);

                isListBoxItemActive = false;
                isListBoxSelectActive = true;
            }
        }

        if (isListBoxSelectActive) {
            ItemData itemData;
            listBox_Select.update();
            int content = listBox_Select.checkTouchContent();
            switch(content) {
                case(0) ://購入する
                    itemData = (ItemData)itemShopData.getOneDataByName(buyItemName);
                    buyItem(itemData);
                    isListBoxItemActive = true;
                    isListBoxSelectActive = false;
                    break;
                case(1) ://詳細を表示
                    itemData = (ItemData)itemShopData.getOneDataByName(buyItemName);
                    explainItem(itemData);
                    break;
                case(2) ://キャンセル
                    isListBoxItemActive = true;
                    isListBoxSelectActive = false;
                    break;
            }
        }
    }

    //TODO:購入時処理
    public abstract void buyItem(ItemData _itemData);

    //TODO:商品詳細情報の表示処理
    public abstract void explainItem(ItemData _itemData);

    public void debugDraw() {
        for (int i = 0 ; i < itemShopData.getItemDataSize(); i++) {
            System.out.println("shop :" + itemShopData.getItemData(i).getName() + " ¥" + itemShopData.getItemData(i).getPrice());
        }
    }

    public void draw() {
        listBox_Item.draw();
        if (isListBoxSelectActive) {
            listBox_Select.draw();
        }
    }

    /*
    public void draw(Canvas canvas) {
        listBox_Item.draw(canvas);
        if (isListBoxSelectActive) {
            listBox_Select.draw(canvas);
        }
    }
    */
}

/*
DB関係
ファイル名前のDB自動入力
拡張子を除いた値


 */