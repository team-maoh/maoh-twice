package com.maohx2.kmhanko.geonode;

/**
 * Created by user on 2017/10/27.
 */

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SIDE_INVENTRY;
import com.maohx2.ina.GameSystem.DungeonModeManage;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.GameSystem.WorldModeAdmin;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.WindowPlate.WindowTextPlate;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.dungeonselect.MapIconPlate;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.plate.BackPlate;
import com.maohx2.kmhanko.plate.BoxImageTextPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;

//GeoSlotAdminの実体を持つクラス
//GeoSlotMapButtonの実体も持つ。


/*ダンジョン画面における挙動
・GeoInventryは非表示とする(これにより実質的にジオの配置不可能)
・GeoSlotをタッチしても反応しない
・現在選択中のジオを表示しない
・戻るボタンの処理が異なる
・各ダンジョンアイコンをInventryの代わりに設置し、ActiveGeoSlotAdminを切り替えることができる(魔王を含まない)
*/

/*
採掘画面の岩をでかく

 */

/*
ステータス表示

・アイコンを表示する
・HP : 0/0　数字の列を同じにする
TextBoxを使用するのをやめる


 */

public class GeoSlotAdminManager {
    static final String DB_NAME_GEOSLOTMAP = "GeoSlotMapDB";
    static final String DB_ASSET_GEOSLOTMAP = "GeoSlotMapDB.db";

    static final String DB_NAME_GEOSLOTEVENT = "GeoSlotEventDB";
    static final String DB_ASSET_GEOSLOTEVENT = "GeoSlotEvent.db";

    static public final int GEO_SLOT_ADMIN_MAX = 16;
    List<GeoSlotAdmin> geoSlotAdmins = new ArrayList<GeoSlotAdmin>(GEO_SLOT_ADMIN_MAX);
    GeoSlotAdmin activeGeoSlotAdmin = null;

    MyDatabaseAdmin databaseAdmin;
    Graphic graphic;
    UserInterface userInterface;
    TextBoxAdmin textBoxAdmin;
    WorldModeAdmin worldModeAdmin;
    //DungeonModeManage dungeonModeManage;

    PlayerStatus playerStatus;
    MaohMenosStatus maohMenosStatus;
    InventryS geoInventry;

    GeoSlotSaver geoSlotSaver;

    SoundAdmin soundAdmin;

    EffectAdmin effectAdmin;
    PlateGroup<MapIconPlate> mapIconPlateGroup;

    DungeonModeManage dungeonModeManage;

    boolean is_load_database;

    public enum MODE {
        WORLD_NORMAL, WORLD_SEE_ONLY, DUNGEON
    }

    MODE mode;

    public void release() {
        System.out.println("takanoRelease : GeoSlotAdminManager");
        activeGeoSlotAdmin = null;
        if (geoSlotAdmins != null) {
            for (int i = 0; i < geoSlotAdmins.size(); i++) {
                geoSlotAdmins.get(i).release();
            }
            geoSlotAdmins.clear();
            geoSlotAdmins = null;
        }
        mode = null;
        if (mapIconPlateGroup != null) {
            mapIconPlateGroup.release();
            mapIconPlateGroup = null;
        }
        if (backPlateGroup != null) {
            backPlateGroup.release();
            backPlateGroup = null;
        }
        if (dungeonName != null) {
            dungeonName.clear();
            dungeonName = null;
        }
    }

    public GeoSlotAdminManager(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, PlayerStatus _playerStatus, InventryS _geoInventry, GeoSlotSaver _geoSlotSaver, MaohMenosStatus _maohMenosStatus, SoundAdmin _soundAdmin, EffectAdmin _effectAdmin, DungeonModeManage _dungeonModeManage) {
        this(_graphic, _userInterface, null, _databaseAdmin, _textBoxAdmin, _playerStatus, _geoInventry, _geoSlotSaver,_maohMenosStatus,_soundAdmin, _effectAdmin);
        dungeonModeManage = _dungeonModeManage;
        mode = MODE.DUNGEON;
    }


    public GeoSlotAdminManager(Graphic _graphic, UserInterface _userInterface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, PlayerStatus _playerStatus, InventryS _geoInventry, GeoSlotSaver _geoSlotSaver, MaohMenosStatus _maohMenosStatus, SoundAdmin _soundAdmin, EffectAdmin _effectAdmin) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
        worldModeAdmin = _worldModeAdmin;
        playerStatus = _playerStatus;
        geoInventry = _geoInventry;
        geoSlotSaver = _geoSlotSaver;
        maohMenosStatus = _maohMenosStatus;
        soundAdmin = _soundAdmin;
        effectAdmin = _effectAdmin;

        addDatabase();
        this.loadGeoSlotDatabase();

        geoSlotSaver.setGeoSlotAdminManager(this);
        setSlot();
        //initStatusTextBox();
        initBackPlate();
        initMapIconPlate();

    }


    public void start() {
        activeGeoSlotAdmin.start();
        calcStatus();
        geoInventry.setPosition(SIDE_INVENTRY.INV_LEFT, SIDE_INVENTRY.INV_UP, SIDE_INVENTRY.INV_RIGHT,SIDE_INVENTRY.INV_BOTTOM,SIDE_INVENTRY.INV_CONTENT_NUM);
    }


    static final int DUNGEON_SELECT_BUTTON_RATE_TOURH_R = 110;
    static final String DUNGEON_SELECT_BUTTON_TABLE_NAME = "dungeon_select_button";
    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";

    List<String> dungeonName;

    PlateGroup<BackPlate> backPlateGroup;

    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[] {
                        new BackPlate(
                                graphic, userInterface
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                soundAdmin.play("cancel00");

                                if ( activeGeoSlotAdmin != null) {
                                    activeGeoSlotAdmin.clearGeoSlotLineEffect();
                                }

                                if (getMode() == GeoSlotAdminManager.MODE.WORLD_NORMAL || getMode() == GeoSlotAdminManager.MODE.WORLD_SEE_ONLY) {
                                    if (activeGeoSlotAdmin !=null) {
                                        activeGeoSlotAdmin.initUIs();
                                    }

                                    calcStatus();
                                    saveGeoInventry();
                                    saveGeoSlot();
                                    worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.GEO_MAP_SELECT_INIT);
                                }
                                if (getMode() == GeoSlotAdminManager.MODE.DUNGEON) {
                                    getDungeonModeManage().setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
                                }
                                //textBoxAdmin.setTextBoxExists(statusTextBoxID,false);
                            }
                        }
                }
        );
    }


    private void initMapIconPlate(){
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        MyDatabase database = databaseAdmin.getMyDatabase(DB_NAME);

        String w_script = "event = " + MyDatabase.s_quo("dungeon");

        dungeonName = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "name", w_script);
        List<String> dungeonNameExpress = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "dungeonName", w_script);
        List<String> imageName = database.getString(DUNGEON_SELECT_BUTTON_TABLE_NAME, "image_name", w_script);
        List<Integer> x = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "statusX", w_script);
        List<Integer> y = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "statusY", w_script);
        List<Integer> scale = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "scale", w_script);
        List<Integer> scale_feed = database.getInt(DUNGEON_SELECT_BUTTON_TABLE_NAME, "scale_feed", w_script);

        int size = dungeonName.size();

        List<MapIconPlate> mapIconPlateList = new ArrayList<MapIconPlate>();

        //インスタンス化
        for (int i = 0; i < size; i++) {
            mapIconPlateList.add(new MapIconPlate(
                    graphic, userInterface,
                    Constants.Touch.TouchWay.UP_MOMENT,
                    Constants.Touch.TouchWay.MOVE,
                    new int[] { x.get(i), y.get(i), DUNGEON_SELECT_BUTTON_RATE_TOURH_R },
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), scale.get(i), scale.get(i), 0.0f, 255, false),
                    graphic.makeImageContext(graphic.searchBitmap(imageName.get(i)),x.get(i), y.get(i), scale_feed.get(i), scale_feed.get(i), 0.0f, 255, false),
                    dungeonName.get(i),
                    dungeonNameExpress.get(i),
                    "dungeon"
            ));
        }


        MapIconPlate[] mapIconPlates = new MapIconPlate[mapIconPlateList.size()];
        mapIconPlateGroup = new PlateGroup<MapIconPlate>(mapIconPlateList.toArray(mapIconPlates));
    }

    public void mapIconPlateCheck() {
        int buttonID = mapIconPlateGroup.getTouchContentNum();
        if (buttonID != -1 ) {
            soundAdmin.play("enter00");
            if ( activeGeoSlotAdmin != null) {
                activeGeoSlotAdmin.clearGeoSlotLineEffect();
            }
            this.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
            //statusTextBoxUpdate();
        }
    }

    public void update() {
        if (mode == MODE.WORLD_NORMAL) {
            if (activeGeoSlotAdmin != null) {
                activeGeoSlotAdmin.updateInStatus();
            }
            //textBoxAdmin.setTextBoxExists(statusTextBoxID, worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.GEO_MAP);
            backPlateGroup.update();
        } else {
            updateSeeOnly();
        }
    }

    private void updateSeeOnly() {
        geoInventry.updata();
        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.update();
        }
        //mapIconPlateCheck();
        //mapIconPlateGroup.update();
        backPlateGroup.update();
        //textBoxAdmin.setTextBoxExists(statusTextBoxID, dungeonModeManage.getMode() == Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MAP);
    }

    public void draw() {
        if (mode == MODE.WORLD_NORMAL) {
            geoInventry.draw();
            if (activeGeoSlotAdmin != null) {
                activeGeoSlotAdmin.draw();
            }
            backPlateGroup.draw();
        } else {
            drawSeeOnly();
        }
    }

    private void drawSeeOnly() {
        //effectAdmin.draw();
        geoInventry.draw();
        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.drawInStatus();
        }
        //mapIconPlateGroup.draw();
        backPlateGroup.draw();
        //geoMapSelectButton.draw();
    }

    public void setActiveGeoSlotAdmin(String name) {
        for (int i = 0; i < geoSlotAdmins.size(); i++) {
            if (geoSlotAdmins.get(i) != null) {
                if (geoSlotAdmins.get(i).getName().equals(name)) {
                    activeGeoSlotAdmin = geoSlotAdmins.get(i);
                    activeGeoSlotAdmin.geoUpdate();
                    return;
                }
            }
        }
        throw new Error("☆タカノ:GeoSlotAdminManager#setActiveGeoSlotAdmin : There is no GeoSlotAdmin you request by name : " + name);
    }

    public void setNullToActiveGeoSlotAdmin() {
        activeGeoSlotAdmin = null;
    }

    public void closeGeoSlotAdmin() {
        activeGeoSlotAdmin = null;
    }

    public void addDatabase() {
        databaseAdmin.addMyDatabase(DB_NAME_GEOSLOTMAP, DB_ASSET_GEOSLOTMAP, 1, "r");
        databaseAdmin.addMyDatabase(DB_NAME_GEOSLOTEVENT, DB_ASSET_GEOSLOTEVENT, 1, "r");
    }

    public void loadGeoSlotDatabase() {
        if (is_load_database) {
            return;
        }
        is_load_database = true;

        MyDatabase geoSlotMapDB = databaseAdmin.getMyDatabase(DB_NAME_GEOSLOTMAP);
        MyDatabase geoSlotEventDB = databaseAdmin.getMyDatabase(DB_NAME_GEOSLOTEVENT);

        List<String> t_names = geoSlotMapDB.getTables();

        GeoSlotAdmin.setGeoSlotMapDB(geoSlotMapDB);
        GeoSlotAdmin.setGeoSlotEventDB(geoSlotEventDB);

        for (int i = 0; i < t_names.size(); i++) {
            GeoSlotAdmin new_geo_slot_admin = new GeoSlotAdmin(graphic, userInterface, worldModeAdmin, textBoxAdmin, this, playerStatus, geoInventry, soundAdmin, effectAdmin);
            new_geo_slot_admin.loadDatabase(t_names.get(i));
            geoSlotAdmins.add(new_geo_slot_admin);
        }

    }

    public void addToInventry(GeoObjectData geoObjectData) {
        geoInventry.addItemData(geoObjectData);
    }

    public void deleteFromInventry(GeoObjectData geoObjectData) {
        geoInventry.subItemData(geoObjectData);
    }

    public void calcStatus() {
        if (activeGeoSlotAdmin != null) {
            if (activeGeoSlotAdmin.getName().equals("Maoh")) {
                calcMaohMenosStatus();
            } else {
                calcPlayerStatus();
            }
        }
    }

    public void calcPlayerStatus() {
        playerStatus.initGeoStatus();
        for (int i = 0; i < geoSlotAdmins.size(); i++) {
            if (!geoSlotAdmins.get(i).getName().equals("Maoh")) {
                if (geoSlotAdmins.get(i) != null) {
                    geoSlotAdmins.get(i).calcGeoSlot();
                    GeoCalcSaverAdmin geoCSA = geoSlotAdmins.get(i).getGeoCalcSaverAdmin();
                    if (geoCSA != null) {
                        playerStatus.calcGeoStatus(geoCSA);
                    }
                }
            }
        }
        playerStatus.calcStatus();
        //statusTextBoxUpdate();
        //activeGeoSlotAdmin.statusTextBoxUpdate();
    }

    public void calcMaohMenosStatus() {
        maohMenosStatus.initGeoStatus();
        for (int i = 0; i < geoSlotAdmins.size(); i++) {
            if (geoSlotAdmins.get(i).getName().equals("Maoh")) {
                if (geoSlotAdmins.get(i) != null) {
                    geoSlotAdmins.get(i).calcGeoSlot();
                    GeoCalcSaverAdmin geoCSA = geoSlotAdmins.get(i).getGeoCalcSaverAdmin();
                    if (geoCSA != null) {
                        maohMenosStatus.calcGeoStatus(geoCSA);
                    }
                }
            }
        }
        maohMenosStatus.calcStatus();
        //statusTextBoxUpdate();
        //activeGeoSlotAdmin.statusTextBoxUpdate();
    }

    public void saveGeoInventry() {
        geoInventry.save();
    }

    public void saveGeoSlot() {
        geoSlotSaver.save();
    }

    public void loadGeoSlot() {
        geoSlotSaver.load();
    }

    public List<String> getGeoSlotAdminNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < geoSlotAdmins.size(); i++) {
            names.add(geoSlotAdmins.get(i).getName());
        }
        return names;
    }

    public List<GeoSlotAdmin> getGeoSlotAdmins() {
        return geoSlotAdmins;
    }
    public GeoSlotAdmin getGeoSlotAdmin(String name) {
        for (int j = 0; j < geoSlotAdmins.size(); j++) {
            if (name.equals(getGeoSlotAdminNames().get(j))) {
                return getGeoSlotAdmins().get(j);
            }
        }
        return null;
    }

    //この関数はManagerが作られたあとであって、かつGeoInventryが読まれた後に一度だけ実行する
    public void setSlot() {
        GeoObjectData geoObjectData;
        for (int i = 0; i < Constants.Inventry.INVENTRY_DATA_MAX; i++) {
            geoObjectData = (GeoObjectData) geoInventry.getItemData(i);
            if (geoObjectData != null) {
                for (int j = 0; j < geoSlotAdmins.size(); j++) {
                    if (geoObjectData.getSlotSetName() != null) {
                        if (geoObjectData.getSlotSetName().equals(getGeoSlotAdminNames().get(j))) {
                            geoSlotAdmins.get(j).getGeoSlots().get(geoObjectData.getSlotSetID()).pushGeoObject(geoObjectData);
                            System.out.println("☆タカノ:GeoSlotAdminManager#setSlot : " + getGeoSlotAdminNames().get(j) + "(" + geoObjectData.getSlotSetID() + ") " + "->" + geoObjectData.getName());

                        }
                    }
                }
            }
        }

    }

    public boolean isMaohGeoMap() {
        if (activeGeoSlotAdmin != null) {
            if (activeGeoSlotAdmin.getName().equals("Maoh")) {
                return true;
            }
        }
        return false;
    }

    //ステータス表示関係
/*
    int statusTextBoxID;

    public void initStatusTextBox() {
        statusTextBoxID = textBoxAdmin.createTextBox(0,600,300,900,7);
        textBoxAdmin.setTextBoxUpdateTextByTouching(statusTextBoxID,false);
        textBoxAdmin.setTextBoxExists(statusTextBoxID,false);
        statusTextBoxUpdate();
    }

    public void statusTextBoxUpdate() {
        if (activeGeoSlotAdmin == null) {
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
        } else if (activeGeoSlotAdmin.getName().equals("Maoh")) {
            textBoxAdmin.bookingDrawText(statusTextBoxID, "Maoh Weaken");
            textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
            textBoxAdmin.bookingDrawText(statusTextBoxID, "HP " + maohMenosStatus.getGeoHP());
            textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
            textBoxAdmin.bookingDrawText(statusTextBoxID, "Attack " + maohMenosStatus.getGeoAttack());
            textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
            textBoxAdmin.bookingDrawText(statusTextBoxID, "Deffence " + maohMenosStatus.getGeoDefence());
            textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
            textBoxAdmin.bookingDrawText(statusTextBoxID, "Luck " + maohMenosStatus.getGeoLuck());
            textBoxAdmin.bookingDrawText(statusTextBoxID, "MOP");
        }
        textBoxAdmin.updateText(statusTextBoxID);
    }
*/
    public MODE getMode() {
        return mode;
    }
    public void setMode(MODE _mode) {
        mode = _mode;
    }

    public String getSlotNameDemo(int i) {
        String slotName = "";
        switch (i) {
            case 0:
                slotName = "Forest";
                break;
            case 1:
                slotName = "Lava";
                break;
            case 2:
                slotName = "Sea";
                break;
            case 3:
                slotName = "Chess";
                break;
            case 4:
                slotName = "Swamp";
                break;
            case 5:
                slotName = "Haunted";
                break;
            case 6:
                slotName = "Dragon";
                break;
            case 7:
                slotName = "Maoh";
                break;
        }
        return slotName;
    }

    public DungeonModeManage getDungeonModeManage() {
        return dungeonModeManage;
    }
/*
    public void setStatusTextBoxFlag(boolean flag) {
        textBoxAdmin.setTextBoxExists(statusTextBoxID,flag);
    }


*/

}

/*
1/12
どうしたい？
→置けない判定
→一定の条件を満たした場合に置けるようになるもの

→スロットの女神ガードの解放条件
・お金を支払う
・一定条件を満たすジオスロットを捧げる




 */
