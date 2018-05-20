package com.maohx2.kmhanko.itemshop;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldActivity;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.Saver.PlayerStatusSaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.MapIconPlate;
import com.maohx2.kmhanko.plate.BackPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.Constants.GAMESYSTEN_MODE.WORLD_MODE;
import static com.maohx2.ina.Constants.Inventry.*;

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
        initInventrys();
        initUIs();
        initSwitchPlate();
    }

    //**** 処理関係

    public void initSellItemInventry() {
        sellItemInventry = new Inventry();
        sellItemInventry.init(userInterface, graphic,50,50,350,600, 10);
    }

    public void sellInventryAll() {
        int tempSellMoney = calcSellMoney(sellItemInventry);
        //TODO:sellItemInventry.deleteAll();
        addMoneyToPlayerStatus(tempSellMoney);
        inventrysSave();
    }

    public int calcSellMoney(Inventry inventry) {
        //ItemInventryの中のアイテムの売却合計金額を得る関数
        int tempSellMoney = 0;
        for (int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (inventry.getItemData(i) != null) {
                if (inventry.getItemData(i).getPrice() > 0 && inventry.getItemNum(i) > 0) {
                    tempSellMoney += inventry.getItemData(i).getPrice() * inventry.getItemNum(i) / 2;
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

        geoInventry.updata();
        expendItemInventry.updata();
        equipmentInventry.updata();
        sellItemInventry.updata();
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
        sellItemInventry.init(userInterface, graphic, 50,50,350,600, 10);
        equipmentInventry.init(userInterface, graphic,400,50,700,600, 10);
        geoInventry.init(userInterface, graphic,700,50,1000,600,10);
        expendItemInventry.init(userInterface, graphic,1000,50,1300,600,10);
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

        textBoxAdmin.bookingDrawText(sellConfirmTextBoxID, "売却価格は " + _sellMoney + " G です．", sellConfirmTextBoxPaint);
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
                                graphic, userInterface, worldModeAdmin
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                soundAdmin.play("cancel00");
                                initUIs();
                                worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT_INIT);
                            }
                        }
                }
        );
        backPlateGroup.setUpdateFlag(true);
        backPlateGroup.setDrawFlag(true);
    }

    PlateGroup<BoxTextPlate> switchPlateGroup;
    private void initSwitchPlate() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        switchPlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ 1300, 700, 1500, 800 },
                                "購入画面へ",
                                textPaint
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

    PlateGroup<BoxTextPlate> sellSelectButtonGroup;
    private void initSellSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        sellSelectButtonGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
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
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
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
                                soundAdmin.play("cancel00");
                                updateSellConformTextBox(calcSellMoney(sellItemInventry));
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
