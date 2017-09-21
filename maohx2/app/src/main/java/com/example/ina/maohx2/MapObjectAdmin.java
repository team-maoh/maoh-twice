package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

/*
 * Created by ina on 2017/09/05.
 */

public class MapObjectAdmin {

    int NUM_OF_ENEMY;
    int NUM_OF_ITEM;
    MapUnit[] map_unit = new MapUnit[10];
    MapItem[] map_item = new MapItem[3];
    MapEnemy[] map_enemy = new MapEnemy[5];
    SurfaceHolder holder;

    public void init(SurfaceHolder _holder, Bitmap draw_player, Bitmap draw_item, Bitmap draw_enemy) {//draw_player = neco, draw_item = apple, draw_enemy = slime (GameSystem.java参照)

        NUM_OF_ENEMY = 10;
        NUM_OF_ITEM = 3;
        map_unit[0] = new MapPlayer();
        holder = _holder;

        map_unit[0].init(_holder, draw_player, this);

        for (int i = 0; i < 5; i++) {
            map_enemy[i] = new MapEnemy();
            map_enemy[i].init(_holder, draw_enemy, this);
        }

        for (int i = 0; i < 3; i++) {
            map_item[i] = new MapItem();
            map_item[i].init(_holder, draw_item, this);
        }

    }

    public void update(double touch_x, double touch_y, int touch_state) {

        map_unit[0].update(touch_x, touch_y, touch_state);
/*
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_unit[i].update(touch_x, touch_y, touch_state);
        }
*/
        for (int i = 0; i < 3; i++)
            map_item[i].update(touch_x, touch_y, touch_state);

        for (int i = 0; i < 5; i++) {
            map_enemy[i].update(touch_x, touch_y, touch_state);
        }
    }

    public void draw(double touch_x, double touch_y, int touch_state) {

        Canvas canvas = null;
        canvas = holder.lockCanvas(null);
        canvas.drawColor(Color.BLACK);

        map_unit[0].draw(touch_x, touch_y, touch_state, canvas);
/*
        for (int i = 0; i < NUM_OF_ENEMY; i++) {
            map_unit[i].draw(touch_x, touch_y, touch_state);
        }
*/

        for (int i = 0; i < 3; i++)
            map_item[i].draw(touch_x, touch_y, touch_state, canvas);

        for (int i = 0; i < 5; i++) {
            map_enemy[i].draw(touch_x, touch_y, touch_state, canvas);
        }
        holder.unlockCanvasAndPost(canvas);
    }

    public MapUnit getUnit(int unit_num) {

        return map_unit[unit_num];
    }

}