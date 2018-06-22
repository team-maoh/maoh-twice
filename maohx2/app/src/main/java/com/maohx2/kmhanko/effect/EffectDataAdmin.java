package com.maohx2.kmhanko.effect;

/**
 * Created by user on 2018/01/19.
 */

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;
import java.util.ArrayList;

//EffectDataの実体を持つためのクラス
public class EffectDataAdmin {
    List<EffectData> effectData = new ArrayList<EffectData>();

    MyDatabaseAdmin databaseAdmin;
    MyDatabase database;

    List<String> tables;

    public EffectDataAdmin(MyDatabaseAdmin _databaseAdmin) {
        databaseAdmin = _databaseAdmin;
        databaseAdmin.addMyDatabase("EffectData","EffectData.db",1,"r");
        database = databaseAdmin.getMyDatabase("EffectData");
        loadDatabase();//必ず初めに呼ぶ
    }

    private void loadDatabase() {
        tables = database.getTables();
        for(int i = 0; i < tables.size(); i++) {
            effectData.add(new EffectData(database, tables.get(i)));
        }
    }

    public EffectData getEffectData(String name) {
        for(int i = 0; i < tables.size(); i++) {
            if (effectData.get(i).getName().equals(name)) {
                return effectData.get(i);
            }
        }
        return null;
    }


    public void release() {
        for (int i = 0; i < effectData.size(); i++) {
            effectData.get(i).release();
        }
        effectData.clear();
        effectData = null;

        tables.clear();
        tables = null;
    }




}
