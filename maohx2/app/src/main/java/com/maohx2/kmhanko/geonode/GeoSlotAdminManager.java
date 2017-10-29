package com.maohx2.kmhanko.geonode;

/**
 * Created by user on 2017/10/27.
 */

import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

import com.maohx2.ina.Map.GeoSlot;
import com.maohx2.ina.Map.GeoSlotAdmin;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;

//GeoSlotAdminの実体を持つクラス
public class GeoSlotAdminManager {
    public final int GEO_SLOT_ADMIN_MAX = 16;
    List<GeoSlotAdmin> geo_slot_admins = new ArrayList<GeoSlotAdmin>(GEO_SLOT_ADMIN_MAX);
    GeoSlotAdmin active_geo_slot_admin;
    GeoCalcSaverAdmin geo_calc_saver_admin;

    MyDatabase database;

    boolean is_load_database;

    public GeoSlotAdminManager() {
    }

    public void init(UserInterface userInterface, MyDatabase _database) {
        database = _database;
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

    public void draw(Canvas canvas) {
        if (active_geo_slot_admin != null) {
            active_geo_slot_admin.draw(canvas);
        }
        /*
        for(int i = 0; i < geo_slot_admins.size(); i++) {
            if (geo_slot_admins.get(i) != null) {
                geo_slot_admins.get(i).draw(canvas);
            }
        }
        */

        active_geo_slot_admin.drawParam(canvas);

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
            new_geo_slot_admin.init(user_inter_face);
            new_geo_slot_admin.loadDatabase(t_names.get(i));
            geo_slot_admins.add(new_geo_slot_admin);
        }

    }

}
