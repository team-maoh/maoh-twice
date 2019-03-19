package com.maohx2.kmhanko.geonode;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.Text.BoxItemPlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.UI.UserInterface;

// Added by kmhanko
import com.maohx2.ina.GameSystem.WorldModeAdmin;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.WindowPlate.WindowTextPlate;
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
import com.maohx2.kmhanko.plate.BoxImageTextPlate;
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

    /*

    スロットで使える範囲

    (0, 100) - ( 1200, 850 )が最大なので
    (50, 150) - ( 1150, 800 )　が実質の範囲になる。



     */

public class GeoSlotAdmin {

    //** Created by kmhanko **//

    static MyDatabase geoSlotMapDB;
    static MyDatabase geoSlotEventDB;


    static public final int GEO_SLOT_MAX = 64;
    static public final int TOUCH_R = 90;

    String t_name; //このGeoSlotAdmin = ジオマップの名称 = Table名

    List<Integer> tree_code = new ArrayList<Integer>(); //GeoSlotのツリー上構造を表す数値列
    List<GeoSlot> geoSlots = new ArrayList<GeoSlot>(GEO_SLOT_MAX);
    GeoSlot grand_geo_slot; //ツリーの中心であるGeoSlot
    GeoCalcSaverAdmin geo_calc_saver_admin; //GeoSlotの計算を行い、計算結果を格納する

    Graphic graphic;
    TextBoxAdmin textBoxAdmin;
    UserInterface userInterface;
    WorldModeAdmin worldModeAdmin;
    GeoSlotAdminManager geoSlotAdminManager;

    PlateGroup<GeoSlot> geoSlotGroup;
    PlateGroup<BoxImageTextPlate> releasePlateGroup;//解放する/やめる　の選択

    WindowTextPlate geoReleaseNamePlate;
    Paint geoReleaseNamePaint;

    Paint releaseTextPaint;

    InventryS geoInventry;


    GeoSlot focusGeoSlot; //今操作している(条件の解放のため選択している)GeoSlot

    int releaseTextBoxID; //スロット条件解放の説明文を表示するためのTextBoxID


    PlayerStatus playerStatus;

    SoundAdmin soundAdmin;

    EffectAdmin effectAdmin;

    public enum INVENTRY_MODE {
        SET,
        RELEASE
    }
    INVENTRY_MODE inventryMode;

    public void release() {
        System.out.println("takanoRelease : GeoSlotAdmin");
        if (tree_code != null) {
            tree_code.clear();
            tree_code = null;
        }
        if (geoSlots != null) {
            for (int i = 0; i < geoSlots.size(); i++) {
                if (geoSlots.get(i) != null) {
                    geoSlots.get(i).release();
                }
            }
            geoSlots.clear();
            geoSlots = null;
        }
        if (geoSlotGroup != null) {
            geoSlotGroup.release();
            geoSlotGroup = null;
        }
        if (releasePlateGroup != null) {
            releasePlateGroup.release();
            releasePlateGroup = null;
        }
        if (geoReleaseNamePaint != null) {
            geoReleaseNamePaint = null;
        }
        if (okButtonGroup != null) {
            okButtonGroup.release();
        }
        if (geoRemoveButton != null) {
            geoRemoveButton.release();
        }
        if (cancelButtonGroup != null) {
            cancelButtonGroup.release();
        }
    }

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
        initReleasePlate();
        initGeoRemoveButton();
        initOkButton();
        initWindow();
        initCancelButton();
        initUIs();
    }

    public void start() {
        for(int i = 0; i < geoSlots.size(); i++) {
            geoSlots.get(i).updateGeoBaseTileImageContext();
        }
        this.initUIs();
    }

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
                graphic.makeImageContext(graphic.searchBitmap("apple"), 0, 0, 5.0f, 5.0f, 0.0f, 128, false),
                graphic.makeImageContext(graphic.searchBitmap("apple"), 0, 0, 6.0f, 6.0f, 0.0f, 128, false)
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
            if ( i == 0 ) {
                geoSlots.get(i).setParam(xs.get(i), ys.get(i), TOUCH_R, true);
            } else {
                geoSlots.get(i).setParam(xs.get(i), ys.get(i), TOUCH_R, false);
            }
            //TouchIDセット
            //geoSlots.get(i).setTouchID(userInterface.setCircleTouchUI(xs.get(i), ys.get(i), 100));
            geoSlots.get(i).setReleaseEvent(release_events.get(i));
            geoSlots.get(i).setRestriction(restrictions.get(i));
            geoSlots.get(i).setID(i);
        }

        //plateGroupインスタンス化
        geoSlotGroup = new PlateGroup<GeoSlot>((GeoSlot[])grand_geo_slot.getGeoSlots().toArray(new GeoSlot[0]));
    }
    private void initWindow() {
        geoReleaseNamePlate = new WindowTextPlate(graphic, new int[]{100, 300, 1000, 500});
        geoReleaseNamePaint = new Paint();
        geoReleaseNamePaint.setTextSize(40);
        geoReleaseNamePaint.setStrokeWidth(20);
        geoReleaseNamePaint.setColor(Color.WHITE);

        geoReleaseNamePlate.setDrawFlag(false);
    }

    private void initReleasePlate() {

        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);

        //「解放する」「解放しない」ボタン表示　→　ListBox<Button>の完成待ち
        releasePlateGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "解放する",
                                textPaint
                        ),
                        new BoxImageTextPlate(
                                graphic, userInterface,
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

    PlateGroup<BoxImageTextPlate> cancelButtonGroup;
    private void initCancelButton() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(Constants.POPUP_WINDOW.BUTTON_TEXT_SIZE);
        textPaint.setARGB(255, 255, 255, 255);

        cancelButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{250, 500+5, 850, 600}, "やめる", textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //OKが押された時の処理
                                soundAdmin.play("enter00");
                                initUIs();
                            }
                        }
                });
        cancelButtonGroup.setUpdateFlag(false);
        cancelButtonGroup.setDrawFlag(false);
    }

    PlateGroup<BoxImageTextPlate> okButtonGroup;
    private void initOkButton() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(Constants.POPUP_WINDOW.BUTTON_TEXT_SIZE);
        textPaint.setARGB(255, 255, 255, 255);

        okButtonGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{Constants.POPUP_WINDOW.OK_LEFT, Constants.POPUP_WINDOW.OK_UP, Constants.POPUP_WINDOW.OK_RIGHT, Constants.POPUP_WINDOW.OK_BOTTOM}, "OK", textPaint
                        ) {
                            @Override
                            public void callBackEvent() {
                                //OKが押された時の処理
                                soundAdmin.play("enter00");
                                initUIs();
                            }
                        }
                });
        okButtonGroup.setUpdateFlag(false);
        okButtonGroup.setDrawFlag(false);
    }
    private void initTextBox() {

        releaseTextBoxID = textBoxAdmin.createTextBox(SELECT_WINDOW.MESS_LEFT,SELECT_WINDOW.MESS_UP,SELECT_WINDOW.MESS_RIGHT,SELECT_WINDOW.MESS_BOTTOM, SELECT_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(releaseTextBoxID, false);
        textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
        releaseTextPaint = new Paint();
        releaseTextPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        releaseTextPaint.setColor(Color.WHITE);
    }

    PlateGroup<BoxImageTextPlate> geoRemoveButton;
    private void initGeoRemoveButton() {
        Paint textPaint1 = new Paint();
        textPaint1.setTextSize(50);
        textPaint1.setARGB(255, 255, 255, 255);

        geoRemoveButton = new PlateGroup<>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE,
                                new int[]{ 1200, 100, 1400, 200 },
                                new String[] { "外す"},
                                new Paint[] { textPaint1 },
                                new WindowTextPlate.TextPosition[] { WindowTextPlate.TextPosition.CENTER }
                        ) {
                            @Override
                            public void callBackEvent() {
                                //OKが押された時の処理
                                soundAdmin.play("enter00");

                                //ジオを外す
                                removeGeoObject();
                            }
                        }
                });
        geoRemoveButton.setUpdateFlag(false);
        geoRemoveButton.setDrawFlag(false);
    }




    public void geoUpdate() {
        calcGeoSlot();
        for(int i = 0; i < geoSlots.size(); i++) {
            geoSlots.get(i).drawLine();
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

    //GeoSlotを解放しますか？的なメッセージ
    public void geoSlotReleaseChoiceMessage() {

        if (!focusGeoSlot.isEventClear()) {
            //TextBox表示「ここを解放するためには　？？？　が必要」
            textBoxAdmin.setTextBoxExists(releaseTextBoxID, true);
            textBoxAdmin.resetTextBox(releaseTextBoxID);

            textBoxAdmin.bookingDrawText(releaseTextBoxID, "ガイア「ごめ〜ん、ここを使うなら、 ", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "\n", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, focusGeoSlot.getReleaseEvent(), releaseTextPaint);//TODO:イベント名そのままになっているが、これは仮
            textBoxAdmin.bookingDrawText(releaseTextBoxID, " を私に献上しなさい！」", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "\n", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "献上してこのジオスロットを解放しますか？", releaseTextPaint);
            textBoxAdmin.bookingDrawText(releaseTextBoxID, "MOP", releaseTextPaint);

            textBoxAdmin.updateText(releaseTextBoxID);

            releasePlateGroup.setDrawFlag(true);
            releasePlateGroup.setUpdateFlag(true);

            geoSlotGroup.setUpdateFlag(false);
        }
    }

    //GeoSlotを解放しますか？的なメッセージ
    public void notEnoughMaonMessage() {
        textBoxAdmin.setTextBoxExists(releaseTextBoxID, true);
        textBoxAdmin.resetTextBox(releaseTextBoxID);

        textBoxAdmin.bookingDrawText(releaseTextBoxID, "所持Maonが足りません", releaseTextPaint);
        textBoxAdmin.bookingDrawText(releaseTextBoxID, "MOP", releaseTextPaint);

        textBoxAdmin.updateText(releaseTextBoxID);

        okButtonGroup.setDrawFlag(true);
        okButtonGroup.setUpdateFlag(true);

        geoSlotGroup.setUpdateFlag(false);
    }

    public void geoReleaseNameWindow(String name) {
        geoReleaseNamePlate.setText(0, "解放に必要なジオオブジェクト", geoReleaseNamePaint, WindowTextPlate.TextPosition.UP);
        geoReleaseNamePlate.setText(1, "「 " + name + " 」", geoReleaseNamePaint, WindowTextPlate.TextPosition.CENTER);
        geoReleaseNamePlate.setText(2, "右のインベントリから選択してください", geoReleaseNamePaint, WindowTextPlate.TextPosition.DOWN);

        geoReleaseNamePlate.setDrawFlag(true);

    }

    //ジオスロットがタッチされた場合の処理
    public void geoSlotTouched() {
        //GeoInventryの呼び出し
        geoInventry.setDrawAndUpdateFlag(true);
        inventryMode = INVENTRY_MODE.SET;
        geoRemoveButton.setUpdateFlag(true);
        geoRemoveButton.setDrawFlag(true);
        //選択しているジオスロットをマークする
    }

    //解放する、が選ばれた場合の処理
    public void removeTouched() {
        //GeoInventryの呼び出し
        geoInventry.setDrawAndUpdateFlag(true);
        inventryMode = INVENTRY_MODE.RELEASE;
        cancelButtonGroup.setDrawFlag(true);
        cancelButtonGroup.setUpdateFlag(true);
        geoRemoveButton.setUpdateFlag(false);
        geoRemoveButton.setDrawFlag(false);
    }

    //インベントリのジオがタッチされた場合の処理
    public void setGeoObject(GeoObjectData geoObjectData) {
        switch (inventryMode) {
            case SET:
                if (focusGeoSlot != null) {
                    focusGeoSlot.setGeoObject(geoObjectData);
                    this.initUIs();
                }
                break;
            case RELEASE:
                if (focusGeoSlot != null) {
                    if (focusGeoSlot.releaseByGeoObject(geoObjectData)) {
                        this.geoReleasedEvent(true);
                        this.initUIs();
                    } else {
                        soundAdmin.play("cannot_exit_room");
                    }
                }
                break;
        }
    }

    public void removeGeoObject() {
        if (focusGeoSlot != null) {
            focusGeoSlot.outGeoObject();
            this.initUIs();
        }
    }

    //外すボタンが押された場合の処理


    public void update(){

        releasePlateGroup.update();
        if (!textBoxAdmin.getTextBoxExists(releaseTextBoxID)) {
            geoInventry.updata();
            geoSlotGroup.update();
            geoRemoveButton.update();
            cancelButtonGroup.update();
        }
        okButtonGroup.update();

        this.checkReleasePlateGroup();
        this.checkInventrySelect();
    }

    public void updateInStatus(){
        update();
    }

    public void draw() {
        geoSlotGroup.draw();
        releasePlateGroup.draw();
        geoRemoveButton.draw();
        okButtonGroup.draw();
        geoReleaseNamePlate.draw();
        cancelButtonGroup.draw();
    }

    public void drawInStatus() {
        geoSlotGroup.draw();
    }

    //Inventryから何か選択されているならそれをジオスロットに格納する
    private void checkInventrySelect() {
        InventryData inventryData = userInterface.getInventryData();
        if (inventryData != null) {
            if (inventryData.getItemData() != null) {
                if (inventryData.getItemData().getItemKind() == Constants.Item.ITEM_KIND.GEO) {
                    GeoObjectData tmp = (GeoObjectData) inventryData.getItemData();
                    if (inventryData.getItemNum() > 0 && tmp.getName() != null && tmp.getSlotSetName().equals("noSet")) {
                        this.setGeoObject((GeoObjectData)inventryData.getItemData());
                        userInterface.setInventryData(null);
                    }
                }
            }
        }
    }

    //解放しますか？のYes/No
    private void checkReleasePlateGroup() {
        if (releasePlateGroup.getUpdateFlag()) {
            int content = releasePlateGroup.getTouchContentNum();
            switch (content) {
                case (0)://解放する
                    //解放するための色々な処理
                    textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
                    focusGeoSlot.geoSlotRelease();
                    releasePlateGroup.setDrawFlag(false);
                    releasePlateGroup.setUpdateFlag(false);
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
    }

    public void geoReleasedEvent(boolean x) {
        if (x) {
            soundAdmin.play("levelup00");
        } else {
            soundAdmin.play("cancel00");
        }
        textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
        geoSlotGroup.setUpdateFlag(true);
    }



    public void clearGeoSlotLineEffect() {
        for(int i = 0; i < geoSlots.size(); i++) {
            geoSlots.get(i).clearGeoSlotLineEffect();
        }
    }

    public void initUIs() {
        textBoxAdmin.setTextBoxExists(releaseTextBoxID, false);
        releasePlateGroup.setDrawFlag(false);
        releasePlateGroup.setUpdateFlag(false);
        geoRemoveButton.setDrawFlag(false);
        geoRemoveButton.setUpdateFlag(false);
        geoInventry.setDrawAndUpdateFlag(false);
        okButtonGroup.setDrawFlag(false);
        okButtonGroup.setUpdateFlag(false);
        geoReleaseNamePlate.setDrawFlag(false);
        focusGeoSlot = null;
        if (geoSlotGroup != null) {
            geoSlotGroup.setUpdateFlag(true);
            geoSlotGroup.setDrawFlag(true);
        }
        cancelButtonGroup.setDrawFlag(false);
        cancelButtonGroup.setUpdateFlag(false);
    }

    public GeoSlotAdminManager.MODE getMode() {
        return geoSlotAdminManager.getMode();
    }

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

    private List<Integer> getTreeCode() {
        return geoSlotMapDB.getInt(t_name, "children_num");
    }
    public String getName() { return t_name; }
    public List<GeoSlot> getGeoSlots() { return geoSlots; }
    public GeoCalcSaverAdmin getGeoCalcSaverAdmin() { return geo_calc_saver_admin; }
    public PlayerStatus getPlayerStatus() { return playerStatus; }

    public void setFocusGeoSlot(GeoSlot _focusGeoSlot) {
        focusGeoSlot = _focusGeoSlot;
    }
    public static void setGeoSlotMapDB(MyDatabase _geoSlotMapDB) { geoSlotMapDB = _geoSlotMapDB; }
    public static void setGeoSlotEventDB(MyDatabase _geoSlotEventDB) { geoSlotEventDB = _geoSlotEventDB; }

}