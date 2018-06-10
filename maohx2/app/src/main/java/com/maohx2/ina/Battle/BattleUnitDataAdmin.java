package com.maohx2.ina.Battle;

import com.maohx2.horie.map.DungeonMonsterData;
import com.maohx2.horie.map.DungeonMonsterDataAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.Item.*;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.ina.Battle.BattleBaseUnitData.DbStatusID;
import com.maohx2.ina.Battle.BattleBaseUnitData.ActionID;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    List<String> name;

    final String[] tableName = new String[] {
            "battle_unit_data", "maoh_unit_data"
    };

    public BattleUnitDataAdmin(MyDatabaseAdmin _databaseAdmin, Graphic _graphic) {
        graphic = _graphic;
        //DBからデータ読み込み
        databaseAdmin = _databaseAdmin;
        databaseAdmin.addMyDatabase("BattleUnitData","BattleUnitData.db",1,"r");
        battle_unit_data_database = databaseAdmin.getMyDatabase("BattleUnitData");
    }

    //データベースを読み込んで画像をbitmap_dataに保存する
    public void loadBattleUnitData(Constants.DungeonKind.DUNGEON_KIND dungeonKind) {
        String tableName;
        switch (dungeonKind) {
            case FOREST:
                tableName = "forest_unit_data";
                break;
            case HAUNTED:
                tableName = "haunted_unit_data";
                break;
            case DRAGON:
                tableName = "dragon_unit_data";
                break;
            case CHESS:
                tableName = "chess_unit_data";
                break;
            case MAOH:
                tableName = "maoh_unit_data";
                break;
            case OPENING:
                tableName = "opening_unit_data";
                break;
            case SEA:
                tableName = "sea_unit_data";
                break;
            case SWAMP:
                tableName = "swamp_unit_data";
                break;
            case LAVA:
                tableName = "lava_unit_data";
                break;
            default:
                throw new Error("☆BattleUnitDataAdmin: ダンジョンの種類が不適切");
        }
        loadBattleUnitData(tableName);

        //デバッグ用
        loadBattleUnitData("battle_unit_data");

    }

    public void loadBattleUnitData(String tableName) {

        /*
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

        //特殊行動　バリア、カウンター、ステルス
        //List<String> special_action_name = new ArrayList<String>();
        List<Integer> special_action_period = new ArrayList<Integer>();
        List<Integer> special_action_width = new ArrayList<Integer>();

        //攻撃確率　通常攻撃、毒、まひ、ストップ、盲目、呪い
        List<Float> normal_attack = new ArrayList<Float>();
        List<Float> poison = new ArrayList<Float>();
        List<Float> paralysys = new ArrayList<Float>();
        List<Float> stop = new ArrayList<Float>();
        List<Float> blindness = new ArrayList<Float>();
        List<Float> curse = new ArrayList<Float>();
        */

        //for(int k = 0; k < tableName.length; k++) {


        /*
            List<String>[] drop_item = new ArrayList[Constants.Item.DROP_NUM];
            for (int i = 0; i < drop_item.length; i++) {
                drop_item[i] = new ArrayList<String>();
            }
            List<Double>[] drop_item_percent = new ArrayList[Constants.Item.DROP_NUM];
            for (int i = 0; i < drop_item_percent.length; i++) {
                drop_item_percent[i] = new ArrayList<Double>();
            }
            */

            name = battle_unit_data_database.getString(tableName, "name", null);
            List<Integer> attack_frame = battle_unit_data_database.getInt(tableName, "attack_frame", null);
            List<Integer> initial_hp = battle_unit_data_database.getInt(tableName, "initial_hp", null);
            List<Integer> initial_attack = battle_unit_data_database.getInt(tableName, "initial_attack", null);
            List<Integer> initial_defence = battle_unit_data_database.getInt(tableName, "initial_defence", null);
            List<Integer> initial_luck = battle_unit_data_database.getInt(tableName, "initial_luck", null);
            List<Integer> initial_speed = battle_unit_data_database.getInt(tableName, "initial_speed", null);
            List<Integer> delta_hp = battle_unit_data_database.getInt(tableName, "delta_hp", null);
            List<Integer> delta_attack = battle_unit_data_database.getInt(tableName, "delta_attack", null);
            List<Integer> delta_defence = battle_unit_data_database.getInt(tableName, "delta_defence", null);
            List<Integer> delta_luck = battle_unit_data_database.getInt(tableName, "delta_luck", null);
            List<Integer> delta_speed = battle_unit_data_database.getInt(tableName, "delta_speed", null);
            List<Integer> initial_bonus_hp = battle_unit_data_database.getInt(tableName, "initial_bonus_hp", null);
            List<Integer> initial_bonus_attack = battle_unit_data_database.getInt(tableName, "initial_bonus_attack", null);
            List<Integer> initial_bonus_defence = battle_unit_data_database.getInt(tableName, "initial_bonus_defence", null);
            List<Integer> initial_bonus_speed = battle_unit_data_database.getInt(tableName, "initial_bonus_speed", null);
            List<Integer> delta_bonus_hp = battle_unit_data_database.getInt(tableName, "delta_bonus_hp", null);
            List<Integer> delta_bonus_attack = battle_unit_data_database.getInt(tableName, "delta_bonus_attack", null);
            List<Integer> delta_bonus_defence = battle_unit_data_database.getInt(tableName, "delta_bonus_defence", null);
            List<Integer> delta_bonus_speed = battle_unit_data_database.getInt(tableName, "delta_bonus_speed", null);

            List<Integer> hp_bar_offset = battle_unit_data_database.getInt(tableName, "hp_bar_offset", null);
            List<Integer> hp_bar_length = battle_unit_data_database.getInt(tableName, "hp_bar_length", null);
            List<Integer> radius = battle_unit_data_database.getInt(tableName, "hit_radius", null);

            List<String> special_action_name = battle_unit_data_database.getString(tableName, "special_action_name", null);
            List<Integer> special_action_period = battle_unit_data_database.getInt(tableName, "special_action_period", null);
            List<Integer> special_action_width = battle_unit_data_database.getInt(tableName, "special_action_width", null);

            List<Float> normal_attack = battle_unit_data_database.getFloat(tableName, "normal_attack", null);
            List<Float> poison = battle_unit_data_database.getFloat(tableName, "poison", null);
            List<Float> paralysis = battle_unit_data_database.getFloat(tableName, "paralysis", null);
            List<Float> stop = battle_unit_data_database.getFloat(tableName, "stop", null);
            List<Float> blindness = battle_unit_data_database.getFloat(tableName, "blindness", null);
            List<Float> curse = battle_unit_data_database.getFloat(tableName, "curse", null);

            List<Integer> poison_time = battle_unit_data_database.getInt(tableName, "poison_time", null);
            List<Integer> paralysis_time = battle_unit_data_database.getInt(tableName, "paralysis_time", null);
            List<Integer> stop_time = battle_unit_data_database.getInt(tableName, "stop_time", null);
            List<Integer> blindness_time = battle_unit_data_database.getInt(tableName, "blindness_time", null);
            List<Integer> curse_time = battle_unit_data_database.getInt(tableName, "curse_time", null);

            List<Integer> power = battle_unit_data_database.getInt(tableName, "power", null);

        List<String> drop_item1 = battle_unit_data_database.getString(tableName, "drop_item01", null);
        List<String> drop_item2 = battle_unit_data_database.getString(tableName, "drop_item02", null);
        List<String> drop_item3 = battle_unit_data_database.getString(tableName, "drop_item03", null);

        List<Double> drop_item_percent1 = battle_unit_data_database.getDouble(tableName, "drop_item01_percent", null);
        List<Double> drop_item_percent2 = battle_unit_data_database.getDouble(tableName, "drop_item02_percent", null);
        List<Double> drop_item_percent3 = battle_unit_data_database.getDouble(tableName, "drop_item03_percent", null);


/*
            for (int i = 0; i < drop_item.length; i++) {
                drop_item[i] = battle_unit_data_database.getString(tableName, "drop_item0" + String.valueOf(i + 1), null);
                drop_item_percent[i] = battle_unit_data_database.getDouble(tableName, "drop_item0" + String.valueOf(i + 1) + "_percent", null);
            }*/

            for (int i = 0; i < name.size(); i++) {
                battle_base_unit_datas.add(new BattleBaseUnitData());
                //battle_base_unit_Zdatas.get(i)init();

                //by kmhanko
                //DBから読み込んだデータを全てBattleBaseUnitDataに格納する
                BattleBaseUnitData tempBattleBaseUnitData = battle_base_unit_datas.get(battle_base_unit_datas.size() - 1);

                tempBattleBaseUnitData.setName(name.get(i));
                tempBattleBaseUnitData.setBitmapData(graphic.searchBitmap(name.get(i)));
                if (radius.get(i) < 0) {
                    tempBattleBaseUnitData.setRadius(graphic.searchBitmap(name.get(i)).getHeight() > graphic.searchBitmap(name.get(i)).getWidth() ? graphic.searchBitmap(name.get(i)).getHeight() / 2 : graphic.searchBitmap(name.get(i)).getWidth() / 2);
                } else {
                    tempBattleBaseUnitData.setRadius(radius.get(i));
                }

                tempBattleBaseUnitData.setDbStatus(DbStatusID.AttackFlame, attack_frame.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialHP, initial_hp.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialAttack, initial_attack.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialDefence, initial_defence.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialLuck, initial_luck.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialSpeed, initial_speed.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaHP, delta_hp.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaAttack, delta_attack.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaDefence, delta_defence.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaLuck, delta_luck.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaSpeed, delta_speed.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialBonusHP, initial_bonus_hp.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialBonusAttack, initial_bonus_attack.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialBonusDefence, initial_bonus_defence.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.InitialBonusSpeed, initial_bonus_speed.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaBonusHP, delta_bonus_hp.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaBonusAttack, delta_bonus_attack.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaBonusDefence, delta_bonus_defence.get(i));
                tempBattleBaseUnitData.setDbStatus(DbStatusID.DeltaBonusSpeed, delta_bonus_speed.get(i));

                tempBattleBaseUnitData.setSpecialAction(special_action_name.get(i));
                tempBattleBaseUnitData.setSpecialActionPeriod(special_action_period.get(i));
                tempBattleBaseUnitData.setSpecialActionWidth(special_action_width.get(i));

                tempBattleBaseUnitData.setActionRate(ActionID.NORMAL_ATTACK, normal_attack.get(i));
                tempBattleBaseUnitData.setActionRate(ActionID.POISON, poison.get(i));
                tempBattleBaseUnitData.setActionRate(ActionID.PARALYSIS, paralysis.get(i));
                tempBattleBaseUnitData.setActionRate(ActionID.STOP, stop.get(i));
                tempBattleBaseUnitData.setActionRate(ActionID.BLINDNESS, blindness.get(i));
                tempBattleBaseUnitData.setActionRate(ActionID.CURSE, curse.get(i));

                tempBattleBaseUnitData.setAlimentTime(ActionID.POISON, poison_time.get(i));
                tempBattleBaseUnitData.setAlimentTime(ActionID.PARALYSIS, paralysis_time.get(i));
                tempBattleBaseUnitData.setAlimentTime(ActionID.STOP, stop_time.get(i));
                tempBattleBaseUnitData.setAlimentTime(ActionID.BLINDNESS, blindness_time.get(i));
                tempBattleBaseUnitData.setAlimentTime(ActionID.CURSE, curse_time.get(i));

                tempBattleBaseUnitData.setPower(power.get(i));

                List<String> tempDropItem;
                List<Double> tempDropItemPercent;
                for (int j = 0; j < 3 ; j++) {
                    switch (j) {
                        case 1:
                            tempDropItem = drop_item1;
                            tempDropItemPercent = drop_item_percent1;
                            break;
                        case 2:
                            tempDropItem = drop_item2;
                            tempDropItemPercent = drop_item_percent2;
                            break;
                        case 3:
                            tempDropItem = drop_item3;
                            tempDropItemPercent = drop_item_percent3;
                            break;
                        default:
                            tempDropItem = null;
                            tempDropItemPercent = null;
                            break;
                    }

                    if (tempDropItem == null) {
                        continue;
                    }
                    if (tempDropItem.get(i) == null) {
                        continue;
                    }
                    //武器の場合
                    EQUIPMENT_KIND bufEK;
                    switch (tempDropItem.get(i)) {
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

                        tempBattleBaseUnitData.setDropItemName(j, tempDropItem.get(i));
                        tempBattleBaseUnitData.setDropItemKind(j, Constants.Item.ITEM_KIND.EQUIPMENT);
                        tempBattleBaseUnitData.setDropItemRate(j, tempDropItemPercent.get(i));
                    } else {
                        //消費アイテムの場合
                        tempBattleBaseUnitData.setDropItemName(j, tempDropItem.get(i));
                        tempBattleBaseUnitData.setDropItemKind(j, Constants.Item.ITEM_KIND.EXPEND);
                        tempBattleBaseUnitData.setDropItemRate(j, tempDropItemPercent.get(i));
                    }
                }
            }
       // }

    }

    //name で検索して、BattleBaseUnitData型を返す関数。getBattleDungeonUnitDataByName
    public BattleBaseUnitData getBattleUnitDataNum(String bitmap_name) {
        //todo:適当な定数をつける
        for (int i = 0; i < battle_base_unit_datas.size(); i++) {
            if (bitmap_name.equals(battle_base_unit_datas.get(i).getName()) == true) {
                return battle_base_unit_datas.get(i);
            }
        }
        throw new Error("その名前のユニットは存在しません : " + bitmap_name);
    }

    public List<String> getMaohUnitNames() {
        return battle_unit_data_database.getString("maoh_unit_data", "name");
    }

    public List<String> getUnitNames() {
        return name;
    }

    public BattleBaseUnitData getRandomBattleBaseUnitData() {
        Random random = new Random();
        int i = random.nextInt(battle_base_unit_datas.size() - 1);
        return battle_base_unit_datas.get(i);
    }

    public BattleBaseUnitData getRandomBattleBaseUnitDataExceptBoss(DungeonMonsterDataAdmin dungeonMonsterDataAdmin) {
        List<DungeonMonsterData> dungeonMonsterData = dungeonMonsterDataAdmin.getDungeon_monster_data();
        List<DungeonMonsterData> tempDungeonMonsterData = new ArrayList<>();
        for(int i = 0 ; i < dungeonMonsterData.size(); i++) {
            if (dungeonMonsterData.get(i).getType() == 0) {
                tempDungeonMonsterData.add(dungeonMonsterData.get(i));
            }
        }
        Random random = new Random();
        int i = random.nextInt(tempDungeonMonsterData.size() - 1);
        return battle_base_unit_datas.get(i);

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
















