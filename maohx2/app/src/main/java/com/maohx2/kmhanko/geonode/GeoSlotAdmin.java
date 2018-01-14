package com.maohx2.kmhanko.geonode;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.UI.UserInterface;

// Added by kmhanko
import com.maohx2.kmhanko.database.MyDatabase;
import java.util.ArrayList;
import java.util.List;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import com.maohx2.kmhanko.itemdata.GeoObjectData;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;

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

    static MyDatabase geoSlotMapDB;
    static MyDatabase geoSlotEventDB;

    public final int GEO_SLOT_MAX = 64;
    String t_name; //このGeoSlotAdmin = ジオマップの名称 = Table名

    List<GeoSlot> geo_slots = new ArrayList<GeoSlot>(GEO_SLOT_MAX);
    List<Integer> tree_code = new ArrayList<Integer>(); //GeoSlotのツリー上構造を表す数値列
    GeoSlot grand_geo_slot; //ツリーの中心であるGeoSlot
    GeoCalcSaverAdmin geo_calc_saver_admin; //GeoSlotの計算を行い、計算結果を格納する

    //TODO:ここでのGeoObjectDataは、書き換えを行うという点で少しおかしいので、変えた方がいいかもしれない
    GeoObjectData holdGeoObject; //現在選択中のジオオブジェクトを格納する。

    Graphic graphic;
    TextBoxAdmin textBoxAdmin;
    UserInterface userInterface;

    GeoSlot focusGeoSlot; //今操作している(条件の解放のため選択している)GeoSlot

    int releaseTextBoxID; //スロット条件解放の説明文を表示するためのTextBoxID
    boolean isReleaseListActive = false;
    ListBox releaseList;//解放する/やめる　の選択

    //Rewrite by kmhanko
    public GeoSlotAdmin(Graphic _graphic, UserInterface _user_interface, TextBoxAdmin _textBoxAdmin) {
        graphic = _graphic;
        userInterface = _user_interface;
        textBoxAdmin = _textBoxAdmin;
        GeoSlot.setUserInterface(userInterface);

        //TextBoxなどの初期化
        releaseTextBoxID = textBoxAdmin.createTextBox(950,600,1450,800,2);
        //textBoxAdmin.hideTextBox(releaseTextBoxID);
    }

    public static void setGeoSlotMapDB(MyDatabase _geoSlotMapDB) {
        geoSlotMapDB = _geoSlotMapDB;
    }
    public static void setGeoSlotEventDB(MyDatabase _geoSlotEventDB) {
        geoSlotEventDB = _geoSlotEventDB;
    }

    //ジオスロットの並びを表すツリーコードを用いて、GeoSlotのインスタンス化を行う。
    public void loadDatabase(String _t_name) {
        t_name = _t_name;

        //DBからツリーコードを取得する関数
        tree_code = this.getTreeCode();

        //根スロットのインスタンス化
        grand_geo_slot = new GeoSlot(this);

        //このメソッドを呼ぶと、全てのGeoSlotのインスタンス化が完了する。実体は各GeoSlotが子GeoSlotとして持つ。
        grand_geo_slot.makeGeoSlotInstance(tree_code, null);

        //GeoSlotの管理のため、GeoSlotのインスタンスをコピーしてくるメソッド。
        //geo_slots = (GeoSlot[])grand_geo_slot.getGeoSlots().toArray(new GeoSlot[0]);
        geo_slots = grand_geo_slot.getGeoSlots();

        //各GeoSlotの初期化
        List<Integer> xs = geoSlotMapDB.getInt(t_name, "x");
        List<Integer> ys = geoSlotMapDB.getInt(t_name, "y");
        List<String> release_events = geoSlotMapDB.getString(t_name, "release_event");
        List<String> restrictions = geoSlotMapDB.getString(t_name, "restriction");
        int r;

        GeoSlot.staticInit(graphic, userInterface, textBoxAdmin, geoSlotEventDB);

        for(int i = 0; i < geo_slots.size(); i++) {
            //TODO:根の表示も適当
            if (i == 0) {
                r = 70;
            } else {
                r = 70;
            }
            geo_slots.get(i).setParam(xs.get(i),ys.get(i), r);
            //TouchIDセット
            geo_slots.get(i).setTouchID(userInterface.setCircleTouchUI(xs.get(i), ys.get(i), r+10));

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

    //GeoSlotを解放しますか？的なもの
    public void geoSlotReleaseChoice() {
        if (!focusGeoSlot.isEventClear()) {
            //TextBox表示「ここを解放するためには　？？？　が必要」

            //textBoxAdminHide解消
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "このスロットを解放するには");
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "\n");
            textBoxAdmin.bookingDrawText(releaseTextBoxID, focusGeoSlot.getReleaseEvent());//TODO:イベント名そのままになっているが、これは仮
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "が必要です。");

            //「解放する」「解放しない」ボタン表示　→　ListBox<Button>の完成待ち
            releaseList = new ListBox();
            releaseList.init(userInterface, graphic, Constants.Touch.TouchWay.DOWN_MOMENT, 2 , 1200, 50, 1500, 50 + 100 * 2);
            releaseList.setContent(0, "解放する");
            releaseList.setContent(1, "やめる");
            isReleaseListActive = true;
        }
    }


    public void update(){
        //GeoSlot
        for(int i = 0; i < geo_slots.size(); i++) {
            if (geo_slots.get(i) != null) {
                geo_slots.get(i).update();
            }
        }
        //ListBox
        if (isReleaseListActive) {
            releaseList.update();
            int content = releaseList.checkTouchContent();
            switch (content) {
                case (0)://解放する
                    //解放するための色々な処理
                    focusGeoSlot.geoSlotRelease();
                    break;
                case (1)://やめる
                    isReleaseListActive = false;
                    //textBoxAdmin.hideTextBox(releaseTextBoxID);
                    break;
            }
        }
    }

    public void draw() {
        //GeoSlot
        //2つのfor文に分けなければならない(描画順の問題)
        for(int i = 0; i < geo_slots.size(); i++) {
            geo_slots.get(i).drawLine();
        }
        for(int i = 0; i < geo_slots.size(); i++) {
            boolean f = geo_slots.get(i).equals(focusGeoSlot);
            geo_slots.get(i).draw(f);
        }
        //ListBox
        if (isReleaseListActive) {
            if (releaseList != null) {
                releaseList.draw();
            }
        }
    }




    private List<Integer> getTreeCode() {
        return geoSlotMapDB.getInt(t_name, "children_num");
    }

    public String getName() { return t_name; }
    public GeoObjectData getHoldGeoObject() {
        return holdGeoObject;
    }
    public void setHoldGeoObject(GeoObjectData geoObjectData) {
        holdGeoObject = geoObjectData;
    }
    public boolean isHoldGeoObject() {
        return holdGeoObject != null;
    }
    public void setFocusGeoSlot(GeoSlot _focusGeoSlot) {
        focusGeoSlot = _focusGeoSlot;
    }

    //** Created by ina **//

    //rewrire by kmhanko
    //GeoSlot geo_slots[] = new GeoSlot[10];
    //UserInterface user_interface;

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
