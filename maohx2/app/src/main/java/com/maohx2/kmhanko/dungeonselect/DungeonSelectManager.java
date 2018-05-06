package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.ActivityChange;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.ina.Constants.GAMESYSTEN_MODE.WORLD_MODE;

import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;

import com.maohx2.ina.Constants.WorldMap.SELECT_MODE;

import android.graphics.Color;
import android.graphics.Paint;

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

    static final float DUNGEON_SELECT_BUTTON_RATE_DEFAULT = 6.0f;
    static final float DUNGEON_SELECT_BUTTON_RATE_FEEDBACK = 8.0f;
    static final int DUNGEON_SELECT_BUTTON_RATE_TOURH_R = 120;

    private boolean isDungeonSelectActive = false;

    int enterTextBoxID;
    Paint enterTextPaint;

    Graphic graphic;
    UserInterface userInterface;
    MyDatabaseAdmin databaseAdmin;
    GeoSlotAdminManager geoSlotAdminManager;
    WorldModeAdmin worldModeAdmin;
    TextBoxAdmin textBoxAdmin;

    MyDatabase database;

    //TODO いなの関数待ち
    List<String> dungeonName;
    List<String> event;

    PlateGroup<MapIconPlate> mapIconPlateGroup;

    PlateGroup<BoxTextPlate> dungeonInformationPlate;
    PlateGroup<BoxTextPlate> dungeonEnterSelectButtonGroup;
    PlateGroup<BoxTextPlate> maohEnterSelectButtonGroup;

    PlateGroup<CircleImagePlate> menuButtonGroup;

    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";

    String tableName = "dungeon_select_button";

    int focusDungeonButtonID;

    //SELECT_MODE selectMode = SELECT_MODE.DUNGEON_SELECT;

    Paint paint = new Paint(); //TODO GeoMapとDungeonSelectの切り替え表示用。いつか消える

    PlayerStatus playerStatus;

   //WorldActivity worldActivity;
    ActivityChange activityChange;

    //いなの実装までの仮置き
    //boolean enterSelectFlag = false;

    public DungeonSelectManager(Graphic _graphic, UserInterface _userInterface, TextBoxAdmin _textBoxAdmin, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, GeoSlotAdminManager _geoSlotAdminManager, PlayerStatus _playerStatus, ActivityChange _activityChange) {
        graphic = _graphic;
        userInterface = _userInterface;
        textBoxAdmin = _textBoxAdmin;
        databaseAdmin = _databaseAdmin;
        geoSlotAdminManager = _geoSlotAdminManager;
        worldModeAdmin = _worldModeAdmin;
        //worldActivity = _worldActivity;
        activityChange = _activityChange;
        playerStatus = _playerStatus;

        setDatabase(databaseAdmin);
        initMapIconPlate();
        initDungeonEnterSelectButton();
        initMaohEnterSelectButton();
        initModeSelectButton();
        initTextBox();
        initUIs();

        //TODO : Loopselect
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
        int size = database.getSize(tableName);

        dungeonName = database.getString(tableName, "name");
        List<String> imageName = database.getString(tableName, "image_name");
        List<Integer> x = database.getInt(tableName, "x");
        List<Integer> y = database.getInt(tableName, "y");
        List<Integer> scale = database.getInt(tableName, "scale");
        List<Integer> scale_feed = database.getInt(tableName, "scale_feed");
        event = database.getString(tableName, "event");

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
                    event.get(i)

            ));
        }

        MapIconPlate[] mapIconPlates = new MapIconPlate[mapIconPlateList.size()];
        mapIconPlateGroup = new PlateGroup<MapIconPlate>(mapIconPlateList.toArray(mapIconPlates));
    }

    private void initDungeonEnterSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

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
        dungeonEnterSelectButtonGroup.setUpdateFlag(false);
        dungeonEnterSelectButtonGroup.setDrawFlag(false);
    }

    private void initMaohEnterSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        maohEnterSelectButtonGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "魔王と戦う",
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
        maohEnterSelectButtonGroup.setUpdateFlag(false);
        maohEnterSelectButtonGroup.setDrawFlag(false);
    }

    private void initModeSelectButton() {
        int x = 1500;
        int y[] = { 100,300,500,700 };

        float defaultRate = DUNGEON_SELECT_BUTTON_RATE_DEFAULT;
        float feedbackRate = DUNGEON_SELECT_BUTTON_RATE_FEEDBACK;

        menuButtonGroup = new PlateGroup<CircleImagePlate>(
                new CircleImagePlate[]{
                        new CircleImagePlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ x, y[0], 100 },
                                graphic.makeImageContext(
                                        graphic.searchBitmap("マップ"),
                                        x, y[0],
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false),
                                graphic.makeImageContext(
                                        graphic.searchBitmap("マップ"),
                                        x, y[0],
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false)
                        ),
                        new CircleImagePlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ x, y[1], 100 },
                                graphic.makeImageContext(
                                        graphic.searchBitmap("武器"),
                                        x, y[1],
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false),
                                graphic.makeImageContext(
                                        graphic.searchBitmap("武器"),
                                        x, y[1],
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false)
                        ),
                        new CircleImagePlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ x, y[2], 100 },
                                graphic.makeImageContext(
                                        graphic.searchBitmap("オプション"),
                                        x, y[2],
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false),
                                graphic.makeImageContext(
                                        graphic.searchBitmap("オプション"),
                                        x, y[2],
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false)
                        )
                }
        );
    }

    private void initTextBox() {
        enterTextBoxID = textBoxAdmin.createTextBox(SELECT_WINDOW.MESS_LEFT, SELECT_WINDOW.MESS_UP, SELECT_WINDOW.MESS_RIGHT, SELECT_WINDOW.MESS_BOTTOM, SELECT_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(enterTextBoxID, false);
        textBoxAdmin.setTextBoxExists(enterTextBoxID, false);
        enterTextPaint = new Paint();
        enterTextPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        enterTextPaint.setColor(Color.WHITE);
    }

    //***** draw関係 *****
    public void draw() {
        // ** Buttonの表示
        mapIconPlateGroup.draw();
        dungeonEnterSelectButtonGroup.draw();
        menuButtonGroup.draw();

        maohEnterSelectButtonGroup.draw();
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

        modeSelectButtonCheck();
        menuButtonGroup.update();

    }

    //注 : 紛らわしいが、DungeonSelectButtonはGeoMapSelectとDungeonSelectとで共通になっている
    public void mapIconPlateCheck() {
        int buttonID = mapIconPlateGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            //この間、マップアイコンなどの操作を受け付けない
            menuButtonGroup.setUpdateFlag(false);
            mapIconPlateGroup.setUpdateFlag(false);

            focusDungeonButtonID = buttonID;
            //TODO plateGroupの内部のT型配列を返す関数が欲しい。eventの確認のため
            //if mapIconPlateGroup.
            //ボタンに登録されているイベント名を参照して、それそれの場合の結果を返す
            if (event.get(focusDungeonButtonID).equals("dungeon")) {
                if (worldModeAdmin.getMode() == WORLD_MODE.DUNGEON_SELECT) {
                    enterTextBoxUpdateDungeon();
                    dungeonEnterSelectButtonGroup.setUpdateFlag(true);
                    dungeonEnterSelectButtonGroup.setDrawFlag(true);

                }
                if (worldModeAdmin.getMode() == WORLD_MODE.GEO_MAP_SELECT) {
                    geoSlotAdminManager.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
                    worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_INIT);
                    initUIs();
                }
            }

            if (event.get(focusDungeonButtonID).equals("shop")) {
                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.SHOP_INIT);
                initUIs();

            }
            if (event.get(focusDungeonButtonID).equals("present")) {
                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.PRESENT_INIT);
                initUIs();
            }
            if (event.get(focusDungeonButtonID).equals("maoh")) {
                enterTextBoxUpdateMaoh();
                dungeonEnterSelectButtonGroup.setUpdateFlag(true);
                dungeonEnterSelectButtonGroup.setDrawFlag(true);
            }
        }
    }

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

    public void dungeonEnterSelectButtonCheck() {
        if (!(dungeonEnterSelectButtonGroup.getUpdateFlag() && worldModeAdmin.getMode() == WORLD_MODE.DUNGEON_SELECT)) {
            return;
        }

        int buttonID = dungeonEnterSelectButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //侵入する
            initUIs();

            //TODO 侵入処理におけるダンジョンデータによる分岐処理
            /*
            MapIconPlate tmp = (MapIconPlate)mapIconPlateGroup.getPlate(focusDungeonButtonID);

            tmp.getMapIconName()
            */

            activityChange.toDungeonActivity(Constants.DungeonKind.DUNGEON_KIND.GOKI);
        }
        if (buttonID == 1 ) { //やめる
            initUIs();
        }
    }

    public void maohEnterSelectButtonCheck() {
        if (!(maohEnterSelectButtonGroup.getUpdateFlag() && worldModeAdmin.getMode() == WORLD_MODE.GEO_MAP_SELECT)) {
            return;
        }

        int buttonID = maohEnterSelectButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //挑戦する
            initUIs();

            //TODO 魔王の画面へ行く
            //activityChange.toDungeonActivity(Constants.DungeonKind.DUNGEON_KIND.GOKI);
        }
        if (buttonID == 1 ) { //やめる
            initUIs();
        }
    }

    //TExtBox
    public void enterTextBoxUpdateMaoh() {
        textBoxAdmin.setTextBoxExists(enterTextBoxID, true);

        textBoxAdmin.bookingDrawText(enterTextBoxID, "魔王に挑戦しますか？", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "現在の討伐回数 : ", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, String.valueOf(playerStatus.getMaohWinCount()), enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "MOP", enterTextPaint);

        textBoxAdmin.updateText(enterTextBoxID);
    }

    public void enterTextBoxUpdateDungeon() {
        MapIconPlate tmp = (MapIconPlate)mapIconPlateGroup.getPlate(focusDungeonButtonID);

        textBoxAdmin.setTextBoxExists(enterTextBoxID, true);

        textBoxAdmin.bookingDrawText(enterTextBoxID, "ダンジョン名 : ", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, tmp.getMapIconName(), enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "\n", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "このダンジョンに入りますか？", enterTextPaint);
        textBoxAdmin.bookingDrawText(enterTextBoxID, "MOP", enterTextPaint);

        textBoxAdmin.updateText(enterTextBoxID);
    }

    private void initUIs() {
        dungeonEnterSelectButtonGroup.setUpdateFlag(false);
        dungeonEnterSelectButtonGroup.setDrawFlag(false);
        maohEnterSelectButtonGroup.setUpdateFlag(false);
        maohEnterSelectButtonGroup.setDrawFlag(false);
        textBoxAdmin.setTextBoxExists(enterTextBoxID, false);

        menuButtonGroup.setUpdateFlag(true);
        mapIconPlateGroup.setUpdateFlag(true);
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
