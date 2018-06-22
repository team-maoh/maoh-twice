package com.maohx2.horie.EquipTutorial;

import com.maohx2.kmhanko.Saver.SaveManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.List;

/**
 * Created by horie on 2018/06/12.
 */

public class EquipTutorialSaver extends SaveManager {
    EquipTutorialSaveData equip_tutorial_save_data;

    public EquipTutorialSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode, EquipTutorialSaveData _equip_tutorial_save_data) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
        equip_tutorial_save_data = _equip_tutorial_save_data;
    }
    @Override
    public void dbinit() {
        //特にすることなし
    }

    @Override
    public void load() {
        List<Integer> is_tutprial_finished = database.getInt("EquipTutorial", "is_tutorial_finished");
        equip_tutorial_save_data.setTutorialFinishStatus(is_tutprial_finished.get(0));
    }

    @Override
    public void save() {
        deleteAll();
        database.insertLineByArray(
                "EquipTutorial",
                new String[]{"is_tutorial_finished"},
                equip_tutorial_save_data.getSaveStatus()
            );
    }

    @Override
    public void onUpgrade(int oldVersion, int newVersion) {
    };
    @Override
    public void onDowngrade (int oldVersion, int newVersion) {
    };
}
