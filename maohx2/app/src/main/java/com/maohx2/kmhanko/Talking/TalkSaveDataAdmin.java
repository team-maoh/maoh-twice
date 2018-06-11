package com.maohx2.kmhanko.Talking;

import com.maohx2.ina.Constants;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.Saver.TalkSaver;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by user on 2018/06/12.
 */

//イベントを一度行ったかどうかを保存しておくためのクラス
public class TalkSaveDataAdmin {
    List<String> talkName = new ArrayList<>();
    List<Boolean> talkFlag = new ArrayList<>();

    MyDatabaseAdmin databaseAdmin;
    TalkSaver talkSaver;

    public TalkSaveDataAdmin(MyDatabaseAdmin _databaseAdmin) {
        databaseAdmin = _databaseAdmin;
        talkSaver = new TalkSaver(databaseAdmin, "TalkSave", "TalkSave.db", 1,  Constants.DEBUG_SAVE_MODE);
        talkSaver.setTalkSaveDataAdmin(this);
    }

    public void load() {
        talkSaver.load();
    }

    public void save() {
        talkSaver.save();
    }

    public String getTalkName(int i) {
        return talkName.get(i);
    }

    public Boolean getTalkFlag(int i) {
        return talkFlag.get(i);
    }

    public String getTalkFlagByString(int i) {
        if (talkFlag.get(i)) {
            return "true";
        } else {
            return "false";
        }
    }

    public void setTalkNames(List<String> _talkName) {
        talkName = _talkName;
    }

    public void setTalkFlags(List<Boolean> _talkFlag) {
        talkFlag = _talkFlag;
    }

    public int getSize() {
        return talkName.size();
    }

    // *** 主に使う関数
    public void setTalkFlagByName(String name, Boolean flag) {
        for (int i = 0; i < talkName.size(); i++) {
            if (name.equals(talkName.get(i))) {
                talkFlag.set(i, flag);
            }
        }
    }

    public boolean getTalkFlagByName(String name) {//falseなら未実行、trueなら実行済
        for (int i = 0; i < talkName.size(); i++) {
            if (name.equals(talkName.get(i))) {
                return talkFlag.get(i);
            }
        }
        return false;
    }


}
