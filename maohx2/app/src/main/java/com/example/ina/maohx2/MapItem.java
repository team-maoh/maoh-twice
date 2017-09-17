package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import java.util.Random;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapItem extends MapObject {

    MapUnit player;
    MapObjectAdmin map_object_admin;
    double player_x, player_y;
    double REACH_OF_PLAYER;

    public void init(SurfaceHolder _holder, Bitmap _draw_item, MapObjectAdmin _map_object_admin) {
        super.init(_holder, _draw_item);

        map_object_admin = _map_object_admin;
        REACH_OF_PLAYER = 100.0;//プレイヤーのアイテム取得半径

        Random random = new Random();
        x = random.nextDouble() * 1000;//アイテムが発生する座標
        y = random.nextDouble() * 600;
    }

    @Override
    public void update(double touch_x, double touch_y, int touch_state) {
        super.update(touch_x, touch_y, touch_state);

        player = map_object_admin.getUnit(0);

        player_x = player.getMapX();//getMapX(),getMapY()はMapObject内部に記述されている
        player_y = player.getMapY();
        if (Math.pow(Math.pow(player_x - x, 2.0) + Math.pow(player_y - y, 2.0), 0.5) < REACH_OF_PLAYER) {
            System.out.println("アイテム取得");
            x = -1.0;
            y = -1.0;
        }
    }
}
