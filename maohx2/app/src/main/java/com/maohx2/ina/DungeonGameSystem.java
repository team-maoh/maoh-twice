package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Battle.BattleUnitDataAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;

/**
 * Created by ina on 2017/09/05.
 */



public class DungeonGameSystem {

    MapObjectAdmin map_object_admin;
    MapAdmin map_admin;
    DungeonUserInterface dungeon_user_interface;
    BattleUserInterface battle_user_interface;
    Graphic graphic;
    Paint paint;
    BattleUnitAdmin battle_unit_admin;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;

    // by kmhanko
    BattleUnitDataAdmin battleUnitDataAdmin;

    Inventry inventry;
    EquipmentItemDataAdmin equipment_item_data_admin;
    PaletteAdmin palette_admin;

    DungeonModeManage dungeonModeManage;

    public void init(DungeonUserInterface _dungeon_user_interface, Graphic _graphic, SoundAdmin sound_admin, MyDatabaseAdmin _myDatabaseAdmin, BattleUserInterface _battle_user_interface, Activity dungeon_activity, MyDatabaseAdmin my_database_admin) {
        dungeon_user_interface = _dungeon_user_interface;
        battle_user_interface = _battle_user_interface;
        graphic = _graphic;

        dungeonModeManage = new DungeonModeManage();
        map_admin = new MapAdmin(graphic);
        map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, sound_admin, map_admin);
        paint = new Paint();
        paint.setColor(Color.BLUE);





        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin(graphic);
        list_box_admin = new ListBoxAdmin();
        text_box_admin.init(dungeon_user_interface);
        list_box_admin.init(dungeon_user_interface, graphic);



        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);

        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, my_database_admin);

        palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipment_item_data_admin);



        //by kmhanko
        GlobalData globalData = (GlobalData) dungeon_activity.getApplication();
        PlayerStatus playerStatus = globalData.getPlayerStatus();
        battleUnitDataAdmin = new BattleUnitDataAdmin(_myDatabaseAdmin, graphic); // TODO : 一度読み出せばいいので、GlobalData管理が良いかもしれない
        battle_unit_admin.init(graphic, battle_user_interface, dungeon_activity, battleUnitDataAdmin, playerStatus, palette_admin);
    }

    public void update() {

        switch (dungeonModeManage.getMode()) {

            case MAP:
                map_object_admin.update();
                break;

            case BUTTLE:
                battle_user_interface.update();
                battle_unit_admin.update();
                break;
        }

    }

    public void draw() {

        switch (dungeonModeManage.getMode()) {
            case MAP:
                map_admin.drawMap_for_autotile_4div_combine();
                map_object_admin.draw();
                //graphic.bookingDrawCircle(0,0,10,paint);
                break;

            case BUTTLE:
                battle_unit_admin.draw();
                break;
        }

        graphic.draw();
    }

    public DungeonGameSystem() {
    }
}


