package com.maohx2.kmhanko.Saver;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;

/**
 * Created by user on 2018/04/08.
 */

public abstract class InventrySaver extends SaveManager {
    protected InventryS inventry;

    public InventrySaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
    }

    public void setInventry(InventryS _inventry) {inventry = _inventry;}

}
