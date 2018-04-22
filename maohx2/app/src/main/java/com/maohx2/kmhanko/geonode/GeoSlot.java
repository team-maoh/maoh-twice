package com.maohx2.kmhanko.geonode;

// Added by kmhanko

import java.util.ArrayList;
import java.util.List;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.itemdata.GeoObjectData;

/**
 * Created by ina on 2017/10/08.
 */

/*
初回起動時エラーを直した。android_metadataをテーブル名取得しないことによって。これを確認する作業
GeoSlotとGeoSlotADmin, 動かすためにいくつかコメントアウトされているので注意
いなのtextにprotectedをつける作業
MakeGeoSlot的なやつ、MyDBをパスしてsql文章で一行づつ消滅させる形を取ることによりtree_codeをgeoSlotCodeに変更する。
無理だったらクラスにして渡す。
これによって検証によるコンストラクタの変更に対応する
 */
//TODO 計算が反映されない
public class GeoSlot extends CircleImagePlate {

    //** Created by kmhanko **//

    static final int GEO_SLOT_CHILDREN_MAX = 8;

    GeoSlotAdmin geoSlotAdmin; //staticにしてはならない。いくつかのGeoSlotAdminがあるため。
    static TextBoxAdmin textBoxAdmin;
    static MyDatabase geoSlotEventDB;

    List<GeoSlot> children_slot = new ArrayList<GeoSlot>(GEO_SLOT_CHILDREN_MAX);
    GeoSlot parent_slot;

    boolean is_in_geoObjectData; //穴にGeoが入っているかどうか
    boolean is_exist; //穴が存在するかどうか

    String release_event;
    String restriction;

    GeoObjectData geoObjectData;

    ImageContext notEventCrearImageContext;
    ImageContext slotHoleImageContext;
    ImageContext geoImageContext;

    //TODO:デバッグ用。セーブデータの用意が必要
    boolean isReleased = false;

    /*
    public GeoSlot(GeoSlotAdmin _geoSlotAdmin) {
        item_id = -1;
        is_exist = true;
        geoSlotAdmin = _geoSlotAdmin;
    }
    */

    public GeoSlot(GeoSlotAdmin _geoSlotAdmin, Graphic _graphic, UserInterface _user_interface,
                   Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way,
                   int[] position,
                   ImageContext _default_image_context, ImageContext _feedback_image_context
    ){
        super(_graphic, _user_interface, _judge_way, _feedback_way, position, _default_image_context, _feedback_image_context);

        item_id = -1;
        is_exist = true;
        geoSlotAdmin = _geoSlotAdmin;
    }



    static public void staticInit(TextBoxAdmin _textBoxAdmin, MyDatabase _geoSlotEventDB) {
        textBoxAdmin = _textBoxAdmin;
        geoSlotEventDB = _geoSlotEventDB;
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
            children_slot.add(new GeoSlot(
                    geoSlotAdmin, graphic, user_interface,
                    judge_way, feedback_way, new int[] {0,0,0}, default_image_context, feedback_image_context));
            tree_code = children_slot.get(children_slot.size() - 1).makeGeoSlotInstance(tree_code, this);
        }

        return tree_code;
    }

    //GeoObjectDataをGeoSlotにはめる。
    public boolean pushGeoObject(GeoObjectData _geoObjectData) {
        if (!is_exist) {
            return false;
        }
        is_in_geoObjectData = true;
        geoObjectData = _geoObjectData;
        geoImageContext = graphic.makeImageContext(geoObjectData.getItemImage(), x, y, 5.0f, 5.0f, 0.0f, 128, false);
        return true;
    }

    //GeoObjectをGeoSlotから抜き取る。
    public boolean popGeoObject() {
        if (!is_exist) {
            return false;
        }
        is_in_geoObjectData = false;
        geoObjectData = null;
        geoImageContext = null;
        return true;
    }

    //TODO:あるGeoObjectを設置できるか返す関数。引数はGeoObject。GeoObjectができてから作る
    public boolean isPushThisObject(GeoObjectData geoObjectData) {
        return true;
    }

    //TODO:イベントがクリアされているか返す関数。
    public boolean isEventClear() {
        if (release_event == null) {
            return true;
        } else {
            //何かしらrelease_eventが設定されている

            //セーブデータにアクセスし、その条件を満たしているかを確認する

            //満たしているなら
            //return true;
            //満たしていないなら
            //return false;

            //TODO:デバッグ用　とりあえずSlot自身が一時的にイベントがクリアされたかの変数を持つことにする
            if (isReleased == true) {
                return true;
            } else {
                return false;
            }
        }
    }

    //親を含めて、イベントがクリアされているかを再帰風に返す関数。(つまり、このスロットよりも根元に、置いてはいけないマークがあるかどうか)
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
        if (!is_exist || !is_in_geoObjectData) {
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
        geo_calc_saver_admin.getGeoCalcSaver("HP").calc(geoObjectData.getHp(),geoObjectData.getHpRate());
        geo_calc_saver_admin.getGeoCalcSaver("Attack").calc(geoObjectData.getAttack(),geoObjectData.getAttackRate());
        geo_calc_saver_admin.getGeoCalcSaver("Defence").calc(geoObjectData.getDefence(),geoObjectData.getDefenceRate());
        geo_calc_saver_admin.getGeoCalcSaver("Luck").calc(geoObjectData.getLuck(),geoObjectData.getLuckRate());
    }

    public void drawLine() {
        //子に対しての線を書く

        /*

        int c_x = 0;
        int c_y = 0;
        int degree = 0;
        float scale = 0.0f;

        for (int i = 0; i<children_slot.size(); i++) {
            if (children_slot.get(i) != null) {
                if (children_slot.get(i).isExist()) {
                    c_x = children_slot.get(i).getPositionX();
                    c_y = children_slot.get(i).getPositionY();

                    degree = (int)Math.toDegrees(Math.atan2(position_y - c_y, position_x - c_x));
                    scale = (float)MyAvail.distance(position_x, position_y, c_x, c_y) / 24.0f;

                    graphic.drawBooking("watermelon.png", (position_x + c_x)/2, (position_y + c_y)/2, scale , 5.0f, degree, 255, true);
                }
            }
        }

        */
    }


    @Override
    public void draw() {
        super.draw();
        if (!isEventClear()) {
            graphic.bookingDrawBitmapData(notEventCrearImageContext);
        }
        if (isInGeoObject()) {
            graphic.bookingDrawBitmapData(geoImageContext);
        }
    }
    /*
    public void draw(boolean isFocused) {
        if (isFocused) {
            graphic.bookingDrawBitmapName("apple", position_x, position_y, SCALE*1.5f, SCALE*1.5f, 0, 255, false);
        } else {
            graphic.bookingDrawBitmapName("apple", position_x, position_y, SCALE, SCALE, 0, 255, false);
        }

        if (is_in_geoObjectData && geoObjectData != null) {
            //TODO: geoObjectDataの画像名を獲得する
            graphic.bookingDrawBitmapName("neco", position_x, position_y, SCALE, SCALE, 0, 255, false);
        }

        if (!isEventClear()) {
            graphic.bookingDrawBitmapName("banana", position_x, position_y, SCALE, SCALE, 0, 255, false);
        }
    }
    */

    /*
    public void update() {
        super.update();
        //touchEvent();
    }
    */

    @Override
    //タッチされた時の処理
    public void callBackEvent() {
        geoSlotAdmin.setFocusGeoSlot(this);
        geoSlotAdmin.geoSlotReleaseChoice();
        if (isEventClearAll()) {
            setGeoObjectFromHold();
            geoSlotAdmin.calcPlayerStatus();
        }
    }

    public void setGeoObjectFromHold() {

        if (isPushThisObject(geoSlotAdmin.getHoldGeoObject())) {
            if (geoSlotAdmin.isHoldGeoObject()) {
                if (!isInGeoObject()) {
                    //Holdしており、Geoが入っていない時　→そのままセット

                    //この辺りは書き方の順序に注意。nullにしてからセットしようとしてしまったりするため

                    //Geoをセットする
                    pushGeoObject(geoSlotAdmin.getHoldGeoObject());

                    //GeoをInventryとHoldから消す
                    geoSlotAdmin.deleteFromInventry(geoObjectData);
                    geoSlotAdmin.setHoldGeoObject(null);

                } else {
                    //Holdしており、Geoが入っている時　→　入れ替え
                    GeoObjectData tempGeoObjectData = geoSlotAdmin.getHoldGeoObject();

                    geoSlotAdmin.addToInventry(geoObjectData);
                    geoSlotAdmin.setHoldGeoObject(geoObjectData);

                    //Geoを上書きセットする
                    pushGeoObject(tempGeoObjectData);

                    //GeoをInventryからけす。
                    geoSlotAdmin.deleteFromInventry(tempGeoObjectData);
                }
                geoSlotAdmin.calcGeoSlot();
            } else {
                if (!isInGeoObject()) {
                    //Holdしておらず、Geoも入っていない時　→　何もしない
                } else {
                    //Holdしておらず、Geoが入っている時　→　Holdにセット
                    geoSlotAdmin.addToInventry(geoObjectData);
                    geoSlotAdmin.setHoldGeoObject(geoObjectData);
                    popGeoObject();
                }

            }
        }
    }

    //GeoSlot解放のデータ的処理
    public void geoSlotRelease() {
        if (!isEventClear()) {
            //GeoSlotEvent.DBを参照し、release_eventと一致するものを探し、そのTable名で分岐して処理
            List<String> tableName = geoSlotEventDB.getTables();

            int rowid;
            String eventGroupName = null;
            for(int i = 0; i < tableName.size(); i++) {
                rowid = geoSlotEventDB.getOneRowID(tableName.get(i), "name = " + MyDatabase.s_quo(release_event));
                if (rowid > 0) {
                    //該当イベントが存在した
                    eventGroupName = tableName.get(i);
                    break;
                }
            }
            if (eventGroupName == null) {
                throw new Error("☆タカノ : GeoSlot#geoSlotRelease 該当する解放イベント名がDB上に存在しない : " + release_event);
            }

            if (eventGroupName == "Money") {
                //プレイヤーの所持金をチェック、必要金額以上あれば減らす。
                System.out.println("GeoSlot#geoSlotRelease　金を支払う　" + release_event);
            }
            if (eventGroupName == "GeoObject") {
                //プレイヤーの所持GeoObjectを表示、選択させるイベントを発生させる。
                System.out.println("GeoSlot#geoSlotRelease　ジオオブジェクトを支払う　" + release_event);
            }

            //色々あって解決した場合
            isReleased = true;
        }
    }

    public boolean isInGeoObject() {
        return is_in_geoObjectData;
    }
    public boolean isExist() {
        return is_exist;
    }
    public boolean isInGeoObjectAndExist() {
        return (is_in_geoObjectData && is_exist);
    }

    public void setGeoSlotAdmin(GeoSlotAdmin _geoSlotAdmin) { geoSlotAdmin = _geoSlotAdmin; }
    //static public void setUserInterface(UserInterface _userInterface) { userInterface = _userInterface; }
    static public void setTextBoxAdmin(TextBoxAdmin _textBoxAdmin) { textBoxAdmin = _textBoxAdmin; }
    public void setIsExist(boolean _is_exist) {
        is_exist = _is_exist;
    }

    //public int getPositionX() { return position_x; }
    //public int getPositionY() { eturn position_y; }


    public GeoObjectData getGeoObjectData() { return geoObjectData; }
    public String getReleaseEvent() { return release_event; }
    public String getRestriction() { return restriction; }
    public void setReleaseEvent(String _release_event) { release_event = _release_event; }
    public void setRestriction(String _restriction) { restriction = _restriction; }
    public void setReleased(Boolean _isReleased) { isReleased = _isReleased; }

    //TODO: inaの関数ができたら消す
    public void setParam(int _x, int _y, int _r) {
        x = _x;
        y = _y;
        radius = _r;
        touch_id = user_interface.setCircleTouchUI(x, y, radius);
        //TODO: 前の奴を消せないので格納上の問題あり

        notEventCrearImageContext = graphic.makeImageContext(graphic.searchBitmap("apple"), x , y, 5.0f, 5.0f, 0.0f, 255, false);
        slotHoleImageContext = graphic.makeImageContext(graphic.searchBitmap("neco"), x, y, 5.0f, 5.0f, 0.0f, 255, false);
        geoImageContext = null;

        default_image_context = slotHoleImageContext;
        draw_image_context = slotHoleImageContext;
        feedback_image_context = slotHoleImageContext;
    }

    //** Created by ina **//

    //delete by kmhanko
    //Paint paint;

    //delete by kmhanko
    //int position_x;
    //int position_y;
    //int radius;
    //int touch_id;

    int item_id;


    /*rewrite by kmhanko
    public void init() {
        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        item_id = -1;
    }
    */

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


    //delete by kmhanko
    /*
    public void setParam(int _position_x,int _position_y,int _radius){
        position_x = _position_x;
        position_y = _position_y;
        radius = _radius;
    }
    */

    public int getTouchID(){

        return touch_id;
    }

    public void setTouchID(int _touch_id) {
        touch_id = _touch_id;
    }
}

//

        /*
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
        */



        //by kmhanko
        //itemIDを元に、GeoObjectの数値を代入する。デバッグ用
    /*
        public void setGeoObjectByItemID(int id) {

        GeoObjectData geoObjectData = null;
        switch(id) {
            case 0:
                geoObjectData = new GeoObjectData(graphic, 50,0,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geoObjectData);
                break;
            case 1:
                geoObjectData = new GeoObjectData(graphic, 5,20,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geoObjectData);
                break;
            case 2:
                geoObjectData = new GeoObjectData(graphic, 5,10,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geoObjectData);
                break;
            case 3:
                geoObjectData = new GeoObjectData(graphic, 5,0,0,0,1.5,1.0,1.0,1.0);
                this.pushGeoObject(geoObjectData);
                break;
            case 4:
                geoObjectData = new GeoObjectData(graphic, 20,10,0,0,1.0,1.0,1.0,1.0);
                this.pushGeoObject(geoObjectData);
                break;
            case 5:
                geoObjectData = new GeoObjectData(graphic, 0,0,0,0,1.2,1.2,1.0,1.0);
                this.pushGeoObject(geoObjectData);
                break;
            default:
                this.popGeoObject();
                break;
        }
}*/

    /*
    public void touchEvent() {
        if (userInterface.checkUI(getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true) {
            //System.out.println(userInterface.getItemID());
            //TODO:isPushThisObject引数

            geoSlotAdmin.setFocusGeoSlot(this);

            //ジオオブジェクトをホールドしている時
            if (geoSlotAdmin.isHoldGeoObject()) {
                if (isEventClearAll() && isPushThisObject(null)) {
                    //GeoSlotを設置する
                    setItemID(userInterface.getItemID());
                    setGeoObjectByItemID();
                    geoSlotAdmin.calcGeoSlot();
                }
            } else {
                //ジオオブジェクトをホールドしていない時
                geoSlotAdmin.geoSlotReleaseChoice();
            }
        }
    }
*/
