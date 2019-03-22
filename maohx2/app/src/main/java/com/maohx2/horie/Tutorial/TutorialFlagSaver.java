package com.maohx2.horie.Tutorial;

import com.maohx2.kmhanko.Saver.SaveManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by horie on 2018/07/31.
 */

public class TutorialFlagSaver extends SaveManager {
   TutorialFlagData tutorial_flag_data;
   int size;

    public TutorialFlagSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode, TutorialFlagData _tutorial_flag_data) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
        tutorial_flag_data = _tutorial_flag_data;
    }
    @Override
    public void dbinit() {
        //特にすることなし
    }

    @Override
    public void load() {
        List<String> flag_name = database.getString("TutorialFlag", "flag_name");
        List<Integer> is_tutorial_finished = database.getInt("TutorialFlag", "tutorial_flag");
        size = flag_name.size();
        for(int i = 0; i < size; i++) {
            tutorial_flag_data.setFlag_name(flag_name.get(i), i);
            tutorial_flag_data.setIs_tutorial_finished(is_tutorial_finished.get(i), i);
        }
    }

    @Override
    public void save() {
        deleteAll();
        for(int i = 0;i < size;i++) {
            database.insertLineByArray(
                    "TutorialFlag",
                    new String[]{"flag_name", "tutorial_flag"},
                    tutorial_flag_data.getSaveStatuses(i)
            );
        }
    }

    @Override
    public void onUpgrade(int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            database.insertLineByArray(
                    "TutorialFlag",
                    new String[]{"flag_name", "tutorial_flag"},
                    new String[] { "geoLava", "false" }
            );
            database.insertLineByArray(
                    "TutorialFlag",
                    new String[]{"flag_name", "tutorial_flag"},
                    new String[] { "dungeon", "false" }
            );
            database.insertLineByArray(
                    "TutorialFlag",
                    new String[]{"flag_name", "tutorial_flag"},
                    new String[] { "battle", "false" }
            );
            database.insertLineByArray(
                    "TutorialFlag",
                    new String[]{"flag_name", "tutorial_flag"},
                    new String[] { "mining", "false" }
            );

        }
    };
    @Override
    public void onDowngrade (int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            database.delete("TutorialFlag", "flag_name == geoLava");
            database.delete("TutorialFlag", "flag_name == dungeon");
            database.delete("TutorialFlag", "flag_name == battle");
            database.delete("TutorialFlag", "flag_name == mining");
        }
    }
}
