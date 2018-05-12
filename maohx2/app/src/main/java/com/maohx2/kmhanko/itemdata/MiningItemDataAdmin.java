package com.maohx2.kmhanko.itemdata;


/**
 * Created by user on 2018/05/08.
 */

import com.maohx2.ina.Constants;import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.ina.ItemData.ItemDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import java.util.List;

/**
 * Created by user on 2017/11/05.
 */

public class MiningItemDataAdmin extends ItemDataAdmin<MiningItemData> {



    public MiningItemDataAdmin(Graphic _graphic, MyDatabaseAdmin databaseAdmin) {
        super(_graphic, databaseAdmin);
        /*
        dbName = "ExpendItemDataDB";
        dbAsset = "ExpendItemDataDB.db";
        */
        loadItemData("MiningItemData");
    }

    @Override
    public void loadItemData(String tableName) {
        int size = database.getSize(tableName);

        List<String> name = database.getString(tableName, "name");
        List<String> imageName = database.getString(tableName, "image_name");
        List<Integer> radius = database.getInt(tableName, "radius");
        List<Float> decayRate = database.getFloat(tableName, "decay_rate");
        List<Integer> touchFrequency = database.getInt(tableName, "touch_frequency");
        List<Integer> autoFrequencyRate = database.getInt(tableName, "auto_frequency_rate");
        List<Integer> attack = database.getInt(tableName, "attack");


        for (int i = 0; i < size; i++) {
            datas.add(new MiningItemData());
            datas.get(i).setName(name.get(i));
            datas.get(i).setImageName(imageName.get(i));

            datas.get(i).setRadius(radius.get(i));
            datas.get(i).setDecayRate(decayRate.get(i));
            datas.get(i).setTouchFrequency(touchFrequency.get(i));
            datas.get(i).setAutoFrequencyRate(autoFrequencyRate.get(i));
            datas.get(i).setAttack(attack.get(i));

            datas.get(i).setItemKind(Constants.Item.ITEM_KIND.MINING);

            datas.get(i).setItemImage(graphic.searchBitmap(imageName.get(i)));
        }
    }
/*
    static public ExpendItemData getDebugExpendItemData(int i) {

    }
*/
}
