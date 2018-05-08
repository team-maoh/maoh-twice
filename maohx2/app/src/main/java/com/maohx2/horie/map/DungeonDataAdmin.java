package com.maohx2.horie.map;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horie on 2018/05/01.
 */

public class DungeonDataAdmin {
    public MyDatabase dungeon_database;
    public List<DungeonData> dungeon_data = new ArrayList<>();

    public DungeonDataAdmin(MyDatabaseAdmin _database_admin){
        _database_admin.addMyDatabase("DungeonData", "DungeonData.db", 1, "r");
        dungeon_database = _database_admin.getMyDatabase("DungeonData");
        loadMapData("DungeonData");
    }

    public void loadMapData(String tableName) {
        int size = dungeon_database.getSize(tableName);

        List<String> dungeon_name = dungeon_database.getString(tableName, "dungeon_name");
        List<Integer> mine_max_num = dungeon_database.getInt(tableName, "mine_max_num");
        List<Integer> mine_min_num = dungeon_database.getInt(tableName, "mine_min_num");
        List<Integer> floor_num = dungeon_database.getInt(tableName, "floor_num");
        List<String> floor_tile_name = dungeon_database.getString(tableName, "floor_tile_name");
        List<String> wall_tile_name = dungeon_database.getString(tableName, "wall_tile_name");
        List<String> sidewall_tile_name = dungeon_database.getString(tableName, "sidewall_tile_name");
        List<Integer> map_size_x = dungeon_database.getInt(tableName, "map_size_x");
        List<Integer> map_size_y = dungeon_database.getInt(tableName, "map_size_y");

        for (int i = 0; i < size; i++) {
            dungeon_data.add(new DungeonData());
            dungeon_data.get(i).setDungeon_name(dungeon_name.get(i));
            dungeon_data.get(i).setMine_max_num(mine_max_num.get(i));
            dungeon_data.get(i).setMine_min_num(mine_min_num.get(i));
            dungeon_data.get(i).setFloor_num(floor_num.get(i));
            dungeon_data.get(i).setFloor_tile_name(floor_tile_name.get(i));
            dungeon_data.get(i).setWall_tile_name(wall_tile_name.get(i));
            dungeon_data.get(i).setSidewall_tile_name(sidewall_tile_name.get(i));
            dungeon_data.get(i).setMap_size_x(map_size_x.get(i));
            dungeon_data.get(i).setMap_size_y(map_size_y.get(i));
        }
    }

    public List<DungeonData> getDungeon_data(){
        return dungeon_data;
    }
}
