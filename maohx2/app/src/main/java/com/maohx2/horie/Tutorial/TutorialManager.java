package com.maohx2.horie.Tutorial;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.MiningItemData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2019/03/21.
 */

public class TutorialManager {
    //FlagSave.dbと同一にしないといけない
    TutorialFlagData tutorialFlagData;
    TutorialFlagSaver tutorialFlagSaver;
    Graphic graphic;
    UserInterface userInterface;
    SoundAdmin soundAdmin;

    String name;
    BitmapData backGroundBitmapData;

    int count;

    boolean isTutorialActive = false;

    boolean touchFlag;

    boolean saveIgnore;

    MyDatabase tutorialDB;
    int size;
    List<String> tableName = new ArrayList<String>();
    List<String> imageName = new ArrayList<String>();

    public TutorialManager(Graphic _graphic, MyDatabaseAdmin myDatabaseAdmin, UserInterface _userInterface, SoundAdmin _soundAdmin) {
        graphic = _graphic;

        tutorialFlagData = new TutorialFlagData();
        tutorialFlagSaver = new TutorialFlagSaver(myDatabaseAdmin, "FlagSave", "FlagSave.db", Constants.SaveDataVersion.TUTORIAL, Constants.DEBUG_SAVE_MODE,tutorialFlagData);
        this.load();

        userInterface = _userInterface;
        soundAdmin = _soundAdmin;

        myDatabaseAdmin.addMyDatabase("Tutorial", "Tutorial.db", 1, "r");
        tutorialDB = myDatabaseAdmin.getMyDatabase("Tutorial");
        tableName = tutorialDB.getTables();
    }

    public boolean start(String tutorialName, boolean _saveIgnore) {
        if (!this.searchTableName(tutorialName)) {
            return false;
        }
        if (!tutorialFlagData.searchTutorialName(tutorialName)) {
            return false;
        }
        if (tutorialFlagData.getIsTutorialFinishedByName(tutorialName) && !_saveIgnore) {
            return false;
        }
        name = tutorialName;
        imageName = tutorialDB.getString(tutorialName, "imageName");
        size = imageName.size();
        saveIgnore = _saveIgnore;

        count = 0;
        touchFlag = true;
        isTutorialActive = true;
        this.setBackGround();
        return true;
    }
    public void update() {
        if (!isTutorialActive) {
            return;
        }
        if (userInterface.getTouchState() == Constants.Touch.TouchState.UP && !touchFlag) {
            count++;
            touchFlag = true;
            soundAdmin.play("enter00");
            if (count >= size) {
                this.tutorialEnd();
            } else {
                this.setBackGround();
            }
        }
        if (userInterface.getTouchState() == Constants.Touch.TouchState.AWAY) {
            touchFlag = false;
        }
    }

    public void draw() {
        if (!isTutorialActive) {
            return;
        }
        if (backGroundBitmapData != null) {
            graphic.bookingDrawBitmapData(backGroundBitmapData, 0, 0, 0.983f, 0.983f, 0, 255, true);
        } else {
            System.out.println("TutorialManager: draw: backGroundBitmapData == null");
        }
    }

    public void tutorialEnd() {
        isTutorialActive = false;
        tutorialFlagData.setIsTutorialFinishedByName(1, name);
        if (!saveIgnore) {
            this.save();
        }
        saveIgnore = false;
    }

    public void tutorialResetAll() {
        tutorialFlagData.tutorialResetAll();
    }
    public void save() {
        tutorialFlagSaver.save();
    }
    public void load() {
        tutorialFlagSaver.load();
    }

    public void setBackGround() {
        backGroundBitmapData = graphic.searchBitmap(imageName.get(count));
    }

    public boolean searchTableName(String tutorialName) {
        for (int i = 0; i < tableName.size(); i++) {
            if (tutorialName.equals(tableName.get(i))) {
                return true;
            }
        }
        return false;
    }

    public TutorialFlagSaver getTutorialFlagSaver() {
        return tutorialFlagSaver;
    }
    public TutorialFlagData getTutorialFlagData() {
        return tutorialFlagData;
    }
    public MyDatabase getTutorialDB() {
        return tutorialDB;
    }

    public boolean isTutorialActive() {
        return isTutorialActive;
    }

    public void release() {
        tableName.clear();
        tableName = null;
        imageName.clear();
        imageName = null;
        tutorialFlagData.release();
        tutorialFlagSaver.release();
    }


}
