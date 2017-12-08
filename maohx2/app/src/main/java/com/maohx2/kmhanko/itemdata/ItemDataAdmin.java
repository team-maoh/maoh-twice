package com.maohx2.kmhanko.itemdata;

/**
 * Created by user on 2017/11/05.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.jar.Attributes;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.NamedDataAdmin;

public abstract class ItemDataAdmin<T extends ItemData> extends NamedDataAdmin<T> {
    String dbName;
    String dbAsset;
    String tableName;

    //TODO tableNameが定数なのをなんとかして、NamedDataAdminにまで落とし込む作業
    public void init(MyDatabaseAdmin databaseAdmin) {
        loadDatabase(databaseAdmin);
    }

    public void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = database_admin.getMyDatabase(dbName);
        loadItemData(tableName);
    }

    public void loadItemData(String tableName) {
        int size = database.getSize(tableName);

        List<String> name = database.getString(tableName, "name");
        List<String> image_name = database.getString(tableName, "image_name");
        List<Integer> price = database.getInt(tableName, "price");

        for (int i = 0; i < size; i++) {
            //初期化
            datas.get(i).setName(name.get(i));
            datas.get(i).setImageName(image_name.get(i));
            datas.get(i).setPrice(price.get(i));
        }
    }

    public List<T> getItemDatas() {
        return datas;
    }

}
