package com.maohx2.kmhanko.geonode;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;

// Added by kmhanko
import com.maohx2.kmhanko.database.MyDatabase;
import java.util.ArrayList;
import java.util.List;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

/**
 * Created by ina on 2017/10/08.
 */

    /* メモ
    tree_code 2120021010

    2 - 1 - 2 - 0
              - 0
      - 2 - 1 - 0
            1 - 0

     */

public class GeoSlotAdmin {

    //** Created by kmhanko **//

    static MyDatabase database;

    public final int GEO_SLOT_MAX = 64;
    String t_name; //このGeoSlotAdmin = ジオマップの名称 = Table名

    List<GeoSlot> geo_slots = new ArrayList<GeoSlot>(GEO_SLOT_MAX);
    List<Integer> tree_code = new ArrayList<Integer>(); //GeoSlotのツリー上構造を表す数値列
    GeoSlot grand_geo_slot; //ツリーの中心であるGeoSlot
    GeoCalcSaverAdmin geo_calc_saver_admin; //GeoSlotの計算を行い、計算結果を格納する

    Graphic graphic;

    //Rewrite by kmhanko
    public void init(Graphic _graphic, UserInterface _user_interface) {
        graphic = _graphic;
        user_interface = _user_interface;
        GeoSlot.setGeoSlotAdmin(this);
    }

    public static void setDatabase(MyDatabase _database) {
        database = _database;
    }

    //ジオスロットの並びを表すツリーコードを用いて、GeoSlotのインスタンス化を行う。
    public void loadDatabase(String _t_name) {
        t_name = _t_name;

        //DBからツリーコードを取得する関数
        tree_code = this.getTreeCode();

        //根スロットのインスタンス化
        grand_geo_slot = new GeoSlot();

        //このメソッドを呼ぶと、全てのGeoSlotのインスタンス化が完了する。実体は各GeoSlotが子GeoSlotとして持つ。
        grand_geo_slot.makeGeoSlotInstance(tree_code, null);

        //GeoSlotの管理のため、GeoSlotのインスタンスをコピーしてくるメソッド。
        //geo_slots = (GeoSlot[])grand_geo_slot.getGeoSlots().toArray(new GeoSlot[0]);
        geo_slots = grand_geo_slot.getGeoSlots();

        //各GeoSlotの初期化
        List<Double> xs = database.getDouble(t_name, "x");
        List<Double> ys = database.getDouble(t_name, "y");
        List<String> release_events = database.getString(t_name, "release_event");
        List<String> restrictions = database.getString(t_name, "restriction");
        int r;
        for(int i = 0; i < geo_slots.size(); i++) {
            //TODO:根の表示も適当
            if (i == 0) {
                r = 50;
            } else {
                r = 40;
            }
            geo_slots.get(i).init(graphic);
            //TODO:とりあえずの適当変換にしているので、カメラ系かなんかが出来上がり次第直す
            geo_slots.get(i).setParam((int) (xs.get(i) * 1600), (int) (ys.get(i) * 900), r);
            geo_slots.get(i).setTouchID(user_interface.setCircleTouchUI((int) (xs.get(i) * 1600), (int) (ys.get(i) * 900), r+10));

            geo_slots.get(i).setReleaseEvent(release_events.get(i));
            geo_slots.get(i).setRestriction(restrictions.get(i));
        }

    }

    //GeoSlotによるステータスへの加算量を計算する
    public boolean calcGeoSlot() {
        geo_calc_saver_admin = grand_geo_slot.calcPass();
        if (geo_calc_saver_admin != null) {
            geo_calc_saver_admin.finalCalc();
            return true;
        }
        return false;
    }

    public GeoCalcSaverAdmin getGeoCalcSaverAdmin() {
        return geo_calc_saver_admin;
    }

    //とりあえず計算後のパラメーターを表示するだけのメソッド。いつか消える。
    /*
    public void drawParam(Canvas canvas) {
        if (geo_calc_saver_admin == null) {
            return;
        }
        Paint paint = new Paint();
        paint.setColor(Color.argb(128, 0, 0, 0));
        paint.setTextSize(80);
        canvas.drawText(geo_calc_saver_admin.getParam("HP"), 1550, 200, paint);
        canvas.drawText(geo_calc_saver_admin.getParam("Attack"), 1550, 300, paint);
        canvas.drawText(geo_calc_saver_admin.getParam("Defence"), 1550, 400, paint);
        canvas.drawText(geo_calc_saver_admin.getParam("Luck"), 1550, 500, paint);
    }
    */


    public void update(){
        for(int i = 0; i < geo_slots.size(); i++) {
            if (geo_slots.get(i) != null) {
                if (user_interface.checkUI(geo_slots.get(i).getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true) {
                    System.out.println(user_interface.getItemID());
                    //TODO:isPushThisObject引数
                    if (geo_slots.get(i).isEventClearAll() && geo_slots.get(i).isPushThisObject(null)) {
                        geo_slots.get(i).setItemID(user_interface.getItemID());
                        geo_slots.get(i).setGeoObjectByItemID();
                        this.calcGeoSlot();
                    }
                }
            }
        }
    }

    public void draw() {
        //2つのfor文に分けなければならない(描画順の問題)
        for(int i = 0; i < geo_slots.size(); i++) {
            geo_slots.get(i).drawLine();
        }
        for(int i = 0; i < geo_slots.size(); i++) {
            geo_slots.get(i).draw();
        }
    }

    private List<Integer> getTreeCode() {
        return database.getInt(t_name, "children_num");
    }

    public String getName() { return t_name; }


    //** Created by ina **//

    //rewrire by kmhanko
    //GeoSlot geo_slots[] = new GeoSlot[10];

    UserInterface user_interface;

    /* rewrite by kmhanko
    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;

        for(int i = 0; i < 10; i++) {
            geo_slots.get(i) = new GeoSlot();
            geo_slots.get(i).init();
        }

        for(int i = 0; i < 10; i++) {
            geo_slots.get(i).setParam(30+50*i, 30+50*i, 20);
            geo_slots.get(i).setTouchID(user_interface.setCircleTouchUI(30+50*i,30+50*i,30));
        }
    }
    */

    /*rewrite by kmhanko
    public void update(){

        for(int i = 0; i < 10; i++) {
            if(user_interface.checkUI(geo_slots.get(i).getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true){
                System.out.println(user_interface.getItemID());
                geo_slots.get(i).setItemID(user_interface.getItemID());
            }
        }
    }
    */


    /*rewrite by kmhanko
    public void draw(Canvas canvas) {

        for(int i = 0; i < 10; i++) {
            geo_slots.get(i).draw(canvas);
        }
    }
    */
}
