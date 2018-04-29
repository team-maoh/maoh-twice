package com.maohx2.kmhanko.Saver;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Constants;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlot;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.myavail.MyAvail;

/**
 * Created by user on 2018/04/15.
 */

/*
セーブ事項
・テーブルをダンジョンの数生成
各ダンジョンについて
・各スロットにはめ込まれているジオ
位置はIDで、その他のジオの能力データも同時に格納する or アイテムインベントリにおけるナンバーを格納する
後者は安全でない
・各スロットのイベント解放
これはフラグとして別で保存する？→スロットに入れるでいいかな

ID,release_flag,name,hp,attack,defence,luck,hprate,attackrate,defencerate,luckrate

 */

public class GeoSlotSaver extends SaveManager {

    GeoSlotAdminManager geoSlotAdminManager;
    Graphic graphic;

    public GeoSlotSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode, Graphic _graphic) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
        graphic = _graphic;
    }

    public void setGeoSlotAdminManager(GeoSlotAdminManager _geoSlotAdminManager) {
        geoSlotAdminManager = _geoSlotAdminManager;
        if (database.isNew()) {
            makeTable();//TODO よくない
        }
    }

    @Override
    public void dbinit() {
    }

    //TODO データベースのアップデートがあった場合に良くない処理。
    public void makeTable() {
        //テーブルの生成
        deleteAll();
        deleteTableAll();
        List<String> dBtableNames = database.getTables();
        List<String> tableNames = geoSlotAdminManager.getGeoSlotAdminNames();
        for(int i = 0; i < tableNames.size(); i++) {
            if (MyAvail.matchStringList(tableNames.get(i),dBtableNames)) {
                System.out.println("☆タカノ:GeoSlotSaver#init テーブル " + tableNames.get(i) + "はDB上に存在するので作成しませんでした : " + dBtableNames);
                continue;
            }

            database.execSQL(
                    "create table " + tableNames.get(i) + "(" +
                            "ID integer primary key," +
                            "name string," +
                            "image_name string," +
                            "release_flag string," +
                            "hp integer," +
                            "attack integer," +
                            "defence integer," +
                            "luck integer," +
                            "hp_rate real," +
                            "attack_rate real," +
                            "defence_rate real," +
                            "luck_rate real" +
                            ")"
            );
            System.out.println("☆タカノ:GeoSlotSaver#init テーブル生成: " + tableNames.get(i));
        }




    }

    @Override
    public void save() {
        String datas[] = new String[10];

        deleteAll();
        List<GeoSlotAdmin> geoSlotAdmins  = geoSlotAdminManager.getGeoSlotAdmins();
        List<String> dBtableNames = database.getTables();

        for(int i = 0; i < geoSlotAdmins.size(); i++) {
            List<GeoSlot> geoSlots = geoSlotAdmins.get(i).getGeoSlots();
            String name = geoSlotAdmins.get(i).getName();

            if (!MyAvail.matchStringList(name, dBtableNames)) {
                System.out.println("☆タカノ:GeoSlotSaver#save テーブル " + name + "はDB上に存在しないのでセーブできませんでした : " + dBtableNames);
                continue;
            }

            for(int j = 0; j < geoSlots.size(); j++) {
                GeoObjectData geoObjectData = (GeoObjectData)geoSlots.get(j).getGeoObjectData();

                if (geoObjectData == null) {
                    datas[0] = null;
                    datas[1] = null;
                    datas[2] = null;
                    datas[3] = null;
                    datas[4] = null;
                    datas[5] = null;
                    datas[6] = null;
                    datas[7] = null;
                    datas[8] = null;
                    datas[9] = null;
                } else {
                    datas[0] = geoObjectData.getName();
                    datas[1] = geoObjectData.getImageName();
                    datas[2] = String.valueOf(geoObjectData.getHp());
                    datas[3] = String.valueOf(geoObjectData.getAttack());
                    datas[4] = String.valueOf(geoObjectData.getDefence());
                    datas[5] = String.valueOf(geoObjectData.getLuck());
                    datas[6] = String.valueOf(geoObjectData.getHpRate());
                    datas[7] = String.valueOf(geoObjectData.getAttackRate());
                    datas[8] = String.valueOf(geoObjectData.getDefenceRate());
                    datas[9] = String.valueOf(geoObjectData.getLuckRate());
                }

                String releaseFlag;
                if (geoSlots.get(j).isReleased()) {
                    releaseFlag = "true";
                } else {
                    releaseFlag = "false";
                }

                database.insertLineByArrayString(
                        name,
                        new String[] { "id", "release_flag", "name", "image_name", "hp", "attack", "defence", "luck", "hp_rate", "attack_rate", "defence_rate", "luck_rate" },
                        new String[] {
                                String.valueOf(j),
                                releaseFlag,
                                datas[0],
                                datas[1],
                                datas[2],
                                datas[3],
                                datas[4],
                                datas[5],
                                datas[6],
                                datas[7],
                                datas[8],
                                datas[9]
                        }
                );
                if (geoObjectData != null) {
                    System.out.println("☆タカノ:GeoSlotSaver#save : " + name + "(" + j + ") " + "<-" + geoObjectData.getName());
                }
            }
        }
    }

    @Override
    public void load() {
        List<GeoSlotAdmin> geoSlotAdmins  = geoSlotAdminManager.getGeoSlotAdmins();
        List<String> dBtableNames = database.getTables();

        for(int i = 0; i < geoSlotAdmins.size(); i++) {
            String tableName = geoSlotAdmins.get(i).getName();

            if (!MyAvail.matchStringList(tableName, dBtableNames)) {
                System.out.println("☆タカノ:GeoSlotSaver#load テーブル " + tableName + "はDB上に存在しないのでロードできませんでした : " + dBtableNames);
                continue;
            }

            List<Integer> ids = database.getInt(tableName, "id");
            List<String> names = database.getString(tableName, "name");
            List<String> imageNames = database.getString(tableName, "image_name");
            List<Boolean> releaseFlags = database.getBoolean(tableName, "release_flag");
            List<Integer> hps = database.getInt(tableName, "hp");
            List<Integer> attacks = database.getInt(tableName, "attack");
            List<Integer> defences = database.getInt(tableName, "defence");
            List<Integer> lucks = database.getInt(tableName, "luck");
            List<Float> hpRates = database.getFloat(tableName, "hp_rate");
            List<Float> attackRates = database.getFloat(tableName, "attack_rate");
            List<Float> defenceRates = database.getFloat(tableName, "defence_rate");
            List<Float> luckRates = database.getFloat(tableName, "luck_rate");

            List<GeoSlot> geoSlots = geoSlotAdmins.get(i).getGeoSlots();
            for(int j = 0; j < ids.size(); j++) {
                if (names.get(i) != null) {
                    if (geoSlots.get(ids.get(j)).pushGeoObject(
                            new GeoObjectData(
                                    names.get(j),
                                    graphic.searchBitmap(imageNames.get(j)),
                                    hps.get(j),
                                    attacks.get(j),
                                    defences.get(j),
                                    lucks.get(j),
                                    hpRates.get(j),
                                    attackRates.get(j),
                                    defenceRates.get(j),
                                    luckRates.get(j)
                            )
                    )
                            ) {
                        System.out.println("☆タカノ:GeoSlotSaver#load : " + tableName + "(" + ids.get(j) + ") " + "->" + names.get(j));
                    }
                }
                geoSlots.get(ids.get(j)).setReleased(releaseFlags.get(j));
            }
        }

    }
}
