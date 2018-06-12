package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.horie.map.MapStatus;
import com.maohx2.horie.map.MapStatusSaver;
import com.maohx2.ina.ActivityChange;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.Constants.LOOP_WINDOW;
import com.maohx2.ina.Constants.GAMESYSTEN_MODE.WORLD_MODE;
import com.maohx2.ina.Constants.DungeonKind.DUNGEON_KIND;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldActivity;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.plate.BoxImageTextPlate;

import com.maohx2.ina.Constants.POPUP_WINDOW;

import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;

import com.maohx2.ina.Constants.WorldMap.SELECT_MODE;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.kmhanko.sound.SoundAdmin;
/**
 * Created by user on 2017/11/24.
 */

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

//TODO 指を離すと元に戻ってしまうので、どこを選択したのかわかりにくい。説明プレートを出せばわかるけど
public class DungeonSelectManager {
    //DungeonSelectButtonAdmin dungeonSelectButtonAdmin;
    //LoopSelectButtonAdmin loopSelectButtonAdmin;

    static final float DUNGEON_SELECT_BUTTON_RATE_DEFAULT = 4.0f;
    static final float DUNGEON_SELECT_BUTTON_RATE_FEEDBACK = 5.0f;
    static final int DUNGEON_SELECT_BUTTON_RATE_TOURH_R = 110;

    static final String DUNGEON_SELECT_BUTTON_TABLE_NAME = "dungeon_select_button";
    //static final String MENU_BUTTON_TABLE_NAME = "menu_button";


    boolean initUIsFlag = false;

    private boolean isDungeonSelectActive = false;

    int enterTextBoxID;
    Paint enterTextPaint;

    int loopCountTextBoxID;
    Paint loopCountTextPaint;

    Graphic graphic;
    UserInterface userInterface;
    MyDatabaseAdmin databaseAdmin;
    GeoSlotAdminManager geoSlotAdminManager;
    WorldModeAdmin worldModeAdmin;
    TextBoxAdmin textBoxAdmin;

    WorldActivity worldActivity;

    MyDatabase database;

    SoundAdmin soundAdmin;

    //TODO いなの関数待ち
    List<String> dungeonName;
    List<String> dungeonNameExpress;
    List<String> event;

    PlateGroup<MapIconPlate> mapIconPlateGroup;

    //PlateGroup<BoxTextPlate> dungeonInformationPlate;
    PlateGroup<BoxImageTextPlate> dungeonEnterSelectButtonGroup;
    PlateGroup<BoxImageTextPlate> maohEnterSelectButtonGroup;



    //PlateGroup<CircleImagePlate> menuButtonGroup;

    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";


    int focusDungeonButtonID;

    //SELECT_MODE selectMode = SELECT_MODE.DUNGEON_SELECT;

    Paint paint = new Paint(); //TODO GeoMapとDungeonSelectの切り替え表示用。いつか消える

    PlayerStatus playerStatus;

   //WorldActivity worldActivity;
    ActivityChange activityChange;

    //いなの実装までの仮置き
    //boolean enterSelectFlag = false;

    InventryS geoInventry;
    InventryS expendItemInventry;
    InventryS equipmentInventry;

    MapStatus mapStatus;
    MapStatusSaver mapStatusSaver;

    public DungeonSelectManager(Graphic _graphic, UserInterface _userInterface, TextBoxAdmin _textBoxAdmin, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, GeoSlotAdminManager _geoSlotAdminManager, PlayerStatus _playerStatus, ActivityChange _activityChange, SoundAdmin _soundAdmin, WorldActivity _worldActivity, MapStatus _mapStatus, MapStatusSaver _mapStatusSaver) {
        graphic = _graphic;
        userInterface = _userInterface;
        textBoxAdmin = _textBoxAdmin;
        databaseAdmin = _databaseAdmin;
        geoSlotAdminManager = _geoSlotAdminManager;
        worldModeAdmin = _worldModeAdmin;
        worldActivity = _worldActivity;
        activityChange = _activityChange;
        //playerStatus = _playerStatus;
        soundAdmin = _soundAdmin;
        mapStatus = _mapStatus;
        mapStatusSaver = _mapStatusSaver;

        worldActivity = _worldActivity;
        GlobalData globalData = (GlobalData) worldActivity.getApplication();
        playerStatus = globalData.getPlayerStatus();

        geoInventry = globalData.getGeoInventry();
        expendItemInventry = globalData.getExpendItemInventry();
        equipmentInventry = globalData.getEquipmentInventry();

        setDatabase(databaseAdmin);
        initMapIconPlate();
        initDungeonEnterSelectButton();
        initMaohEnterSelectButton();
        //initModeSelectButton();
        initTextBox();
        initOkButton();
        initLoopCountSelectButton();
        initUIs();

        //TODO : Loopselect
    }

    public void start() {

        //todo:魔王の種類10を置き換える
        if(playerStatus.getMaohWinCount() == 1){
            worldModeAdmin.setMode(WORLD_MODE.CREDIT);
        }

        //前ダンジョンクリアかつ魔王討伐回数＝Clear+1なら、Clearを+1
        boolean flag = true;
        for (int i = 0; i < Constants.STAGE_NUM; i++) {
            if (mapStatus.getMapClearStatus(i) == 0) {
                flag = false;
            }
        }

        if (flag) {
            playerStatus.addClearCount();
            playerStatus.setNowClearCount(playerStatus.getClearCount());

            enterTextBoxUpdateCountUp();
            OkButtonGroup.setUpdateFlag(true);
            OkButtonGroup.setDrawFlag(true);

            for (int i = 0; i < Constants.STAGE_NUM; i++) {
                mapStatus.setMapClearStatus(0,i);
                mapStatusSaver.save();
            }
            playerStatus.save();
        }
    }

    private void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    //***** GeoMapとDungeonSelectMapの切り替え *****

    public void switchSelectMode() {
        if (worldModeAdmin.getMode() == WORLD_MODE.GEO_MAP_SELECT) {
            worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT_INIT);
        } else {
            worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_SELECT_INIT);
        }
    }

    //***** Buttonのinit関係 *****
    private void initMapIconPlate(){
        int size = database.getSize(DUNGEON_SELECT_BUTTON_TABLE_NAME);

        dungeonName = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "name");
        dungeonNameExpress = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "dungeonName");
        List<String> imageName = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "image_name");
        List<Integer> x = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "x");
        List<Integer> y = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "y");
        List<Integer> scale = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "scale");
        List<Integer> scale_feed = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "scale_feed");
        event = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "event");

        List<MapIconPlate> mapIconPlateList = new ArrayList<MapIconPlate>();

        //インスタンス化
        for (int i = 0; i < size; i++) {
            mapIconPlateList.add(new MapIconPlate(
                    graphic, userInterface,
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[] { x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_TOURH_R },
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), scale.get(i), scale.get(i), 0.0f, 255, false),
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), scale_feed.get(i), scale_feed.get(i), 0.0f, 255, false),
                    dungeonName.get(i),
                    dungeonNameExpress.get(i),
                    event.get(i)

            ));
        }


        MapIconPlate[] mapIconPlates = new MapIconPlate[mapIconPlateList.size()];
        mapIconPlateGroup = new PlateGroup<MapIconPlate>(mapIconPlateList.toArray(mapIconPlates));
        mapIconPlateListUpdate();
    }

    public void mapIconPlateListUpdate() {
        int size = database.getSize(DUNGEON_SELECT_BUTTON_TABLE_NAME);
        dungeonName = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "name");
        List<String> imageName = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "image_name");
        List<Integer> x = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "x");
        List<Integer> y = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "y");
        List<Integer> scale = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "scale");
        List<Integer> scale_feed = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "scale_feed");
        event = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "event");


        MapIconPlate mapIconPlates[] = mapIconPlateGroup.getPlates();
        boolean alphaFlag, geoEnterFlag;
        int clear = 0;
        for (int i = 0; i < mapIconPlates.length; i++) {
            switch (mapIconPlates[i].getMapIconName()) {
                case "Forest":
                    clear = 1;//mapStatus.getMapClearStatus(DUNGEON_KIND.FOREST.ordinal());
                    geoEnterFlag = (mapStatus.getMapClearStatus(DUNGEON_KIND.FOREST.ordinal()) == 1 || (playerStatus.getClearCount() > 0));
                    break;
                case "Lava":
                    clear = mapStatus.getMapClearStatus(DUNGEON_KIND.FOREST.ordinal());
                    geoEnterFlag = (mapStatus.getMapClearStatus(DUNGEON_KIND.LAVA.ordinal()) == 1 || (playerStatus.getClearCount() > 0));
                    break;
                case "Sea":
                    clear = mapStatus.getMapClearStatus(DUNGEON_KIND.LAVA.ordinal());
                    geoEnterFlag = (mapStatus.getMapClearStatus(DUNGEON_KIND.SEA.ordinal()) == 1 || (playerStatus.getClearCount() > 0));
                    break;
                case "Chess":
                    clear = mapStatus.getMapClearStatus(DUNGEON_KIND.SEA.ordinal());
                    geoEnterFlag = (mapStatus.getMapClearStatus(DUNGEON_KIND.SEA.ordinal()) == 1 || (playerStatus.getClearCount() > 0));
                    break;
                case "Swamp":
                    clear = mapStatus.getMapClearStatus(DUNGEON_KIND.CHESS.ordinal());
                    geoEnterFlag = (mapStatus.getMapClearStatus(DUNGEON_KIND.CHESS.ordinal()) == 1 || (playerStatus.getClearCount() > 0));
                    break;
                case "Haunted":
                    clear = mapStatus.getMapClearStatus(DUNGEON_KIND.SWAMP.ordinal());
                    geoEnterFlag = (mapStatus.getMapClearStatus(DUNGEON_KIND.SWAMP.ordinal()) == 1 || (playerStatus.getClearCount() > 0));
                    break;
                case "Dragon":
                    clear = mapStatus.getMapClearStatus(DUNGEON_KIND.HAUNTED.ordinal());
                    geoEnterFlag = (mapStatus.getMapClearStatus(DUNGEON_KIND.HAUNTED.ordinal()) == 1 || (playerStatus.getClearCount() > 0));
                    break;
                case "Maoh":
                    clear = 1;
                    geoEnterFlag = true;
                    break;
                default:
                    clear = 1;
                    geoEnterFlag = true;
                    break;
            }
            alphaFlag = (clear != 1) && (playerStatus.getNowClearCount() == playerStatus.getClearCount());

            mapIconPlates[i].setImageContext(
                    imageName.get(i),x.get(i), y.get(i), scale.get(i), scale.get(i), scale_feed.get(i), scale_feed.get(i), alphaFlag
            );
            mapIconPlates[i].setEnterFlag(!alphaFlag);
            mapIconPlates[i].setGeoEnterFlag(geoEnterFlag);
        }
    }

    private void initDungeonEnterSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        /*
        dungeonEnterSelectButtonGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "侵入する",
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
        */

        dungeonEnterSelectButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "侵入する",
                                textPaint
                        ),
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.NO_LEFT, SELECT_WINDOW.NO_UP, SELECT_WINDOW.NO_RIGHT, SELECT_WINDOW.NO_BOTTOM},
                                "やめる",
                                textPaint
                        )
                }
        );

        dungeonEnterSelectButtonGroup.setUpdateFlag(false);
        dungeonEnterSelectButtonGroup.setDrawFlag(false);
    }

    private void initMaohEnterSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        maohEnterSelectButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "魔王と戦う",
                                textPaint
                        ),
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.NO_LEFT, SELECT_WINDOW.NO_UP, SELECT_WINDOW.NO_RIGHT, SELECT_WINDOW.NO_BOTTOM},
                                "やめる",
                                textPaint
                        )
                }
        );
        maohEnterSelectButtonGroup.setUpdateFlag(false);
        maohEnterSelectButtonGroup.setDrawFlag(false);
    }


    PlateGroup<CircleImagePlate> loopCountSelectButtonGroup;
    private void initLoopCountSelectButton(){
        float tempScale = 2.0f;

        loopCountSelectButtonGroup = new PlateGroup<CircleImagePlate>(
                new CircleImagePlate[]{
                        new CircleImagePlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ LOOP_WINDOW.MENOS_X, LOOP_WINDOW.MENOS_Y, LOOP_WINDOW.MENOS_R },
                                graphic.makeImageContext(graphic.searchBitmap("矢印左"), LOOP_WINDOW.MENOS_X, LOOP_WINDOW.MENOS_Y, tempScale, tempScale, 0.0f, 254, false),
                                graphic.makeImageContext(graphic.searchBitmap("矢印左"), LOOP_WINDOW.MENOS_X - 30, LOOP_WINDOW.MENOS_Y, tempScale, tempScale, 0.0f, 254, false)
                        ) {
                            @Override
                            public void callBackEvent() {
                                //-ボタンが押された時の処理
                                soundAdmin.play("enter00");
                                playerStatus.subNowClearCountLoop();
                                loopCountTextBoxUpdate();
                                mapIconPlateListUpdate();
                            }
                        },
                        new CircleImagePlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ LOOP_WINDOW.PLUS_X, LOOP_WINDOW.PLUS_Y, LOOP_WINDOW.PLUS_R },
                                graphic.makeImageContext(graphic.searchBitmap("矢印右"), LOOP_WINDOW.PLUS_X, LOOP_WINDOW.PLUS_Y, tempScale, tempScale, 0.0f, 254, false),
                                graphic.makeImageContext(graphic.searchBitmap("矢印右"), LOOP_WINDOW.PLUS_X + 30, LOOP_WINDOW.PLUS_Y, tempScale, tempScale, 0.0f, 254, false)

                        ) {
                            @Override
                            public void callBackEvent() {
                                //-ボタンが押された時の処理
                                soundAdmin.play("enter00");
                                playerStatus.addNowClearCountLoop();
                                loopCountTextBoxUpdate();
                                mapIconPlateListUpdate();
                            }
                        }
                }
        );
        loopCountSelectButtonGroup.setUpdateFlag(false);
        loopCountSelectButtonGroup.setDrawFlag(false);
    }

    /*
    PlateGroup<BoxImageTextPlate> loopCountSelectButtonGroup;
    private void initLoopCountSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(LOOP_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        loopCountSelectButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ LOOP_WINDOW.MENOS_LEFT, LOOP_WINDOW.MENOS_UP, LOOP_WINDOW.MENOS_RIGHT, LOOP_WINDOW.MENOS_BOTTOM },
                                "-",
                                textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //-ボタンが押された時の処理
                                soundAdmin.play("enter00");
                                playerStatus.subNowClearCountLoop();
                                loopCountTextBoxUpdate();
                                mapIconPlateListUpdate();
                            }
                        },
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ LOOP_WINDOW.PLUS_LEFT, LOOP_WINDOW.PLUS_UP, LOOP_WINDOW.PLUS_RIGHT, LOOP_WINDOW.PLUS_BOTTOM },
                                "+",
                                textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //-ボタンが押された時の処理
                                soundAdmin.play("enter00");
                                playerStatus.addNowClearCountLoop();
                                loopCountTextBoxUpdate();
                                mapIconPlateListUpdate();
                            }
                        }
                }
        );
        loopCountSelectButtonGroup.setUpdateFlag(false);
        loopCountSelectButtonGroup.setDrawFlag(false);
    }
    */

    private void initTextBox() {
        enterTextBoxID = textBoxAdmin.createTextBox(SELECT_WINDOW.MESS_LEFT, SELECT_WINDOW.MESS_UP, SELECT_WINDOW.MESS_RIGHT, SELECT_WINDOW.MESS_BOTTOM, SELECT_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(enterTextBoxID, false);
        textBoxAdmin.setTextBoxExists(enterTextBoxID, false);
        enterTextPaint = new Paint();
        enterTextPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        enterTextPaint.setColor(Color.WHITE);

        loopCountTextBoxID = textBoxAdmin.createTextBox(LOOP_WINDOW.COUNT_LEFT, LOOP_WINDOW.COUNT_UP, LOOP_WINDOW.COUNT_RIGHT, LOOP_WINDOW.COUNT_BOTTOM, LOOP_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(loopCountTextBoxID, false);
        textBoxAdmin.setTextBoxExists(loopCountTextBoxID, false);
        loopCountTextPaint = new Paint();
        loopCountTextPaint.setTextSize(LOOP_WINDOW.TEXT_SIZE);
        loopCountTextPaint.setColor(Color.WHITE);
    }

    //***** draw関係 *****
    public void draw() {
        // ** Buttonの表示
        mapIconPlateGroup.draw();
        dungeonEnterSelectButtonGroup.draw();
        //menuButtonGroup.draw();

        maohEnterSelectButtonGroup.draw();
        loopCountSelectButtonGroup.draw();

        OkButtonGroup.draw();
    }

    //***** update関係 *****
    public void update() {
        /*
        if (selectMode == SELECT_MODE.GEOMAP_SELECT) {
            mapIconPlateCheckGeo();
        }
        if (selectMode == SELECT_MODE.DUNGEON_SELECT) {
            mapIconPlateCheck();
            if (enterSelectFlag) {
                dungeonEnterSelectButtonCheck();
            }
        }
        */


        //TODO いな依頼:同一フレームの同時タッチ問題→PlatePlateにおけるフラグ判定を予約タイプに変更する(set_falseした場合に実際にfalseになるのはupdate()の最後にする。)ことで解決できる。
        dungeonEnterSelectButtonCheck();
        dungeonEnterSelectButtonGroup.update();

        maohEnterSelectButtonCheck();
        maohEnterSelectButtonGroup.update();

        mapIconPlateCheck();
        mapIconPlateGroup.update();

        loopCountSelectButtonGroup.update();
        OkButtonGroup.update();

        //modeSelectButtonCheck();
        //menuButtonGroup.update();

        if (initUIsFlag) {
            initUIs();
            initUIsFlag = false;
        }

    }

    //注 : 紛らわしいが、DungeonSelectButtonはGeoMapSelectとDungeonSelectとで共通になっている
    public void mapIconPlateCheck() {
        int buttonID = mapIconPlateGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            //この間、マップアイコンなどの操作を受け付けない
            //menuButtonGroup.setUpdateFlag(false);
            mapIconPlateGroup.setUpdateFlag(false);

            focusDungeonButtonID = buttonID;
            //TODO plateGroupの内部のT型配列を返す関数が欲しい。eventの確認のため
            //if mapIconPlateGroup.
            //ボタンに登録されているイベント名を参照して、それそれの場合の結果を返す
            if (event.get(focusDungeonButtonID).equals("dungeon")) {
                if (worldModeAdmin.getMode() == WORLD_MODE.DUNGEON_SELECT) {
                    soundAdmin.play("enter00");
                    if (dungeonEnterCheck()) {
                        enterTextBoxUpdateDungeon();
                        dungeonEnterSelectButtonGroup.setUpdateFlag(true);
                        dungeonEnterSelectButtonGroup.setDrawFlag(true);
                    };
                }
                if (worldModeAdmin.getMode() == WORLD_MODE.GEO_MAP_SELECT) {
                    soundAdmin.play("enter00");
                    geoSlotAdminManager.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
                    if (mapIconPlateGroup.getPlates(focusDungeonButtonID).getGeoEnterFlag()) {
                        worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_INIT);
                    } else {
                        worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_SEE_ONLY_INIT);
                    }
                    initUIsFlag = true;
                }
            }

            if (event.get(focusDungeonButtonID).equals("shop")) {
                soundAdmin.play("enter00");
                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.SHOP_INIT);
                initUIsFlag = true;

            }
            if (event.get(focusDungeonButtonID).equals("present")) {
                soundAdmin.play("enter00");
                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.PRESENT_INIT);
                initUIsFlag = true;
            }
            if (event.get(focusDungeonButtonID).equals("maoh")) {
                soundAdmin.play("enter00");
                if (worldModeAdmin.getMode() == WORLD_MODE.DUNGEON_SELECT) {
                    if (dungeonEnterCheck()) {
                        enterTextBoxUpdateMaoh();
                        maohEnterSelectButtonGroup.setUpdateFlag(true);
                        maohEnterSelectButtonGroup.setDrawFlag(true);
                    }
                }
                if (worldModeAdmin.getMode() == WORLD_MODE.GEO_MAP_SELECT) {
                    geoSlotAdminManager.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
                    worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_INIT);
                    initUIsFlag = true;
                }
            }
            if (event.get(focusDungeonButtonID).equals("map")) {
                soundAdmin.play("enter00");
                switchSelectMode();
                initUIsFlag = true;
            }
            if (event.get(focusDungeonButtonID).equals("equip")) {
                soundAdmin.play("enter00");
                initUIsFlag = true;
                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.EQUIP_INIT);
            }
            if (event.get(focusDungeonButtonID).equals("option")) {
                soundAdmin.play("enter00");
                initUIsFlag = true;
            }
            if (event.get(focusDungeonButtonID).equals("credit")) {
                soundAdmin.play("enter00");
                initUIsFlag = true;
                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.CREDIT);//TODO : 0612先輩、遷移先のMODEを指定
            }
            if (event.get(focusDungeonButtonID).equals("loop")) {
                soundAdmin.play("enter00");
                //ループ回数セッティング
                loopCountTextBoxUpdate();
                loopCountSelectButtonGroup.setDrawFlag(true);
                loopCountSelectButtonGroup.setUpdateFlag(true);
                OkButtonGroup.setDrawFlag(true);
                OkButtonGroup.setUpdateFlag(true);
                //initUIsFlag = true;
            }
        }
    }

    /*
    public void modeSelectButtonCheck() {
        int buttonID = menuButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //Map
            switchSelectMode();
        }
        if (buttonID == 1 ) { //Equip
            initUIs();
            worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.EQUIP);
        }
    }
    */

    private boolean dungeonEnterCheck() {
        System.out.println("takano geo : " + geoInventry.getInventryNum());
        if (geoInventry.getInventryNum() > playerStatus.getGeoInventryMaxNum()) {
            enterTextBoxUpdateInventryMax(Constants.Item.ITEM_KIND.GEO);
            OkButtonGroup.setUpdateFlag(true);
            OkButtonGroup.setDrawFlag(true);
            return false;
        }
        System.out.println("takano expend : " + expendItemInventry.getInventryNum());
        if (expendItemInventry.getInventryNum() > playerStatus.getExpendInvetryMaxNum()) {
            enterTextBoxUpdateInventryMax(Constants.Item.ITEM_KIND.EXPEND);
            OkButtonGroup.setUpdateFlag(true);
            OkButtonGroup.setDrawFlag(true);
            return false;
        }
        System.out.println("takano equipment : " + equipmentInventry.getInventryNum());
        if (equipmentInventry.getInventryNum() > playerStatus.getEquipmentInventryMaxNum()) {
            enterTextBoxUpdateInventryMax(Constants.Item.ITEM_KIND.EQUIPMENT);
            OkButtonGroup.setUpdateFlag(true);
            OkButtonGroup.setDrawFlag(true);
            return false;
        }

        if (!mapIconPlateGroup.getPlates(focusDungeonButtonID).getEnterFlag()) {
            enterTextBoxUpdateNotAccept();
            OkButtonGroup.setUpdateFlag(true);
            OkButtonGroup.setDrawFlag(true);
            return false;
        }
        return true;
    }



    public void dungeonEnterSelectButtonCheck() {
        if (!(dungeonEnterSelectButtonGroup.getUpdateFlag() && worldModeAdmin.getMode() == WORLD_MODE.DUNGEON_SELECT)) {
            return;
        }

        int buttonID = dungeonEnterSelectButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //侵入する
            soundAdmin.play("enter00");
            playerStatus.setNowHPMax();
            initUIsFlag = true;
            MapIconPlate tmp = (MapIconPlate)mapIconPlateGroup.getPlate(focusDungeonButtonID);
            String dungeonName = tmp.getMapIconName();
            Constants.DungeonKind.DUNGEON_KIND dungeonKind;

            switch(dungeonName) {
                case "Chess": dungeonKind = Constants.DungeonKind.DUNGEON_KIND.CHESS; break;
                case "Dragon": dungeonKind = Constants.DungeonKind.DUNGEON_KIND.DRAGON; break;
                case "Haunted": dungeonKind = Constants.DungeonKind.DUNGEON_KIND.HAUNTED; break;
                case "Forest": dungeonKind = Constants.DungeonKind.DUNGEON_KIND.FOREST; break;
                case "Lava": dungeonKind = Constants.DungeonKind.DUNGEON_KIND.LAVA; break;
                case "Swamp": dungeonKind = Constants.DungeonKind.DUNGEON_KIND.SWAMP; break;
                case "Sea": dungeonKind = Constants.DungeonKind.DUNGEON_KIND.SEA; break;
                default: dungeonKind = Constants.DungeonKind.DUNGEON_KIND.GOKI; break;
            }

            activityChange.toDungeonActivity(dungeonKind);
        }
        if (buttonID == 1 ) { //やめる
            soundAdmin.play("cancel00");
            initUIsFlag = true;
        }
    }

    public void maohEnterSelectButtonCheck() {
        if (!maohEnterSelectButtonGroup.getUpdateFlag() || !(worldModeAdmin.getMode() == WORLD_MODE.DUNGEON_SELECT)) {
            return;
        }

        int buttonID = maohEnterSelectButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //挑戦する
            soundAdmin.play("enter00");
            initUIsFlag = true;

            activityChange.toDungeonActivity(Constants.DungeonKind.DUNGEON_KIND.MAOH);

            //dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT);
        }
        if (buttonID == 1 ) { //やめる
            soundAdmin.play("cancel00");
            initUIsFlag = true;
        }
    }

    //TExtBox
    public void enterTextBoxUpdateMaoh() {
        textBoxAdmin.setTextBoxExists(enterTextBoxID, true);
        textBoxAdmin.resetTextBox(enterTextBoxID);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "魔王に挑戦しますか？", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "現在の討伐回数 : ", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, String.valueOf(playerStatus.getMaohWinCount()), enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "MOP", enterTextPaint);

        textBoxAdmin.updateText(enterTextBoxID);
    }

    public void enterTextBoxUpdateCountUp() {
        textBoxAdmin.setTextBoxExists(enterTextBoxID, true);
        textBoxAdmin.resetTextBox(enterTextBoxID);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "全ダンジョンを制覇した！", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "しかし、", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "戦いはまだ終わらない…　", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "ループ回数：", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, String.valueOf(playerStatus.getClearCount()), enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "MOP", enterTextPaint);

        textBoxAdmin.updateText(enterTextBoxID);
    }


    public void enterTextBoxUpdateDungeon() {
        MapIconPlate tmp = (MapIconPlate)mapIconPlateGroup.getPlate(focusDungeonButtonID);

        textBoxAdmin.setTextBoxExists(enterTextBoxID, true);
        textBoxAdmin.resetTextBox(enterTextBoxID);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "ダンジョン名 : ", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, tmp.getDungeonName(), enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "このダンジョンに入りますか？", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "MOP", enterTextPaint);

        textBoxAdmin.updateText(enterTextBoxID);
    }

    public void enterTextBoxUpdateNotAccept() {
        MapIconPlate tmp = (MapIconPlate)mapIconPlateGroup.getPlate(focusDungeonButtonID);

        textBoxAdmin.setTextBoxExists(enterTextBoxID, true);
        textBoxAdmin.resetTextBox(enterTextBoxID);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "ダンジョン名 : ", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, tmp.getDungeonName(), enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "このダンジョンにはまだ侵入できません", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "MOP", enterTextPaint);

        textBoxAdmin.updateText(enterTextBoxID);
    }

    public void enterTextBoxUpdateInventryMax(Constants.Item.ITEM_KIND inventryKind) {
        textBoxAdmin.setTextBoxExists(enterTextBoxID, true);

        textBoxAdmin.bookingDrawText(enterTextBoxID, "インベントリの容量がオーバーしているため侵入できません", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        switch(inventryKind) {
            case GEO:
                textBoxAdmin.bookingDrawText(enterTextBoxID, "ジオオブジェクト : " + geoInventry.getInventryNum() + " / " + playerStatus.getGeoInventryMaxNum(), enterTextPaint);
                break;
            case EQUIPMENT:
                textBoxAdmin.bookingDrawText(enterTextBoxID,"装備品 : " +  equipmentInventry.getInventryNum() + " / " + playerStatus.getEquipmentInventryMaxNum(), enterTextPaint);
                break;
            case EXPEND:
                textBoxAdmin.bookingDrawText(enterTextBoxID,"消費アイテム : " +  expendItemInventry.getInventryNum() + " / " + playerStatus.getExpendInvetryMaxNum(), enterTextPaint);
                break;
        }
        textBoxAdmin.bookingDrawText(enterTextBoxID, "MOP", loopCountTextPaint);


        textBoxAdmin.updateText(enterTextBoxID);
    }

    public void loopCountTextBoxUpdate() {
        textBoxAdmin.setTextBoxExists(loopCountTextBoxID, true);

        textBoxAdmin.bookingDrawText(loopCountTextBoxID, "\n", loopCountTextPaint);
        textBoxAdmin.bookingDrawText(loopCountTextBoxID, "" + playerStatus.getNowClearCount() + " / " + playerStatus.getClearCount(), loopCountTextPaint);
        textBoxAdmin.bookingDrawText(loopCountTextBoxID, "MOP", loopCountTextPaint);

        textBoxAdmin.updateText(loopCountTextBoxID);
    }

    private void initUIs() {
        dungeonEnterSelectButtonGroup.setUpdateFlag(false);
        dungeonEnterSelectButtonGroup.setDrawFlag(false);
        maohEnterSelectButtonGroup.setUpdateFlag(false);
        maohEnterSelectButtonGroup.setDrawFlag(false);
        loopCountSelectButtonGroup.setUpdateFlag(false);
        loopCountSelectButtonGroup.setDrawFlag(false);
        OkButtonGroup.setUpdateFlag(false);
        OkButtonGroup.setDrawFlag(false);
        textBoxAdmin.setTextBoxExists(enterTextBoxID, false);
        textBoxAdmin.setTextBoxExists(loopCountTextBoxID, false);

        //menuButtonGroup.setUpdateFlag(true);
        mapIconPlateGroup.setUpdateFlag(true);
    }

    PlateGroup<BoxImageTextPlate> OkButtonGroup;
    private void initOkButton() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(POPUP_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255, 255, 255, 255);

        OkButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{POPUP_WINDOW.OK_LEFT, POPUP_WINDOW.OK_UP, POPUP_WINDOW.OK_RIGHT, POPUP_WINDOW.OK_BOTTOM}, "OK", textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //OKが押された時の処理
                                soundAdmin.play("enter00");
                                initUIs();
                            }
                        }
                });
        OkButtonGroup.setUpdateFlag(false);
        OkButtonGroup.setDrawFlag(false);
    }


}

        /*
        int size = 2;

        List<BoxTextPlate> dungeonEnterSelectButtonList = new ArrayList<BoxTextPlate>();
        List<Integer> x1 = new ArrayList<Integer>();
        List<Integer> y1 = new ArrayList<Integer>();
        List<Integer> x2 = new ArrayList<Integer>();
        List<Integer> y2 = new ArrayList<Integer>();
        List<String> text = new ArrayList<String>();

        text.add("侵入する");
        text.add("やめる");

        x1.add(500);
        y1.add(600);
        x2.add(700);
        y2.add(700);

        x1.add(800);
        y1.add(600);
        x2.add(1000);
        y2.add(700);

        Paint textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setARGB(255,255,0,0);

        //インスタンス化
        for (int i = 0; i < size; i++) {

            dungeonEnterSelectButtonList.add(new BoxTextPlate(
                    graphic, userInterface,
                    new Paint(),
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[] { x1.get(i), y1.get(i), x2.get(i), y2.get(i) },
                    text.get(i),
                    textPaint
            ));
        }
        BoxTextPlate[] dungeonEnterSelectButton = new BoxTextPlate[dungeonEnterSelectButtonList.size()];
        dungeonEnterSelectButtonGroup = new PlateGroup<BoxTextPlate>(dungeonEnterSelectButtonList.toArray(dungeonEnterSelectButton));
        */
