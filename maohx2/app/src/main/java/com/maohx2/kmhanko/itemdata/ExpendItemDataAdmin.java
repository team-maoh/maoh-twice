package com.maohx2.kmhanko.itemdata;


import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.ina.ItemData.ItemDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by user on 2017/11/05.
 */

public class ExpendItemDataAdmin extends ItemDataAdmin<ExpendItemData> {

    public ExpendItemDataAdmin(Graphic _graphic, MyDatabaseAdmin databaseAdmin) {
        super(_graphic, databaseAdmin);
        /*
        dbName = "ExpendItemDataDB";
        dbAsset = "ExpendItemDataDB.db";
        */
        loadItemData("ExpendItemData");
    }

    @Override
    public void loadItemData(String tableName) {
        int size = database.getSize(tableName);

        List<String> name = database.getString(tableName, "name");
        List<String> imageName = database.getString(tableName, "image_name");
        List<Integer> price = database.getInt(tableName, "price");
        List<Integer> hp = database.getInt(tableName, "hp");
        List<String> expline = database.getString(tableName, "expline");

        for (int i = 0; i < size; i++) {
            datas.add(new ExpendItemData());
            datas.get(i).setName(name.get(i));
            datas.get(i).setImageName(imageName.get(i));
            datas.get(i).setPrice(price.get(i));
            datas.get(i).setHp(hp.get(i));
            datas.get(i).setExpline(expline.get(i));

            datas.get(i).setItemImage(graphic.searchBitmap(imageName.get(i)));
        }
    }

}
