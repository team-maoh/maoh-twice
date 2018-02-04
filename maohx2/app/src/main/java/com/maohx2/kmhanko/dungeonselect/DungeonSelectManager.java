package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;
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

    MyDatabase database;
    List<String> dungeonName;

    PlateGroup<CircleImagePlate> dungeonSelectButtonGroup;

    PlateGroup<BoxTextPlate> dungeonInformationPlate;
    PlateGroup<BoxTextPlate> dungeonEnterSelectButtonGroup;

    PlateGroup<CircleImagePlate> modeSelectButtonGroup;

    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";

    String tableName = "dungeon_select_button";

    int focusDungeonButtonID;

    SELECT_MODE selectMode = SELECT_MODE.DUNGEON_SELECT;

    Paint paint = new Paint(); //TODO GeoMapとDungeonSelectの切り替え表示用。いつか消える

    //いなの実装までの仮置き
    boolean enterSelectFlag = false;

    public DungeonSelectManager(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin _databaseAdmin, GeoSlotAdminManager _geoSlotAdminManager) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;
        geoSlotAdminManager = _geoSlotAdminManager;

        setDatabase(databaseAdmin);
        loadDungeonSelectButton();
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
    private void loadDungeonSelectButton(){
        int size = database.getSize(tableName);

        dungeonName = database.getString(tableName, "name");
        List<String> imageName = database.getString(tableName, "image_name");
        List<Integer> x = database.getInt(tableName, "x");
        List<Integer> y = database.getInt(tableName, "y");

        List<CircleImagePlate> dungeonSelectButtonList = new ArrayList<CircleImagePlate>();


        //インスタンス化
        for (int i = 0; i < size; i++) {
            dungeonSelectButtonList.add(new CircleImagePlate(
                    graphic, userInterface,
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[] { x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_TOURH_R },
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_DEFAULT, DUNGEON_SELECT_BUTTON_RATE_DEFAULT, 0.0f, 255, false),
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_FEEDBACK, DUNGEON_SELECT_BUTTON_RATE_FEEDBACK, 0.0f, 255, false)
            ));
        }

        CircleImagePlate[] dungeonSelectButton = new CircleImagePlate[dungeonSelectButtonList.size()];
        dungeonSelectButtonGroup = new PlateGroup<CircleImagePlate>(dungeonSelectButtonList.toArray(dungeonSelectButton));
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
                        /*
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 250, 1550, 400},
                                "ジオマップを開く(仮)",
                                textPaint
                        ),*/
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
        if (!isDungeonSelectActive) { return; };

        // ** GeoMap / DungeonSelectの表示 **
        if (selectMode == SELECT_MODE.DUNGEON_SELECT) {
            paint.setARGB(255, 255, 255, 255);
        }
        if (selectMode == SELECT_MODE.GEOMAP_SELECT) {
            paint.setARGB(255, 255, 255, 128);
        }
        graphic.bookingDrawRect(0, 0, 1600, 900, paint);


        // ** Buttonの表示
        dungeonSelectButtonGroup.draw();
        if (enterSelectFlag) {
            dungeonEnterSelectButtonGroup.draw();
        }
        modeSelectButtonGroup.draw();
    }

    //***** update関係 *****
    public void update() {
        if (!isDungeonSelectActive) { return; };

        if (selectMode == SELECT_MODE.GEOMAP_SELECT) {
            GeoMapSelectButtonCheck();
        }
        if (selectMode == SELECT_MODE.DUNGEON_SELECT) {
            dungeonSelectButtonCheck();
            if (enterSelectFlag) {
                dungeonEnterSelectButtonCheck();
            }
        }
        modeSelectButtonCheck();

        dungeonSelectButtonGroup.update();
        if (enterSelectFlag) {
            dungeonEnterSelectButtonGroup.update();
        }
        modeSelectButtonGroup.update();
    }

    //注 : 紛らわしいが、DungeonSelectButtonはGeoMapSelectとDungeonSelectとで共通になっている
    public void dungeonSelectButtonCheck() {
        int buttonID = dungeonSelectButtonGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            focusDungeonButtonID = buttonID;
            enterSelectFlag = true;
        }
    }

    public void GeoMapSelectButtonCheck() {
        int buttonID = dungeonSelectButtonGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            geoSlotAdminManager.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
            enterSelectFlag = false;
            setActive(false);
        }
    }

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

            setActive(false);
            enterSelectFlag = false;
        }
        if (buttonID == 1 ) { //やめる
            enterSelectFlag = false;
        }
    }


    //***** Setter *****
    public void setActive(boolean f) {
        isDungeonSelectActive = f;
    }

    //***** Getter *****
    public boolean IsActive() {
        return isDungeonSelectActive;
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
