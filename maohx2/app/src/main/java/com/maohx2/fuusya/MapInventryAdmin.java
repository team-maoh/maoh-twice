package com.maohx2.fuusya;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Constants;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.ItemData.EquipmentInventrySaver;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.ina.Constants.Item.*;//こう書かないとswitchの比較時に赤線が出る

public class MapInventryAdmin {

    GlobalData globalData;
    GeoInventrySaver geoItemInventrySaver;
    InventryS geoItemInventry;
    ExpendItemInventrySaver expendItemInventrySaver;
    InventryS expendItemInventry;
    EquipmentInventrySaver equipmentInventrySaver;
    InventryS equipmentInventry;
    //
    Inventry inventry;

    MapPlayer player;
    MapPlateAdmin map_plate_admin;

    int debug_count;

    public MapInventryAdmin(GlobalData _globalData, Inventry _inventry, MapObjectAdmin map_object_admin, MapPlateAdmin _map_plate_admin) {

        globalData = _globalData;
        geoItemInventrySaver = globalData.getGeoInventrySaver();
        geoItemInventry = globalData.getGeoInventry();
        expendItemInventrySaver = globalData.getExpendItemInventrySaver();
        expendItemInventry = globalData.getExpendItemInventry();
        equipmentInventry = globalData.getEquipmentInventry();
        equipmentInventrySaver = globalData.getEquipmentInventrySaver();
        inventry = _inventry;

        player = map_object_admin.getPlayer();
        map_plate_admin = _map_plate_admin;
        map_plate_admin.setMapInventryAdmin(this);

        debug_count = 300;

    }

    public void init() {

    }

    public void update() {

//        System.out.println("dededede" + debug_count);
//        if (debug_count > 0) {
//            debug_count--;
//
//        } else if(debug_count == 0){
//
////            int obtained_item_num = map_plate_admin.getObtainedItemNum();
//            storageMapInventry(map_plate_admin.getObtainedItemNum());
//
//            debug_count = -1;
//        }


//        if (map_plate_admin.getWillStorageInventry()) {
//
//            storageMapInventry(map_plate_admin.getObtainedItemNum());
//            map_plate_admin.setWillStorageInventry(false);
//        }

    }

    public void storageMapInventry() {

        for (int i = 0; i < Constants.Inventry.INVENTRY_DATA_MAX; i++) {
            ItemData tmp_item_data = inventry.getItemData(i);

            if (tmp_item_data == null) {
                break;
            } else {

                ITEM_KIND tmp_item_kind = tmp_item_data.getItemKind();

                switch (tmp_item_kind) {
                    case EXPEND:
                        expendItemInventry.addItemData(tmp_item_data);

                        break;
                    case GEO:
                        geoItemInventry.addItemData(tmp_item_data);

                        break;
                    case EQUIPMENT:
                        equipmentInventry.addItemData(tmp_item_data);

                        break;
                    default:

                        break;
                }
            }

        }

        equipmentInventry.save();
        equipmentInventrySaver.save();
        geoItemInventry.save();
        geoItemInventrySaver.save();
        expendItemInventry.save();
        expendItemInventrySaver.save();

    }

}
