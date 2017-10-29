package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Map.GeoSlotAdmin;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.Text.TextBoxAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.ina.UI.MapUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.waste.ImageAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;

import javax.microedition.khronos.opengles.GL10;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;

/**
 * Created by ina on 2017/09/05.
 */



public class DungeonGameSystem {

    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;
    SurfaceHolder holder;
    DungeonUserInterface dungeon_user_interface;
    Graphic graphic;


    public void init(Context context, SurfaceHolder _holder, DungeonUserInterface _dungeon_user_interface, SoundAdmin sound_admin, Bitmap neco, Bitmap apple, Bitmap banana, Bitmap grape, Bitmap watermelon, Bitmap slime, Activity activity, Point display_size,Graphic _graphic) {
        dungeon_user_interface = _dungeon_user_interface;
        graphic = _graphic;
        map_admin = new MapAdmin(_holder, activity, display_size);
        map_object_admin = new MapObjectAdmin();
        map_object_admin.init(context, _holder, dungeon_user_interface, sound_admin, neco, apple, banana,grape,watermelon, slime, map_admin);//MapObjectAdmin.javaのinitを実行
        holder = _holder;
    }

//    public void init(ImageAdmin image_admin) {
//        map_object_admin = new MapObjectAdmin();
//        map_object_admin.init(image_admin);//MapObjectAdmin.javaのinitを実行
//    }



    public void update(double touch_x, double touch_y, TouchState touch_state) {

        map_object_admin.update(touch_x, touch_y, touch_state);
    }

    public void draw(double touch_x, double touch_y, TouchState touch_state) {


        Canvas canvas = null;
        canvas = holder.lockCanvas(null);
        if(canvas != null) {
            map_admin.drawMap(canvas);
            map_object_admin.draw(touch_x, touch_y, touch_state, canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void draw(GL10 gl) {
        map_object_admin.draw(gl);
    }

    public DungeonGameSystem() {
    }
}


