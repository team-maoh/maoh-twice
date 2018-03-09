package com.maohx2.ina.Battle;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ina on 2017/10/29.
 */

//敵キャラのデータベースの管理
public class BattleUnitDataAdmin {

    MyDatabaseAdmin databaseAdmin;
    MyDatabase battle_unit_data_database;
    List<BattleBaseUnitData> battle_base_unit_datas = new ArrayList<BattleBaseUnitData>();
    //List<BattleDungeonUnitData> battle_dungeon_unit_datas = new ArrayList<BattleDungeonUnitData>();


    public BattleUnitDataAdmin(MyDatabaseAdmin _databaseAdmin) {
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
        List<Integer> delta_hp = new ArrayList<Integer>();
        List<Integer> delta_attack = new ArrayList<Integer>();
        List<Integer> delta_defence = new ArrayList<Integer>();
        List<Integer> delta_luck = new ArrayList<Integer>();
        List<Integer> initial_bonus_hp = new ArrayList<Integer>();
        List<Integer> initial_bonus_attack = new ArrayList<Integer>();
        List<Integer> initial_bonus_defence = new ArrayList<Integer>();
        List<Integer> delta_bonus_hp = new ArrayList<Integer>();
        List<Integer> delta_bonus_attack = new ArrayList<Integer>();
        List<Integer> delta_bonus_defence = new ArrayList<Integer>();

        name = battle_unit_data_database.getString("battle_unit_data", "name", null);
        attack_frame = battle_unit_data_database.getInt("battle_unit_data", "attack_frame", null);
        initial_hp = battle_unit_data_database.getInt("battle_unit_data", "initial_hp", null);
        initial_attack = battle_unit_data_database.getInt("battle_unit_data", "initial_attack", null);
        initial_defence = battle_unit_data_database.getInt("battle_unit_data", "initial_defence", null);
        initial_luck = battle_unit_data_database.getInt("battle_unit_data", "initial_luck", null);
        delta_hp = battle_unit_data_database.getInt("battle_unit_data", "delta_hp", null);
        delta_attack = battle_unit_data_database.getInt("battle_unit_data", "delta_attack", null);
        delta_defence = battle_unit_data_database.getInt("battle_unit_data", "delta_defence", null);
        delta_luck = battle_unit_data_database.getInt("battle_unit_data", "delta_luck", null);
        initial_bonus_hp = battle_unit_data_database.getInt("battle_unit_data", "initial_bonus_hp", null);
        initial_bonus_attack = battle_unit_data_database.getInt("battle_unit_data", "initial_bonus_attack", null);
        initial_bonus_defence = battle_unit_data_database.getInt("battle_unit_data", "initial_bonus_defence", null);
        delta_bonus_hp = battle_unit_data_database.getInt("battle_unit_data", "delta_bonus_hp", null);
        delta_bonus_attack = battle_unit_data_database.getInt("battle_unit_data", "delta_bonus_attack", null);
        delta_bonus_defence = battle_unit_data_database.getInt("battle_unit_data", "delta_bonus_defence", null);

        for (int i = 0; i < name.size(); i++) {
            battle_base_unit_datas.add(new BattleBaseUnitData());
            //battle_base_unit_Zdatas.get(i)init();

            //by kmhanko
            //DBから読み込んだデータを全てBattleBaseUnitDataに格納する
            BattleBaseUnitData tempBattleBaseUnitData = battle_base_unit_datas.get(i);
            tempBattleBaseUnitData.setName(name.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialHP, initial_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialAttack, initial_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialDefence, initial_defence.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialLuck, initial_luck.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaHP, delta_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaAttack, delta_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaDefence, delta_defence.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaLuck, delta_luck.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialBonusHP, initial_bonus_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialBonusAttack, initial_bonus_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialBonusDefence, initial_bonus_defence.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaBonusHP, delta_bonus_hp.get(i));tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.InitialHP, initial_hp.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaBonusAttack, delta_bonus_attack.get(i));
            tempBattleBaseUnitData.setDbStatus(BattleBaseUnitData.DbStatusID.DeltaBonusDefence, delta_bonus_defence.get(i));

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
















