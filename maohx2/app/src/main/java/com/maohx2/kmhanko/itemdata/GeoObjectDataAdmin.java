package com.maohx2.kmhanko.itemdata;


import com.maohx2.ina.ItemData.ItemDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import java.util.List;

/**
 * Created by user on 2017/11/05.
 */

public class GeoObjectDataAdmin extends ItemDataAdmin<GeoObjectData> {

    public GeoObjectDataAdmin() {
        super();
        dbName = "GeoObjectDataDB";
        dbAsset = "GeoObjectDataDB.db";
        tableName = "debug";
    }

    @Override
    public void loadItemData(String table_name) {
        int size = database.getSize(table_name);
        List<Integer> hp = database.getInt(table_name, "hp");
        List<Integer> attack = database.getInt(table_name, "attack");
        List<Integer> defence = database.getInt(table_name, "defence");
        List<Integer> luck = database.getInt(table_name, "luck");
        List<Integer> hpRate = database.getInt(table_name, "hp_rate");
        List<Integer> attackRate = database.getInt(table_name, "attack_rate");
        List<Integer> defenceRate = database.getInt(table_name, "defence_rate");
        List<Integer> luckRate = database.getInt(table_name, "luck_rate");

        int cnt;
        for (int i = 0; i < size; i++) {
            datas.add(new GeoObjectData());
            cnt = datas.size() - 1;
            datas.get(cnt).setHp(hp.get(i));
            datas.get(cnt).setAttack(attack.get(i));
            datas.get(cnt).setDefence(defence.get(i));
            datas.get(cnt).setLuck(luck.get(i));
            datas.get(cnt).setHpRate(hpRate.get(i));
            datas.get(cnt).setAttackRate(attackRate.get(i));
            datas.get(cnt).setDefenceRate(defenceRate.get(i));
            datas.get(cnt).setLuckRate(luckRate.get(i));
        }

        //呼び忘れないように注意
        super.loadItemData(table_name);
    }

}