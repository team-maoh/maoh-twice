package com.maohx2.kmhanko.GeoPresent;

import android.graphics.Paint;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.Money;
import com.maohx2.ina.Arrange.Inventry;

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

    static final String dbName = "GeoPresentDB";
    static final String dbAsset = "GeoPresent.db";

    static final String presentListTableName = "BasePresentList";
    int size;


    //TODO: セーブデータの一部
    int hpScore;
    int attackScore;
    int defenceScore;
    int luckScore;
    int specialScore;
    int sumScore;
    List <PresentGetFlag> presentGetFlags = new ArrayList();

    Inventry geoInventry;

    public GeoPresentManager() {
    }

    public void init(UserInterface _user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, Inventry _geoInventry) {
        userInterface = _user_interface;
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
        loadDatabase(databaseAdmin);
        geoInventry = _geoInventry; //TODO globalから

        //TODO : セーブデータからの読み込み
        initAlreadyGet();

    }

    private void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = database_admin.getMyDatabase(dbName);

        size = database.getSize(presentListTableName);
    }

    //DBに記載された全てのプレゼントについて、それぞれ既にもらったかどうか。
    private void initAlreadyGet() {
        for(int i = 0; i < size; i++) {
            presentGetFlags.add(new PresentGetFlag(
                    database.getStringByRowID(presentListTableName, "present_name", i + 1),
                    false)//TODO 初期は全部もらっていないことになってる
            );
        }
    }

    //プレゼント処理を行った上で、何かのプレゼントに達したかチェック
    public List<ItemData> presentAndCheck(GeoObjectData geoObjectData) {
        presentGeoObject(geoObjectData);
        return getPresentItemData(getNewPresentCheckFlags(getCheckFlag()));
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
    }

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

    private List<ItemData> getPresentItemData(List<Present> newPresent) {
        Present presentBuf;
        String w_script;
        List<ItemData> itemdata = new ArrayList<ItemData>();
        for(int i = 0; i < newPresent.size();i++) {
            presentBuf = newPresent.get(i);
            w_script = database.s_quo("present_name") + " = " + presentBuf.getPresentName();

            if (presentBuf.tableName.equals("GeoObject")) {
                itemdata.add(new GeoObjectData(
                        graphic,
                        database.getOneInt(presentListTableName, "hp", w_script),
                        database.getOneInt(presentListTableName, "attack", w_script),
                        database.getOneInt(presentListTableName, "defence", w_script),
                        database.getOneInt(presentListTableName, "luck", w_script),
                        database.getOneDouble(presentListTableName, "hp_rate", w_script),
                        database.getOneDouble(presentListTableName, "attack_rate", w_script),
                        database.getOneDouble(presentListTableName, "defence_rate", w_script),
                        database.getOneDouble(presentListTableName, "luck_rate", w_script)
                ));
            }
            if (presentBuf.tableName.equals("Money")) {
                itemdata.add(new Money(database.getOneInt(presentListTableName, "money", w_script)));
            }
            if (presentBuf.tableName.equals("ExpendItem")) {
            }
        }
        return itemdata;
    }

    //debug
    private void expressPresent(List<Present> newPresent) {
        for(int i = 0; i < newPresent.size(); i++) {
            System.out.println("present : " + newPresent.get(i).getPresentName() +" " + newPresent.get(i).getTableName());
        }
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