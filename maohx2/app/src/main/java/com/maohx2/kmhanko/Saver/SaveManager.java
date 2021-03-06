package com.maohx2.kmhanko.Saver;

import java.util.ArrayList;
import java.util.List;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;

/**
 * Created by user on 2018/04/01.
 */

public abstract class SaveManager {

    protected MyDatabaseAdmin databaseAdmin;
    protected MyDatabase database;
    protected String loadMode;

    //loadMode = ds or s　dsの場合はAssetsからコピーされる
    public SaveManager(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        databaseAdmin = _databaseAdmin;
        loadMode = _loadMode;
        databaseAdmin.addMyDatabase(dbName, dbAsset, version, loadMode);
        database = databaseAdmin.getMyDatabase(dbName);
        if (!loadMode.equals("s") && !loadMode.equals("ds") && !loadMode.equals("ns")) {
            throw new Error("☆タカノ SaveManager#SaveManager loadMode must be ds or s or ns");
        }
        if (database.isNew()) {
            dbinit();
        }
    }

    public void init() {};
    abstract public void dbinit();//DBがもともと存在せず、saveフォルダからセーブデータをコピーした時に呼ばれる。loadmode = dsの時は呼ばれない
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

    public void deleteTableAll() {
        database.deleteTableAll();
    }
}
