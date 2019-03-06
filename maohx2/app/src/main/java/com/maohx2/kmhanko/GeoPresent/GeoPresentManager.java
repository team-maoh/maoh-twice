package com.maohx2.kmhanko.GeoPresent;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.SELECT_WINDOW;
import com.maohx2.ina.Constants.SELECT_WINDOW_PLATE;
import com.maohx2.ina.Constants.SIDE_INVENTRY;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.GameSystem.WorldModeAdmin;
import com.maohx2.kmhanko.Saver.GeoPresentSaver;
import com.maohx2.kmhanko.WindowPlate.WindowTextPlate;
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
import com.maohx2.kmhanko.plate.BoxImageTextPlate;
import com.maohx2.kmhanko.sound.SoundAdmin;

import com.maohx2.ina.Draw.ImageContext;

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

    //List<Boolean> alreadyPresentGetFlags = new ArrayList<>();
    List<Integer> presentGetCounts = new ArrayList<>();
    //[0] id = 1, [1] id=2, であることに注意

    InventryS geoInventry;
    InventryS expendItemInventry;
    ExpendItemDataAdmin expendItemDataAdmin;

    PlateGroup<BoxImageTextPlate> presentSelectPlateGroup;

    WorldModeAdmin worldModeAdmin;

    PlayerStatus playerStatus;

    GeoPresentSaver geoPresentSaver;

    SoundAdmin soundAdmin;

    ImageContext gaiaImage;


    WindowTextPlate scoreTitle;
    WindowTextPlate scoreWindow[] = new WindowTextPlate[5];

    Paint scoreTitlePaint;
    Paint scorePaint;

    public GeoPresentManager(Graphic _graphic, UserInterface _user_interface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, InventryS _geoInventry, InventryS _expendItemInventry, ExpendItemDataAdmin _expendItemDataAdmin, PlayerStatus _playerStatus, SoundAdmin _soundAdmin) {
        userInterface = _user_interface;
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
        geoInventry = _geoInventry; //TODO globalから
        expendItemInventry = _expendItemInventry;
        expendItemDataAdmin = _expendItemDataAdmin;
        playerStatus = _playerStatus;
        worldModeAdmin = _worldModeAdmin;
        soundAdmin = _soundAdmin;

        loadDatabase(databaseAdmin);
        initTextBox();
        initPlateGroup();
        initPresentTextBox();
        initWindow();
        initUIs();

    }

    public void start() {
        geoInventry.setPosition(SIDE_INVENTRY.INV_LEFT, SIDE_INVENTRY.INV_UP, SIDE_INVENTRY.INV_RIGHT,SIDE_INVENTRY.INV_BOTTOM,SIDE_INVENTRY.INV_CONTENT_NUM);
        gaiaImage = graphic.makeImageContext(graphic.searchBitmap("gaia0r"), 300, 450, 2.7f, 2.7f, 0, 255, false);
        scoreWindowUpdate();
        updateTextBox();
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
        scoreWindowUpdate();
    }

    private void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = database_admin.getMyDatabase(dbName);

        size = database.getSize(presentListTableName);
    }

    private void initWindow() {
        scoreTitlePaint = new Paint();
        scoreTitlePaint.setColor(Color.WHITE);
        scoreTitlePaint.setTextSize(50);

        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(40);

        for (int i = 0; i < scoreWindow.length; i++) {
            scoreWindow[i] = new WindowTextPlate(graphic, new int[] {520, 200 + i * 100, 1080, 200 + i * 100 + 80});
            scoreWindow[i].setDrawFlag(true);
        }
        scoreTitle = new WindowTextPlate(graphic, new int[] { 500, 50, 1100, 150}, "献上ポイント", scoreTitlePaint, WindowTextPlate.TextPosition.CENTER);
        scoreTitle.setDrawFlag(true);
    }

    private void initTextBox() {
        /*
        scoreTextBoxID = textBoxAdmin.createTextBox(600,100,1000,660,6);
        textBoxAdmin.setTextBoxUpdateTextByTouching(scoreTextBoxID, false);
        textBoxAdmin.setTextBoxExists(scoreTextBoxID, false);
        scoreTextBoxUpdate();
        */

        messageBoxID = textBoxAdmin.createTextBox(50, 700, 1150, 880,3);
        textBoxAdmin.setTextBoxExists(messageBoxID, false);
        updateTextBox();
    }

    private void updateTextBox() {
        textBoxAdmin.resetTextBox(messageBoxID);
        textBoxAdmin.setTextBoxUpdateTextByTouching(messageBoxID, false);
        textBoxAdmin.bookingDrawText(messageBoxID, "ジオオブジェクトちょうだ〜い");
        textBoxAdmin.bookingDrawText(messageBoxID, "MOP");
        textBoxAdmin.updateText(messageBoxID);
    }


    private void initPlateGroup() {
        Paint textPaint = new Paint();
        textPaint.setTextSize(SELECT_WINDOW_PLATE.BUTTON_TEXT_SIZE);
        textPaint.setARGB(255,255,255,255);
        presentSelectPlateGroup = new PlateGroup<BoxImageTextPlate>(
                new BoxImageTextPlate[]{
                        new BoxImageTextPlate(
                                graphic, userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{SELECT_WINDOW.YES_LEFT, SELECT_WINDOW.YES_UP, SELECT_WINDOW.YES_RIGHT, SELECT_WINDOW.YES_BOTTOM},
                                "献上する",
                                textPaint
                        ),
                        new BoxImageTextPlate(
                                graphic, userInterface,
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


    /*
    private void scoreTextBoxUpdate() {
        textBoxAdmin.resetTextBox(scoreTextBoxID);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "献上ポイント");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "HP " + hpScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Attack " + attackScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Deffence " + defenceScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "Luck " + luckScore);
        //textBoxAdmin.bookingDrawText(scoreTextBoxID, "\n");
        //textBoxAdmin.bookingDrawText(scoreTextBoxID, "Special " + specialScore);
        textBoxAdmin.bookingDrawText(scoreTextBoxID, "MOP");
        textBoxAdmin.updateText(scoreTextBoxID);
    }
    */


    private void scoreWindowUpdate() {
        scoreWindow[0].setText("HP "  + hpScore, scorePaint, WindowTextPlate.TextPosition.CENTER);
        scoreWindow[1].setText("Attack " + attackScore, scorePaint, WindowTextPlate.TextPosition.CENTER);
        scoreWindow[2].setText("Deffence " + defenceScore, scorePaint, WindowTextPlate.TextPosition.CENTER);
        scoreWindow[3].setText("Luck " + luckScore, scorePaint, WindowTextPlate.TextPosition.CENTER);
        scoreWindow[4].setText("Special " + specialScore, scorePaint, WindowTextPlate.TextPosition.CENTER);
    }

    public void geoInventryUpdate() {
        geoInventry.updata();
        InventryData inventryData = userInterface.getInventryData();
        if (inventryData != null) {
            if (inventryData.getItemNum() > 0 && inventryData.getItemData().getItemKind() == Constants.Item.ITEM_KIND.GEO) {
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
        presentToInventry(getCheckFlag());
    }

    private void presentGeoObject(GeoObjectData geoObjectData) {
        hpScore += geoObjectData.getHp();
        attackScore += geoObjectData.getAttack();
        defenceScore += geoObjectData.getDefence();
        luckScore += geoObjectData.getLuck();
        hpScore += (geoObjectData.getHpRate() - 1.0) * 30 * Math.pow(2,playerStatus.getClearCount());
        attackScore += (geoObjectData.getAttackRate() - 1.0) * 30 * Math.pow(2,playerStatus.getClearCount());
        defenceScore += (geoObjectData.getDefenceRate() - 1.0) * 30 * Math.pow(2,playerStatus.getClearCount());
        luckScore += (geoObjectData.getLuckRate() - 1.0) * 30 * Math.pow(2,playerStatus.getClearCount());

        sumScore = hpScore + attackScore + defenceScore + luckScore + specialScore;

        geoInventry.deleteItemData(holdGeoObbjectData.getName());
        holdGeoObbjectData = null;

        textBoxAdmin.resetTextBox(messageBoxID);
        textBoxAdmin.bookingDrawText(messageBoxID, "ありがたくいただくとするわ");
        textBoxAdmin.bookingDrawText(messageBoxID, "MOP");
        textBoxAdmin.updateText(messageBoxID);
    }

    //プレゼントリストのDBをチェックして、プレゼントを受け取るIDを返す。過去に受け取った回数なども考慮。
    private List<Integer> getCheckFlag() {
        List<Integer> newPresentNumber = new ArrayList<Integer>();

        List<Integer> hp = database.getInt(presentListTableName, "hp");
        List<Integer> attack = database.getInt(presentListTableName, "attack");
        List<Integer> defence = database.getInt(presentListTableName, "defence");
        List<Integer> luck = database.getInt(presentListTableName, "luck");
        List<Integer> special = database.getInt(presentListTableName, "special");
        List<Integer> sum = database.getInt(presentListTableName, "sum");
        List<Boolean> infinityFlag = database.getBoolean(presentListTableName, "infinityFlag");

        boolean flag = true;
        int j = 0;
        while(flag) {
            flag = false;
            for (int i = 0; i < size; i++) {
                if (
                                hpScore >= hp.get(i) * Math.pow(2, presentGetCounts.get(i) + j) &&
                                attackScore >= attack.get(i) * Math.pow(2, presentGetCounts.get(i) + j) &&
                                defenceScore >= defence.get(i) * Math.pow(2, presentGetCounts.get(i) + j) &&
                                luckScore >= luck.get(i) * Math.pow(2, presentGetCounts.get(i) + j) &&
                                specialScore >= special.get(i) * Math.pow(2, presentGetCounts.get(i) + j) &&
                                sumScore >= sum.get(i) * Math.pow(2, presentGetCounts.get(i) + j) &&
                                (infinityFlag.get(i) || (!infinityFlag.get(i) && (presentGetCounts.get(i) + j) == 0))
                        ) {
                    newPresentNumber.add(i);
                    flag = true;
                }
            }
            j++;
        }
        return newPresentNumber;
    }

    //まだ入手していない且つ入手条件を満たしているPresentのリストを返す
    /*
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
    */


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
            w_script = "id = " + (newPresentNumber.get(i) + 1);
            tableName = database.getOneString(presentListTableName, "table_name", w_script);
            presentName = database.getOneString(presentListTableName, "present_name", w_script);

            if (tableName.equals("GeoObject")) {
                w_script = "name = " + MyDatabase.s_quo(presentName);

                GeoObjectData bufGeoObjectData = GeoObjectDataCreater.getGeoObjectData(
                        new int[] {
                                database.getOneInt(tableName, "hp", w_script) * (int)Math.pow(2, presentGetCounts.get(newPresentNumber.get(i))),
                                database.getOneInt(tableName, "attack", w_script) * (int)Math.pow(2, presentGetCounts.get(newPresentNumber.get(i))),
                                database.getOneInt(tableName, "defence", w_script) * (int)Math.pow(2, presentGetCounts.get(newPresentNumber.get(i))),
                                database.getOneInt(tableName, "luck", w_script) * (int)Math.pow(2, presentGetCounts.get(newPresentNumber.get(i))),
                        },
                        new double[] {
                                1.0 + (database.getOneDouble(tableName, "hp_rate", w_script) - 1.0) * Math.pow(2, presentGetCounts.get(newPresentNumber.get(i))),
                                1.0 + (database.getOneDouble(tableName, "attack_rate", w_script) - 1.0) * Math.pow(2, presentGetCounts.get(newPresentNumber.get(i))),
                                1.0 + (database.getOneDouble(tableName, "defence_rate", w_script) - 1.0) * Math.pow(2, presentGetCounts.get(newPresentNumber.get(i))),
                                1.0 + (database.getOneDouble(tableName, "luck_rate", w_script) - 1.0) * Math.pow(2, presentGetCounts.get(newPresentNumber.get(i)))
                        }
                );

                geoInventry.addItemData(bufGeoObjectData);
                name = bufGeoObjectData.getName();
            }
            if (tableName.equals("Money")) {
                w_script = "name = " + MyDatabase.s_quo(presentName);
                int bufMoney = database.getOneInt(tableName, "price", w_script) * (int)Math.pow(2, presentGetCounts.get(newPresentNumber.get(i)));
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
            gaiaImage = graphic.makeImageContext(graphic.searchBitmap("gaia22r"), 300, 450, 2.7f, 2.7f, 0, 255, false);

            System.out.println("takano:GeoPresentManager#presentToInventry : 献上により獲得 : " + name);

            //セーブのために格納
            presentGetCounts.set(newPresentNumber.get(i), presentGetCounts.get(newPresentNumber.get(i)) + 1);
            //alreadyPresentGetFlags.set(newPresentNumber.get(i) - 1, true);
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
                    soundAdmin.play("levelup00");
                    presentAndCheck(holdGeoObbjectData);
                    holdGeoObbjectData = null;
                    scoreWindowUpdate();
                    initUIs();
                    break;
                case(1) ://キャンセル
                    soundAdmin.play("cancel00");
                    holdGeoObbjectData = null;
                    initUIs();
                    break;
            }
        }
    }

    public void update() {
        geoInventryUpdate();
        presentSelectUpdate();
        backPlateGroup.update();

        //TODO TextBoxの一括ではないupdate
        //textBoxAdmin.setTextBoxExists(scoreTextBoxID, worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.PRESENT);
        textBoxAdmin.setTextBoxExists(messageBoxID, worldModeAdmin.getMode() == Constants.GAMESYSTEN_MODE.WORLD_MODE.PRESENT);
    }




    public void draw() {
        if (gaiaImage != null) {
            graphic.bookingDrawBitmapData(gaiaImage);
        }
        scoreTitle.draw();
        for(int i = 0; i < scoreWindow.length; i++) {
            scoreWindow[i].draw();
        }
        geoInventry.draw();
        presentSelectPlateGroup.draw();
        backPlateGroup.draw();
    }

    private void initBackPlate() {
        backPlateGroup = new PlateGroup<BackPlate>(
                new BackPlate[] {
                        new BackPlate(
                                graphic, userInterface
                        ) {
                            @Override
                            public void callBackEvent() {
                                //戻るボタンが押された時の処理
                                soundAdmin.play("cancel00");

                                holdGeoObbjectData = null;
                                initUIs();

                                worldModeAdmin.setMode(Constants.GAMESYSTEN_MODE.WORLD_MODE.DUNGEON_SELECT_INIT);
                            }
                        }
                }
        );
        backPlateGroup.setUpdateFlag(true);
        backPlateGroup.setDrawFlag(true);
    }


    /*
    public void setAlreadyPresentGetFlags(List<Boolean> _alreadyPresentGetFlags) {
        alreadyPresentGetFlags = _alreadyPresentGetFlags;
    }
    public List<Boolean> getAlreadyPresentGetFlags() {
        return alreadyPresentGetFlags;
    }
    */

    public void setPresentGetCounts(List<Integer> temp) {
        presentGetCounts = temp;

    }
    public List<Integer> getPresentGetCounts() {
        return presentGetCounts;
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
        textBoxAdmin.resetTextBox(presentTextBoxID);
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

    public void release() {
        System.out.println("takanoRelease : GeoPresentManager");
        presentTextPaint = null;
        if (presentSelectPlateGroup != null) {
            presentSelectPlateGroup.release();
            presentSelectPlateGroup = null;
        }
        if (backPlateGroup != null) {
            backPlateGroup.release();
            backPlateGroup = null;
        }
        if (presentGetCounts != null) {
            presentGetCounts.clear();
            presentGetCounts = null;
        }

        for (int i = 0; i < scoreWindow.length; i++) {
            if (scoreWindow[i] != null) {
                scoreWindow[i].release();
                scoreWindow[i] = null;
            }
        }
        scoreWindow = null;

        if (scoreTitle != null) {
            scoreTitle.release();
            scoreTitle = null;
        }
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