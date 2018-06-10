package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.fuusya.MapPlateAdmin;
import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.DungeonDataAdmin;
import com.maohx2.horie.map.DungeonMonsterDataAdmin;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.horie.map.MapStatus;
import com.maohx2.horie.map.MapStatusSaver;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Battle.BattleUnitDataAdmin;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatusViewer;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.sound.SoundAdmin;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.itemdata.MiningItemDataAdmin;
import com.maohx2.kmhanko.music.MusicAdmin;
import com.maohx2.kmhanko.plate.BackPlate;
import com.maohx2.fuusya.MapInventryAdmin;


/**
 * Created by ina on 2017/09/05.
 */

public class DungeonGameSystem {

    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;
    DungeonUserInterface dungeon_user_interface;
    BattleUserInterface battle_user_interface;
    Graphic graphic;
    Paint paint;
    BattleUnitAdmin battle_unit_admin;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;
    DungeonDataAdmin dungeon_data_admin;
    Camera camera;
    Point map_size = new Point(0, 0);//カメラのインスタンス化に必要

    MapStatus map_status;
    MapStatusSaver map_status_saver;

    // by kmhanko
    BattleUnitDataAdmin battleUnitDataAdmin;
    MiningItemDataAdmin miningItemDataAdmin;

    Inventry inventry;
    EquipmentItemDataAdmin equipment_item_data_admin;
    PaletteAdmin palette_admin;

    DungeonModeManage dungeonModeManage;
    boolean is_displaying_menu, is_touching_outside_menu;
    MapPlateAdmin map_plate_admin;
    MapInventryAdmin map_inventry_admin;

    InventryS equipmentInventry;
    InventryS expendInventry;
    BitmapData backGround;

    ActivityChange activityChange;
    PlayerStatus playerStatus;
    MaohMenosStatus maohMenosStatus;

    DungeonMonsterDataAdmin dungeonMonsterDataAdmin;

    Activity dungeonActivity;

    MusicAdmin musicAdmin;
    SoundAdmin soundAdmin;

    GeoSlotSaver geoSlotSaver;
    GeoSlotAdminManager geoSlotAdminManager;

    Constants.DungeonKind.DUNGEON_KIND dungeon_kind;

    EffectAdmin effectAdmin;

    PlayerStatusViewer playerStatusViewer;

    int repeat_count;

    public void init(DungeonUserInterface _dungeon_user_interface, Graphic _graphic, SoundAdmin _soundAdmin, MyDatabaseAdmin _myDatabaseAdmin, BattleUserInterface _battle_user_interface, Activity dungeon_activity, MyDatabaseAdmin my_database_admin, ActivityChange _activityChange, int _repeat_count, Constants.DungeonKind.DUNGEON_KIND _dungeon_kind) {
        dungeon_user_interface = _dungeon_user_interface;
        battle_user_interface = _battle_user_interface;
        dungeonActivity = dungeon_activity;
        graphic = _graphic;
        dungeon_kind = _dungeon_kind;

        soundAdmin = _soundAdmin;

        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin(graphic, soundAdmin);
        list_box_admin = new ListBoxAdmin();
        text_box_admin.init(dungeon_user_interface);
        list_box_admin.init(dungeon_user_interface, graphic);

        GlobalData globalData = (GlobalData)(dungeon_activity.getApplication());
        musicAdmin = globalData.getMusicAdmin();


        effectAdmin = new EffectAdmin(graphic, _myDatabaseAdmin, soundAdmin);
        battle_unit_admin.getEffectAdmin(effectAdmin);

        //repeat_count = _repeat_count;

        /*
        if (((DungeonActivity)dungeonActivity).dungeon_surface_view.getOpeningFlag()) {
            dungeon_kind = Constants.DungeonKind.DUNGEON_KIND.OPENING;
        }
        */

        int dungeon_num = 0;
        //TODO 先輩にお願いしてダンジョンのデータ増やしてもらう
        switch(dungeon_kind) {
            case CHESS:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "ChessMonsterData");
                break;
            case DRAGON:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "DragonMonsterData");
                break;
            case FOREST:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "ForestMonsterData");
                break;
            case HAUNTED:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "HauntedMonsterData");
                break;
            case SEA:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "SeaMonsterData");
                break;
            case SWAMP:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "SwampMonsterData");
                break;
            case LAVA:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "LavaMonsterData");
                break;
            case MAOH:
                break;
            case OPENING:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "OpeningMonsterData");
                break;
            default:
                break;
        }
        dungeon_num = dungeon_kind.ordinal();



        battleUnitDataAdmin = new BattleUnitDataAdmin(_myDatabaseAdmin, graphic); // TODO : 一度読み出せばいいので、GlobalData管理が良いかもしれない
        battleUnitDataAdmin.loadBattleUnitData(dungeon_kind);//敵読み込み


        activityChange = _activityChange;
        dungeonModeManage = new DungeonModeManage();
        map_plate_admin = new MapPlateAdmin(graphic, dungeon_user_interface, activityChange, globalData, dungeonModeManage, soundAdmin);
        map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, soundAdmin, map_plate_admin, dungeonModeManage, globalData, battle_unit_admin, text_box_admin);
        map_inventry_admin = new MapInventryAdmin(globalData, map_plate_admin.getInventry(), map_object_admin, map_plate_admin);

        dungeon_data_admin = new DungeonDataAdmin(_myDatabaseAdmin);

        map_status = new MapStatus(Constants.STAGE_NUM);//mapのクリア状況,チュートリアルを見たかどうかを記憶しておく
        map_status_saver = new MapStatusSaver(_myDatabaseAdmin, "MapSaveData", "MapSaveData.db", 1, "s", map_status, Constants.STAGE_NUM);
        map_status_saver.load();
//        for(int i = 0;i < 7;i++){
//            System.out.println("before:stage_num = "+i+", is_clear = "+map_status.getTutorialFinishStatus(i));
//        }
//        map_status.setTutorialFinishStatus(1, 0);
//        saveMapSaveData();
//        map_status_saver.load();
//
//        for(int i = 0;i < 7;i++){
//            System.out.println("after:stage_num = "+i+", is_clear = "+map_status.getTutorialFinishStatus(i));
//        }
//      camera = new Camera(map_size, 64*4);


        if (!(dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH)) {
            map_size.set(dungeon_data_admin.getDungeon_data().get(dungeon_num).getMap_size_x(), dungeon_data_admin.getDungeon_data().get(dungeon_num).getMap_size_y());
            map_admin = new MapAdmin(graphic, map_object_admin, dungeon_data_admin.getDungeon_data().get(dungeon_num), dungeonMonsterDataAdmin.getDungeon_monster_data(), map_status, map_status_saver);
            map_admin.goNextFloor();
        }
//        map_object_admin.getCamera(map_admin.getCamera());

        //map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, sound_admin, map_admin,this, dungeonModeManage);
        paint = new Paint();
        paint.setColor(Color.BLUE);

        //map_plate_admin = new MapPlateAdmin(graphic, dungeon_user_interface, this);

        PaletteCenter.initStatic(graphic);miningItemDataAdmin = new MiningItemDataAdmin(graphic, my_database_admin);
        PaletteElement.initStatic(graphic);

        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, my_database_admin);

        equipmentInventry = globalData.getEquipmentInventry();
        expendInventry = globalData.getExpendItemInventry();


        miningItemDataAdmin = new MiningItemDataAdmin(graphic, my_database_admin);

        palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipmentInventry, expendInventry, equipment_item_data_admin, soundAdmin);
        //palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipment_item_data_admin);
        palette_admin.setMiningItems(miningItemDataAdmin);//TODO コンストラクタに入れて居ないためよくない


        playerStatus = globalData.getPlayerStatus();
        maohMenosStatus = globalData.getMaohMenosStatus();

        playerStatusViewer = new PlayerStatusViewer(graphic, dungeon_user_interface, playerStatus);

        if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH) {
            repeat_count = playerStatus.getMaohWinCount();
        } else {
            repeat_count = playerStatus.getNowClearCount();
        }

        battle_unit_admin.init(
                graphic,
                battle_user_interface,
                dungeon_activity,
                battleUnitDataAdmin,
                playerStatus,
                palette_admin,
                dungeonModeManage,
                my_database_admin,
                map_plate_admin,
                text_box_admin,
                repeat_count,
                maohMenosStatus,
                soundAdmin,
                map_status,
                map_status_saver,
                dungeon_kind,
                dungeonMonsterDataAdmin
        );

        backGround = graphic.searchBitmap("firstBackground");


        geoSlotSaver = new GeoSlotSaver(my_database_admin, "GeoSlotSave", "GeoSlotSave.db", 1, "ns", graphic);
        geoSlotAdminManager = new GeoSlotAdminManager(graphic, dungeon_user_interface, my_database_admin, text_box_admin, playerStatus, globalData.getGeoInventry(), geoSlotSaver, maohMenosStatus, soundAdmin, effectAdmin, dungeonModeManage);
        geoSlotAdminManager.loadGeoSlot();

        dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP_INIT);

        //by kmhanko即座に魔王との戦闘画面へ
        if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH) {
            dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAOH_INIT);
        }

        //デバッグ用
        //dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING_INIT);


        //
        //dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MAP_INIT);
    }

    public void saveMapSaveData() {
        map_status_saver.save();
    }


    public void update() {

        switch (dungeonModeManage.getMode()) {
            case MAP_INIT:
                playMapBGM();
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
            case MAP:
                //map_object_admin.update(is_displaying_menu, is_touching_outside_menu);
                //map_plate_admin.update(is_displaying_menu);
                map_object_admin.update();
                map_plate_admin.update();
                break;
            case OPENING_BATTLE_INIT:
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE);
                backGround = graphic.searchBitmap("firstBackground");
                musicAdmin.loadMusic("boss00",true);
                battle_user_interface.update();
                battle_unit_admin.update();
                break;

            case BUTTLE_INIT:
                //battle_unit_admin.reset(BattleUnitAdmin.MODE.BATTLE);
                //battle_unit_admin.spawnEnemy();
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE);
                backGround = graphic.searchBitmap("firstBackground");
                musicAdmin.loadMusic("battle00",true);

            case BUTTLE:
                battle_user_interface.update();
                battle_unit_admin.update();
                break;

            case MAOH_INIT:
                battle_unit_admin.reset(BattleUnitAdmin.MODE.MAOH);
                battle_unit_admin.spawnEnemy(
                        new String[] {
                                battleUnitDataAdmin.getMaohUnitNames().get(playerStatus.getMaohWinCount())
                        }
                );
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAOH);
                musicAdmin.loadMusic("boss00",true);

            case MAOH:
                battle_user_interface.update();
                battle_unit_admin.update();
                break;

            case GEO_MINING_INIT:
                battle_unit_admin.reset(BattleUnitAdmin.MODE.MINING);
                //battle_unit_admin.spawnRock();　reset内で呼んでいる
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING);
                backGround = graphic.searchBitmap("miningBack");
                musicAdmin.loadMusic("battle00",true);

            case GEO_MINING:
                battle_user_interface.update();
                battle_unit_admin.update();
                break;

            case TO_WORLD:
                activityChange.toWorldActivity();
                break;

            case EQUIP_EXPEND_INIT:
                initBackPlate();
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.EQUIP_EXPEND);

            case EQUIP_EXPEND:
                //equipmentInventry.updata();
                //expendInventry.updata();
                //palette_admin.update(false);
                backPlateGroup.update();
                break;
            case GEO_MAP_INIT:
                initBackPlate();
                geoSlotAdminManager.start();
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MAP);
            case GEO_MAP:
                geoSlotAdminManager.updateInStatus();
                break;

        }

        text_box_admin.update();
        effectAdmin.update();
        //musicAdmin.update();
    }

    public void draw() {
        for(int i = 0;i < 7;i++) {
            System.out.println("ホリエ：is_tf("+i+") = " + map_status.getTutorialFinishStatus(i));
        }
        switch (dungeonModeManage.getMode()) {
            case MAP:
                map_admin.drawMap_for_autotile_light_animation();
                map_object_admin.draw();
                map_plate_admin.draw();
                //graphic.bookingDrawCircle(0,0,10,paint);
                break;

            case BUTTLE:
            case MAOH:
                graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
                battle_unit_admin.draw();
                break;

            case GEO_MINING:
                graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
                battle_unit_admin.draw();
                break;

            case EQUIP_EXPEND_INIT:
                break;

            case EQUIP_EXPEND:
                //equipmentInventry.drawOnly();
                //expendInventry.drawOnly();
                palette_admin.drawOnly();
                //dungeon_user_interface.draw();
                backPlateGroup.draw();
                //backPlateGroup.draw();
                playerStatusViewer.draw();
                break;

            case GEO_MAP:
                geoSlotAdminManager.drawInStatus();
                playerStatusViewer.draw();
                break;

        }

        text_box_admin.draw();
        effectAdmin.draw();
        graphic.draw();

        if (resetBossImage) {
            resetBossImage = false;
            map_object_admin.setBossBitmap("ボス");
        }

    }

    public void setIsDisplayingMenu(boolean _is_displaying_menu){
        is_displaying_menu = _is_displaying_menu;
    }

    public void setIsTouchingOutsideMenu(boolean _is_touching_outside_menu){
        is_touching_outside_menu = _is_touching_outside_menu;
    }



    private void playMapBGM() {
        switch(dungeon_kind) {
            case CHESS:
                musicAdmin.loadMusic("chess00",true);
                break;
            case DRAGON:
                musicAdmin.loadMusic("dragon00",true);
                break;
            case FOREST:
                musicAdmin.loadMusic("forest00",true);
                break;
            case HAUNTED:
                musicAdmin.loadMusic("haunted00",true);
                break;
            case SEA:
                musicAdmin.loadMusic("sea00",true);
                break;
            case SWAMP:
                musicAdmin.loadMusic("swamp00",true);
                break;
            case LAVA:
                musicAdmin.loadMusic("lava00",true);
                break;
            case MAOH:
                break;
            case OPENING:
                musicAdmin.loadMusic("forest00",true);
                break;
            default:
                break;
        }
    }


    //オープニング関係
    int count = 0;
    int openningTextBoxID;
    boolean text_mode = false;
    boolean boss_is_running = false;
    boolean flag1 = false;
    boolean charaFlag;
    boolean resetBossImage = false;

    ImageContext talkChara;

    public void openningInit() {

        openningTextBoxID = text_box_admin.createTextBox(50, 700, 1550, 880, 4);
        playMapBGM();
        text_box_admin.setTextBoxUpdateTextByTouching(openningTextBoxID, false);
        text_box_admin.setTextBoxExists(openningTextBoxID, false);
        map_admin.createOpeningMap();
    }

    public void drawCharaAndTouchCheck(ImageContext _imageContext){
        if (_imageContext != null) {
            charaFlag = true;
        }
        if (battle_user_interface.getTouchState() == Constants.Touch.TouchState.UP) {
            text_box_admin.setTextBoxExists(openningTextBoxID, false);
            soundAdmin.play("textenter00");
            text_mode = false;
        }
    }



    public void openningUpdate() {

        paint.setTextSize(35);
        paint.setARGB(255, 255, 255, 255);

        if(count == 1){
            map_object_admin.putPlayer();
        }

        if (count == 40) {
            talkChara = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
            text_box_admin.bookingDrawText(openningTextBoxID, "今日も平和だなぁ", paint);
            text_box_admin.bookingDrawText(openningTextBoxID, "MOP");
            text_box_admin.updateText(openningTextBoxID);
            text_box_admin.setTextBoxExists(openningTextBoxID, true);
            text_mode = true;
        }

        if (count == 80) {
            talkChara = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
            text_box_admin.bookingDrawText(openningTextBoxID, "暇だなぁ，何か面白いことないかなぁ．", paint);
            text_box_admin.bookingDrawText(openningTextBoxID, "MOP");
            text_box_admin.updateText(openningTextBoxID);
            text_box_admin.setTextBoxExists(openningTextBoxID, true);
            text_mode = true;
        }

        if (count == 120) {
            talkChara = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
            text_box_admin.bookingDrawText(openningTextBoxID, "ん？何か向こうから向かってくるぞ？", paint);
            text_box_admin.bookingDrawText(openningTextBoxID, "MOP");
            text_box_admin.updateText(openningTextBoxID);
            text_box_admin.setTextBoxExists(openningTextBoxID, true);
            text_mode = true;
        }

        if (count == 120) {
            map_object_admin.putBoss();
            boss_is_running = true;
        }

        //ここから先フジワラ，敵と衝突し，戦闘を行い，倒されるということを実現する．
        //主に，プレイヤーが右に進み続ける，と，敵をちゃんとプレイヤーにぶつけて戦闘に入る，というところをちゃんと実装する．
        //ぶつかる直前に「うわ，なんだ!?」を入れる．

        //count = 180 でboss_is_running = true としたとすると、
        //うわ、なんだ、で画面を止めるのは count = 191
        if (!flag1 && map_object_admin.bossIsHitPlayer(400)){
            flag1 = true;
            talkChara = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
            text_box_admin.bookingDrawText(openningTextBoxID, "うわ，なんだ!?", paint);
            text_box_admin.bookingDrawText(openningTextBoxID, "MOP");
            text_box_admin.updateText(openningTextBoxID);
            text_box_admin.setTextBoxExists(openningTextBoxID, true);
            text_mode = true;

            //System.out.println("count_desudesudesu"+count);
        }

        if (map_object_admin.bossIsHitPlayer(160)) {
            boss_is_running = false;
            //ボスとの戦闘
            System.out.println("茶番：ボスとの戦闘");

            //by kmhanko
            text_box_admin.setTextBoxExists(openningTextBoxID, false);

            battle_unit_admin.reset(BattleUnitAdmin.MODE.OPENING);
            battle_unit_admin.spawnEnemy(
                    new String[] {
                            "m014"
                    }
            );
            ((DungeonActivity)dungeonActivity).dungeon_surface_view.setOpeningFlag(false);////////
            dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.OPENING_BATTLE_INIT);
            text_box_admin.setTextBoxExists(openningTextBoxID, false);
            resetBossImage = true;
        }

        charaFlag = false;
        if (text_mode) {
            drawCharaAndTouchCheck(talkChara);
        }//elseにしてはいけない

        if (!text_mode) {
            map_object_admin.openingUpdate(boss_is_running);
            count++;
        }

        text_box_admin.update();

    }

    public void openningDraw() {

        map_admin.drawOpeningMap();
        map_object_admin.draw();
        if (charaFlag) {
            graphic.bookingDrawBitmapData(talkChara);
        }

        //map_plate_admin.draw();

        text_box_admin.draw();
        graphic.draw();

    }

    PlateGroup<BackPlate> backPlateGroup;

    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[]{
                        new BackPlate(
                                graphic, dungeon_user_interface
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
                                map_plate_admin.initMenu();
                                /*worldModeAdmin.setEquip(Constants.Mode.ACTIVATE.STOP);
                                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.ACTIVE);
                                */

                            }
                        }
                }
        );
    }

    public DungeonGameSystem() {
    }


}

