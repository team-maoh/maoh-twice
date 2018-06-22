package com.maohx2.ina.ItemData;

import com.maohx2.ina.Constants.Item.*;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by ina on 2017/12/15.
 */

public class EquipmentItemDataAdmin extends ItemDataAdmin<EquipmentItemData> {

    EQUIPMENT_KIND[] itoe;

    public EquipmentItemDataAdmin(Graphic _graphic, MyDatabaseAdmin _database_admin){
        super(_graphic, _database_admin);
        itoe = EQUIPMENT_KIND.values();

        //loadItemData("EquipmentItemData");
        loadItemData("EquipmentItemDebugData");
    }

    @Override
    public void loadItemData(String tableName) {
        int size = database.getSize(tableName);

        List<String> name = database.getString(tableName, "name");
        List<String> imageName = database.getString(tableName, "imageName");
        List<Integer> price = database.getInt(tableName, "price");
        List<Integer> equipmentKind = database.getInt(tableName, "equipmentKind");
        List<Integer> useNum = database.getInt(tableName, "useNum");
        List<Integer> radius = database.getInt(tableName, "radius");
        List<Float> decayRate = database.getFloat(tableName, "decayRate");
        List<Integer> touchFrequency = database.getInt(tableName, "touchFrequency");
        List<Float> autoFrequencyRate = database.getFloat(tableName, "autoFrequencyRate");
        List<Integer> attack = database.getInt(tableName, "attack");
        List<Integer> defence = database.getInt(tableName, "defence");


        for (int i = 0; i < size; i++) {
            datas.add(new EquipmentItemData());
            datas.get(i).setItemKind(ITEM_KIND.EQUIPMENT);
            datas.get(i).setName(name.get(i));
            datas.get(i).setImageName(imageName.get(i));
            datas.get(i).setItemImage(graphic.searchBitmap(imageName.get(i)));
            datas.get(i).setPrice(price.get(i));
            datas.get(i).setEquipmentKind(itoe[equipmentKind.get(i)]);
            datas.get(i).setUseNum(useNum.get(i));
            datas.get(i).setRadius(radius.get(i));
            datas.get(i).setDecayRate(decayRate.get(i));
            datas.get(i).setTouchFrequency(touchFrequency.get(i));
            datas.get(i).setAutoFrequencyRate(autoFrequencyRate.get(i));
            datas.get(i).setAttack(attack.get(i));
            datas.get(i).setDefence(defence.get(i));

        }
    }

    public static ItemData getDebugItem(){
        EquipmentItemData debug_item = new EquipmentItemData();
        debug_item.setAttack(57);
        debug_item.setEquipmentKind(EQUIPMENT_KIND.AX);
        debug_item.setImageName("斧A");
        debug_item.setItemImage(graphic.searchBitmap("斧A"));
        debug_item.setItemKind(ITEM_KIND.EXPEND);
        debug_item.setName("金の斧");
        debug_item.setPrice(30239);
        return debug_item;
    }

    public void release() {
        super.release();
        itoe = null;
    }

}
