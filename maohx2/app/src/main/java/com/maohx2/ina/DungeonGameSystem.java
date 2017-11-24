package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.sound.SoundAdmin;

/**
 * Created by ina on 2017/09/05.
 */



public class DungeonGameSystem {

    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;
    DungeonUserInterface dungeon_user_interface;
    Graphic graphic;


    public void init(DungeonUserInterface _dungeon_user_interface, Graphic _graphic, SoundAdmin sound_admin) {
        dungeon_user_interface = _dungeon_user_interface;
        graphic = _graphic;
        //holder = _holder;

        //☆map_admin = new MapAdmin(graphic);
        //☆map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, sound_admin, map_admin);
        //map_object_admin.init();//MapObjectAdmin.javaのinitを実行
    }


    public void update() {
        //☆map_object_admin.update();
    }

    public void draw() {
        /*
        Canvas canvas = null;
        canvas = holder.lockCanvas(null);
        if(canvas != null) {
            map_admin.drawMap(canvas);
            map_object_admin.draw(touch_x, touch_y, touch_state, canvas);
            holder.unlockCanvasAndPost(canvas);
        }
        */

        //☆map_admin.drawMap();
        //☆map_object_admin.draw();
        //☆graphic.draw();

    }

    public DungeonGameSystem() {
    }
}


