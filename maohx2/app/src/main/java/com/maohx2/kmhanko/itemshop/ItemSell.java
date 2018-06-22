package com.maohx2.kmhanko.itemshop;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.Text.BoxImagePlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldActivity;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.Saver.PlayerStatusSaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.MapIconPlate;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.plate.BackPlate;
import com.maohx2.kmhanko.plate.BoxImageTextPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.Constants.GAMESYSTEN_MODE.WORLD_MODE;
import static com.maohx2.ina.Constants.Inventry.*;
import com.maohx2.ina.Constants.SIDE_INVENTRY;

/**
 * Created by user on 2018/05/21.
 */

//アイテムを売却する画面のためのクラス


//InventryData型にSoldNumを実装してやる

    //TODO 所持金表示　インベントリから売却インベントリへアイテムを移す　写したアイテムの表示変更 画面遷移時戻す インベントリリセット

public class ItemSell {

    //基本メンバ
    UserInterface userInterface;
    Graphic graphic;
    TextBoxAdmin textBoxAdmin;
    WorldModeAdmin worldModeAdmin;
    SoundAdmin soundAdmin;

    InventryS geoInventry;
    InventryS expendItemInventry;
    InventryS equipmentInventry;

    Inventry sellItemInventry;

    PlayerStatus playerStatus;
    PlayerStatusSaver playerStatusSaver;

    WorldActivity worldActivity;

    //*** コンストラクタ
    public ItemSell(Graphic _graphic, UserInterface _userInterface, WorldActivity _worldActivity, TextBoxAdmin _textBoxAdmin, WorldModeAdmin _worldModeAdmin, SoundAdmin _soundAdmin) {
        graphic = _graphic;
        userInterface = _userInterface;
        worldActivity = _worldActivity;
        textBoxAdmin = _textBoxAdmin;
        worldModeAdmin = _worldModeAdmin;
        soundAdmin = _soundAdmin;

        GlobalData globalData = (GlobalData) worldActivity.getApplication();
        geoInventry = globalData.getGeoInventry();
        expendItemInventry = globalData.getExpendItemInventry();
        equipmentInventry = globalData.getEquipmentInventry();
        playerStatus = globalData.getPlayerStatus();
        playerStatusSaver = globalData.getPlayerStatusSaver();

        initBackPlate();
        initSellSelectButton();
        initSellItemInventry();
        initSellConformTextBox();
        initsellEnterPlate();
        initUIs();
        initSwitchPlate();
    }

    //**** 処理関係

    public void start() {
        initInventrys();
    }

    public void initSellItemInventry() {
        sellItemInventry = new Inventry();
        sellItemInventry.init(userInterface, graphic, 50,50,350,600, 10);
    }

    public void sellInventryAll() {
        int tempSellMoney = calcSellMoney();
        sellFromInventry(geoInventry);
        sellFromInventry(equipmentInventry);
        sellFromInventry(expendItemInventry);
        addMoneyToPlayerStatus(tempSellMoney);
        reset();
        inventrysSave();
    }

    public void reset() {
        for(int i = 0; i < INVENTRY_DATA_MAX; i++) {
            sellItemInventry.getInventryData(i).delete();
            geoInventry.getInventryData(i).setSoldNum(0);
            expendItemInventry.getInventryData(i).setSoldNum(0);
            equipmentInventry.getInventryData(i).setSoldNum(0);
        }
    }

    private void sellFromInventry(Inventry inventry) {
        Inventry parentInv;
        for(int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (inventry.getItemData(i) != null) {
                if (inventry.getInventryData(i).getSoldNum() > 0) {
                    /*
                    inventry.getInventryData(i).setItemNum(
                        inventry.getInventryData(i).getItemNum() - inventry.getInventryData(i).getSoldNum()
                    );
                    */
                    parentInv = inventry.getInventryData(i).getParentInventry();
                    for (int j = 0; j < inventry.getInventryData(i).getSoldNum(); j++) {
                        parentInv.subItemData(inventry.getItemData(i));
                    }
                    inventry.getInventryData(i).setSoldNum(0);
                }
            }
        }
    }

    public int calcSellMoney() {
        //ItemInventryの中のアイテムの売却合計金額を得る関数
        int tempSellMoney = 0;
        for (int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (sellItemInventry.getItemData(i) != null) {
                if (sellItemInventry.getItemData(i).getPrice() > 0 && sellItemInventry.getItemNum(i) > 0) {
                    tempSellMoney += sellItemInventry.getItemData(i).getPrice() * sellItemInventry.getItemNum(i);
                }
            }
        }
        return tempSellMoney;
    }

    private void addMoneyToPlayerStatus(int addMoney) {
        playerStatus.addMoney(addMoney);
        playerStatusSaver.save();
    }

    private void inventrysSave() {
        equipmentInventry.save();
        expendItemInventry.save();
        geoInventry.save();
    }

    private void checkInventry() {
        InventryData tempInventryData = userInterface.getInventryData();

        if (tempInventryData == null) {
            return;
        }
        if (tempInventryData.getItemData() == null) {
            return;
        }
        if (tempInventryData.getParentInventry() == sellItemInventry) {//参照が等しいかどうかを確認したいので==を使用している
            //売却インベントリからの場合。そのInventryDataを売却インベントリから1つ削除する
            //さらに、同一ItemDataを各種インベントリから探し、その売却個数に-1する
            if (tempInventryData.getItemNum() > 0) {
                tempInventryData.getParentInventry().subItemData(tempInventryData.getItemData());
                switch (tempInventryData.getItemData().getItemKind()) {
                    case GEO:
                        geoInventry.searchInventryData(tempInventryData.getItemData()).subSoldNum();
                        break;
                    case EQUIPMENT:
                        equipmentInventry.searchInventryData(tempInventryData.getItemData()).subSoldNum();
                        break;
                    case EXPEND:
                        expendItemInventry.searchInventryData(tempInventryData.getItemData()).subSoldNum();
                        break;
                    default:
                        throw new Error("☆タカノ: 不適切なアイテムの売却個数を減らそうとしました" + tempInventryData.getItemData().getName());
                }
            }
        } else {
            //所持インベントリからの場合。そのInventryDataのコピーを作成し、売却インベントリに格納する。
            //さらに元のInventryDataの売却個数を+1する。ただし、売却個数が所持個数を上回るような場合には全ての処理を行わない。
            if (tempInventryData.getParentInventry() == geoInventry) {
                GeoObjectData tempGeo = (GeoObjectData) (tempInventryData.getItemData());
                if (tempInventryData.getItemNum() > tempInventryData.getSoldNum() && tempGeo.getSlotSetName() == "noSet") {
                    sellItemInventry.addItemData(tempInventryData.getItemData());
                    tempInventryData.addSoldNum();
                }
            }
            if (tempInventryData.getParentInventry() == expendItemInventry) {
                ExpendItemData tempExpend = (ExpendItemData) (tempInventryData.getItemData());
                if (tempInventryData.getItemNum() - tempExpend.getPalettePositionNum() > tempInventryData.getSoldNum()) {
                    sellItemInventry.addItemData(tempInventryData.getItemData());
                    tempInventryData.addSoldNum();
                }
            }
            if (tempInventryData.getParentInventry() == equipmentInventry) {
                EquipmentItemData tempEquip = (EquipmentItemData) (tempInventryData.getItemData());
                if (tempInventryData.getItemNum() > tempInventryData.getSoldNum() && tempEquip.getPalettePosition() == 0) {
                    sellItemInventry.addItemData(tempInventryData.getItemData());
                    tempInventryData.addSoldNum();
                }
            }
        }
    }

    //***** draw関係 *****
    public void draw() {
        geoInventry.draw();
        expendItemInventry.draw();
        equipmentInventry.draw();
        sellItemInventry.draw();
        switchPlateGroup.draw();

        sellEnterPlateGroup.draw();
        sellSelectButtonGroup.draw();
        backPlateGroup.draw();
    }

    //***** update関係 *****
    public void update() {

        // インベントリにおけるアイテムのタッチ
        checkInventry();


        if (!sellSelectButtonGroup.getUpdateFlag()) {
            geoInventry.updata();
            expendItemInventry.updata();
            equipmentInventry.updata();
            sellItemInventry.updata();
        }
        switchPlateGroup.update();

        //sellSelectButtonCheck();
        sellEnterPlateGroup.update();
        sellSelectButtonGroup.update();
        backPlateGroup.update();
    }
    //ui.getInventryData
    //***initUIs
    public void initUIs() {
        textBoxAdmin.setTextBoxExists(sellConfirmTextBoxID, false);

        sellSelectButtonGroup.setUpdateFlag(false);
        sellSelectButtonGroup.setDrawFlag(false);
        sellEnterPlateGroup.setUpdateFlag(true);
    }

    //***インベントリ関係
    public void initInventrys() {
        /*
        sellItemInventry.setPosition(50,50,350,500);
        equipmentInventry.setPosition(400,50,700,500);
        geoInventry.setPosition(700,50,1000,500);
        expendItemInventry.setPosition(1000,50,1300,500);
        */
        sellItemInventry.setPosition(0,SIDE_INVENTRY.INV_UP,390,SIDE_INVENTRY.INV_BOTTOM, SIDE_INVENTRY.INV_CONTENT_NUM);
        equipmentInventry.setPosition(430,SIDE_INVENTRY.INV_UP,820,SIDE_INVENTRY.INV_BOTTOM, SIDE_INVENTRY.INV_CONTENT_NUM);
        geoInventry.setPosition(820,SIDE_INVENTRY.INV_UP,1210,SIDE_INVENTRY.INV_BOTTOM, SIDE_INVENTRY.INV_CONTENT_NUM);
        expendItemInventry.setPosition(1210,SIDE_INVENTRY.INV_UP,1600,SIDE_INVENTRY.INV_BOTTOM, SIDE_INVENTRY.INV_CONTENT_NUM);
    }


    //***テキストボックス関係
    int sellConfirmTextBoxID;
    Paint sellConfirmTextBoxPaint;
    private void initSellConformTextBox() {
        //売却確認テキストボックス
        sellConfirmTextBoxID = textBoxAdmin.createTextBox(Constants.SELECT_WINDOW.MESS_LEFT, Constants.SELECT_WINDOW.MESS_UP, Constants.SELECT_WINDOW.MESS_RIGHT, Constants.SELECT_WINDOW.MESS_BOTTOM, Constants.SELECT_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(sellConfirmTextBoxID, false);
        textBoxAdmin.setTextBoxExists(sellConfirmTextBoxID, false);
        sellConfirmTextBoxPaint = new Paint();
        sellConfirmTextBoxPaint.setTextSize(Constants.SELECT_WINDOW.TEXT_SIZE);
        sellConfirmTextBoxPaint.setColor(Color.WHITE);
    }

    private void updateSellConformTextBox(int _sellMoney) {
        textBoxAdmin.setTextBoxExists(sellConfirmTextBoxID, true);
        textBoxAdmin.resetTextBox(sellConfirmTextBoxID);

        textBoxAdmin.bookingDrawText(sellConfirmTextBoxID, "売却価格は " + _sellMoney + " Maon です．", sellConfirmTextBoxPaint);
        textBoxAdmin.bookingDrawText(sellConfirmTextBoxID, "\n", sellConfirmTextBoxPaint);
        textBoxAdmin.bookingDrawText(sellConfirmTextBoxID, "売却しますか？", sellConfirmTextBoxPaint);
        textBoxAdmin.bookingDrawText(sellConfirmTextBoxID, "MOP", sellConfirmTextBoxPaint);

        textBoxAdmin.updateText(sellConfirmTextBoxID);
    }


    //***ボタン関係
    PlateGroup<BackPlate> backPlateGroup;
    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[] {
                        new BackPlate(
                                graphic, userInterface
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                soundAdmin.play("cancel00");
                                initUIs();
                                reset();
                                worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT_INIT);
                            }
                        }
                }
        );
        backPlateGroup.setUpdateFlag(true);
        backPlateGroup.setDrawFlag(true);
    }

    PlateGroup<BoxImagePlate> switchPlateGroup;
    private void initSwitchPlate() {
        int position[] = new int[] {
                1250, 50, 1350, 150
        };
        switchPlateGroup = new PlateGroup<BoxImagePlate>(
                new BoxImagePlate[]{
                        new BoxImagePlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                position,
                                graphic.makeImageContext(
                                        graphic.searchBitmap("buyPlate"),
                                        (position[0] + position[2])/2, (position[1] + position[3])/2,
                                        5.0f, 5.0f, 0,
                                        255, false
                                ),
                                graphic.makeImageContext(
                                        graphic.searchBitmap("buyPlate"),
                                        (position[0] + position[2])/2, (position[1] + position[3])/2,
                                        7.0f, 7.0f, 0,
                                        255, false
                                )
                        ) {
                            @Override
                            public void callBackEvent() {
                                //購入画面へが押された時の処理
                                soundAdmin.play("enter00");
                                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.SHOP_INIT);
                                initUIs();
                            }
                        }
                }
        );
        switchPlateGroup.setUpdateFlag(true);
        switchPlateGroup.setDrawFlag(true);
    }


    PlateGroup<BoxImageTextPlate> sellSelectButtonGroup;
    private void initSellSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);


        sellSelectButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "売却する",
                                textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //売却するが押された時の処理
                                soundAdmin.play("shop00");
                                sellInventryAll();
                                initUIs();
                            }
                        },
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.NO_LEFT, SELECT_WINDOW.NO_UP, SELECT_WINDOW.NO_RIGHT, SELECT_WINDOW.NO_BOTTOM},
                                "やめる",
                                textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //やめるが押された時の処理
                                soundAdmin.play("cancel00");
                                initUIs();
                            }
                        }
                }
        );
        sellSelectButtonGroup.setUpdateFlag(false);
        sellSelectButtonGroup.setDrawFlag(false);
    }

/*
    PlateGroup<BoxTextPlate> sellEnterPlateGroup;
    private void initsellEnterPlate() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        sellEnterPlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[] {
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ 50, 750, 350, 850 },
                                "売却",
                                textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //売却ボタンが押された時の処理
                                soundAdmin.play("enter00");
                                updateSellConformTextBox(calcSellMoney());
                                sellSelectButtonGroup.setUpdateFlag(true);
                                sellSelectButtonGroup.setDrawFlag(true);
                                sellEnterPlateGroup.setUpdateFlag(false);
                            }
                        }
                }
        );
        sellEnterPlateGroup.setUpdateFlag(true);
        sellEnterPlateGroup.setDrawFlag(true);
    }
    */



    PlateGroup<BoxImagePlate> sellEnterPlateGroup;
    private void initsellEnterPlate() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        int position[] = new int[] {
                50, 50, 150, 150
        };

        sellEnterPlateGroup = new PlateGroup<BoxImagePlate>(
                new BoxImagePlate[] {
                        new BoxImagePlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                position,
                                graphic.makeImageContext(
                                        graphic.searchBitmap("sellPlate2"),
                                        (position[0] + position[2])/2, (position[1] + position[3])/2,
                                        5.0f, 5.0f, 0,
                                        255, false
                                ),
                                graphic.makeImageContext(
                                        graphic.searchBitmap("sellPlate2"),
                                        (position[0] + position[2])/2, (position[1] + position[3])/2,
                                        7.0f, 7.0f, 0,
                                        255, false
                                )
                        ) {
                            @Override
                            public void callBackEvent() {
                                //売却ボタンが押された時の処理
                                soundAdmin.play("enter00");
                                updateSellConformTextBox(calcSellMoney());
                                sellSelectButtonGroup.setUpdateFlag(true);
                                sellSelectButtonGroup.setDrawFlag(true);
                                sellEnterPlateGroup.setUpdateFlag(false);
                            }
                        }
                }
        );
        sellEnterPlateGroup.setUpdateFlag(true);
        sellEnterPlateGroup.setDrawFlag(true);
    }


    public void release() {
        sellItemInventry.release();
        sellConfirmTextBoxPaint = null;
        backPlateGroup.release();
        backPlateGroup = null;
        switchPlateGroup.release();
        switchPlateGroup = null;
        sellSelectButtonGroup.release();
        sellSelectButtonGroup = null;
        sellEnterPlateGroup.release();
        sellEnterPlateGroup = null;
    }


        /*
    public void sellSelectButtonCheck() {
        if (!(sellSelectButtonGroup.getUpdateFlag())) {
            return;
        }

        int buttonID = sellSelectButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //売却する
            soundAdmin.play("enter00");
            sellInventryAll(sellItemInventry);
            initUIs();
        }
        if (buttonID == 1 ) { //やめる
            soundAdmin.play("cancel00");
            initUIs();
        }
    }
    */


}
