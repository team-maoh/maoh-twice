package com.maohx2.kmhanko.geonode;

/**
 * Created by user on 2017/10/27.
 */

import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.DungeonModeManage;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
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

public class GeoSlotAdminManager {
    static final String DB_NAME_GEOSLOTMAP = "GeoSlotMapDB";
    static final String DB_ASSET_GEOSLOTMAP = "GeoSlotMapDB.db";

    static final String DB_NAME_GEOSLOTEVENT = "GeoSlotEventDB";
    static final String DB_ASSET_GEOSLOTEVENT = "GeoSlotEvent.db";

    public final int GEO_SLOT_ADMIN_MAX = 16;
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

    enum MODE {
        WORLD, DUNGEON
    }

    MODE mode;

    public GeoSlotAdminManager(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, PlayerStatus _playerStatus, InventryS _geoInventry, GeoSlotSaver _geoSlotSaver, MaohMenosStatus _maohMenosStatus, SoundAdmin _soundAdmin, EffectAdmin _effectAdmin, DungeonModeManage _dungeonModeManage) {
        this(_graphic, _userInterface, null, _databaseAdmin, _textBoxAdmin, _playerStatus, _geoInventry, _geoSlotSaver,_maohMenosStatus,_soundAdmin, _effectAdmin);
        mode = MODE.DUNGEON;
        initMapIconPlate();
        dungeonModeManage = _dungeonModeManage;
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
        initStatusTextBox();
        initBackPlate();
        mode = MODE.WORLD;

    }


    public void start() {
        //activeGeoSlotAdmin.start();
        calcStatus();
        geoInventry.init(userInterface,graphic,1200, 100, 1600, 700, 10);
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
                                graphic, userInterface, worldModeAdmin
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                soundAdmin.play("cancel00");

                                if ( activeGeoSlotAdmin != null) {
                                    for (int i = 0; i < activeGeoSlotAdmin.getGeoSlots().size(); i++) {
                                        activeGeoSlotAdmin.getGeoSlots().get(i).clearGeoSlotLineEffect();
                                    }
                                }

                                if (getMode() == GeoSlotAdminManager.MODE.WORLD) {
                                    if (activeGeoSlotAdmin !=null) {
                                        activeGeoSlotAdmin.initUIs();
                                    }

                                    calcStatus();
                                    saveGeoInventry();
                                    saveGeoSlot();
                                    worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.DUNGEON_SELECT_INIT);
                                }
                                if (getMode() == GeoSlotAdminManager.MODE.DUNGEON) {
                                    getDungeonModeManage().setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
                                }
                                textBoxAdmin.setTextBoxExists(statusTextBoxID,false);
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
            this.setActiveGeoSlotAdmin(dungeonName.get(buttonID));
            statusTextBoxUpdate();
        }
    }

    public void update() {
        geoInventry.updata();
        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.update();
        }
        textBoxAdmin.setTextBoxExists(statusTextBoxID, worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.GEO_MAP);
        backPlateGroup.update();
    }

    public void updateInStatus() {
        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.updateInStatus();
        }

        mapIconPlateCheck();
        mapIconPlateGroup.update();
        backPlateGroup.update();
        textBoxAdmin.setTextBoxExists(statusTextBoxID, dungeonModeManage.getMode() == Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MAP);
    }

    public void draw() {
        effectAdmin.draw();
        geoInventry.draw();

        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.draw();
        }
        backPlateGroup.draw();
    }

    public void drawInStatus() {
        effectAdmin.draw();
        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.drawInStatus();
        }
        mapIconPlateGroup.draw();
        backPlateGroup.draw();
        //geoMapSelectButton.draw();
    }

    public void setActiveGeoSlotAdmin(String name) {
        for (int i = 0; i < geoSlotAdmins.size(); i++) {
            if (geoSlotAdmins.get(i) != null) {
                if (geoSlotAdmins.get(i).getName().equals(name)) {
                    activeGeoSlotAdmin = geoSlotAdmins.get(i);
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
        statusTextBoxUpdate();
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
        statusTextBoxUpdate();
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

    //ステータス表示関係

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

    public MODE getMode() {
        return mode;
    }

    public DungeonModeManage getDungeonModeManage() {
        return dungeonModeManage;
    }

    public void setStatusTextBoxFlag(boolean flag) {
        textBoxAdmin.setTextBoxExists(statusTextBoxID,flag);
    }


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
