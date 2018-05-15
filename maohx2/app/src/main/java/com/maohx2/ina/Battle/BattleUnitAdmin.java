package com.maohx2.ina.Battle;

import android.app.Activity;
import android.graphics.Paint;

import com.maohx2.fuusya.MapPlateAdmin;
import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Constants;

import static com.maohx2.ina.Constants.Item.EQUIPMENT_KIND;
import static com.maohx2.ina.Constants.Item.ITEM_KIND;

import static com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_NORMAL;
import static com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_RATE;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonModeManage;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.ItemData.EquipmentItemBaseData;
import com.maohx2.ina.ItemData.EquipmentItemBaseDataAdmin;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataCreater;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.kmhanko.MaohMenosStatus.MaohMenosStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;

import com.maohx2.ina.ItemData.ItemData;

import static com.maohx2.ina.Constants.BattleUnit.BATTLE_UNIT_MAX;
import static com.maohx2.ina.Constants.Touch.TouchState;
import static com.maohx2.ina.Constants.UnitStatus.Status.ATTACK;
import static com.maohx2.ina.Constants.UnitStatus.Status.DEFENSE;
import static com.maohx2.ina.Constants.UnitStatus.Status.HP;
import static com.maohx2.ina.Constants.UnitStatus.Status.LUCK;

import com.maohx2.ina.Battle.*;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;

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

    PlayerStatus playerStatus;
    MaohMenosStatus maohMenosStatus;

    boolean resultOperatedFlag;//リザルト関係の処理を一度だけ呼ぶためのフラグ
    boolean battleEndFlag;//戦闘が終わったかどうか

    //by kmhanko BattleUnitDataAdmin追加
    public void init(Graphic _graphic, BattleUserInterface _battle_user_interface, Activity _battle_activity, BattleUnitDataAdmin _battleUnitDataAdmin, PlayerStatus _playerStatus, PaletteAdmin _palette_admin, DungeonModeManage _dungeonModeManage, MyDatabaseAdmin _databaseAdmin, MapPlateAdmin _map_plate_admin, TextBoxAdmin _textBoxAdmin, int _repeat_count, MaohMenosStatus _maohMenosStatus) {
        //引数の代入
        graphic = _graphic;
        BattleRockCreater.setGraphic(graphic);
        battle_user_interface = _battle_user_interface;
        battle_activity = _battle_activity;
        battleUnitDataAdmin = _battleUnitDataAdmin;
        palette_admin = _palette_admin;
        maohMenosStatus = _maohMenosStatus;

        dungeonModeManage = _dungeonModeManage;
        databaseAdmin = _databaseAdmin;
        mapPlateAdmin = _map_plate_admin;

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

        //TODO タッチマーカー残骸を消す

        if (mode == MODE.BATTLE || mode == MODE.MAOH || mode == MODE.BOSS || mode == MODE.OPENING) {
            palette_admin.setPalettesFlags(new boolean[]{true, true, false});
            //spawnEnemy();
        }

        if (mode == MODE.MINING) {
            palette_admin.setPalettesFlags(new boolean[]{false, false, true});
            spawnRock();
            timeLimitBar.reset(30 * 60);
        }
    }

    //by kmhanko
    //BattleUnitDataAdminを使用し、敵情報をBattleUnit配列にセットする関数 enemy(i >=1 )にしか対応していない点に注意
    private int setBattleUnitData(String enemyName, int repeatCount) {
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (!battle_units[i].isExist()) {
                BattleBaseUnitData tempBattleBaseUnitData = battleUnitDataAdmin.getBattleUnitDataNum(enemyName);
                battle_units[i].setBattleUnitDataEnemy(tempBattleBaseUnitData, repeatCount);
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

                    battle_units[i].setDefence(battle_units[i].getDefence()- maohMenosStatus.getGeoDefence());
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
        battle_units[0].setBattleUnitDataPlayer(playerStatus.makeBattleDungeonUnitData());//TODO なぜかコメントアウトされてた
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

        //岩に対応する敵を決定し、その対応する敵データからHPを決める
        for (int i = 0; i < 6; i++) {
            setRockUnitData(battleUnitDataAdmin.getRandomBattleBaseUnitData());
        }
    }

    public void spawnBoss(String[] monsters) {
        for (int i = 0; i < monsters.length; i++) {
            setBattleUnitData(monsters[i], repeat_count);
        }
    }

    public int setRockUnitData(BattleBaseUnitData bBUD) {
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (!battle_units[i].isExist()) {
                BattleBaseUnitData tempBBUD = BattleRockCreater.getBattleBaseUnitData(bBUD.getDbStatus(BattleBaseUnitData.DbStatusID.InitialHP) * bBUD.getPower(), 0);
                battle_units[i].setBattleUnitDataRock(tempBBUD);
                ((BattleEnemy) battle_units[i]).setBattleBaseUnitDataForRock(bBUD);
                return i;
            }
        }
        return -1;
    }


    public void update() {

        double touch_x = battle_user_interface.getTouchX();
        double touch_y = battle_user_interface.getTouchY();
        TouchState touch_state = battle_user_interface.getTouchState();

        if (!battleEndFlag) {

            battle_units[0].update();

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
                            if ((ex - cx) * (ex - cx) + (ey - cy) * (ey - cy) < (er - cr) * (er - cr)) {
                                markHitFlag = true;
                                if (((BattleEnemy) (battle_units[i])).isSpecialAction() == false) {
                                    //敵が特殊行動していないなら

                                    int new_hp = battle_units[i].getHitPoint() - touch_markers[j].getDamage();
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
                                        int new_hp = battle_units[0].getHitPoint() - touch_markers[j].getDamage();
                                        if (new_hp > 0) {
                                            battle_units[0].setHitPoint(new_hp);
                                        } else {
                                            battle_units[0].existIs(false);
                                        }
                                    } else if (((BattleEnemy) (battle_units[i])).getSpecialAction() == BattleBaseUnitData.SpecialAction.STEALTH) {
                                        int new_hp = battle_units[i].getHitPoint() - touch_markers[j].getDamage();
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

                        int heel_to_player = 0;
                        if (palette_admin.checkSelectedExpendItemData() != null) {
                            heel_to_player = battle_units[0].getHitPoint() * palette_admin.checkSelectedExpendItemData().getHp();
                            palette_admin.deleteExpendItemData();
                        }

                        int new_hp = battle_units[0].getHitPoint() - (int) (damage_to_player * damage_rate) + heel_to_player;
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
                        }

                        if (new_hp <= 0) {
                            //負けたとき
                            gameOver();
                            resultOperatedFlag = true;
                            battleEndFlag = true;
                            new_hp = 0;
                        }
                        battle_units[0].setHitPoint(new_hp);
                    }
                }
            }
        }

        boolean result_flag = true;
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            result_flag = result_flag && !battle_units[i].isExist();
        }

        if ( battle_units[0].getHitPoint() > 0 && ((result_flag == true || (timeLimitBar.isTimeUp() && timeLimitBar.isExist())) && !resultOperatedFlag)) {
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
        switch(mode) {
            case BATTLE:
                battleEnd();
                break;
            case BOSS:
                battleEnd();
                break;
            case MINING:
                battleEnd();
                break;
            case MAOH:
                battleEnd();
                break;
            case OPENING:
                resultTextBoxUpdate(new String[]{"うわあああああああああ！",});
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                break;
        }
    }

    public void win() {
        winFlag = true;
        switch(mode) {
            case BATTLE:
            case BOSS:
                getDropItem();
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                break;
            case MINING:
                if (timeLimitBar.isTimeUp()) {
                    //TODO 時間切れで終了した場合
                    resultTextBoxUpdate(new String[]{"時間切れになってしまった！", "今回の獲得ジオはありません"});
                } else {
                    //ジオを掘り尽くした場合
                    getDropGeo();
                }
                resultButtonGroup.setUpdateFlag(true);
                resultButtonGroup.setDrawFlag(true);
                break;
            case MAOH:
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

        //text
        resultButtonGroup.draw();

    }

    //by kmhanko
    Paint playerStatusPaint = new Paint();

    public void debugPlayerStatusDraw() {
        //playerStatusPaint.setTextSize(50);
        //graphic.bookingDrawText(battle_units[0].getName(), 0, 50, playerStatusPaint);
        //graphic.bookingDrawText(battle_units[0].getHitPoint() + " / " + battle_units[0].getMaxHitPoint(), 0, 100, playerStatusPaint);
        //graphic.bookingDrawText(""+ battle_units[0].getAttack(), 0, 150, playerStatusPaint);
        //graphic.bookingDrawText(""+battle_units[0].getDefence(), 0, 200, playerStatusPaint);
        //graphic.bookingDrawText(""+battle_units[0].getLuck(), 0, 250, playerStatusPaint);
    }

    //by kmhanko
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
                }
            }
        }
        resultTextBoxUpdateItems(dropItemNames);
    }


    //by kmhanko
    private void getDropItem() {
        List<String> dropItemNames = new ArrayList<String>();
        BattleBaseUnitData tempBattleBaseUnitData = null;
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isDropFlag()) {
                battle_units[i].dropFlagIs(false);
                tempBattleBaseUnitData = battleUnitDataAdmin.getBattleUnitDataNum(battle_units[i].getName());

                EQUIPMENT_KIND[] dropItemEquipmentKind = tempBattleBaseUnitData.getDropItemEquipmentKinds();
                String[] dropItemName = tempBattleBaseUnitData.getDropItemNames();
                double[] dropItemRate = tempBattleBaseUnitData.getDropItemRate();
                ITEM_KIND[] dropItemKind = tempBattleBaseUnitData.getDropItemKinds();
                ItemData tempItemData = null;
                double tempRand;
                for (int j = 0; j < Constants.Item.DROP_NUM; j++) {
                    if (dropItemName[j] != null) {
                        tempRand = Math.random();
                        System.out.println("☆タカノ:BattleUnitAdmin#getDropItem : アイテムドロップ率計算 : " + dropItemName[j] + " from " + battle_units[i].getName() + " : " + dropItemRate[j] + " ? " + tempRand);

                        if (true) {//if (dropItemRate[j] > tempRand) { //ドロップ確率
                            switch (dropItemKind[j]) {
                                case EQUIPMENT:
                                    tempItemData = equipmentItemDataCreater.getEquipmentItemData(dropItemEquipmentKind[j], battle_units[i].getBattleDungeonUnitData());
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
                        dropItemNames.add(tempItemData.getName());

                        System.out.println("☆タカノ:BattleUnitAdmin#getDropItem : アイテムを取得 : " + dropItemName[j] + " from " + battle_units[i].getName());
                    }
                }
            }
        }

        // result関係
        resultTextBoxUpdateItems(dropItemNames);
    }

    public BattleUnitAdmin() {
    }


    // *** リザルトメッセージ関係 ***

    int resultTextBoxID;
    Paint resultTextPaint;
    TextBoxAdmin textBoxAdmin;
    PlateGroup resultButtonGroup;

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

    private void resultTextBoxUpdateItems(List<String> itemNames) {


        textBoxAdmin.setTextBoxExists(resultTextBoxID, true);

        String winMessage = "▽入手アイテム▽";

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

        resultButtonGroup = new PlateGroup<BoxTextPlate>(new BoxTextPlate[]{new BoxTextPlate(graphic, battle_user_interface, new Paint(), Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{POPUP_WINDOW.OK_LEFT, POPUP_WINDOW.OK_UP, POPUP_WINDOW.OK_RIGHT, POPUP_WINDOW.OK_BOTTOM}, "OK", textPaint)});
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
        if (winFlag) {
            switch(mode) {
                case BATTLE:
                case BOSS:
                case MINING:
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
                    break;
                case MAOH:
                    playerStatus.addMaohWinCount();
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.TO_WORLD);
                    break;
                case OPENING:
                    dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.TO_WORLD);
                    break;
            }
        } else {
            switch(mode) {
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
    }

    // *** リザルトメッセージ関係ここまで ***

}
