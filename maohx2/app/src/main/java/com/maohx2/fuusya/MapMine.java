package com.maohx2.fuusya;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonModeManage;

/**
 * Created by Fuusya on 2018/05/01.
 */


public class MapMine extends MapInanimate {

    //Playerに対する当たり判定の半径
    double REACH_FOR_PLAYER = 150;
    DungeonModeManage dungeon_mode_manage;

    public MapMine(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, DungeonModeManage _dungeon_mode_manage) {
        super(graphic, _map_object_admin, _id, _camera);

        exists = false;
        dungeon_mode_manage = _dungeon_mode_manage;

    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

        if (exists == true) {

            if (player.isWithinReach(w_x, w_y, REACH_FOR_PLAYER) == true) {
                System.out.println("採掘スポットに接触");
                if (dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE && dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT) {
                    //戦闘画面では「罠だ！」のTextBoxを出さない
                    map_object_admin.eraseEffectBox();
                    dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING_INIT);
                    exists = false;
                }

//                sound_admin.play("getItem");
//                exists = false;
//            bag_Item_admin.setItemIdToBagItem(map_Item[i].getId());//アイテムidを引き渡す
//            map_Item[i].setExists(false);
            }

        }

    }

    public void setId(int _id) {

        id = _id;
    }

    public static class MapInventryAdmin {
    }
}

