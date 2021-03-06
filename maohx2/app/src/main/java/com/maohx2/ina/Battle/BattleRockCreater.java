package com.maohx2.ina.Battle;

import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2018/05/06.
 */

public class BattleRockCreater {

    static Graphic graphic;

    private BattleRockCreater(){};

    public static void setGraphic(Graphic _graphic) {
        graphic = _graphic;
    }

    static BattleBaseUnitData getBattleBaseUnitData(int _hp, int _defence) {
        BattleBaseUnitData tempBBUD = new BattleBaseUnitData();

        tempBBUD.setName("岩");//仮
        tempBBUD.setDbStatus(BattleBaseUnitData.DbStatusID.InitialHP, _hp);
        tempBBUD.setDbStatus(BattleBaseUnitData.DbStatusID.InitialDefence, _defence);
        tempBBUD.setDbStatus(BattleBaseUnitData.DbStatusID.AttackFlame, 0);

        tempBBUD.setBitmapData(graphic.searchBitmap("rock01"));//仮

        tempBBUD.setRadius(70);

        //HP、defence格納。ボーナスや成長は0。ドロップアイテムは無しにしておく。
        //別途ドロップ設定が必要
        return tempBBUD;
    }
}
