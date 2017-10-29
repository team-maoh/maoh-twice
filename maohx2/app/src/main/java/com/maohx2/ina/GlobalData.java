package com.maohx2.ina;

/**
 * Created by ina on 2017/10/22.
 */

import android.app.Application;

import com.maohx2.ina.Draw.BitmapDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

//アイテムのアイコン画像、プレイヤーのステータス、アイテムのデータ、をグローバルで持つ予定
public class GlobalData extends Application {

    BitmapDataAdmin g_bitmap_data_admin;
    MyDatabaseAdmin g_my_database_admin;

    public void init() {
        g_my_database_admin = new MyDatabaseAdmin(this);
        g_my_database_admin.addMyDatabase("global_image_DB", "global_image1.db", 1, "r");
        g_bitmap_data_admin = new BitmapDataAdmin();
        g_bitmap_data_admin.init(this, g_my_database_admin.getMyDatabase("global_image_DB"));
        g_bitmap_data_admin.loadGlobalImages();
    }

    //ゲッターとか
    public BitmapDataAdmin getGlobalBitmapDataAdmin() { return g_bitmap_data_admin;}
}
