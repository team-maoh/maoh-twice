package com.maohx2.ina;


import android.app.Activity;
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
 * Created by ina on 2017/10/15.
 */




public class BattleGameSystem {

    BattleUnitAdmin battle_unit_admin;
    SurfaceHolder holder;
    Paint paint = new Paint();
    Canvas canvas;
    BattleUserInterface battle_user_interface;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;
    Graphic graphic;

    public void init(SurfaceHolder _holder, Graphic _graphic, BattleUserInterface _battle_user_interface, Activity battle_activity) {

        holder = _holder;
        graphic = _graphic;

        battle_user_interface = _battle_user_interface;
        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin();
        list_box_admin = new ListBoxAdmin();
        canvas = null;
        battle_unit_admin.init(graphic, battle_user_interface, battle_activity);
        text_box_admin.init(battle_user_interface);
        list_box_admin.init(battle_user_interface, graphic);
    }


    public void updata() {

        text_box_admin.update();
        list_box_admin.update();
        battle_user_interface.update();
        battle_unit_admin.update();
    }


    public void draw() {
        battle_unit_admin.draw();
        graphic.draw();
    }
}