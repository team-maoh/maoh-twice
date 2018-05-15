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
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.itemdata.MiningItemDataAdmin;
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

    int repeat_count;

    public void init(DungeonUserInterface _dungeon_user_interface, Graphic _graphic, SoundAdmin sound_admin, MyDatabaseAdmin _myDatabaseAdmin, BattleUserInterface _battle_user_interface, Activity dungeon_activity, MyDatabaseAdmin my_database_admin, ActivityChange _activityChange, int _repeat_count, Constants.DungeonKind.DUNGEON_KIND dungeon_kind) {
        dungeon_user_interface = _dungeon_user_interface;
        battle_user_interface = _battle_user_interface;
        dungeonActivity = dungeon_activity;
        graphic = _graphic;

        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin(graphic);
        list_box_admin = new ListBoxAdmin();
        text_box_admin.init(dungeon_user_interface);
        list_box_admin.init(dungeon_user_interface, graphic);

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
                dungeon_num = 0;
                break;
            case DRAGON:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "DragonMonsterData");
                dungeon_num = 1;
                break;
            case FOREST:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "ForestMonsterData");
                dungeon_num = 2;
                break;
            case HAUNTED:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "HauntedMonsterData");
                dungeon_num = 3;
                break;
                /*
            case SEA:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "SeaMonsterData");
                dungeon_num = 4;
                break;
            case SWAMP:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "SwampMonsterData");
                dungeon_num = 5;
                break;
            case LAVA:
                dungeonMonsterDataAdmin = new DungeonMonsterDataAdmin(my_database_admin, "LavaMonsterData");
                dungeon_num = 6;
                break;
                 */
            case MAOH:
                dungeon_num = 0;
                break;
            case OPENING:
                dungeon_num = 0;
                break;
            default:
                dungeon_num = 0;
        }



        battleUnitDataAdmin = new BattleUnitDataAdmin(_myDatabaseAdmin, graphic); // TODO : 一度読み出せばいいので、GlobalData管理が良いかもしれない
        battleUnitDataAdmin.loadBattleUnitData(dungeon_kind);//敵読み込み



        GlobalData globalData = (GlobalData) (dungeon_activity.getApplication());
        activityChange = _activityChange;
        dungeonModeManage = new DungeonModeManage();
        map_plate_admin = new MapPlateAdmin(graphic, dungeon_user_interface, activityChange, globalData);
        map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, sound_admin, map_plate_admin, dungeonModeManage, globalData, battle_unit_admin);
        map_inventry_admin = new MapInventryAdmin(globalData, map_plate_admin.getInventry(), map_object_admin, map_plate_admin);

        dungeon_data_admin = new DungeonDataAdmin(_myDatabaseAdmin);

        map_status = new MapStatus(4);
        map_status_saver = new MapStatusSaver(_myDatabaseAdmin, "MapSaveData", "MapSaveData.db", 1, "s", map_status, 4);
        map_status_saver.load();

//        for(int i = 0;i < 4;i++){
//            System.out.println("after:stage_num = "+i+", is_clear = "+map_status.getMapStatus(i));
//        }
//      camera = new Camera(map_size, 64*4);


        if (!(dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH)) {
            map_size.set(dungeon_data_admin.getDungeon_data().get(dungeon_num).getMap_size_x(), dungeon_data_admin.getDungeon_data().get(dungeon_num).getMap_size_y());
            map_admin = new MapAdmin(graphic, map_object_admin, dungeon_data_admin.getDungeon_data().get(dungeon_num), dungeonMonsterDataAdmin.getDungeon_monster_data());
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

        //palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipmentInventry, expendInventry);
        palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipment_item_data_admin);
        palette_admin.setMiningItems(miningItemDataAdmin);//TODO コンストラクタに入れて居ないためよくない


        playerStatus = globalData.getPlayerStatus();
        maohMenosStatus = globalData.getMaohMenosStatus();

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
                maohMenosStatus
        );

        backGround = graphic.searchBitmap("firstBackground");


        //by kmhanko即座に魔王との戦闘画面へ
        if (dungeon_kind == Constants.DungeonKind.DUNGEON_KIND.MAOH) {
            dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAOH_INIT);
        }

        //デバッグ用
        //dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING_INIT);

    }

    public void saveMapSaveData() {
        map_status_saver.deleteAll();
        map_status_saver.save();
    }


    public void update() {

        switch (dungeonModeManage.getMode()) {

            case MAP:
                //map_object_admin.update(is_displaying_menu, is_touching_outside_menu);
                //map_plate_admin.update(is_displaying_menu);
                map_object_admin.update();
                map_plate_admin.update();
                break;

            case BUTTLE_INIT:
                //battle_unit_admin.reset(BattleUnitAdmin.MODE.BATTLE);
                //battle_unit_admin.spawnEnemy();
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE);
                backGround = graphic.searchBitmap("firstBackground");

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

            case MAOH:
                battle_user_interface.update();
                battle_unit_admin.update();
                break;

            case GEO_MINING_INIT:
                battle_unit_admin.reset(BattleUnitAdmin.MODE.MINING);
                //battle_unit_admin.spawnRock();　reset内で呼んでいる
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING);
                backGround = graphic.searchBitmap("miningBack");

            case GEO_MINING:
                battle_user_interface.update();
                battle_unit_admin.update();
                break;

            case TO_WORLD:
                activityChange.toWorldActivity();
                break;

            case EQUIP_EXPEND:
                break;

            case GEO_MAP:
                break;

        }

        text_box_admin.update();
    }

    public void draw() {

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

            case EQUIP_EXPEND:
                break;

            case GEO_MAP:
                break;

        }
        text_box_admin.draw();
        graphic.draw();
    }

    public void setIsDisplayingMenu(boolean _is_displaying_menu){
        is_displaying_menu = _is_displaying_menu;
    }

    public void setIsTouchingOutsideMenu(boolean _is_touching_outside_menu){
        is_touching_outside_menu = _is_touching_outside_menu;
    }



    //オープニング関係
    int count = 0;
    int openningTextBoxID;
    boolean text_mode = false;
    boolean boss_is_running = false;

    public void openningInit() {

        openningTextBoxID = text_box_admin.createTextBox(200, 500, 1400, 800, 3);
        text_box_admin.setTextBoxUpdateTextByTouching(openningTextBoxID, false);
        text_box_admin.setTextBoxExists(openningTextBoxID, false);
    }

    public void openningUpdate() {


        if(count == 1){
            map_object_admin.putPlayer();
        }

        if (count == 100) {
            text_box_admin.bookingDrawText(openningTextBoxID, "今日も平穏だなぁ");
            text_box_admin.bookingDrawText(openningTextBoxID, "MOP");
            text_box_admin.updateText(openningTextBoxID);
            text_box_admin.setTextBoxExists(openningTextBoxID, true);
            text_mode = true;
        }

        if (count == 100 && (battle_user_interface.getTouchState() == Constants.Touch.TouchState.DOWN || battle_user_interface.getTouchState() == Constants.Touch.TouchState.DOWN_MOVE || battle_user_interface.getTouchState() == Constants.Touch.TouchState.MOVE)) {
            text_box_admin.setTextBoxExists(openningTextBoxID, false);
            text_mode = false;

        }

        if (count == 180) {
            map_object_admin.putBoss();
            boss_is_running = true;
        }


        //ここから先フジワラ，敵と衝突し，戦闘を行い，倒されるということを実現する．
        //主に，プレイヤーが右に進み続ける，と，敵をちゃんとプレイヤーにぶつけて戦闘に入る，というところをちゃんと実装する．
        //ぶつかる直前に「うわ，なんだ!?」を入れる．

        //count = 180 でboss_is_running = true としたとすると、
        //うわ、なんだ、で画面を止めるのは count = 191
        if (map_object_admin.bossIsHitPlayer()) {
            boss_is_running = false;
            text_box_admin.bookingDrawText(openningTextBoxID, "うわ，なんだ!?");
            text_box_admin.bookingDrawText(openningTextBoxID, "MOP");
            text_box_admin.updateText(openningTextBoxID);
            text_box_admin.setTextBoxExists(openningTextBoxID, true);
            text_mode = true;

//            System.out.println("count_desudesudesu"+count);
        }

        if (count == 191 && (battle_user_interface.getTouchState() == Constants.Touch.TouchState.DOWN || battle_user_interface.getTouchState() == Constants.Touch.TouchState.DOWN_MOVE || battle_user_interface.getTouchState() == Constants.Touch.TouchState.MOVE)) {

            //ボスとの戦闘
            System.out.println("茶番：ボスとの戦闘");

            //by kmhanko

            battle_unit_admin.reset(BattleUnitAdmin.MODE.OPENING);
            battle_unit_admin.spawnEnemy(
                    new String[] {
                            "m014"
                    }
            );
            ((DungeonActivity)dungeonActivity).dungeon_surface_view.setOpeningFlag(false);////////
            dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT);


        }


        if (text_mode == false) {
            map_object_admin.openingUpdate(boss_is_running);
            map_plate_admin.update();
            count++;
        }

        text_box_admin.update();


    }

    public void openningDraw() {

        map_admin.drawMap_for_autotile_light_animation();
        map_object_admin.draw();
        map_plate_admin.draw();

        text_box_admin.draw();
        graphic.draw();

    }


    public DungeonGameSystem() {
    }


}

