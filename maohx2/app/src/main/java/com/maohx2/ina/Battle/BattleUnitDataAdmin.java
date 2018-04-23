package com.maohx2.ina.Battle;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.Item.*;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ina on 2017/10/29.
 */

//TODO HPバー関係と半径関係

//敵キャラのデータベースの管理
public class BattleUnitDataAdmin {

    MyDatabaseAdmin databaseAdmin;
    MyDatabase battle_unit_data_database;
    List<BattleBaseUnitData> battle_base_unit_datas = new ArrayList<BattleBaseUnitData>();
    //List<BattleDungeonUnitData> battle_dungeon_unit_datas = new ArrayList<BattleDungeonUnitData>();
    Graphic graphic;


    public BattleUnitDataAdmin(MyDatabaseAdmin _databaseAdmin, Graphic _graphic) {
        graphic = _graphic;
        //DBからデータ読み込み
        databaseAdmin = _databaseAdmin;
        databaseAdmin.addMyDatabase("BattleUnitData","BattleUnitData.db",1,"r");
        battle_unit_data_database = databaseAdmin.getMyDatabase("BattleUnitData");
        loadBattleUnitData();//必ず初めに呼ぶこと
    }

    //データベースを読み込んで画像をbitmap_dataに保存する
    private void loadBattleUnitData() {

        //by kmhanko String → Integer
        List<String> name = new ArrayList<String>();
        List<Integer> attack_frame = new ArrayList<Integer>();
        List<Integer> initial_hp = new ArrayList<Integer>();
        List<Integer> initial_attack = new ArrayList<Integer>();
        List<Integer> initial_defence = new ArrayList<Integer>();
        List<Integer> initial_luck = new ArrayList<Integer>();
        List<Integer> initial_speed = new ArrayList<Integer>();
        List<Integer> delta_hp = new ArrayList<Integer>();
        List<Integer> delta_attack = new ArrayList<Integer>();
        List<Integer> delta_defence = new ArrayList<Integer>();
        List<Integer> delta_luck = new ArrayList<Integer>();
        List<Integer> delta_speed = new ArrayList<Integer>();
        List<Integer> initial_bonus_hp = new ArrayList<Integer>();
        List<Integer> initial_bonus_attack = new ArrayList<Integer>();
        List<Integer> initial_bonus_defence = new ArrayList<Integer>();
        List<Integer> initial_bonus_speed = new ArrayList<Integer>();
        List<Integer> delta_bonus_hp = new ArrayList<Integer>();
        List<Integer> delta_bonus_attack = new ArrayList<Integer>();
        List<Integer> delta_bonus_defence = new ArrayList<Integer>();
        List<Integer> delta_bonus_speed = new ArrayList<Integer>();

        List<Integer> hp_bar_offset = new ArrayList<Integer>();
        List<Integer> hp_bar_length = new ArrayList<Integer>();
        List<Integer> radius = new ArrayList<Integer>();

        List<String>[] drop_item = new ArrayList[Constants.Item.DROP_NUM];
        for (int i = 0; i<drop_item.length; i++) {
            drop_item[i] = new ArrayList<String>();
        }
        List<Double>[] drop_item_percent = new ArrayList[Constants.Item.DROP_NUM];
        for (int i = 0; i<drop_item_percent.length; i++) {
            drop_item_percent[i] = new ArrayList<Double>();
        }

        name = battle_unit_data_database.getString("battle_unit_data", "name", null);
        attack_frame = battle_unit_data_database.getInt("battle_unit_data", "attack_frame", null);
        initial_hp = battle_unit_data_database.getInt("battle_unit_data", "initial_hp", null);
        initial_attack = battle_unit_data_database.getInt("battle_unit_data", "initial_attack", null);
        initial_defence = battle_unit_data_database.getInt("battle_unit_data", "initial_defence", null);
        initial_luck = battle_unit_data_database.getInt("battle_unit_data", "initial_luck", null);
        initial_speed = battle_unit_data_database.getInt("battle_unit_data", "initial_speed", null);
        delta_hp = battle_unit_data_database.getInt("battle_unit_data", "delta_hp", null);
        delta_attack = battle_unit_data_database.getInt("battle_unit_data", "delta_attack", null);
        delta_defence = battle_unit_data_database.getInt("battle_unit_data", "delta_defence", null);
        delta_luck = battle_unit_data_database.getInt("battle_unit_data", "delta_luck", null);
        delta_speed = battle_unit_data_database.getInt("battle_unit_data", "delta_speed", null);
        initial_bonus_hp = battle_unit_data_database.getInt("battle_unit_data", "initial_bonus_hp", null);
        initial_bonus_attack = battle_unit_data_database.getInt("battle_unit_data", "initial_bonus_attack", null);
        initial_bonus_defence = battle_unit_data_database.getInt("battle_unit_data", "initial_bonus_defence", null);
        initial_bonus_speed = battle_unit_data_database.getInt("battle_unit_data", "initial_bonus_speed", null);
        delta_bonus_hp = battle_unit_data_database.getInt("battle_unit_data", "delta_bonus_hp", null);
        delta_bonus_attack = battle_unit_data_database.getInt("battle_unit_data", "delta_bonus_attack", null);
        delta_bonus_defence = battle_unit_data_database.getInt("battle_unit_data", "delta_bonus_defence", null);
        delta_bonus_speed = battle_unit_data_database.getInt("battle_unit_data", "delta_bonus_speed", null);

        hp_bar_offset = battle_unit_data_database.getInt("battle_unit_data", "hp_bar_offset", null);
        hp_bar_length = battle_unit_data_database.getInt("battle_unit_data", "hp_bar_length", null);
        radius = battle_unit_data_database.getInt("battle_unit_data", "hit_radius", null);

        for (int i = 0; i < drop_item.length; i++) {
            drop_item[i] = battle_unit_data_database.getString("battle_unit_data", "drop_item0" + String.valueOf(i + 1), null);
            drop_item_percent[i] = battle_unit_data_database.getDouble("battle_unit_data", "drop_item0" + String.valueOf(i + 1) + "_percent", null);
        }

        for (int i = 0; i < name.size(); i++) {
            battle_base_unit_datas.add(new BattleBaseUnitData());
            //battle_base_unit_Zdatas.get(i)init();

            //by kmhanko
            //DBから読み込んだデータを全てBattleBaseUnitDataに格納する
            BattleBaseUnitData tempBattleBaseUnitData = battle_base_unit_datas.get(i);

            tempBattleBaseUnitData.setName(name.get(i));
            tempBattleBaseUnitData.setBitmapData(graphic.searchBitmap(name.get(i)));
            if(radius.get(i) < 0) {
                tempBattleBaseUnitData.setRadius(graphic.searchBitmap(name.get(i)).getHeight() > graphic.searchBitmap(name.get(i)).getWidth() ? graphic.searchBitmap(name.get(i)).getHeight() / 2 : graphic.searchBitmap(name.get(i)).getWidth() / 2);
            }else{
                tempBattleBaseUnitData.setRadius(radius.get(i));
            }

            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.AttackFlame, attack_frame.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialHP, initial_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialAttack, initial_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialDefence, initial_defence.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialLuck, initial_luck.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialSpeed, initial_speed.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaHP, delta_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaAttack, delta_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaDefence, delta_defence.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaLuck, delta_luck.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaSpeed, delta_speed.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialBonusHP, initial_bonus_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialBonusAttack, initial_bonus_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialBonusDefence, initial_bonus_defence.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialBonusSpeed, initial_bonus_speed.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaBonusHP, delta_bonus_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaBonusAttack, delta_bonus_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaBonusDefence, delta_bonus_defence.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaBonusSpeed, delta_bonus_speed.get(i));


            for (int j = 0; j < drop_item.length; j++ ) {
                if (drop_item[j].get(i) == null) {
                    continue;
                }
                //武器の場合
                EQUIPMENT_KIND bufEK;
                switch (drop_item[j].get(i)) {
                    case "剣":
                        bufEK = EQUIPMENT_KIND.SWORD;
                        break;
                    case "杖":
                        bufEK = EQUIPMENT_KIND.WAND;
                        break;
                    case "斧":
                        bufEK = EQUIPMENT_KIND.AX;
                        break;
                    case "槍":
                        bufEK = EQUIPMENT_KIND.SPEAR;
                        break;
                    case "弓":
                        bufEK = EQUIPMENT_KIND.BOW;
                        break;
                    case "銃":
                        bufEK = EQUIPMENT_KIND.GUN;
                        break;
                    case "ナックル":
                        bufEK = EQUIPMENT_KIND.FIST;
                        break;
                    case "メイス":
                        bufEK = EQUIPMENT_KIND.CLUB;
                        break;
                    case "鞭":
                        bufEK = EQUIPMENT_KIND.WHIP;
                        break;
                    case "楽器":
                        bufEK = EQUIPMENT_KIND.MUSIC;
                        break;
                    case "盾":
                        bufEK = EQUIPMENT_KIND.SHIELD;
                        break;
                    default:
                        bufEK = null;
                        break;
                }
                if (bufEK != null) {
                    tempBattleBaseUnitData.setDropItemEquipmentKind(j, bufEK);

                    tempBattleBaseUnitData.setDropItemName(j, drop_item[j].get(i));
                    tempBattleBaseUnitData.setDropItemKind(j, Constants.Item.ITEM_KIND.EQUIPMENT);
                    tempBattleBaseUnitData.setDropItemRate(j, drop_item_percent[j].get(i));
                } else {
                    //消費アイテムの場合
                    tempBattleBaseUnitData.setDropItemName(j, drop_item[j].get(i));
                    tempBattleBaseUnitData.setDropItemKind(j, Constants.Item.ITEM_KIND.EXPEND);
                    tempBattleBaseUnitData.setDropItemRate(j, drop_item_percent[j].get(i));
                }
            }
        }

    }

    //name で検索して、BattleBaseUnitData型を返す関数。getBattleDungeonUnitDataByName
    public BattleBaseUnitData getBattleUnitDataNum(String bitmap_name) {
        //todo:適当な定数をつける
        for (int i = 0; i < battle_base_unit_datas.size(); i++) {
            if (bitmap_name.equals(battle_base_unit_datas.get(i).getName()) == true) {
                return battle_base_unit_datas.get(i);
            }
        }
        throw new Error("その名前のユニットは存在しません");
    }

    /*
    public BattleDungeonUnitData getBattleUnitDataNum(String bitmap_name) {
        //todo:適当な定数をつける
        for (int i = 0; i < battle_dungeon_unit_datas.size(); i++) {
            if (battle_dungeon_unit_datas.get(i).getName().equals(bitmap_name) == true) {
                return battle_dungeon_unit_datas.get(i);
            }
        }
        throw new Error("その名前のユニットは存在しません");
    }
    */


    //古い関数

    //public void init(MyDatabase _battle_unit_data_database) {battle_unit_data_database = _battle_unit_data_database;}
    //public void setDatabase(MyDatabase _battle_unit_data_database) {battle_unit_data_database = _battle_unit_data_database;}
}

/*
battleに関する予定
・データベースからのEnemyData読み込み
・ダメージ計算関係の関数
・戦闘開始・戦闘終了のAct遷移？
・状態異常
・アイテムの使用効果関数




 */
















