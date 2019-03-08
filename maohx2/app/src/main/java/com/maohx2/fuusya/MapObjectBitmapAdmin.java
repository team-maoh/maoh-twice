package com.maohx2.fuusya;

import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2019/03/08.
 */

public class MapObjectBitmapAdmin {

    MapObjectBitmap[] mapObjectBitmap = new MapObjectBitmap[512];
    int mapObjectBitmapIndex;

    public MapObjectBitmapAdmin() {
        mapObjectBitmapIndex = 0;
    }

    public MapObjectBitmap addMapObjectBitmap(int _total_dirs, Graphic _graphic, String _object_name) {
        MapObjectBitmap tempMapObjectBitmap;
        if ((tempMapObjectBitmap = this.searchMapObjectBitmap(_object_name)) != null) {
            return tempMapObjectBitmap;
        }

        if (mapObjectBitmapIndex >= mapObjectBitmap.length) {
            throw new Error("MapObjectBitmapAdmin: addMapObjectBitmap: 配列オーバー");
        }

        mapObjectBitmap[mapObjectBitmapIndex] = new MapObjectBitmap(_total_dirs, _graphic, _object_name);
        tempMapObjectBitmap = mapObjectBitmap[mapObjectBitmapIndex];
        mapObjectBitmapIndex++;

        return tempMapObjectBitmap;
    }

    public MapObjectBitmap searchMapObjectBitmap(String objectName) {
        for (int i = 0; i < mapObjectBitmap.length; i++) {
            if (mapObjectBitmap[i] != null) {
                if (mapObjectBitmap[i].getObjectName() != null) {
                    if (objectName.equals(mapObjectBitmap[i].getObjectName())) {
                        return mapObjectBitmap[i];
                    }
                }
            }
        }
        return null;
    }

    public void release() {
        for (int i = 0; i < mapObjectBitmap.length; i++) {
            if (mapObjectBitmap[i] != null) {
                mapObjectBitmap[i].release();
            }
        }
        mapObjectBitmap = null;
    }

}
