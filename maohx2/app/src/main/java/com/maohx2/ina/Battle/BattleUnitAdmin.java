package com.maohx2.ina.Battle;

import android.app.Activity;
import android.graphics.Paint;

import com.maohx2.fuusya.MapPlateAdmin;
import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.horie.map.DungeonMonsterDataAdmin;
import com.maohx2.horie.map.MapStatus;
import com.maohx2.horie.map.MapStatusSaver;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Constants;

import static com.maohx2.ina.Constants.Item.EQUIPMENT_KIND;
import static com.maohx2.ina.Constants.Item.ITEM_KIND;

import static com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_NORMAL;
import static com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_RATE;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.DungeonModeManage;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.ItemData.EquipmentItemBaseData;
import com.maohx2.ina.ItemData.EquipmentItemBaseDataAdmin;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataCreater;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;

import com.maohx2.ina.ItemData.ItemData;

import static com.maohx2.ina.Constants.BattleUnit.BATTLE_UNIT_MAX;
import static com.maohx2.ina.Constants.Touch.TouchState;
import static com.maohx2.ina.Constants.UnitStatus.Status.ATTACK;
import static com.maohx2.ina.Constants.UnitStatus.Status.DEFENSE;
import static com.maohx2.ina.Constants.UnitStatus.Status.HP;
import static com.maohx2.ina.Constants.UnitStatus.Status.LUCK;
import static com.maohx2.ina.Constants.UnitStatus.BonusStatus;
import static com.maohx2.ina.Constants.DungeonKind.DUNGEON_KIND;

import com.maohx2.ina.Battle.*;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatusViewer;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.effect.Effect;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;

import com.maohx2.kmhanko.Talking.TalkAdmin;

import com.maohx2.kmhanko.plate.BoxImageTextPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.maohx2.ina.Battle.BattleRockCreater;

// text
import android.graphics.Color;

import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Constants.POPUP_WINDOW;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataCreater;
import com.maohx2.kmhanko.itemdata.MiningItemData;

/**
 * Created by ina on 2017/09/21.
 */

//BattleUnitを一括管理するクラス
public class BattleUnitAdmin {

    public enum MODE {
        BATTLE, MINING, MAOH, BOSS, OPENING
    }

    MODE mode;

    BattleUserInterface battle_user_interface;
    BattleUnit[] battle_units = new BattleUnit[BATTLE_UNIT_MAX];
    int battle_palette_mode;
    CalcUnitStatus calc_unit_status;
    Activity battle_activity;
    int MAKER_NUM = 1000;
    TouchMarker[] touch_markers = new TouchMarker[MAKER_NUM];
    DungeonModeManage dungeonModeManage;

    //by kmhanko
    BattleUnitDataAdmin battleUnitDataAdmin;
    Paint paint = new Paint();
    MyDatabaseAdmin databaseAdmin;
    TimeLimitBar timeLimitBar;

    ExpendItemDataAdmin expendItemDataAdmin;
    EquipmentItemDataCreater equipmentItemDataCreater;

    MapPlateAdmin mapPlateAdmin;

    Graphic graphic;
    PaletteAdmin palette_admin;
    boolean marker_flag;
    boolean first_attack_frag;
    int attack_count;

    int repeat_count;
    int battle_effect_ID;
    int mine_effect_ID;

    String gameoverMes = "";

    PlayerStatus playerStatus;
    MaohMenosStatus maohMenosStatus;
    SoundAdmin soundAdmin;

    MapStatus mapStatus;
    MapStatusSaver mapStatusSaver;
    EffectAdmin effectAdmin;

    DUNGEON_KIND dungeonKind;

    DungeonMonsterDataAdmin dungeonMonsterDataAdmin;

    TalkAdmin talkAdmin;

    boolean resultOperatedFlag;//リザルト関係の処理を一度だけ呼ぶためのフラグ
    boolean battleEndFlag;//戦闘が終わったかどうか

    InventryS expendInventry;

    //by kmhanko BattleUnitDataAdmin追加
    public void init(
            Graphic _graphic,
            BattleUserInterface _battle_user_interface,
            Activity _battle_activity,
            BattleUnitDataAdmin _battleUnitDataAdmin,
            PlayerStatus _playerStatus,
            PaletteAdmin _palette_admin,
            DungeonModeManage _dungeonModeManage,
            MyDatabaseAdmin _databaseAdmin,
            MapPlateAdmin _map_plate_admin,
            TextBoxAdmin _textBoxAdmin,
            int _repeat_count,
            MaohMenosStatus _maohMenosStatus,
            SoundAdmin _soundAdmin,
            MapStatus _mapStatus,
            MapStatusSaver _mapStatusSaver,
            DUNGEON_KIND _dungeonKind,
            DungeonMonsterDataAdmin _dungeonMonsterDataAdmin,
            TalkAdmin _talkAdmin,
            InventryS _expendInventry
    ) {
        //引数の代入
        graphic = _graphic;
        BattleRockCreater.setGraphic(graphic);
        battle_user_interface = _battle_user_interface;
        battle_activity = _battle_activity;
        battleUnitDataAdmin = _battleUnitDataAdmin;
        palette_admin = _palette_admin;
        maohMenosStatus = _maohMenosStatus;
        soundAdmin = _soundAdmin;
        mapStatus = _mapStatus;
        mapStatusSaver = _mapStatusSaver;

        dungeonModeManage = _dungeonModeManage;
        databaseAdmin = _databaseAdmin;
        mapPlateAdmin = _map_plate_admin;

        dungeonKind = _dungeonKind;

        dungeonMonsterDataAdmin = _dungeonMonsterDataAdmin;
        talkAdmin = _talkAdmin;

        expendInventry = _expendInventry;

        textBoxAdmin = _textBoxAdmin;
        initResultTextBox();
        initResultButton();

        repeat_count = _repeat_count;

        playerStatus = _playerStatus;

        timeLimitBar = new TimeLimitBar(graphic);

        //by kmhanko
        // *** equipItemDataCreaterのインスタンス化 ***
        EquipmentItemBaseDataAdmin equipmentItemBaseDataAdmin = new EquipmentItemBaseDataAdmin(graphic, databaseAdmin);
        List<EquipmentItemBaseData> tempEquipmentItemBaseDatas = equipmentItemBaseDataAdmin.getItemDatas();
        EquipmentItemBaseData[] equipmentItemBaseDatas = new EquipmentItemBaseData[EQUIPMENT_KIND.NUM.ordinal()];
        for (int i = 0; i < EQUIPMENT_KIND.NUM.ordinal(); i++) {
            equipmentItemBaseDatas[i] = tempEquipmentItemBaseDatas.get(i);
        }
        equipmentItemDataCreater = new EquipmentItemDataCreater(equipmentItemBaseDatas);
        // *** ここまで ***


        // *** expendItemDataAdminの取得 ***///
        GlobalData globalData = (GlobalData) battle_activity.getApplication();
        expendItemDataAdmin = globalData.getItemDataAdminManager().getExpendItemDataAdmin();
        // *** ここまで ***

        marker_flag = false;
        first_attack_frag = false;
        attack_count = 0;

        //BattleUnit配列のインスタンス化・初期化
        battle_units[0] = new BattlePlayer(graphic);
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i] = new BattleEnemy(graphic);
        }

        for (int i = 0; i < MAKER_NUM; i++) {
            touch_markers[i] = new TouchMarker(graphic);
        }

        //CalcUnitStatusのインスタンス化・初期化
        calc_unit_status = new CalcUnitStatus();
        calc_unit_status.init();


        palette_admin.resetDungeonUseNum();

        paint.setARGB(230, 0, 0, 0);

    }

    //by kmhanko
    public void reset(MODE _mode) {
        mode = _mode;
        resultOperatedFlag = false;
        battleEndFlag = false;

        //毎戦闘開始時に、前回の戦闘でのゴミなどを消す処理を行う

        //プレイヤーデータのコンバート
        setPlayer(playerStatus);

        palette_admin.setPalletPosition();

        //TODO タッチマーカー残骸を消す

        if (mode == MODE.BATTLE || mode == MODE.MAOH || mode == MODE.BOSS || mode == MODE.OPENING) {
            palette_admin.setPalettesFlags(new boolean[]{true, true, false});
            //spawnEnemy();
        }
        /*
        if (mode == MODE.OPENING) {
            openingTextInit();
        }
        */


        if (mode == MODE.MINING) {
            //for (int i=0;i<100;i++) {//TODO dbug
                palette_admin.setPalettesFlags(new boolean[]{false, false, true});
                dropGeoObject.clear();
                dropGeoObjectKind.clear();
                spawnRock();
                timeLimitBar.reset(30 * 30);
                //deleteEnemy();
            //}
        }

        textBoxAdmin.resetTextBox(resultTextBoxID);
    }

    //by kmhanko
    //BattleUnitDataAdminを使用し、敵情報をBattleUnit配列にセットする関数 enemy(i >=1 )にしか対応していない点に注意
    private int setBattleUnitData(String enemyName, int repeatCount) {
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (!battle_units[i].isExist()) {
                BattleBaseUnitData tempBattleBaseUnitData = battleUnitDataAdmin.getBattleUnitDataNum(enemyName);
                battle_units[i].setBattleUnitDataEnemy(tempBattleBaseUnitData, repeatCount, mode == MODE.MAOH);
                if (mode == MODE.MAOH) {
                    //魔王の弱体化

                    battle_units[i].setMaxHitPoint(battle_units[i].getMaxHitPoint() - maohMenosStatus.getGeoHP());
                    if (battle_units[i].getMaxHitPoint() <= 0) {
                        battle_units[i].setMaxHitPoint(1);
                    }
                    battle_units[i].setHitPoint(battle_units[i].getMaxHitPoint());
                    battle_units[i].setAttack(battle_units[i].getAttack() - maohMenosStatus.getGeoAttack());
                    if (battle_units[i].getAttack() < 0) {
                        battle_units[i].setAttack(0);
                    }

                    battle_units[i].setDefence(battle_units[i].getDefence() - maohMenosStatus.getGeoDefence());
                    if (battle_units[i].getDefence() < 0) {
                        battle_units[i].setDefence(0);
                    }

                    battle_units[i].setLuck(battle_units[i].getLuck() - maohMenosStatus.getGeoLuck());
                    if (battle_units[i].getLuck() < 0) {
                        battle_units[i].setLuck(0);
                    }

                }
                return i;
            }
        }
        return -1;
    }

    public void setPlayer(PlayerStatus playerStatus) {
        playerStatus.calcStatus();
        battle_units[0].setBattleUnitDataPlayer(playerStatus.makeBattleDungeonUnitData());
        battle_units[0].setHitPoint(playerStatus.getNowHP());
    }

    public void spawnEnemy(String[] monsters) {
        for (int i = 0; i < monsters.length; i++) {
            setBattleUnitData(monsters[i], repeat_count);
        }
    }

    public void spawnMaoh(String[] monsters) {
        for (int i = 0; i < monsters.length; i++) {
            setBattleUnitData(monsters[i], repeat_count);
        }
    }

    public void deleteEnemy() {
        for (int i = 0; i < battle_units.length; i++) {
            //battle_units[i].clear();
            battle_units[i].existIs(false);
            battle_units[i].dropFlagIs(false);
        }
    }


    public void spawnEnemy() {
        //TODO 仮。本当はダンジョンのデータなどを引数にして出現する敵をランダムなどで決定する

        //一覧
        //setBattleUnitData("m014", 0); //通常攻撃のみ
        setBattleUnitData("e54-3", 0); //毒攻撃のみ
        //setBattleUnitData("e01-0", 0); //麻痺のみ
        //setBattleUnitData("e88-0", 0); //ストップのみ
        //setBattleUnitData("e74-0", 0); //カウンター持ち　暗黒のみ
        setBattleUnitData("m003-2", 0); //呪いのみ
        setBattleUnitData("e96-0", 0); //ステルス持ち　行動いろいろ
        setBattleUnitData("e27", 0); //バリア持ち　行動いろいろ
        //setBattleUnitData("m007", 0); //行動いろいろ
        //setBattleUnitData("e103-0", 0);//バリア持ち　行動いろいろ
        setBattleUnitData("e83-1", 0);//カウンター持ち　行動いろいろ
        //setBattleUnitData("e94-3", 0);//ステルス持ち　行動いろいろ

    }

    public void spawnRock() {
        //GeoMining画面のスポーン用。岩を出現させる

        Random r = new Random();


        //岩に対応する敵を決定し、その対応する敵データからHPを決める
        for (int i = 0; i < r.nextInt(3) + 1; i++) {
        //for (int i = 0; i < 100; i++) {
            setRockUnitData();
        }

        //TODO debug
        //getDropGeoAfter();

    }

    public void spawnBoss(String[] monsters) {
        for (int i = 0; i < monsters.length; i++) {
            setBattleUnitData(monsters[i], repeat_count);
        }
    }

    public int setRockUnitData() {
        List<BattleBaseUnitData> battleBaseUnitData = battleUnitDataAdmin.getBattleBaseUnitDataExceptBoss(dungeonMonsterDataAdmin);
        BattleBaseUnitData bBUD = battleUnitDataAdmin.getRandomBattleBaseUnitDataExceptBoss(dungeonMonsterDataAdmin);
        int maxMinParam[][] = new int[4][2];// 第二引数は0がMAX,1がMIN

        for (int i = 0; i < battleBaseUnitData.size(); i++) {
            int tempParam[] = battleBaseUnitData.get(i).getStatus(repeat_count, 5.042);
            for (int j = 0; j < maxMinParam.length; j++) {
                if (tempParam[1+j] * battleBaseUnitData.get(i).getPower() > maxMinParam[j][0] || i == 0) {
                    maxMinParam[j][0] = tempParam[1+j] * battleBaseUnitData.get(i).getPower();
                }
                if (tempParam[1+j] * battleBaseUnitData.get(i).getPower() < maxMinParam[j][1] || i == 0) {
                    maxMinParam[j][1] = tempParam[1+j] * battleBaseUnitData.get(i).getPower();
                }
            }
        }


        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (!battle_units[i].isExist()) {
                ((BattleEnemy) battle_units[i]).setBattleBaseUnitDataForRock(bBUD);
                int dropGeoNum = getDropGeoBefore(battle_units[i]);
                float rareRate = 0.0f;
                switch (dropGeoObjectKind.get(dropGeoNum)) {
                    case HP:
                    case HP_RATE:
                        rareRate = (float) (dropGeoObject.get(dropGeoNum) - maxMinParam[0][1]) / (float) (maxMinParam[0][0] - maxMinParam[0][1]);
                        break;
                    case ATTACK:
                    case ATTACK_RATE:
                        rareRate = (float) (dropGeoObject.get(dropGeoNum) - maxMinParam[1][1]) / (float) (maxMinParam[1][0] - maxMinParam[1][1]);
                        break;
                    case DEFENCE:
                    case DEFENCE_RATE:
                        rareRate = (float) (dropGeoObject.get(dropGeoNum) - maxMinParam[2][1]) / (float) (maxMinParam[2][0] - maxMinParam[2][1]);
                        break;
                    case LUCK:
                    case LUCK_RATE:
                        rareRate = (float) (dropGeoObject.get(dropGeoNum) - maxMinParam[3][1]) / (float) (maxMinParam[3][0] - maxMinParam[3][1]);
                        break;
                }
                int hp = bBUD.getDbStatus(BattleBaseUnitData.DbStatusID.InitialHP);
                int attack = bBUD.getDbStatus(BattleBaseUnitData.DbStatusID.InitialAttack);
                int defence = bBUD.getDbStatus(BattleBaseUnitData.DbStatusID.InitialDefence);
                BattleBaseUnitData tempBBUD = BattleRockCreater.getBattleBaseUnitData(hp, attack, defence, rareRate);//ダメージ計算上で攻撃力が必要
                battle_units[i].setBattleUnitDataRock(tempBBUD, repeat_count);
                return i;
            }
        }
        return -1;
    }


    public void update() {
        battle_units[0].setDamagedFlag(false);
        double touch_x = battle_user_interface.getTouchX();
        double touch_y = battle_user_interface.getTouchY();
        TouchState touch_state = battle_user_interface.getTouchState();

        if (!battleEndFlag) {
            //if (!battleEndFlag && !(mode == MODE.OPENING && text_mode)) {

            battle_units[0].update();//この内部で毒の処理も行われている
            if (battle_units[0].getHitPoint() <= 0) {
                //毒で死んだ
                gameoverMes = "毒の状態異常で";
                gameOver();
                resultOperatedFlag = true;
                battleEndFlag = true;
            }
        }
        if (!battleEndFlag) {//毒で死んだ場合のために改めて

            if (battle_units[0].getAlimentCounts(BattleBaseUnitData.ActionID.PARALYSIS.ordinal() - 1) == 0 || battle_units[0].getAlimentCounts(BattleBaseUnitData.ActionID.PARALYSIS.ordinal() - 1) % 2 == 0) {
                attack_count++;
            }

            if (marker_flag == false) {
                palette_admin.update(true);
            }

            //ストップ状態ならば攻撃できない
            if (battle_units[0].getAlimentCounts(BattleBaseUnitData.ActionID.STOP.ordinal() - 1) == 0) {
                if (palette_admin.doUsePalette() == false) {
                    //プレイヤーの攻撃によるマーカーの設置
                    if (!resultOperatedFlag) {
                        if ((touch_state == TouchState.DOWN) || (touch_state == TouchState.DOWN_MOVE) || (touch_state == TouchState.MOVE)) {
                            EquipmentItemData attack_equipment = null;
                            if (mode == MODE.BATTLE || mode == MODE.MAOH || mode == MODE.BOSS || mode == MODE.OPENING) {
                                attack_equipment = palette_admin.getEquipmentItemData();
                            }
                            if (mode == MODE.MINING) {
                                attack_equipment = palette_admin.getMiningItemData();
                            }
                            if (attack_equipment != null) {
                                if (attack_equipment.getDungeonUseNum() > 0) {
                                    //最高攻撃頻度を上回っていないか
                                    if ((first_attack_frag == false && attack_count >= attack_equipment.getTouchFrequency()) || (first_attack_frag == true && attack_count >= attack_equipment.getTouchFrequency() * attack_equipment.getAutoFrequencyRate())) {
                                        if (mode == MODE.BATTLE || mode == MODE.MAOH || mode == MODE.BOSS || mode == MODE.OPENING) {
                                            attack_equipment.setDungeonUseNum(attack_equipment.getDungeonUseNum() - 1);
                                        }
                                        first_attack_frag = true;
                                        marker_flag = true;
                                        attack_count = 0;
                                        for (int i = 0; i < MAKER_NUM; i++) {
                                            if (touch_markers[i].isExist() == false) {
                                                //todo:attackの計算
                                                touch_markers[i].generate((int) touch_x, (int) touch_y, attack_equipment.getRadius(), battle_units[0].getAttack() + attack_equipment.getAttack(), attack_equipment.getDecayRate());
                                                System.out.println("装備攻撃力:" + attack_equipment.getAttack());
                                                System.out.println("プレーヤー攻撃力:" + battle_units[0].getAttack());
                                                System.out.println("マーカーダメージ:" + touch_markers[i].getDamage());
                                                //エフェクト by Horie
                                                if (mode == MODE.MINING) {
                                                    switch (attack_equipment.getName()) {
                                                        case "スコップ":
                                                            mine_effect_ID = effectAdmin.createEffect("scoop_effect", "scoop_effect", 5, 4);
                                                            break;
                                                        case "ハンマー":
                                                            mine_effect_ID = effectAdmin.createEffect("hammer_effect", "hammer_effect", 5, 1);
                                                            break;
                                                        case "爆弾":
                                                            mine_effect_ID = effectAdmin.createEffect("bomb_effect", "bomb_effect", 5, 2);
                                                            break;
                                                        case "ダイナマイト":
                                                            mine_effect_ID = effectAdmin.createEffect("dynamite_effect", "dynamite_effect", 5, 4);
                                                            break;
                                                        case "ダイナマイト束":
                                                            mine_effect_ID = effectAdmin.createEffect("bunch_of_dynamite_effect", "bunch_of_dynamite_effect", 5, 4);
                                                            break;
                                                    }
                                                    effectAdmin.getEffect(mine_effect_ID).setPosition((int) touch_x, (int) touch_y);
                                                    effectAdmin.getEffect(mine_effect_ID).start();

                                                    break;
                                                } else {
                                                    switch (attack_equipment.getEquipmentKind()) {
                                                        case AX:
                                                            battle_effect_ID = effectAdmin.createEffect("axe_effect", "axe_effect", 3, 5);
                                                            break;
                                                        case BARE:
                                                            battle_effect_ID = effectAdmin.createEffect("barehand_effect", "barehand_effect", 5, 3);
                                                            break;
                                                        case BOW:
                                                            battle_effect_ID = effectAdmin.createEffect("bow_effect", "bow_effect", 5, 2);
                                                            break;
                                                        case WAND:
                                                            battle_effect_ID = effectAdmin.createEffect("cane_effect", "cane_effect", 14, 1);
                                                            break;
                                                        case GUN:
                                                            battle_effect_ID = effectAdmin.createEffect("gun_effect", "gun_effect", 5, 2);
                                                            break;
                                                        case FIST:
                                                            battle_effect_ID = effectAdmin.createEffect("knuckle_effect", "knuckle_effect", 6, 3);
                                                            break;
                                                        case CLUB:
                                                            battle_effect_ID = effectAdmin.createEffect("mace_effect", "mace_effect", 5, 1);
                                                            break;
                                                        case MUSIC:
                                                            battle_effect_ID = effectAdmin.createEffect("musical_instrument_effect", "musical_instrument_effect", 1, 15);
                                                            break;
                                                        case SPEAR:
                                                            battle_effect_ID = effectAdmin.createEffect("spear_effect", "spear_effect", 5, 3);
                                                            break;
                                                        case SWORD:
                                                            battle_effect_ID = effectAdmin.createEffect("sword_effect", "sword_effect", 9, 1);
                                                            break;
                                                        case WHIP:
                                                            battle_effect_ID = effectAdmin.createEffect("whip_effect", "whip_effect", 5, 5);
                                                            break;

//                                                    case MONSTER:
//                                                        effect_id = effect_ID[1];
//                                                    case SHIELD:
//                                                        effect_id = effect_ID[1];
//                                                    case NUM:
//                                                        effect_id = effect_ID[1];
                                                    }
                                                    if (attack_equipment.getEquipmentKind() == EQUIPMENT_KIND.MUSIC) {
                                                        effectAdmin.getEffect(battle_effect_ID).setPosition(0, 150);
                                                    } else {
                                                        effectAdmin.getEffect(battle_effect_ID).setPosition((int) touch_x, (int) touch_y);
                                                    }
                                                    effectAdmin.getEffect(battle_effect_ID).start();

                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if ((touch_state == TouchState.UP) || (touch_state == TouchState.AWAY)) {
                first_attack_frag = false;
                marker_flag = false;
            }

            //マーカーの縮小
            for (int i = 0; i < MAKER_NUM; i++) {
                if (touch_markers[i].isExist() == true) {
                    touch_markers[i].update();
                }
            }


            //敵HP更新
            boolean markHitFlag;
            for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
                if (battle_units[i].isExist() == true) {
                    int ex = (int) battle_units[i].getPositionX();
                    int ey = (int) battle_units[i].getPositionY();
                    int er = (int) battle_units[i].getRadius();
                    markHitFlag = false;
                    for (int j = 0; j < MAKER_NUM; j++) {
                        if (touch_markers[j].isExist() == true) {
                            int cx = touch_markers[j].getPositionX();
                            int cy = touch_markers[j].getPositionY();
                            int cr = touch_markers[j].getRadius();

                            //マーカーが当たっている
                            if ((ex - cx) * (ex - cx) + (ey - cy) * (ey - cy) < (er + cr) * (er + cr)) { //kmhanko 修正
                                markHitFlag = true;

                                double strong_ratio = (touch_markers[j].getDamage() * 100.0) / (battle_units[i].getDefence() * 22222.0 + 1);
                                strong_ratio = Math.pow(strong_ratio, 7.0f);
                                double level_rate = battle_units[i].getAttack() / 1000.0 * battle_units[i].getDefence() / 100.0 * battle_units[i].getMaxHitPoint() / 1000.0;
                                level_rate = Math.pow(level_rate, 0.4);
                                int new_hp = battle_units[i].getHitPoint() - (int) (20.0 * strong_ratio * level_rate);

                                if (((BattleEnemy) (battle_units[i])).isSpecialAction() == false) {
                                    //敵が特殊行動していないなら
                                    //int new_hp = battle_units[i].getHitPoint() - touch_markers[j].getDamage()/(1+battle_units[i].getDefence()*battle_units[i].getDefence()*battle_units[i].getDefence());//System.out.println("マーカーダメージ:"+touch_markers[j].getDamage());

                                    //敵が特殊行動していないなら

//                                    int coef = 3;//3だと4回タッチで倒せるくらい
//                                    int new_hp = battle_units[i].getHitPoint() - coef * touch_markers[j].getDamage() / (battle_units[i].getDefence() * battle_units[i].getDefence() * battle_units[i].getDefence() + 1);
//                                    int new_hp = battle_units[i].getHitPoint() - (int) ((int) (real_atk * real_atk * real_atk * real_atk * 0.00032) / (battle_units[i].getDefence() + 1));
//                                    double exp = 4 * (Math.log10(battle_units[i].getDefence()) / Math.log10(100) - 1);
//                                    double exp = 4 * battle_units[i].getDefence() / 100.0;
//                                    double exp = Math.pow(battle_units[i].getDefence() / 100.0, 4);
//                                    double tmp_exp = battle_units[i].getDefence();
//                                    double exp = tmp_exp * tmp_exp * tmp_exp * tmp_exp * tmp_exp * tmp_exp * tmp_exp;
//                                    double exp = tmp_exp * tmp_exp * tmp_exp * tmp_exp * tmp_exp;
//                                    exp = exp * exp * exp;

//                                    System.out.println("pow_desuyo_teki" + exp);

//                                    System.out.println("log_atk_desu" + battle_units[0].getAttack());
//                                    int new_hp = battle_units[i].getHitPoint() - ((int) (touch_markers[j].getDamage() * 0.09) / (battle_units[i].getDefence() * (int) Math.pow(2, exp) + 1));
//                                    int new_hp = battle_units[i].getHitPoint() - (int) ((touch_markers[j].getDamage() * 0.09) / (battle_units[i].getDefence() * exp + 1));
                                    //fusya double strong_ratio = (touch_markers[j].getDamage() * 100.0) / (battle_units[i].getDefence() * 22222.0);
//                                    double strong_ratio = (touch_markers[j].getDamage() * 100) / (battle_units[i].getDefence() * 22222 + 1);
                                    //fusya strong_ratio = strong_ratio * strong_ratio * strong_ratio * strong_ratio * strong_ratio;
                                    //fusya strong_ratio = strong_ratio * strong_ratio * strong_ratio;
                                    //fusya double level_rate = battle_units[i].getAttack() * battle_units[i].getDefence() * battle_units[i].getMaxHitPoint() / 1000.0 / 100.0 / 1000.0;
//                                    System.out.println("level_da_Atk_1_" + battle_units[i].getAttack());
//                                    System.out.println("level_da_Def_1_" + battle_units[i].getDefence());
//                                    System.out.println("level_da_MHP_1_" + battle_units[i].getMaxHitPoint());
//                                    System.out.println("level_teki_1_" + level_rate);
                                    //fusya level_rate = Math.pow(level_rate, 0.4);
//                                    System.out.println("level_teki_2_" + level_rate);
                                    //fusya int new_hp = battle_units[i].getHitPoint() - (int) (20 * strong_ratio * level_rate);

//                                    System.out.println("strong_ratio_teki" + strong_ratio);
//                                    System.out.println("hp_teki_desuyo_" + new_hp);//270だけ減る(全体は1000)
//                                    System.out.println("damage_teki_desuyo_" + touch_markers[j].getDamage());//22222
//                                    System.out.println("def_teki_desuyo_" + battle_units[i].getDefence());//100

                                    //OPならダメージは0
                                    if (mode == MODE.OPENING) {
                                        new_hp = battle_units[i].getHitPoint();
                                    }

                                    if (new_hp > 0) {
                                        battle_units[i].setHitPoint(new_hp);
                                    } else {
                                        //by kmhanko
                                        //岩は特殊行動しないため、死亡判定についてこの位置のみに記述すれば良い。
                                        //岩はマーカーが当たっているときはマーカーが当たっている間は死亡しない
                                        if (battle_units[i].getUnitKind() != Constants.UnitKind.ROCK) {
                                            battle_units[i].existIs(false);
                                        } else {
                                            //岩の場合はHPが負であってもHPにセットする
                                            battle_units[i].setHitPoint(new_hp);
                                        }
                                    }
                                } else {

                                    if (((BattleEnemy) (battle_units[i])).getSpecialAction() == BattleBaseUnitData.SpecialAction.BARRIER) {
                                        //敵がバリアを張っているなら(ダメージを受けない)
                                    } else if (((BattleEnemy) (battle_units[i])).getSpecialAction() == BattleBaseUnitData.SpecialAction.COUNTER) {
                                        //カウンターでも敵にダメージは入る
                                        if (new_hp > 0) {
                                            battle_units[i].setHitPoint(new_hp);
                                        } else {
                                            battle_units[i].existIs(false);
                                        }

                                        //プレイヤーも同じだけダメージを食らう
                                        int damage = (int) (20 * strong_ratio * level_rate);
                                        new_hp = battle_units[0].getHitPoint() - damage;
                                        if (new_hp <= 0) {
                                            //負けたとき
                                            gameoverMes = "敵のカウンター攻撃で" + String.valueOf((int)((float)(damage) / PlayerStatusViewer.EXPRESS_RATE)) +"ダメージを受けて";
                                            gameOver();
                                            resultOperatedFlag = true;
                                            battleEndFlag = true;
                                            new_hp = 0;
                                        }
                                        battle_units[0].setDamagedFlag(true);
                                        battle_units[0].setHitPoint(new_hp);

                                    } else if (((BattleEnemy) (battle_units[i])).getSpecialAction() == BattleBaseUnitData.SpecialAction.STEALTH) {
                                        if (new_hp > 0) {
                                            battle_units[i].setHitPoint(new_hp);
                                        } else {
                                            battle_units[i].existIs(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!markHitFlag) {
                        //岩の場合の死亡タイミング(いかなるマーカーにも当たっておらず、HPが負のとき)
                        if (battle_units[i].getUnitKind() == Constants.UnitKind.ROCK && battle_units[i].getHitPoint() <= 0) {
                            battle_units[i].existIs(false);
                            //HPがマイナスになっているはずなので、マイナス分をジオ計算時に反映する
                        }
                    }
                }
            }

            //ポーションなどの使用の処
            if (palette_admin.checkSelectedExpendItemData() != null) {
                int heel_to_player = (int) (battle_units[0].getMaxHitPoint() * palette_admin.checkSelectedExpendItemData().getHp() / 100.0f);
                palette_admin.deleteExpendItemData();
                soundAdmin.play("cure00");
                int new_hp = heel_to_player + battle_units[0].getHitPoint();
                if (new_hp > battle_units[0].getMaxHitPoint()) {
                    new_hp = battle_units[0].getMaxHitPoint();
                }
                battle_units[0].setHitPoint(new_hp);
            }

            //敵の更新と攻撃処理
            for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
                if (battle_units[i].isExist() == true) {
                    int damage_to_player = battle_units[i].update();
                    if (damage_to_player > 0) {
                        EquipmentItemData defense_equipment = palette_admin.getEquipmentItemData();
                        float damage_rate = 1;
                        if (defense_equipment != null) {
                            damage_rate = (1 - (float) defense_equipment.getDefence() / 100.0f);
                        }

                        /*
                        int heel_to_player = 0;
                        if (palette_admin.checkSelectedExpendItemData() != null) {
                            heel_to_player = (int)(battle_units[0].getMaxHitPoint() * palette_admin.checkSelectedExpendItemData().getHp() / 100.0f);
                            palette_admin.deleteExpendItemData();
                            soundAdmin.play("cure00");
                        }
                        */

                        /*
                        int new_hp = battle_units[0].getHitPoint() - (int) ((damage_to_player/(battle_units[0].getDefence()*battle_units[0].getDefence()*battle_units[0].getDefence())) * damage_rate) + heel_to_player;
                        if (new_hp > battle_units[0].getMaxHitPoint()) {
                            new_hp = battle_units[0].getMaxHitPoint();
                        }
                        */


                        double strong_ratio = (damage_to_player * 2222.0) / (battle_units[0].getDefence() * 1000.0 + 1.0);
                        strong_ratio = strong_ratio * strong_ratio * strong_ratio * strong_ratio * strong_ratio;
                        strong_ratio = strong_ratio * strong_ratio * strong_ratio;

                        //by kmhanko オーバーフローするので修正
                        double level_rate = battle_units[i].getAttack() / 1000.0 * battle_units[i].getDefence() / 100.0 * battle_units[i].getMaxHitPoint() / 1000.0;
//                        double level_rate = battle_units[0].getAttack() * battle_units[0].getDefence() * battle_units[0].getMaxHitPoint() / 22222.0 / 22222.0 / 2222.0;
//                        System.out.println("damage_to_desuno_" + damage_to_player);
//
//                        System.out.println("level_wa_Atk_1_" + battle_units[0].getAttack());
//                        System.out.println("level_wa_Def_1_" + battle_units[0].getDefence());
//                        System.out.println("level_wa_MHP_1_" + battle_units[0].getMaxHitPoint());
//
//                        System.out.println("level_pl_1_" + level_rate);
                        level_rate = Math.pow(level_rate, 0.4);//0.4だと、自他のStatusが定数倍になっても、敵を倒すための確定数はほぼ変化しない
//                        System.out.println("level_pl_2_" + level_rate);

                        int damage = (int) ((133.0 * strong_ratio) * level_rate * damage_rate);
                        int new_hp = battle_units[0].getHitPoint() - damage;
                        //int new_hp = battle_units[0].getHitPoint() - (int) ((133.0 * strong_ratio) * level_rate * damage_rate - heel_to_player);
//                        int new_hp = battle_units[0].getHitPoint() - (int) ((real_atk * exp * 0.295 / (real_def + 1) * damage_rate) + heel_to_player);

//                        System.out.println("strong_ratio_pl" + strong_ratio);
//                        System.out.println("hp_pl_desuyo_" + new_hp);//PlayerのHP22222に対して、133ずつ減る
//                        System.out.println("damage_pl_desuyo_" + damage_to_player);//1000
//                        System.out.println("def_pl_desuyo_" + battle_units[0].getDefence());//2222

                        gameoverMes = "敵から" + String.valueOf((int)((float)(damage) / PlayerStatusViewer.EXPRESS_RATE)) + "ダメージを受けて";

                        if (new_hp > battle_units[0].getMaxHitPoint()) {
                            new_hp = battle_units[0].getMaxHitPoint();
                        }


                        //状態異常攻撃
                        BattleBaseUnitData.ActionID actionID = ((BattleEnemy) (battle_units[i])).checkActionID();
                        if (actionID != BattleBaseUnitData.ActionID.NORMAL_ATTACK) {
                            if (actionID != BattleBaseUnitData.ActionID.CURSE) {
                                //状態異常のカウントが長くなるようであれば，状態異常のカウントを更新
                                if (battle_units[0].getAlimentCounts(actionID.ordinal() - 1) < ((BattleEnemy) (battle_units[i])).getAlimentTime(actionID)) {
                                    battle_units[0].setAilmentCounts(actionID.ordinal() - 1, ((BattleEnemy) (battle_units[i])).getAlimentTime(actionID));
                                }
                            } else {
                                //呪いに関してはカウントを自身につけて，誰か一人でもカウントが0になったら死亡とする
                                if (battle_units[i].getAlimentCounts(actionID.ordinal() - 1) < 0) {
                                    battle_units[i].setAilmentCounts(actionID.ordinal() - 1, ((BattleEnemy) (battle_units[i])).getAlimentTime(actionID));
                                }
                            }
                        }


                        if (battle_units[i].getAlimentCounts(BattleBaseUnitData.ActionID.CURSE.ordinal() - 1) == 0) {
                            new_hp = 0;
                            gameoverMes = "呪いの状態異常で";
                        }

                        if (new_hp <= 0) {
                            //負けたとき
                            gameOver();
                            resultOperatedFlag = true;
                            battleEndFlag = true;
                            new_hp = 0;
                        }
                        battle_units[0].setDamagedFlag(true);
                        battle_units[0].setHitPoint(new_hp);
                    }
                }
            }
        }

        //Opening時用のアップデート
        if (mode == MODE.OPENING) {
            openingUpdate();
        }

        boolean result_flag = true;
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            result_flag = result_flag && !battle_units[i].isExist();
        }

        if (battle_units[0].getHitPoint() > 0 && ((result_flag == true || (timeLimitBar.isTimeUp() && timeLimitBar.isExist())) && !resultOperatedFlag)) {
            //戦闘が終了した時
            resultOperatedFlag = true;
            battleEndFlag = true;
            // by kmhanko
            win();
        }

        //text関係
        if (resultOperatedFlag && battleEndFlag) {
            resultButtonCheck();
            resultButtonGroup.update();
        }

        //timeLimit関係
        if (mode == MODE.MINING && !battleEndFlag) {
            timeLimitBar.update();
        }
    }

    //TODO ゲームオーバー処理
    public void gameOver() {
        winFlag = false;
        //死んだらお金を半分にする
        playerStatus.setMoney(playerStatus.getMoney() / 2);
        switch (mode) {
            case BATTLE:
            case BOSS:
                gameoverMessage();
                //battleEnd();
                break;
            case MINING:
                //ここには来ない
                battleEnd();
                break;
            case MAOH:
                gameoverMessage();
                //battleEnd();
                break;
            case OPENING:
                /*
                resultTextBoxUpdate(new String[]{"うわあああああああああ！",});
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                */
                //battleEnd();
                break;
        }
    }

    public void win() {
        winFlag = true;
        switch (mode) {
            case BATTLE:
            case BOSS:
                getDropItem();
                getDropMoney();
                growUp();
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                break;
            case MINING:
                if (timeLimitBar.isTimeUp()) {
                    //TODO 時間切れで終了した場合
                    resultTextBoxUpdate(new String[]{"時間切れになってしまった！", "今回の獲得ジオはありません"});
                } else {
                    //ジオを掘り尽くした場合
                    getDropGeoAfter();
                }
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                break;
            case MAOH:
                growUp();
                resultTextBoxUpdate(new String[]{"魔王を倒した！",});
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                break;
            case OPENING:
                //本来来ない場所
                resultTextBoxUpdate(new String[]{"あれ？なんか！",});
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                break;
        }
    }

    public void draw() {


        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isExist() == true) {
                battle_units[i].draw();
            }
        }

        //盲目だった場合の処理
        /*
        if(battle_units[0].getAlimentCounts(BattleBaseUnitData.ActionID.BLINDNESS.ordinal() -1) > 0){
            graphic.bookingDrawRect(0,0,1000,300,paint);
            graphic.bookingDrawRect(1000,0,1601,600,paint);
            graphic.bookingDrawRect(600,600,1601,901,paint);
            graphic.bookingDrawRect(0,300,600,901,paint);
        }
        */
        if (mode == MODE.BATTLE || mode == MODE.MAOH || mode == MODE.BOSS || mode == MODE.OPENING) {
            ((BattlePlayer) (battle_units[0])).drawStatus();
        }
        if (mode == MODE.MINING) {
            //制限時間の表示
            timeLimitBar.draw();
        }

        for (int i = 0; i < MAKER_NUM; i++) {
            if (touch_markers[i].isExist() == true) {
                touch_markers[i].draw();
            }
        }

        //デバッグ用 プレイヤーのステータスを表示
        //debugPlayerStatusDraw();
        palette_admin.draw();

        /*
        if (talkCharaFlag && text_mode) {
            graphic.bookingDrawBitmapData(talkChara[count]);
        }
        */

        //text
        resultButtonGroup.draw();

    }

    //by kmhanko

    //1ずれるので注意
    List<Integer> dropGeoObject = new ArrayList<>();
    List<Constants.Item.GEO_KIND_ALL> dropGeoObjectKind = new ArrayList<>();

    private int getDropGeoBefore(BattleUnit tempBattleUnit) {
        //岩からのジオドロップ
        BattleBaseUnitData bBUDforRock = ((BattleEnemy) tempBattleUnit).getBattleBaseUnitDataForRock();
        int[] status = bBUDforRock.getStatus(repeat_count, 5.042);
        //このジオは何ジオか決定する
        if (Math.random() < 0.7) {
            //NormalGeo
            GEO_PARAM_KIND_NORMAL kindNormal = GeoObjectDataCreater.getRandKindNormal();
            switch (kindNormal) {
                case HP:
                    dropGeoObject.add(status[HP.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.HP);
                    break;
                case ATTACK:
                    dropGeoObject.add(status[ATTACK.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.ATTACK);
                    break;
                case DEFENCE:
                    dropGeoObject.add(status[DEFENSE.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.DEFENCE);
                    break;
                case LUCK:
                    dropGeoObject.add(status[LUCK.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.LUCK);
                    break;
            }
        } else {
            //RateGeo
            GEO_PARAM_KIND_RATE kindRate = GeoObjectDataCreater.getRandKindRate();
            switch (kindRate) {
                case HP_RATE:
                    dropGeoObject.add(status[HP.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.HP_RATE);
                    break;
                case ATTACK_RATE:
                    dropGeoObject.add(status[ATTACK.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.ATTACK_RATE);
                    break;
                case DEFENCE_RATE:
                    dropGeoObject.add(status[DEFENSE.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.DEFENCE_RATE);
                    break;
                case LUCK_RATE:
                    dropGeoObject.add(status[LUCK.ordinal()] * bBUDforRock.getPower());
                    dropGeoObjectKind.add(Constants.Item.GEO_KIND_ALL.LUCK_RATE);
                    break;
            }
        }
        return dropGeoObject.size() - 1;
    }

    private void getDropGeoAfter() {
        List<String> dropItemNames = new ArrayList<String>();
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            GeoObjectData geoObjectData = null;
            if (battle_units[i].getUnitKind() == Constants.UnitKind.ROCK && battle_units[i].isDropFlag()) {
                int parameter = dropGeoObject.get(i - 1);
                if (battle_units[i].getHitPoint() < 0) {
                    parameter = (int) ((float) parameter * (float) (battle_units[i].getHitPoint() + battle_units[i].getMaxHitPoint()) / (float) battle_units[i].getMaxHitPoint());
                }
                if (parameter > 0) {
                    geoObjectData = GeoObjectDataCreater.getGeoObjectData(parameter, dropGeoObjectKind.get(i - 1));
                }
                if (geoObjectData != null) {
                    mapPlateAdmin.getInventry().addItemData(geoObjectData);
                    dropItemNames.add(geoObjectData.getName());
                }
            }
        }
        resultTextBoxUpdateItems(dropItemNames);
    }


    //敵を倒したら成長する処理
    private void growUp() {
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isDropFlag()) {
                playerStatus.addBaseHP(battle_units[i].getBattleDungeonUnitData().getBonusStatus(BonusStatus.BONUS_HP));
                playerStatus.addBaseAttack(battle_units[i].getBattleDungeonUnitData().getBonusStatus(BonusStatus.BONUS_ATTACK));
                playerStatus.addBaseDefence(battle_units[i].getBattleDungeonUnitData().getBonusStatus(BonusStatus.BONUS_DEFENSE));
                playerStatus.addBaseLuck(battle_units[i].getBattleDungeonUnitData().getBonusStatus(BonusStatus.BONUS_SPEED));
                //System.out.println("testtt" + battle_units[i].getBattleDungeonUnitData().getBonusStatus(BonusStatus.BONUS_HP));
            }
        }
        playerStatus.calcStatus();
        playerStatus.save();
    }

    //by kmhanko


    private int getDropMoney() {
        int getMoney = 0;
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isDropFlag()) {
                getMoney += (battle_units[i].getAttack() + battle_units[i].getDefence() + battle_units[i].getLuck()) / 3;
            }
        }
        playerStatus.addMoney(getMoney);
        return getMoney;
    }

    //by kmhanko
    private void getDropItem() {
        List<String> dropItemNames = new ArrayList<String>();

        dropItemNames.add(String.valueOf(getDropMoney()) + " Maon");

        BattleBaseUnitData tempBattleBaseUnitData = null;
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isDropFlag()) {
                tempBattleBaseUnitData = battleUnitDataAdmin.getBattleUnitDataNum(battle_units[i].getName());

                EQUIPMENT_KIND[] dropItemEquipmentKind = tempBattleBaseUnitData.getDropItemEquipmentKinds();
                String[] dropItemName = tempBattleBaseUnitData.getDropItemNames();
                double[] dropItemRate = tempBattleBaseUnitData.getDropItemRate();
                ITEM_KIND[] dropItemKind = tempBattleBaseUnitData.getDropItemKinds();
                ItemData tempItemData = null;
                EquipmentItemData eqTempItemData = null;
                double tempRand;
                float rate = 0.0f;
                for (int j = 0; j < Constants.Item.DROP_NUM; j++) {
                    rate = 0.0f;
                    if (dropItemName[j] != null) {
                        tempRand = Math.random();
                        System.out.println("☆タカノ:BattleUnitAdmin#getDropItem : アイテムドロップ率計算 : " + dropItemName[j] + " from " + battle_units[i].getName() + " : " + dropItemRate[j] + " ? " + tempRand);
                        rate += dropItemRate[j];
                        if (rate > tempRand) {
                            switch (dropItemKind[j]) {
                                case EQUIPMENT:
                                    eqTempItemData = equipmentItemDataCreater.getEquipmentItemData(dropItemEquipmentKind[j], battle_units[i].getBattleDungeonUnitData(), battle_units[0].getLuck());

                                    // equipmentItemDataCreater.getEquipmentItemData(dropItemEquipmentKind[j], battle_units[i].getBattleDungeonUnitData());
                                    tempItemData = (ItemData) eqTempItemData;
                                    break;
                                case EXPEND:
                                    tempItemData = expendItemDataAdmin.getOneDataByName(dropItemName[j]);
                                    break;
                                default:
                                    tempItemData = null;
                                    break;
                            }
                        }
                    }
                    if (tempItemData != null) {
                        mapPlateAdmin.getInventry().addItemData(tempItemData);
                        if (tempItemData.getItemKind() == ITEM_KIND.EQUIPMENT) {
                            dropItemNames.add(tempItemData.getName() + "+" + eqTempItemData.getAttack());
                        } else {
                            dropItemNames.add(tempItemData.getName());
                        }
                        System.out.println("☆タカノ:BattleUnitAdmin#getDropItem : アイテムを取得 : " + dropItemName[j] + " from " + battle_units[i].getName());
                        break;
                    }
                }
            }
        }

        // result関係
        resultTextBoxUpdateItems(dropItemNames);
    }

    public BattleUnitAdmin() {
    }

    public void getEffectAdmin(EffectAdmin _effect_admin) {
        effectAdmin = _effect_admin;
    }

    // *** リザルトメッセージ関係 ***

    int resultTextBoxID;
    Paint resultTextPaint;
    TextBoxAdmin textBoxAdmin;
    PlateGroup<BoxImageTextPlate> resultButtonGroup;

    private void initResultTextBox() {
        resultTextBoxID = textBoxAdmin.createTextBox(POPUP_WINDOW.MESS_LEFT, POPUP_WINDOW.MESS_UP, POPUP_WINDOW.MESS_RIGHT, POPUP_WINDOW.MESS_BOTTOM, POPUP_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(resultTextBoxID, true);
        textBoxAdmin.setTextBoxExists(resultTextBoxID, false);
        resultTextPaint = new Paint();
        resultTextPaint.setTextSize(POPUP_WINDOW.TEXT_SIZE);
        resultTextPaint.setColor(Color.WHITE);
    }

    private void resultTextBoxUpdate(String[] messages) {
        textBoxAdmin.setTextBoxExists(resultTextBoxID, true);
        for (int i = 0; i < messages.length; i++) {
            textBoxAdmin.bookingDrawText(resultTextBoxID, messages[i], resultTextPaint);
            if (i == messages.length - 1) {
                textBoxAdmin.bookingDrawText(resultTextBoxID, "MOP", resultTextPaint);
            } else {
                textBoxAdmin.bookingDrawText(resultTextBoxID, "\n", resultTextPaint);
            }
        }
    }

    private void gameoverMessage() {
        resultTextBoxUpdate(
                new String[]{
                        "あなたは",
                        gameoverMes,
                        "やられてしまった！"
                })
        ;
        resultButtonGroup.setUpdateFlag(true);
        resultButtonGroup.setDrawFlag(true);
        return;
    }

    private void resultTextBoxUpdateItems(List<String> itemNames) {

        textBoxAdmin.setTextBoxExists(resultTextBoxID, true);

        String winMessage = "▽入手アイテム▽";
        if (itemNames.size() == 0) {
            textBoxAdmin.bookingDrawText(resultTextBoxID, winMessage, resultTextPaint);
        }


        int row = 0;
        int i = 0;

        while (i < itemNames.size()) {
            if (row == 0) {
                textBoxAdmin.bookingDrawText(resultTextBoxID, winMessage, resultTextPaint);
                textBoxAdmin.bookingDrawText(resultTextBoxID, "\n", resultTextPaint);
                row++;
                continue;
            }
            textBoxAdmin.bookingDrawText(resultTextBoxID, itemNames.get(i), resultTextPaint);
            i++;
            row++;

            if (row == POPUP_WINDOW.MESS_ROW) {
                textBoxAdmin.bookingDrawText(resultTextBoxID, "MOP", resultTextPaint);
                row = 0;
            } else {
                textBoxAdmin.bookingDrawText(resultTextBoxID, "\n", resultTextPaint);
            }
        }
        textBoxAdmin.bookingDrawText(resultTextBoxID, "MOP", resultTextPaint);
    }

    private void initResultButton() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(POPUP_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255, 255, 255, 255);

        resultButtonGroup = new PlateGroup<BoxImageTextPlate>(new BoxImageTextPlate[]{new BoxImageTextPlate(graphic, battle_user_interface, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{POPUP_WINDOW.OK_LEFT, POPUP_WINDOW.OK_UP, POPUP_WINDOW.OK_RIGHT, POPUP_WINDOW.OK_BOTTOM}, "OK", textPaint)});
        resultButtonGroup.setUpdateFlag(false);
        resultButtonGroup.setDrawFlag(false);
    }

    private void resultButtonCheck() {
        if (!(resultButtonGroup.getUpdateFlag())) {
            return;
        }
        int buttonID = resultButtonGroup.getTouchContentNum();
        if (buttonID == 0) { //OK
            //戦闘画面終了する
            battleEnd();
        }
    }

    boolean winFlag;

    public void battleEnd() {
        deleteEnemy();
        playerStatus.setNowHP(battle_units[0].getHitPoint());
        expendInventry.save();


        if (winFlag) {
            switch (mode) {
                case BATTLE:
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP_INIT);
                    break;
                case BOSS:
                    mapPlateAdmin.getMapInventryAdmin().storageMapInventry();
                    if (playerStatus.getClearCount() == playerStatus.getNowClearCount()) {
                        mapStatus.setMapClearStatus(1, dungeonKind.ordinal());
                        mapStatusSaver.save();
                    }
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.TO_WORLD);
                    break;
                case MINING:
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP_INIT);
                    break;
                case MAOH:
                    playerStatus.setMaohWinCount(playerStatus.getMaohWinCount() + 1);
                    playerStatus.save();
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.TO_WORLD);
                    break;
                case OPENING:
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.TO_WORLD);
                    break;
            }
        } else {
            switch (mode) {
                case BATTLE:
                case BOSS:
                case MINING:
                case MAOH:
                case OPENING:
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.TO_WORLD);
                    break;
            }
        }
        resultButtonGroup.setUpdateFlag(false);
        resultButtonGroup.setDrawFlag(false);
        textBoxAdmin.setTextBoxExists(resultTextBoxID, false);
        timeLimitBar.delete();


        for (int i = 0; i < MAKER_NUM; i++) {
            if (touch_markers[i].isExist() == true) {
                touch_markers[i].clear();
            }
        }

        effectAdmin.clearAllEffect();

    }

    // *** リザルトメッセージ関係ここまで ***

    // *** オープニング戦闘の会話文関係

    float talkHpRate[] = new float[]{
            0.8f, 0.6f, 0.4f, 0.2f, 0.0f
    };

    public void openingUpdate() {
        //HPの状況に応じてイベントを発生させる
        for (int i = 0; i < talkHpRate.length; i++) {
            if ((float) battle_units[0].getHitPoint() / (float) battle_units[0].getMaxHitPoint() <= talkHpRate[i]) {
                talkAdmin.start("Opening_in_battle0" + String.valueOf(i), false);
            }
        }

        if (!talkAdmin.isTalking() && battle_units[0].getHitPoint() <= 0) {
            battleEnd();
        }
    }

    public void release() {

    }

    // *** オープニング戦闘の会話文関係 ここまで ***
}

        /*
    //by kmhanko 旧式岩からのジオ
    private void getDropGeo() {
        //岩からのジオドロップ
        List<String> dropItemNames = new ArrayList<String>();
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].getUnitKind() == Constants.UnitKind.ROCK && battle_units[i].isDropFlag()) {
                BattleBaseUnitData bBUDforRock = ((BattleEnemy) battle_units[i]).getBattleBaseUnitDataForRock();
                GeoObjectData geoObjectData = null;
                int parameter = 0;
                int[] status = bBUDforRock.getStatus(repeat_count);
                //このジオは何ジオか決定する
                if (Math.random() < 0.5) {
                    //NormalGeo
                    GEO_PARAM_KIND_NORMAL kindNormal = GeoObjectDataCreater.getRandKindNormal();
                    switch (kindNormal) {
                        case HP:
                            parameter = status[HP.ordinal()] * bBUDforRock.getPower();
                            break;
                        case ATTACK:
                            parameter = status[ATTACK.ordinal()] * bBUDforRock.getPower();
                            break;
                        case DEFENCE:
                            parameter = status[DEFENSE.ordinal()] * bBUDforRock.getPower();
                            break;
                        case LUCK:
                            parameter = status[LUCK.ordinal()] * bBUDforRock.getPower();
                            break;
                    }
                    if (battle_units[i].getHitPoint() < 0) {
                        parameter = (int) ((float) parameter * (float)(battle_units[i].getHitPoint() + battle_units[i].getMaxHitPoint()) / (float)battle_units[i].getMaxHitPoint());
                    }
                    if (parameter > 0) {
                        geoObjectData = GeoObjectDataCreater.getGeoObjectData(parameter, kindNormal);
                    }
                } else {
                    //RateGeo
                    GEO_PARAM_KIND_RATE kindRate = GeoObjectDataCreater.getRandKindRate();
                    switch (kindRate) {
                        case HP_RATE:
                            parameter = status[HP.ordinal()] * bBUDforRock.getPower();
                            break;
                        case ATTACK_RATE:
                            parameter = status[ATTACK.ordinal()] * bBUDforRock.getPower();
                            break;
                        case DEFENCE_RATE:
                            parameter = status[DEFENSE.ordinal()] * bBUDforRock.getPower();
                            break;
                        case LUCK_RATE:
                            parameter = status[LUCK.ordinal()] * bBUDforRock.getPower();
                            break;
                    }
                    if (battle_units[i].getHitPoint() < 0) {
                        parameter = (int) ((float) parameter * (float)(battle_units[i].getHitPoint() + battle_units[i].getMaxHitPoint()) / (float)battle_units[i].getMaxHitPoint());
                    }
                    if (parameter > 0) {
                        geoObjectData = GeoObjectDataCreater.getGeoObjectData(parameter, kindRate);
                    }
                }
                if (geoObjectData != null) {
                    mapPlateAdmin.getInventry().addItemData(geoObjectData);
                    dropItemNames.add(geoObjectData.getName());
                    //(BattleEnemy)battle_units[i].setRock(geoObjectData);
                }
            }
        }
        resultTextBoxUpdateItems(dropItemNames);
    }*/


    //int openingTextBoxID;
    //Paint openingPaint;

    //boolean text_mode;//trueならばテキストを表示中のため、本updateを呼ばない。
    //boolean talkCharaFlag;
    //int count; //現在何番目のテキストを表示しているか。

    //String talkContent[][] = new String[10][];
    //ImageContext talkChara[] = new ImageContext[10];
    //boolean talkFlag[] = new boolean[10];

    /*
    public void openingTextInit() {
        text_mode = false;
        count = 0;
        for (int i = 0; i < talkFlag.length; i++) {
            talkFlag[i] = false;
        }
        openingPaint = new Paint();
        openingTextBoxID = textBoxAdmin.createTextBox(50, 700, 1550, 880, 4);
        textBoxAdmin.setTextBoxUpdateTextByTouching(openingTextBoxID, true);
        textBoxAdmin.setTextBoxExists(openingTextBoxID, false);
        openingPaint.setTextSize(35);
        openingPaint.setARGB(255, 255, 255, 255);
        int i = 0;

        talkContent[i] = new String[2];
        talkChara[i] = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
        talkContent[i][0] = "痛い痛い痛いいたい！";
        talkContent[i][1] = "MOP";
        i++;

        talkContent[i] = new String[2];
        talkChara[i] = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
        talkContent[i][0] = "なんだこのモンスター，すごく強いぞ！";
        talkContent[i][1] = "MOP";
        i++;

        talkContent[i] = new String[2];
        talkChara[i] = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
        talkContent[i][0] = "やばい，どんどんHPバーが削られていく！";
        talkContent[i][1] = "MOP";
        i++;

        talkContent[i] = new String[2];
        talkChara[i] = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
        talkContent[i][0] = "このままじゃ，やられちゃうよ！";
        talkContent[i][1] = "MOP";
        i++;

        talkContent[i] = new String[2];
        talkChara[i] = graphic.makeImageContext(graphic.searchBitmap("主人公立ち絵右向"), 300, 450, 2.0f, 2.0f, 0, 255, false);
        talkContent[i][0] = "うわあああああああ！";
        talkContent[i][1] = "MOP";
        i++;
    }*/

    /*
    public void openingUpdate() {
        //テキスト進行中は本updateを実行しない。

        if (text_mode) {
            if (talkContent[count] != null) {
                talkCharaFlag = false;
                drawCharaAndTouchCheck(talkChara[count]);
                if (count >= 5) {
                    battleEnd();
                }
            }
        }

        if (text_mode == false) {
            if(talkContent[count] != null) {
                if (!talkFlag[count] && ((float)battle_units[0].getHitPoint()/(float)battle_units[0].getMaxHitPoint()) <= talkHpRate[count]) {
                    talk(talkContent[count]);
                    talkFlag[count] = true;
                }
            }
        }
        */
    //}
    /*
    public void talk(String[] talkContent) {

        for(int i = 0; i < talkContent.length; i++){
            textBoxAdmin.bookingDrawText(openingTextBoxID,talkContent[i], openingPaint);
        }
        textBoxAdmin.updateText(openingTextBoxID);
        textBoxAdmin.setTextBoxExists(openingTextBoxID, true);
        text_mode = true;
    }
    public void drawCharaAndTouchCheck(ImageContext _imageContext){
        if (_imageContext != null) {
            talkCharaFlag = true;
        }
        if (battle_user_interface.getTouchState() == Constants.Touch.TouchState.UP) {
            textBoxAdmin.setTextBoxExists(openingTextBoxID, false);
            soundAdmin.play("textenter00");
            text_mode = false;
            count++;
        }
    }
    */


