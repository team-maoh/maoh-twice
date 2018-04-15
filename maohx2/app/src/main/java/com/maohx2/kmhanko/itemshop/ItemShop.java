package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxItemPlate;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.NamedDataAdmin;
import com.maohx2.ina.ItemData.ItemDataAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.kmhanko.Arrange.InventryS;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;

import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;
import com.maohx2.kmhanko.plate.BackPlate;

import android.graphics.Canvas;
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

    /*
    public void init(UserInterface _userInterface, Graphic _graphic, TextBoxAdmin _textBoxAdmin) {
        //listBox_Item = new ListBox();
        //listBox_Select = new ListBox();

        userInterface = _userInterface;
        graphic = _graphic;

        textBoxAdmin = _textBoxAdmin;
    }
    */

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

    public void initPlateGroup() {
        int size = itemShopData.getItemDataSize();
        /*
        listBox_Item.init(userInterface, graphic, Constants.Touch.TouchWay.DOWN_MOMENT, size , 50, 50, 50 + 600, 50 + 80 * size);
        for (int i = 0; i < size; i++) {
            listBox_Item.setContent(i, itemShopData.getItemData(i).getName());
            //ItemDataを渡す予定
            //listBox_Item.setItemContent(i, itemShopData.getItemData(i).getName());
        }
        */

        Paint productPlatePaint = new Paint();
        productPlatePaint.setARGB(255,64,64,64);
        //TODO このPlateは価格表示ができないので、オーバーライドするかしてBoxProductPlatesでも作る
        BoxProductPlate[] boxProductPlates = new BoxProductPlate[size];
        for (int i = 0; i < size; i++) {
            boxProductPlates[i] = new BoxProductPlate(
                    graphic, userInterface, productPlatePaint,
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[] { 50, 50 + 100 * i, 900, 150 + 100 * i },
                    itemShopData.getItemData(i)
            );
        }
        productPlateGroup = new PlateGroup<BoxProductPlate>(boxProductPlates);
        productPlateGroup.setUpdateFlag(true);
        productPlateGroup.setDrawFlag(true);


        Paint textPaint = new Paint();
        textPaint.setTextSize(80f);
        textPaint.setARGB(255,255,255,255);
        buySelectPlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 550, 1550, 650},
                                "購入する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 700, 1550, 800},
                                "やめる",
                                textPaint
                        )
                }
        );
        buySelectPlateGroup.setUpdateFlag(false);
        buySelectPlateGroup.setDrawFlag(false);

        loadBackPlate();
    }

    private void loadBackPlate() {
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

                                /*
                                databaseAdmin.addMyDatabase("ExpendItemInventrySaveTest", "ExpendItemInventrySaveTest.db", 1, "w");
                                MyDatabase database = databaseAdmin.getMyDatabase("ExpendItemInventrySaveTest");

                                List<String> itemNames = new ArrayList<String>();
                                for (int i = 0; i < 30; i++) {
                                    if (itemInventry.getItemData(i) == null) {
                                        break;
                                    }
                                    itemNames.add(itemInventry.getItemData(i).getName());
                                }
                                System.out.println(database.getTables());

                                database.insertRawByList("ExpendItemInventry", "name", itemNames);

                                List<String> load = database.getString("ExpendItemInventry", "name");

                                System.out.println("takano:" + load);
                                */

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
                focusItemData = productPlateGroup.getContentItem(content);

                //TODO アイテムの詳細を表示するぷれーとか何か
                productPlateGroup.setUpdateFlag(false);
                buySelectPlateGroup.setUpdateFlag(true);
                buySelectPlateGroup.setDrawFlag(true);
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
                    productPlateGroup.setUpdateFlag(true);
                    buySelectPlateGroup.setUpdateFlag(false);
                    buySelectPlateGroup.setDrawFlag(false);
                    break;
                case(1) ://キャンセル
                    productPlateGroup.setUpdateFlag(true);
                    buySelectPlateGroup.setUpdateFlag(false);
                    buySelectPlateGroup.setDrawFlag(false);
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
        buySelectPlateGroup.draw();
        productPlateGroup.draw();
        backPlateGroup.draw();
        itemInventry.draw();
    }

}