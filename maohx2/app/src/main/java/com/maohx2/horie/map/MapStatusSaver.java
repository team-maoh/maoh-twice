package com.maohx2.horie.map;

import com.maohx2.kmhanko.Saver.SaveManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by horie on 2018/05/12.
 */

public class MapStatusSaver extends SaveManager {
    MapStatus map_status;
    int stage_num;

    public MapStatusSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode, MapStatus _map_status, int _stage_num) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
        map_status = _map_status;
        stage_num = _stage_num;
    }
    @Override
    public void dbinit() {
        //特にすることなし
    }

    @Override
    public void load() {
        List<Integer> is_clear = database.getInt("MapStatus", "is_clear");
        for(int i = 0;i < stage_num;i++) {
            map_status.setMapStatus(is_clear.get(i), i);
        }
    }

    @Override
    public void save() {
        for (int i = 0; i < stage_num; i++) {
            database.insertLineByArray(
                    "MapStatus",
                    new String[]{"is_clear"},
                    map_status.getSaveStatus(i)
            );
        }
    }
}
