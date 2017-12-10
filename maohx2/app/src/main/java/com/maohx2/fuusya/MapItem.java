package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;


//import com.maohx2.ina.MySprite;

import com.maohx2.ina.Draw.Graphic;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapItem extends MapObject {

    MapObjectAdmin map_object_admin;

    public MapItem(Graphic graphic, MapObjectAdmin _map_object_admin, int _id) {
        super(graphic, _map_object_admin);

        id = _id;

        map_object_admin = _map_object_admin;

//        draw_object = "apple";

        switch (id) {
            case 0:
                draw_object = "grape";
            case 1:
                draw_object = "grape";
            case 2:
                draw_object = "banana";
//            case 3:
//                draw_object = "watermelon";
            default:
                draw_object = "apple";
        }

        Random random = new Random();
        x = random.nextDouble() * 1000;//アイテムが発生する座標
        y = random.nextDouble() * 1300;
    }

    public void init() {
        super.init();

    }

//    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin _map_object_admin) {
//        super.init(gl, draw_object);
//
//        map_object_admin = _map_object_admin;
//
//        Random random = new Random();
//        x = random.nextDouble() * 1000;//アイテムが発生する座標
//        y = random.nextDouble() * 1300;
//    }

    @Override
    public void update() {
        super.update();
    }

    //setIdという名前なのにset以上のことをしているのはおかしいので後で何とかする
    public void setId(int _id) {

        id = _id;
//
//        switch (id){
//            case 1:
//                draw_object = "apple";
//            case 2:
//                draw_object = "grape";
//            case 3:
//                draw_object = "banana";
//            case 4:
//                draw_object = "watermelon";
//        }
    }
}
