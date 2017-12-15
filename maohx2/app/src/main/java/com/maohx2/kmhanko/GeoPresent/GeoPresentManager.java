package com.maohx2.kmhanko.GeoPresent;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;

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

    public GeoPresentManager() {
    }

    public void init(UserInterface _user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin) {
        userInterface = _user_interface;
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
    }

    public void checkPresent() {




    }


}
