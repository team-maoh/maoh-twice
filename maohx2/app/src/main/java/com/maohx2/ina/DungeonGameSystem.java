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
import com.maohx2.horie.map.DungeonData;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleDungeonUnitData;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Battle.BattleUnitDataAdmin;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.EquipmentItemBaseData;
import com.maohx2.ina.ItemData.EquipmentItemBaseDataAdmin;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.EquipmentItemDataCreater;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatusViewer;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.Talking.TalkAdmin;
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

import java.util.List;


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
    //ListBoxAdmin list_box_admin;
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

    //GeoSlotSaver geoSlotSaver;
    //GeoSlotAdminManager geoSlotAdminManager;

    Constants.DungeonKind.DUNGEON_KIND dungeon_kind;

    EffectAdmin effectAdmin;
    EffectAdmin backEffectAdmin;

    PlayerStatusViewer playerStatusViewer;

    TalkAdmin talkAdmin;

    int repeat_count;

    ChangeMovie changeMovie;
    boolean map_init = false;

    public void init(DungeonUserInterface _dungeon_user_interface, Graphic _graphic, SoundAdmin _soundAdmin, MyDatabaseAdmin _myDatabaseAdmin, BattleUserInterface _battle_user_interface, Activity dungeon_activity, MyDatabaseAdmin my_database_admin, ActivityChange _activityChange, Constants.DungeonKind.DUNGEON_KIND _dungeon_kind) {
        dungeon_user_interface = _dungeon_user_interface;
        battle_user_interface = _battle_user_interface;
        dungeonActivity = dungeon_activity;
        graphic = _graphic;
        dungeon_kind = _dungeon_kind;

        soundAdmin = _soundAdmin;

        dungeon_data_admin = new DungeonDataAdmin(_myDatabaseAdmin);
        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin(graphic, soundAdmin);
        //list_box_admin = new ListBoxAdmin();
        text_box_admin.init(dungeon_user_interface);
        //list_box_admin.init(dungeon_user_interface, graphic);

        talkAdmin = new TalkAdmin(graphic, dungeon_user_interface, _myDatabaseAdmin, text_box_admin , soundAdmin);

        GlobalData globalData = (GlobalData)(dungeon_activity.getApplication());
        musicAdmin = globalData.getMusicAdmin();

        playerStatus = globalData.getPlayerStatus();
        maohMenosStatus = globalData.getMaohMenosStatus();

        effectAdmin = new EffectAdmin(graphic, _myDatabaseAdmin, soundAdmin);
        battle_unit_admin.getEffectAdmin(effectAdmin);
        backEffectAdmin = new EffectAdmin(graphic, _myDatabaseAdmin, soundAdmin);
        battle_unit_admin.getEnemyBackEffectAdmin(backEffectAdmin);

        changeMovie = new ChangeMovie(graphic, soundAdmin);

        //repeat_count = _repeat_count;

        /*
        if (((DungeonActivity)dungeonActivity).dungeon_surface_view.getOpeningFlag()) {
            dungeon_kind = Constants.DungeonKind.DUNGEON_KIND.OPENING;
        }
        */

        int dungeon_num = dungeon_kind.ordinal();
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
                dungeon_num = Constants.DungeonKind.DUNGEON_KIND.FOREST.ordinal();
                break;
            default:
                break;
        }

        if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH) {
            repeat_count = playerStatus.getMaohWinCount();
        } else {
            repeat_count = playerStatus.getNowClearCount();
        }

        DungeonData dungeonData = null;
        if (!(dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH)) {
            dungeonData = dungeon_data_admin.getDungeon_data().get(dungeon_num);
        }




        battleUnitDataAdmin = new BattleUnitDataAdmin(_myDatabaseAdmin, graphic); // TODO : 一度読み出せばいいので、GlobalData管理が良いかもしれない
        battleUnitDataAdmin.loadBattleUnitData(dungeon_kind);//敵読み込み


        activityChange = _activityChange;
        dungeonModeManage = new DungeonModeManage();
        map_plate_admin = new MapPlateAdmin(graphic, dungeon_user_interface, activityChange, globalData, dungeonModeManage, soundAdmin);
        map_object_admin = new MapObjectAdmin(
                graphic,
                dungeon_user_interface,
                soundAdmin,
                map_plate_admin,
                dungeonModeManage,
                globalData,
                battle_unit_admin,
                text_box_admin,
                battleUnitDataAdmin,
                dungeonMonsterDataAdmin,
                repeat_count,
                dungeon_kind,
                dungeonData,
                effectAdmin,
                backEffectAdmin
        );
        map_plate_admin.setMapObjectAdmin(map_object_admin);
        map_inventry_admin = new MapInventryAdmin(globalData, map_plate_admin.getInventry(), map_object_admin, map_plate_admin);


        map_status = new MapStatus(Constants.STAGE_NUM);//mapのクリア状況,チュートリアルを見たかどうかを記憶しておく
        map_status_saver = new MapStatusSaver(_myDatabaseAdmin, "MapSaveData", "MapSaveData.db", Constants.SaveDataVersion.MAP_SAVE_DATA, Constants.DEBUG_SAVE_MODE, map_status, Constants.STAGE_NUM);
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
            dungeonData = dungeon_data_admin.getDungeon_data().get(dungeon_num);
            map_size.set(dungeonData.getMap_size_x(), dungeonData.getMap_size_y());
            map_admin = new MapAdmin(graphic, map_object_admin, dungeonData, dungeonMonsterDataAdmin.getDungeon_monster_data(), map_status, map_status_saver);
            map_admin.goNextFloor();
        }

//        map_object_admin.getCamera(map_admin.getCamera());

        //map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, sound_admin, map_admin,this, dungeonModeManage);
        paint = new Paint();
        paint.setColor(Color.BLUE);

        //map_plate_admin = new MapPlateAdmin(graphic, dungeon_user_interface, this);

        PaletteCenter.initStatic(graphic);miningItemDataAdmin = new MiningItemDataAdmin(graphic, my_database_admin);
        PaletteElement.initStatic(graphic);

        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic ,my_database_admin);//globalData.getEquipmentItemDataAdmin();

        equipmentInventry = globalData.getEquipmentInventry();
        expendInventry = globalData.getExpendItemInventry();


        miningItemDataAdmin = new MiningItemDataAdmin(graphic, my_database_admin);

        palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipmentInventry, expendInventry, equipment_item_data_admin, soundAdmin);
        //palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipment_item_data_admin);
        palette_admin.setMiningItems(miningItemDataAdmin);//TODO コンストラクタに入れて居ないためよくない


        playerStatusViewer = new PlayerStatusViewer(graphic, dungeon_user_interface, playerStatus);

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
                dungeonMonsterDataAdmin,
                talkAdmin,
                expendInventry,
                musicAdmin
        );

        backGround = graphic.searchBitmap("firstBackground");


        /*
        geoSlotSaver = new GeoSlotSaver(my_database_admin, "GeoSlotSave", "GeoSlotSave.db", Constants.SaveDataVersion.GEO_SLOT, Constants.DEBUG_SAVE_MODE, graphic);
        geoSlotAdminManager = new GeoSlotAdminManager(graphic, dungeon_user_interface, my_database_admin, text_box_admin, playerStatus, globalData.getGeoInventry(), geoSlotSaver, maohMenosStatus, soundAdmin, effectAdmin, dungeonModeManage);
        geoSlotAdminManager.loadGeoSlot();
        */

        dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP_INIT);

        //by kmhanko即座に魔王との戦闘画面へ
        if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH) {
            dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAOH_INIT);
        }


        //ブキ生成　デバッグよう
//        BattleDungeonUnitData battleDungeonUnitData = new BattleDungeonUnitData();
//        battleDungeonUnitData.setName(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getName());
//        battleDungeonUnitData.setStatus(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getStatus(0));
//        battleDungeonUnitData.setBonusStatus(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getBonusStatus(0));
//        battleDungeonUnitData.setBitmapData(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getBitmapData());
//        EquipmentItemBaseDataAdmin equipmentItemBaseDataAdmin = new EquipmentItemBaseDataAdmin(graphic, my_database_admin);
//        List<EquipmentItemBaseData> tempEquipmentItemBaseDatas = equipmentItemBaseDataAdmin.getItemDatas();
//        EquipmentItemBaseData[] equipmentItemBaseDatas = new EquipmentItemBaseData[Constants.Item.EQUIPMENT_KIND.NUM.ordinal()];
//        for (int i = 0; i < Constants.Item.EQUIPMENT_KIND.NUM.ordinal(); i++) {
//            equipmentItemBaseDatas[i] = tempEquipmentItemBaseDatas.get(i);
//        }
//        EquipmentItemDataCreater equipmentItemDataCreater = new EquipmentItemDataCreater(equipmentItemBaseDatas);
//        equipmentInventry.addItemData(
//                equipmentItemDataCreater.getEquipmentItemData(
//                        Constants.Item.EQUIPMENT_KIND.SWORD,
//                        battleDungeonUnitData,
//                        22222
//                )
//        );
//        equipmentInventry.addItemData(
//                equipmentItemDataCreater.getEquipmentItemData(
//                        Constants.Item.EQUIPMENT_KIND.BOW,
//                        battleDungeonUnitData,
//                        22222
//                )
//        );
//        equipmentInventry.addItemData(
//                equipmentItemDataCreater.getEquipmentItemData(
//                        Constants.Item.EQUIPMENT_KIND.AX,
//                        battleDungeonUnitData,
//                        22222
//                )
//        );//kokomade

        //デバッグ用
        //dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING_INIT);


        //
        //dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MAP_INIT);
    }

    public void saveMapSaveData() {
        map_status_saver.save();
    }


    public void update() {
        if (updateStopFlag) {
            return;
        }

        switch (dungeonModeManage.getMode()) {
            case MAP_INIT:
                if(map_init == false) {
                    map_init = true;
                    playMapBGM();
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
                    if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.DRAGON) {
                        backGround = graphic.searchBitmap("firstBackground");
                    }
                } else if(changeMovie.update(false) == true) {
                    playMapBGM();
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
                    if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.DRAGON) {
                        backGround = graphic.searchBitmap("firstBackground");
                    }
                }
                break;
            case MAP:
                if (!talkAdmin.isTalking()) {
                    map_object_admin.update();
                    map_plate_admin.update();
                    playerStatusViewer.update();
                }
                break;
            case OPENING_BATTLE_INIT:
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE);
                backGround = graphic.searchBitmap("firstBackground");
                musicAdmin.loadMusic("boss00",true);
                battle_user_interface.update();
                battle_unit_admin.update();
                break;

            case BUTTLE_INIT:
                if(changeMovie.update(true) == true) {
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE);
                    backGround = graphic.searchBitmap("firstBackground");
                    musicAdmin.loadMusic("battle00", true);
                }
                break;
            case BUTTLE:
                if (!talkAdmin.isTalking()) {
                    battle_user_interface.update();
                    battle_unit_admin.update();
                }
                break;

            case MAOH_INIT:
                playerStatus.calcStatus();
                playerStatus.setNowHP(playerStatus.getHP());
                battle_unit_admin.reset(BattleUnitAdmin.MODE.MAOH);
                int size = battleUnitDataAdmin.getMaohUnitNames().size();
                battle_unit_admin.spawnMaoh(
                        new String[] {
                                battleUnitDataAdmin.getMaohUnitNames().get(playerStatus.getMaohWinCount()%size)
                        }
                );
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAOH);
                musicAdmin.loadMusic("boss00",true);

            case MAOH:
                if (!talkAdmin.isTalking()) {
                    battle_user_interface.update();
                    battle_unit_admin.update();
                }
                break;

            case GEO_MINING_INIT:
                battle_unit_admin.reset(BattleUnitAdmin.MODE.MINING);
                //battle_unit_admin.spawnRock();　reset内で呼んでいる
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING);
                backGround = graphic.searchBitmap("miningBack");
                musicAdmin.loadMusic("battle00",true);

            case GEO_MINING:
                if (!talkAdmin.isTalking()) {
                    battle_user_interface.update();
                    battle_unit_admin.update();
                    playerStatusViewer.update();
                }
                break;

            case TO_WORLD:
                activityChange.toWorldActivity();
                break;

            case EQUIP_EXPEND_INIT:
                initBackPlate();
                palette_admin.setPalletPosition(0, 1400, 450);
                palette_admin.setPalletPosition(1, 200, 450);
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.EQUIP_EXPEND);

            case EQUIP_EXPEND:
                if (!talkAdmin.isTalking()) {
                    palette_admin.update(true);//便宜上

                    //TODO by kmhanko あまり良くない書き方
                    if (palette_admin.checkSelectedExpendItemData() != null) {
                        int heel_to_player = (int) (playerStatus.getHP() * palette_admin.checkSelectedExpendItemData().getHp() / 100.0f);
                        palette_admin.deleteExpendItemData();
                        soundAdmin.play("cure00");
                        playerStatus.setNowHP(playerStatus.getNowHP() + heel_to_player);
                        expendInventry.save();
                    }
                    playerStatusViewer.update();

                    backPlateGroup.update();
                }
                break;
            case ITEM_INIT:
                initBackPlate();
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.ITEM);

            case ITEM:
                if (!talkAdmin.isTalking()) {
                    map_plate_admin.update();
                    backPlateGroup.update();
                    playerStatusViewer.update();
                }
                break;
            case GEO_MAP_INIT:
                initBackPlate();
                //geoSlotAdminManager.start();
                //geoSlotAdminManager.setMode(GeoSlotAdminManager.MODE.DUNGEON);
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MAP);
            case GEO_MAP:
                if (!talkAdmin.isTalking()) {
                    //geoSlotAdminManager.update();
                    playerStatusViewer.update();
                }
                break;

        }
        if (updateStopFlag) {
            return;
        }

        talkAdmin.update();
        text_box_admin.update();

        effectAdmin.update();
        backEffectAdmin.update();

        activityChange.toChangeActivity();
    }

    public void draw() {
        if (drawStopFlag) {
            return;
        }

        switch (dungeonModeManage.getMode()) {
            case MAP_INIT:
                graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
                battle_unit_admin.draw();
                changeMovie.draw();
                break;
            case MAP:
                if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.DRAGON) {
                    graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
                }
                map_admin.drawMap_for_autotile_light_animation();
                backEffectAdmin.draw();
                map_object_admin.draw();
                map_admin.drawSmallMap();
                effectAdmin.draw();
                map_plate_admin.draw();
                if (playerStatus.getTutorialInDungeon() == 1) {
                    playerStatusViewer.draw();
                }
                break;

            case BUTTLE_INIT:
                if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.DRAGON) {
                    graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
                }
                map_admin.drawMap_for_autotile_light_animation();
                backEffectAdmin.draw();
                map_object_admin.draw();
                map_admin.drawSmallMap();
                effectAdmin.draw();
                map_plate_admin.draw();
                playerStatusViewer.draw();
                changeMovie.draw();
                break;

            case BUTTLE:
            case MAOH:
                graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
                backEffectAdmin.draw();
                battle_unit_admin.draw();
                effectAdmin.draw();
                break;

            case GEO_MINING:
                graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
                battle_unit_admin.draw();
                effectAdmin.draw();
                break;

            case ITEM_INIT:
                map_admin.drawMap_for_autotile_light_animation();
                backEffectAdmin.draw();
                map_object_admin.draw();
                effectAdmin.draw();
                map_admin.drawSmallMap();
                map_plate_admin.draw();
                playerStatusViewer.draw();
                break;

            case ITEM:
                map_admin.drawMap_for_autotile_light_animation();
                backEffectAdmin.draw();
                map_object_admin.draw();
                effectAdmin.draw();
                map_admin.drawSmallMap();
                map_plate_admin.draw();
                backPlateGroup.draw();
                playerStatusViewer.draw();
                break;

            case EQUIP_EXPEND_INIT:
                map_admin.drawMap_for_autotile_light_animation();
                backEffectAdmin.draw();
                map_object_admin.draw();
                effectAdmin.draw();
                map_admin.drawSmallMap();
                map_plate_admin.draw();
                //palette_admin.drawOnly();
                playerStatusViewer.draw();
                break;

            case EQUIP_EXPEND:
                map_admin.drawMap_for_autotile_light_animation();
                backEffectAdmin.draw();
                map_object_admin.draw();
                map_admin.drawSmallMap();
                map_plate_admin.draw();
                //equipmentInventry.drawOnly();
                //expendInventry.drawOnly();
                //dungeon_user_interface.draw();
                palette_admin.draw();
                effectAdmin.draw();
                backPlateGroup.draw();
                playerStatusViewer.draw();
                break;
            case GEO_MAP:
                //geoSlotAdminManager.draw();
                effectAdmin.draw();
                playerStatusViewer.draw();
                break;

        }

        talkAdmin.draw();
        text_box_admin.draw();
        graphic.draw();

        if (resetBossImage) {
            resetBossImage = false;
            map_object_admin.setBossBitmap("ボス");
        }
    }

    boolean updateStopFlag = false;
    public void updateStop() {
        updateStopFlag = true;
    }

    boolean drawStopFlag = false;
    public void drawStop() {
        drawStopFlag = true;
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

    public void release() {
        System.out.println("takanoRelease : DungeonGameSystem");
        if (map_admin != null) {
            map_admin.release();
        }
        if (dungeonMonsterDataAdmin != null) {
            dungeonMonsterDataAdmin.release();
        }
        if (battle_unit_admin != null) {
            battle_unit_admin.release();
        }
        if (palette_admin != null) {
            palette_admin.release();
        }
        if (map_object_admin != null) {
            map_object_admin.release();
        }
        if (dungeon_user_interface != null) {
            dungeon_user_interface.release();
        }
        if (battle_user_interface != null) {
            battle_user_interface.release();
        }
        if (text_box_admin != null) {
            text_box_admin.release();
        }
        if (dungeon_data_admin != null) {
            dungeon_data_admin.release();
        }
        if (talkAdmin != null) {
            talkAdmin.release();
        }
        if (effectAdmin != null) {
            effectAdmin.release();
        }
        //dungeonModeManage
        if (map_plate_admin != null) {
            map_plate_admin.release();
        }
        //map_inventry_admin
        if (map_status != null) {
            map_status.release();
        }
        if (map_status_saver != null) {
            map_status_saver.release();
        }
        if (paint != null) {
            paint.reset();
            paint = null;
        }
        if (equipment_item_data_admin != null) {
            equipment_item_data_admin.release();
        }
        if (miningItemDataAdmin != null) {
            miningItemDataAdmin.release();
        }
        if (playerStatusViewer != null) {
            playerStatusViewer.release();
        }
        /*
        if (geoSlotSaver != null) {
            geoSlotSaver.release();
        }
        if (geoSlotAdminManager != null) {
            geoSlotAdminManager.release();
        }
        */
        if (dungeonModeManage != null) {
            dungeonModeManage.release();
        }
        System.gc();
    }


    //オープニング関係

    //int count = 0;
    //int openningTextBoxID;
    //boolean text_mode = false;
    boolean boss_is_running = false;
    //boolean flag1 = false;
    //boolean charaFlag;
    boolean resetBossImage = false;

    //ImageContext talkChara;

    public void openningInit() {
        /*
        openningTextBoxID = text_box_admin.createTextBox(50, 700, 1550, 880, 4);
        */
        playMapBGM();
        /*
        text_box_admin.setTextBoxUpdateTextByTouching(openningTextBoxID, false);
        text_box_admin.setTextBoxExists(openningTextBoxID, false);
        */
        map_admin.createOpeningMap();
        paint.setTextSize(70);
        paint.setARGB(255,0,0,0);
    }

    /*
    public void drawCharaAndTouchCheck(ImageContext _imageContext) {
        if (_imageContext != null) {
            charaFlag = true;
        }
        if (battle_user_interface.getTouchState() == Constants.Touch.TouchState.UP) {
            text_box_admin.setTextBoxExists(openningTextBoxID, false);
            soundAdmin.play("textenter00");
            text_mode = false;
        }
    }
    */

    int opennigCount = 0;
    int alpha = 0;
    int up_down = 5;
    boolean introFlag = true;

    public void openningUpdate() {

        if(introFlag == true) {
            paint.setARGB(255, 0, 0, 0);
            graphic.bookingDrawRect(0, 0, 1600, 900, paint);

            paint.setARGB(alpha, 255, 255, 255);

            alpha += up_down;
            if (alpha < 0 || alpha > 255) {
                if (alpha < 0) {
                    introFlag = false;
                    map_object_admin.putPlayer();
                    talkAdmin.start("Opening_in_dungeon");
                }
                up_down *= -1;
                alpha += up_down * 2;
            }
        }else {
            talkAdmin.update();//先にやること

            if (talkAdmin.isUpdateThisFrame() && talkAdmin.getID() == 3) {
                map_object_admin.putBoss();
                boss_is_running = true;
            }

            if (map_object_admin.bossIsHitPlayer(160)) {
                boss_is_running = false;
                //ボスとの戦闘
                System.out.println("茶番：ボスとの戦闘");

                playerStatus.calcStatus();
                playerStatus.setNowHP(playerStatus.getHP());
                battle_unit_admin.reset(BattleUnitAdmin.MODE.OPENING);
                battle_unit_admin.spawnEnemy(
                        new String[]{
                                "maoh001"
                        }
                );
                ((DungeonActivity) dungeonActivity).dungeon_surface_view.setOpeningFlag(false);////////
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.OPENING_BATTLE_INIT);
                //text_box_admin.setTextBoxExists(openningTextBoxID, false);
                resetBossImage = true;
            }
            if (talkAdmin.isWaitOrNotTalk()) {
                map_object_admin.openingUpdate(boss_is_running);
                //count++;
            }

            text_box_admin.update();
        }
    }

    public void openningDraw() {
        if(introFlag == true) {
            graphic.bookingDrawText("ある日のこと・・・",350,480, paint);
        }else {
            map_admin.drawOpeningMap();
            map_object_admin.draw();
            talkAdmin.draw();
            text_box_admin.draw();
        }
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
                                soundAdmin.play("cancel00");
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

