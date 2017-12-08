//package com.maohx2.kmhanko.itemshop;

import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/10.
 */

/*

public class ExpendItemShopDataAdmin extends ItemShopDataAdmin {
    static final String DB_NAME = "ExpendItemShopDataDB";
    static final String DB_ASSET = "ExpendItemShopDataDB.db";

    ExpendItemDataAdmin expendItemDataAdmin;
    List<ExpendItemShopData> expendItemShopDataList = new ArrayList<>();
    MyDatabase database;
    List<String> table_names;

    public ExpendItemShopDataAdmin() {
    }


    public void init(ExpendItemDataAdmin _expendItemDataAdmin) {
        expendItemDataAdmin = _expendItemDataAdmin;
    }

    public void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = database_admin.getMyDatabase(DB_NAME);

        table_names = database.getTables();
        for (int i = 0; i < table_names.size(); i++) {

        }
    }

}

*/