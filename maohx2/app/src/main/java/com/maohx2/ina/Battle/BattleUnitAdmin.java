package com.maohx2.ina.Battle;

import android.app.Activity;
import android.graphics.Paint;

import com.maohx2.fuusya.MapPlateAdmin;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Constants;
import static com.maohx2.ina.Constants.Item.EQUIPMENT_KIND;
import static com.maohx2.ina.Constants.Item.ITEM_KIND;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonModeManage;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.ItemData.EquipmentItemBaseData;
import com.maohx2.ina.ItemData.EquipmentItemBaseDataAdmin;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataCreater;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;

import com.maohx2.ina.ItemData.ItemData;

import static com.maohx2.ina.Constants.BattleUnit.BATTLE_UNIT_MAX;
import static com.maohx2.ina.Constants.Touch.TouchState;

import com.maohx2.ina.Battle.*;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ina on 2017/09/21.
 */

//BattleUnitを一括管理するクラス
public class BattleUnitAdmin {

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
    int count = 0;
    Paint paint = new Paint();
    MyDatabaseAdmin databaseAdmin;

    ExpendItemDataAdmin expendItemDataAdmin;
    EquipmentItemDataCreater equipmentItemDataCreater;

    MapPlateAdmin mapPlateAdmin;

    Graphic graphic;
    PaletteAdmin palette_admin;
    boolean marker_flag;
    boolean first_attack_frag;
    int attack_count;

    //by kmhanko BattleUnitDataAdmin追加
    public void init(Graphic _graphic, BattleUserInterface _battle_user_interface, Activity _battle_activity, BattleUnitDataAdmin _battleUnitDataAdmin, PlayerStatus _playerStatus, PaletteAdmin _palette_admin, DungeonModeManage _dungeonModeManage, MyDatabaseAdmin _databaseAdmin, MapPlateAdmin _map_plate_admin) {
        //引数の代入
        graphic = _graphic;
        battle_user_interface = _battle_user_interface;
        battle_activity = _battle_activity;
        battleUnitDataAdmin = _battleUnitDataAdmin;
        palette_admin = _palette_admin;

        dungeonModeManage = _dungeonModeManage;
        databaseAdmin = _databaseAdmin;
        mapPlateAdmin = _map_plate_admin;

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
        /*
        for (int i = 0; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].init();
        }
        */
        for (int i = 0; i < MAKER_NUM; i++) {
            touch_markers[i] = new TouchMarker(graphic);
        }

        //CalcUnitStatusのインスタンス化・初期化
        calc_unit_status = new CalcUnitStatus();
        calc_unit_status.init();


        /*
        //削除 仮に敵を適当な位置に配置する setBattleUnitDataに入れた
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].setPositionX(200 + 200 * (i - 1));
            battle_units[i].setPositionY(300);
            battle_units[i].setRadius(50);
        }

        //削除 仮に敵にUIのタッチIDを割り振る setBattleUnitDataに入れた
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].setUIID(battle_user_interface.setCircleTouchUI(battle_units[i].getPositionX(), battle_units[i].getPositionY(), battle_units[i].getRadius()));
        }
        */

        //TODO 仮。プレイヤーデータのコンバート
        setPlayer(_playerStatus);

        //TODO 仮。敵の生成。本当はここじゃなくて戦闘画面に移動する画面エフェクト的な奴を処理した後とかに呼ぶとかする
        //spawnEnemy();
    }

    //by kmhanko
    //BattleUnitDataAdminを使用し、敵情報をBattleUnit配列にセットする関数 enemy(i >=1 )にしか対応していない点に注意
    public int setBattleUnitData(String enemyName, int repeatCount) {
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (!battle_units[i].isExist()) {
                BattleBaseUnitData tempBattleBaseUnitData = battleUnitDataAdmin.getBattleUnitDataNum(enemyName);
                battle_units[i].setBattleUnitData(tempBattleBaseUnitData, repeatCount, i);//TODO: iはそのうち消す
                return i;
            }
        }
        return -1;
    }

    public void setPlayer(PlayerStatus playerStatus) {
        //TODO
        playerStatus.calcStatus();
        //battle_units[0].setBattleUnitData(playerStatus.makeBattleDungeonUnitData());
    }

    public void spawnEnemy() {
        //TODO 仮。本当はダンジョンのデータなどを引数にして出現する敵をランダムなどで決定する

        //一覧
        //setBattleUnitData("e01-0", 1);
        //setBattleUnitData("e88-0", 1);
        //setBattleUnitData("e74-0", 1);
        setBattleUnitData("m003-2", 1);
        setBattleUnitData("e96-0", 1);
        //setBattleUnitData("e27", 1);
        //setBattleUnitData("m007", 1);
        //setBattleUnitData("e103-0", 1);
        //setBattleUnitData("e94-3", 1);
        //setBattleUnitData("e83-1", 1);
    }


    public void update() {

        double touch_x = battle_user_interface.getTouchX();
        double touch_y = battle_user_interface.getTouchY();
        TouchState touch_state = battle_user_interface.getTouchState();

        attack_count++;

        if(marker_flag == false) {
            palette_admin.update(true);
        }

        if(palette_admin.doUsePalette() == false) {
            //プレイヤーの攻撃によるマーカーの設置
            if ((touch_state == TouchState.DOWN) || (touch_state == TouchState.DOWN_MOVE) || (touch_state == TouchState.MOVE)) {
                EquipmentItemData attack_equipment = palette_admin.getEquipmentItemData();
                System.out.println("first_attack_flag:   " + first_attack_frag);
                if(attack_equipment != null) {
                    if ((first_attack_frag == false && attack_count >= attack_equipment.getTouchFrequency()) || (first_attack_frag == true && attack_count >= attack_equipment.getTouchFrequency() * attack_equipment.getAutoFrequencyRate())) {
                        first_attack_frag = true;
                        marker_flag = true;
                        attack_count = 0;
                        for (int i = 0; i < MAKER_NUM; i++) {
                            if (touch_markers[i].isExist() == false) {
                                //todo:attackの計算
                                touch_markers[i].generate((int) touch_x, (int) touch_y, palette_admin.getEquipmentItemData().getRadius(), battle_units[0].getAttack() + palette_admin.getEquipmentItemData().getAttack(), palette_admin.getEquipmentItemData().getDecayRate());
                                break;
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
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isExist() == true) {
                int ex = (int)battle_units[i].getPositionX();
                int ey = (int)battle_units[i].getPositionY();
                int er = (int)battle_units[i].getRadius();
                for(int j = 0; j< MAKER_NUM; j++) {
                    if (touch_markers[j].isExist() == true) {
                        int cx = touch_markers[j].getPositionX();
                        int cy = touch_markers[j].getPositionY();
                        int cr = touch_markers[j].getRadius();

                        if((ex-cx)*(ex-cx)+(ey-cy)*(ey-cy)<(er-cr)*(er-cr)){
                            int new_hp = battle_units[i].getHitPoint() - touch_markers[j].getDamage();
                            if(new_hp > 0) {
                                battle_units[i].setHitPoint(new_hp);
                            }else{
                                battle_units[i].existIs(false);
                            }
                        }
                    }
                }
            }
        }


        //敵の更新と攻撃処理
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isExist() == true ) {
                int damage_to_player = battle_units[i].update();
                if(damage_to_player > 0) {
                    EquipmentItemData defense_equipment = palette_admin.getEquipmentItemData();
                    float damage_rate = 1;
                    if (defense_equipment != null) {
                        damage_rate = (1 - (float) defense_equipment.getDefence() / 100.0f);
                    }
                    int new_hp = battle_units[0].getHitPoint() - (int) (damage_to_player * damage_rate);
                    if (new_hp <= 0) {
                        //ゲームオーバー
                    }
                    battle_units[0].setHitPoint(new_hp);
                }
            }
        }

        boolean result_flag = true;
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            result_flag = result_flag && !battle_units[i].isExist();
        }

        if(result_flag == true){
            //戦闘が終了した時
            // by kmhanko
            getDropItem();

            dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.MAP);
        }
    }





    public void draw() {


        for(int i = 0; i < MAKER_NUM; i++) {
            if(touch_markers[i].isExist() == true ) {
                touch_markers[i].draw();
            }
        }

        for (int i = 0; i < BATTLE_UNIT_MAX; i++) {

            if (battle_units[i].isExist() == true ) {
                battle_units[i].draw();
            }
        }

        //デバッグ用 プレイヤーのステータスを表示
        //debugPlayerStatusDraw();
        palette_admin.draw();

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

    private void getDropItem() {
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

                        if (dropItemRate[j] > tempRand) { //ドロップ確率
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
                        System.out.println("☆タカノ:BattleUnitAdmin#getDropItem : アイテムを取得 : " + dropItemName[j] + " from " + battle_units[i].getName());
                    }
                }
            }
        }
    }

    public BattleUnitAdmin() {}
    

}
