package com.maohx2.ina.ItemData;

import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectDataAdmin;

/**
 * Created by user on 2017/11/19.
 */

/*
package itemdata
ItemDataAdminManagerだけインスタンス化すれば、他は全てインスタンス化される。
#init(DBadmin)
#get**Admin().からItemDataを取得する種々のメソッドにアクセスできる。
*/

public class ItemDataAdminManager {
    ExpendItemDataAdmin expendItemDataAdmin;
    GeoObjectDataAdmin geoObjectDataAdmin;

    public void init(MyDatabaseAdmin databaseAdmin) {
        expendItemDataAdmin = new ExpendItemDataAdmin();
        expendItemDataAdmin.init(databaseAdmin);

        geoObjectDataAdmin = new GeoObjectDataAdmin();
        geoObjectDataAdmin.init(databaseAdmin);
    }

    public ExpendItemDataAdmin getExpendItemDataAdmin() {
        return expendItemDataAdmin;
    }

    public GeoObjectDataAdmin getGeoObjectDataAdmin() {
        return geoObjectDataAdmin;
    }
}
