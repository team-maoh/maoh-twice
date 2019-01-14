package com.maohx2.ina.Activity;

import android.view.SurfaceHolder;
import com.maohx2.ina.Activity.BaseSurfaceView;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GameSystem.StartGameSystem;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.ina.GameSystem.WorldGameSystem;
import com.maohx2.ina.GameSystem.DungeonGameSystem;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.Talking.TalkSaveDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.music.MusicAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;
import com.maohx2.ina.Constants.DungeonKind.*;

/**
 * Created by user on 2019/01/14.
 */

public class UnitedSurfaceView extends BaseSurfaceView {

    UnitedActivity unitedActivity;

    StartGameSystem start_game_system;
    WorldGameSystem worldGameSystem;
    DungeonGameSystem dungeonGameSystem;

    Graphic graphic;
    BattleUserInterface userInterface;
    DungeonUserInterface dungeonUserInterface;

    MyDatabaseAdmin my_database_admin;
    MusicAdmin musicAdmin;
    SoundAdmin soundAdmin;

    enum GAME_SYSTEM_MODE {
        START,
        WORLD,
        DUNGEON,
        NONE
    }

    GAME_SYSTEM_MODE gameSystemMode;

    GAME_SYSTEM_MODE gameSystemModeReserve = GAME_SYSTEM_MODE.NONE;

    @Override
    public void release() {
        System.out.println("takanoRelease : UnitedSurfaceview");
        super.release();
        start_game_system.drawStop();
        start_game_system.updateStop();
        start_game_system.release();
        my_database_admin.release();
        userInterface.release();
        graphic.releaseBitmap();
    }

    public UnitedSurfaceView(UnitedActivity _unitedActivity, BackSurfaceView _backSurfaceView) {
        super(_unitedActivity, _backSurfaceView);
        unitedActivity = _unitedActivity;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    public void startGame() {//アプリ開始時のみ一度だけ実行
        //アプリ開始時の全ての準備を実行する

        //globalDataクラスにおいてクラス管理を行う必要は無くなったが、とりあえず残してある

        //ここで必ずしなければならない事項
        graphic = new Graphic(unitedActivity, holder);
        my_database_admin = new MyDatabaseAdmin(unitedActivity);

        soundAdmin = new SoundAdmin(unitedActivity, my_database_admin);
        musicAdmin = global_data.getMusicAdmin();

        userInterface = new BattleUserInterface(global_data.getGlobalConstants(), graphic);
        userInterface.init();
        dungeonUserInterface = new DungeonUserInterface(global_data.getGlobalConstants(), graphic);
        dungeonUserInterface.init();

        //activityChange = new ActivityChange(this, currentActivity);

        //必ずしもここでなくて良いが、元々StartActで実行されていた事項
        global_data.getEquipmentInventrySaver().init(graphic);
        global_data.getEquipmentInventry().init(userInterface, graphic, 1000, 100, 1400, 508, 10);
        global_data.getEquipmentInventry().load();
        global_data.getEquipmentInventrySaver().setInventry(global_data.getEquipmentInventry());

        global_data.getGeoInventrySaver().init(graphic);

        GeoInventrySaver geoInventrySaver = global_data.getGeoInventrySaver();
        InventryS geoInventry = global_data.getGeoInventry();
        ExpendItemInventrySaver expendItemInventrySaver = global_data.getExpendItemInventrySaver();
        InventryS expendItemInventry = global_data.getExpendItemInventry();

        global_data.getItemDataAdminManager().init(my_database_admin, graphic);

        geoInventry.init(userInterface, graphic, 1000, 100, 1400, 508, 10);
        geoInventry.load();
        geoInventrySaver.setInventry(geoInventry);

        expendItemInventry.init(userInterface, graphic, 200, 100, 600, 508, 10);
        expendItemInventrySaver.init(global_data.getItemDataAdminManager());
        expendItemInventry.load();
        expendItemInventrySaver.setInventry(expendItemInventry);

        //新規にここに移した事項
        my_database_admin.addMyDatabase("StartDB", "LocalStartImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("StartDB"), "Start");

        //Start画面への準備へ
        runStartGameSystem();

        //以下は一番下に置くこと
        thread = new Thread(this);
        thread.start();
    }
    private void runStartGameSystem() {
        //Start画面に遷移する時に一度だけ実行
        this.setGameSystemMode(GAME_SYSTEM_MODE.START);
        start_game_system = new StartGameSystem();
        start_game_system.init(holder, graphic, userInterface, unitedActivity, my_database_admin);

        musicAdmin.loadMusic("openingbgm00",false);
        soundAdmin.loadSoundPack("basic");

        TalkSaveDataAdmin talkSaveDataAdmin = new TalkSaveDataAdmin(my_database_admin);
        talkSaveDataAdmin.load();
        openingFlag = !(talkSaveDataAdmin.getTalkFlagByName("Opening_in_dungeon")||talkSaveDataAdmin.getTalkFlagByName("Opening_in_world")); //openingFlag = true ならOpeningを実行
    }

    private void releaseStartGameSystem() {
        start_game_system.release();
        start_game_system = null;
        userInterface.resetUI();
        graphic.clearLocalBitmapData();
    }

    private void runWorldGameSystem() {
        //World画面に遷移する時に一度だけ実行
        this.setGameSystemMode(GAME_SYSTEM_MODE.WORLD);

        my_database_admin.addMyDatabase("WorldDB", "LocalWorldImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("WorldDB"), "World");

        worldGameSystem = new WorldGameSystem();

        global_data.getEquipmentInventry().init(userInterface, graphic, 950,100,1150,708, 7);
        global_data.getExpendItemInventry().init(userInterface, graphic,450,100,850,708, 7);
        global_data.getGeoInventry().init(userInterface, graphic,1200,100,1600,700, 10);

        global_data.getEquipmentInventry().sortItemData();
        global_data.getEquipmentInventry().sortItemDatabyKind();
        global_data.getGeoInventry().sortItemData();
        global_data.getGeoInventry().sortItemDatabyKind();
        global_data.getExpendItemInventry().sortItemData();

        worldGameSystem.init(
                userInterface,
                graphic,
                my_database_admin,
                soundAdmin,
                unitedActivity
        );

    }

    private void releaseWorldGameSystem() {
        worldGameSystem.release();
        worldGameSystem = null;
        userInterface.resetUI();
        graphic.clearLocalBitmapData();
    }

    private void runDungeonGameSystem() {
        this.setGameSystemMode(GAME_SYSTEM_MODE.DUNGEON);

        dungeonGameSystem = new DungeonGameSystem();

        my_database_admin.addMyDatabase("DungeonDB", "LocalDungeonImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("DungeonDB"), "Dungeon");

        my_database_admin.addMyDatabase("BattleDB", "LocalBattleImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("BattleDB"), "Battle");

        switch (dungeon_kind){
            case CHESS:
                my_database_admin.addMyDatabase("ChessDB", "LocalChessImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("ChessDB"), "Chess");
                break;

            case DRAGON:
                my_database_admin.addMyDatabase("DragonDB", "LocalDragonImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("DragonDB"), "Dragon");
                break;

            case FOREST:
                my_database_admin.addMyDatabase("ForestDB", "LocalForestImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("ForestDB"), "Forest");
                break;

            case GOKI:
                my_database_admin.addMyDatabase("GokiDB", "LocalGokiImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("GokiDB"), "Goki");
                break;

            case HAUNTED:
                my_database_admin.addMyDatabase("HauntedDB", "LocalHauntedImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("HauntedDB"), "Haunted");
                break;
            case SEA:
                my_database_admin.addMyDatabase("SeaDB", "LocalSeaImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("SeaDB"), "Sea");
                break;
            case SWAMP:
                my_database_admin.addMyDatabase("SwampDB", "LocalSwampImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("SwampDB"), "Swamp");
                break;
            case LAVA:
                my_database_admin.addMyDatabase("LavaDB", "LocalLavaImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("LavaDB"), "Lava");
                break;
            case MAOH:
                my_database_admin.addMyDatabase("GokiDB", "LocalGokiImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("GokiDB"), "Goki");
                break;
            case OPENING:
                my_database_admin.addMyDatabase("ForestDB", "LocalForestImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("ForestDB"), "Forest");
                openingFlag = true;
                break;
        }

        global_data.getEquipmentInventry().init(userInterface, graphic, 1000,100,1400,508, 10);
        global_data.getGeoInventry().init(userInterface, graphic,1200,100,1600,700, 10);
        global_data.getExpendItemInventry().init(userInterface, graphic,200,100,600,508, 10);

        dungeonGameSystem.init(
                dungeonUserInterface,
                graphic, soundAdmin,
                my_database_admin,
                userInterface,
                unitedActivity,
                my_database_admin,
                dungeon_kind
        );

        if(openingFlag == true){
            dungeonGameSystem.openningInit();
        }

    }

    private void releaseDungeonGameSystem() {
        dungeonGameSystem.release();
        dungeonGameSystem = null;
        userInterface.resetUI();
        dungeonUserInterface.resetUI();
        graphic.clearLocalBitmapData();
    }

    public void toWorldGameMode() {
        gameSystemModeReserve = GAME_SYSTEM_MODE.WORLD;
    }

    DUNGEON_KIND dungeon_kind;//
    public void toDungeonGameMode(DUNGEON_KIND _dungeon_kind) {
        dungeon_kind = _dungeon_kind;
        gameSystemModeReserve = GAME_SYSTEM_MODE.DUNGEON;
    }

    private void releaseNowGameSystem() {
        switch (gameSystemMode) {
            case START:
                releaseStartGameSystem();
                break;
            case WORLD:
                releaseWorldGameSystem();
                break;
            case DUNGEON:
                releaseDungeonGameSystem();
                break;
            default:
                break;
        }
    }

    public void toChangeGameMode() {
        switch (gameSystemModeReserve) {
            case START:
                //ここには来ない(ゲーム画面からスタート画面に戻れるようにするなら必要)
                gameSystemModeReserve = GAME_SYSTEM_MODE.NONE;
                break;
            case WORLD:
                releaseNowGameSystem();
                runWorldGameSystem();
                gameSystemModeReserve = GAME_SYSTEM_MODE.NONE;
                break;
            case DUNGEON:
                releaseNowGameSystem();
                runDungeonGameSystem();
                gameSystemModeReserve = GAME_SYSTEM_MODE.NONE;
                break;
            default:
                break;
        }
    }

    @Override
    public void gameLoop() {
        if (unitedActivity.isFinishing() || unitedActivity.isPaused()) {
            return;
        }
        //場合分けして実行
        switch (gameSystemMode) {
            case START:
                startGameLoop();
                break;
            case WORLD:
                worldGameLoop();
                break;
            case DUNGEON:
                dungeonGameLoop();
                break;
            default:
                break;
        }
    }

    private void startGameLoop() {
        userInterface.updateTouchState(touch_x, touch_y, touch_state);

        if(touch_state == Constants.Touch.TouchState.DOWN && touchWaitcount > 15){
            if (downCount == 0) {
                soundAdmin.play("opening02");
            }
            setDownCount(downCount+1);
            touchWaitcount = 0;
        }
        touchWaitcount++;

        if (touchWaitcount == 30 && downCount == 1) {
            musicAdmin.play();
        }

        switch (downCount) {
            case 0:
                start_game_system.openingUpdate();
                //start_game_system.openingdraw();
                break;
            case 1:
                start_game_system.updata();
                start_game_system.draw();
                break;
            case 2:
                soundAdmin.play("opening01");
                //初めてゲームを開始下ならDungeonへ。そうでないならワールドへ。
                if (!openingFlag) {
                    //activityChange.toWorldActivity();
                    this.toWorldGameMode();
                } else {
                    //activityChange.toDungeonActivity(Constants.DungeonKind.DUNGEON_KIND.OPENING);
                    this.toDungeonGameMode(Constants.DungeonKind.DUNGEON_KIND.OPENING);
                }
                break;
        }
        //activityChange.toChangeActivity();
        toChangeGameMode();
    }

    int downCount = 0;
    int touchWaitcount = 0;
    public void setDownCount(int _downCount) {
        downCount = _downCount;
    }
    public void setTouchWaitcount(int x) {
        touchWaitcount = x;
    }

    private void worldGameLoop() {
        userInterface.updateTouchState(touch_x, touch_y, touch_state);
        worldGameSystem.update();
        worldGameSystem.draw();
        toChangeGameMode();

    }

    private void dungeonGameLoop(){

        userInterface.updateTouchState(touch_x, touch_y, touch_state);
        dungeonUserInterface.updateTouchState(touch_x, touch_y, touch_state);

        if(openingFlag == true) {
            dungeonGameSystem.openningUpdate();
            dungeonGameSystem.openningDraw();
        } else {
            dungeonGameSystem.update();
            dungeonGameSystem.draw();
        }
        toChangeGameMode();
    }






    private void setGameSystemMode(GAME_SYSTEM_MODE _gameSystemMode) {
        gameSystemMode = _gameSystemMode;
    }

    public SoundAdmin getSoundAdmin() {
        return soundAdmin;
    }
}
