package com.maohx2.kmhanko.geonode;

/**
 * Created by user on 2017/10/27.
 */

import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

//GeoSlotAdminの実体を持つクラス
public class GeoSlotAdminManager {
    static final String DB_NAME = "GeoSlotMapDB";
    static final String DB_ASSET = "GeoSlotMapDB.db";

    public final int GEO_SLOT_ADMIN_MAX = 16;
    List<GeoSlotAdmin> geo_slot_admins = new ArrayList<GeoSlotAdmin>(GEO_SLOT_ADMIN_MAX);
    GeoSlotAdmin active_geo_slot_admin;
    GeoCalcSaverAdmin geo_calc_saver_admin;

    MyDatabase database;
    Graphic graphic;

    boolean is_load_database;

    public GeoSlotAdminManager() {
    }

    public void init(Graphic _graphic, UserInterface userInterface, MyDatabaseAdmin databaseAdmin) {
        graphic = _graphic;
        this.setDatabase(databaseAdmin);
        this.loadDatabase(userInterface);
    }

    public void update() {
        if (active_geo_slot_admin != null) {
            active_geo_slot_admin.update();
        }
        /*
        for(int i = 0; i < geo_slot_admins.size(); i++) {
            if (geo_slot_admins.get(i) != null) {
                geo_slot_admins.get(i).update();
            }
        }
        */
    }

    public void draw() {
        if (active_geo_slot_admin != null) {
            active_geo_slot_admin.draw();
        }
        /*
        for(int i = 0; i < geo_slot_admins.size(); i++) {
            if (geo_slot_admins.get(i) != null) {
                geo_slot_admins.get(i).draw(canvas);
            }
        }
        */

        //active_geo_slot_admin.drawParam(canvas);

    }

    public void setActiveGeoCalcSaverAdmin() {
        geo_calc_saver_admin = active_geo_slot_admin.getGeoCalcSaverAdmin();
    }

    public void setActiveGeoSlotAdmin(String name) {
        for(int i = 0; i < geo_slot_admins.size(); i++) {
            if (geo_slot_admins.get(i) != null) {
                if (geo_slot_admins.get(i).getName().equals(name)) {
                    active_geo_slot_admin = geo_slot_admins.get(i);
                    return;
                }
            }
        }
        throw new Error("GeoSlotAdminManager#setActiveGeoSlotAdmin : There is no GeoSlotAdmin you request by name : "+name);
    }

    public void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    public void loadDatabase(UserInterface user_inter_face) {
        if (is_load_database) {
            return;
        }
        is_load_database = true;
        //List<Integer> test = database.getInt("Test","rowid");
        List<String> t_names = database.getTables();
        GeoSlotAdmin.setDatabase(database);

        for(int i = 0; i < t_names.size(); i++) {
            GeoSlotAdmin new_geo_slot_admin = new GeoSlotAdmin();
            new_geo_slot_admin.init(graphic, user_inter_face);
            new_geo_slot_admin.loadDatabase(t_names.get(i));
            geo_slot_admins.add(new_geo_slot_admin);
        }

    }

}
