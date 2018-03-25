package com.maohx2.kmhanko.GeoPresent;

import android.graphics.Paint;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.Money;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.plate.BackPlate;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by user on 2017/12/10.
 */

/*
PLUSの値をたくさん貯める
×2とかは特殊パラメータとか言う別のパラメータ

例えば今までのPLUSについて、攻撃が50かつ防御が50溜まったらこれ、など

*/

/*
連続して呼ぶような内容ではないので、
DBファイルから直接取得する処理にしている。
 */

public class GeoPresentManager {

    UserInterface userInterface;
    Graphic graphic;
    MyDatabaseAdmin databaseAdmin;
    TextBoxAdmin textBoxAdmin;
    MyDatabase database;

    int scoreTextBoxID;
    int messageBoxID;

    static final String dbName = "GeoPresentDB";
    static final String dbAsset = "GeoPresent.db";

    static final String presentListTableName = "BasePresentList";
    int size;

    PlateGroup<BackPlate> backPlateGroup;

    GeoObjectData holdGeoObbjectData;

    //TODO: セーブデータの一部
    int hpScore;
    int attackScore;
    int defenceScore;
    int luckScore;
    int specialScore;
    int sumScore;
    List<PresentGetFlag> presentGetFlags = new ArrayList();

    Inventry geoInventry;
    Inventry expendItemInventry;
    ExpendItemDataAdmin expendItemDataAdmin;

    PlateGroup<BoxTextPlate> presentSelectPlateGroup;

    WorldModeAdmin worldModeAdmin;

    PlayerStatus playerStatus;

    public GeoPresentManager(Graphic _graphic, UserInterface _user_interface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, Inventry _geoInventry, Inventry _expendItemInventry, ExpendItemDataAdmin _expendItemDataAdmin, PlayerStatus _playerStatus) {
        userInterface = _user_interface;
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
        loadDatabase(databaseAdmin);
        geoInventry = _geoInventry; //TODO globalから
        expendItemInventry = _expendItemInventry;
        expendItemDataAdmin = _expendItemDataAdmin;
        playerStatus = _playerStatus;
        worldModeAdmin = _worldModeAdmin;

        //TODO : セーブデータからの読み込み

        initAlreadyGet();
        initTextBox();
        initPlateGroup();
    }

    private void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = database_admin.getMyDatabase(dbName);

        size = database.getSize(presentListTableName);
    }

    //DBに記載された全てのプレゼントについて、それぞれ既にもらったかどうか。
    private void initAlreadyGet() {
        List<String> bufPresentName = database.getString(presentListTableName, "present_name");
        for(int i = 0; i < size; i++) {
            presentGetFlags.add(new PresentGetFlag(
                    bufPresentName.get(i),
                    false)//TODO 初期は全部もらっていないことになってる
            );
        }
    }

    private void initTextBox() {
        scoreTextBoxID = textBoxAdmin.createTextBox(50,50,500,600,6);
        textBoxAdmin.setTextBoxUpdateTextByTouching(scoreTextBoxID, false);
        textBoxAdmin.setTextBoxExists(scoreTextBoxID, false);
        scoreTextBoxUpdate();

        messageBoxID = textBoxAdmin.createTextBox(50,650,1000,850,3);
        textBoxAdmin.setTextBoxExists(messageBoxID, false);


        textBoxAdmin.setTextBoxUpdateTextByTouching(messageBoxID, false);
        textBoxAdmin.bookingDrawText(messageBoxID, "ジオオブジェクトちょうだ〜い");
        textBoxAdmin.bookingDrawText(messageBoxID, "MOP");
        textBoxAdmin.updateText(messageBoxID);
    }

    private void initPlateGroup() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(80f);
        textPaint.setARGB(255,255,255,255);
        presentSelectPlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 550, 1550, 650},
                                "献上する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{1100, 700, 1550, 800},
                                "やめる",
                                textPaint
                        )
                }
        );
        presentSelectPlateGroup.setUpdateFlag(false);
        presentSelectPlateGroup.setDrawFlag(false);

        initBackPlate();
    }

    //毎回呼ぶわけではない
    private void scoreTextBoxUpdate() {
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "献上ポイント");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "HP¥t" + hpScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Attack¥t" + attackScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Deffence¥t" + defenceScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Luck¥t" + luckScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Special¥t" + specialScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "MOP");
        textBoxAdmin.updateText(scoreTextBoxID);
    }

    public void geoInventryUpdate() {
        geoInventry.updata();
        InventryData inventryData = userInterface.getInventryData();
        if (inventryData != null) {
            if (inventryData.getItemNum() > 0) {
                //TODO InventryOFF
                holdGeoObbjectData = (GeoObjectData)inventryData.getItemData();
                userInterface.setInventryData(null);
                presentSelectPlateGroup.setUpdateFlag(true);
                presentSelectPlateGroup.setDrawFlag(true);
            }
        }
    }

    //プレゼント処理まとめ
    public void presentAndCheck(GeoObjectData geoObjectData) {
        presentGeoObject(geoObjectData);
        presentToInventry(getNewPresentCheckFlags(getCheckFlag()));
    }

    private void presentGeoObject(GeoObjectData geoObjectData) {
        //TODO:計算が適当なので直す
        hpScore += geoObjectData.getHp();
        attackScore += geoObjectData.getAttack();
        defenceScore += geoObjectData.getDefence();
        luckScore += geoObjectData.getLuck();
        specialScore += (geoObjectData.getHpRate() - 1.0) * 10;
        specialScore += (geoObjectData.getAttackRate() - 1.0) * 10;
        specialScore += (geoObjectData.getDefenceRate() - 1.0) * 10;
        specialScore += (geoObjectData.getLuckRate() - 1.0) * 10;

        sumScore = hpScore + attackScore + defenceScore + luckScore + specialScore;
        scoreTextBoxUpdate();

        geoInventry.subItemData(holdGeoObbjectData);
        holdGeoObbjectData = null;

        textBoxAdmin.bookingDrawText(messageBoxID, "ありがたくいただくとするわ");
        textBoxAdmin.bookingDrawText(messageBoxID, "MOP");
        textBoxAdmin.updateText(messageBoxID);
    }

    //プレゼントリストのDBをチェックして、どのプレゼントの取得条件を満たしているかを返す
    private List<Boolean> getCheckFlag() {
        List<Boolean> checkFlag = new ArrayList<Boolean>(size);

        List<Integer> hp = database.getInt(presentListTableName, "hp");
        List<Integer> attack = database.getInt(presentListTableName, "attack");
        List<Integer> defence = database.getInt(presentListTableName, "defence");
        List<Integer> luck = database.getInt(presentListTableName, "luck");
        List<Integer> special = database.getInt(presentListTableName, "special");
        List<Integer> sum = database.getInt(presentListTableName, "sum");

        for(int i = 0; i < size; i++) {
            if (
                    hpScore >= hp.get(i) &&
                    attackScore >= attack.get(i) &&
                    defenceScore >= defence.get(i) &&
                    luckScore >= luck.get(i) &&
                    specialScore >= special.get(i) &&
                    sumScore >= sum.get(i)
                    ) {
                checkFlag.add(true);
            } else {
                checkFlag.add(false);
            }
        }
        return checkFlag;
    }

    //まだ入手していない且つ入手条件を満たしているPresentのリストを返す
    private List<Present> getNewPresentCheckFlags(List<Boolean> checkFlag) {
        List<Present> newPresentCheckFlags = new ArrayList<Present>();
        String tableName;
        String presentName;
        for (int i = 0; i < checkFlag.size(); i++) {
            if (checkFlag.get(i) && !presentGetFlags.get(i).isAlreadyGet()) {
                //今回初めて条件を満たしたもの
                tableName = database.getStringByRowID(presentListTableName, "table_name", i + 1);
                presentName = database.getStringByRowID(presentListTableName, "present_name", i + 1);
                newPresentCheckFlags.add(new Present(tableName, presentName));
            }
        }
        return newPresentCheckFlags;
    }

    //入手するプレゼント処理
    private void presentToInventry(List<Present> newPresent) {
        Present bufPresent = null;
        String w_script = "";
        String name = "";

        //System.out.println("takano : 貰えるアイテム個数 : " + newPresent.size());
        //List<ItemData> itemdata = new ArrayList<ItemData>();
        for(int i = 0; i < newPresent.size();i++) {
            bufPresent = newPresent.get(i);
            w_script = "name = " + database.s_quo(bufPresent.getPresentName());

            if (bufPresent.getTableName().equals("GeoObject")) {
                GeoObjectData bufGeoObjectData = new GeoObjectData(
                        graphic,
                        database.getOneInt(bufPresent.getTableName(), "hp", w_script),
                        database.getOneInt(bufPresent.getTableName(), "attack", w_script),
                        database.getOneInt(bufPresent.getTableName(), "defence", w_script),
                        database.getOneInt(bufPresent.getTableName(), "luck", w_script),
                        database.getOneDouble(bufPresent.getTableName(), "hp_rate", w_script),
                        database.getOneDouble(bufPresent.getTableName(), "attack_rate", w_script),
                        database.getOneDouble(bufPresent.getTableName(), "defence_rate", w_script),
                        database.getOneDouble(bufPresent.getTableName(), "luck_rate", w_script)
                );
                geoInventry.addItemData(bufGeoObjectData);
                name = bufGeoObjectData.getName();
            }
            if (bufPresent.getTableName().equals("Money")) {
                int bufMoney = database.getOneInt(bufPresent.getTableName(), "price", w_script);
                playerStatus.addMoney(bufMoney);
                name = bufMoney + "G";
            }
            //Expendだけはプレゼント名＝アイテム名
            if (bufPresent.getTableName().equals("ExpendItem")) {
                ItemData bufItemData = expendItemDataAdmin.getOneDataByName(bufPresent.getPresentName());
                expendItemInventry.addItemData(bufItemData);
                name = bufItemData.getName();
            }
            presentGetMessage(name);
            //System.out.println("takano : 献上により獲得 : " + name);

            //TODO セーブデータへの書き込み
            //仮
            for (int j = 0; j < presentGetFlags.size(); j++) {
                if (presentGetFlags.get(j).getPresentName().equals(bufPresent.getPresentName())) {
                    presentGetFlags.get(j).setAlreadyGet(true);
                }
            }
        }
        return;
    }

    //TODO 藤原依頼 TextBoxが最後に到達してるかのフラグ
    private void presentGetMessage(String name) {
        textBoxAdmin.setTextBoxUpdateTextByTouching(messageBoxID, true);
        textBoxAdmin.bookingDrawText(messageBoxID, "献上ポイントが上がったから");
        textBoxAdmin.bookingDrawText(messageBoxID, "\n");
        textBoxAdmin.bookingDrawText(messageBoxID, name + " をあげる！大事に使いなさい");
        textBoxAdmin.bookingDrawText(messageBoxID, "MOP");
    }

    public void presentSelectUpdate() {
        presentSelectPlateGroup.update();
        if (presentSelectPlateGroup.getUpdateFlag()) {
            int content = presentSelectPlateGroup.getTouchContentNum();
            switch(content) {
                case(0) ://献上する
                    presentAndCheck(holdGeoObbjectData);
                    holdGeoObbjectData = null;
                    scoreTextBoxUpdate();
                    presentSelectPlateGroup.setUpdateFlag(false);
                    presentSelectPlateGroup.setDrawFlag(false);
                    break;
                case(1) ://キャンセル
                    holdGeoObbjectData = null;
                    presentSelectPlateGroup.setUpdateFlag(false);
                    presentSelectPlateGroup.setDrawFlag(false);
                    break;
            }
        }
    }

    //debug
    private void expressPresent(List<Present> newPresent) {
        for(int i = 0; i < newPresent.size(); i++) {
            System.out.println("present : " + newPresent.get(i).getPresentName() +" " + newPresent.get(i).getTableName());
        }
    }

    public void update() {
        geoInventryUpdate();
        presentSelectUpdate();
        backPlateGroup.update();

        //TODO TextBoxの一括ではないupdate
        textBoxAdmin.setTextBoxExists(scoreTextBoxID, worldModeAdmin.getIsDraw(worldModeAdmin.getPresent()));
        textBoxAdmin.setTextBoxExists(messageBoxID, worldModeAdmin.getIsDraw(worldModeAdmin.getPresent()));
    }

    public void draw() {
        geoInventry.draw();
        presentSelectPlateGroup.draw();
        backPlateGroup.draw();
    }

    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[] {
                        new BackPlate(
                                graphic, userInterface, worldModeAdmin
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                presentSelectPlateGroup.setUpdateFlag(false);
                                presentSelectPlateGroup.setDrawFlag(false);
                                holdGeoObbjectData = null;
                                worldModeAdmin.setPresent(Constants.Mode.ACTIVATE.STOP);
                                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.ACTIVE);
                            }
                        }
                }
        );
        backPlateGroup.setUpdateFlag(true);
        backPlateGroup.setDrawFlag(true);
    }


}

class Present {
    String tableName;
    String presentName;

    public Present() {
    }

    public Present(String _tableName, String _presentName) {
        tableName = _tableName;
        presentName = _presentName;
    }

    public void clear() {
        tableName = null;
        presentName = null;
    }

    public void setTableName(String _tableName) {
        tableName = _tableName;
    }
    public void setPresentName(String _presentName) {
        presentName = _presentName;
    }
    public String getTableName() {
        return tableName;
    }
    public String getPresentName() {
        return presentName;
    }

}

class PresentGetFlag {
    String presentName;
    boolean alreadyGet;

    public PresentGetFlag() {
        presentName = null;
        alreadyGet = false;
    }

    public PresentGetFlag(String _presentName, boolean _alreadyGet) {
        presentName = _presentName;
        alreadyGet = _alreadyGet;
    }

    public void setPresentName(String _presentName) {
        presentName = _presentName;
    }
    public void setAlreadyGet(boolean _alreadyGet) {
        alreadyGet = _alreadyGet;
    }
    public String getPresentName() {
        return presentName;
    }
    public boolean isAlreadyGet() {
        return alreadyGet;
    }
}