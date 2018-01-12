package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.*;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;
import com.maohx2.kmhanko.database.NamedDataAdmin;
import com.maohx2.kmhanko.itemdata.*;

import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by user on 2017/11/05.
 */


/*
一つのItemDataListを保持するクラス。異なる店を参照する場合は、その都度loadShopDataで読み出す。
常に全てのショップデータを持っている必要はないであろうという判断。
 */

public class ExpendItemShopData extends ItemShopData<ExpendItemData> {
    ExpendItemDataAdmin expendItemDataAdmin;

    public ExpendItemShopData(Graphic _graphic, MyDatabaseAdmin my_database_admin) {
        super(_graphic, my_database_admin);
        dbName = "ExpendItemShopDataDB";
        dbAsset = "ExpendItemShopDataDB.db";
    }

    public void init(ExpendItemDataAdmin _expendItemDataAdmin) {
        expendItemDataAdmin = _expendItemDataAdmin;
    }

    public void init(ExpendItemDataAdmin _expendItemDataAdmin, MyDatabaseAdmin database_admin) {
        setDatabase(database_admin);
        this.init(_expendItemDataAdmin);
    }

    @Override
    public void loadShopData(String table_name) {
        List<String> names = database.getString(table_name, "item_name");
        datas = expendItemDataAdmin.getDatasByNames(names);
    }

    @Override
    public void loadItemData(String tableName) {}
}
