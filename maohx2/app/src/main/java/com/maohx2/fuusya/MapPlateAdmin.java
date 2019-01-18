package com.maohx2.fuusya;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Activity.UnitedActivity;
//import com.maohx2.ina.ActivityChange;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GameSystem.DungeonGameSystem;
import com.maohx2.ina.GameSystem.DungeonModeManage;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.GameSystem.StartGameSystem;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatusViewer;
import com.maohx2.kmhanko.Saver.PlayerStatusSaver;
import com.maohx2.kmhanko.WindowPlate.WindowTextPlate;
import com.maohx2.kmhanko.itemdata.GeoObjectDataCreater;

import com.maohx2.ina.Constants.Touch.TouchWay.*;
import com.maohx2.kmhanko.plate.BoxImageTextPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Touch.TouchWay.MOVE;
import static com.maohx2.ina.Constants.Touch.TouchWay.UP_MOMENT;
//import com.maohx2.ina.Constants.Item.*;//こう書かないとswitchの比較時に赤線が出る

//Constants.Touch.TouchWay.MOVE

/**
 * Created by Fuusya on 2018/03/25.
 */

public class MapPlateAdmin {


    Graphic graphic;
    DungeonUserInterface dungeon_user_interface;
    Inventry inventry;
    MapInventryAdmin map_inventry_admin;
    //ActivityChange activityChange;
    GlobalData globalData;
    PlayerStatus playerStatus;
    PlayerStatusSaver playerStatusSaver;
    boolean is_displaying_list;
    MapAdmin map_admin;

    double max_hp;

    int displaying_content;
    // -1 : 何も表示していない
    // 0  : [ステータス][アイテム][リタイア]というmenu
    // 1  : ステータス表示
    // 2  : アイテム（inventory）
    // 3  : 本当にリタイアしますか？[はい][いいえ]

    int LEFT_COORD = 1100;
    int RIGHT_COORD = 1600;
    int UP_COORD = 50;
    int BUTTON_HEIGHT = 100;
    //
    int HP_BG_TOP = 15;
    int HP_BG_RIGHT = 110;
    int HP_BG_LEFT = 1570;
    int HP_BG_BOTTOM = 35;
    //
//    int ITEM_LEFT = 1200;
//    int ITEM_TOP = 50;
//    int ITEM_RIGHT = 1550;
//    int ITEM_BOTTOM = 450;
//    int ITEM_CONTENTS_NUM = 10;
    int ITEM_CONTENTS_NUM = 8;
    int ITEM_LEFT = LEFT_COORD;
    int ITEM_TOP = UP_COORD + 150;
    int ITEM_RIGHT = RIGHT_COORD;
    int ITEM_BOTTOM = ITEM_TOP + 70 * ITEM_CONTENTS_NUM;

    int ITEM_BOTTOM_WITH_SWITCH = ITEM_BOTTOM + 100;
    //
    int HP_RIGHT = 200;
    int HP_LEFT = 1400;
    int HP_HEIGHT = 20;
    int HP_TOP = HP_BG_TOP + ((HP_BG_BOTTOM - HP_BG_TOP) - HP_HEIGHT) / 2;

    Paint blue_paint = new Paint();
    Paint green_paint = new Paint();
    Paint red_paint = new Paint();
    //
    Paint text_paint = new Paint();
    Paint floor_bg = new Paint();
    //
    double hp_ratio;//0.00 ~ 1.00

    PlateGroup<BoxTextPlate> menuGroup;//[ステータス][アイテム][リタイア]
//    PlateGroup<BoxTextPlate> confirmRetireGroup;//リタイアしますか？[はい][いいえ]
//    PlateGroup<BoxPlate> hitpoint;

    EquipmentItemData tmpEquipmentItemData;
    int obtained_item_num;
    boolean will_storage_inventry;
    DungeonModeManage dungeon_mode_manage;
    SoundAdmin sound_admin;
    MapObjectAdmin mapObjectAdmin;

    UnitedActivity unitedActivity;

    int NUM_OF_TUTORIAL_BITMAP = 3;
    int i_of_tutorial_bitmap;
    String tutorial_name = "スライド";

    DungeonSelectWindowAdmin dungeonSelectWindowAdmin;

    public MapPlateAdmin(Graphic _graphic, DungeonUserInterface _dungeon_user_interface, UnitedActivity _unitedActivity, DungeonModeManage _dungeon_mode_manage, SoundAdmin _sound_admin) {
        graphic = _graphic;
        dungeon_user_interface = _dungeon_user_interface;
        //activityChange = _activityChange;
        //globalData = _globalData;
        unitedActivity = _unitedActivity;
        globalData = (GlobalData)unitedActivity.getApplication();

        playerStatus = globalData.getPlayerStatus();
        playerStatusSaver = globalData.getPlayerStatusSaver();
        dungeon_mode_manage = _dungeon_mode_manage;
        sound_admin = _sound_admin;

        inventry = new Inventry();
        inventry.init(dungeon_user_interface, graphic, ITEM_LEFT, ITEM_TOP, ITEM_RIGHT, ITEM_BOTTOM, ITEM_CONTENTS_NUM);


        text_paint.setTextSize(50f);
        text_paint.setARGB(255, 255, 255, 255);

        is_displaying_list = false;

        displaying_content = -1;

        hp_ratio = 1;
        will_storage_inventry = false;

        menuGroup = new PlateGroup<BoxTextPlate>(new BoxTextPlate[]{new BoxTextPlate(graphic, dungeon_user_interface, new Paint(), UP_MOMENT, MOVE, new int[]{LEFT_COORD, UP_COORD, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT}, "ステータス", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, new Paint(), UP_MOMENT, MOVE, new int[]{LEFT_COORD, UP_COORD + BUTTON_HEIGHT, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT * 2}, "アイテム", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, new Paint(), UP_MOMENT, MOVE, new int[]{LEFT_COORD, UP_COORD + BUTTON_HEIGHT * 2, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT * 3}, "リタイア", text_paint)});

//本当にダンジョンをリタイアしますか？[はい][いいえ]
//        confirmRetireGroup = new PlateGroup<BoxTextPlate>(new BoxTextPlate[]{new BoxTextPlate(graphic, dungeon_user_interface, new Paint(), UP_MOMENT, MOVE, new int[]{RETIRE_LEFT, RETIRE_TOP, RETIRE_RIGHT, RETIRE_BOTTOM}, "はい", text_paint)});


//        int hp_cutting_x = (int) ((HP_RIGHT - HP_LEFT) * hp_ratio) + HP_LEFT;

        obtained_item_num = 0;

//        hitpoint = new PlateGroup<BoxPlate>(new BoxTextPlate[]{new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, UP_MOMENT, MOVE, new int[]{HP_BG_LEFT, HP_BG_TOP, HP_BG_RIGHT, hp_top}, " ", text_paint),
//                //↓　HPバー
//                new BoxTextPlate(graphic, dungeon_user_interface, hp_paint, UP_MOMENT, MOVE, new int[]{HP_LEFT, hp_top, hp_cutting_x, hp_top + HP_HEIGHT}, " ", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, hp_hole_paint, UP_MOMENT, MOVE, new int[]{hp_cutting_x, hp_top, HP_RIGHT, hp_top + HP_HEIGHT}, " ", text_paint),
//                //↑　HPバー
//                new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, UP_MOMENT, MOVE, new int[]{HP_BG_LEFT, hp_top + HP_HEIGHT, HP_BG_RIGHT, HP_BG_BOTTOM}, " ", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, UP_MOMENT, MOVE, new int[]{HP_BG_LEFT, hp_top, HP_LEFT, hp_top + HP_HEIGHT}, " ", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, UP_MOMENT, MOVE, new int[]{HP_BG_RIGHT, hp_top, HP_RIGHT, hp_top + HP_HEIGHT}, " ", text_paint)});

        i_of_tutorial_bitmap = 0;

        max_hp = playerStatus.getHP();
        blue_paint.setColor(Color.BLUE);
        red_paint.setColor(Color.RED);
        red_paint.setTextSize(40f);
        green_paint.setColor(Color.GREEN);
        floor_bg.setColor(Color.BLUE);

        dungeonSelectWindowAdmin = new DungeonSelectWindowAdmin(graphic, dungeon_user_interface, sound_admin, this);
        dungeonSelectWindowAdmin.init();

        initTutorialButton();

    }

    public void setMapObjectAdmin(MapObjectAdmin _mapObjectAdmin) {
        mapObjectAdmin = _mapObjectAdmin;
    }

    public void init() {

    }

    PlateGroup<BoxImageTextPlate> tutorialButtonGroup;
    private void initTutorialButton() {
        Paint textPaint1 = new Paint();
        textPaint1.setTextSize(Constants.TUTRIAL_BUTTON.TEXT_SIZE_TU);
        textPaint1.setARGB(255, 255, 255, 255);
        Paint textPaint2 = new Paint();
        textPaint2.setTextSize(Constants.TUTRIAL_BUTTON.TEXT_SIZE_NAME);
        textPaint2.setARGB(255, 255, 255, 255);

        tutorialButtonGroup = new PlateGroup<>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, dungeon_user_interface, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE,
                                new int[]{Constants.TUTRIAL_BUTTON.DUNGEON_LEFT,Constants.TUTRIAL_BUTTON.DUNGEON_UP,Constants.TUTRIAL_BUTTON.DUNGEON_RIGHT,Constants.TUTRIAL_BUTTON.DUNGEON_BOTTOM},
                                new String[] { "チュートリアル", "- ダンジョン -"},
                                new Paint[] { textPaint1, textPaint2},
                                new WindowTextPlate.TextPosition[] { WindowTextPlate.TextPosition.UP, WindowTextPlate.TextPosition.DOWN }
                        ) {
                            @Override
                            public void callBackEvent() {
                                //OKが押された時の処理
                                sound_admin.play("enter00");
                                //チュートリアル表示
                                i_of_tutorial_bitmap = 0;
                                playerStatus.setTutorialInDungeon(0);
                            }
                        }
                });
        tutorialButtonGroup.setUpdateFlag(true);
        tutorialButtonGroup.setDrawFlag(true);
    }

    public void update() {
        if(i_of_tutorial_bitmap > NUM_OF_TUTORIAL_BITMAP+4 || playerStatus.getTutorialInDungeon() != 0) {
        } else {
            return;
        }

        menuGroup.update();
//        hitpoint.update();
        inventry.updata();

        tutorialButtonGroup.update();

        dungeonSelectWindowAdmin.update();

        int pre_displaying_content = displaying_content;

        max_hp = playerStatus.getHP();//最大HPは毎フレームupdateする（でないとHPが枠をオーバーする）

        Constants.Touch.TouchState touch_state = dungeon_user_interface.getTouchState();
        switch (displaying_content) {
            case 0://menu

                mapObjectAdmin.setPlayerAlpha(128);
                int content = menuGroup.getTouchContentNum();

                //各画面に遷移した時に一度だけ実行したいものを記述
                switch (content) {
                    case 0://ステータス
//                        System.out.println("tatami ステータス");
                        //mapObjectAdmin.setPlayerAlpha(128);
                        dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.EQUIP_EXPEND_INIT);
                        displaying_content = 1;
                        is_displaying_list = false;
                        break;
                    case 1://アイテム
//                        System.out.println("tatami アイテム");
                        //mapObjectAdmin.setPlayerAlpha(128);
                        if (dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.ITEM) {
                            dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.ITEM_INIT);
                        }
                        displaying_content = 2;
                        is_displaying_list = false;
                        break;
                    case 2://（ダンジョンを）リタイア
//                        System.out.println("tatami リタイア");
                        this.retireDungeonWindowActivate();
                        displaying_content = 3;
                        is_displaying_list = false;
                        break;
                    default:
                        break;

                }

                break;

                //各画面で毎フレーム実行したい物を記述
            case 1://[ステータス]
                break;

            case 2://[アイテム]
                break;

            case 3://[リタイア]
                break;

            default:
                break;
//                }
        }

        if (displaying_content != pre_displaying_content) {
            sound_admin.play("enter00");
        }

        if (displaying_content == -1) {
            if (mapObjectAdmin.getPlayerAlpha() != 255) {
                mapObjectAdmin.setPlayerAlpha(255);
            }
        }

//        }

    }

    public void draw() {

        switch (displaying_content) {
            case 0:
                menuGroup.draw();
                break;
            case 1:
                break;
            case 2:
                inventry.drawExceptEquip();
                break;
            case 3:
                break;
            default:
                break;
        }

//        if(displaying_content == 0) {
//            menuGroup.draw();
//        }
//        hitpoint.draw();
//        hitpoint.draw();
//        hitpoint.draw();

        drawTutorialImage();

        if(i_of_tutorial_bitmap > NUM_OF_TUTORIAL_BITMAP+4 || playerStatus.getTutorialInDungeon() != 0) {
            drawFloorAndHP();
            dungeonSelectWindowAdmin.draw();

            if (dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.EQUIP_EXPEND) {
                tutorialButtonGroup.draw();
            }

        }


    }

    //**選択肢関係ここから
    public void escapeDungeonWindowActivate() {
        dungeonSelectWindowAdmin.setDungeonPlateMode(DungeonSelectWindowAdmin.DUNGEON_PLATE_MODE.ESCAPE);
        dungeonSelectWindowAdmin.dungeonPlateUpdate();
        dungeonSelectWindowAdmin.activate();
    }

    public void escapeDungeon() {
        mapObjectAdmin.escapeDungeon();
    }

    public void gotoMiningWindowActivate() {
        dungeonSelectWindowAdmin.setDungeonPlateMode(DungeonSelectWindowAdmin.DUNGEON_PLATE_MODE.MINING);
        dungeonSelectWindowAdmin.dungeonPlateUpdate();
        dungeonSelectWindowAdmin.activate();
    }

    public void gotoMining() {
        mapObjectAdmin.gotoMining();
    }

    public void retireDungeonWindowActivate() {
        dungeonSelectWindowAdmin.setDungeonPlateMode(DungeonSelectWindowAdmin.DUNGEON_PLATE_MODE.RETIRE);
        dungeonSelectWindowAdmin.dungeonPlateUpdate();
        dungeonSelectWindowAdmin.activate();
    }

    public void retireDungeon() {
        //map_inventry_admin.storageMapInventry();
        globalData.getExpendItemInventry().save();
        //activityChange.toWorldActivity();
        unitedActivity.getUnitedSurfaceView().toWorldGameMode();
    }
    // ***選択肢関係ここまで

    public void setIsDisplayingList(boolean _is_displaying_list) {
        is_displaying_list = _is_displaying_list;
    }

    public void setDisplayingContent(int _displaying_content) {
        displaying_content = _displaying_content;
    }

    public int getDisplayingContent() {
        return displaying_content;
    }

    public boolean getIsDisplayingList() {
        return is_displaying_list;
    }

    public boolean isTouchingList(double touch_n_x, double touch_n_y) {

        switch (displaying_content) {
            case -1://Listが画面に出ていない
                return false;
            case 0:
                return LEFT_COORD <= touch_n_x && touch_n_x <= RIGHT_COORD && UP_COORD <= touch_n_y && touch_n_y <= UP_COORD + BUTTON_HEIGHT * 3;
            case 2:
                return ITEM_LEFT <= touch_n_x && touch_n_x <= ITEM_RIGHT && ITEM_TOP <= touch_n_y && touch_n_y <= ITEM_BOTTOM_WITH_SWITCH;
            default:
                return false;
        }

    }

    public Inventry getInventry() {
        return inventry;
    }

    private void obtainDebugItem(int m) {

        obtained_item_num++;

        //        StartGameSystem参照
        tmpEquipmentItemData = new EquipmentItemData();
        //
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("デバッグ剣" + String.valueOf(m));
        tmpEquipmentItemData.setImageName("剣");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("剣"));
        tmpEquipmentItemData.setPrice(100);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.SWORD);
        tmpEquipmentItemData.setUseNum(100);
        tmpEquipmentItemData.setRadius(100);
        tmpEquipmentItemData.setDecayRate(0.99f);
        tmpEquipmentItemData.setTouchFrequency(2);
        tmpEquipmentItemData.setAutoFrequencyRate(0.7f);
        tmpEquipmentItemData.setAttack(100);
        tmpEquipmentItemData.setDefence(50);
//
        inventry.addItemData(tmpEquipmentItemData);

    }

    public int getObtainedItemNum() {
        return obtained_item_num;
    }

    public void setWillStorageInventry(boolean _will_storage_inventry) {
        will_storage_inventry = _will_storage_inventry;
    }

    public boolean getWillStorageInventry() {
        return will_storage_inventry;
    }

    public void setMapInventryAdmin(MapInventryAdmin _map_inventry_admin) {
        map_inventry_admin = _map_inventry_admin;
    }

    private void drawTutorialImage() {

        if (playerStatus.getTutorialInDungeon() == 0) {
            BitmapData tutorial_bitmap;
            if(i_of_tutorial_bitmap == 0){
                tutorial_bitmap = graphic.searchBitmap("t_dungeon_start");
            }
            else if(i_of_tutorial_bitmap <= NUM_OF_TUTORIAL_BITMAP) {
                String bitmap_name = tutorial_name + String.valueOf(i_of_tutorial_bitmap);
                tutorial_bitmap = graphic.searchBitmap(bitmap_name);
            }
            else if(i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+1){
                tutorial_bitmap = graphic.searchBitmap("t_battle_start");
            }
            else if(i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+2) {
                tutorial_bitmap = graphic.searchBitmap("t_battle");
            }
            else if(i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+3) {
                tutorial_bitmap = graphic.searchBitmap("t_mine_start");
            }
            else if(i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+4) {
                tutorial_bitmap = graphic.searchBitmap("t_mine");
            }
            else{
                tutorial_bitmap = null;
            }

            if (tutorial_bitmap != null && i_of_tutorial_bitmap <= NUM_OF_TUTORIAL_BITMAP) {
                graphic.bookingDrawBitmapData(tutorial_bitmap, 0, 0, 1, 1, 0, 255, true);
            }
            else if (tutorial_bitmap != null && (i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+1 || i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+2)) {
                graphic.bookingDrawBitmapData(tutorial_bitmap, 0, -1, 0.983f, 0.983f, 0, 255, true);
            }
            else if (tutorial_bitmap != null && (i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+3 || i_of_tutorial_bitmap == NUM_OF_TUTORIAL_BITMAP+4)) {
                graphic.bookingDrawBitmapData(tutorial_bitmap, 0, 0, 0.983f, 0.983f, 0, 255, true);
            }

            Constants.Touch.TouchState touch_state = dungeon_user_interface.getTouchState();

            if (touch_state == Constants.Touch.TouchState.UP) {
                i_of_tutorial_bitmap++;

                if(i_of_tutorial_bitmap > NUM_OF_TUTORIAL_BITMAP+4){
                    playerStatus.setTutorialInDungeon(1);
                    playerStatusSaver.save();
                }
            }
        }
    }

    public MapInventryAdmin getMapInventryAdmin() {
        return map_inventry_admin;
    }

    public void initMenu() {
        displaying_content = -1;
//        frame_count = 0;

    }

    /*public ActivityChange getActivityChange() {
        return activityChange;
    }
    */

    public void setMapAdmin(MapAdmin _map_admin) {
        map_admin = _map_admin;
    }

    private void drawFloorAndHP() {

        double now_hp = playerStatus.getNowHP();
        double hp_ratio = now_hp / max_hp;
//        double hp_ratio = 0.7;
        int right_of_green = HP_RIGHT - (int) (hp_ratio * (HP_RIGHT - HP_LEFT));

        //graphic.bookingDrawRect(HP_BG_LEFT, HP_BG_TOP, HP_BG_RIGHT, HP_BG_BOTTOM, blue_paint);
        graphic.bookingDrawRect(HP_LEFT, HP_TOP, right_of_green, HP_TOP + HP_HEIGHT, red_paint);
        graphic.bookingDrawRect(right_of_green, HP_TOP, HP_RIGHT, HP_TOP + HP_HEIGHT, green_paint);

        if (map_admin != null) {
            String now_floor = String.valueOf(map_admin.getNow_floor_num()) + "F";

            //graphic.bookingDrawRect(5, 5, 100, 50, floor_bg);
            graphic.bookingDrawText(now_floor, 70, 45, text_paint);
        }

    }

    public DungeonSelectWindowAdmin getDungeonSelectWindowAdmin() {
        return dungeonSelectWindowAdmin;
    }

    public void release() {
        System.out.println("takanoRelease : MapPlateAdmin");
        blue_paint = null;
        green_paint = null;
        red_paint = null;
        text_paint = null;
        floor_bg = null;

        dungeonSelectWindowAdmin.release();
        dungeonSelectWindowAdmin = null;

        if (menuGroup != null) {
            menuGroup.release();
            menuGroup = null;
        }

        if (tutorialButtonGroup != null) {
            tutorialButtonGroup.release();
            tutorialButtonGroup = null;
        }


        //TODO 心配
        //inentry.release();
    }

}