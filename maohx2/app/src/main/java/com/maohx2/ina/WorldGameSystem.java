package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.EquipmentInventrySaver;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.ina.Constants.GAMESYSTEN_MODE.WORLD_MODE;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.GeoPresent.GeoPresentManager;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.Saver.GeoPresentSaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectDataCreater;
import com.maohx2.kmhanko.itemshop.ItemShopAdmin;
import com.maohx2.kmhanko.effect.*;
import com.maohx2.kmhanko.plate.BackPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Paint;

/**
 * Created by ina on 2017/10/01.
 */

public class WorldGameSystem {

    SurfaceHolder holder;
    Paint paint = new Paint();
    Canvas canvas;
    TextBoxAdmin text_box_admin;
    //ListBoxAdmin list_box_admin;
    GeoSlotAdmin geo_slot_admin;
    GeoSlotAdminManager geoSlotAdminManager;
    MyDatabaseAdmin databaseAdmin;
    Graphic graphic;

    DungeonSelectManager dungeonSelectManager;
    ItemShopAdmin itemShopAdmin;
    ItemDataAdminManager itemDataAdminManager;
    GeoPresentManager geoPresentManager;

    EffectAdmin effectAdmin;
    SoundAdmin soundAdmin;

    BattleUserInterface world_user_interface;

    WorldModeAdmin worldModeAdmin;

    WorldActivity worldActivity;

    PlayerStatus playerStatus;
    MaohMenosStatus maohMenosStatus;

    GeoInventrySaver geoInventrySaver;
    ExpendItemInventrySaver expendItemInventrySaver;
    GeoSlotSaver geoSlotSaver;
    GeoPresentSaver geoPresentSaver;
    ActivityChange activityChange;

    //TODO いな依頼:引数にUI,Graphicが入って居るためGlobalDataに設置できない
    InventryS geoInventry;
    InventryS expendItemInventry;

    //TODO いな依頼　Inventryのupdateを呼ばないと真っ黒。あとアクティブ関係
    PaletteAdmin palette_admin;
    EquipmentInventrySaver equipmentInventrySaver;
    InventryS equipmentInventry;

    BitmapData backGround;

    String talkContent[][] = new String[100][];
    ImageContext talkChara[] = new ImageContext[100];


    public void init(BattleUserInterface _world_user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin, WorldActivity _worldActivity, ActivityChange _activityChange) {
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        soundAdmin = _soundAdmin;
        world_user_interface = _world_user_interface;
        activityChange = _activityChange;


        worldActivity = _worldActivity;
        GlobalData globalData = (GlobalData) worldActivity.getApplication();
        playerStatus = globalData.getPlayerStatus();
        maohMenosStatus = globalData.getMaohMenosStatus();
        //GeoInventry = globalData.getGeoInventry();

        worldModeAdmin = new WorldModeAdmin();
        worldModeAdmin.initWorld();

        soundAdmin.loadSoundPack("basic");

        text_box_admin = new TextBoxAdmin(graphic);
        text_box_admin.init(world_user_interface);

        itemDataAdminManager = new ItemDataAdminManager();
        itemShopAdmin = new ItemShopAdmin();

        effectAdmin = new EffectAdmin(graphic, databaseAdmin, soundAdmin);

        itemDataAdminManager.init(databaseAdmin, graphic);

        geoInventrySaver = globalData.getGeoInventrySaver();
        geoInventry = globalData.getGeoInventry();
        expendItemInventrySaver = globalData.getExpendItemInventrySaver();
        expendItemInventry = globalData.getExpendItemInventry();

        geoSlotSaver = new GeoSlotSaver(databaseAdmin, "GeoSlotSave", "GeoSlotSave.db", 1, "ns", graphic);
        geoSlotAdminManager = new GeoSlotAdminManager(graphic, world_user_interface, worldModeAdmin, databaseAdmin, text_box_admin, playerStatus, geoInventry, geoSlotSaver, maohMenosStatus);


        dungeonSelectManager = new DungeonSelectManager(graphic, world_user_interface, text_box_admin, worldModeAdmin, databaseAdmin, geoSlotAdminManager, playerStatus, activityChange);

        geoSlotAdminManager.loadGeoSlot();

        itemShopAdmin.init(graphic, world_user_interface, worldModeAdmin, databaseAdmin, text_box_admin, itemDataAdminManager, expendItemInventry, geoInventry, playerStatus);
        itemShopAdmin.makeAndOpenItemShop(ItemShopAdmin.ITEM_KIND.EXPEND, "debug");

        canvas = null;

        GeoObjectDataCreater.setGraphic(graphic);
        // 仮。適当にGeo入れる GEO1が上がる能力は単一
        //TODO 同じの追加されたら個数とかないのに2とかになりそう
        for (int i = 0; i < 4; i++) {
            geoInventry.addItemData(GeoObjectDataCreater.getGeoObjectData(100));
        }


        geoPresentManager = new GeoPresentManager(
                graphic,
                world_user_interface,
                worldModeAdmin,
                databaseAdmin,
                text_box_admin,
                geoInventry,
                expendItemInventry,
                itemDataAdminManager.getExpendItemDataAdmin(),
                playerStatus
        );


        GeoPresentSaver.setGeoPresentManager(geoPresentManager);
        geoPresentSaver = new GeoPresentSaver(
                databaseAdmin,
                "GeoPresentSave",
                "GeoPresentSave.db",
                1, "ns"
        );

        geoPresentManager.setGeoPresentSaver(geoPresentSaver);

        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);

        //palette_admin = new PaletteAdmin(world_user_interface, graphic);
        equipmentInventry = globalData.getEquipmentInventry();
        equipmentInventrySaver = globalData.getEquipmentInventrySaver();

        palette_admin = new PaletteAdmin(world_user_interface, graphic, equipmentInventry, expendItemInventry);

        backGround = graphic.searchBitmap("firstBackground");

        geoSlotAdminManager.calcPlayerStatus();

        //TODO かり。戻るボタン
        initBackPlate();
    }


    public void update() {

/*
        if (world_user_interface.getTouchState() == Constants.Touch.TouchState.DOWN) {
            List<BitmapData> testBitmapData = new ArrayList<BitmapData>();
            int testID = effectAdmin.createEffect("test2", "打撃01", 9,1);
            effectAdmin.getEffect(testID).setPosition((int)world_user_interface.getTouchX(),(int)world_user_interface.getTouchY());
            effectAdmin.getEffect(testID).start();
        }
        */


        switch (worldModeAdmin.getMode()) {
            case DUNGEON_SELECT_INIT:
                backGround = graphic.searchBitmap("firstBackground");
                worldModeAdmin.setMode(WORLD_MODE.DUNGEON_SELECT);
            case DUNGEON_SELECT:
                dungeonSelectManager.update();
                break;
            case GEO_MAP_SELECT_INIT:
                backGround = graphic.searchBitmap("GeoMap");
                worldModeAdmin.setMode(WORLD_MODE.GEO_MAP_SELECT);
            case GEO_MAP_SELECT:
                dungeonSelectManager.update();
                break;
            case GEO_MAP_INIT:
                backGround = graphic.searchBitmap("GeoMap");
                worldModeAdmin.setMode(WORLD_MODE.GEO_MAP);
                geoSlotAdminManager.start();
            case GEO_MAP:
                geoSlotAdminManager.update();
                break;
            case SHOP_INIT:
                backGround = graphic.searchBitmap("City");
                worldModeAdmin.setMode(WORLD_MODE.SHOP);
            case SHOP:
                itemShopAdmin.update();
                break;
            case EQUIP_INIT:
                backGround = graphic.searchBitmap("firstBackground");//仮
                worldModeAdmin.setMode(WORLD_MODE.EQUIP);
            case EQUIP:
                equipmentInventry.updata();
                expendItemInventry.updata();
                palette_admin.update(false);
                backPlateGroup.update();
                break;
            case PRESENT_INIT:
                backGround = graphic.searchBitmap("firstBackground");//TODO 仮
                worldModeAdmin.setMode(WORLD_MODE.PRESENT);
            case PRESENT:
                geoPresentManager.update();
                break;
        }
/*
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getGetSlotMap())) {
            geoSlotAdminManager.update();
        }
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getWorldMap())) {
            dungeonSelectManager.update();
        }
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getShop())) {
            itemShopAdmin.update();
        }
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getPresent())) {
            geoPresentManager.update();
        }
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getEquip())) {
            equipmentInventry.updata();
            expendItemInventry.updata();
            palette_admin.update(false);
            backPlateGroup.update();
        }
        */

        text_box_admin.update();
        effectAdmin.update();
    }


    public void draw() {
        graphic.bookingDrawBitmapData(backGround, 0, 0, 1, 1, 0, 255, true);
        //graphic.bookingDrawBitmapData(graphic.searchBitmap("杖"),300,590);

        switch (worldModeAdmin.getMode()) {
            case DUNGEON_SELECT_INIT:
                break;
            case DUNGEON_SELECT:
                dungeonSelectManager.draw();
                break;
            case GEO_MAP_SELECT_INIT:
                break;
            case GEO_MAP_SELECT:
                dungeonSelectManager.draw();
                break;
            case GEO_MAP_INIT:
                break;
            case GEO_MAP:
                geoSlotAdminManager.draw();
                break;
            case SHOP_INIT:
                break;
            case SHOP:
                itemShopAdmin.draw();
                break;
            case EQUIP_INIT:
                break;
            case EQUIP:
                equipmentInventry.draw();
                expendItemInventry.draw();
                palette_admin.draw();
                world_user_interface.draw();
                backPlateGroup.draw();
                break;
            case PRESENT_INIT:
                break;
            case PRESENT:
                geoPresentManager.draw();
                break;
        }
        /*
        if (worldModeAdmin.getIsDraw(worldModeAdmin.getGetSlotMap())) {
            geoSlotAdminManager.draw();
        }
        if (worldModeAdmin.getIsDraw(worldModeAdmin.getWorldMap())) {
            dungeonSelectManager.draw();
        }
        if (worldModeAdmin.getIsDraw(worldModeAdmin.getShop())) {
            itemShopAdmin.draw();
        }
        if (worldModeAdmin.getIsDraw(worldModeAdmin.getPresent())) {
            geoPresentManager.draw();
        }

        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getEquip())) {
            equipmentInventry.draw();
            expendItemInventry.draw();
            palette_admin.draw();
            world_user_interface.draw();

            backPlateGroup.draw();
        }
        */

        text_box_admin.draw();
        effectAdmin.draw();

        graphic.draw();
    }

    //TODO 仮。もどるボタン
    PlateGroup<BackPlate> backPlateGroup;

    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[]{
                        new BackPlate(
                                graphic, world_user_interface, worldModeAdmin
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.DUNGEON_SELECT_INIT);
                                /*worldModeAdmin.setEquip(Constants.Mode.ACTIVATE.STOP);
                                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.ACTIVE);
                                */

                                expendItemInventry.save();
                                equipmentInventry.save();

                            }
                        }
                }
        );
    }


    int count = 0;
    int openningTextBoxID;
    boolean text_mode = false;

    public void openningInit() {

        openningTextBoxID = text_box_admin.createTextBox(50, 700, 1550, 880, 4);
        text_box_admin.setTextBoxUpdateTextByTouching(openningTextBoxID, true);
        text_box_admin.setTextBoxExists(openningTextBoxID, true);
        backGround = graphic.searchBitmap("firstBackground");
        dungeonSelectManager.update();

        paint.setTextSize(35);
        paint.setARGB(255, 255, 255, 255);



        talkContent[0] = new String[2];
        talkChara[0] = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[0][0] = "あいたた，なんだあいつ，ひどい目にあったな・・・．";
        talkContent[0][1] = "MOP";


        talkContent[1] = new String[4];
        talkChara[1] = graphic.makeImageContext(graphic.searchBitmap("ガイア立ち絵左向"), 1100, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[1][0] = "あらら，やられちゃったかぁ．";
        talkContent[1][1] = "\n";
        talkContent[1][2] = "もしかしたらうまくやってくれると思ったのに・・・．";
        talkContent[1][3] = "MOP";


        talkContent[2] = new String[4];
        talkChara[2] = graphic.makeImageContext(graphic.searchBitmap("e54-1"), 300, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[2][0] = "だれだお前．";
        talkContent[2][1] = "\n";
        talkContent[2][2] = "というか，うまくやるって何をだよ？";
        talkContent[2][3] = "MOP";


        talkContent[3] = new String[8];
        talkChara[3] = graphic.makeImageContext(graphic.searchBitmap("e19"), 1100, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[3][0] = "私はガイアよ．";
        talkContent[3][1] = "\n";
        talkContent[3][2] = "あなたが生まれたころからずっとあなたのことを見ていたわ．";
        talkContent[3][3] = "\n";
        talkContent[3][4] = "さっきあなたが出会ったのは魔王よ．";
        talkContent[3][5] = "\n";
        talkContent[3][6] = "ついにこの時が来てしまったのね・・・．";
        talkContent[3][7] = "MOP";


        talkContent[4] = new String[6];
        talkChara[4] = graphic.makeImageContext(graphic.searchBitmap("e54-1"), 300, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[4][0] = "魔王！？";
        talkContent[4][1] = "\n";
        talkContent[4][2] = "魔王ってなにいっているんだ，突然だな．";
        talkContent[4][3] = "\n";
        talkContent[4][4] = "それになんで俺が生まれた時からずっと俺のことを見てるんだよ，ストーカーか？";
        talkContent[4][5] = "MOP";


        talkContent[5] = new String[4];
        talkChara[5] = graphic.makeImageContext(graphic.searchBitmap("e19"), 1100, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[5][0] = "それはあなたがこの世界を流れる地脈のエネルギー．";
        talkContent[5][1] = "\n";
        talkContent[5][2] = "ジオエネルギーの加護を受けるものだからよ！！";
        talkContent[5][3] = "MOP";


        talkContent[6] = new String[2];
        talkChara[6] = graphic.makeImageContext(graphic.searchBitmap("e54-1"), 300, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[6][0] = "・・・．";
        talkContent[6][1] = "MOP";


        talkContent[7] = new String[4];
        talkChara[7] = graphic.makeImageContext(graphic.searchBitmap("e19"), 1100, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[7][0] = "あと，ストーカーはやめて．";
        talkContent[7][1] = "\n";
        talkContent[7][2] = "これでも女神様なんだから，あなたのことをずっと加護してたのよ，感謝しなさい．";
        talkContent[7][3] = "MOP";


        talkContent[8] = new String[4];
        talkChara[8] = graphic.makeImageContext(graphic.searchBitmap("e54-1"), 300, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[8][0] = "加護だか何だかわからないけど，図々しい女神さまだな．";
        talkContent[8][1] = "\n";
        talkContent[8][2] = "俺は加護なんてなくても生きていけるし，そんなものあったって何の得にもならない．";
        talkContent[8][3] = "MOP";


        talkContent[9] = new String[6];
        talkChara[9] = graphic.makeImageContext(graphic.searchBitmap("e19"), 1100, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[9][0] = "何よ，失礼な．";
        talkContent[9][1] = "\n";
        talkContent[9][2] = "ほら，最近いいことあったんじゃない？";
        talkContent[9][3] = "\n";
        talkContent[9][4] = "宝くじが当たったり，好きな女の子から告白されたり・・・．";
        talkContent[9][5] = "MOP";


        talkContent[10] = new String[2];
        talkChara[10] = graphic.makeImageContext(graphic.searchBitmap("e54-1"), 300, 300, 3.0f, 3.0f, 0, 255, false);
        talkContent[10][0] = "俺は宝くじも買わなければ，好きな女の子もいない．";
        talkContent[10][1] = "MOP";

    }

    public void openningUpdate() {

        graphic.bookingDrawBitmapData(backGround, 0, 0, 1, 1, 0, 255, true);

        dungeonSelectManager.draw();

        if(talkContent[count] != null) {
            drawCharaAndTouchCheck(talkChara[count]);
        }else{
            worldActivity.worldSurfaceView.setOpenningFlag(false);
        }

        if (text_mode == false) {
            if(talkContent[count] != null) {
                talk(talkContent[count]);
            }
        }

        text_box_admin.update();
        text_box_admin.draw();
    }


    public void openningDraw() {

        graphic.draw();
    }





    public void talk(String[] talkContent) {

        for(int i = 0; i < talkContent.length; i++){
            text_box_admin.bookingDrawText(openningTextBoxID,talkContent[i], paint);
        }
        text_box_admin.updateText(openningTextBoxID);
        text_box_admin.setTextBoxExists(openningTextBoxID, true);
        text_mode = true;
    }

    public void drawCharaAndTouchCheck(ImageContext _imageContext){

        graphic.bookingDrawBitmapData(_imageContext);
        if (world_user_interface.getTouchState() == Constants.Touch.TouchState.UP) {
            text_box_admin.setTextBoxExists(openningTextBoxID, false);
            text_mode = false;
            count++;
        }
    }
}








/*
memo作業内容

WorldGameSystemの中身を整理した
いなに画面切り替えオーダー
いなにボタンオーダー(ListBoxがButtonの配列をもち、それぞれの位置が決められる。それが画像だったりテキストだったりできて、
ListBox<T extends Button>としてButtonを継承したButtonを作れるようにする)
Shop,Present,GeoSlotあたりはいなのItemBag待ち
いなのボタンができたらメニューを並べる
いなの画面遷移ができたらメニューから遷移できるようにし、GeoWorldMapからGeoSlotMapに遷移できるようにする。
GeoSlotMapのここに置けないとか、一定の条件を満たした場合に解放されるやつ。
いなのセーブ機能

 */


