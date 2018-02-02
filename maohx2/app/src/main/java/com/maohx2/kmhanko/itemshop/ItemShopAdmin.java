package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.fuusya.TextBox.TextBoxAdmin;
/**
 * Created by user on 2017/11/19.
 */

/*
package itemshop
ItemShopAdminだけインスタンス化すれば、他は全てインスタンス化される。
#init(UI,Graphic,DBadmin,ItemDataAdminManager)
#makeItemShop(ITEM_KIND, table_name)で店を出すことが可能。後はupdateとdrawさえ呼べば良い。
#closeItemShopで店を終了させる。update,drawは呼びっぱなしでも構わない。
*/

/*
ItemShopDataの管理及びItemShopを管理するためのクラス

ItemShopDataは、各種類についてそれぞれ、一つのテーブルについてしか同時保持しない。
そのため、ItemShopも各種類についてそれぞれ一つしか存在しないところまで管理できるが、
このクラスではItemShop自体を、同時に一つしか保持しないことにしている。
すなわち複数のItemShopについてメモリ上で管理していないということになる。
 */

public class ItemShopAdmin {
    //ItemShopの管理。同時に一つまで
    ItemShop itemShop;
    boolean itemShopActive = false;

    //ItemShopDataの管理。もし種類が増えるようならManagerを作ってそこに実体を持たせることにする
    ExpendItemShopData expendItemShopData;
    GeoObjectShopData geoObjectShopData;

    //基本事項
    UserInterface userInterface;
    Graphic graphic;
    TextBoxAdmin textBoxAdmin;

    public enum ITEM_KIND {
        EXPEND,
        GEO_OBJECT,
        ITEM_KIND_NUM
    }

    public void init(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin myDatabaseAdmin, TextBoxAdmin _textBoxAdmin, ItemDataAdminManager itemDataAdminManager) {
        userInterface = _userInterface;
        graphic = _graphic;
        textBoxAdmin = _textBoxAdmin;

        expendItemShopData = new ExpendItemShopData(graphic, myDatabaseAdmin);
        expendItemShopData.setExpendItemDataAdmin(itemDataAdminManager.getExpendItemDataAdmin());

        geoObjectShopData = new GeoObjectShopData(graphic, myDatabaseAdmin);
        geoObjectShopData.setGeoObjectDataAdmin(itemDataAdminManager.getGeoObjectDataAdmin());
    }

    public void makeItemShop(ITEM_KIND _itemKind, String _tableName) {
        boolean itemKindFlag = false;

        if (_itemKind == ITEM_KIND.EXPEND) {
            itemShop = new ExpendItemShop(userInterface, graphic, textBoxAdmin);
            itemShop.setItemShopData(expendItemShopData);
            itemKindFlag = true;
        }
        if (_itemKind == ITEM_KIND.GEO_OBJECT) {
            itemShop = new GeoObjectShop(userInterface, graphic, textBoxAdmin);
            itemShop.setItemShopData(geoObjectShopData);
            itemKindFlag = true;
        }
        if (!itemKindFlag) {
            throw new Error("ItemShopAdmin#makeItemShop : ☆タカノ itemKindが不適切 : " + _itemKind);
        }
        itemShop.loadShopData(_tableName);
        itemShop.initPlateGroup();
        itemShop.setTextBox();
    }

    public void makeAndOpenItemShop(ITEM_KIND _itemKind, String _tableName) {
        makeItemShop(_itemKind, _tableName);
        openItemShop();
    }

    public void openItemShop() {
        itemShopActive = true;
    }

    public void closeItemShop() {
        itemShopActive = false;
    }

    public boolean isItemShopActive() {
        return itemShopActive;
    }

    public void update() {
        if (itemShopActive) {
            itemShop.update();
        }
    }

    //TODO:Graphicに変更
    public void draw() {
        if (itemShopActive) {
            itemShop.draw();
        }
    }

    public ItemShop getItemShop() {
        return itemShop;
    }

}

/*
ItemShopAdmin {
    ItemShop
    └ExpendItemShop
    └GeoObjectShop

    ItemShopDataAdmin 店切り替え時、何ShopDataの何Tableかを指定してセットする。
}

ItemShopData
└ExpendItemShopData ←1店データのみをメモリに保持
└GeoObjectShopData　←同上

*/
