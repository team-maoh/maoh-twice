package com.maohx2.ina.Activity;

import com.maohx2.horie.Tutorial.TutorialFlagData;
import com.maohx2.horie.Tutorial.TutorialFlagSaver;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.horie.map.MapStatusSaver;
import com.maohx2.ina.Constants;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.Saver.PlayerStatusSaver;
import com.maohx2.kmhanko.Talking.TalkAdmin;
import com.maohx2.kmhanko.Talking.TalkSaveDataAdmin;
import com.maohx2.kmhanko.geonode.GeoSlot;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataCreater;
import com.maohx2.horie.map.MapStatus;

import java.util.List;

// 所持アイテム(消費)

// アイテムスロットへのセット

// 所持武器〇

// 武器スロットへのセット〇

// 所持ジオ〇

// ジオスロットへのセット〇

// マップ解放〇

// ジオスロットの解放〇

// 会話イベントのフラグ〇

// 所持ジオ・ジオスロットへのセット

// 献上ポイント


/**
 * Created by user on 2019/01/24.
 */

public class DemoManager {


    //デモモードかどうか
    static final boolean demoMode = true;


    boolean startGameDemoEndFlag = false;
    boolean worldGameDemoEndFlag = false;
    boolean dungeonGameDemoEndFlag = false;
    Graphic graphic;

    public DemoManager() {}

    public void startGameDemo(TalkSaveDataAdmin talkSaveDataAdmin) {
        if (startGameDemoEndFlag || !demoMode) {
            return;
        }
        startGameDemoEndFlag = true;
        openingDemo(talkSaveDataAdmin);
    }

    public void worldGameDemo(Graphic graphic, InventryS equipmentInventry, PaletteAdmin paletteAdmin, InventryS expendItemInventry, ItemDataAdminManager itemDataAdminManager, PlayerStatus playerStatus, InventryS geoInventry, GeoSlotAdminManager geoSlotAdminManager, MapStatus mapStatus, MapStatusSaver mapStatusSaver, TalkSaveDataAdmin talkSaveDataAdmin, TutorialFlagData tutorialFlagData, TutorialFlagSaver tutorialFlagSaver) {
        if (worldGameDemoEndFlag) {
            return;
        }
        worldGameDemoEndFlag = true;

        //武器・アイテムここから

        EquipmentItemData tmpEquipmentItemData = new EquipmentItemData();

        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("剣");
        tmpEquipmentItemData.setImageName("剣");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("剣"));
        tmpEquipmentItemData.setPrice(6713);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.SWORD);
        tmpEquipmentItemData.setUseNum(80);
        tmpEquipmentItemData.setRadius(20);
        tmpEquipmentItemData.setDecayRate(0.92f);
        tmpEquipmentItemData.setTouchFrequency(8);
        tmpEquipmentItemData.setAutoFrequencyRate(1.3f);
        tmpEquipmentItemData.setAttack(959);
        tmpEquipmentItemData.setDefence(0);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,0);


        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("槍");
        tmpEquipmentItemData.setImageName("槍");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("槍"));
        tmpEquipmentItemData.setPrice(10003);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.SPEAR);
        tmpEquipmentItemData.setUseNum(60);
        tmpEquipmentItemData.setRadius(40);
        tmpEquipmentItemData.setDecayRate(0.94f);
        tmpEquipmentItemData.setTouchFrequency(4);
        tmpEquipmentItemData.setAutoFrequencyRate(1.4f);
        tmpEquipmentItemData.setAttack(1429);
        tmpEquipmentItemData.setDefence(0);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,1);


        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("銃");
        tmpEquipmentItemData.setImageName("銃");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("銃"));
        tmpEquipmentItemData.setPrice(8778);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.GUN);
        tmpEquipmentItemData.setUseNum(30);
        tmpEquipmentItemData.setRadius(30);
        tmpEquipmentItemData.setDecayRate(0.94f);
        tmpEquipmentItemData.setTouchFrequency(10);
        tmpEquipmentItemData.setAutoFrequencyRate(999999);
        tmpEquipmentItemData.setAttack(1254);
        tmpEquipmentItemData.setDefence(2);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,2);


        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("ナックル");
        tmpEquipmentItemData.setImageName("ナックル");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("ナックル"));
        tmpEquipmentItemData.setPrice(5698);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.BARE);
        tmpEquipmentItemData.setUseNum(80);
        tmpEquipmentItemData.setRadius(30);
        tmpEquipmentItemData.setDecayRate(0.93f);
        tmpEquipmentItemData.setTouchFrequency(10);
        tmpEquipmentItemData.setAutoFrequencyRate(999999);
        tmpEquipmentItemData.setAttack(814);
        tmpEquipmentItemData.setDefence(0);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,3);



        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("弓");
        tmpEquipmentItemData.setImageName("弓");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("弓"));
        tmpEquipmentItemData.setPrice(7987);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.BOW);
        tmpEquipmentItemData.setUseNum(50);
        tmpEquipmentItemData.setRadius(30);
        tmpEquipmentItemData.setDecayRate(0.95f);
        tmpEquipmentItemData.setTouchFrequency(6);
        tmpEquipmentItemData.setAutoFrequencyRate(1.1f);
        tmpEquipmentItemData.setAttack(1141);
        tmpEquipmentItemData.setDefence(2);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,4);



        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("杖");
        tmpEquipmentItemData.setImageName("杖");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("杖"));
        tmpEquipmentItemData.setPrice(11732);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.WAND);
        tmpEquipmentItemData.setUseNum(10);
        tmpEquipmentItemData.setRadius(70);
        tmpEquipmentItemData.setDecayRate(0.99f);
        tmpEquipmentItemData.setTouchFrequency(2);
        tmpEquipmentItemData.setAutoFrequencyRate(999999);
        tmpEquipmentItemData.setAttack(1676);
        tmpEquipmentItemData.setDefence(1);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,5);


        equipmentInventry.save();

        ExpendItemData tempExpendItemData = ((ExpendItemData)(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ポーション")));
        paletteAdmin.setDebugExpend(tempExpendItemData, 0);
        paletteAdmin.setDebugExpend(tempExpendItemData, 3);
        paletteAdmin.setDebugExpend(tempExpendItemData, 4);
        paletteAdmin.setDebugExpend(tempExpendItemData, 5);
        paletteAdmin.setDebugExpend(tempExpendItemData, 6);
        for(int j = 0; j < 7; j++){
            expendItemInventry.addItemData(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ポーション"));
        }

        tempExpendItemData = ((ExpendItemData)(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ハイポーション")));
        paletteAdmin.setDebugExpend(tempExpendItemData, 1);
        for(int j = 0; j < 3; j++){
            expendItemInventry.addItemData(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ハイポーション"));
        }

        tempExpendItemData = ((ExpendItemData)(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("エクスポーション")));
        paletteAdmin.setDebugExpend(tempExpendItemData, 2);
        for(int j = 0; j < 1; j++){
            expendItemInventry.addItemData(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("エクスポーション"));
        }

        //武器・アイテムここまで

        //ジオ関係
        geoInventryDemo(geoInventry, geoSlotAdminManager);//playerStatusDemoより先に呼ぶ

        //プレイヤーステータス関係
        playerStatusDemo(playerStatus);

        //ダンジョンクリア関係
        mapClearDemo(mapStatus, mapStatusSaver);

        //会話イベント関係
        talkDemo(talkSaveDataAdmin, playerStatus, mapStatus);//playerStatusDemo, mapClearDemoの後に呼ぶこと

        //チュートリアル関係
        tutorialDemo(tutorialFlagData, tutorialFlagSaver);
    }

    public void dungeonGameDemo() {
        if (dungeonGameDemoEndFlag || !demoMode) {
            return;
        }
        dungeonGameDemoEndFlag = true;
    }

    //チュートリアル管理
    private void tutorialDemo(TutorialFlagData tutorialFlagData, TutorialFlagSaver tutorialFlagSaver) {
        //1でチュートリアルを表示しない
        tutorialFlagData.setIs_tutorial_finished(1, 0);//装備画面
        tutorialFlagData.setIs_tutorial_finished(1, 1);//ショップ購入
        tutorialFlagData.setIs_tutorial_finished(1, 2);//ショップ売却
        tutorialFlagData.setIs_tutorial_finished(1, 3);//ジオ画面
        tutorialFlagSaver.save();
    }

    // プレイヤーステータス・魔王勝利回数・ダンジョンループクリア回数など
    private void playerStatusDemo(PlayerStatus playerStatus) {
        //プレイヤーステータス

        playerStatus.setBaseHP(60000);//22222
        playerStatus.setBaseAttack(67000);//22222
        playerStatus.setBaseDefence(6800);//2222
        playerStatus.setBaseLuck(22222 * 2);//2222
        playerStatus.setMoney(100000);//10000

        //魔王勝利回数
        playerStatus.setMaohWinCount(1);//0

        //ダンジョンチュートリアルを行ったか
        playerStatus.setTutorialInDungeon(0);//0

        //ダンジョンをすでに何周したか (1以上であるとき、自動的に全ての"ジオマップ"が解放されるので注意)
        playerStatus.setClearCount(0);//0

        //現在はダンジョンの何周目にセットしてあるか
        playerStatus.setNowClearCount(0);//0

        //エンディングを見たかどうか
        playerStatus.setEndingFlag(0);//0


        playerStatus.calcStatus();
        playerStatus.setEffectFlag(false);
        playerStatus.save();
    }
    
    private void geoInventryDemo(InventryS geoInventry, GeoSlotAdminManager geoSlotAdminManager) {
        GeoObjectData tempGeoObjectData;
        int[][] normalGeo = new int[][] {
                {5, 8, 10, 12, 15, 18, 20, 25, 30, 35},
                {5, 8, 10, 12, 15, 18, 20, 25, 30, 35},
                {5, 8, 10, 12, 15, 18, 20, 25, 30, 35},
        };
        double[][] rateGeo = new double[][] {
                {1.02, 1.04, 1.06, 1.08, 1.10, 1.15},
                {1.02, 1.04, 1.06, 1.08, 1.10, 1.15},
                {1.02, 1.04, 1.06, 1.08, 1.10, 1.15},
        };

        //{0, 1} = ForestのID1番にジオをセットするという意味
        //{X, 0} = どこにもセットしないの意味。第一要素の数字は無視される
        int[][][] normalGeoSlot = new int[][][] {
                {{4, 10}, {4, 11},{0, 1}, {0, 4}, {0, 5}, {0, 6} , {0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{1, 2}, {0, 8}, {4, 1}, {4, 4}, {4, 7}, {4, 8}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{1, 1}, {4, 3}, {4, 5}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
        };

        int[][][] rateGeoSlot = new int[][][] {
                {{0, 0}, {0, 0}, {4, 9}, {0, 0}, {0, 0}, {0, 0}},
                {{1, 1}, {4, 2}, {4, 6}, {0, 7}, {0, 0}, {0, 0}},
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},
        };

        geoInventry.deleteItemDataAll();

        Constants.Item.GEO_PARAM_KIND_NORMAL kindNormal = Constants.Item.GEO_PARAM_KIND_NORMAL.HP;
        Constants.Item.GEO_PARAM_KIND_RATE kindRate = Constants.Item.GEO_PARAM_KIND_RATE.HP_RATE;
        String slotName = "";

        for (int i = 0; i < normalGeo.length; i++) {
            switch (i) {
                case(0):
                    kindNormal = Constants.Item.GEO_PARAM_KIND_NORMAL.HP;
                    break;
                case(1):
                    kindNormal = Constants.Item.GEO_PARAM_KIND_NORMAL.ATTACK;
                    break;
                case(2):
                    kindNormal = Constants.Item.GEO_PARAM_KIND_NORMAL.DEFENCE;
                    break;
            }

            for (int j = 0; j < normalGeo[i].length; j++) {
                tempGeoObjectData = GeoObjectDataCreater.getGeoObjectData(normalGeo[i][j], kindNormal, false);

                if (normalGeoSlot[i][j][1] > 0) {
                    slotName = geoSlotAdminManager.getSlotNameDemo(normalGeoSlot[i][j][0]);
                    if (geoSlotAdminManager.getGeoSlotAdmin(slotName).getGeoSlots().size() >= normalGeoSlot[i][j][1]) {
                        tempGeoObjectData.setSlotSetName(slotName);
                        tempGeoObjectData.setSlotSetID(normalGeoSlot[i][j][1] - 1);
                    }
                }

                geoInventry.addItemData(tempGeoObjectData);
            }
        }

        for (int i = 0; i < rateGeo.length; i++) {
            switch (i) {
                case(0):
                    kindRate = Constants.Item.GEO_PARAM_KIND_RATE.HP_RATE;
                    break;
                case(1):
                    kindRate = Constants.Item.GEO_PARAM_KIND_RATE.ATTACK_RATE;
                    break;
                case(2):
                    kindRate = Constants.Item.GEO_PARAM_KIND_RATE.DEFENCE_RATE;
                    break;
            }

            for (int j = 0; j < rateGeo[i].length; j++) {
                tempGeoObjectData = GeoObjectDataCreater.getGeoObjectData(rateGeo[i][j], kindRate, false);

                if (rateGeoSlot[i][j][1] > 0) {
                    slotName = geoSlotAdminManager.getSlotNameDemo(rateGeoSlot[i][j][0]);
                    if (geoSlotAdminManager.getGeoSlotAdmin(slotName).getGeoSlots().size() >= rateGeoSlot[i][j][1]) {
                        tempGeoObjectData.setSlotSetName(slotName);
                        tempGeoObjectData.setSlotSetID(rateGeoSlot[i][j][1] - 1);
                    }
                }

                geoInventry.addItemData(tempGeoObjectData);
            }
        }

        //ジオスロット解放
        int[][][] geoSlotEvent = new int[][][] {
                {{2, 1}, {8, 1}, {11, 0}, {12, 0}},
                {{3, 1}, {4, 1}, {5, 1}, {8, 0}, {11, 0}},
                {{4, 1}, {6, 1}, {8, 1}, {10, 1}, {12, 1}},
                {{4, 1}, {6, 1}, {8, 0}, {10, 0}},
                {{2, 1}, {4, 1}, {6, 1}, {9, 1}, {11, 1}},
                {{3, 1}, {5, 1}, {7, 1}, {9, 0}, {11, 0}, {14, 0}},
                {{4, 1}, {7, 1}, {8, 1}, {9, 1}, {10, 1}, {11, 0}, {12, 0}, {13, 0}, {14, 0}},
                {{2, 1}, {3, 1}, {4, 1}, {5, 0}, {6, 0}, {7, 0},
                        {8, 1}, {9, 1}, {10, 1}, {11, 1}, {12, 1}, {13, 0}, {14, 0}, {15, 0}, {16, 0}, {17, 0}},
        };


        for(int i = 0; i < geoSlotEvent.length; i++) {
            slotName = geoSlotAdminManager.getSlotNameDemo(i);
            List<GeoSlot> geoSlots = geoSlotAdminManager.getGeoSlotAdmin(slotName).getGeoSlots();
            for (int j = 0; j < geoSlotEvent[i].length; j++) {
                if (geoSlotEvent[i][j][1] == 1) {
                    geoSlots.get(geoSlotEvent[i][j][0] - 1).setReleased(true);
                } else {
                    geoSlots.get(geoSlotEvent[i][j][0] - 1).setReleased(false);
                }
            }
        }

        geoSlotAdminManager.setSlot();
        geoInventry.save();
        geoSlotAdminManager.calcPlayerStatus();
        geoSlotAdminManager.calcMaohMenosStatus();
        geoSlotAdminManager.saveGeoSlot();
    }

    // マップ解放・ジオマップの解放
    private void mapClearDemo(MapStatus mapStatus, MapStatusSaver mapStatusSaver) {
        //"最新のループ回数としたとき"、どのダンジョンまで侵入可能な状態か？
        //ここで指定したダンジョンまで侵入可能
        //ループクリア回数が0である場合は、このダンジョンの一つ手前までジオマップに侵入可能
        Constants.DungeonKind.DUNGEON_KIND canEnterDungeon = Constants.DungeonKind.DUNGEON_KIND.SWAMP;

        int i = 0;
        switch (canEnterDungeon) {
            case FOREST:
                i = 0;
                break;
            case LAVA:
                i = 1;
                break;
            case SEA:
                i = 2;
                break;
            case CHESS:
                i = 3;
                break;
            case SWAMP:
                i = 4;
                break;
            case HAUNTED:
                i = 5;
                break;
            case DRAGON:
                i = 6;
                break;
        }
        for (int j = 0; j < i; j++) {
            mapStatus.setMapClearStatus(1, j);
        }
        mapStatusSaver.save();
    }

    //オープニングのフラグ
    private void openingDemo(TalkSaveDataAdmin talkSaveDataAdmin) {
        boolean openingMode = false;//trueで実行する
        if (!openingMode) {
            talkSaveDataAdmin.setTalkFlagByName("Opening_in_world", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("Opening_in_battle00", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("Opening_in_battle01", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("Opening_in_battle02", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("Opening_in_battle03", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("Opening_in_battle04", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("Opening_in_dungeon", true);//falseで実行する
        }
        talkSaveDataAdmin.save();
    }

    //会話イベントのフラグ
    private void talkDemo(TalkSaveDataAdmin talkSaveDataAdmin, PlayerStatus playerStatus, MapStatus mapStatus) {
        talkSaveDataAdmin.setTalkFlagByName("AfterMaoh002forDEMO", false);//falseで実行する

        //多くはプレイヤーステータスなどで設定した数値によって自動的に決定される
        if (playerStatus.getMaohWinCount() >= 1) {
            talkSaveDataAdmin.setTalkFlagByName("AfterMaoh001", true);//falseで実行する
        }
        if (playerStatus.getMaohWinCount() >= 3) {
            talkSaveDataAdmin.setTalkFlagByName("AfterMaoh003", true);//falseで実行する
        }
        if (playerStatus.getMaohWinCount() >= 6) {
            talkSaveDataAdmin.setTalkFlagByName("AfterMaoh006", true);//falseで実行する
        }
        if (playerStatus.getMaohWinCount() >= 8) {
            talkSaveDataAdmin.setTalkFlagByName("AfterMaoh008", true);//falseで実行する
        }
        if (playerStatus.getMaohWinCount() >= 9) {
            talkSaveDataAdmin.setTalkFlagByName("AfterMaoh009", true);//falseで実行する
        }
        if (playerStatus.getMaohWinCount() >= 10) {
            talkSaveDataAdmin.setTalkFlagByName("AfterMaoh010", true);//falseで実行する
        }
        if (playerStatus.getClearCount() > 0) {
            talkSaveDataAdmin.setTalkFlagByName("ClearChess", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("ClearDragon", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("ClearForest", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("ClearHaunted", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("ClearLava", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("ClearSea", true);//falseで実行する
            talkSaveDataAdmin.setTalkFlagByName("ClearSwamp", true);//falseで実行する
        } else {
            if (mapStatus.getMapClearStatus(0) == 1) {
                talkSaveDataAdmin.setTalkFlagByName("ClearForest", true);//falseで実行する
            }
            if (mapStatus.getMapClearStatus(1) == 1) {
                talkSaveDataAdmin.setTalkFlagByName("ClearLava", true);//falseで実行する
            }
            if (mapStatus.getMapClearStatus(2) == 1) {
                talkSaveDataAdmin.setTalkFlagByName("ClearSea", true);//falseで実行する
            }
            if (mapStatus.getMapClearStatus(3) == 1) {
                talkSaveDataAdmin.setTalkFlagByName("ClearChess", true);//falseで実行する
            }
            if (mapStatus.getMapClearStatus(4) == 1) {
                talkSaveDataAdmin.setTalkFlagByName("ClearSwamp", true);//falseで実行する
            }
            if (mapStatus.getMapClearStatus(5) == 1) {
                talkSaveDataAdmin.setTalkFlagByName("ClearHaunted", true);//falseで実行する
            }
            if (mapStatus.getMapClearStatus(6) == 1) {
                talkSaveDataAdmin.setTalkFlagByName("ClearDragon", true);//falseで実行する
            }
        }
        talkSaveDataAdmin.save();
    }

    static public boolean getDemoMode() {
        return demoMode;
    }


}
