/*package com.maohx2.ina;


import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteBattleAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.Text.ListBoxAdmin;

import com.maohx2.ina.UI.BattleUserInterface;
import android.graphics.Paint;

//by kmhanko
import com.maohx2.ina.Battle.*;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
*/

/**
 * Created by ina on 2017/10/15.
 */
/*



public class BattleGameSystem {

    BattleUnitAdmin battle_unit_admin;
    BattleUserInterface battle_user_interface;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;
    Graphic graphic;

    // by kmhanko
    BattleUnitDataAdmin battleUnitDataAdmin;

    Inventry inventry;
    EquipmentItemDataAdmin equipment_item_data_admin;
    PaletteAdmin palette_admin;
    DungeonModeManage dungeonModeManage;


    // TODO : holderは不要
    public void init(Graphic _graphic, MyDatabaseAdmin _myDatabaseAdmin, BattleUserInterface _battle_user_interface, Activity battle_activity, MyDatabaseAdmin my_database_admin) {

        graphic = _graphic;

        battle_user_interface = _battle_user_interface;
        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin(graphic);
        list_box_admin = new ListBoxAdmin();
        text_box_admin.init(battle_user_interface);
        list_box_admin.init(battle_user_interface, graphic);
        dungeonModeManage = new DungeonModeManage();



        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);

        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, my_database_admin);

        palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipment_item_data_admin);



        //by kmhanko
        GlobalData globalData = (GlobalData) battle_activity.getApplication();
        PlayerStatus playerStatus = globalData.getPlayerStatus();
        battleUnitDataAdmin = new BattleUnitDataAdmin(_myDatabaseAdmin, graphic); // TODO : 一度読み出せばいいので、GlobalData管理が良いかもしれない
        battle_unit_admin.init(graphic, battle_user_interface, battle_activity, battleUnitDataAdmin, playerStatus, palette_admin, dungeonModeManage, my_database_admin);
    }

    //by kmhanko
    public void updata() {
        update();
    }
    public void update() {
        text_box_admin.update();
        list_box_admin.update();
        battle_user_interface.update();
        battle_unit_admin.update();
    }


    public void draw() {
        battle_unit_admin.draw();
        graphic.draw();
    }
}
*/