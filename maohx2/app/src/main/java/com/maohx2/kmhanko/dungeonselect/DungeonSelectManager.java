package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

/**
 * Created by user on 2017/11/24.
 */

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.Text.ListBox;

import android.content.Intent;

public class DungeonSelectManager {
    DungeonSelectButtonAdmin dungeonSelectButtonAdmin;
    LoopSelectButtonAdmin loopSelectButtonAdmin;

    Graphic graphic;
    UserInterface userInterface;
    MyDatabaseAdmin databaseAdmin;

    ListBox enterListBox;
    boolean enterListBoxActive;

    //TODO ここに直書きはよくないかもの部分で使ってるメンバ
    //Buttonの状態で保持するんじゃなくて、ダンジョンデータ的なもので保持したい
    DungeonSelectButton dungeonSelectButton;

    public DungeonSelectManager() {
        dungeonSelectButtonAdmin = new DungeonSelectButtonAdmin();
        loopSelectButtonAdmin = new LoopSelectButtonAdmin();
        enterListBox = new ListBox();
    }

    public void init(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin _databaseAdmin) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;

        dungeonSelectButtonAdmin.init(graphic,userInterface,databaseAdmin);
        dungeonSelectButtonAdmin.staticInit(this);

        enterListBox.init(userInterface,graphic, Constants.Touch.TouchWay.DOWN_MOMENT,2,600,600,1000,800);
        enterListBoxActive = false;

        //TODO :loopselect
    }


    public void draw() {
        dungeonSelectButtonAdmin.draw();
        if (enterListBoxActive) {
            enterListBox.draw();
        }
    }

    public void update() {
        dungeonSelectButtonAdmin.update();
        if (enterListBoxActive) {
            enterListBox.update();
        }
        dungeonSelectListUpdate();
    }

    public void makeEnterListBox(DungeonSelectButton _button) {
        dungeonSelectButton = _button;

        enterListBoxActive = true;
        enterListBox.init(userInterface, graphic, Constants.Touch.TouchWay.UP_MOMENT, 2, 1300, 400, 1600, 600);
        enterListBox.setContent(0, "侵入する");
        enterListBox.setContent(1, "やめる");
    }


    //TODO ここに直書きはよくない気がする
    public void dungeonSelectListUpdate() {
        if (enterListBoxActive) {
            switch (enterListBox.checkTouchContent()) {
                case (0):
                    //TODO 先輩にデータを渡す

                    //TODO Act遷移

                    break;
                case (1):
                    enterListBoxActive = false;
                    break;
            }
        }
    }


}
