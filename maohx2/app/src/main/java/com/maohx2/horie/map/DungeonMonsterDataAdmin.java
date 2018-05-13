package com.maohx2.horie.map;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horie on 2018/05/06.
 */

public class DungeonMonsterDataAdmin {
    public MyDatabase dungeon_monster_database;
    public List<DungeonMonsterData> dungeon_monster_data = new ArrayList<>();

    public DungeonMonsterDataAdmin(MyDatabaseAdmin _monster_database_admin, String table_name) {
        _monster_database_admin.addMyDatabase("DungeonMonsterData", "DungeonData.db", 1, "r");
        dungeon_monster_database = _monster_database_admin.getMyDatabase("DungeonMonsterData");
        loadMonsterData(table_name);
    }

    private void loadMonsterData(String table_name) {
        int size = dungeon_monster_database.getSize(table_name);

        List<String> monster_name = dungeon_monster_database.getString(table_name, "monster_name");
        List<Integer> type = dungeon_monster_database.getInt(table_name, "type");

        for (int i = 0; i < size; i++) {
            dungeon_monster_data.add(new DungeonMonsterData());
            dungeon_monster_data.get(i).setMonsterName(monster_name.get(i));
            dungeon_monster_data.get(i).setType(type.get(i));
        }
    }

    public List<DungeonMonsterData> getDungeon_monster_data(){
        return dungeon_monster_data;
    }
}
