package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.ina.ItemData.ItemDataAdminManager;

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

    public enum ITEM_KIND {
        EXPEND,
        GEO_OBJECT,
        ITEM_KIND_NUM
    }

    public void init(UserInterface _userInterface, Graphic _graphic, MyDatabaseAdmin myDatabaseAdmin, ItemDataAdminManager itemDataAdminManager) {
        userInterface = _userInterface;
        graphic = _graphic;

        expendItemShopData = new ExpendItemShopData();
        expendItemShopData.init(itemDataAdminManager.getExpendItemDataAdmin(), myDatabaseAdmin);

        geoObjectShopData = new GeoObjectShopData();
        geoObjectShopData.init(itemDataAdminManager.getGeoObjectDataAdmin(), myDatabaseAdmin);
    }

    public void makeItemShop(ITEM_KIND _itemKind, String _tableName) {
        if (_itemKind == ITEM_KIND.EXPEND) {
            itemShop = new ExpendItemShop();
            itemShop.init(userInterface, graphic);
            itemShop.setItemShopData(expendItemShopData);
        }
        if (_itemKind == ITEM_KIND.ITEM_KIND_NUM) {
            itemShop = new GeoObjectShop();
            itemShop.init(userInterface, graphic);
            itemShop.setItemShopData(geoObjectShopData);
        }
        itemShop.loadShopData(_tableName);
        itemShop.setList();
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
