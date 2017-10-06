package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.horie.map.MapAdmin;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ina on 2017/09/05.
 */

public class GameSystem {

    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;
    SurfaceHolder holder;

    public void init(SurfaceHolder m_holder, Bitmap neco, Bitmap apple, Bitmap banana,Bitmap grape, Bitmap watermelon, Bitmap slime, Activity activity, Point display_size) {
        map_admin = new MapAdmin(m_holder, activity, display_size);
        map_object_admin = new MapObjectAdmin();
        map_object_admin.init(m_holder, neco, apple, banana,grape,watermelon, slime, map_admin);//MapObjectAdmin.javaのinitを実行
        holder = m_holder;
    }

    public void init(ImageAdmin image_admin) {
        map_object_admin = new MapObjectAdmin();
        map_object_admin.init(image_admin);//MapObjectAdmin.javaのinitを実行
    }



    public void update(double touch_x, double touch_y, int touch_state) {

        map_object_admin.update(touch_x, touch_y, touch_state);

    }

    public void draw(double touch_x, double touch_y, int touch_state) {
        Canvas canvas = null;
        canvas = holder.lockCanvas(null);
        map_admin.drawMap(canvas);
        map_object_admin.draw(touch_x, touch_y, touch_state, canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    public void draw(GL10 gl) {
        map_object_admin.draw(gl);
    }

    public GameSystem() {
    }
}
