package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

/**
 * Created by ina on 2017/09/05.
 */

public class MapObjectAdmin {

    int NUM_OF_ENEMY = 5;
    int NUM_OF_ITEM = 6;// > 2
    MapUnit[] map_unit = new MapUnit[1];
    double player_x, player_y;
    MapItem[] map_item = new MapItem[NUM_OF_ITEM];
    MapEnemy[] map_enemy = new MapEnemy[NUM_OF_ENEMY];
    SurfaceHolder holder;
    double REACH_OF_PLAYER = 100.0;//プレイヤーのアイテム取得半径
    double item_distance, enemy_distance;
    BagItem bag_item;

    public void init(SurfaceHolder _holder, Bitmap draw_player, Bitmap draw_apple, Bitmap draw_banana, Bitmap draw_enemy) {
        //↑draw_player = neco, draw_item = apple, draw_enemy = slime (GameSystem.java参照)

        holder = _holder;

        map_unit[0] = new MapPlayer();
        map_unit[0].init(_holder, draw_player, this);
        player_x = map_unit[0].getMapX();
        player_y = map_unit[0].getMapY();

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i] = new MapEnemy();
            map_enemy[i].init(_holder, draw_enemy, this);
        }

        for (int i = 0; i < 2; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(_holder, draw_apple, this);
            map_item[i].setId(1);
        }
        for (int i = 2; i < NUM_OF_ITEM; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(_holder, draw_banana, this);
            map_item[i].setId(2);
        }

        item_distance = 0.0;
        enemy_distance = 0.0;

        bag_item = new BagItem();
        bag_item.init();

    }

    public void update(double touch_x, double touch_y, int touch_state) {

        map_unit[0].update(touch_x, touch_y, touch_state);
        player_x = map_unit[0].getMapX();
        player_y = map_unit[0].getMapY();

        for (int i = 0; i < NUM_OF_ITEM; i++)
            map_item[i].update(touch_x, touch_y, touch_state);

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i].update(touch_x, touch_y, touch_state);
        }

        //アイテム獲得
        for (int i = 0; i < NUM_OF_ITEM; i++) {
            item_distance = Math.pow(Math.pow(player_x - map_item[i].getMapX(), 2.0) + Math.pow(player_y - map_item[i].getMapY(), 2.0), 0.5);
            if (item_distance < REACH_OF_PLAYER && map_item[i].exists() == true) {
                System.out.println("アイテム獲得");
                bag_item.setItemIdToBagItem(map_item[i].getId());//アイテムidを引き渡す
                map_item[i].setExists(false);
            }
        }

        //ジオ敵と接触
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            enemy_distance = Math.pow(Math.pow(player_x - map_enemy[i].getMapX(), 2.0) + Math.pow(player_y - map_enemy[i].getMapY(), 2.0), 0.5);
            if (enemy_distance < REACH_OF_PLAYER && map_enemy[i].exists() == true) {
                System.out.println("ジオ敵と接触");
                map_enemy[i].setExists(false);
            }
        }
    }

    public void draw(double touch_x, double touch_y, int touch_state) {

        Canvas canvas = null;
        canvas = holder.lockCanvas(null);
        canvas.drawColor(Color.BLACK);

        map_unit[0].draw(touch_x, touch_y, touch_state, canvas);

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            if (map_item[i].exists() == true) {
                map_item[i].draw(touch_x, touch_y, touch_state, canvas);
            }
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            if (map_enemy[i].exists() == true) {
                map_enemy[i].draw(touch_x, touch_y, touch_state, canvas);
            }
        }
        holder.unlockCanvasAndPost(canvas);
    }

    public MapUnit getUnit(int unit_num) {
        return map_unit[unit_num];
    }
}