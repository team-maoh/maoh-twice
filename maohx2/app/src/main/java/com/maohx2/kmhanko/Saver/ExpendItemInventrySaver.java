package com.maohx2.kmhanko.Saver;

import java.util.List;

import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.ExpendItemData;

import static com.maohx2.ina.Constants.Inventry.INVENTRY_DATA_MAX;

/**
 * Created by user on 2018/04/01.
 */

public class ExpendItemInventrySaver extends InventrySaver {

    ItemDataAdminManager itemDataAdminManager;

    public ExpendItemInventrySaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
    }

    @Override
    public void dbinit() {}

    public void init(ItemDataAdminManager _itemDataAdminManager) {
        itemDataAdminManager = _itemDataAdminManager;
    }

    @Override
    public void save() {
        deleteAll(); //セーブデータをリセットして書き直す場合に呼び出す

        /*
        List<String> itemNames = new ArrayList<String>();
        List<Integer> nums = new ArrayList<Integer>();
        */

        for (int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (inventry.getItemData(i) == null) {
                break;
            }
            /*
            itemNames.add(inventry.getItemData(i).getName());
            nums.add(inventry.getItemNum(i));
            */

            database.insertLineByArrayString(
                    "ExpendItemInventry",
                    new String[] { "name", "num", "palettePosition"},
                    new String[] { inventry.getItemData(i).getName(), String.valueOf(inventry.getItemNum(i)), String.valueOf(((ExpendItemData)(inventry.getItemData(i))).getPalettePosition())}
            );

        }
/*
        database.insertRawByList("ExpendItemInventry", "name", itemNames);
        database.rewriteRawByList("ExpendItemInventry", "num", nums);
*/
    }

    @Override
    public void load() {
        List<String> itemNames = database.getString("ExpendItemInventry", "name");
        List<Integer> nums = database.getInt("ExpendItemInventry", "num");
        List<Integer> palettePosition = database.getInt("ExpendItemInventry", "palettePosition");

        if (itemNames.size() != nums.size()) {
            throw new Error("☆タカノ:ExpendItemInventrySaver#load 取得したアイテム名とアイテムの個数のデータ数が一致しない " + itemNames.size() + "," + nums.size());
        }

        for(int i = 0; i < itemNames.size(); i++) {
            ExpendItemData tempExpendItemData = ((ExpendItemData)(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName(itemNames.get(i))));
            tempExpendItemData.setPalettePosition(palettePosition.get(i),true);
            for(int j = 0; j < nums.get(i); j++){
                inventry.addItemData(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName(itemNames.get(i)));
            }
        }
    }
}
