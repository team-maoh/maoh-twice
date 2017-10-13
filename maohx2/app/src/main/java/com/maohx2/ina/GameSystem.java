package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Battle.BattleUnitAdmin;
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



public class GameSystem {

    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;
    SurfaceHolder holder;
    DungeonUserInterface dungeon_user_interface;

    public void init(SurfaceHolder _holder, DungeonUserInterface _dungeon_user_interface, SoundAdmin sound_admin,Bitmap neco, Bitmap apple, Bitmap banana, Bitmap grape, Bitmap watermelon, Bitmap slime, Activity activity, Point display_size) {
        dungeon_user_interface = _dungeon_user_interface;
        map_admin = new MapAdmin(_holder, activity, display_size);
        map_object_admin = new MapObjectAdmin();
        map_object_admin.init(_holder, dungeon_user_interface, sound_admin, neco, apple, banana,grape,watermelon, slime, map_admin);//MapObjectAdmin.javaのinitを実行
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
        map_admin.drawMap(canvas);
        map_object_admin.draw(touch_x, touch_y, touch_state, canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void draw(GL10 gl) {
        map_object_admin.draw(gl);
    }

    public GameSystem() {
    }
}



/*
/**
 * Created by ina on 2017/09/21.
 */


/*

public class GameSystem {

    BattleUnitAdmin battle_unit_admin;
    SurfaceHolder holder;
    Paint paint = new Paint();
    Canvas canvas;
    BattleUserInterface battle_user_interface;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;

    public void init(SurfaceHolder _holder, BattleUserInterface _battle_user_interface) {

        holder = _holder;
        battle_user_interface = _battle_user_interface;
        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin();
        list_box_admin = new ListBoxAdmin();
        canvas = null;
        battle_unit_admin.init(battle_user_interface);
        text_box_admin.init(battle_user_interface);
        list_box_admin.init(battle_user_interface);
    }


    public void updata() {

        text_box_admin.update();
        list_box_admin.update();
        battle_user_interface.update();
        battle_unit_admin.update();
    }


    public void draw() {
        canvas = null;
        canvas = holder.lockCanvas(null);
        canvas.drawColor(Color.WHITE);
        paint.setAntiAlias(true);

        text_box_admin.draw(canvas);
        list_box_admin.draw(canvas);
        battle_user_interface.drawBattlePalette(canvas);
        battle_unit_admin.draw(canvas);

        holder.unlockCanvasAndPost(canvas);

    }
}







*/




/**
 * Created by ina on 2017/10/01.
 */

/*

public class GameSystem {

    SurfaceHolder holder;
    Paint paint = new Paint();
    Canvas canvas;
    UserInterface map_user_interface;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;
    GeoSlotAdmin geo_slot_admin;

    public void init(SurfaceHolder _holder, UserInterface _map_user_interface) {

        holder = _holder;
        canvas = null;
        map_user_interface = _map_user_interface;
        geo_slot_admin = new GeoSlotAdmin();
        text_box_admin = new TextBoxAdmin();
        list_box_admin = new ListBoxAdmin();
        geo_slot_admin.init(map_user_interface);
        text_box_admin.init(map_user_interface);
        list_box_admin.init(map_user_interface);
    }


    public void updata() {

        geo_slot_admin.updata();
        text_box_admin.update();
        list_box_admin.update();
        //map_user_interface.update();
    }


    public void draw() {
        canvas = null;
        canvas = holder.lockCanvas(null);
        canvas.drawColor(Color.WHITE);
        paint.setAntiAlias(true);

        geo_slot_admin.draw(canvas);
        text_box_admin.draw(canvas);
        list_box_admin.draw(canvas);

        holder.unlockCanvasAndPost(canvas);

    }
}

*/