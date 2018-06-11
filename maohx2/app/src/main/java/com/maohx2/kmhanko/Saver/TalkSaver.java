package com.maohx2.kmhanko.Saver;

import java.util.List;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import com.maohx2.kmhanko.Talking.TalkSaveDataAdmin;

/**
 * Created by user on 2018/06/12.
 */

public class TalkSaver extends SaveManager {

    TalkSaveDataAdmin talkSaveDataAdmin;

    public TalkSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
    }
    public void setTalkSaveDataAdmin(TalkSaveDataAdmin _talkSaveDataAdmin) {
        talkSaveDataAdmin = _talkSaveDataAdmin;
    }

    @Override
    public void dbinit() {
        //特にすることなし
    }

    @Override
    public void save() {
        deleteAll();
        for (int i = 0 ; i < talkSaveDataAdmin.getSize(); i++) {
            database.insertLineByArray(
                    "TalkSave",
                    new String[]{"talk_name", "flag"},
                    new String[]{
                            talkSaveDataAdmin.getTalkName(i),
                            talkSaveDataAdmin.getTalkFlagByString(i)
                    }
            );
        }
    }

    @Override
    public void load() {
        talkSaveDataAdmin.setTalkNames(
                database.getString("TalkSave", "talk_name")
        );
        talkSaveDataAdmin.setTalkFlags(
                database.getBoolean("TalkSave", "flag")
        );
    }

}
