package com.maohx2.ina.GameSystem;

import static com.maohx2.ina.Constants.GAMESYSTEN_MODE.*;

/**
 * Created by ina on 2018/04/01.
 */

public class DungeonModeManage {

    DUNGEON_MODE mode;

    public DungeonModeManage() {
        mode = DUNGEON_MODE.MAP;
    }

    public DUNGEON_MODE getMode() {
        return mode;
    }

    public void setMode(DUNGEON_MODE mode) {
        this.mode = mode;
    }

    public void release() {
        System.out.println("takanoRelease : DungeonModeManage");
        mode = null;
    }
}
