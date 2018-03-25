package com.maohx2.kmhanko.itemdata;


import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import java.util.List;

/**
 * Created by user on 2017/11/05.
 */

public class GeoObjectDataAdmin extends ItemDataAdmin<GeoObjectData> {

    public GeoObjectDataAdmin(Graphic _graphic, MyDatabaseAdmin databaseAdmin) {
        super(_graphic, databaseAdmin);
        loadItemData("DefaultGeoObjectData");
    }

    @Override
    public void loadItemData(String table_name) {
        int size = database.getSize(table_name);
        List<String> name = database.getString(table_name, "name");
        List<String> imageName = database.getString(table_name, "image_name");
        List<Integer> price = database.getInt(table_name, "price");
        List<Integer> hp = database.getInt(table_name, "hp");
        List<Integer> attack = database.getInt(table_name, "attack");
        List<Integer> defence = database.getInt(table_name, "defence");
        List<Integer> luck = database.getInt(table_name, "luck");
        List<Integer> hpRate = database.getInt(table_name, "hp_rate");
        List<Integer> attackRate = database.getInt(table_name, "attack_rate");
        List<Integer> defenceRate = database.getInt(table_name, "defence_rate");
        List<Integer> luckRate = database.getInt(table_name, "luck_rate");

        for (int i = 0; i < size; i++) {
            datas.add(new GeoObjectData());
            datas.get(i).setName(name.get(i));
            datas.get(i).setImageName(imageName.get(i));
            datas.get(i).setPrice(price.get(i));
            datas.get(i).setHp(hp.get(i));
            datas.get(i).setAttack(attack.get(i));
            datas.get(i).setDefence(defence.get(i));
            datas.get(i).setLuck(luck.get(i));
            datas.get(i).setHpRate(hpRate.get(i));
            datas.get(i).setAttackRate(attackRate.get(i));
            datas.get(i).setDefenceRate(defenceRate.get(i));
            datas.get(i).setLuckRate(luckRate.get(i));

            datas.get(i).setItemImage(graphic.searchBitmap(imageName.get(i)));
        }
    }


    static public GeoObjectData getDebugGeoObjectData(int i) {
        GeoObjectData[] buf = new GeoObjectData[]{
                new GeoObjectData(
                        graphic,
                        new int[]{100, 0, 0, 0},
                        new double[]{1.0, 1.0, 1.0, 1.0}
                ),
                new GeoObjectData(
                        graphic,
                        new int[]{0, 50, 0, 0},
                        new double[]{1.0, 1.0, 1.0, 1.0}
                ),
                new GeoObjectData(
                        graphic,
                        new int[]{0, 0, 30, 0},
                        new double[]{1.0, 1.0, 1.0, 1.0}
                ),
                new GeoObjectData(
                        graphic,
                        new int[]{0, 0, 0, 20},
                        new double[]{1.0, 1.0, 1.0, 1.0}
                ),
                new GeoObjectData(
                        graphic,
                        new int[]{0, 0, 0, 0},
                        new double[]{2.0, 1.0, 1.0, 1.0}
                ),
                new GeoObjectData(
                        graphic,
                        new int[]{0, 0, 0, 0},
                        new double[]{1.0, 3.0, 1.0, 1.0}
                ),
                new GeoObjectData(
                        graphic,
                        new int[]{0, 0, 0, 0},
                        new double[]{1.0, 1.0, 2.0, 1.0}
                ),
                new GeoObjectData(
                        graphic,
                        new int[]{0, 0, 0, 0},
                        new double[]{1.0, 1.0, 0.0, 5.0}
                )
        };
        return buf[i];
    }
}