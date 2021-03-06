package com.maohx2.fuusya;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by Fuusya on 2018/05/01.
 */


public class MapMine extends MapInanimate {

    //Playerに対する当たり判定の半径
    double REACH_FOR_PLAYER = 75;

    public MapMine(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera) {
        super(graphic, _map_object_admin, _id, _camera);

        exists = false;

    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

        if (exists == true) {

            if (player.isWithinReach(w_x, w_y, REACH_FOR_PLAYER) == true) {
                System.out.println("採掘スポットに接触");
                //map_object_admin.mineStart();
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

