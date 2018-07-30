package com.maohx2.ina.ItemData;

import com.maohx2.ina.Draw.Graphic;
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

    public void init(MyDatabaseAdmin databaseAdmin, Graphic _graphic) {
        expendItemDataAdmin = new ExpendItemDataAdmin(_graphic, databaseAdmin);

        geoObjectDataAdmin = new GeoObjectDataAdmin(_graphic, databaseAdmin);
    }

    public ExpendItemDataAdmin getExpendItemDataAdmin() {
        return expendItemDataAdmin;
    }

    public GeoObjectDataAdmin getGeoObjectDataAdmin() {
        return geoObjectDataAdmin;
    }

    public void release() {
        System.out.println("takanoRelease : ItemDataAdminManager");
        if (expendItemDataAdmin != null) {
            expendItemDataAdmin.release();
        }
        if (geoObjectDataAdmin != null) {
            geoObjectDataAdmin.release();
        }
        expendItemDataAdmin = null;
        geoObjectDataAdmin = null;
    }

}
