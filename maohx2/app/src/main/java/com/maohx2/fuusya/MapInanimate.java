package com.maohx2.fuusya;

import android.graphics.Point;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Draw.Graphic;

//import com.maohx2.ina.MySprite;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapInanimate extends MapObject {

    public MapInanimate(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera) {
        super(graphic, _map_object_admin, _camera);

        id = _id;

    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

    }

    public void setId(int _id) {

        id = _id;
    }
}
