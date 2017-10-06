package com.maohx2.fuusya;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.ImageAdmin;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.pow;

/**
 * Created by ina on 2017/09/05.
 */

public class MapObjectAdmin {

    int NUM_OF_ENEMY = 5;
    int NUM_OF_ITEM = 10;// > 2
    MapUnit[] map_unit = new MapUnit[1];
    double player_x, player_y;
    MapItem[] map_item = new MapItem[NUM_OF_ITEM];
    MapEnemy[] map_enemy = new MapEnemy[NUM_OF_ENEMY];
    SurfaceHolder holder;
    int REACH_OF_PLAYER = 25;//プレイヤーのアイテム取得半径
    int item_distance, enemy_distance;
    BagItemAdmin bag_item_admin;
    ImageAdmin image_admin;
    MapAdmin map_admin;

    public void init(SurfaceHolder _holder, Bitmap draw_player, Bitmap draw_apple, Bitmap draw_banana, Bitmap draw_grape, Bitmap draw_watermelon, Bitmap draw_enemy, MapAdmin _map_admin) {
        //↑draw_player = neco, draw_item = apple, draw_enemy = slime (GameSystem.java参照)

        holder = _holder;

        map_admin = _map_admin;
        map_unit[0] = new MapPlayer();
        map_unit[0].init(_holder, draw_player, this, map_admin);
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
        for (int i = 2; i < 5; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(_holder, draw_banana, this);
            map_item[i].setId(2);
        }
        for (int i = 5; i < 8; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(_holder, draw_grape, this);
            map_item[i].setId(3);
        }
        for (int i = 8; i < NUM_OF_ITEM; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(_holder, draw_watermelon, this);
            map_item[i].setId(4);
        }


        item_distance = 0;
        enemy_distance = 0;

        bag_item_admin = new BagItemAdmin();
        bag_item_admin.init();
    }


    public void init(ImageAdmin _image_admin) {
        image_admin = _image_admin;

        map_unit[0] = new MapPlayer();
        map_unit[0].init(image_admin.getGL10(), image_admin.getImage(0), this);
        player_x = map_unit[0].getMapX();
        player_y = map_unit[0].getMapY();

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_enemy[i] = new MapEnemy();
            map_enemy[i].init(image_admin.getGL10(), image_admin.getImage(1), this);
        }

        for (int i = 0; i < 2; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(image_admin.getGL10(), image_admin.getImage(2), this);
            map_item[i].setId(1);
        }
        for (int i = 2; i < NUM_OF_ITEM; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(image_admin.getGL10(), image_admin.getImage(3), this);
            map_item[i].setId(2);
        }

        item_distance = 0;
        enemy_distance = 0;

        bag_item_admin = new BagItemAdmin();
        bag_item_admin.init();
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
            item_distance = myDistance(player_x, player_y, map_item[i].getMapX(), map_item[i].getMapY());

            if (item_distance < REACH_OF_PLAYER && map_item[i].exists() == true) {
                System.out.println("アイテム獲得");
                bag_item_admin.setItemIdToBagItem(map_item[i].getId());//アイテムidを引き渡す
                map_item[i].setExists(false);
            }
        }

        //ジオ敵と接触
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            enemy_distance = myDistance(player_x, player_y, map_enemy[i].getMapX(), map_enemy[i].getMapY());

            if (enemy_distance < REACH_OF_PLAYER && map_enemy[i].exists() == true) {
                System.out.println("ジオ敵と接触");
                map_enemy[i].setExists(false);//接触すると敵が消える(戦闘に突入する)
            }
        }
    }

    public void draw(double touch_x, double touch_y, int touch_state, Canvas canvas) {
        //canvas.drawColor(Color.BLACK);

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

    }

    /*
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
*/

    public void draw(GL10 gl) {

        map_unit[0].draw(gl);

        for (int i = 0; i < NUM_OF_ITEM; i++) {
            if (map_item[i].exists() == true) {
                map_item[i].draw(gl);
            }
        }

        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            if (map_enemy[i].exists() == true) {
                map_enemy[i].draw(gl);
            }
        }
    }

    public MapUnit getUnit(int unit_num) {
        return map_unit[unit_num];
    }

    //(x1, y1)と(x2, y2)の距離を返す
    int myDistance(double x1, double y1, double x2, double y2) {
        return (int) pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5);
    }
}