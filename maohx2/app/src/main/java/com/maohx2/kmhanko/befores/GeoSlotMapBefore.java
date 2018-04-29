package com.maohx2.kmhanko.befores;

import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;

/**
 * Created by user on 2018/04/30.
 */

public class GeoSlotMapBefore {

    GeoSlotAdminManager geoSlotMapAdminManager;

    public GeoSlotMapBefore(GeoSlotAdminManager _geoSlotMapAdminManager) {
        geoSlotMapAdminManager = _geoSlotMapAdminManager;
    }

    public void update() {
        geoSlotMapAdminManager.start();
    }

}
