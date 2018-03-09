package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.ina.WorldActivity;
import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;

import com.maohx2.ina.Constants.WorldMap.SELECT_MODE;

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

    Graphic graphic;
    UserInterface userInterface;
    MyDatabaseAdmin databaseAdmin;
    GeoSlotAdminManager geoSlotAdminManager;
    WorldModeAdmin worldModeAdmin;

    MyDatabase database;

    //TODO いなの関数待ち
    List<String> dungeonName;
    List<String> event;

    PlateGroup<MapIconPlate> mapIconPlateGroup;

    PlateGroup<BoxTextPlate> dungeonInformationPlate;
    PlateGroup<BoxTextPlate> dungeonEnterSelectButtonGroup;

    PlateGroup<CircleImagePlate> modeSelectButtonGroup;

    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";

    String tableName = "dungeon_select_button";

    int focusDungeonButtonID;

    SELECT_MODE selectMode = SELECT_MODE.DUNGEON_SELECT;

    Paint paint = new Paint(); //TODO GeoMapとDungeonSelectの切り替え表示用。いつか消える

    WorldActivity worldActivity;

    //いなの実装までの仮置き
    boolean enterSelectFlag = false;

    public DungeonSelectManager(Graphic _graphic, UserInterface _userInterface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, GeoSlotAdminManager _geoSlotAdminManager, WorldActivity _worldActivity) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;
        geoSlotAdminManager = _geoSlotAdminManager;
        worldModeAdmin = _worldModeAdmin;
        worldActivity = _worldActivity;

        setDatabase(databaseAdmin);
        loadMapIconPlate();
        loadDungeonEnterSelectButton();
        loadModeSelectButton();

        //TODO : Loopselect
    }

    private void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    //***** GeoMapとDungeonSelectMapの切り替え *****
    public void switchSelectMode() {
        if (selectMode == SELECT_MODE.GEOMAP_SELECT) {
            selectMode = SELECT_MODE.DUNGEON_SELECT;
        } else {
            selectMode = SELECT_MODE.GEOMAP_SELECT;
        }
    }
    public void switchSelectModeDungeon() {
        selectMode = SELECT_MODE.DUNGEON_SELECT;
    }
    public void switchSelectModeGeoMap() {
        selectMode = SELECT_MODE.GEOMAP_SELECT;
    }


    //***** ButtonのLoad関係 *****
    private void loadMapIconPlate(){
        int size = database.getSize(tableName);

        dungeonName = database.getString(tableName, "name");
        List<String> imageName = database.getString(tableName, "image_name");
        List<Integer> x = database.getInt(tableName, "x");
        List<Integer> y = database.getInt(tableName, "y");
        event = database.getString(tableName, "event");

        List<MapIconPlate> mapIconPlateList = new ArrayList<MapIconPlate>();

        //インスタンス化
        for (int i = 0; i < size; i++) {
            mapIconPlateList.add(new MapIconPlate(
                    graphic, userInterface,
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[] { x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_TOURH_R },
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_DEFAULT, DUNGEON_SELECT_BUTTON_RATE_DEFAULT, 0.0f, 255, false),
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_FEEDBACK, DUNGEON_SELECT_BUTTON_RATE_FEEDBACK, 0.0f, 255, false),
                    dungeonName.get(i),
                    event.get(i)

            ));
        }

        MapIconPlate[] mapIconPlates = new MapIconPlate[mapIconPlateList.size()];
        mapIconPlateGroup = new PlateGroup<MapIconPlate>(mapIconPlateList.toArray(mapIconPlates));
    }

    private void loadDungeonEnterSelectButton(){

        Paint textPaint = new Paint();
        textPaint.setTextSize(60f);
        textPaint.setARGB(255,255,255,255);

        dungeonEnterSelectButtonGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 50, 1550, 200},
                                "侵入する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 450, 1550, 600},
                                "やめる",
                                textPaint
                        )
                }
        );

        enterSelectFlag = false;
    }

    private void loadModeSelectButton() {
        int x = 1500;
        int y = 100;
        float defaultRate = DUNGEON_SELECT_BUTTON_RATE_DEFAULT;
        float feedbackRate = DUNGEON_SELECT_BUTTON_RATE_FEEDBACK;

        modeSelectButtonGroup = new PlateGroup<CircleImagePlate>(
                new CircleImagePlate[]{
                        new CircleImagePlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{ x, y, 100 },
                                graphic.makeImageContext(
                                        graphic.searchBitmap("マップ"),
                                        x, y,
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false),
                                graphic.makeImageContext(
                                        graphic.searchBitmap("マップ"),
                                        x, y,
                                        defaultRate, feedbackRate,
                                        0.0f, 255, false)
                        )
                }
        );
    }

    //***** draw関係 *****
    public void draw() {
        // ** GeoMap / DungeonSelectの表示 **
        if (selectMode == SELECT_MODE.DUNGEON_SELECT) {
            paint.setARGB(255, 255, 255, 255);
        }
        if (selectMode == SELECT_MODE.GEOMAP_SELECT) {
            paint.setARGB(255, 255, 255, 128);
        }
        graphic.bookingDrawRect(0, 0, 1600, 900, paint);


        // ** Buttonの表示
        mapIconPlateGroup.draw();
        if (enterSelectFlag) {
            dungeonEnterSelectButtonGroup.draw();
        }
        modeSelectButtonGroup.draw();
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
        mapIconPlateCheck();
        if (enterSelectFlag && selectMode == SELECT_MODE.DUNGEON_SELECT) {
            dungeonEnterSelectButtonCheck();
        }

        modeSelectButtonCheck();

        mapIconPlateGroup.update();
        if (enterSelectFlag) {
            dungeonEnterSelectButtonGroup.update();
        }
        modeSelectButtonGroup.update();
    }

    //注 : 紛らわしいが、DungeonSelectButtonはGeoMapSelectとDungeonSelectとで共通になっている
    public void mapIconPlateCheck() {
        int buttonID = mapIconPlateGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            focusDungeonButtonID = buttonID;
            //TODO plateGroupの内部のT型配列を返す関数が欲しい。eventの確認のため
            //if mapIconPlateGroup.
            if (event.get(focusDungeonButtonID).equals("dungeon")) {
                if (selectMode == SELECT_MODE.DUNGEON_SELECT) {
                    enterSelectFlag = true;
                }
                if (selectMode == SELECT_MODE.GEOMAP_SELECT) {
                    geoSlotAdminManager.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
                    worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
                    worldModeAdmin.setGeoSlotMap(Constants.Mode.ACTIVATE.ACTIVE);
                }
            }

            if (event.get(focusDungeonButtonID).equals("shop")) {
                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
                worldModeAdmin.setShop(Constants.Mode.ACTIVATE.ACTIVE);
            }
            if (event.get(focusDungeonButtonID).equals("present")) {
                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
                worldModeAdmin.setPresent(Constants.Mode.ACTIVATE.ACTIVE);
            }
        }
    }

    /*
    public void mapIconPlateCheckGeo() {
        int buttonID = mapIconPlateGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            //GeoSlotMapへの移動処理
            if (event.get(buttonID).equals("dungeon")) {
                geoSlotAdminManager.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
                worldModeAdmin.setGeoSlotMap(Constants.Mode.ACTIVATE.ACTIVE);
            }

            enterSelectFlag = false;
        }
    }*/

    public void modeSelectButtonCheck() {
        int buttonID = modeSelectButtonGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            switchSelectMode();
        }
    }

    public void dungeonEnterSelectButtonCheck() {
        int buttonID = dungeonEnterSelectButtonGroup.getTouchContentNum();

        if (buttonID == 0 ) { //侵入する
            //侵入処理

            //TODO 仮
            //worldActivity.goToBattleActivity();

            //worldModeAdmin.setDungeon(Constants.Mode.ACTIVATE.ACTIVE);
            enterSelectFlag = false;
        }
        if (buttonID == 1 ) { //やめる
            enterSelectFlag = false;
        }
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
