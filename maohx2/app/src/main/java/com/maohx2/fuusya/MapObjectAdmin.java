package com.maohx2.fuusya;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonGameSystem;
import com.maohx2.ina.DungeonModeManage;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.ImageAdmin;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.pow;
import static com.maohx2.ina.Constants.Touch.TouchState;

/**
 * Created by ina on 2017/09/05.
 */

public class MapObjectAdmin {

    int NUM_OF_ENEMY = 10;
    //    int NUM_OF_ITEM = 10;// > 2
    int NUM_OF_TRAP = 15;
    int NUM_OF_MINE = 5;
    int NUM_OF_BOSS = 1;

    int PLAYER_DIR = 8;
    int ENEMY_DIR = 8;
    int BOSS_DIR = 8;

    int magnification;

    MapPlayer map_player;
    MapObjectBitmap map_player_bitmap;
    double player_x, player_y;

    //    MapItem[] map_item = new MapItem[NUM_OF_ITEM];
//    MapObjectBitmap[] map_item_bitmap = new MapObjectBitmap[NUM_OF_ITEM];
    MapTrap[] map_trap = new MapTrap[NUM_OF_TRAP];
    MapObjectBitmap[] map_trap_bitmap = new MapObjectBitmap[NUM_OF_TRAP];
    MapMine[] map_mine = new MapMine[NUM_OF_MINE];
    MapObjectBitmap[] map_mine_bitmap = new MapObjectBitmap[NUM_OF_MINE];

    MapBoss map_boss;
    MapObjectBitmap map_boss_bitmap;

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

    Graphic graphic;
    Camera camera;

    boolean is_displaying_menu;


    public MapObjectAdmin(Graphic _graphic, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, MapPlateAdmin _map_plate_admin, DungeonModeManage _dungeon_mode_manage, GlobalData _globalData) {
        graphic = _graphic;
        dungeon_user_interface = _dungeon_user_interface;
        sound_admin = _sound_admin;
        map_plate_admin = _map_plate_admin;
        dungeon_mode_manage = _dungeon_mode_manage;
        globalData = _globalData;
        playerStatus = globalData.getPlayerStatus();

        is_displaying_menu = false;

        map_player = new MapPlayer(graphic, this, dungeon_user_interface, _sound_admin, camera, map_plate_admin);
        map_player.init();
        map_player_bitmap = new MapObjectBitmap(PLAYER_DIR, graphic, "主人公");
        map_player_bitmap.init(3 / 2);

        player_x = map_player.getWorldX();
        player_y = map_player.getWorldY();

        for (int i = 0; i < NUM_OF_TRAP; i++) {

            map_trap[i] = new MapTrap(graphic, this, i % 4, camera, false, "being_teleported");
            map_trap[i].init();

            map_trap_bitmap[i] = new MapObjectBitmap(1, graphic, "cave_thing_01");
            map_trap_bitmap[i].init();
        }

        for (int i = 0; i < NUM_OF_MINE; i++) {
            map_mine[i] = new MapMine(graphic, this, i % 4, camera);
            map_mine[i].init();

            map_mine_bitmap[i] = new MapObjectBitmap(8, graphic, "発掘ポイント黄");
            map_mine_bitmap[i].init();

        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {

            map_enemy[i] = new MapEnemy(graphic, this, camera, ENEMY_DIR, true, true);
            map_enemy[i].init();
            map_enemy_bitmap[i] = new MapObjectBitmap(ENEMY_DIR, graphic, "ジオイーター");
            map_enemy_bitmap[i].init(3 / 2);
        }

        map_boss = new MapBoss(graphic, this, 0, camera);
        map_boss.init();
        map_boss_bitmap = new MapObjectBitmap(BOSS_DIR, graphic, "ボス");
        map_boss_bitmap.init();

//        item_distance = 0;
        enemy_distance = 0;

        bag_item_admin = new BagItemAdmin();
        bag_item_admin.init();

    }

    public void init() {
    }

    public void update() {

        //チュートリアル中は時が停止する
        if (playerStatus.getTutorialInDungeon() == 1) {

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

            map_boss.update();
            map_boss_bitmap.update();
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
                map_trap_bitmap[i].draw(map_trap[i].getDirOnMap(), map_trap[i].getNormX(), map_trap[i].getNormY());
            }
        }
        for (int i = 0; i < NUM_OF_MINE; i++) {
            if (map_mine[i].exists() == true) {
                map_mine_bitmap[i].draw(map_mine[i].getDirOnMap(), map_mine[i].getNormX(), map_mine[i].getNormY());
            }
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            if (map_enemy[i].exists() == true) {
                map_enemy_bitmap[i].draw(map_enemy[i].getDirOnMap(), map_enemy[i].getNormX(), map_enemy[i].getNormY());

            }
        }

        if (map_boss.exists() == true) {
            map_boss_bitmap.draw(map_boss.getDirOnMap(), map_boss.getNormX(), map_boss.getNormY());
        }
        if (map_player.exists() == true) {
            map_player_bitmap.draw(map_player.getDirOnMap(), map_player.getNormX(), map_player.getNormY());
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


    public void getMapAdmin(MapAdmin _map_admin) {

        map_admin = _map_admin;
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

        map_boss.initClass(map_admin);

    }

    //リスポーン関数
    public void spawnMapObject(Point[] mine_point, Point boss_point, String[] names_of_enemys, String[] names_of_traps) {

//        spawnMine(mine_point);
        spawnBoss(boss_point);
        spawnEnemy(names_of_enemys);
        spawnTrap(names_of_traps);

    }

    public void spawnMine(Point[] mine_point) {

        for (int i = 0; i < NUM_OF_MINE; i++) {

            if (mine_point[i].x > 0) {
                map_mine[i].generatePosition(mine_point[i].x * magnification + magnification / 2, mine_point[i].y * magnification + magnification / 2, magnification);
                map_mine[i].setExists(true);
            } else {
                map_mine[i].setPosition(1, 1);
                map_mine[i].setExists(false);
            }

        }

        //本当はmap_adminで実行してもらう
        //堀江さんと二人がかりでデバッグするのも何なので、今はここに書く
        String[] debug_enemy_name = new String[NUM_OF_ENEMY];
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            debug_enemy_name[i] = "中ボス(デバッグ用の仮名)";
        }
        String[] debug_trap_name = new String[NUM_OF_TRAP];
        for (int i = 0; i < NUM_OF_TRAP; i++) {
            debug_trap_name[i] = "being_teleported";
        }
        Point[] debug_mine_point = new Point[NUM_OF_MINE];
        for (int i = 0; i < NUM_OF_MINE; i++) {
            debug_mine_point[i] = new Point(-1, -1);
        }
        //
        spawnMapObject(debug_mine_point, debug_mine_point[0], debug_enemy_name, debug_trap_name);

    }

    private void spawnBoss(Point boss_point) {

        if (boss_point.x > 0) {
            map_boss.setPosition(boss_point.x, boss_point.y);
            map_boss.setExists(true);
        } else {
            map_boss.setPosition(1, 1);
            map_boss.setExists(false);
        }

    }

    private void spawnEnemy(String[] names_of_enemys) {

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].setExists(false);
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {

            if (names_of_enemys[i].equals("null")) {
                break;
            }

            if (i == 2 && map_mine[0].exists() == true) {
                map_enemy[i].setPosition((int) map_mine[0].getWorldX(), (int) map_mine[0].getWorldY());//採掘スポットの近く
            } else {
                Point room_point = map_admin.getRoomPoint();
                map_enemy[i].setPosition(room_point.x + magnification / 2, room_point.y + magnification / 2);
            }
            map_enemy[i].setExists(true);
            map_enemy[i].setName(names_of_enemys[i]);
        }
    }

    private void spawnTrap(String[] names_of_traps) {

        for (int i = 0; i < NUM_OF_TRAP; i++) {
            map_trap[i].setExists(false);
        }

        for (int i = 0; i < NUM_OF_TRAP; i++) {

            if (names_of_traps[i].equals("null")) {
                break;
            }

            Point room_point = map_admin.getRoomPoint();
            map_trap[i].setPosition(room_point.x + magnification / 2, room_point.y + magnification / 2);
            map_trap[i].setExists(true);
            map_trap[i].setName(names_of_traps[i]);
        }

    }

}
