package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

/**
 * Created by user on 2017/11/24.
 */

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.Text.ListBox;

public class DungeonSelectManager {
    DungeonSelectButtonAdmin dungeonSelectButtonAdmin;
    LoopSelectButtonAdmin loopSelectButtonAdmin;

    Graphic graphic;
    UserInterface userInterface;
    MyDatabaseAdmin databaseAdmin;

    ListBox enterListBox;
    boolean enterListBoxActive;

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

        //TODO :loop
    }


    public void draw() {
        dungeonSelectButtonAdmin.draw();
        //enterListBox.draw();
    }

    public void update() {
        dungeonSelectButtonAdmin.update();
        if (enterListBoxActive) {
            enterListBox.update();
        }
    }

    public void makeEnterListBox(DungeonSelectButton _button) {
        enterListBoxActive = true;
    }


}
