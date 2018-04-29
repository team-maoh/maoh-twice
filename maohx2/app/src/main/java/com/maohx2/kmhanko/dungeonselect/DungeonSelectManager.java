package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.ina.ActivityChange;
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
    PlateGroup<BoxTextPlate> maohEnterSelectButtonGroup;

    PlateGroup<CircleImagePlate> menuButtonGroup;

    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";

    String tableName = "dungeon_select_button";

    int focusDungeonButtonID;

    SELECT_MODE selectMode = SELECT_MODE.DUNGEON_SELECT;

    Paint paint = new Paint(); //TODO GeoMapとDungeonSelectの切り替え表示用。いつか消える

   //WorldActivity worldActivity;
    ActivityChange activityChange;

    //いなの実装までの仮置き
    //boolean enterSelectFlag = false;

    public DungeonSelectManager(Graphic _graphic, UserInterface _userInterface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, GeoSlotAdminManager _geoSlotAdminManager, ActivityChange _activityChange) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;
        geoSlotAdminManager = _geoSlotAdminManager;
        worldModeAdmin = _worldModeAdmin;
        //worldActivity = _worldActivity;
        activityChange = _activityChange;

        setDatabase(databaseAdmin);
        loadMapIconPlate();
        loadDungeonEnterSelectButton();
        loadMaohEnterSelectButton();
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
                                new int[]{300, 550, 700, 700},
                                "侵入する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{900, 550, 1300, 700},
                                "やめる",
                                textPaint
                        )
                }
        );
        dungeonEnterSelectButtonGroup.setUpdateFlag(false);
        dungeonEnterSelectButtonGroup.setDrawFlag(false);
    }

    private void loadMaohEnterSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(60f);
        textPaint.setARGB(255,255,255,255);

        maohEnterSelectButtonGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{300, 550, 700, 700},
                                "魔王と戦う",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{900, 550, 1300, 700},
                                "やめる",
                                textPaint
                        )
                }
        );
        maohEnterSelectButtonGroup.setUpdateFlag(false);
        maohEnterSelectButtonGroup.setDrawFlag(false);
    }

    private void loadModeSelectButton() {
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
        dungeonEnterSelectButtonGroup.draw();
        maohEnterSelectButtonGroup.draw();
        menuButtonGroup.draw();
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
                if (selectMode == SELECT_MODE.DUNGEON_SELECT) {
                    dungeonEnterSelectButtonGroup.setUpdateFlag(true);
                    dungeonEnterSelectButtonGroup.setDrawFlag(true);

                }
                if (selectMode == SELECT_MODE.GEOMAP_SELECT) {
                    geoSlotAdminManager.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
                    worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
                    worldModeAdmin.setGeoSlotMap(Constants.Mode.ACTIVATE.ACTIVE);
                    menuButtonGroup.setUpdateFlag(true);//TODO よくない。モード間モードを実装する
                    mapIconPlateGroup.setUpdateFlag(true);
                }
            }

            if (event.get(focusDungeonButtonID).equals("shop")) {
                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
                worldModeAdmin.setShop(Constants.Mode.ACTIVATE.ACTIVE);
                menuButtonGroup.setUpdateFlag(true);
                mapIconPlateGroup.setUpdateFlag(true);

            }
            if (event.get(focusDungeonButtonID).equals("present")) {
                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
                worldModeAdmin.setPresent(Constants.Mode.ACTIVATE.ACTIVE);
                menuButtonGroup.setUpdateFlag(true);
                mapIconPlateGroup.setUpdateFlag(true);
            }
            if (event.get(focusDungeonButtonID).equals("maoh")) {
                maohEnterSelectButtonGroup.setUpdateFlag(true);
                maohEnterSelectButtonGroup.setDrawFlag(true);
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
        int buttonID = menuButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //Map
            switchSelectMode();
        }
        if (buttonID == 1 ) { //Equip
            dungeonEnterSelectButtonGroup.setUpdateFlag(false);
            dungeonEnterSelectButtonGroup.setDrawFlag(false);
            //menuButtonGroup.setUpdateFlag(false);
            //mapIconPlateGroup.setUpdateFlag(false);
            menuButtonGroup.setUpdateFlag(true);
            mapIconPlateGroup.setUpdateFlag(true);
            worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.STOP);
            worldModeAdmin.setEquip(Constants.Mode.ACTIVATE.ACTIVE);
        }
    }

    public void dungeonEnterSelectButtonCheck() {
        if (!(dungeonEnterSelectButtonGroup.getUpdateFlag() && selectMode == SELECT_MODE.DUNGEON_SELECT)) {
            return;
        }

        int buttonID = dungeonEnterSelectButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //侵入する
            dungeonEnterSelectButtonGroup.setUpdateFlag(false);
            dungeonEnterSelectButtonGroup.setDrawFlag(false);
            //menuButtonGroup.setUpdateFlag(false);
            //mapIconPlateGroup.setUpdateFlag(false);
            menuButtonGroup.setUpdateFlag(true);
            mapIconPlateGroup.setUpdateFlag(true);

            //TODO 侵入処理におけるダンジョンデータによる分岐処理

            activityChange.toDungeonActivity(Constants.DungeonKind.DUNGEON_KIND.GOKI);
        }
        if (buttonID == 1 ) { //やめる
            dungeonEnterSelectButtonGroup.setUpdateFlag(false);
            dungeonEnterSelectButtonGroup.setDrawFlag(false);
            menuButtonGroup.setUpdateFlag(true);
            mapIconPlateGroup.setUpdateFlag(true);
        }
    }

    public void maohEnterSelectButtonCheck() {
        if (!(maohEnterSelectButtonGroup.getUpdateFlag() && selectMode == SELECT_MODE.DUNGEON_SELECT)) {
            return;
        }

        int buttonID = maohEnterSelectButtonGroup.getTouchContentNum();
        if (buttonID == 0 ) { //侵入する
            maohEnterSelectButtonGroup.setUpdateFlag(false);
            maohEnterSelectButtonGroup.setDrawFlag(false);
            //menuButtonGroup.setUpdateFlag(false);
            //mapIconPlateGroup.setUpdateFlag(false);
            menuButtonGroup.setUpdateFlag(true);
            mapIconPlateGroup.setUpdateFlag(true);

            //TODO 魔王の画面へ行く
            //activityChange.toDungeonActivity(Constants.DungeonKind.DUNGEON_KIND.GOKI);
        }
        if (buttonID == 1 ) { //やめる
            maohEnterSelectButtonGroup.setUpdateFlag(false);
            maohEnterSelectButtonGroup.setDrawFlag(false);
            menuButtonGroup.setUpdateFlag(true);
            mapIconPlateGroup.setUpdateFlag(true);
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
