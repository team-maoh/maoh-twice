package com.maohx2.ina.Battle;

import com.maohx2.kmhanko.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ina on 2017/10/29.
 */

public class BattleUnitDataAdmin {

    MyDatabase battle_unit_data_database;
    List<BattleBaseUnitData> battle_base_unit_datas = new ArrayList<BattleBaseUnitData>();
    List<BattleDungeonUnitData> battle_dungeon_unit_datas = new ArrayList<BattleDungeonUnitData>();

    public void init(MyDatabase _battle_unit_data_database) {battle_unit_data_database = _battle_unit_data_database;}

    //データベースを読み込んで画像をbitmap_dataに保存する
    public void loadBattleUnitData() {

        List<String> name = new ArrayList<String>();
        List<String> attack_farame = new ArrayList<String>();
        List<String> initial_hp = new ArrayList<String>();
        List<String> initial_attack = new ArrayList<String>();
        List<String> initial_defence = new ArrayList<String>();
        List<String> initial_luck = new ArrayList<String>();
        List<String> delta_hp = new ArrayList<String>();
        List<String> delta_attack = new ArrayList<String>();
        List<String> delta_defence = new ArrayList<String>();
        List<String> delta_luck = new ArrayList<String>();
        List<String> initial_bonus_hp = new ArrayList<String>();
        List<String> initial_bonus_attack = new ArrayList<String>();
        List<String> initial_bonus_defence = new ArrayList<String>();
        List<String> delta_bonus_hp = new ArrayList<String>();
        List<String> delta_bonus_attack = new ArrayList<String>();
        List<String> delta_bonus_defence = new ArrayList<String>();

        name = battle_unit_data_database.getString("battle_unit_data", "name", null);
        attack_farame = battle_unit_data_database.getString("battle_unit_data", "attack_frame", null);
        initial_hp = battle_unit_data_database.getString("battle_unit_data", "initial_hp", null);
        initial_attack = battle_unit_data_database.getString("battle_unit_data", "initial_attack", null);
        initial_defence = battle_unit_data_database.getString("battle_unit_data", "initial_defence", null);
         initial_luck = battle_unit_data_database.getString("battle_unit_data", "initial_luck", null);
         delta_hp = battle_unit_data_database.getString("battle_unit_data", "delta_hp", null);
         delta_attack = battle_unit_data_database.getString("battle_unit_data", "delta_attack", null);
         delta_defence = battle_unit_data_database.getString("battle_unit_data", "delta_defence", null);
         delta_luck = battle_unit_data_database.getString("battle_unit_data", "delta_luck", null);
         initial_bonus_hp = battle_unit_data_database.getString("battle_unit_data", "initial_bonus_hp", null);
         initial_bonus_attack = battle_unit_data_database.getString("battle_unit_data", "initial_bonus_attack", null);
         initial_bonus_defence = battle_unit_data_database.getString("battle_unit_data", "initial_bonus_defence", null);
         delta_bonus_hp = battle_unit_data_database.getString("battle_unit_data", "delta_bonus_hp", null);
         delta_bonus_attack = battle_unit_data_database.getString("battle_unit_data", "delta_bonus_attack", null);
         delta_bonus_defence = battle_unit_data_database.getString("battle_unit_data", "delta_bonus_defence", null);

        for (int i = 0; i < name.size(); i++) {
            battle_base_unit_datas.add(new BattleBaseUnitData());
            //battle_base_unit_datas.get(i)init();
        }

    }

    public BattleDungeonUnitData getBattleUnitDataNum(String bitmap_name) {

        //todo:適当な定数をつける
        for (int i = 0; i < battle_dungeon_unit_datas.size(); i++) {
            if (battle_dungeon_unit_datas.get(i).getName().equals(bitmap_name) == true) {
                return battle_dungeon_unit_datas.get(i);
            }
        }
        throw new Error("その名前のユニットは存在しません");
    }

    public void setDatabase(MyDatabase _battle_unit_data_database) {battle_unit_data_database = _battle_unit_data_database;}
}