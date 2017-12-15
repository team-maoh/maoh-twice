package com.maohx2.kmhanko.itemdata;


import com.maohx2.ina.ItemData.ItemDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by user on 2017/11/05.
 */

public class ExpendItemDataAdmin extends ItemDataAdmin<ExpendItemData> {

    public ExpendItemDataAdmin() {
        super();
        dbName = "ExpendItemDataDB";
        dbAsset = "ExpendItemDataDB.db";
        tableName = "debug";
    }

    @Override
    public void loadItemData(String table_name) {
        int size = database.getSize(table_name);
        List<Integer> hp = database.getInt(table_name, "hp");
        List<String> expline = database.getString(table_name, "expline");

        for (int i = 0; i < size; i++) {
            datas.add(new ExpendItemData());
            datas.get(datas.size() - 1).setHp(hp.get(i));
            datas.get(datas.size() - 1).setExpline(expline.get(i));
        }

        //呼び忘れないように注意
        super.loadItemData(table_name);

    }

}
