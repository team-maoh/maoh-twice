package com.maohx2.kmhanko.itemshop;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SIDE_INVENTRY;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;

/**
 * Created by user on 2017/11/19.
 */

//TODO この魔王は倒した、フラグ

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
    WorldModeAdmin worldModeAdmin;
    MyDatabaseAdmin databaseAdmin;

    InventryS expendItemInventry;
    InventryS geoInventry;

    PlayerStatus playerStatus;

    SoundAdmin soundAdmin;

    //int moneyTextBoxID;


    public enum ITEM_KIND {
        EXPEND,
        GEO_OBJECT,
        ITEM_KIND_NUM
    }

    public void init(Graphic _graphic, UserInterface _userInterface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, ItemDataAdminManager itemDataAdminManager, InventryS _expendItemInventry, InventryS _geoInventry, PlayerStatus _playerStatus, SoundAdmin _soundAdmin) {
        userInterface = _userInterface;
        graphic = _graphic;
        textBoxAdmin = _textBoxAdmin;
        worldModeAdmin = _worldModeAdmin;
        expendItemInventry = _expendItemInventry;
        geoInventry = _geoInventry;
        databaseAdmin = _databaseAdmin;
        playerStatus = _playerStatus;
        soundAdmin = _soundAdmin;

        expendItemShopData = new ExpendItemShopData(graphic, databaseAdmin);
        expendItemShopData.setExpendItemDataAdmin(itemDataAdminManager.getExpendItemDataAdmin());

        geoObjectShopData = new GeoObjectShopData(graphic, databaseAdmin);
        geoObjectShopData.setGeoObjectDataAdmin(itemDataAdminManager.getGeoObjectDataAdmin());

        //initTextBox();
    }

    public void start() {
        expendItemInventry.setPosition(SIDE_INVENTRY.INV_LEFT, SIDE_INVENTRY.INV_UP, SIDE_INVENTRY.INV_RIGHT,SIDE_INVENTRY.INV_BOTTOM,SIDE_INVENTRY.INV_CONTENT_NUM);

        //geoInventry.setPosition(1200, 100 , 1600, 600,10);
        //moneyTextBoxUpdate();
    }


    public void makeItemShop(ITEM_KIND _itemKind, String _tableName) {
        boolean itemKindFlag = false;

        if (_itemKind == ITEM_KIND.EXPEND) {
            itemShop = new ExpendItemShop(userInterface, graphic, databaseAdmin, textBoxAdmin, worldModeAdmin, expendItemInventry, playerStatus, this, soundAdmin);
            itemShop.setItemShopData(expendItemShopData);
            itemKindFlag = true;
        }
        if (_itemKind == ITEM_KIND.GEO_OBJECT) {
            itemShop = new GeoObjectShop(userInterface, graphic, databaseAdmin, textBoxAdmin, worldModeAdmin, geoInventry, playerStatus, this, soundAdmin);
            itemShop.setItemShopData(geoObjectShopData);
            itemKindFlag = true;
        }
        if (!itemKindFlag) {
            throw new Error("ItemShopAdmin#makeItemShop : ☆タカノ itemKindが不適切 : " + _itemKind);
        }
        itemShop.loadShopData(_tableName);//こちらがinitより先でなけらばならない
        itemShop.init();
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
        //textBoxAdmin.setTextBoxExists(moneyTextBoxID, worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.SHOP);

    }

    public void draw() {
        if (itemShopActive) {
            itemShop.draw();
        }
        //textBoxAdmin.setTextBoxExists(moneyTextBoxID, worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.SHOP);
    }

    public ItemShop getItemShop() {
        return itemShop;
    }

    public void release() {
        System.out.println("takanoRelease : ItemShopAdmin");
        itemShop.release();
        itemShop = null;
    }


/*
    public void moneyTextBoxUpdate() {
        textBoxAdmin.bookingDrawText(moneyTextBoxID, "所持金 " + playerStatus.getMoney());
        textBoxAdmin.bookingDrawText(moneyTextBoxID, "MOP");
        textBoxAdmin.updateText(moneyTextBoxID);
    }
*/
/*
    private void initTextBox() {
        moneyTextBoxID = textBoxAdmin.createTextBox(0,600,300,900,6);
        textBoxAdmin.setTextBoxUpdateTextByTouching(moneyTextBoxID, false);
        textBoxAdmin.setTextBoxExists(moneyTextBoxID, false);
        moneyTextBoxUpdate();
    }
    public int getMoneyTextBoxID() {
        return moneyTextBoxID;
    }
*/

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
