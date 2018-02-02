package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxItemPlate;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.database.NamedDataAdmin;
import com.maohx2.ina.ItemData.ItemDataAdmin;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;

import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.LinkedHashSet;

/**
 * Created by user on 2017/11/05.
 */

public abstract class ItemShop {

    //ListBox listBox_Item;
    //ListBox listBox_Select;
    PlateGroup<BoxProductPlate> productPlateGroup;
    PlateGroup<BoxTextPlate> buySelectPlateGroup;

    int textBoxID;

    //boolean isListBoxItemActive = true;
    //boolean isListBoxSelectActive = false;
    //TODO いなの関数待ち productPlateは非表示にはならないので注意
    boolean isProductPlateGroupActive = true;
    boolean isBuySelectPlateGroupActive = false;

    String buyItemName;

    ItemShopData itemShopData;
    Graphic graphic;
    UserInterface userInterface;
    TextBoxAdmin textBoxAdmin;

    ItemData focusItemData;//現在選択しているItem


    public ItemShop(UserInterface _userInterface, Graphic _graphic, TextBoxAdmin _textBoxAdmin) {
        userInterface = _userInterface;
        graphic = _graphic;
        textBoxAdmin = _textBoxAdmin;
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


        Paint textPaint = new Paint();
        textPaint.setTextSize(80f);
        textPaint.setARGB(255,255,255,255);

        buySelectPlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 50, 1550, 200},
                                "購入する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 250, 1550, 400},
                                "やめる",
                                textPaint
                        )
                }
        );

        /*
        listBox_Select.init(userInterface, graphic, Constants.Touch.TouchWay.DOWN_MOMENT, 3 , 1200, 50, 1500, 50 + 100 * 2);
        listBox_Select.setContent(0, "購入する");
        listBox_Select.setContent(1, "詳細");
        listBox_Select.setContent(2, "キャンセル");
        */

    }

    public void setTextBox() {
        //textBoxID = textBoxAdmin.createTextBox(100.0, 300.0, 500.0, 500, 5);
    }

    public void update() {
        productUpdate();
        buySelectUpdate();

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
        if (isProductPlateGroupActive) {
            productPlateGroup.update();

            int content = productPlateGroup.getTouchContentNum();
            if (content != -1) {
                focusItemData = productPlateGroup.getContentItem(content);

                //TODO アイテムの詳細を表示するぷれーとか何か

                isProductPlateGroupActive = false;
                isBuySelectPlateGroupActive = true;
            }
        }
    }

    public void buySelectUpdate() {
        if (isBuySelectPlateGroupActive) {
            buySelectPlateGroup.update();

            int content = buySelectPlateGroup.getTouchContentNum();
            switch(content) {
                case(0) ://購入する
                    //itemData = (ItemData)itemShopData.getOneDataByName(buyItemName);
                    buyItem(focusItemData);
                    isProductPlateGroupActive = true;
                    isBuySelectPlateGroupActive = false;
                    break;
                case(1) ://キャンセル
                    isProductPlateGroupActive = true;
                    isBuySelectPlateGroupActive = false;
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
        /*
        listBox_Item.draw();
        if (isListBoxSelectActive) {
            listBox_Select.draw();
        }
        */
        if (isBuySelectPlateGroupActive) {
            buySelectPlateGroup.draw();
        }
        //if (isProductPlateGroupActive) {
            productPlateGroup.draw();
        //}
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