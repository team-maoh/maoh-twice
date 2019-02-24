package com.maohx2.fuusya;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.DungeonMonsterDataAdmin;
import com.maohx2.horie.map.MapAdmin;
//import com.maohx2.ina.ActivityChange;
import com.maohx2.ina.Activity.UnitedActivity;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Battle.BattleUnitDataAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GameSystem.DungeonGameSystem;
import com.maohx2.ina.GameSystem.DungeonModeManage;
import com.maohx2.horie.map.DungeonData;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.WindowPlate.WindowTextPlate;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.plate.BoxImageTextPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.ImageAdmin;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.pow;
import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.subtractExact;

/**
 * Created by ina on 2017/09/05.
 */

public class MapObjectAdmin {

    //int NUM_OF_ENEMY = 10;
    int NUM_OF_ENEMY = 64;
    //int NUM_OF_TRAP = 64;//最大値
    int NUM_OF_TRAP = 0;//最大値
    int NUM_OF_MINE = 128;
    int NUM_OF_BOSS = 1;
    int NUM_OF_ITEM = 64;//最大値

    int PLAYER_DIR = 8;
    int ENEMY_DIR = 8;
    int BOSS_DIR = 8;

    int magnification;

    MapPlayer map_player;
    MapObjectBitmap map_player_bitmap;
    double player_x, player_y;
    Random random;

    MapItem[] map_item = new MapItem[NUM_OF_ITEM];
    MapObjectBitmap[] map_item_bitmap = new MapObjectBitmap[NUM_OF_ITEM];
    MapTrap[] map_trap = new MapTrap[NUM_OF_TRAP];
    MapObjectBitmap[] map_trap_bitmap = new MapObjectBitmap[NUM_OF_TRAP];
    MapMine[] map_mine = new MapMine[NUM_OF_MINE];
    MapObjectBitmap[] map_mine_bitmap = new MapObjectBitmap[NUM_OF_MINE];

    MapBoss[] map_boss = new MapBoss[NUM_OF_BOSS];
    MapObjectBitmap[] map_boss_bitmap = new MapObjectBitmap[NUM_OF_BOSS];

    MapEnemy[] map_enemy = new MapEnemy[NUM_OF_ENEMY];
    MapObjectBitmap[] map_enemy_bitmap = new MapObjectBitmap[NUM_OF_ENEMY];

    double enemy_distance;

    BagItemAdmin bag_item_admin;
    SoundAdmin sound_admin;
    DungeonUserInterface dungeon_user_interface;
    MapPlateAdmin map_plate_admin;
    MapAdmin map_admin;
    DungeonModeManage dungeon_mode_manage;
    GlobalData globalData;
    PlayerStatus playerStatus;
    BattleUnitAdmin battle_unit_admin;

    DungeonData dungeonData;

    Graphic graphic;
    Camera camera;

    int textbox_id;
    TextBoxAdmin text_box_admin;
    Paint paint;

    int playerAlpha = 255;

    public void setPlayerAlpha(int _playerAlpha) {
        playerAlpha = _playerAlpha;
    }
    public int getPlayerAlpha() {
        return playerAlpha;
    }

    boolean is_displaying_menu;

    //リリース時にはfalse
    //trueにすると敵との戦闘を回避できる(デバッグ用)
    boolean avoid_battle_for_debug = false;

    MapInventryAdmin map_inventry_admin;
    //ActivityChange activityChange;
    int kind_of_zako;

    int repeatCount;

    EffectAdmin effectAdmin;
    EffectAdmin backEffectAdmin;

    Constants.DungeonKind.DUNGEON_KIND dungeonKind;

    UnitedActivity unitedActivity;

    boolean initUIsFlag;
    PaletteAdmin palette_admin;

    public MapObjectAdmin(Graphic _graphic, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, MapPlateAdmin _map_plate_admin, DungeonModeManage _dungeon_mode_manage, UnitedActivity _unitedActivity, BattleUnitAdmin _battle_unit_admin, TextBoxAdmin _text_box_admin, BattleUnitDataAdmin _battleUnitDataAdmin, DungeonMonsterDataAdmin _dungeonMonsterDataAdmin, int _repeatCount, Constants.DungeonKind.DUNGEON_KIND _dungeonKind, DungeonData _dungeonData, EffectAdmin _effectAdmin, EffectAdmin _backEffectAdmin, PaletteAdmin _paletteAdmin) {
        graphic = _graphic;
        dungeon_user_interface = _dungeon_user_interface;
        sound_admin = _sound_admin;
        map_plate_admin = _map_plate_admin;
        dungeon_mode_manage = _dungeon_mode_manage;
        unitedActivity = _unitedActivity;
        globalData = (GlobalData)unitedActivity.getApplication();
        playerStatus = globalData.getPlayerStatus();
        battle_unit_admin = _battle_unit_admin;
        text_box_admin = _text_box_admin;
        repeatCount = _repeatCount;
        dungeonKind = _dungeonKind;
        dungeonData = _dungeonData;

        effectAdmin = _effectAdmin;
        backEffectAdmin = _backEffectAdmin;

        palette_admin = _paletteAdmin;

        MapItem.setBackEffectAdmin(backEffectAdmin);
        MapItem.setEffectAdmin(effectAdmin);

        is_displaying_menu = false;
        random = new Random();

        map_player = new MapPlayer(graphic, this, dungeon_user_interface, _sound_admin, camera, map_plate_admin, battle_unit_admin, dungeon_mode_manage, avoid_battle_for_debug);
        map_player.init();
        map_player_bitmap = new MapObjectBitmap(PLAYER_DIR, graphic, "主人公");
        map_player_bitmap.init(3 / 2);

        player_x = map_player.getWorldX();
        player_y = map_player.getWorldY();

        for (int i = 0; i < NUM_OF_TRAP; i++) {

            String name_of_trap = randomTrapName();

            map_trap[i] = new MapTrap(graphic, this, i % 4, camera, false, name_of_trap, sound_admin);
//            map_trap[i] = new MapTrap(graphic, this, i % 4, camera, false, "being_teleported");
            map_trap[i].init();

//            map_trap_bitmap[i] = new MapObjectBitmap(1, graphic, "cave_thing_01");
            map_trap_bitmap[i] = new MapObjectBitmap(1, graphic, name_of_trap);
            map_trap_bitmap[i].init();
        }

        for (int i = 0; i < NUM_OF_MINE; i++) {
            map_mine[i] = new MapMine(graphic, this, i % 4, camera, dungeon_mode_manage);
            map_mine[i].init();

            map_mine_bitmap[i] = new MapObjectBitmap(8, graphic, "発掘ポイント黄");
            map_mine_bitmap[i].init();

        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {

            map_enemy[i] = new MapEnemy(graphic, this, camera, ENEMY_DIR, true, true, battle_unit_admin, dungeon_mode_manage, avoid_battle_for_debug, sound_admin);
            map_enemy[i].init();
            map_enemy_bitmap[i] = new MapObjectBitmap(ENEMY_DIR, graphic, "ジオイーター");
            map_enemy_bitmap[i].init(3 / 2);
        }

        //by kmhanko
        MapItem.setRepeatCount(repeatCount);
        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i] = new MapItem(graphic, this, i, camera, playerStatus, _battleUnitDataAdmin, _dungeonMonsterDataAdmin);
            map_item[i].init();
            map_item_bitmap[i] = new MapObjectBitmap(1, graphic, randomItemName());
            map_item_bitmap[i].init();
        }

        for (int i = 0; i < NUM_OF_BOSS; i++) {
            map_boss[i] = new MapBoss(graphic, this, 0, camera, battle_unit_admin, dungeon_mode_manage);
            map_boss[i].init();
            map_boss_bitmap[i] = new MapObjectBitmap(BOSS_DIR, graphic, "ボス");
            map_boss_bitmap[i].init();
        }

//        item_distance = 0;
        enemy_distance = 0;

        bag_item_admin = new BagItemAdmin();
        bag_item_admin.init();

        //activityChange = map_plate_admin.getActivityChange();

        textbox_id = text_box_admin.createTextBox(100, 700, 1450, 880, 4);
        text_box_admin.setTextBoxUpdateTextByTouching(textbox_id, false);
        text_box_admin.setTextBoxExists(textbox_id, false);

        paint = new Paint();
        paint.setTextSize(40);
        paint.setARGB(255, 255, 255, 255);

        kind_of_zako = 1;
    }

    public void init() {
    }

    public void openingUpdate(boolean boss_is_running) {

        map_player.openingUpdate();
        map_player_bitmap.update();

        if (boss_is_running) {

            map_boss[0].openingUpdate();
            map_boss_bitmap[0].update();

        }
    }

    public void update() {

        //チュートリアル中と選択肢出現中は時が停止する
        //if (playerStatus.getTutorialInDungeon() == 1 && !dungeonEscapeSelectButtonGroup.getDrawFlag() && map_plate_admin.getDisplayingContent() != 1) {
        //if (playerStatus.getTutorialInDungeon() == 1 && !map_plate_admin.getDungeonSelectWindowAdmin().isActive() && map_plate_admin.getDisplayingContent() == -1) {

        if(map_boss[0].exists() == true){
            for (int i = 0; i < NUM_OF_ENEMY; i++) {
                map_enemy[i].setExists(false);
            }
        }

        if(map_plate_admin.getDungeonSelectWindowAdmin().isActive() == true){map_player.restMove();}

        map_player.nonactiveMenuCheck();

        if (playerStatus.getTutorialInDungeon() == 1 && (map_plate_admin.getDisplayingContent() == -1 && !map_plate_admin.getDungeonSelectWindowAdmin().isActive())) {

            map_player.update();
            map_player_bitmap.update();
            player_x = map_player.getWorldX();
            player_y = map_player.getWorldY();
//        System.out.println("player_x_desu" + player_x);
//        System.out.println("player_y_desu" + player_y);

//        for (int i = 0; i < NUM_OF_ITEM; i++) {
//            map_item[i].update();
//            map_item_bitmap[i].update();
//        }
            for (int i = 0; i < NUM_OF_TRAP; i++) {
                map_trap[i].update();
                map_trap_bitmap[i].update();
            }
            for (int i = 0; i < NUM_OF_MINE; i++) {
                map_mine[i].update();
                map_mine_bitmap[i].update();
            }
            for (int i = 0; i < NUM_OF_ENEMY; i++) {
                map_enemy[i].update();
                map_enemy_bitmap[i].update();
            }
            for (int i = 0; i < NUM_OF_ITEM; i++) {//by kmhanko
                map_item[i].update();
                map_item_bitmap[i].update();
            }
            for (int i = 0; i < NUM_OF_BOSS; i++) {
                map_boss[i].update();
                map_boss_bitmap[i].update();
            }
        }
    }

    public void draw() {

//        for (int i = 0; i < NUM_OF_ITEM; i++) {
//            if (map_item[i].exists() == true) {
//                map_item_bitmap[i].draw(map_item[i].getDirOnMap(), map_item[i].getNormX(), map_item[i].getNormY());
//            }
//        }
        for (int i = 0; i < NUM_OF_TRAP; i++) {
            if (map_trap[i].exists() == true && map_trap[i].isVisible() == true) {
                map_trap_bitmap[i].draw(map_trap[i].getDirOnMap(), map_trap[i].getNormX(), map_trap[i].getNormY(),false);
            }
        }
        for (int i = 0; i < NUM_OF_ITEM; i++) {
            if (map_item[i].exists()) {
                map_item_bitmap[i].draw(map_item[i].getDirOnMap(), map_item[i].getNormX(), map_item[i].getNormY(), 192, map_item[i].getExtend(), false);
            }
        }
        for (int i = 0; i < NUM_OF_MINE; i++) {
            if (map_mine[i].exists() == true) {
                map_mine_bitmap[i].draw(map_mine[i].getDirOnMap(), map_mine[i].getNormX(), map_mine[i].getNormY(),false);
            }
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            if (map_enemy[i].exists() == true && playerStatus.getTutorialInDungeon() == 1) {
                map_enemy_bitmap[i].draw(map_enemy[i].getDirOnMap(), map_enemy[i].getNormX(), map_enemy[i].getNormY(),map_enemy[i].getHasFoundPlayer());
            }
        }

        for (int i = 0; i < NUM_OF_BOSS; i++) {
            if (map_boss[i].exists() == true) {
                map_boss_bitmap[i].draw(map_boss[i].getDirOnMap(), map_boss[i].getNormX(), map_boss[i].getNormY(),false);
            }
        }
        if (map_player.exists() == true) {
            map_player_bitmap.draw(map_player.getDirOnMap(), map_player.getNormX(), map_player.getNormY(), playerAlpha,false);
            map_player.draw();
        }
    }

    public MapPlayer getPlayer() {
        return map_player;
    }

    public SoundAdmin getSoundAdmin() {
        return sound_admin;
    }

    public void makeAllEnemiesFindPlayer() {
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].setHasFoundPlayer(true);

        }
    }

    //(x1, y1)と(x2, y2)の距離を返す
    private double myDistance(double x1, double y1, double x2, double y2) {
        return pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5);
    }

    //by kmhanko
    public void battleStart() {
        //dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT);
    }

    public void setMapAdmin(MapAdmin _map_admin) {

        map_admin = _map_admin;

        map_plate_admin.setMapAdmin(map_admin);

        magnification = map_admin.getMagnification();

        map_player.initClass(map_admin);

        for (int i = 0; i < NUM_OF_MINE; i++) {
            map_mine[i].initClass(map_admin);
        }

        for (int i = 0; i < NUM_OF_TRAP; i++) {
            map_trap[i].initClass(map_admin);
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].initClass(map_admin);
        }

        for (int i = 0; i < NUM_OF_BOSS; i++) {
            map_boss[i].initClass(map_admin);
        }

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i].initClass(map_admin);
        }

        //by takano

        //by kmhanko
        String mosnterName[] = map_admin.getMonsterName(0);
        for (int i = 0 ; i < mosnterName.length; i++) {
            if (mosnterName[kind_of_zako] == null) {
                break;
            }
            kind_of_zako++;
        }

    }

    //リスポーン関数
    //Mineの後でEnemyをspawnしないとEnemyをMineの近くで発生させることができない
//    public void spawnMapObject(Point[] mine_point) {
//
//        spawnMine(mine_point);
//
//    }

    public void spawnMapObject(Point[] mine_point) {
        if (dungeonKind == Constants.DungeonKind.DUNGEON_KIND.OPENING || dungeonKind == Constants.DungeonKind.DUNGEON_KIND.MAOH) {
            return;
        }


        //本当はmap_adminで実行してもらう
        //堀江さんと二人がかりでデバッグするのも何なので、今はここに書く
//        String[] debug_enemy_name = new String[NUM_OF_ENEMY];
//        String mid_monster_name[] = map_admin.getMidMonster(1);

        String[] debug_trap_name = new String[NUM_OF_TRAP];
        for (int i = 0; i < NUM_OF_TRAP; i++) {
            debug_trap_name[i] = randomTrapName();
        }
        Point[] debug_mine_point = new Point[NUM_OF_MINE];
        for (int i = 0; i < NUM_OF_MINE; i++) {
            debug_mine_point[i] = new Point(-1, -1);
        }
        String[] debug_item_name = new String[NUM_OF_ITEM];//by kmhanko
        for (int i = 0; i < NUM_OF_ITEM; i++) {
            debug_item_name[i] = randomItemName();
        }

        //
        Point[] debug_boss_point = new Point[NUM_OF_BOSS];

        for (int i = 0; i < NUM_OF_BOSS; i++) {
            Point room_point = new Point(1, 1);
            debug_boss_point[i] = room_point;
            debug_boss_point[i].set((int) (magnification * 7.5), (int) (magnification * 7.5));
        }

        effectAdmin.clearAllEffect();
        backEffectAdmin.clearAllEffect();

        spawnMine(mine_point);
        if(debug_trap_name.length > 0) {spawnTrap(debug_trap_name);}
        spawnBoss(debug_boss_point);
        spawnItem(debug_item_name);
    }

    public void spawnMine(Point[] mine_point) {

        for (int i = 0; i < NUM_OF_MINE; i++) {
            map_mine[i].setExists(false);
        }

        if (map_admin.getNow_floor_num() != map_admin.getBoss_floor_num()) {

            for (int i = 0; i < NUM_OF_MINE; i++) {

                if (mine_point[i].x > 0) {
                    map_mine[i].generatePosition(mine_point[i].x * magnification + magnification / 2, mine_point[i].y * magnification + magnification / 2, magnification);
                    map_mine[i].setExists(true);
                } else {
                    map_mine[i].setPosition(1, 1);
                    map_mine[i].setExists(false);
                }
            }
        }
    }

    public void spawnBoss(Point[] boss_point) {

        for (int i = 0; i < NUM_OF_BOSS; i++) {
            map_boss[i].setExists(false);
        }

//        int a = map_admin.getNow_floor_num();
//        int b = map_admin.getBoss_floor_num();
        if (map_admin.getNow_floor_num() == map_admin.getBoss_floor_num()) {

            for (int i = 0; i < NUM_OF_BOSS; i++) {
                map_boss[i].setPosition(boss_point[i].x, boss_point[i].y);
//            map_boss[i].setPosition(boss_point[i].x, boss_point[i].y);
                String[] tmp_boss = new String[1];

                tmp_boss[0] = map_admin.getMonsterName(2)[0];

//            name[0] = names;
                map_boss[i].setName(tmp_boss[0]);
                map_boss[i].setExists(true);
//            System.out.println("boss_point.x ___ " + boss_point.x);
//            System.out.println("boss_point.y ___ " + boss_point.y);
//                System.out.println("boss_point.x ___ " + boss_point[i].x);
//                System.out.println("boss_point.y ___ " + boss_point[i].y);
            }
        }

    }

    public void spawnEnemy() {


        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].setExists(false);
        }

        if (map_admin.getNow_floor_num() != map_admin.getBoss_floor_num()) {

            String[] tmp_enemy_name = new String[1];
            String[] enemy_string = new String[50];

//            int local_i = 0;

//            map_admin.getMonsterName(1)[local_i];

            enemy_string = map_admin.getMonsterName(1);

            int un_null = 0;
            for (int j = 0 ; j < enemy_string.length; j++) {
                if (enemy_string[j] == null) {
                    break;
                }
                System.out.println("MapObjectAdmin: SpawnEnemy: enemy_string = " + enemy_string[j]);
                un_null++;
            }

            System.out.println("MapObjectAdmin: SpawnEnemy: un_null = " + String.valueOf(un_null));
            if (un_null == 0) {
                System.out.println("MapObjectAdmin: SpawnEnemy: un_null = 0です");
                return;
            }

            for (int i = 0; i < dungeonData.getEnemyNum(); i++) {

//                tmp_enemy_name[0] = map_admin.getMonsterName(1)[local_i];
//                if (i == 2 && map_mine[0].exists() == true) {
//                    map_enemy[i].setPosition((int) map_mine[0].getWorldX(), (int) map_mine[0].getWorldY());
//                } else {
                Point room_point = map_admin.getGoodSpawnRoomPointWithoutPlayer();
                /* by kmhanko
                while (true) {
                    room_point = map_admin.getRoomPoint();
                    if (isGoodSpawnPoint(room_point)) {
                        break;
                    }
                }
                */
                map_enemy[i].setPosition(room_point.x + magnification / 2, room_point.y + magnification / 2);
//                }
                map_enemy[i].setExists(true);

                tmp_enemy_name[0] = enemy_string[i % un_null];
                System.out.println("MapObjectAdmin: SpawnEnemy: " + tmp_enemy_name[0]);
//                tmp_enemy_name[0] = "火山(1)";
                map_enemy[i].setName(tmp_enemy_name[0]);

            }
        }
    }


    public void spawnTrap(String[] names_of_traps) {

        for (int i = 0; i < NUM_OF_TRAP; i++) {
            map_trap[i].setExists(false);
        }

        if (map_admin.getNow_floor_num() != map_admin.getBoss_floor_num()) {
            if (dungeonData == null) {
                return;
            }

            for (int i = 0; i < dungeonData.getTrapNum(); i++) {

                if (names_of_traps[i].equals("null")) {
                    break;
                }

                Point room_point;
                boolean flag = false;
                do {
                    flag = false;
                    room_point = map_admin.getRoomPointNotStairsAndNotGate();
                    for (int j = 0; j < i; j++) {
                        if (map_trap[j].getWorldX() == room_point.x + magnification / 2 && map_trap[j].getWorldY() == room_point.y + magnification / 2) {
                            flag = true;
                            break;
                        }
                    }
                } while(flag);

                map_trap[i].setPosition(room_point.x + magnification / 2, room_point.y + magnification / 2);
                map_trap[i].setExists(true);
                map_trap[i].setName(names_of_traps[i]);
                map_trap[i].setId(i);
                map_trap_bitmap[i].setName(names_of_traps[i]);
            }
        }
    }

    //by kmhanko
    public void spawnItem(String[] names_of_items) {

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i].setExists(false);
            map_item[i].deleteEffect();
        }

        if (map_admin.getNow_floor_num() != map_admin.getBoss_floor_num()) {
            if (dungeonData == null) {
                return;
            }

            for (int i = 0; i < dungeonData.getItemNum(); i++) {

                if (names_of_items[i].equals("null")) {
                    break;
                }


                Point room_point;
                boolean flag = false;
                do {
                    flag = false;
                    room_point = map_admin.getRoomPointNotStairsAndNotGate();
                    for (int j = 0; j < i; j++) {
                        if (map_item[j].getWorldX() == room_point.x + magnification / 2 && map_item[j].getWorldY() == room_point.y + magnification / 2) {
                            flag = true;
                        }
                    }
                } while(flag);

                map_item[i].setPosition(room_point.x + magnification / 2, room_point.y + magnification / 2);
                map_item[i].setExists(true);
                map_item[i].setName(names_of_items[i]);
                map_item[i].setId(i);
                map_item[i].settingParameter();//名前の後
                map_item[i].makeEffect();
                map_item_bitmap[i].setName(names_of_items[i]);
            }
        }
    }

    public void putBoss() {
        map_boss[0].setExists(true);
//        map_boss[0].putBoss(map_player.getWorldX() + 10 + (140 + map_player.getStep()) * 10, map_player.getWorldY());
        setBossBitmap("maoh_walk");
        map_boss[0].putBoss(map_player.getWorldX() + 170 + (150 + 25) * 10, map_player.getWorldY());
        for (int i = 0; i < NUM_OF_ENEMY; i++) { map_enemy[i].setExists(false); }
    }

    public void setBossBitmap(String name) {
        map_boss_bitmap[0] = new MapObjectBitmap(BOSS_DIR, graphic, name);
    }

    public void putPlayer() {
        map_player.setExists(true);
        map_player.putUnit(1200, 650);
    }

    public boolean bossIsHitPlayer(int r) {
        return map_player.isWithinReach(map_boss[0].getWorldX(), map_boss[0].getWorldY(), r);
    }
    private String randomItemName() {
        int kind_of_trap = random.nextInt(3);//luckはでない
        switch (kind_of_trap) {
            case 0:
                return "HpUp";
            case 1:
                return "AtkUp";
            case 2:
                return "DefUp";
            case 3:
                return "LuckUp";
            default:
                return "NONE";
        }
    }

    private String randomTrapName() {
        double random_double = 7 * random.nextDouble();
        int kind_of_trap = (int) (random_double);
        switch (kind_of_trap) {
            case 0:
                return "walking_slowly";
            case 1:
                return "walking_inversely";
            case 2:
                return "cannot_walk";
            case 3:
                return "being_drunk";
            //case 4:
            //    return "being_teleported";
            case 4:
                return "cannot_exit_room";
            case 5:
                return "found_by_enemy";
            default:
                return "being_blown_away";
        }

    }

    public void setMapInventryAdmin(MapInventryAdmin _map_inventry_admin) {
        map_inventry_admin = _map_inventry_admin;
    }

    public void advanceDungeon() {
        map_player.advanceDungeon();
    }

    public void escapeDungeon() {
        sound_admin.play("step00");
        playerStatus.setNowHPMax();
        map_inventry_admin.storageMapInventry();
        globalData.getExpendItemInventry().save();
        //activityChange.toWorldActivity();
        palette_admin.resetDungeonUseNum();
        unitedActivity.getUnitedSurfaceView().toWorldGameMode();
    }

    MapMine nowMapMine;
    public void gotoMiningWindowActivate(MapMine _nowMapMine) {
        nowMapMine = _nowMapMine;
        map_plate_admin.gotoMiningWindowActivate();
    }
    public void gotoMining() {
        nowMapMine.setExists(false);
        map_player.setEncountSteps(0);
        this.eraseEffectBox();
        dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING_INIT);
    }


    public int getMagnification() {
        return magnification;
    }

    public void displayEffectText(String trap_name) {
        String effect_text1 = "罠だ！";
        String effect_text2;
        switch (trap_name) {
            case "walking_slowly":
                effect_text2 = "あなたは移動が遅くなってしまった";
                break;
            case "walking_inversely":
                effect_text2 = "あなたは移動が逆向きになってしまった";
                break;
            case "cannot_walk":
                effect_text2 = "あなたは移動できなくなってしまった";
                break;
            case "being_drunk":
                effect_text2 = "あなたは目が回ってしまった";
                break;
            case "being_teleported":
                effect_text2 = "あなたはワープしてしまった";
                break;
            case "cannot_exit_room":
                effect_text2 = "あなたは部屋から出られなくなった";
                break;
            case "found_by_enemy":
                effect_text2 = "あなたは敵に見つかってしまった";
                break;
            case "being_blown_away":
                effect_text2 = "あなたはふっ飛ばされた";
                break;
            default:
                effect_text2 = "あなたは{default}";
        }
        text_box_admin.bookingDrawText(textbox_id, effect_text1, paint);
        text_box_admin.bookingDrawText(textbox_id, "\n");
        text_box_admin.bookingDrawText(textbox_id, effect_text2, paint);
        text_box_admin.bookingDrawText(textbox_id, "\n");
        text_box_admin.bookingDrawText(textbox_id, "MOP");
        text_box_admin.setTextBoxExists(textbox_id, true);
        text_box_admin.updateText(textbox_id);
    }

    public void eraseEffectBox() {
        text_box_admin.setTextBoxExists(textbox_id, false);
        text_box_admin.resetTextBox(textbox_id);
    }

    public int getKindOfZako() {
        return kind_of_zako;
    }

    //0:壁なし, 1: －, 2: |
    private int detectWall(double x1, double y1, double x2, double y2) {
        return map_admin.detectWallDirection(x1, y1, x2, y2);
    }

    public boolean isGoodSpawnPoint(Point _room_point) {
        return isGoodSpawnPoint(_room_point.x, _room_point.y);
    }

    public boolean isGoodSpawnPoint(int _x, int _y) {
        //by kmhanko
        int spawn_x = _x*magnification + magnification / 2;
        int spawn_y = _y*magnification + magnification / 2;
        boolean wall_check_1 = detectWall(spawn_x, spawn_y, spawn_x + magnification, spawn_y) == 0;
        boolean wall_check_2 = detectWall(spawn_x, spawn_y, spawn_x, spawn_y + magnification) == 0;
        boolean wall_check_3 = detectWall(spawn_x, spawn_y, spawn_x - magnification, spawn_y) == 0;
        boolean wall_check_4 = detectWall(spawn_x, spawn_y, spawn_x, spawn_y - magnification) == 0;

        if (wall_check_1 && wall_check_2 && wall_check_3 && wall_check_4 == false) {
            return false;
        }

        for (int i = 0; i < map_item.length; i++) {
            if (map_item[i] != null) {
                if (map_item[i].exists()) {
                    if (spawn_x == map_item[i].getWorldX() && spawn_y == map_item[i].getWorldY()) {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < map_trap.length; i++) {
            if (map_trap[i] != null) {
                if (map_trap[i].exists()) {
                    if (spawn_x == map_trap[i].getWorldX() && spawn_y == map_trap[i].getWorldY()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public void release() {
        System.out.println("takanoRelease : MapObjectAdmin");
        if (map_player != null) {
            map_player.release();
            map_player = null;
        }

        if ( map_player_bitmap != null ) {
            map_player_bitmap.release();
            map_player_bitmap = null;
        }
        random = null;
        if (map_trap != null) {
            for (int i = 0; i < map_trap.length; i++) {
                if (map_trap[i] != null) {
                    map_trap[i].release();
                }
            }
            map_trap = null;
        }
        if (map_trap_bitmap != null) {
            for (int i = 0; i < map_trap_bitmap.length; i++) {
                if (map_trap_bitmap[i] != null) {
                    map_trap_bitmap[i].release();
                }
            }
            map_trap_bitmap = null;
        }
        if (map_mine != null) {
            for (int i = 0; i < map_mine.length; i++) {
                if (map_mine[i] != null) {
                    map_mine[i].release();
                }
            }
            map_mine = null;
        }

        if (map_mine_bitmap != null) {
            for (int i = 0; i < map_mine_bitmap.length; i++) {
                if (map_mine_bitmap[i] != null) {
                    map_mine_bitmap[i].release();
                }
            }
            map_mine_bitmap = null;
        }
        if (map_item != null) {
            for (int i = 0; i < map_item.length; i++) {
                if (map_item[i] != null) {
                    map_item[i].release();
                }
            }
            map_item = null;
        }

        if (map_item_bitmap != null) {
            for (int i = 0; i < map_item_bitmap.length; i++) {
                if (map_item_bitmap[i] != null) {
                    map_item_bitmap[i].release();
                }
            }
            map_item_bitmap = null;
        }

        if (map_boss != null) {
            for (int i = 0; i < map_boss.length; i++) {
                if (map_boss[i] != null) {
                    map_boss[i].release();
                }
            }
            map_boss = null;
        }

        if (map_boss_bitmap != null) {
            for (int i = 0; i < map_boss_bitmap.length; i++) {
                if (map_boss_bitmap[i] != null) {
                    map_boss_bitmap[i].release();
                }
            }
            map_boss_bitmap = null;
        }
        if (map_enemy != null) {
            for (int i = 0; i < map_enemy.length; i++) {
                if (map_enemy[i] != null) {
                    map_enemy[i].release();
                }
            }
            map_enemy = null;
        }
        if (map_enemy_bitmap != null) {
            for (int i = 0; i < map_enemy_bitmap.length; i++) {
                if (map_enemy_bitmap[i] != null) {
                    map_enemy_bitmap[i].release();
                }
            }
            map_enemy_bitmap = null;
        }
        if ( bag_item_admin != null) {
            bag_item_admin.release();
        }
        bag_item_admin = null;
        paint = null;

    }


}
