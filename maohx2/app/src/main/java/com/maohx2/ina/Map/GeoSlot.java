package com.maohx2.ina.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

// Added by kmhanko
import com.maohx2.kmhanko.geonode.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ina on 2017/10/08.
 */

public class GeoSlot {

    //** Created by kmhanko **//

    static final int GEO_SLOT_CHILDREN_MAX = 8;
    static GeoSlotAdmin geo_slot_admin;

    List<GeoSlot> children_slot = new ArrayList<GeoSlot>(GEO_SLOT_CHILDREN_MAX);
    GeoSlot parent_slot;

    boolean is_in_geo_object; //穴にGeoが入っているかどうか
    boolean is_exist; //穴が存在するかどうか

    String release_event;
    String restriction;

    GeoObject geo_object;

    public GeoSlot() {
        is_exist = true;
    }

    //GeoSlotのツリーコードを元に、GeoSlotのインスタンス化を行う。再帰ライクに生成する。
    public List<Integer> makeGeoSlotInstance(List<Integer> tree_code, GeoSlot _parent_slot) {
        parent_slot = _parent_slot;
        if (tree_code.isEmpty()) {
            return new ArrayList<Integer>(0);
        }

        int size = tree_code.get(0);
        tree_code.remove(0);

        for (int i = 0; i < size; i++) {
            children_slot.add(new GeoSlot());
            tree_code = children_slot.get(children_slot.size() - 1).makeGeoSlotInstance(tree_code, this);
        }
        return tree_code;
    }

    //GeoObjectをGeoSlotにはめる。
    public boolean pushGeoObject(GeoObject _geo_object) {
        if (!is_exist) {
            return false;
        }
        is_in_geo_object = true;
        geo_object = _geo_object;
        return true;
    }

    //GeoObjectをGeoSlotから抜き取る。
    public boolean popGeoObject() {
        if (!is_exist) {
            return false;
        }
        is_in_geo_object = false;
        return true;
    }

    //TODO:あるGeoObjectを設置できるか返す関数。引数はGeoObject。GeoObjectができてから作る
    public boolean isPushThisObject(GeoObject geo_object) {
        return true;
    }

    //TODO:イベントがクリアされているか返す関数。
    public boolean isEventClear() {
        if (release_event == null) {
            return true;
        }
        return false;
    }

    //親を含めて、イベントがクリアされているかを再帰風に返す関数。
    public boolean isEventClearAll() {
        if (isEventClear()) {
            if (parent_slot != null) {
                return parent_slot.isEventClearAll();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //全てのGeoSlotを取得するためのメソッド。再帰ライクに取得する。
    public List<GeoSlot> getGeoSlots() {
        List<GeoSlot> buf_geo_slot = new ArrayList<GeoSlot>(children_slot.size() * GEO_SLOT_CHILDREN_MAX + 1);
        buf_geo_slot.add(this);
        for (int i = 0; i < children_slot.size(); i++) {
            if (children_slot.get(i) != null) {
                buf_geo_slot.addAll(children_slot.get(i).getGeoSlots());
            }
        }

        return buf_geo_slot;
    }

    //GeoSlotの計算を行うメソッド。再帰ライクに計算する。
    public GeoCalcSaverAdmin calcPass() {
        if (!is_exist || !is_in_geo_object) {
            return null;
        }
        GeoCalcSaverAdmin geo_calc_saver_admin_temp = null;
        List<GeoCalcSaverAdmin> geo_calc_saver_admins = new ArrayList<GeoCalcSaverAdmin>(GEO_SLOT_CHILDREN_MAX);
        int child_num = 0;
        for (int i = 0; i<children_slot.size(); i++) {
            if (children_slot.get(i) != null) {
                if (children_slot.get(i).isInGeoObjectAndExist()) {
                    GeoCalcSaverAdmin buf_geo_calc_saver_admin = children_slot.get(i).calcPass();
                    if (buf_geo_calc_saver_admin != null) {
                        geo_calc_saver_admins.add(buf_geo_calc_saver_admin);
                        geo_calc_saver_admin_temp = buf_geo_calc_saver_admin;
                        child_num++;
                    }
                }
            }
        }
        //この時点でこのNodeより子供は全て計算された状態にある
        if (child_num == 0) {
            GeoCalcSaverAdmin new_geo_calc_saver_admin = new GeoCalcSaverAdmin();
            calc(new_geo_calc_saver_admin);
            return new_geo_calc_saver_admin;
        }
        if (child_num == 1) {
            calc(geo_calc_saver_admin_temp);
            return geo_calc_saver_admin_temp;
        }
        if (child_num > 1) {
            GeoCalcSaverAdmin new_geo_calc_saver_admin = new GeoCalcSaverAdmin();
            //各々のGeoCalcSaverAdminを合体する
            for (int i = 0; i < geo_calc_saver_admins.size(); i++) {
                if (geo_calc_saver_admins.get(i) != null) {
                    new_geo_calc_saver_admin.union(geo_calc_saver_admins.get(i));
                }
            }
            calc(new_geo_calc_saver_admin);
            return new_geo_calc_saver_admin;
        }
        return null;
    }

    //GeoSlotの計算を行うメソッド。再帰ライクの終着点に該当する。
    private void calc(GeoCalcSaverAdmin geo_calc_saver_admin) {
        //TODO: ステータス加算を書き連ねる。,正式なGeoObjectが完成してから書き直す。
        geo_calc_saver_admin.getGeoCalcSaver("HP").calc(geo_object.getHp(),geo_object.getHpRate());
        geo_calc_saver_admin.getGeoCalcSaver("Attack").calc(geo_object.getAttack(),geo_object.getAttackRate());
        geo_calc_saver_admin.getGeoCalcSaver("Defence").calc(geo_object.getDefence(),geo_object.getDefenceRate());
        geo_calc_saver_admin.getGeoCalcSaver("Luck").calc(geo_object.getLuck(),geo_object.getLuckRate());
    }

    //itemIDを元に、GeoObjectの数値を代入する。デバッグ用
    public void setGeoObjectByItemID() {
        //TODO: 正式にはGeoObjectのDatabaseなどが完成してから書き直す。itemID→GeoObjectDatabaseROWIDへの変換も必要だろう。
        GeoObject geo_object = null;
        switch(item_id) {
            case 0:
                geo_object = new GeoObject(50,0,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geo_object);
                break;
            case 1:
                geo_object = new GeoObject(0,20,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geo_object);
                break;
            case 2:
                geo_object = new GeoObject(0,10,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geo_object);
                break;
            case 3:
                geo_object = new GeoObject(0,0,0,0,1.5,1.0,1.0,1.0);
                this.pushGeoObject(geo_object);
                break;
            case 4:
                geo_object = new GeoObject(20,10,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geo_object);
                break;
            case 5:
                geo_object = new GeoObject(0,0,0,0,1.2,1.2,1.0,1.0);
                this.pushGeoObject(geo_object);
                break;
            default:
                this.popGeoObject();
                break;
        }

    }

    public void draw(Canvas canvas) {
        //TODO:DrawBookingが出来上がったら書き直す

        //丸を描く
        if (item_id != -1) {
            paint.setColor(Color.argb(255, 100 * ((item_id + 0) % 3), 100 * ((item_id + 1) % 3), 100 * ((item_id + 2) % 3)));
        }else {
            paint.setColor(Color.argb(100, 0, 0, 0));
        }
        canvas.drawCircle(position_x, position_y, radius, paint);

        //子に対しての線を描く。
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);

        for (int i = 0; i<children_slot.size(); i++) {
            if (children_slot.get(i) != null) {
                if (children_slot.get(i).isExist()) {
                    canvas.drawLine(position_x, position_y, children_slot.get(i).getPositionX(), children_slot.get(i).getPositionY(), paint);
                }
            }
        }

        //鍵がかかってるところ表示
        if (!isEventClear()) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(120);
            canvas.drawText("×", position_x, position_y, paint);
        }



    }

    public boolean isInGeoObject() {
        return is_in_geo_object;
    }
    public boolean isExist() {
        return is_exist;
    }
    public boolean isInGeoObjectAndExist() {
        return (is_in_geo_object && is_exist);
    }

    static public void setGeoSlotAdmin(GeoSlotAdmin _geo_slot_admin) { geo_slot_admin = _geo_slot_admin; }
    public void setIsExist(boolean _is_exist) {
        is_exist = _is_exist;
    }

    public int getPositionX() {
        return position_x;
    }
    public int getPositionY() {
        return position_y;
    }


    public String getReleaseEvent() { return release_event; }
    public String getRestriction() { return restriction; }
    public void setReleaseEvent(String _release_event) { release_event = _release_event; }
    public void setRestriction(String _restriction) { restriction = _restriction; }

    //** Created by ina **//

    Paint paint;

    int position_x;
    int position_y;
    int radius;
    int touch_id;

    int item_id;


    public void init() {
        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        item_id = -1;
    }

    /*rewrite by kmhanko
    public void draw(Canvas canvas) {

        if (item_id != -1) {
            paint.setColor(Color.argb(255, 100 * ((item_id + 0) % 3), 100 * ((item_id + 1) % 3), 100 * ((item_id + 2) % 3)));
        }else {
            paint.setColor(Color.argb(100, 0, 0, 0));
        }

        canvas.drawCircle(position_x, position_y, radius, paint);

    }
    */

    public int getItemID() {
        return item_id;
    }


    public void setItemID(int _item_id) {
        item_id = _item_id;
    }


    public void setParam(int _position_x,int _position_y,int _radius){
        position_x = _position_x;
        position_y = _position_y;
        radius = _radius;
    }

    public int getTouchID(){

        return touch_id;
    }

    public void setTouchID(int _touch_id) {
        touch_id = _touch_id;
    }
}
