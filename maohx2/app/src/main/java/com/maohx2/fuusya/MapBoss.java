package com.maohx2.fuusya;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonModeManage;

import static android.R.attr.name;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Fuusya on 2018/05/01.
 */

public class MapBoss extends MapInanimate {

    //Playerに対する当たり判定の半径
    double REACH_FOR_PLAYER = 100;
    BattleUnitAdmin battle_unit_admin;
    DungeonModeManage dungeon_mode_manage;

    public MapBoss(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, BattleUnitAdmin _battle_unit_admin, DungeonModeManage _dungeon_mode_manage) {
        super(graphic, _map_object_admin, _id, _camera);

        battle_unit_admin = _battle_unit_admin;
        dungeon_mode_manage = _dungeon_mode_manage;

        exists = false;

    }

    public void init() {
        super.init();

    }

    public void openingUpdate(){

        w_x -= 80;

    }

    public void update() {
        super.update();

        if (exists == true) {

            if (player.isWithinReach(w_x, w_y, REACH_FOR_PLAYER) == true) {
                System.out.println("ボスに接触");
//                sound_admin.play("getItem");
                exists = false;

//                String[] tmp_boss = new String[1];
//
//                tmp_boss[0] = map_admin.getMonsterName(2)[0];

                battle_unit_admin.reset(BattleUnitAdmin.MODE.BOSS);
                battle_unit_admin.spawnEnemy(name);
                dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT);

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

    public void putBoss(double _w_x, double _w_y){
        w_x = _w_x;
        w_y = _w_y;

    }


}
