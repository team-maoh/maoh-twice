package com.maohx2.fuusya;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.WindowPlate.WindowTextPlate;
import com.maohx2.kmhanko.plate.BoxImageTextPlate;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;

/**
 * Created by user on 2019/01/14.
 */

public class DungeonSelectWindowAdmin {

    // *** 選択肢関係
    PlateGroup<BoxImageTextPlate> dungeonSelectButtonGroup;
    WindowTextPlate dungeonPlate;
    Paint dungeonTextPaint;
    public enum DUNGEON_PLATE_MODE {
        ADVANCE,
        ESCAPE,
        MINING,
        RETIRE,
        NONE
    }
    DUNGEON_PLATE_MODE dungeonPlateMode;

    Graphic graphic;
    UserInterface dungeonUserInterface;
    SoundAdmin soundAdmin;
    MapPlateAdmin mapPlateAdmin;

    public DungeonSelectWindowAdmin(Graphic _graphic, UserInterface _dungeonUserInterface, SoundAdmin _soundAdmin, MapPlateAdmin _mapPlateAdmin) {
        graphic = _graphic;
        dungeonUserInterface = _dungeonUserInterface;
        soundAdmin = _soundAdmin;
        mapPlateAdmin = _mapPlateAdmin;

    }

    public void init() {
        initWindowTextPlate();
        initDungeonEscapeSelectButton();
        dungeonPlateMode = DUNGEON_PLATE_MODE.NONE;
        dungeonPlateUpdate();
    }

    private void initWindowTextPlate() {
        dungeonPlate = new WindowTextPlate(graphic, new int[]{Constants.SELECT_WINDOW_PLATE.MESS_LEFT, Constants.SELECT_WINDOW_PLATE.MESS_UP, Constants.SELECT_WINDOW_PLATE.MESS_RIGHT, Constants.SELECT_WINDOW_PLATE.MESS_BOTTOM});
        dungeonTextPaint = new Paint();
        dungeonTextPaint.setTextSize(45);
        dungeonTextPaint.setStrokeWidth(20);
        dungeonTextPaint.setColor(Color.WHITE);
    }

    public void dungeonPlateUpdate() {
        switch (dungeonPlateMode) {
            case ADVANCE:
                dungeonPlate.setText("次の階層に進みますか？", dungeonTextPaint, WindowTextPlate.TextPosition.CENTER);
                break;
            case ESCAPE:
                dungeonPlate.setText("アイテムを持ってダンジョンを脱出しますか？", dungeonTextPaint, WindowTextPlate.TextPosition.CENTER);
                break;
            case MINING:
                dungeonPlate.setText("ジオオブジェクトの採掘を行いますか？", dungeonTextPaint, WindowTextPlate.TextPosition.CENTER);
                break;
            case RETIRE:
                dungeonPlate.setText("アイテムを諦めてダンジョンを脱出しますか？", dungeonTextPaint, WindowTextPlate.TextPosition.CENTER);
                break;
            default:
                dungeonPlate.setText("", dungeonTextPaint, WindowTextPlate.TextPosition.CENTER);
                break;
        }
    }


    private void initDungeonEscapeSelectButton(){
        Paint textPaint = new Paint();
        textPaint.setTextSize(Constants.SELECT_WINDOW_PLATE.BUTTON_TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        dungeonSelectButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, dungeonUserInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{Constants.SELECT_WINDOW.YES_LEFT, Constants.SELECT_WINDOW.YES_UP, Constants.SELECT_WINDOW.YES_RIGHT, Constants.SELECT_WINDOW.YES_BOTTOM},
                                "はい",
                                textPaint
                        ),
                        new BoxImageTextPlate(
                                graphic, dungeonUserInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{Constants.SELECT_WINDOW.NO_LEFT, Constants.SELECT_WINDOW.NO_UP, Constants.SELECT_WINDOW.NO_RIGHT, Constants.SELECT_WINDOW.NO_BOTTOM},
                                "いいえ",
                                textPaint
                        )
                }
        );

        dungeonSelectButtonGroup.setUpdateFlag(false);
        dungeonSelectButtonGroup.setDrawFlag(false);
    }

    public void update() {
        if (!(dungeonSelectButtonGroup.getUpdateFlag())) {
            return;
        }
        dungeonSelectButtonGroup.update();

        int buttonID = dungeonSelectButtonGroup.getTouchContentNum();

        switch (dungeonPlateMode) {
            case ADVANCE:
                if (buttonID == 0 ) { //進む
                    soundAdmin.play("enter00");
                    mapPlateAdmin.advanceDungeon();
                    initUIs();
                }
                if (buttonID == 1 ) { //やめる
                    soundAdmin.play("cancel00");
                    initUIs();
                }
                break;
            case ESCAPE:
                if (buttonID == 0 ) { //脱出
                    soundAdmin.play("enter00");
                    mapPlateAdmin.escapeDungeon();
                    initUIs();
                }
                if (buttonID == 1 ) { //やめる
                    soundAdmin.play("cancel00");
                    initUIs();
                }
                break;
            case MINING:
                if (buttonID == 0 ) { //採掘開始
                    soundAdmin.play("enter00");
                    mapPlateAdmin.gotoMining();
                    initUIs();

                }
                if (buttonID == 1 ) { //やめる
                    soundAdmin.play("cancel00");
                    initUIs();
                }
                break;
            case RETIRE:
                if (buttonID == 0 ) { //リタイア
                    soundAdmin.play("enter00");
                    mapPlateAdmin.retireDungeon();
                    initUIs();

                }
                if (buttonID == 1 ) { //やめる
                    soundAdmin.play("cancel00");
                    mapPlateAdmin.setDisplayingContent(-1);
                    initUIs();
                }
                break;
        }
    }

    public void draw() {
        dungeonPlate.draw();
        dungeonSelectButtonGroup.draw();
    }

    public void setDungeonPlateMode(DUNGEON_PLATE_MODE x) {
        dungeonPlateMode = x;
    }

    public void activate() {
        dungeonSelectButtonGroup.setUpdateFlag(true);
        dungeonSelectButtonGroup.setDrawFlag(true);
        dungeonPlate.setDrawFlag(true);
    }

    public boolean isActive() {
        return dungeonSelectButtonGroup.getUpdateFlag() || dungeonSelectButtonGroup.getDrawFlag();
    }


    private void initUIs() {
        dungeonSelectButtonGroup.setUpdateFlag(false);
        dungeonSelectButtonGroup.setDrawFlag(false);
        dungeonPlate.setDrawFlag(false);
    }

    public void release() {

    }

    //*** 選択肢関係ここまで

}
