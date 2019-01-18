package com.maohx2.ina.GameSystem;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.Random;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
//import com.maohx2.horie.EquipTutorial.EquipTutorialSaveData;
//import com.maohx2.horie.EquipTutorial.EquipTutorialSaver;
import com.maohx2.horie.Tutorial.TutorialFlagData;
import com.maohx2.horie.Tutorial.TutorialFlagSaver;
import com.maohx2.ina.Activity.UnitedActivity;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleDungeonUnitData;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Battle.BattleUnitDataAdmin;
import com.maohx2.ina.ChangeMovie;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Credits;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.EquipmentInventrySaver;
import com.maohx2.ina.ItemData.EquipmentItemBaseData;
import com.maohx2.ina.ItemData.EquipmentItemBaseDataAdmin;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.EquipmentItemDataCreater;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.ina.Constants;
import static com.maohx2.ina.Constants.GAMESYSTEN_MODE.WORLD_MODE;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.GeoPresent.GeoPresentManager;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatusViewer;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatusViewer;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.Saver.GeoPresentSaver;
import com.maohx2.kmhanko.Talking.TalkAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectDataCreater;
import com.maohx2.kmhanko.itemshop.ItemShopAdmin;
import com.maohx2.kmhanko.effect.*;
import com.maohx2.kmhanko.plate.BackPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;
import com.maohx2.kmhanko.music.MusicAdmin;
import com.maohx2.kmhanko.itemshop.ItemSell;
import com.maohx2.horie.map.MapStatus;
import com.maohx2.horie.map.MapStatusSaver;
import com.maohx2.kmhanko.Talking.TalkSaveDataAdmin;
import com.maohx2.ina.GlobalData;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Paint;

/**
 * Created by ina on 2017/10/01.
 */
public class WorldGameSystem {

    SurfaceHolder holder;
    Canvas canvas;
    TextBoxAdmin text_box_admin;

    GeoSlotAdminManager geoSlotAdminManager;
    MyDatabaseAdmin databaseAdmin;
    Graphic graphic;

    DungeonSelectManager dungeonSelectManager;
    ItemShopAdmin itemShopAdmin;
    ItemDataAdminManager itemDataAdminManager;
    GeoPresentManager geoPresentManager;
    ItemSell itemSell;

    EffectAdmin effectAdmin;
    SoundAdmin soundAdmin;

    BattleUserInterface world_user_interface;

    WorldModeAdmin worldModeAdmin;

    //WorldActivity worldActivity;
    UnitedActivity unitedActivity;

    PlayerStatus playerStatus;
    MaohMenosStatus maohMenosStatus;

    GeoInventrySaver geoInventrySaver;
    ExpendItemInventrySaver expendItemInventrySaver;
    GeoSlotSaver geoSlotSaver;
    GeoPresentSaver geoPresentSaver;
    //ActivityChange activityChange;

    //EquipTutorial
//    EquipTutorialSaveData equip_tutorial_save_data;
//    EquipTutorialSaver equip_tutorial_saver;
    TutorialFlagData tutorial_flag_data;
    TutorialFlagSaver tutorial_flag_saver;

    //TODO いな依頼:引数にUI,Graphicが入って居るためGlobalDataに設置できない
    InventryS geoInventry;
    InventryS expendItemInventry;

    //TODO いな依頼　Inventryのupdateを呼ばないと真っ黒。あとアクティブ関係
    PaletteAdmin palette_admin;
    EquipmentInventrySaver equipmentInventrySaver;
    InventryS equipmentInventry;

    BitmapData backGround;
    BitmapData tu_equip_img, tu_equip_start_img;
    BitmapData tu_shop_img, tu_shop_start_img;
    BitmapData tu_sell_img;
    BitmapData tu_geo_img, tu_geo_start_img;
    int tu_geo_flag, tu_shop_flag, tu_equip_flag;

    BitmapData[] credit = new BitmapData[2];

    //String talkContent[][] = new String[100][];
    //ImageContext talkChara[] = new ImageContext[100];

    MapStatus map_status;
    MapStatusSaver map_status_saver;

    int credit_num = 1;

    PlayerStatusViewer playerStatusViewer;
    MaohMenosStatusViewer maohMenosStatusViewer;

    MusicAdmin musicAdmin;
    EquipmentItemDataAdmin equipment_item_data_admin;

    TalkAdmin talkAdmin;

    boolean is_equip_tutorial = true;
    BattleUnitDataAdmin battleUnitDataAdmin;

    Credits credits;

    ChangeMovie changeMovie;

    public void init(BattleUserInterface _world_user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin, UnitedActivity _unitedActivity) {
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        soundAdmin = _soundAdmin;
        world_user_interface = _world_user_interface;
        //activityChange = _activityChange;
        unitedActivity = _unitedActivity;

        tu_equip_img = graphic.searchBitmap("t_equip");
        tu_shop_img = graphic.searchBitmap("t_shop");
        tu_sell_img = graphic.searchBitmap("t_sell");
        tu_geo_img = graphic.searchBitmap("t_geo");
        tu_geo_start_img = graphic.searchBitmap("t_geo_start");
        tu_shop_start_img = graphic.searchBitmap("t_shop_start");
        tu_equip_start_img = graphic.searchBitmap("t_equip_start");

        //mapセーブ関係
        map_status = new MapStatus(Constants.STAGE_NUM);
        map_status_saver = new MapStatusSaver(databaseAdmin, "MapSaveData", "MapSaveData.db", Constants.SaveDataVersion.MAP_SAVE_DATA, Constants.DEBUG_SAVE_MODE, map_status, 7);
        map_status_saver.load();

        //TutorialFlagセーブ関係
//        equip_tutorial_save_data = new EquipTutorialSaveData();
//        equip_tutorial_saver = new EquipTutorialSaver(databaseAdmin, "EquipTutorialSave", "EquipTutorialSave.db", Constants.SaveDataVersion.MAP_SAVE_DATA, Constants.DEBUG_SAVE_MODE,equip_tutorial_save_data);
//        equip_tutorial_saver.load();
        tutorial_flag_data = new TutorialFlagData();
        tutorial_flag_saver = new TutorialFlagSaver(databaseAdmin, "FlagSave", "FlagSave.db", Constants.SaveDataVersion.MAP_SAVE_DATA, Constants.DEBUG_SAVE_MODE,tutorial_flag_data);
        tutorial_flag_saver.load();

//        /*tutorial_flagセーブ係デバッグ用*/
//        for(int i = 0;i < 3;i++){
//            System.out.println("堀江：flag_data.flag_name["+i+"] = "+tutorial_flag_data.getFlag_name(i)+" ,flag = "+tutorial_flag_data.getIs_tutorial_finished(i));
//        }
//        tutorial_flag_data.setIs_tutorial_finished(1, 0);
//        tutorial_flag_saver.save();
//        tutorial_flag_saver.load();
//        for(int i = 0;i < 3;i++){
//            System.out.println("堀江２：flag_data.flag_name["+i+"] = "+tutorial_flag_data.getFlag_name(i)+" ,flag = "+tutorial_flag_data.getIs_tutorial_finished(i));
//        }

        //クレジット
        credit[0] = graphic.searchBitmap("クレジット1");
        credit[1] = graphic.searchBitmap("クレジット2");


        GlobalData globalData = (GlobalData) unitedActivity.getApplication();
        playerStatus = globalData.getPlayerStatus();
        maohMenosStatus = globalData.getMaohMenosStatus();
        musicAdmin = globalData.getMusicAdmin();

        playerStatusViewer = new PlayerStatusViewer(graphic, world_user_interface, playerStatus);
        maohMenosStatusViewer = new MaohMenosStatusViewer(graphic, world_user_interface, maohMenosStatus);

        worldModeAdmin = new WorldModeAdmin();
        worldModeAdmin.initWorld();

        //soundAdmin.loadSoundPack("basic");

        text_box_admin = new TextBoxAdmin(graphic, soundAdmin);
        text_box_admin.init(world_user_interface);

        talkAdmin = new TalkAdmin(graphic, world_user_interface, databaseAdmin, text_box_admin , soundAdmin);

        itemDataAdminManager = globalData.getItemDataAdminManager();//new ItemDataAdminManager();
        itemShopAdmin = new ItemShopAdmin();

        effectAdmin = new EffectAdmin(graphic, databaseAdmin, soundAdmin);

        itemDataAdminManager.init(databaseAdmin, graphic);

        geoInventrySaver = globalData.getGeoInventrySaver();
        geoInventry = globalData.getGeoInventry();
        expendItemInventrySaver = globalData.getExpendItemInventrySaver();
        expendItemInventry = globalData.getExpendItemInventry();

        geoSlotSaver = new GeoSlotSaver(databaseAdmin, "GeoSlotSave", "GeoSlotSave.db", Constants.SaveDataVersion.GEO_SLOT, Constants.DEBUG_SAVE_MODE, graphic);
        geoSlotAdminManager = new GeoSlotAdminManager(graphic, world_user_interface, worldModeAdmin, databaseAdmin, text_box_admin, playerStatus, geoInventry, geoSlotSaver, maohMenosStatus, soundAdmin, effectAdmin);


        dungeonSelectManager = new DungeonSelectManager(graphic, world_user_interface, text_box_admin, worldModeAdmin, databaseAdmin, geoSlotAdminManager, playerStatus, soundAdmin, unitedActivity, map_status,map_status_saver, talkAdmin);

        geoSlotAdminManager.loadGeoSlot();

        itemShopAdmin.init(graphic, world_user_interface, worldModeAdmin, databaseAdmin, text_box_admin, itemDataAdminManager, expendItemInventry, geoInventry, playerStatus, soundAdmin);
        itemShopAdmin.makeAndOpenItemShop(ItemShopAdmin.ITEM_KIND.EXPEND, "expendBasic");

        itemSell = new ItemSell(graphic, world_user_interface, unitedActivity, text_box_admin, worldModeAdmin, soundAdmin);

        canvas = null;

        GeoObjectDataCreater.setGraphic(graphic);

        //by kmhanko デバッグ用削除

        // 仮。適当にGeo入れる GEO1が上がる能力は単一
        /*
        for (int i = 0; i < 50; i++) {
            geoInventry.addItemData(GeoObjectDataCreater.getGeoObjectData(1000000));
            //デバッグ用
            playerStatus.addMoney(100000000);
            //playerStatus.calcStatus();
        }
        */

        credits = new Credits(graphic);

        geoPresentManager = new GeoPresentManager(
                graphic,
                world_user_interface,
                worldModeAdmin,
                databaseAdmin,
                text_box_admin,
                geoInventry,
                expendItemInventry,
                itemDataAdminManager.getExpendItemDataAdmin(),
                playerStatus,
                soundAdmin
        );


        GeoPresentSaver.setGeoPresentManager(geoPresentManager);
        geoPresentSaver = new GeoPresentSaver(
                databaseAdmin,
                "GeoPresentSave",
                "GeoPresentSave.db",
                Constants.SaveDataVersion.GEO_PRESENT, Constants.DEBUG_SAVE_MODE
        );

        geoPresentManager.setGeoPresentSaver(geoPresentSaver);

        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);

        //palette_admin = new PaletteAdmin(world_user_interface, graphic);
        equipmentInventry = globalData.getEquipmentInventry();
        equipmentInventrySaver = globalData.getEquipmentInventrySaver();
        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, databaseAdmin);

        palette_admin = new PaletteAdmin(world_user_interface, graphic, equipmentInventry, expendItemInventry, equipment_item_data_admin, soundAdmin);

        backGround = graphic.searchBitmap("firstBackground");

        geoSlotAdminManager.calcPlayerStatus();

        changeMovie = new ChangeMovie(graphic, soundAdmin);

        worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT_INIT_START);


        //TODO かり。戻るボタン
        initBackPlate();

        //OP判定。まだOPを流していないならOP会話イベントを発動する。
        talkAdmin.start("Opening_in_world", false);//セーブデータ関係を内包しており、ゲーム中一度のみ実行される//堀江デバッグのためにコメントアウト


        /*
        battleUnitDataAdmin = new BattleUnitDataAdmin(databaseAdmin, graphic);
        battleUnitDataAdmin.loadBattleUnitData(Constants.DungeonKind.DUNGEON_KIND.FOREST);//敵読み込み

        //ブキ生成 デバッグよう
        BattleDungeonUnitData battleDungeonUnitData = new BattleDungeonUnitData();
        battleDungeonUnitData.setName(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getName());
        battleDungeonUnitData.setStatus(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getStatus(0));
        battleDungeonUnitData.setBonusStatus(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getBonusStatus(0));
        battleDungeonUnitData.setBitmapData(battleUnitDataAdmin.getBattleBaseUnitData().get(0).getBitmapData());
        EquipmentItemBaseDataAdmin equipmentItemBaseDataAdmin = new EquipmentItemBaseDataAdmin(graphic, databaseAdmin);
        List<EquipmentItemBaseData> tempEquipmentItemBaseDatas = equipmentItemBaseDataAdmin.getItemDatas();
        EquipmentItemBaseData[] equipmentItemBaseDatas = new EquipmentItemBaseData[Constants.Item.EQUIPMENT_KIND.NUM.ordinal()];
        for (int i = 0; i < Constants.Item.EQUIPMENT_KIND.NUM.ordinal(); i++) {
            equipmentItemBaseDatas[i] = tempEquipmentItemBaseDatas.get(i);
        }
        EquipmentItemDataCreater equipmentItemDataCreater = new EquipmentItemDataCreater(equipmentItemBaseDatas);
        equipmentInventry.addItemData(
                equipmentItemDataCreater.getEquipmentItemData(
                        Constants.Item.EQUIPMENT_KIND.SWORD,
                        battleDungeonUnitData,
                        22222
                )
        );
        equipmentInventry.addItemData(
                equipmentItemDataCreater.getEquipmentItemData(
                        Constants.Item.EQUIPMENT_KIND.BOW,
                        battleDungeonUnitData,
                        22222
                )
        );
        equipmentInventry.addItemData(
                equipmentItemDataCreater.getEquipmentItemData(
                        Constants.Item.EQUIPMENT_KIND.AX,
                        battleDungeonUnitData,
                        22222
                )
        );//kokomade
        */

        /* エフェクトテスト用
        width = 120;
        height = 150;

        attackExtendX = (float)(width+height)/2.0f/(768.0f/4.0f*2.0f)*1.5f;
        attackExtendY = (float)(width+height)/2.0f/(768.0f/4.0f*2.0f)*1.5f;

        damagedExtendX = (float)(width+height)/2.0f/(960.0f/5.0f*2.0f)*1.5f;
        damagedExtendY = (float)(width+height)/2.0f/(960.0f/5.0f*2.0f)*1.5f;

        damagedEffect = effectAdmin.createEffect("enemy_damaged_effect" , "bomb_effect", 5, 2, 1);
        //attackEffect = effectAdmin.createEffect("enemy_attack_effect", "enemy_attack", 4, 2, 1);
        */

    }

    /* エフェクトテスト用
    int damagedEffect;
    int attackEffect;
    int id;
    int width;
    int height;
    int damageEffectTime;
    float attackExtendX;
    float attackExtendY;
    float damagedExtendX;
    float damagedExtendY;
    Random rnd = new Random();

    double scale = 3.0f;

    int position_x = 800;
    int position_y = 400;
    */


    int count = 0;
    public void update() {
        if (updateStopFlag) {
            return;
        }


        /* エフェクトテスト用
        damageEffectTime++;
        if (damageEffectTime >= 4 && worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.DUNGEON_SELECT) {
            damagedEffect = effectAdmin.createEffect("enemy_damaged_effect", "bomb_effect", 5, 2, 1);
            effectAdmin.getEffect(damagedEffect).setPosition((int) position_x + rnd.nextInt((int) (width * scale) + 1) - (int) (width * scale) / 2, (int) position_y + rnd.nextInt((int) (scale * height) + 1) - (int) (scale * height) / 2);
            effectAdmin.setExtends(damagedEffect, damagedExtendX, damagedExtendY);
            effectAdmin.getEffect(damagedEffect).start();
            damageEffectTime = 0;
        }
        */

/*
        if (world_user_interface.getTouchState() == Constants.Touch.TouchState.DOWN) {
            List<BitmapData> testBitmapData = new ArrayList<BitmapData>();
            int testID = effectAdmin.createEffect("test2", "打撃01", 9,1);
            effectAdmin.getEffect(testID).setPosition((int)world_user_interface.getTouchX(),(int)world_user_interface.getTouchY());
            effectAdmin.getEffect(testID).start();
        }
*/
/*
        if (!talkAdmin.isTalking()) {
            //talkAdmin.debug();//堀江デバッグのためにコメントアウト
        }
*/
        switch (worldModeAdmin.getMode()) {
            case DUNGEON_SELECT_INIT_START:
                if(changeMovie.update(false, false) == true) {
                    musicAdmin.loadMusic("world00", true);
                    worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT_INIT);
                }
                break;
            case DUNGEON_SELECT_INIT:
                backGround = graphic.searchBitmap("firstBackground");
                worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT);
                //maohMenosStatusViewer.effectClear();
                dungeonSelectManager.start();
            case DUNGEON_SELECT:
                if (!talkAdmin.isTalking()) {
                    dungeonSelectManager.update();
                }
                playerStatusViewer.update();
                break;
            case GEO_MAP_SELECT_INIT:
                backGround = graphic.searchBitmap("GeoMap");
                worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_SELECT);
                if(tutorial_flag_data.getIs_tutorial_finished(3) == 0){
                    worldModeAdmin.setMode(WORLD_MODE.TU_GEO);
                    tutorial_flag_data.setIs_tutorial_finished(1, 3);//チュートリアルフラグを立てる
                }
            case GEO_MAP_SELECT:
                if (!talkAdmin.isTalking()) {
                    dungeonSelectManager.update();
                }
                break;
            case TU_GEO:
                if(world_user_interface.getTouchState() == Constants.Touch.TouchState.UP && tu_geo_flag == 0) {
                    tu_geo_flag = 1;
                }
                else if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP && tu_geo_flag == 1) {
                    worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_SELECT_INIT);
                    tutorial_flag_saver.save();
                }
                break;
            case GEO_MAP_INIT:
                backGround = graphic.searchBitmap("GeoMap");
                worldModeAdmin.setMode(WORLD_MODE.GEO_MAP);
                geoSlotAdminManager.start();
            case GEO_MAP:
                if (!talkAdmin.isTalking()) {
                    geoSlotAdminManager.update();
                }
                playerStatusViewer.update();
                if (geoSlotAdminManager.isMaohGeoMap()) {
                    maohMenosStatusViewer.update();
                }
                break;
            case SHOP_INIT:
                itemShopAdmin.start();
                backGround = graphic.searchBitmap("City");
                worldModeAdmin.setMode(WORLD_MODE.SHOP);
                if(tutorial_flag_data.getIs_tutorial_finished(1) == 0) {//shopチュートリアル
                    worldModeAdmin.setMode(WORLD_MODE.TU_SHOP);
                    tutorial_flag_data.setIs_tutorial_finished(1, 1);//チュートリアルフラグを立てる
                }
                else if(tutorial_flag_data.getIs_tutorial_finished(2) == 0){//sellチュートリアル
                    worldModeAdmin.setMode(WORLD_MODE.TU_SELL);
                    tutorial_flag_data.setIs_tutorial_finished(1, 2);
                }
            case SHOP:
                if (!talkAdmin.isTalking()) {
                    itemShopAdmin.update();
                }
                playerStatusViewer.update();
                break;
            case TU_SHOP:
                if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP && tu_shop_flag == 0) {
                    tu_shop_flag = 1;
                }
                else if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP && tu_shop_flag == 1) {
                    worldModeAdmin.setMode(WORLD_MODE.SHOP_INIT);
                    tutorial_flag_saver.save();
                }
                break;
            case TU_SELL:
                if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP) {
                    worldModeAdmin.setMode(WORLD_MODE.SHOP_INIT);
                    tutorial_flag_saver.save();
                }
                break;
            case EQUIP_INIT:
                backGround = graphic.searchBitmap("equipBackground");//仮
                worldModeAdmin.setMode(WORLD_MODE.EQUIP);
                equipmentInventry.setPosition(825,100,1225,708, 7);
                expendItemInventry.setPosition(375,100,775,708, 7);
                worldModeAdmin.setMode(WORLD_MODE.EQUIP);
                if(tutorial_flag_data.getIs_tutorial_finished(0) == 0){
                    worldModeAdmin.setMode(WORLD_MODE.TU_EQUIP);
                    tutorial_flag_data.setIs_tutorial_finished(1, 0);
                }
            case EQUIP:
                if (!talkAdmin.isTalking()) {
                    equipmentInventry.updata();
                    expendItemInventry.updata();
                    palette_admin.update(false);
                    backPlateGroup.update();
                    //equipmentInventry.onArrow();
                    //expendItemInventry.onArrow();
                }
                break;
            case TU_EQUIP:
                if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP && tu_equip_flag == 0) {
                    tu_equip_flag = 1;
                }
                else if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP && tu_equip_flag == 1) {
                    worldModeAdmin.setMode(WORLD_MODE.EQUIP_INIT);
                    tutorial_flag_saver.save();
                }
                break;
            case PRESENT_INIT:
                geoPresentManager.start();
                backGround = graphic.searchBitmap("firstBackground");//TODO 仮
                worldModeAdmin.setMode(WORLD_MODE.PRESENT);
            case PRESENT:
                if (!talkAdmin.isTalking()) {
                    geoPresentManager.update();
                }
                break;
            case SELL_INIT:
                itemSell.start();
                backGround = graphic.searchBitmap("City");
                worldModeAdmin.setMode(WORLD_MODE.SELL);
            case SELL:
                if (!talkAdmin.isTalking()) {
                    itemSell.update();
                }
                playerStatusViewer.update();
                break;

                /*
            case GEO_MAP_SEE_ONLY_INIT:
                initBackPlate();
                geoSlotAdminManager.start();
                worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_SEE_ONLY);
            case GEO_MAP_SEE_ONLY:
                geoSlotAdminManager.updateInStatus();
                break;
*//*
            case GEO_MAP_SEE_ONLY_INIT:
                backGround = graphic.searchBitmap("GeoMap");
                worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_SEE_ONLY);
                geoSlotAdminManager.start();
            case GEO_MAP_SEE_ONLY:
                if (!talkAdmin.isTalking()) {
                    geoSlotAdminManager.update();
                }
                playerStatusViewer.update();
                break;
*/
            case CREDIT:
                backPlateGroup.update();
                if(world_user_interface.getTouchState() == Constants.Touch.TouchState.UP){
                    credit_num = 3 - credit_num;
                }
                break;
            case ENDING:
                credits.update();
                if(credits.endCheck() == true){
                    worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT_INIT_START);
                }
                break;
            default:
                break;
        }
        if (updateStopFlag) {
            return;
        }
        talkAdmin.update();
        text_box_admin.update();
        effectAdmin.update();
        //musicAdmin.update();

        //activityChange.toChangeActivity();
    }


    public void draw() {

        if (drawStopFlag) {
            return;
        }

        //graphic.bookingDrawBitmapData(graphic.searchBitmap("杖"),300,590);

        switch (worldModeAdmin.getMode()) {
            case DUNGEON_SELECT_INIT_START:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                dungeonSelectManager.draw();
                playerStatusViewer.draw();
                changeMovie.draw();
                break;
            case DUNGEON_SELECT_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                dungeonSelectManager.draw();
                playerStatusViewer.draw();
                break;
            case DUNGEON_SELECT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                dungeonSelectManager.draw();
                playerStatusViewer.draw();
                break;
            case GEO_MAP_SELECT_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                break;
            case GEO_MAP_SELECT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                dungeonSelectManager.draw();
                break;
            case GEO_MAP_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                break;
            case GEO_MAP:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                effectAdmin.draw();
                geoSlotAdminManager.draw();
                playerStatusViewer.draw();
                if (geoSlotAdminManager.isMaohGeoMap()) {
                    maohMenosStatusViewer.draw();
                }
                break;
            case TU_GEO:
                if(tu_geo_flag == 0) {
                    graphic.bookingDrawBitmapData(tu_geo_start_img, 0, 0, 0.983f, 0.983f, 0, 255, true);
                }
                else{
                    graphic.bookingDrawBitmapData(tu_geo_img, 0, 0, 0.983f, 0.983f, 0, 255, true);
                }
                break;
            case SHOP_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                break;
            case SHOP:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                itemShopAdmin.draw();
                playerStatusViewer.draw();
                break;
            case TU_SHOP:
                if(tu_shop_flag == 0) {
                    graphic.bookingDrawBitmapData(tu_shop_start_img, 0, 0, 0.983f, 0.983f, 0, 255, true);
                }
                else{
                    graphic.bookingDrawBitmapData(tu_shop_img, 0, 0, 0.983f, 0.983f, 0, 255, true);
                }
                break;
            case TU_SELL:
                graphic.bookingDrawBitmapData(tu_sell_img, 0, 0, 0.983f, 0.983f, 0, 255, true);
                break;
            case EQUIP_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                break;
            case TU_EQUIP:
                if(tu_equip_flag == 0) {
                    graphic.bookingDrawBitmapData(tu_equip_start_img, 0, 0, 0.983f, 0.983f, 0, 255, true);
                }
                else{
                    graphic.bookingDrawBitmapData(tu_equip_img, 0, 0, 0.983f, 0.983f, 0, 255, true);
                }
                break;
            case EQUIP:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                equipmentInventry.draw();
                expendItemInventry.draw();
                palette_admin.draw();
                world_user_interface.draw();
                backPlateGroup.draw();
                break;
            case PRESENT_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0,true);
                break;
            case PRESENT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                geoPresentManager.draw();
                break;
            case SELL_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                break;
            case SELL:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                itemSell.draw();
                playerStatusViewer.draw();
                break;
                /*
            case GEO_MAP_SEE_ONLY_INIT:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                break;
            case GEO_MAP_SEE_ONLY:
                graphic.bookingDrawBitmapData(backGround, 0, 0, true);
                effectAdmin.draw();
                geoSlotAdminManager.draw();
                playerStatusViewer.draw();
                break;
                */
            case CREDIT:
                graphic.bookingDrawBitmapData(credit[credit_num - 1], 0, 0, 1.25f, 1.25f, 0, 255, true);
                backPlateGroup.draw();
                break;
            case ENDING:
                credits.draw();
                break;
            default:
                break;
        }

        talkAdmin.draw(); //分岐は特に必要ない
        text_box_admin.draw();

        //GEOMAPでは諸事情により、エフェクトを背後に描画したいため
        if (worldModeAdmin.getMode() != WORLD_MODE.GEO_MAP) {
            effectAdmin.draw();
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


    //TODO 仮。もどるボタン
    PlateGroup<BackPlate> backPlateGroup;

    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[]{
                        new BackPlate(
                                graphic, world_user_interface
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                soundAdmin.play("cancel00");
                                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.DUNGEON_SELECT_INIT);
                                /*worldModeAdmin.setEquip(Constants.Mode.ACTIVATE.STOP);
                                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.ACTIVE);
                                */

                                expendItemInventry.save();
                                equipmentInventry.save();

                            }
                        }
                }
        );
    }

    public void release() {
        System.out.println("takanoRelease : WorldGameSystem");
        if (palette_admin != null) {
            palette_admin.release();
        }

        if (map_status != null) {
            map_status.release();
        }
        if (map_status_saver != null) {
            map_status_saver.release();
        }
        if (playerStatusViewer != null) {
            playerStatusViewer.release();
        }
        if (worldModeAdmin != null) {
            worldModeAdmin.release();
        }
        if (text_box_admin != null) {
            text_box_admin.release();
        }
        if (talkAdmin != null) {
            talkAdmin.release();
        }
        /*
        if (itemDataAdminManager != null) {
            itemDataAdminManager.release();
        }
        */
        if (itemShopAdmin != null) {
            itemShopAdmin.release();
        }
        if (effectAdmin != null) {
            effectAdmin.release();
        }
        if (geoSlotSaver != null) {
            geoSlotSaver.release();
        }
        if (geoSlotAdminManager != null) {
            geoSlotAdminManager.release();
        }
        if (dungeonSelectManager != null) {
            dungeonSelectManager.release();
        }
        if (itemSell != null) {
            itemSell.release();
        }
        if (geoPresentManager != null) {
            geoPresentManager.release();
        }
        if (geoPresentSaver != null) {
            geoPresentSaver.release();
        }
        /*
        if (equipment_item_data_admin != null) {
            equipment_item_data_admin.release();
        }
        */

        if (backPlateGroup != null) {
            backPlateGroup.release();
            backPlateGroup = null;
        }

        if (changeMovie != null) {
            changeMovie.release();
            changeMovie = null;
        }
        credit = null;
        System.gc();
    }

/*
    int count = 0;
    int openningTextBoxID;
    boolean text_mode = false;


    public void openningInit() {

        musicAdmin.loadMusic("world00",true);

        openningTextBoxID = text_box_admin.createTextBox(50, 700, 1550, 880, 4);
        text_box_admin.setTextBoxUpdateTextByTouching(openningTextBoxID, true);
        text_box_admin.setTextBoxExists(openningTextBoxID, true);
        backGround = graphic.searchBitmap("firstBackground");
        dungeonSelectManager.update();

        paint.setTextSize(35);
        paint.setARGB(255, 255, 255, 255);



        talkContent[0] = new String[2];
        talkChara[0] = graphic.makeImageContext(graphic.searchBitmap("syujinko7r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[0][0] = "あいたた，なんだあいつ，ひどい目にあったな・・・．";
        talkContent[0][1] = "MOP";


        talkContent[1] = new String[4];
        talkChara[1] = graphic.makeImageContext(graphic.searchBitmap("gaia13l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[1][0] = "あらら，やられちゃったかぁ．";
        talkContent[1][1] = "\n";
        talkContent[1][2] = "もしかしたらうまくやってくれるかもと思ったのに・・・．";
        talkContent[1][3] = "MOP";


        talkContent[2] = new String[4];
        talkChara[2] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[2][0] = "うわっ，だれ！？．";
        talkContent[2][1] = "\n";
        talkContent[2][2] = "びっくりしたー．";
        //talkContent[2][3] = "\n";
        //talkContent[2][4] = "うまくやるって何？";
        talkContent[2][3] = "MOP";


        talkContent[3] = new String[8];
        talkChara[3] = graphic.makeImageContext(graphic.searchBitmap("gaia15l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[3][0] = "私はガイアよ．";
        talkContent[3][1] = "\n";
        talkContent[3][2] = "あなたが生まれたときからずっとあなたのことを見ていたわ．";
        talkContent[3][3] = "\n";
        talkContent[3][4] = "そして，さっきあなたが出会ったのは魔王よ．";
        talkContent[3][5] = "\n";
        talkContent[3][6] = "もしかしたらあなたが倒してくれると思ったんだけど．";
        talkContent[3][7] = "MOP";


        talkContent[4] = new String[6];
        talkChara[4] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[4][0] = "魔王！？";
        talkContent[4][1] = "\n";
        talkContent[4][2] = "魔王ってなにいっているんだ，突然だな．";
        talkContent[4][3] = "\n";
        talkContent[4][4] = "それになんで俺が生まれたときからずっと俺のことを見てるんだよ，ストーカーか？";
        talkContent[4][5] = "MOP";


        talkContent[5] = new String[4];
        talkChara[5] = graphic.makeImageContext(graphic.searchBitmap("gaia12l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[5][0] = "それはあなたがこの世界を流れる地脈のエネルギー．";
        talkContent[5][1] = "\n";
        talkContent[5][2] = "ジオエネルギーの加護を受けるものだからよ！！";
        talkContent[5][3] = "MOP";


        talkContent[6] = new String[2];
        talkChara[6] = graphic.makeImageContext(graphic.searchBitmap("syujinko5r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[6][0] = "・・・．";
        talkContent[6][1] = "MOP";


        talkContent[7] = new String[4];
        talkChara[7] = graphic.makeImageContext(graphic.searchBitmap("gaia14l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[7][0] = "あと，ストーカーはやめて．";
        talkContent[7][1] = "\n";
        talkContent[7][2] = "これでも女神様なんだから，あなたのことをずっと加護してたのよ，感謝しなさい．";
        talkContent[7][3] = "MOP";


        talkContent[8] = new String[4];
        talkChara[8] = graphic.makeImageContext(graphic.searchBitmap("syujinko5r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[8][0] = "加護だか何だかわからないけど，図々しい女神さまだな．";
        talkContent[8][1] = "\n";
        talkContent[8][2] = "俺は加護なんてなくても生きていけるし，そんなものあったって何の得にもならない．";
        talkContent[8][3] = "MOP";


        talkContent[9] = new String[6];
        talkChara[9] = graphic.makeImageContext(graphic.searchBitmap("gaia15l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[9][0] = "何よ，失礼な．";
        talkContent[9][1] = "\n";
        talkContent[9][2] = "ほら，最近いいことあったんじゃない？";
        talkContent[9][3] = "\n";
        talkContent[9][4] = "宝くじが当たったり，好きな女の子から告白されたり・・・．";
        talkContent[9][5] = "MOP";


        talkContent[10] = new String[2];
        talkChara[10] = graphic.makeImageContext(graphic.searchBitmap("syujinko7r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[10][0] = "俺は宝くじも買わなければ，好きな女の子もいない．";
        talkContent[10][1] = "MOP";


        talkContent[11] = new String[2];
        talkChara[11] = graphic.makeImageContext(graphic.searchBitmap("gaia20l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[11][0] = "なによ，夢も何もないのね．悲しい子．";
        talkContent[11][1] = "MOP";


        talkContent[12] = new String[2];
        talkChara[12] = graphic.makeImageContext(graphic.searchBitmap("gaia8l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[12][0] = "平和ねぇ・・・．最近はそんなことも言ってられないのよね．";
        talkContent[12][1] = "MOP";


        talkContent[13] = new String[2];
        talkChara[13] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[13][0] = "ん？今日もこんなにのどかなのに?";
        talkContent[13][1] = "MOP";


        talkContent[14] = new String[2];
        talkChara[14] = graphic.makeImageContext(graphic.searchBitmap("gaia11l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[14][0] = "さっき，あなたもあったでしょ?魔王よ，魔王．";
        talkContent[14][1] = "MOP";


        talkContent[15] = new String[2];
        talkChara[15] = graphic.makeImageContext(graphic.searchBitmap("syujinko8r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[15][0] = "・・・．";
        talkContent[15][1] = "MOP";


        talkContent[16] = new String[4];
        talkChara[16] = graphic.makeImageContext(graphic.searchBitmap("gaia24l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[16][0] = "今まで頑張って抑えてきたけど，もう限界が来ちゃったのよね．";
        talkContent[16][1] = "\n";
        talkContent[16][2] = "それがさっきの魔王よ．";
        talkContent[16][3] = "MOP";


        talkContent[17] = new String[2];
        talkChara[17] = graphic.makeImageContext(graphic.searchBitmap("syujinko6r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[17][0] = "ふーん，なんか大変だね．";
        talkContent[17][1] = "MOP";


        talkContent[18] = new String[4];
        talkChara[18] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[18][0] = "そうなの，すごく大変．";
        talkContent[18][1] = "\n";
        talkContent[18][2] = "どこかに，ジオの加護を受けた屈強な戦士でもいないかなって思っていたのよ．";
        talkContent[18][3] = "MOP";


        talkContent[19] = new String[4];
        talkChara[19] = graphic.makeImageContext(graphic.searchBitmap("syujinko6r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[19][0] = "そっかぁ，見つかるといいね．";
        talkContent[19][1] = "\n";
        talkContent[19][2] = "それじゃあ，俺は忙しいからこれで．";
        talkContent[19][3] = "MOP";


        talkContent[20] = new String[4];
        talkChara[20] = graphic.makeImageContext(graphic.searchBitmap("gaia20l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[20][0] = "あらそう？";
        talkContent[20][1] = "\n";
        talkContent[20][2] = "残念だけど，また会いましょうね．";
        talkContent[20][3] = "MOP";


        talkContent[21] = new String[2];
        talkChara[21] = graphic.makeImageContext(graphic.searchBitmap("syujinko6r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[21][0] = "おう，頑張れよ，見つかるといいな．";
        talkContent[21][1] = "MOP";


        talkContent[22] = new String[2];
        talkChara[22] = graphic.makeImageContext(graphic.searchBitmap("gaia12l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[22][0] = "って！待ちなさいよ！";
        talkContent[22][1] = "MOP";


        talkContent[23] = new String[4];
        talkChara[23] = graphic.makeImageContext(graphic.searchBitmap("syujinko5r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[23][0] = "おお，どうした？";
        talkContent[23][1] = "\n";
        talkContent[23][2] = "なにかあったか？";
        talkContent[23][3] = "MOP";

        talkContent[24] = new String[6];
        talkChara[24] = graphic.makeImageContext(graphic.searchBitmap("gaia12l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[24][0] = "なにが「なにかあったか？」よ！";
        talkContent[24][1] = "\n";
        talkContent[24][2] = "おおありよ！";
        talkContent[24][3] = "\n";
        talkContent[24][4] = "さっき言ったじゃない「ジオの加護を受けた屈きょ";
        talkContent[24][5] = "MOP";


        talkContent[25] = new String[2];
        talkChara[25] = graphic.makeImageContext(graphic.searchBitmap("syujinko6r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[25][0] = "あー，今日は疲れたな，もう帰ろう．";
        talkContent[25][1] = "MOP";


        talkContent[26] = new String[6];
        talkChara[26] = graphic.makeImageContext(graphic.searchBitmap("gaia16l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[26][0] = "まってまって，帰らないで！";
        talkContent[26][1] = "\n";
        talkContent[26][2] = "このままだと，大変なことになっちゃうの！";
        talkContent[26][3] = "\n";
        talkContent[26][4] = "おねがいよ・・・，もう私ひとりじゃ無理なの・・・．";
        talkContent[26][5] = "MOP";


        talkContent[27] = new String[4];
        talkChara[27] = graphic.makeImageContext(graphic.searchBitmap("syujinko8r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[27][0] = "そんなこと言ったって・・・．さっきの見ただろ？";
        talkContent[27][1] = "\n";
        talkContent[27][2] = "あんな奴と戦ったら，今度こそ死んじまう・・・．";
        talkContent[27][3] = "MOP";

        talkContent[28] = new String[4];
        talkChara[28] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[28][0] = "あー，それね，それは大丈夫よ．";
        talkContent[28][1] = "\n";
        talkContent[28][2] = "第一，あなたに屈強さなんて期待してないし．";
        talkContent[28][3] = "MOP";


        talkContent[29] = new String[4];
        talkChara[29] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[29][0] = "えっ，大丈夫なの！？";
        talkContent[29][1] = "\n";
        talkContent[29][2] = "てか，期待してなかったのかい！";
        talkContent[29][3] = "MOP";


        talkContent[30] = new String[2];
        talkChara[30] = graphic.makeImageContext(graphic.searchBitmap("gaia14l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[30][0] = "だって，あなた見るからにしょぼそうじゃない．";
        talkContent[30][1] = "MOP";

        talkContent[31] = new String[2];
        talkChara[31] = graphic.makeImageContext(graphic.searchBitmap("syujinko7r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[31][0] = "がーん・・・．";
        talkContent[31][1] = "MOP";


        talkContent[31] = new String[4];
        talkChara[31] = graphic.makeImageContext(graphic.searchBitmap("gaia15l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[31][0] = "まぁ，そんなしょぼいあなたでも大丈夫よ．";
        talkContent[31][1] = "\n";
        talkContent[31][2] = "なんたってジオの加護を受けてるんだから．";
        talkContent[31][3] = "MOP";


        talkContent[32] = new String[2];
        talkChara[32] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[32][0] = "そのさっきから言ってる「ジオの加護」ってなんだ？";
        talkContent[32][1] = "MOP";


        talkContent[33] = new String[6];
        talkChara[33] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[33][0] = "ああ、説明してなかったわね．";
        talkContent[33][1] = "\n";
        talkContent[33][2] = "あなたはこの世界の地脈を流れるジオエネルギーの加護を受けてるの．";
        talkContent[33][3] = "\n";
        talkContent[33][4] = "だから，常人の何倍もの力を出せるのよ．";
        talkContent[33][5] = "MOP";

        talkContent[34] = new String[6];
        talkChara[34] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[34][0] = "え，俺そうだったの！？";
        talkContent[34][1] = "\n";
        talkContent[34][2] = "ん・・・？";
        talkContent[34][3] = "\n";
        talkContent[34][4] = "でもいままで，そんなことなかったけど・・・？";
        talkContent[34][5] = "MOP";

        talkContent[35] = new String[4];
        talkChara[35] = graphic.makeImageContext(graphic.searchBitmap("gaia13l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[35][0] = "そりゃそうよ．";
        talkContent[35][1] = "\n";
        talkContent[35][2] = "あなたジオスロットにジオオブジェクトをささげてないから．";
        talkContent[35][3] = "MOP";

        talkContent[36] = new String[6];
        talkChara[36] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[36][0] = "ジオスロット？";
        talkContent[36][1] = "\n";
        talkContent[36][2] = "ジオオブジェクト？";
        talkContent[36][3] = "\n";
        talkContent[36][4] = "何それ？";
        talkContent[36][5] = "MOP";

        talkContent[37] = new String[6];
        talkChara[37] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[37][0] = "画面の右上に「MAP」って書いてあるでしょ？";
        talkContent[37][1] = "\n";
        talkContent[37][2] = "そこからジオマップに行けるの．";
        talkContent[37][3] = "\n";
        talkContent[37][4] = "そこで手に入れたジオオブジェクトをスロットに入れれば晴れて屈強な戦士よ．";
        talkContent[37][5] = "MOP";

        talkContent[38] = new String[2];
        talkChara[38] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[38][0] = "MAP・・・?";
        talkContent[38][1] = "MOP";

        talkContent[39] = new String[6];
        talkChara[39] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[39][0] = "まぁ，細かいことはいいの．";
        talkContent[39][1] = "\n";
        talkContent[39][2] = "多分伝わる人には伝わったし．";
        talkContent[39][3] = "\n";
        talkContent[39][4] = "何も問題はないわ．";
        talkContent[39][5] = "MOP";

        talkContent[40] = new String[6];
        talkChara[40] = graphic.makeImageContext(graphic.searchBitmap("syujinko8r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[40][0] = "そうか，大丈夫ならいいけど．";
        talkContent[40][1] = "\n";
        talkContent[40][2] = "というか，それにもっと早く気づいてれば・・・．";
        talkContent[40][3] = "\n";
        talkContent[40][4] = "もっとモテてたかもしれないのに・・・．";
        talkContent[40][5] = "MOP";

        talkContent[41] = new String[4];
        talkChara[41] = graphic.makeImageContext(graphic.searchBitmap("gaia14l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[41][0] = "・・・．";
        talkContent[41][1] = "\n";
        talkContent[41][2] = "女の子に興味がないなんて，やっぱ嘘だったのね．";
        talkContent[41][3] = "MOP";

        talkContent[42] = new String[4];
        talkChara[42] = graphic.makeImageContext(graphic.searchBitmap("syujinko4r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[42][0] = "な，なんだよ．";
        talkContent[42][1] = "\n";
        talkContent[42][2] = "悪いか！";
        talkContent[42][3] = "MOP";

        talkContent[43] = new String[2];
        talkChara[43] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[43][0] = "いやー，別に悪くなんてないけど，嘘はいけないわね．";
        talkContent[43][1] = "MOP";

        talkContent[44] = new String[2];
        talkChara[44] = graphic.makeImageContext(graphic.searchBitmap("syujinko7r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[44][0] = "そ，それは，悪かったな・・・．";
        talkContent[44][1] = "MOP";

        talkContent[45] = new String[6];
        talkChara[45] = graphic.makeImageContext(graphic.searchBitmap("gaia8l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[45][0] = "まぁ，そんなことはどうでもいいのよ．";
        talkContent[45][1] = "\n";
        talkContent[45][2] = "そんなことより，ほかに問題があったわ．";
        talkContent[45][3] = "\n";
        talkContent[45][4] = "最近，でっかいモンスターがジオエネルギーの流れをさえぎっちゃたのよね．";
        talkContent[45][5] = "MOP";

        talkContent[46] = new String[2];
        talkChara[46] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[46][0] = "ん，それは困ることなのか？";
        talkContent[46][1] = "MOP";

        talkContent[47] = new String[4];
        talkChara[47] = graphic.makeImageContext(graphic.searchBitmap("gaia8l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[47][0] = "そのモンスターのせいで，エネルギーの流れがさえぎられちゃってるの．";
        talkContent[47][1] = "\n";
        talkContent[47][2] = "多分ジオオブジェクトを献上してもあなたは，加護を受けることができないかも・・・．";
        talkContent[47][3] = "MOP";

        talkContent[48] = new String[4];
        talkChara[48] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[48][0] = "そうなのか？";
        talkContent[48][1] = "\n";
        talkContent[48][2] = "じゃあ，どうすれば？";
        talkContent[48][3] = "MOP";

        talkContent[49] = new String[2];
        talkChara[49] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[49][0] = "そうね，まずは手始めにそのモンスターを倒してもらおうかしら．";
        talkContent[49][1] = "MOP";

        talkContent[50] = new String[6];
        talkChara[50] = graphic.makeImageContext(graphic.searchBitmap("syujinko7r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[50][0] = "え！？";
        talkContent[50][1] = "\n";
        talkContent[50][2] = "モンスター倒すの！？";
        talkContent[50][3] = "\n";
        talkContent[50][4] = "自信ないぞ・・・．";
        talkContent[50][5] = "MOP";

        talkContent[51] = new String[6];
        talkChara[51] = graphic.makeImageContext(graphic.searchBitmap("gaia9l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[51][0] = "大丈夫よ，私がついてるし，問題ないわ．";
        talkContent[51][1] = "\n";
        talkContent[51][2] = "私の加護を受けると，倒したモンスターの強さを吸収できるの．";
        talkContent[51][3] = "\n";
        talkContent[51][4] = "そこら辺のモンスターを倒してれば，どんどん強くなれるわ．";
        talkContent[51][5] = "MOP";

        talkContent[52] = new String[2];
        talkChara[52] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[52][0] = "お前すごいな・・・．";
        talkContent[52][1] = "MOP";

        talkContent[53] = new String[6];
        talkChara[53] = graphic.makeImageContext(graphic.searchBitmap("gaia8l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[53][0] = "まぁ，女神だしこれくらいは当然よ．";
        talkContent[53][1] = "\n";
        talkContent[53][2] = "でも，魔王は注意してね，あれはそこら辺のモンスターとは違うから．";
        talkContent[53][3] = "\n";
        talkContent[53][4] = "多分ジオオブジェクトの加護がなければまた瞬殺されるわ．";
        talkContent[53][5] = "MOP";

        talkContent[54] = new String[2];
        talkChara[54] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[54][0] = "こっわ・・・．";
        talkContent[54][1] = "MOP";

        talkContent[55] = new String[2];
        talkChara[55] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[55][0] = "それじゃあ，そうと決まったらダンジョンに行きましょ．";
        talkContent[55][1] = "MOP";

        talkContent[56] = new String[4];
        talkChara[56] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[56][0] = "おう，そうしよう．";
        talkContent[56][1] = "\n";
        talkContent[56][2] = "でも，どうやって行くんだ？";
        talkContent[56][3] = "MOP";

        talkContent[57] = new String[6];
        talkChara[57] = graphic.makeImageContext(graphic.searchBitmap("gaia18l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[57][0] = "なに，あなたそんなことも知らないの？";
        talkContent[57][1] = "\n";
        talkContent[57][2] = "あなたの頭にちょうどくっついている";
        talkContent[57][3] = "\n";
        talkContent[57][4] = "それをタッチすればいいのよ．";
        talkContent[57][5] = "MOP";

        talkContent[58] = new String[2];
        talkChara[58] = graphic.makeImageContext(graphic.searchBitmap("syujinko3r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[58][0] = "アイコン？タッチ？";
        talkContent[58][1] = "MOP";

        talkContent[59] = new String[2];
        talkChara[59] = graphic.makeImageContext(graphic.searchBitmap("gaia22l"), 1300, 450, 2.7f, 2.7f, 0, 255, false);
        talkContent[59][0] = "いいの，わかる人にはわかったはずだから．";
        talkContent[59][1] = "MOP";

        talkContent[60] = new String[2];
        talkChara[60] = graphic.makeImageContext(graphic.searchBitmap("syujinko1r"), 300, 450, 3.0f, 3.0f, 0, 255, false);
        talkContent[60][0] = "そうか，大丈夫ならいいや．";
        talkContent[60][1] = "MOP";
        
    }

    public void openningUpdate() {

        graphic.bookingDrawBitmapData(backGround, 0, 0, 1, 1, 0, 255, true);

        dungeonSelectManager.draw();

        if(talkContent[count] != null) {
            drawCharaAndTouchCheck(talkChara[count]);
        }else{
            worldActivity.worldSurfaceView.setOpenningFlag(false);
            worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT_INIT);//startではない。音楽を再び鳴らさないため
        }

        if (text_mode == false) {
            if(talkContent[count] != null) {
                talk(talkContent[count]);
            }
        }

        text_box_admin.update();
        text_box_admin.draw();
        //musicAdmin.update();

        System.out.println(world_user_interface.getTouchState());

    }

    public void openningDraw() {

        graphic.draw();
    }
    public void talk(String[] talkContent) {

        for(int i = 0; i < talkContent.length; i++){
            text_box_admin.bookingDrawText(openningTextBoxID,talkContent[i], paint);
        }
        text_box_admin.updateText(openningTextBoxID);
        text_box_admin.setTextBoxExists(openningTextBoxID, true);
        text_mode = true;
    }

    public void drawCharaAndTouchCheck(ImageContext _imageContext){

        graphic.bookingDrawBitmapData(_imageContext);
        if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP) {
            text_box_admin.setTextBoxExists(openningTextBoxID, false);
            text_mode = false;
            count++;
        }
    }
    */
}








/*
memo作業内容

WorldGameSystemの中身を整理した
いなに画面切り替えオーダー
いなにボタンオーダー(ListBoxがButtonの配列をもち、それぞれの位置が決められる。それが画像だったりテキストだったりできて、
ListBox<T extends Button>としてButtonを継承したButtonを作れるようにする)
Shop,Present,GeoSlotあたりはいなのItemBag待ち
いなのボタンができたらメニューを並べる
いなの画面遷移ができたらメニューから遷移できるようにし、GeoWorldMapからGeoSlotMapに遷移できるようにする。
GeoSlotMapのここに置けないとか、一定の条件を満たした場合に解放されるやつ。
いなのセーブ機能

 */


