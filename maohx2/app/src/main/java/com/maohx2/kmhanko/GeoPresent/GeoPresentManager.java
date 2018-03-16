package com.maohx2.kmhanko.GeoPresent;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.Money;

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

    static final String presentListName = "BasePresentList";
    int size;


    //TODO: セーブデータの一部
    int hpScore;
    int attackScore;
    int defenceScore;
    int luckScore;
    int specialScore;
    int sumScore;
    List<Boolean> alreadyGet = new ArrayList<Boolean>();

    public GeoPresentManager() {
    }

    public void init(UserInterface _user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin) {
        userInterface = _user_interface;
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
        loadDatabase(databaseAdmin);

        //TODO : セーブデータからの読み込み
        initAlreadyGet();
    }

    private void initAlreadyGet() {
        for(int i = 0; i < size; i++) {
            alreadyGet.add(false);
        }
    }

    public void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = database_admin.getMyDatabase(dbName);

        size = database.getSize(presentListName);
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

    public List<ItemData> presentAndCheck(GeoObjectData geoObjectData) {
        presentGeoObject(geoObjectData);
        return getPresentItemData(getNewPresentCheckFlags(getCheckFlag()));
    }

    private List<Boolean> getCheckFlag() {
        List<Boolean> checkFlag = new ArrayList<Boolean>(size);

        List<Integer> hp = database.getInt(presentListName, "hp");
        List<Integer> attack = database.getInt(presentListName, "attack");
        List<Integer> defence = database.getInt(presentListName, "defence");
        List<Integer> luck = database.getInt(presentListName, "luck");
        List<Integer> special = database.getInt(presentListName, "special");
        List<Integer> sum = database.getInt(presentListName, "sum");

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
            if (checkFlag.get(i) && !alreadyGet.get(i)) {
                //今回初めて条件を満たしたもの
                tableName = database.getStringByRowID(presentListName, "table_name", i + 1);
                presentName = database.getStringByRowID(presentListName, "present_name", i + 1);
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
                        database.getOneInt(presentListName, "hp", w_script),
                        database.getOneInt(presentListName, "attack", w_script),
                        database.getOneInt(presentListName, "defence", w_script),
                        database.getOneInt(presentListName, "luck", w_script),
                        database.getOneDouble(presentListName, "hp_rate", w_script),
                        database.getOneDouble(presentListName, "attack_rate", w_script),
                        database.getOneDouble(presentListName, "defence_rate", w_script),
                        database.getOneDouble(presentListName, "luck_rate", w_script)
                ));
            }
            if (presentBuf.tableName.equals("Money")) {
                itemdata.add(new Money(database.getOneInt(presentListName, "money", w_script)));
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