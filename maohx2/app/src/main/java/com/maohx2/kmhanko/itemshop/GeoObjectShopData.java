package com.maohx2.kmhanko.itemshop;


import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataAdmin;


import java.util.List;

/**
 * Created by user on 2017/11/19.
 */

public class GeoObjectShopData extends ItemShopData<GeoObjectData> {
    GeoObjectDataAdmin geoObjectDataAdmin;

    public GeoObjectShopData(Graphic graphic, MyDatabaseAdmin my_database_admin) {
        super(graphic, my_database_admin);
        dbName = "GeoObjectShopDataDB";
        dbAsset = "GeoObjectShopDataDB.db";
        setDatabase();
    }

    public void setGeoObjectDataAdmin(GeoObjectDataAdmin _geoObjectDataAdmin) {
        geoObjectDataAdmin = _geoObjectDataAdmin;
    }

    @Override
    public void loadShopData(String table_name) {
        List<String> names = database.getString(table_name, "item_name");
        datas = geoObjectDataAdmin.getDatasByNames(names);
    }
}
