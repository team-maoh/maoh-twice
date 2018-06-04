package com.maohx2.kmhanko.geonode;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.UI.UserInterface;

// Added by kmhanko
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.kmhanko.effect.Effect;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.myavail.MyAvail;
import com.maohx2.kmhanko.plate.BackPlate;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.sound.SoundAdmin;

// *** Graphic関係 ***
import android.graphics.Color;
import android.graphics.Paint;

// *** List関係 ***
import java.util.ArrayList;
import java.util.List;


import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Text.ListBox;

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

    //imageMagic

public class GeoSlotAdmin {

    //** Created by kmhanko **//

    static MyDatabase geoSlotMapDB;
    static MyDatabase geoSlotEventDB;


    public final int GEO_SLOT_MAX = 64;
    public final int TOUCH_R = 90;

    String t_name; //このGeoSlotAdmin = ジオマップの名称 = Table名

    List<Integer> tree_code = new ArrayList<Integer>(); //GeoSlotのツリー上構造を表す数値列
    List<GeoSlot> geoSlots = new ArrayList<GeoSlot>(GEO_SLOT_MAX);
    GeoSlot grand_geo_slot; //ツリーの中心であるGeoSlot
    GeoCalcSaverAdmin geo_calc_saver_admin; //GeoSlotの計算を行い、計算結果を格納する

    //TODO:ここでのGeoObjectDataは、書き換えを行うという点で少しおかしいので、変えた方がいいかもしれない
    GeoObjectData holdGeoObject; //現在選択中のジオオブジェクトを格納する。

    Graphic graphic;
    TextBoxAdmin textBoxAdmin;
    UserInterface userInterface;
    WorldModeAdmin worldModeAdmin;
    GeoSlotAdminManager geoSlotAdminManager;

    //int statusTextBoxID;

    PlateGroup<GeoSlot> geoSlotGroup;
    PlateGroup<BoxTextPlate> releasePlateGroup;//解放する/やめる　の選択
    //PlateGroup<BackPlate> backPlateGroup;

    Paint releaseTextPaint;

    InventryS geoInventry;


    GeoSlot focusGeoSlot; //今操作している(条件の解放のため選択している)GeoSlot

    int releaseTextBoxID; //スロット条件解放の説明文を表示するためのTextBoxID
    //boolean isReleasePlateActive = false;
    //ListBox releaseList;//解放する/やめる　の選択

    PlayerStatus playerStatus;

    SoundAdmin soundAdmin;

    EffectAdmin effectAdmin;

    //Rewrite by kmhanko
    public GeoSlotAdmin(Graphic _graphic, UserInterface _user_interface, WorldModeAdmin _worldModeAdmin, TextBoxAdmin _textBoxAdmin, GeoSlotAdminManager _geoSlotAdminManager, PlayerStatus _playerStatus, InventryS _geoInventry, SoundAdmin _soundAdmin, EffectAdmin _effectAdmin) {
        graphic = _graphic;
        userInterface = _user_interface;
        textBoxAdmin = _textBoxAdmin;
        worldModeAdmin = _worldModeAdmin;
        geoSlotAdminManager = _geoSlotAdminManager;
        playerStatus = _playerStatus;
        geoInventry = _geoInventry;
        soundAdmin = _soundAdmin;
        effectAdmin = _effectAdmin;

        //初期化
        initTextBox();
        //initBackPlate();
        initReleasePlate();
    }

    /*
    public void start() {
        textBoxAdmin.setTextBoxExists(statusTextBoxID, true);
    }

    public void exit() {
        textBoxAdmin.setTextBoxExists(statusTextBoxID, false);
    }
    */

    //ジオスロットの並びを表すツリーコードを用いて、GeoSlotのインスタンス化を行う。
    public void loadDatabase(String _t_name) {
        t_name = _t_name;

        //DBからツリーコードを取得する関数
        tree_code = this.getTreeCode();

        //各GeoSlotのパラメータをDBから取得
        List<Integer> xs = geoSlotMapDB.getInt(t_name, "x");
        List<Integer> ys = geoSlotMapDB.getInt(t_name, "y");
        List<String> release_events = geoSlotMapDB.getString(t_name, "release_event");
        List<String> restrictions = geoSlotMapDB.getString(t_name, "restriction");

        //根スロットのインスタンス化
        grand_geo_slot = new GeoSlot(this, graphic, userInterface,
                Constants.Touch.TouchWay.UP_MOMENT,
                Constants.Touch.TouchWay.MOVE,
                new int[] { 0, 0, 100 },
                graphic.makeImageContext(graphic.searchBitmap("neco"), 0, 0, 5.0f, 5.0f, 0.0f, 128, false),
                graphic.makeImageContext(graphic.searchBitmap("neco"), 0, 0, 6.0f, 6.0f, 0.0f, 128, false)
        );
        //TODO 配列の位置はタッチ座標用、COntextの位置は表示用
        //TODO 拡大縮小を動的に行う場合は毎回このmakeImageContextを呼ぶと言うことになるわけだが。
        //TODO そもそも画像の表示位置はContextに入っているから毎回呼ぶと言うことになるわけだが。
        //TODO Contextをベースとして、bookingの時の値をオフセットにするとか？
        //TODO 0はとりあえずの値。

        //このメソッドを呼ぶと、全てのGeoSlotのインスタンス化が完了する。実体は各GeoSlotが子GeoSlotとして持つ。
        grand_geo_slot.makeGeoSlotInstance(tree_code, null);


        //GeoSlotの管理のため、GeoSlotのインスタンスをコピーしてくるメソッド。
        geoSlots = grand_geo_slot.getGeoSlots();

        GeoSlot.staticInit(textBoxAdmin, geoSlotEventDB, geoInventry, soundAdmin, effectAdmin);

        for(int i = 0; i < geoSlots.size(); i++) {
            geoSlots.get(i).setParam(xs.get(i), ys.get(i), TOUCH_R);
            //TouchIDセット
            //geoSlots.get(i).setTouchID(userInterface.setCircleTouchUI(xs.get(i), ys.get(i), 100));
            geoSlots.get(i).setReleaseEvent(release_events.get(i));
            geoSlots.get(i).setRestriction(restrictions.get(i));
            geoSlots.get(i).setID(i);
        }

        //plateGroupインスタンス化
        geoSlotGroup = new PlateGroup<GeoSlot>((GeoSlot[])grand_geo_slot.getGeoSlots().toArray(new GeoSlot[0]));

    }

/*
    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[] {
                        new BackPlate(
                                graphic, userInterface, worldModeAdmin
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                soundAdmin.play("cancel00");

                                for (int i = 0; i < geoSlots.size(); i++) {
                                    getGeoSlots().get(i).clearGeoSlotLineEffect();
                                }

                                if (geoSlotAdminManager.getMode() == GeoSlotAdminManager.MODE.WORLD) {
                                    textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
                                    releasePlateGroup.setDrawFlag(false);
                                    releasePlateGroup.setUpdateFlag(false);
                                    geoSlotAdminManager.calcStatus();
                                    geoSlotAdminManager.saveGeoInventry();
                                    geoSlotAdminManager.saveGeoSlot();
                                    worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.DUNGEON_SELECT_INIT);
                                }
                                if (geoSlotAdminManager.getMode() == GeoSlotAdminManager.MODE.DUNGEON) {
                                    geoSlotAdminManager.getDungeonModeManage().setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
                                    geoSlotAdminManager.setStatusTextBoxFlag(false);
                                }
                            }
                        }
                }
        );
    }
*/

    private void initReleasePlate() {

        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        //「解放する」「解放しない」ボタン表示　→　ListBox<Button>の完成待ち
        releasePlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "解放する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.NO_LEFT, SELECT_WINDOW.NO_UP, SELECT_WINDOW.NO_RIGHT, SELECT_WINDOW.NO_BOTTOM},
                                "やめる",
                                textPaint
                        )
                }
        );
        releasePlateGroup.setDrawFlag(false);
        releasePlateGroup.setUpdateFlag(false);

    }

    private void initTextBox() {

        releaseTextBoxID = textBoxAdmin.createTextBox(SELECT_WINDOW.MESS_LEFT,SELECT_WINDOW.MESS_UP,SELECT_WINDOW.MESS_RIGHT,SELECT_WINDOW.MESS_BOTTOM, SELECT_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(releaseTextBoxID, false);
        textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
        releaseTextPaint = new Paint();
        releaseTextPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        releaseTextPaint.setColor(Color.WHITE);


/*
        MyAvail.makeSelection(
                graphic,
                userInterface,
                releasePlateGroup,
                textBoxAdmin,
                releaseTextPaint,
                "解放する",
                "やめる",
                releaseTextBoxID
        );
*/

        //textBoxAdmin.hideTextBox(releaseTextBoxID);

        /*
        statusTextBoxID = textBoxAdmin.createTextBox(0,600,300,900,7);
        textBoxAdmin.setTextBoxUpdateTextByTouching(statusTextBoxID, false);
        textBoxAdmin.setTextBoxExists(statusTextBoxID, false);
        statusTextBoxUpdate();
        */
    }


    public void geoUpdate() {
        calcGeoSlot();
        for(int i = 0; i < geoSlots.size(); i++) {
            geoSlots.get(i).drawLine();
        }
    }

    //***** GeoObjectステータス計算関係 *****

    //GeoSlotによるステータスへの加算量を計算する
    public boolean calcGeoSlot() {
        geo_calc_saver_admin = grand_geo_slot.calcPass();
        if (geo_calc_saver_admin != null) {
            geo_calc_saver_admin.finalCalc();
            return true;
        }
        return false;
    }

    /*
    public void statusTextBoxUpdate() {
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Status");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "HP " + playerStatus.getHP());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Attack " + playerStatus.getAttack());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Deffence " + playerStatus.getDefence());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Luck " + playerStatus.getLuck());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "所持金 " + playerStatus.getMoney());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "クリア回数 " + playerStatus.getNowClearCount() + "/" + playerStatus.getClearCount());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "MOP");
        textBoxAdmin.updateText(statusTextBoxID);
    }*/

    // ***** GeoObject計算関係ここまで　*****

    //GeoSlotを解放しますか？的なもの
    public void geoSlotReleaseChoice() {

        if (!focusGeoSlot.isEventClear()) {
            //TextBox表示「ここを解放するためには　？？？　が必要」

            textBoxAdmin.setTextBoxExists(releaseTextBoxID, true);

            textBoxAdmin.bookingDrawText(releaseTextBoxID, "このスロットを解放するには ", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, focusGeoSlot.getReleaseEvent(), releaseTextPaint);//TODO:イベント名そのままになっているが、これは仮
            textBoxAdmin.bookingDrawText(releaseTextBoxID, " が必要です。", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "\n", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "条件がジオの場合は今ホールドしているジオを消費します。", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "\n", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "お金の場合は所持金から消費します。", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "\n", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "解放しますか？", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "MOP", releaseTextPaint);

            textBoxAdmin.updateText(releaseTextBoxID);

            releasePlateGroup.setDrawFlag(true);
            releasePlateGroup.setUpdateFlag(true);

            geoSlotGroup.setUpdateFlag(false);
        }
    }


    public void update(){
        geoSlotGroup.update();

        releasePlateGroup.update();
        if (releasePlateGroup.getUpdateFlag()) {
            int content = releasePlateGroup.getTouchContentNum();
            switch (content) {
                case (0)://解放する
                    //解放するための色々な処理
                    if (focusGeoSlot.geoSlotRelease()) {
                        //TODO 解放したことを通知
                        soundAdmin.play("levelup00");
                    } else {
                        //TODO 条件を満たしていないことを通知
                        soundAdmin.play("cancel00");
                    }
                    releasePlateGroup.setDrawFlag(false);
                    releasePlateGroup.setUpdateFlag(false);
                    textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
                    geoSlotGroup.setUpdateFlag(true);

                    break;
                case (1)://やめる
                    soundAdmin.play("cancel00");
                    releasePlateGroup.setDrawFlag(false);
                    releasePlateGroup.setUpdateFlag(false);
                    textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
                    geoSlotGroup.setUpdateFlag(true);
                    break;
            }
        }

        checkInventrySelect();

        //backPlateGroup.update();
        //textBoxAdmin.setTextBoxExists(statusTextBoxID, worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.GEO_MAP_SELECT);

        //textBoxAdmin.update();
    }



    public void updateInStatus(){
        geoSlotGroup.update();
        //backPlateGroup.update();
    }

    public void draw() {
        geoSlotGroup.draw();

        //Holdの表示
        if (isHoldGeoObject()) {
            graphic.bookingDrawBitmapData(
                    graphic.makeImageContext(holdGeoObject.getItemImage(), 100, 100, Constants.GeoSlotParam.GEO_SLOT_SCALE, Constants.GeoSlotParam.GEO_SLOT_SCALE, 0, 255, false)
            );
        }

        //ListBox
        releasePlateGroup.draw();
        //backPlateGroup.draw();
    }

    public void drawInStatus() {
        geoSlotGroup.draw();
        //backPlateGroup.draw();
    }

    //Inventryから何か選択されているならそれを格納
    public void checkInventrySelect() {
        InventryData inventryData = userInterface.getInventryData();
        if (inventryData != null) {
            GeoObjectData tmp = (GeoObjectData)inventryData.getItemData();
            if (inventryData.getItemNum() > 0 && tmp.getName() != null && tmp.getSlotSetName().equals("noSet")) {
                setHoldGeoObject((GeoObjectData) inventryData.getItemData());
                userInterface.setInventryData(null);
            }
        }
    }

    public void initUIs() {
        textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
        releasePlateGroup.setDrawFlag(false);
        releasePlateGroup.setUpdateFlag(false);
    }

    public GeoSlotAdminManager.MODE getMode() {
        return geoSlotAdminManager.getMode();
    }

    /*
    //InventryにGeoを加える
    public void addToInventry(GeoObjectData geoObjectData) {
        geoSlotAdminManager.addToInventry(geoObjectData);
    }

    //InventryからGeoを消す
    public void deleteFromInventry(GeoObjectData geoObjectData) {
        geoSlotAdminManager.deleteFromInventry(geoObjectData);
    }
    */

    public void setSlotData(GeoObjectData geoObjectData, int id) {
        geoObjectData.setSlotSetID(id);
        geoObjectData.setSlotSetName(t_name);
    }

    public void popSlotData(GeoObjectData geoObjectData) {
        geoObjectData.setSlotSetID(-1);
        geoObjectData.setSlotSetName("noSet");
    }

    public void calcStatus() {
        geoSlotAdminManager.calcStatus();
    }

    // ***** Getter *****
    private List<Integer> getTreeCode() {
        return geoSlotMapDB.getInt(t_name, "children_num");
    }
    public boolean isHoldGeoObject() {
        return holdGeoObject != null;
    }
    public String getName() { return t_name; }
    public GeoObjectData getHoldGeoObject() {
        return holdGeoObject;
    }
    public List<GeoSlot> getGeoSlots() { return geoSlots; }
    public GeoCalcSaverAdmin getGeoCalcSaverAdmin() { return geo_calc_saver_admin; }
    public PlayerStatus getPlayerStatus() { return playerStatus; }

    // ***** Setter *****
    public void setHoldGeoObject(GeoObjectData geoObjectData) {
        holdGeoObject = geoObjectData;
    }
    public void setFocusGeoSlot(GeoSlot _focusGeoSlot) {
        focusGeoSlot = _focusGeoSlot;
    }
    public static void setGeoSlotMapDB(MyDatabase _geoSlotMapDB) { geoSlotMapDB = _geoSlotMapDB; }
    public static void setGeoSlotEventDB(MyDatabase _geoSlotEventDB) { geoSlotEventDB = _geoSlotEventDB; }

    //** Created by ina **

    //rewrire by kmhanko
    //GeoSlot geoSlots[] = new GeoSlot[10];
    //UserInterface user_interface;

    /* rewrite by kmhanko
    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;

        for(int i = 0; i < 10; i++) {
            geoSlots.get(i) = new GeoSlot();
            geoSlots.get(i).init();
        }

        for(int i = 0; i < 10; i++) {
            geoSlots.get(i).setParam(30+50*i, 30+50*i, 20);
            geoSlots.get(i).setTouchID(user_interface.setCircleTouchUI(30+50*i,30+50*i,30));
        }
    }
    */

    /*rewrite by kmhanko
    public void update(){

        for(int i = 0; i < 10; i++) {
            if(user_interface.checkUI(geoSlots.get(i).getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true){
                System.out.println(user_interface.getItemID());
                geoSlots.get(i).setItemID(user_interface.getItemID());
            }
        }
    }
    */


    /*rewrite by kmhanko
    public void draw(Canvas canvas) {

        for(int i = 0; i < 10; i++) {
            geoSlots.get(i).draw(canvas);
        }
    }
    */
}