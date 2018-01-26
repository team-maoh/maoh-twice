package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;

import android.graphics.Paint;

/**
 * Created by user on 2017/11/24.
 */

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.Text.ListBox;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class DungeonSelectManager {
    //DungeonSelectButtonAdmin dungeonSelectButtonAdmin;
    //LoopSelectButtonAdmin loopSelectButtonAdmin;

    Graphic graphic;
    UserInterface userInterface;
    MyDatabaseAdmin databaseAdmin;

    MyDatabase database;

    PlateGroup<CircleImagePlate> dungeonSelectButtonGroup;

    PlateGroup<BoxTextPlate> dungeonInformationPlate;
    PlateGroup<BoxTextPlate> dungeonEnterSelectButtonGroup;

    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";

    String tableName = "dungeon_select_button";

    //いなの実装までの仮置き
    boolean enterSelectFlag = false;

    public DungeonSelectManager(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin _databaseAdmin) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;

        setDatabase(databaseAdmin);
        loadDungeonSelectButton();
        loadDungeonEnterSelectButton();

        //dungeonSelectButtonAdmin = new DungeonSelectButtonAdmin();
        //loopSelectButtonAdmin = new LoopSelectButtonAdmin();
        //enterListBox = new ListBox();

        //TODO : Loopselect
    }

    private void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    private void loadDungeonSelectButton(){
        int size = database.getSize(tableName);

        List<String> name = database.getString(tableName, "name");
        List<String> imageName = database.getString(tableName, "image_name");
        List<Integer> x = database.getInt(tableName, "x");
        List<Integer> y = database.getInt(tableName, "y");

        List<CircleImagePlate> dungeonSelectButtonList = new ArrayList<CircleImagePlate>();

        //インスタンス化
        for (int i = 0; i < size; i++) {
            dungeonSelectButtonList.add(new CircleImagePlate(
                    graphic, userInterface,
                    new Paint(),
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[] { x.get(i), y.get(i), 100 },
                    graphic.searchBitmap(imageName.get(i))
                    ));
        }
        CircleImagePlate[] dungeonSelectButton = new CircleImagePlate[dungeonSelectButtonList.size()];
        dungeonSelectButtonGroup = new PlateGroup<CircleImagePlate>(dungeonSelectButtonList.toArray(dungeonSelectButton));
    }

    private void loadDungeonEnterSelectButton(){
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
    }

    //ListBox enterListBox;
    //boolean enterListBoxActive;

    //TODO ここに直書きはよくないかもの部分で使ってるメンバ
    //Buttonの状態で保持するんじゃなくて、ダンジョンデータ的なもので保持したい
    //DungeonSelectButton dungeonSelectButton;


    public void draw() {
        dungeonSelectButtonGroup.draw();
        if (enterSelectFlag) {
            dungeonEnterSelectButtonGroup.draw();
        }

        //dungeonSelectButtonAdmin.draw();
        //if (enterListBoxActive) {
        //    enterListBox.draw();
        //}
    }

    public void update() {
        dungeonSelectButtonCheck();
        dungeonEnterSelectButtonCheck();

        dungeonSelectButtonGroup.update();
        dungeonEnterSelectButtonGroup.update();
        //dungeonSelectButtonAdmin.update();
        //if (enterListBoxActive) {
        //    enterListBox.update();
        //}
        //dungeonSelectListUpdate();
    }

    //public void makeEnterListBox(DungeonSelectButton _button) {
        //dungeonSelectButton = _button;

        //enterListBoxActive = true;
        //enterListBox.setContent(0, "侵入する");
        //enterListBox.setContent(1, "やめる");
    //}

    public void dungeonSelectButtonCheck() {
        int buttonID = dungeonSelectButtonGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            enterSelectFlag = true;
        }
    }

    public void dungeonEnterSelectButtonCheck() {
        int buttonID = dungeonEnterSelectButtonGroup.getTouchContentNum();
        if (buttonID == 1 ) { //侵入する
            //侵入処理

            enterSelectFlag = false;
        }
        if (buttonID == 2 ) { //やめる
            enterSelectFlag = false;
        }
    }

}
