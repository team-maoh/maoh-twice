package com.maohx2.ina.Activity;

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

/**
 * Created by user on 2019/01/24.
 */

public class DemoManager {


    //デモモードかどうか
    final boolean demoMode = true;


    boolean startGameDemoEndFlag = false;
    boolean worldGameDemoEndFlag = false;
    boolean dungeonGameDemoEndFlag = false;

    public DemoManager() {
    }

    public void startGameDemo(TalkSaveDataAdmin talkSaveDataAdmin) {
        if (startGameDemoEndFlag || !demoMode) {
            return;
        }
        startGameDemoEndFlag = true;
        openingDemo(talkSaveDataAdmin);
    }
    public void worldGameDemo(PlayerStatus playerStatus, InventryS geoInventry, GeoSlotAdminManager geoSlotAdminManager, MapStatus mapStatus, MapStatusSaver mapStatusSaver, TalkSaveDataAdmin talkSaveDataAdmin) {
        if (worldGameDemoEndFlag || !demoMode) {
            return;
        }
        worldGameDemoEndFlag = true;
        geoInventryDemo(geoInventry, geoSlotAdminManager);//playerStatusDemoより先に呼ぶ
        playerStatusDemo(playerStatus);
        mapClearDemo(mapStatus, mapStatusSaver);
        talkDemo(talkSaveDataAdmin, playerStatus, mapStatus);//playerStatusDemo, mapClearDemoの後に呼ぶこと

    }
    public void dungeonGameDemo() {
        if (dungeonGameDemoEndFlag || !demoMode) {
            return;
        }
        dungeonGameDemoEndFlag = true;
    }


    // プレイヤーステータス・魔王勝利回数・ダンジョンループクリア回数など
    private void playerStatusDemo(PlayerStatus playerStatus) {
        //プレイヤーステータス
        playerStatus.setBaseHP(22222 * 2);//22222
        playerStatus.setBaseAttack(22222 * 2);//22222
        playerStatus.setBaseDefence(2222 * 2);//2222
        playerStatus.setBaseLuck(22222 * 2);//22222
        playerStatus.setMoney(1000000);//10000

        //魔王勝利回数
        playerStatus.setMaohWinCount(0);//0

        //ダンジョンチュートリアルを行ったか
        playerStatus.setTutorialInDungeon(1);//0

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

    // 所持アイテム(消費)

    // アイテムスロットへのセット

    // 所持武器

    // 武器スロットへのセット

    // 所持ジオ・ジオスロットへのセット・ジオスロットの解放
    private void geoInventryDemo(InventryS geoInventry, GeoSlotAdminManager geoSlotAdminManager) {
        GeoObjectData tempGeoObjectData;
        int[][] normalGeo = new int[][] {
                {5, 10, 15, 20},
                {5, 10, 15, 20},
                {5, 10, 15, 20}
        };
        double[][] rateGeo = new double[][] {
                {1.05, 1.10, 1.15, 1.20},
                {1.05, 1.10, 1.15, 1.20},
                {1.05, 1.10, 1.15, 1.20},
        };

        //{0, 1} = ForestのID1番にジオをセットするという意味
        //{X, 0} = どこにもセットしないの意味。第一要素の数字は無視される
        int[][][] normalGeoSlot = new int[][][] {
                {{0, 1}, {0, 4}, {0, 0}, {0, 0}},
                {{1, 2}, {0, 0}, {0, 5}, {0, 6}},
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
        };

        int[][][] rateGeoSlot = new int[][][] {
                {{1, 1}, {0, 0}, {0, 0}, {0, 0}},
                {{0, 0}, {0, 7}, {0, 0}, {0, 0}},
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
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

        for (int i = 0; i < normalGeo.length; i++) {
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

            for (int j = 0; j < normalGeo[i].length; j++) {
                tempGeoObjectData = GeoObjectDataCreater.getGeoObjectData(rateGeo[i][j], kindRate, false);

                if (rateGeoSlot[i][j][1] > 0) {
                    slotName = geoSlotAdminManager.getSlotNameDemo(rateGeoSlot[i][j][0]);
                    if (geoSlotAdminManager.getGeoSlotAdmin(slotName).getGeoSlots().size() >= rateGeoSlot[i][j][1]) {
                        tempGeoObjectData.setSlotSetName(slotName);
                        tempGeoObjectData.setSlotSetID(rateGeoSlot[i][j][1]);
                    }
                }

                geoInventry.addItemData(tempGeoObjectData);
            }
        }

        //ジオスロット解放
        int[][][] geoSlotEvent = new int[][][] {
                {{2, 1}, {8, 1}, {11, 1}, {12, 1}},
                {{3, 1}, {4, 1}, {5, 1}, {8, 1}, {11, 1}},
                {{4, 1}, {6, 1}, {8, 1}, {10, 1}, {12, 1}},
                {{4, 1}, {6, 1}, {8, 1}, {10, 1}},
                {{2, 1}, {4, 1}, {6, 1}, {9, 1}, {11, 1}},
                {{3, 1}, {5, 1}, {7, 1}, {9, 1}, {11, 1}, {14, 1}},
                {{4, 1}, {7, 1}, {8, 1}, {9, 1}, {10, 1}, {11, 1}, {12, 1}, {13, 1}, {14, 1}},
                {{2, 1}, {3, 1}, {4, 1}, {5, 1}, {6, 1}, {7, 1},
                        {8, 1}, {9, 1}, {10, 1}, {11, 1}, {12, 1}, {13, 1}, {14, 1}, {15, 1}, {16, 1}, {17, 1}},
        };


        for(int i = 0; i < geoSlotEvent.length; i++) {
            slotName = geoSlotAdminManager.getSlotNameDemo(i);
            List<GeoSlot> geoSlots = geoSlotAdminManager.getGeoSlotAdmin(slotName).getGeoSlots();
            for (int j = 0; j < geoSlotEvent[i].length; j++) {
                if (geoSlotEvent[i][j][1] == 1) {
                    geoSlots.get(geoSlotEvent[i][j][0] - 1).setReleased(true);
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

    // 献上ポイント

}
