package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.Arrange.InventryS;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;

import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;
import com.maohx2.kmhanko.plate.BackPlate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import java.util.LinkedHashSet;

/**
 * Created by user on 2017/11/05.
 */

public abstract class ItemShop {

    //ListBox listBox_Item;
    //ListBox listBox_Select;
    PlateGroup<BoxProductPlate> productPlateGroup;
    PlateGroup<BoxTextPlate> buySelectPlateGroup;
    PlateGroup<BackPlate> backPlateGroup;

    int textBoxID;

    //boolean isListBoxItemActive = true;
    //boolean isListBoxSelectActive = false;
    //TODO いなの関数待ち productPlateは非表示にはならないので注意
    //boolean isProductPlateGroupActive = true;
    //boolean isBuySelectPlateGroupActive = false;

    String buyItemName;

    int buyTextBoxID;
    Paint buyTextBoxPaint;

    ItemShopData itemShopData;
    Graphic graphic;
    UserInterface userInterface;
    TextBoxAdmin textBoxAdmin;
    WorldModeAdmin worldModeAdmin;
    MyDatabaseAdmin databaseAdmin;

    ItemData focusItemData;//現在選択しているItem

    InventryS itemInventry;

    ExpendItemDataAdmin expendItemDataAdmin;

    public ItemShop(UserInterface _userInterface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, WorldModeAdmin _worldModeAdmin, InventryS _itemInventry) {
        userInterface = _userInterface;
        graphic = _graphic;
        textBoxAdmin = _textBoxAdmin;
        worldModeAdmin = _worldModeAdmin;
        itemInventry = _itemInventry;
        databaseAdmin = _databaseAdmin;
    }

    public void init() {
        initProductPlate();
        initBuySelectPlate();
        initBackPlate();
        initTextBox();
        initUIs();
    }

    private void initTextBox() {
        buyTextBoxID = textBoxAdmin.createTextBox(SELECT_WINDOW.MESS_LEFT, SELECT_WINDOW.MESS_UP, SELECT_WINDOW.MESS_RIGHT, SELECT_WINDOW.MESS_BOTTOM, SELECT_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(buyTextBoxID, false);
        textBoxAdmin.setTextBoxExists(buyTextBoxID, false);
        buyTextBoxPaint = new Paint();
        buyTextBoxPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        buyTextBoxPaint.setColor(Color.WHITE);
    }

    public void setItemShopData(ItemShopData _itemShopData) {
        itemShopData = _itemShopData;
    }

    public void loadShopData(String table_name) {
        try {
            itemShopData.loadShopData(table_name);
        } catch(NullPointerException e) {
            throw new Error(e);
        }
    }

    public void initProductPlate() {
        int size = itemShopData.getItemDataSize();

        Paint productPlatePaint = new Paint();
        productPlatePaint.setARGB(255, 64, 64, 64);
        //TODO このPlateは価格表示ができないので、オーバーライドするかしてBoxProductPlatesでも作る
        BoxProductPlate[] boxProductPlates = new BoxProductPlate[size];
        for (int i = 0; i < size; i++) {
            boxProductPlates[i] = new BoxProductPlate(
                    graphic, userInterface, productPlatePaint,
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[]{50, 50 + 100 * i, 900, 150 + 100 * i},
                    itemShopData.getItemData(i)
            );
        }
        productPlateGroup = new PlateGroup<BoxProductPlate>(boxProductPlates);
    }

    public void initBuySelectPlate() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);
        buySelectPlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "購入する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.NO_LEFT, SELECT_WINDOW.NO_UP, SELECT_WINDOW.NO_RIGHT, SELECT_WINDOW.NO_BOTTOM},
                                "やめる",
                                textPaint
                        )
                }
        );
    }

    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[] {
                        new BackPlate(
                                graphic, userInterface, worldModeAdmin
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                productPlateGroup.setUpdateFlag(true);
                                buySelectPlateGroup.setUpdateFlag(false);
                                buySelectPlateGroup.setDrawFlag(false);
                                worldModeAdmin.setShop(Constants.Mode.ACTIVATE.STOP);
                                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.ACTIVE);

                                itemInventry.save();

                            }
                        }
                }
        );
        backPlateGroup.setUpdateFlag(true);
        backPlateGroup.setDrawFlag(true);
    }

    public void setTextBox() {
        //textBoxID = textBoxAdmin.createTextBox(100.0, 300.0, 500.0, 500, 5);
    }

    public void update() {
        productUpdate();
        buySelectUpdate();
        backPlateGroup.update();
        itemInventry.updata();

        /*
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
        */
    }

    public void productUpdate() {
            productPlateGroup.update();

            int content = productPlateGroup.getTouchContentNum();

            if (content != -1) {
                //何かの商品をタッチした
                focusItemData = productPlateGroup.getContentItem(content);

                //TODO アイテムの詳細を表示するぷれーとか何か
                productPlateGroup.setUpdateFlag(false);
                buySelectPlateGroup.setUpdateFlag(true);
                buySelectPlateGroup.setDrawFlag(true);
                buyTextBoxUpdate(focusItemData);

            }
    }

    public void buySelectUpdate() {
        buySelectPlateGroup.update();
        if (buySelectPlateGroup.getUpdateFlag()) {
            int content = buySelectPlateGroup.getTouchContentNum();
            switch(content) {
                case(0) ://購入する
                    //itemData = (ItemData)itemShopData.getOneDataByName(buyItemName);
                    buyItem(focusItemData);
                    initUIs();
                    break;
                case(1) ://キャンセル
                    initUIs();
                    break;
            }
        }
    }

    //TODO:購入時処理
    public abstract void buyItem(ItemData _itemData);

    //TODO:商品詳細情報の表示処理
    public abstract void explainItem(ItemData _itemData);

    public void buyTextBoxUpdate(ItemData _itemData) {
        textBoxAdmin.setTextBoxExists(buyTextBoxID, true);

        textBoxAdmin.bookingDrawText(buyTextBoxID, _itemData.getName(), buyTextBoxPaint);
        textBoxAdmin.bookingDrawText(buyTextBoxID, "を購入しますか？", buyTextBoxPaint);
        textBoxAdmin.bookingDrawText(buyTextBoxID, "\n", buyTextBoxPaint);
        textBoxAdmin.bookingDrawText(buyTextBoxID, "価格 : ", buyTextBoxPaint);
        textBoxAdmin.bookingDrawText(buyTextBoxID, String.valueOf(_itemData.getPrice()), buyTextBoxPaint);
        textBoxAdmin.bookingDrawText(buyTextBoxID, "MOP", buyTextBoxPaint);

        textBoxAdmin.updateText(buyTextBoxID);
    }

    public void debugDraw() {
        for (int i = 0 ; i < itemShopData.getItemDataSize(); i++) {
            System.out.println("shop :" + itemShopData.getItemData(i).getName() + " ¥" + itemShopData.getItemData(i).getPrice());
        }
    }

    public void draw() {
        buySelectPlateGroup.draw();
        productPlateGroup.draw();
        backPlateGroup.draw();
        itemInventry.draw();
    }

    public void initUIs() {
        productPlateGroup.setUpdateFlag(true);
        productPlateGroup.setDrawFlag(true);
        buySelectPlateGroup.setUpdateFlag(false);
        buySelectPlateGroup.setDrawFlag(false);
        textBoxAdmin.setTextBoxExists(buyTextBoxID, false);
    }

}