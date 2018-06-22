package com.maohx2.kmhanko.Talking;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;
import java.util.List;
import android.graphics.Paint;

/**
 * Created by user on 2018/06/12.
 */

//Talkの呼び出し、タッチによる進行、立ち絵の表示、TextBoxIDの管理を行うクラス
public class TalkAdmin {

    Graphic graphic;
    UserInterface userInterface;
    MyDatabaseAdmin databaseAdmin;
    MyDatabase database;
    TextBoxAdmin textBoxAdmin;
    SoundAdmin soundAdmin;

    int textBoxID;
    Paint paint;

    boolean isTalking;
    int count;
    int talkSize;

    int waitCount;
    boolean isWait;

    boolean isUpdateThisFrame;

    static final int TALK_CONTENT_MAX = 100;
    String talkContent[][] = new String[TALK_CONTENT_MAX][];
    //ImageContext talkChara[] = new ImageContext[TALK_CONTENT_MAX];

    String talkCharaName[] = new String[TALK_CONTENT_MAX];
    boolean leftOrRight[] = new boolean[TALK_CONTENT_MAX];

    int talkWaitTime[] = new int[TALK_CONTENT_MAX];

    String talkCharaLeft;
    String talkCharaRight;

    static final float SCALE_X = 2.7f;
    static final float SCALE_Y = 2.7f;

    static final int ROWS = 4;

    static final String DB_NAME = "TalkData";
    static final String DB_ASSET = "TalkData.db";

    TalkSaveDataAdmin talkSaveDataAdmin;

    public TalkAdmin(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, SoundAdmin _soundAdmin) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
        soundAdmin = _soundAdmin;

        initTextBox();
        initDatabase();

        talkSaveDataAdmin = new TalkSaveDataAdmin(databaseAdmin);
        talkSaveDataAdmin.load();

        clear();//init群の後
    }

    // *** init関係 ***
    private void initTextBox() {
        //TextBox関係の初期化
        paint = new Paint();
        paint.setTextSize(35);
        paint.setARGB(255, 255, 255, 255);

        textBoxID = textBoxAdmin.createTextBox(50, 700, 1550, 880, ROWS);
        textBoxAdmin.setTextBoxUpdateTextByTouching(textBoxID, false);//falseでOK。このクラス側でタッチを判定して進める。

        textBoxAdmin.setTextBoxExists(textBoxID, false);
    }

    private void initDatabase() {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }


    // *** 実用関数 ***

    public void start(String tableName, Boolean saveIgnoreFlag) {
        //この関数を呼ぶと、会話イベントがスタートする。 saveIgnoreFlag = true でセーブ状況によらず実行
        if (isTalking) {
            return;
        }
        if (!saveIgnoreFlag && talkSaveDataAdmin.getTalkFlagByName(tableName)) {
            return;
        }
        talkSaveDataAdmin.setTalkFlagByName(tableName, true);
        talkSaveDataAdmin.save();

        clear();
        talkContent = loadTalkContent(tableName);
        talkCharaName = loadTalkChara(tableName);
        talkWaitTime = loadTalkWaitTime(tableName);
        leftOrRight = loadLeftOrRight(tableName);

        isTalking = true;
        textBoxAdmin.setTextBoxExists(textBoxID, true);

        updateText();
    }

    public void start(String tableName) {
        //第二引数なしの場合、セーブデータ依存となる
        start(tableName, false);
    }

    public void update() {
        //会話イベント用の更新。常に呼び続けておいて構わない
        isUpdateThisFrame = false;
        if (!isTalking) {
            return;
        }
        if (isWait) {
            if (waitCount >= talkWaitTime[count]) {
                updateTextFinal();
                updateChara();
                isWait = false;
                waitCount = 0;
            } else {
                waitCount++;
            }
        } else {
            touchCheck();
        }
    }

    public void draw() {
        //会話イベント用の描画。常に呼び続けておいて構わない
        if (!isTalking) {
            return;
        }
        if (isWait) {
            return;
        }
        if (count >= TALK_CONTENT_MAX ) {
            throw new Error("TalkAdmin : countがTALK_CONTENT_MAX以上となった。配列変数の大きさが足りません。(TALK_CONTENT_MAXを増やしなさい)");
        }

        int alphaLeft, alphaRight;
        if (leftOrRight[count]) {
            alphaLeft = 254;
            alphaRight = 192;
        } else {
            alphaLeft = 192;
            alphaRight = 254;
        }
        if (talkCharaLeft != null) {
            graphic.bookingDrawBitmapData(
                    graphic.searchBitmap(talkCharaLeft),
                    300, 450,
                    SCALE_X, SCALE_Y,
                    0, alphaLeft,
                    false
            );
        }
        if (talkCharaRight != null) {
            graphic.bookingDrawBitmapData(
                    graphic.searchBitmap(talkCharaRight),
                    1300, 450,
                    SCALE_X, SCALE_Y,
                    0, alphaRight,
                    false
            );
        }

    }

    public void clear() {
        //会話イベントの終了。呼ぶと会話イベントが強制終了する
        isTalking = false;
        count = 0;
        talkSize = 0;
        isWait = false;
        isUpdateThisFrame = false;
        waitCount = 0;
        textBoxAdmin.setTextBoxExists(textBoxID, false);
        textBoxAdmin.resetTextBox(textBoxID);
    }

    // *** 内部関数 ***
    private void touchCheck() {
        //画面をタッチしていた場合、会話文番号を進め、終了でなければテキストを更新する
        if (userInterface.getTouchState() == Constants.Touch.TouchState.UP) {
            if (countUp()) {
                updateText();
            }
        }
    }

    private boolean countUp() {
        //会話文番号を進め、終了であればfalse、終了でなければtrueを返す
        count++;
        if (count >= talkSize) {
            //全ての会話文を流したので会話を終了
            clear();
            return false;
        }
        return true;
    }

    private void updateText() {
        //テキストを更新する
        textBoxAdmin.resetTextBox(textBoxID);
        for(int i = 0; i < talkContent[count].length; i++){
            if (talkContent[count][i] != null) {
                textBoxAdmin.bookingDrawText(textBoxID, talkContent[count][i], paint);
            }
        }

        textBoxAdmin.updateText(textBoxID);
        if (talkWaitTime[count] > 1) {
            isWait = true;
            waitCount = 0;
            textBoxAdmin.setTextBoxExists(textBoxID, false);
        } else {
            updateTextFinal();
            updateChara();
        }
    }

    private void updateTextFinal() {
        textBoxAdmin.setTextBoxExists(textBoxID, true);
        isUpdateThisFrame = true;
    }

    private void updateChara() {
        if (leftOrRight[count]) {
            talkCharaLeft = talkCharaName[count];
        } else {
            talkCharaRight = talkCharaName[count];
        }
    }


    // *** Load関係 *** 重いようなら別クラスにしてゲーム開始時に読む
    private String[][] loadTalkContent(String tableName) {
        //DBからテキストを読み出す。TextBoxAdminに送り出せるよう変換する。
        talkSize = database.getSize(tableName);
        if (talkSize >= TALK_CONTENT_MAX) {
            throw new Error("TalkAdmin : 会話文の数がTALK_CONTENT_MAX以上となった。配列変数の大きさが足りません。(TALK_CONTENT_MAXを増やしなさい) : " + talkSize);
        }

        List<String> text = database.getString(tableName, "text");

        String tempTalkContent[][] = new String[TALK_CONTENT_MAX][ROWS * 2];
        String[] tempText;

        for (int i = 0; i < talkSize; i++) {
            tempText = text.get(i).split("\n");
            if (tempText.length > ROWS) {
                throw new Error("TalkAdmin : 会話文の行数が多すぎる : " + tempText.length +" / " + ROWS + "行 該当箇所:" + tableName +" rowid = " + (i + 1) );
            }
            if (tempText.length <= 0) {
                throw new Error("TalkAdmin : 会話文に記載なし : 該当箇所:" + tableName +" rowid = " + (i + 1) );
            }
            for (int j = 0; j < tempText.length; j++) {
                tempTalkContent[i][2 * j] = tempText[j];
                if (j == tempText.length - 1) {
                    //ループの最後の場合
                    tempTalkContent[i][2 * j + 1] = "MOP";
                } else {
                    tempTalkContent[i][2 * j + 1] = "\n";
                }
            }

        }
        return tempTalkContent;
    }

    private int[] loadTalkWaitTime(String tableName) {
        //DBからWaitTime(次の会話文表示までの待機フレーム)を読み出す。
        talkSize = database.getSize(tableName);
        if (talkSize >= TALK_CONTENT_MAX) {
            throw new Error("TalkAdmin : 会話文の数がTALK_CONTENT_MAX以上となった。配列変数の大きさが足りません。(TALK_CONTENT_MAXを増やしなさい) : " + talkSize);
        }

        List<Integer> waitTime = database.getInt(tableName, "wait_time");

        int tempTalkWaitTime[] = new int[TALK_CONTENT_MAX];

        for (int i = 0; i < talkSize; i++) {
            tempTalkWaitTime[i] = waitTime.get(i);
        }
        return tempTalkWaitTime;
    }

    private boolean[] loadLeftOrRight(String tableName) {
        talkSize = database.getSize(tableName);
        if (talkSize >= TALK_CONTENT_MAX) {
            throw new Error("TalkAdmin : 会話文の数がTALK_CONTENT_MAX以上となった。配列変数の大きさが足りません。(TALK_CONTENT_MAXを増やしなさい) : " + talkSize);
        }
        List<Integer> left_or_right = database.getInt(tableName, "left_or_right");
        boolean tempLeftOrRight[] = new boolean[TALK_CONTENT_MAX];
        for (int i = 0; i < talkSize; i++) {
            tempLeftOrRight[i] = (left_or_right.get(i) == 0);
        }
        return tempLeftOrRight;
    }

    private String[] loadTalkChara(String tableName) {
        talkSize = database.getSize(tableName);
        if (talkSize >= TALK_CONTENT_MAX) {
            throw new Error("TalkAdmin : 会話文の数がTALK_CONTENT_MAX以上となった。配列変数の大きさが足りません。(TALK_CONTENT_MAXを増やしなさい) : " + talkSize);
        }
        List<String> imageName = database.getString(tableName, "person_image_name");
        String tempTalkCharaName[] = new String[TALK_CONTENT_MAX];
        for (int i = 0; i < talkSize; i++) {
            tempTalkCharaName[i] = imageName.get(i);
        }
        return tempTalkCharaName;
    }

    /*
    private String[] loadTalkChara(String tableName) {
        //DBから画像名を読み出す。左右を見て、ImageContextを作成、セットする
        talkSize = database.getSize(tableName);
        if (talkSize >= TALK_CONTENT_MAX) {
            throw new Error("TalkAdmin : 会話文の数がTALK_CONTENT_MAX以上となった。配列変数の大きさが足りません。(TALK_CONTENT_MAXを増やしなさい) : " + talkSize);
        }

        imageName = database.getString(tableName, "person_image_name");
        leftOrRight = database.getInt(tableName, "left_or_right");// 0 = left, 1 = right
        //ImageContext tempTalkChara[] = new ImageContext[TALK_CONTENT_MAX];

        /*
        //立ち絵のImageContext作成
        for (int i = 0; i < talkSize; i++) {
            if (leftOrRight.get(i) == 0) {
                tempTalkChara[i] = graphic.makeImageContext(
                        graphic.searchBitmap(imageName.get(i)),
                        300, 450,
                        SCALE_X, SCALE_Y,
                        0, 255,
                        false);
            } else {
                tempTalkChara[i] = graphic.makeImageContext(
                        graphic.searchBitmap(imageName.get(i)),
                        1300, 450,
                        SCALE_X, SCALE_Y,
                        0, 255,
                        false);
            }
        }
        return tempTalkChara;
        *?
    }
    */


    // *** Getter, Setter ***
    public boolean isTalking() {
        return isTalking;
    }

    public boolean isWait() {
        return isWait;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public boolean isUpdateThisFrame() {
        return isUpdateThisFrame;
    }

    public int getID() {
        if (isTalking) {
            return count + 1;
        } else {
            return -1;
        }
    }

    public int getCount() {
        return count;
    }

    public boolean isWaitOrNotTalk() {
        return isWait || !isTalking;
    }

}
