package com.maohx2.kmhanko.itemshop;

import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataAdmin;


import java.util.List;

/**
 * Created by user on 2017/11/19.
 */

public class GeoObjectShopData extends ItemShopData<GeoObjectData> {
    GeoObjectDataAdmin geoObjectDataAdmin;

    public GeoObjectShopData() {
        super();
        dbName = "GeoObjectShopDataDB";
        dbAsset = "GeoObjectShopDataDB.db";
    }

    public void init(GeoObjectDataAdmin _geoObjectDataAdmin) {
        geoObjectDataAdmin = _geoObjectDataAdmin;
    }

    public void init(GeoObjectDataAdmin _geoObjectDataAdmin, MyDatabaseAdmin database_admin) {
        setDatabase(database_admin);
        this.init(_geoObjectDataAdmin);
    }

    @Override
    public void loadShopData(String table_name) {
        List<String> names = database.getString(table_name, "item_name");
        datas = geoObjectDataAdmin.getDatasByNames(names);
    }
}
