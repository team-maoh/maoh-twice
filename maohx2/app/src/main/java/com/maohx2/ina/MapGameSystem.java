package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.ContextMenu;
import android.view.SurfaceHolder;
import com.maohx2.ina.Map.GeoSlotAdmin;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.Text.TextBoxAdmin;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;

import android.graphics.Color;
import android.graphics.Paint;
/**
 * Created by ina on 2017/10/20.
 */


/**
 * Created by ina on 2017/10/01.
 */

public class MapGameSystem {

    SurfaceHolder holder;
    Paint paint = new Paint();
    Canvas canvas;
    UserInterface map_user_interface;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;
    GeoSlotAdmin geo_slot_admin;
    GeoSlotAdminManager geo_slot_admin_manager;
    MyDatabaseAdmin my_data_base_admin;


    public void init(SurfaceHolder _holder, UserInterface _map_user_interface, Activity map_activity, MyDatabaseAdmin _my_data_base_admin) {

        holder = _holder;
        my_data_base_admin = _my_data_base_admin;
        canvas = null;
        map_user_interface = _map_user_interface;
        //geo_slot_admin = new GeoSlotAdmin();
        text_box_admin = new TextBoxAdmin();
        list_box_admin = new ListBoxAdmin();
        geo_slot_admin_manager = new GeoSlotAdminManager();
        //geo_slot_admin.init(map_user_interface, map_activity);

        text_box_admin.init(map_user_interface);
        list_box_admin.init(map_user_interface);
        geo_slot_admin_manager.init(map_user_interface,my_data_base_admin.getMyDatabase("GeoSlotDB"));
        geo_slot_admin_manager.setActiveGeoSlotAdmin("Test");

    }


    public void updata() {
        geo_slot_admin_manager.update();
        text_box_admin.update();
        list_box_admin.update();
        //map_user_interface.update();
    }


    public void draw() {
        canvas = null;
        canvas = holder.lockCanvas(null);
        if(canvas != null) {
            canvas.drawColor(Color.WHITE);
            paint.setAntiAlias(true);

            geo_slot_admin_manager.draw(canvas);
            text_box_admin.draw(canvas);
            list_box_admin.draw(canvas);

            holder.unlockCanvasAndPost(canvas);
        }

    }
}

