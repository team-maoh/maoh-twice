package com.maohx2.kmhanko.GeoPresent;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.Saver.GeoPresentSaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.dungeonselect.MapIconPlate;
import com.maohx2.kmhanko.itemdata.ExpendItemDataAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataCreater;
import com.maohx2.kmhanko.itemdata.Money;
import com.maohx2.kmhanko.Arrange.InventryS;
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

    Paint presentTextPaint;
    int presentTextBoxID;

    static final String dbName = "GeoPresentDB";
    static final String dbAsset = "GeoPresent.db";

    static final String presentListTableName = "BasePresentList";
    int size;

    PlateGroup<BackPlate> backPlateGroup;

    GeoObjectData holdGeoObbjectData;

    int hpScore;
    int attackScore;
    int defenceScore;
    int luckScore;
    int specialScore;
    int sumScore;

    List<Boolean> alreadyPresentGetFlags = new ArrayList<>();

    InventryS geoInventry;
    InventryS expendItemInventry;
    ExpendItemDataAdmin expendItemDataAdmin;

    PlateGroup<BoxTextPlate> presentSelectPlateGroup;

    WorldModeAdmin worldModeAdmin;

    PlayerStatus playerStatus;

    GeoPresentSaver geoPresentSaver;

    public GeoPresentManager(Graphic _graphic, UserInterface _user_interface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, InventryS _geoInventry, InventryS _expendItemInventry, ExpendItemDataAdmin _expendItemDataAdmin, PlayerStatus _playerStatus) {
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

        initTextBox();
        initPlateGroup();
        initPresentTextBox();
        initUIs();

    }

    private void initPresentTextBox() {
        presentTextBoxID = textBoxAdmin.createTextBox(SELECT_WINDOW.MESS_LEFT, SELECT_WINDOW.MESS_UP, SELECT_WINDOW.MESS_RIGHT, SELECT_WINDOW.MESS_BOTTOM, Constants.SELECT_WINDOW.MESS_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(presentTextBoxID, false);
        textBoxAdmin.setTextBoxExists(presentTextBoxID, false);
        presentTextPaint = new Paint();
        presentTextPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        presentTextPaint.setColor(Color.WHITE);
    }

    public void setGeoPresentSaver(GeoPresentSaver _geoPresentSaver) {
        geoPresentSaver = _geoPresentSaver;
        geoPresentSaver.load();
        scoreTextBoxUpdate();
    }

    private void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = database_admin.getMyDatabase(dbName);

        size = database.getSize(presentListTableName);
    }

    private void initTextBox() {
        scoreTextBoxID = textBoxAdmin.createTextBox(0,0,300,500,6);
        textBoxAdmin.setTextBoxUpdateTextByTouching(scoreTextBoxID, false);
        textBoxAdmin.setTextBoxExists(scoreTextBoxID, false);
        scoreTextBoxUpdate();

        messageBoxID = textBoxAdmin.createTextBox(0,600,1000,900,3);
        textBoxAdmin.setTextBoxExists(messageBoxID, false);


        textBoxAdmin.setTextBoxUpdateTextByTouching(messageBoxID, false);
        textBoxAdmin.bookingDrawText(messageBoxID, "ジオオブジェクトちょうだ〜い");
        textBoxAdmin.bookingDrawText(messageBoxID, "MOP");
        textBoxAdmin.updateText(messageBoxID);
    }

    private void initPlateGroup() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW.TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);
        presentSelectPlateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "献上する",
                                textPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.NO_LEFT, SELECT_WINDOW.NO_UP, SELECT_WINDOW.NO_RIGHT, SELECT_WINDOW.NO_BOTTOM},
                                "やめる",
                                textPaint
                        )
                }
        );
        presentSelectPlateGroup.setUpdateFlag(false);
        presentSelectPlateGroup.setDrawFlag(false);

        initBackPlate();
    }


    private void scoreTextBoxUpdate() {
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "献上ポイント");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "HP " + hpScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Attack " + attackScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Deffence " + defenceScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Luck " + luckScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Special " + specialScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "MOP");
        textBoxAdmin.updateText(scoreTextBoxID);
    }

    public void geoInventryUpdate() {
        geoInventry.updata();
        InventryData inventryData = userInterface.getInventryData();
        if (inventryData != null) {
            if (inventryData.getItemNum() > 0) {
                GeoObjectData tmp = (GeoObjectData)inventryData.getItemData();
                if (inventryData.getItemNum() > 0 && tmp.getName() != null && tmp.getSlotSetName().equals("noSet")) {
                    holdGeoObbjectData = (GeoObjectData) inventryData.getItemData();
                    userInterface.setInventryData(null);
                    presentSelectPlateGroup.setUpdateFlag(true);
                    presentSelectPlateGroup.setDrawFlag(true);
                    presentTextBoxUpdate();
                }
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

        geoInventry.deleteItemData(holdGeoObbjectData.getName());
        holdGeoObbjectData = null;

        textBoxAdmin.bookingDrawText(messageBoxID, "ありがたくいただくとするわ");
        textBoxAdmin.bookingDrawText(messageBoxID, "MOP");
        textBoxAdmin.updateText(messageBoxID);
    }

    //プレゼントリストのDBをチェックして、どのプレゼントの取得条件を満たしているかを返す
    private List<Boolean> getCheckFlag() {
        List<Boolean> checkFlag = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            checkFlag.add(false);
        }

        List<Integer> id = database.getInt(presentListTableName, "id");
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
                    checkFlag.set(id.get(i) - 1, true);
            }
        }
        return checkFlag;
    }

    //まだ入手していない且つ入手条件を満たしているPresentのリストを返す
    private List<Integer> getNewPresentCheckFlags(List<Boolean> checkFlag) {
        List<Integer> newPresentNumber = new ArrayList<Integer>();
        String tableName;
        String presentName;
        for (int i = 0; i < checkFlag.size(); i++) {
            if (checkFlag.get(i) && !alreadyPresentGetFlags.get(i)) {
                //今回初めて条件を満たしたもの
                //tableName = database.getStringByRowID(presentListTableName, "table_name", i);
                //presentName = database.getStringByRowID(presentListTableName, "present_name", i);
                newPresentNumber.add(i + 1);
            }
        }
        return newPresentNumber;
    }

    //入手するプレゼント処理
    private void presentToInventry(List<Integer> newPresentNumber) {
        //Present bufPresent = null;
        String w_script = "";
        String name = "";

        //System.out.println("takano : 貰えるアイテム個数 : " + newPresent.size());
        //List<ItemData> itemdata = new ArrayList<ItemData>();

        String tableName;
        String presentName;

        for(int i = 0; i < newPresentNumber.size();i++) {
            w_script = "id = " + newPresentNumber.get(i);
            tableName = database.getOneString(presentListTableName, "table_name", w_script);
            presentName = database.getOneString(presentListTableName, "present_name", w_script);

            if (tableName.equals("GeoObject")) {
                w_script = "name = " + MyDatabase.s_quo(presentName);

                /*
                GeoObjectData bufGeoObjectData = new GeoObjectData(
                        MyDatabase.s_quo(presentName),
                       graphic.searchBitmap("DefenceGeo01"),//TODO ちゃんと検索。直す
                        database.getOneInt(tableName, "hp", w_script),
                        database.getOneInt(tableName, "attack", w_script),
                        database.getOneInt(tableName, "defence", w_script),
                        database.getOneInt(tableName, "luck", w_script),
                        database.getOneDouble(tableName, "hp_rate", w_script),
                        database.getOneDouble(tableName, "attack_rate", w_script),
                        database.getOneDouble(tableName, "defence_rate", w_script),
                        database.getOneDouble(tableName, "luck_rate", w_script)
                );
                */

                GeoObjectData bufGeoObjectData = GeoObjectDataCreater.getGeoObjectData(
                        new int[] {
                                database.getOneInt(tableName, "hp", w_script),
                                database.getOneInt(tableName, "attack", w_script),
                                database.getOneInt(tableName, "defence", w_script),
                                database.getOneInt(tableName, "luck", w_script),
                        },
                        new double[] {
                                database.getOneDouble(tableName, "hp_rate", w_script),
                                database.getOneDouble(tableName, "attack_rate", w_script),
                                database.getOneDouble(tableName, "defence_rate", w_script),
                                database.getOneDouble(tableName, "luck_rate", w_script)
                        }
                );

                geoInventry.addItemData(bufGeoObjectData);
                name = bufGeoObjectData.getName();
            }
            if (tableName.equals("Money")) {
                w_script = "name = " + MyDatabase.s_quo(presentName);
                int bufMoney = database.getOneInt(tableName, "price", w_script);
                playerStatus.addMoney(bufMoney);
                name = bufMoney + "G";
            }
            //Expendだけはプレゼント名＝アイテム名
            if (tableName.equals("ExpendItem")) {
                w_script = null;
                ItemData bufItemData = expendItemDataAdmin.getOneDataByName(presentName);
                expendItemInventry.addItemData(bufItemData);
                name = bufItemData.getName();
            }
            presentGetMessage(name);
            System.out.println("takano:GeoPresentManager#presentToInventry : 献上により獲得 : " + name);

            //セーブのために格納
            alreadyPresentGetFlags.set(newPresentNumber.get(i) - 1, true);
        }
        //セーブ
        geoPresentSaver.save();
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
                    initUIs();
                    break;
                case(1) ://キャンセル
                    holdGeoObbjectData = null;
                    initUIs();
                    break;
            }
        }
    }

    public void update() {
        geoInventryUpdate();
        presentSelectUpdate();
        scoreTextBoxUpdate();
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
                                holdGeoObbjectData = null;
                                initUIs();

                                worldModeAdmin.setPresent(Constants.Mode.ACTIVATE.STOP);
                                worldModeAdmin.setWorldMap(Constants.Mode.ACTIVATE.ACTIVE);
                            }
                        }
                }
        );
        backPlateGroup.setUpdateFlag(true);
        backPlateGroup.setDrawFlag(true);
    }


    public void setAlreadyPresentGetFlags(List<Boolean> _alreadyPresentGetFlags) {
        alreadyPresentGetFlags = _alreadyPresentGetFlags;
    }
    public List<Boolean> getAlreadyPresentGetFlags() {
        return alreadyPresentGetFlags;
    }

    public int getPresentListSize() {
        return size;
    }

    public Integer[] getScores() {
        return new Integer[] {
            hpScore,
            attackScore,
            defenceScore,
            luckScore,
            specialScore
        };
    }

    public void setScores(int[] _scores) {
        hpScore = _scores[0];
        attackScore = _scores[1];
        defenceScore = _scores[2];
        luckScore = _scores[3];
        specialScore = _scores[4];
    }


    public void presentTextBoxUpdate() {
        textBoxAdmin.setTextBoxExists(presentTextBoxID, true);

        textBoxAdmin.bookingDrawText(presentTextBoxID, "ジオ名 : ", presentTextPaint);
        textBoxAdmin.bookingDrawText(presentTextBoxID, holdGeoObbjectData.getName(), presentTextPaint);
        textBoxAdmin.bookingDrawText(presentTextBoxID, "\n", presentTextPaint);
        textBoxAdmin.bookingDrawText(presentTextBoxID, "これもらっちゃうけどOK?", presentTextPaint);
        textBoxAdmin.bookingDrawText(presentTextBoxID, "MOP", presentTextPaint);

        textBoxAdmin.updateText(presentTextBoxID);
    }

    private void initUIs() {
        presentSelectPlateGroup.setUpdateFlag(false);
        presentSelectPlateGroup.setDrawFlag(false);
        textBoxAdmin.setTextBoxExists(presentTextBoxID, false);
    }

}
/*
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

class IdAndCheckFlag {
    boolean flag;
    int id;
    public IdAndCheckFlag(int _id, boolean _flag) {
        id = _id;
        flag  = _flag;
    }

    public int getID() {
        return id;
    }
    public boolean getFlag() {
        return flag;
    }
    public void setID(int _id) {
        id = _id;
    }
}
*/
/*
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
*/