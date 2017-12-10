package com.maohx2.fuusya;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.Graphic;
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

    int NUM_OF_ENEMY = 5;
    int NUM_OF_ITEM = 10;// > 2

    int PLAYER_DIR = 4;
    int ENEMY_DIR = 8;

    MapPlayer map_player;
    MapObjectBitmap map_player_bitmap;
    double player_x, player_y;
    double chase_x, chase_y;

    MapItem[] map_item = new MapItem[NUM_OF_ITEM];
    MapObjectBitmap[] map_item_bitmap = new MapObjectBitmap[NUM_OF_ITEM];
    MapEnemy[] map_enemy = new MapEnemy[NUM_OF_ENEMY];
    MapObjectBitmap[] map_enemy_bitmap = new MapObjectBitmap[NUM_OF_ENEMY];

    int REACH_OF_PLAYER = 25;//プレイヤーのアイテム取得半径
    double item_distance, enemy_distance;
    BagItemAdmin bag_item_admin;
    MapAdmin map_admin;
    SoundAdmin sound_admin;
    DungeonUserInterface dungeon_user_interface;

    Graphic graphic;

    public MapObjectAdmin(Graphic _graphic, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, MapAdmin _map_admin) {
        graphic = _graphic;
        dungeon_user_interface = _dungeon_user_interface;
        map_admin = _map_admin;
        sound_admin = _sound_admin;

        map_player = new MapPlayer(graphic, this, map_admin, dungeon_user_interface, _sound_admin);
        map_player.init();
        map_player_bitmap = new MapObjectBitmap(PLAYER_DIR, graphic);
        map_player_bitmap.init();

        player_x = map_player.getMapX();
        player_y = map_player.getMapY();

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i] = new MapItem(graphic, this, i%4);
            map_item[i].init();
            map_item_bitmap[i] = new MapObjectBitmap(1, graphic);
            map_item_bitmap[i].init();
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i] = new MapEnemy(graphic, this);
            map_enemy[i].init();
            map_enemy_bitmap[i] = new MapObjectBitmap(ENEMY_DIR, graphic);
            map_enemy_bitmap[i].init();
        }


        item_distance = 0;
        enemy_distance = 0;

        bag_item_admin = new BagItemAdmin();
        bag_item_admin.init();
    }

    public void init(){

    }

    public void update() {

        map_player.update();
        player_x = map_player.getMapX();
        player_y = map_player.getMapY();
//        chase_x = map_player.getChaseWorldX();
//        chase_y = map_player.getChaseWorldY();

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            map_item[i].update();
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].update();
            map_enemy_bitmap[i].setDirection(map_enemy[i].getDirection());
        }

        //アイテム獲得
        for (int i = 0; i < NUM_OF_ITEM; i++) {
            item_distance = myDistance(player_x, player_y, map_item[i].getMapX(), map_item[i].getMapY());

            if (item_distance < REACH_OF_PLAYER && map_item[i].exists() == true) {
                System.out.println("アイテム獲得");
                sound_admin.play("getitem");
                bag_item_admin.setItemIdToBagItem(map_item[i].getId());//アイテムidを引き渡す
                map_item[i].setExists(false);
            }
        }

        //敵と接触
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            enemy_distance = myDistance(player_x, player_y, map_enemy[i].getMapX(), map_enemy[i].getMapY());

            if (enemy_distance < REACH_OF_PLAYER && map_enemy[i].exists() == true) {
                System.out.println("敵と接触");
                map_enemy[i].setExists(false);//接触すると敵が消える(戦闘に突入する)
            }
        }
    }

    public void draw() {

        map_player.draw(map_admin);

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            if (map_item[i].exists() == true) {
//                map_item[i].draw(map_admin);
                map_item_bitmap[i].draw();
            }
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            if (map_enemy[i].exists() == true) {
//                map_enemy[i].draw(map_admin);
                map_enemy_bitmap[i].draw();
            }
        }

    }

    public MapPlayer getPlayer() {
        return map_player;
    }

    //(x1, y1)と(x2, y2)の距離を返す
    public double myDistance(double x1, double y1, double x2, double y2) {
        return pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5);
    }
}
