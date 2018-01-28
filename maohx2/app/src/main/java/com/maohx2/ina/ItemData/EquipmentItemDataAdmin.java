package com.maohx2.ina.ItemData;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by ina on 2017/12/15.
 */

public class EquipmentItemDataAdmin extends ItemDataAdmin<EquipmentItemData> {

    public EquipmentItemDataAdmin(Graphic _graphic, MyDatabaseAdmin _database_admin){
        super(_graphic, _database_admin);
        loadItemData("EquipmentItemData");

    }

    @Override
    public void loadItemData(String tableName) {
        int size = database.getSize(tableName);

        List<String> name = database.getString(tableName, "name");
        List<String> image_name = database.getString(tableName, "image_name");
        //List<Integer> item_kind = database.getInt(tableName, "item_kind");
        List<Integer> equipment_kind = database.getInt(tableName, "equipment_kind");
        List<Integer> attack = database.getInt(tableName, "attack");
        List<Integer> price = database.getInt(tableName, "price");


        for (int i = 0; i < size; i++) {
            datas.add(new EquipmentItemData());
            datas.get(i).setName(name.get(i));
            datas.get(i).setImageName(image_name.get(i));
            datas.get(i).setEquipmentKind(equipment_kind.get(i));
            datas.get(i).setAttack(attack.get(i));
            datas.get(i).setPrice(price.get(i));
            datas.get(i).setItemKind(0);
            datas.get(i).setItemImage(graphic.searchBitmap(image_name.get(i)));
        }
    }
}
