package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.pow;


//import com.maohx2.ina.MySprite;
// import com.maohx2.ina.waste.MySprite;

import java.util.Random;
import com.maohx2.ina.waste.MySprite;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapEnemy extends MapUnit {

    MapPlayer player;
    int STEP;
    double dst_x = 100, dst_y = 100;

    @Override
    public void init(SurfaceHolder holder, Bitmap draw_object, MapObjectAdmin map_object_admin) {
        super.init(holder, draw_object, map_object_admin);
        STEP = 10;
        Random random = new Random();
        x = random.nextDouble() * 1000;
        y = random.nextDouble() * 600;
    }

    @Override
    public void init(GL10 gl, MySprite draw_object, MapObjectAdmin map_object_admin) {
        super.init(gl,draw_object, map_object_admin);
        STEP = 10;
        Random random = new Random();
        x = random.nextDouble() * 1000;
        y = random.nextDouble() * 600;
    }

    @Override
    public void update(double touch_x, double touch_y,TouchState touch_state) {

        player = map_object_admin.getPlayer();

        dst_x = player.x;//getMapX(),getMapY()はMapObject内部に記述されている
        dst_y = player.y;

        dst_steps = (int) (pow(pow(dst_x - x, 2.0) + pow(dst_y - y, 2.0), 0.5) / (double) STEP);
        dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので

        dx = (int) ((dst_x - x) / dst_steps);
        dy = (int) ((dst_y - y) / dst_steps);
        x += dx;
        y += dy;
    }
}