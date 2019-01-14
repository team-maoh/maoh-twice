package com.maohx2.fuusya;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GameSystem.DungeonModeManage;

/**
 * Created by Fuusya on 2018/05/01.
 */


public class MapMine extends MapInanimate {

    //Playerに対する当たり判定の半径
    double REACH_FOR_PLAYER = 150;
    DungeonModeManage dungeon_mode_manage;
    boolean skipFlag;

    public MapMine(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, DungeonModeManage _dungeon_mode_manage) {
        super(graphic, _map_object_admin, _id, _camera);

        exists = false;
        dungeon_mode_manage = _dungeon_mode_manage;
        skipFlag = false;

    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

        if (exists == true) {

            if (player.isWithinReach(w_x, w_y, REACH_FOR_PLAYER) == true) {
                if (skipFlag == false) {
                    System.out.println("採掘スポットに接触");
                    if (dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE && dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT) {
                        map_object_admin.gotoMiningWindowActivate(this);
                    }
                    skipFlag = true;
                }
            } else {
                skipFlag = false;
            }

        }

    }

    public void setId(int _id) {

        id = _id;
    }

    public static class MapInventryAdmin {
    }
}

