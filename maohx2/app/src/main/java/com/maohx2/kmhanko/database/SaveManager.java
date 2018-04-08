package com.maohx2.kmhanko.database;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2018/04/01.
 */

public abstract class SaveManager {

    MyDatabaseAdmin databaseAdmin;
    MyDatabase database;
    String loadMode;

    //loadMode = ds or s　dsの場合はAssetsからコピーされる
    public SaveManager(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        databaseAdmin = _databaseAdmin;
        loadMode = _loadMode;
        databaseAdmin.addMyDatabase(dbName, dbAsset, version, loadMode);
        database = databaseAdmin.getMyDatabase(dbName);
        if (!loadMode.equals("s") && !loadMode.equals("ds")) {
            throw new Error("☆タカノ SaveManager#SaveManager loadMode must be ds or s");
        }
    }

    abstract public void save();
    abstract public void load();

    public void deleteAll() {
        List<String> tables = database.getTables();
        for(int i = 0; i < tables.size(); i++) {
            database.delete(tables.get(i),null);
        }
    }
    public void delete(String t_name) {
        database.delete(t_name,null);
    }
}
