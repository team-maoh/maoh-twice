package com.maohx2.kmhanko.Saver;

import com.maohx2.ina.Draw.Graphic;
import java.util.ArrayList;
import java.util.List;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;

/**
 * Created by user on 2018/04/15.
 */

public class GeoSlotSaver extends SaveManager {

    public GeoSlotSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }
}
