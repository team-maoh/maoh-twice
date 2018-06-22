package com.maohx2.ina.ItemData;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.Saver.InventrySaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

import static com.maohx2.ina.Constants.Inventry.INVENTRY_DATA_MAX;

/**
 * Created by ina on 2018/04/22.
 */

public class EquipmentInventrySaver extends InventrySaver {

    Graphic graphic;
    Constants.Item.EQUIPMENT_KIND[] itoe;


    public EquipmentInventrySaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);

        itoe = Constants.Item.EQUIPMENT_KIND.values();
    }

    public void init(Graphic _graphic){

        graphic = _graphic;
    }


    @Override
    public void dbinit() {
    }

    @Override
    public void save() {

        deleteAll(); //セーブデータをリセットして書き直す場合に呼び出す

        EquipmentItemData equipmentItemData = null;
        for (int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (inventry.getItemData(i) == null) {
                break;
            }

            equipmentItemData = (EquipmentItemData) inventry.getItemData(i);

            database.insertLineByArrayString(
                    "EquipmentInventry",
                    new String[]{"name", "imageName", "price", "equipmentKind", "useNum", "radius", "decayRate", "touchFrequency", "autoFrequencyRate", "attack", "defence", "palettePosition"},
                    new String[]{
                            equipmentItemData.getName(),
                            equipmentItemData.getImageName(),
                            String.valueOf(equipmentItemData.getPrice()),
                            String.valueOf(equipmentItemData.getEquipmentKind()),
                            String.valueOf(equipmentItemData.getUseNum()),
                            String.valueOf(equipmentItemData.getRadius()),
                            String.valueOf(equipmentItemData.getDecayRate()),
                            String.valueOf(equipmentItemData.getTouchFrequency()),
                            String.valueOf(equipmentItemData.getAutoFrequencyRate()),
                            String.valueOf(equipmentItemData.getAttack()),
                            String.valueOf(equipmentItemData.getDefence()),
                            String.valueOf(equipmentItemData.getPalettePosition()),
                    }
            );
        }
    }

    @Override
    public void load() {
        System.out.println("ina " + database.getTables());

        List<String> name = database.getString("EquipmentInventry", "name");
        List<String> imageName = database.getString("EquipmentInventry", "imageName");
        List<Integer> price = database.getInt("EquipmentInventry", "price");
        List<Integer> equipmentKind = database.getInt("EquipmentInventry", "equipmentKind");
        List<Integer> useNum = database.getInt("EquipmentInventry", "useNum");
        List<Integer> radius = database.getInt("EquipmentInventry", "radius");
        List<Float> decayRate = database.getFloat("EquipmentInventry", "decayRate");
        List<Integer> touchFrequency = database.getInt("EquipmentInventry", "touchFrequency");
        List<Float> autoFrequencyRate = database.getFloat("EquipmentInventry", "autoFrequencyRate");
        List<Integer> attack = database.getInt("EquipmentInventry", "attack");
        List<Integer> defence = database.getInt("EquipmentInventry", "defence");
        List<Integer> palettePosition = database.getInt("EquipmentInventry", "palettePosition");

        for (int i = 0; i < name.size(); i++) {

            EquipmentItemData tmpEquipmentItemData = new EquipmentItemData();

            tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
            tmpEquipmentItemData.setName(name.get(i));
            tmpEquipmentItemData.setImageName(imageName.get(i));
            tmpEquipmentItemData.setItemImage(graphic.searchBitmap(imageName.get(i)));
            tmpEquipmentItemData.setPrice(price.get(i));
            tmpEquipmentItemData.setEquipmentKind(itoe[equipmentKind.get(i)]);
            tmpEquipmentItemData.setUseNum(useNum.get(i));
            tmpEquipmentItemData.setRadius(radius.get(i));
            tmpEquipmentItemData.setDecayRate(decayRate.get(i));
            tmpEquipmentItemData.setTouchFrequency(touchFrequency.get(i));
            tmpEquipmentItemData.setAutoFrequencyRate(autoFrequencyRate.get(i));
            tmpEquipmentItemData.setAttack(attack.get(i));
            tmpEquipmentItemData.setDefence(defence.get(i));
            tmpEquipmentItemData.setPalettePosition(palettePosition.get(i));

            inventry.addItemData(tmpEquipmentItemData);
        }
    }

    @Override
    public void onUpgrade(int oldVersion, int newVersion) {

    };
    @Override
    public void onDowngrade (int oldVersion, int newVersion) {

    };

}