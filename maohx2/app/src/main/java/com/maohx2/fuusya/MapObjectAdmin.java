package com.maohx2.fuusya;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonGameSystem;
import com.maohx2.ina.DungeonModeManage;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.UI.DungeonUserInterface;
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
    int NUM_OF_ITEM = 10;// > 2
    int NUM_OF_TRAP = 1;

    int PLAYER_DIR = 8;
    int ENEMY_DIR = 8;

    MapPlayer map_player;
    MapObjectBitmap map_player_bitmap;
    double player_x, player_y;

    MapItem[] map_item = new MapItem[NUM_OF_ITEM];
    MapObjectBitmap[] map_item_bitmap = new MapObjectBitmap[NUM_OF_ITEM];
    MapTrap[] map_trap = new MapTrap[NUM_OF_TRAP];
    MapObjectBitmap[] map_trap_bitmap = new MapObjectBitmap[NUM_OF_TRAP];

    MapEnemy[] map_enemy = new MapEnemy[NUM_OF_ENEMY];
    MapObjectBitmap[] map_enemy_bitmap = new MapObjectBitmap[NUM_OF_ENEMY];

    double item_distance, enemy_distance;

    BagItemAdmin bag_item_admin;
    SoundAdmin sound_admin;
    DungeonUserInterface dungeon_user_interface;
    MapPlateAdmin map_plate_admin;
    DungeonModeManage dungeon_mode_manage;

    Graphic graphic;
    Camera camera;

    boolean is_displaying_menu;

    public MapObjectAdmin(Graphic _graphic, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, MapPlateAdmin _map_plate_admin, DungeonModeManage _dungeon_mode_manage) {
        graphic = _graphic;
        dungeon_user_interface = _dungeon_user_interface;
        sound_admin = _sound_admin;
        map_plate_admin = _map_plate_admin;
        dungeon_mode_manage = _dungeon_mode_manage;

        is_displaying_menu = false;

        map_player = new MapPlayer(graphic, this, dungeon_user_interface, _sound_admin, camera, map_plate_admin);
        map_player.init();
        map_player_bitmap = new MapObjectBitmap(PLAYER_DIR, graphic, "主人公");
        map_player_bitmap.init();

        player_x = map_player.getWorldX();
        player_y = map_player.getWorldY();

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i] = new MapItem(graphic, this, i % 2, camera);
            map_item[i].init();

            switch (i % 2) {
                case 0:
                    map_item_bitmap[i] = new MapObjectBitmap(8, graphic, "ハーピー");
                    break;
                case 1:
                    map_item_bitmap[i] = new MapObjectBitmap(8, graphic, "ドラゴン");
                    break;
                default:
                    break;
            }
            map_item_bitmap[i].init();
        }

        for (int i = 0; i < NUM_OF_TRAP; i++) {

            map_trap[i] = new MapTrap(graphic, this, i % 4, camera, false, "being_teleported");
            map_trap[i].init();

            map_trap_bitmap[i] = new MapObjectBitmap(1, graphic, "cave_thing_01");
            map_trap_bitmap[i].init();
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {

            map_enemy[i] = new MapEnemy(graphic, this, camera, ENEMY_DIR, true, true);
            map_enemy[i].init();
            map_enemy_bitmap[i] = new MapObjectBitmap(ENEMY_DIR, graphic, "ジオイーター");
            map_enemy_bitmap[i].init();
        }

        item_distance = 0;
        enemy_distance = 0;

        bag_item_admin = new BagItemAdmin();
        bag_item_admin.init();
    }

    public void init() {
    }

    public void update() {

        map_player.update();
        map_player_bitmap.update();
        player_x = map_player.getWorldX();
        player_y = map_player.getWorldY();

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i].update();
            map_item_bitmap[i].update();
        }
        for (int i = 0; i < NUM_OF_TRAP; i++) {
            map_trap[i].update();
            map_trap_bitmap[i].update();
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].update();
            map_enemy_bitmap[i].update();
        }

    }

    public void draw() {

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            if (map_item[i].exists() == true) {
                map_item_bitmap[i].draw(map_item[i].getDirOnMap(), map_item[i].getNormX(), map_item[i].getNormY());
            }
        }
        for (int i = 0; i < NUM_OF_TRAP; i++) {
            if (map_trap[i].exists() == true && map_trap[i].isVisible() == true) {
                map_trap_bitmap[i].draw(map_trap[i].getDirOnMap(), map_trap[i].getNormX(), map_trap[i].getNormY());
            }
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            if (map_enemy[i].exists() == true) {
                map_enemy_bitmap[i].draw(map_enemy[i].getDirOnMap(), map_enemy[i].getNormX(), map_enemy[i].getNormY());
            }
        }

        map_player_bitmap.draw(map_player.getDirOnMap(), map_player.getNormX(), map_player.getNormY());

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
    public double myDistance(double x1, double y1, double x2, double y2) {
        return pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5);
    }

    //by kmhanko
    public void battleStart() {
        //dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT);
    }

//    public void getCamera(Camera _camera){
//        camera = _camera;
////        map_player.setCamera(camera);
//    }


    public void initObjectPosition(MapAdmin map_admin) {

        map_player.initPosition(map_admin);

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i].initPosition(map_admin);

        }
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].initPosition(map_admin);

        }

        for (int i = 0; i < NUM_OF_TRAP; i++) {
            map_trap[i].initPosition(map_admin);
        }
    }

}
