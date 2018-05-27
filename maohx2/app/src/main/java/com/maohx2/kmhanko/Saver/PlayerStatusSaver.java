package com.maohx2.kmhanko.Saver;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

/**
 * Created by user on 2018/04/22.
 */

public class PlayerStatusSaver extends SaveManager {

    PlayerStatus playerStatus;

    public PlayerStatusSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
    }

    public void setPlayerStatus(PlayerStatus _playerStatus) {
        playerStatus = _playerStatus;
    }

    @Override
    public void dbinit() {
        //特にすることなし
    }


    @Override
    public void load() {
        playerStatus.setBaseHP(database.getOneInt("BaseStatus", "hp", "rowid = 1"));
        playerStatus.setBaseAttack(database.getOneInt("BaseStatus", "attack", "rowid = 1"));
        playerStatus.setBaseDefence(database.getOneInt("BaseStatus", "defence", "rowid = 1"));
        playerStatus.setBaseLuck(database.getOneInt("BaseStatus", "luck", "rowid = 1"));
        playerStatus.setLevel(database.getOneInt("BaseStatus", "level", "rowid = 1"));
        playerStatus.setMoney(database.getOneInt("BaseStatus", "money", "rowid = 1"));
        playerStatus.setMaohWinCount(database.getOneInt("BaseStatus", "maoh_win_count", "rowid = 1"));
        playerStatus.setClearCount(database.getOneInt("BaseStatus", "clear_count", "rowid = 1"));
        playerStatus.setNowClearCount(database.getOneInt("BaseStatus", "now_clear_count", "rowid = 1"));
        playerStatus.setTutorialInDungeon(database.getOneInt("BaseStatus", "tutorial_in_dungeon", "rowid = 1"));
        int test = database.getOneInt("BaseStatus", "tutorial_in_dungeon", "rowid = 1");
        int test2 = 2;
    }

    @Override
    public void save() {
        deleteAll();
        database.insertLineByArray(
                "BaseStatus",
                new String[] { "level", "hp", "attack", "defence", "luck", "money", "maoh_win_count", "clear_count", "now_clear_count", "tutorial_in_dungeon"},
                playerStatus.getSaveStatuses()
        );
    }

}
