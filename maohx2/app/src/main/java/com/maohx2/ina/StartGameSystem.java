package com.maohx2.ina;


import android.app.Activity;
import android.view.SurfaceHolder;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;


/**
 * Created by ina on 2017/10/15.
 */




public class StartGameSystem {

    BattleUnitAdmin battle_unit_admin;
    SurfaceHolder holder;
    UserInterface start_user_interface;
    Graphic graphic;
    Inventry inventry;
    EquipmentItemDataAdmin equipment_item_data_admin;
    PaletteAdmin palette_admin;

    public void init(SurfaceHolder _holder, Graphic _graphic, BattleUserInterface _start_user_interface, Activity start_activity, MyDatabaseAdmin my_database_admin) {

        holder = _holder;
        graphic = _graphic;
        start_user_interface = _start_user_interface;

        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);

        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, my_database_admin);

        inventry = new Inventry(start_user_interface, graphic, equipment_item_data_admin);
        palette_admin = new PaletteAdmin(_start_user_interface, graphic, inventry);

    }


    public void updata() {
        inventry.updata();
        palette_admin.update();
    }


    public void draw() {
        inventry.draw();
        palette_admin.draw();
        start_user_interface.draw();
        graphic.draw();
    }
}