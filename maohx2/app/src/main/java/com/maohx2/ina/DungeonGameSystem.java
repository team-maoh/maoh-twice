package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.fuusya.MapPlateAdmin;
import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.DungeonDataAdmin;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Battle.BattleUnitDataAdmin;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.Arrange.InventryS;
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
    DungeonDataAdmin dungeon_data_admin;
    Camera camera;
    Point map_size = new Point(0, 0);//カメラのインスタンス化に必要

    // by kmhanko
    BattleUnitDataAdmin battleUnitDataAdmin;

    Inventry inventry;
    EquipmentItemDataAdmin equipment_item_data_admin;
    PaletteAdmin palette_admin;

    DungeonModeManage dungeonModeManage;
    boolean is_displaying_menu, is_touching_outside_menu;
    MapPlateAdmin map_plate_admin;

    InventryS equipmentInventry;
    InventryS expendInventry;
    BitmapData backGround;

    public void init(DungeonUserInterface _dungeon_user_interface, Graphic _graphic, SoundAdmin sound_admin, MyDatabaseAdmin _myDatabaseAdmin, BattleUserInterface _battle_user_interface, Activity dungeon_activity, MyDatabaseAdmin my_database_admin, ActivityChange activityChange) {
        dungeon_user_interface = _dungeon_user_interface;
        battle_user_interface = _battle_user_interface;
        graphic = _graphic;

        dungeonModeManage = new DungeonModeManage();
        map_plate_admin = new MapPlateAdmin(graphic, dungeon_user_interface, activityChange);
        map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, sound_admin, map_plate_admin, dungeonModeManage);

        dungeon_data_admin = new DungeonDataAdmin(_myDatabaseAdmin);
        map_size.set(dungeon_data_admin.getDungeon_data().get(2).getMap_size_x(), dungeon_data_admin.getDungeon_data().get(2).getMap_size_y());
        //camera = new Camera(map_size, 64*4);
        map_admin = new MapAdmin(graphic, map_object_admin, dungeon_data_admin.getDungeon_data().get(2));
//        map_object_admin.getCamera(map_admin.getCamera());
        //map_object_admin = new MapObjectAdmin(graphic, dungeon_user_interface, sound_admin, map_admin,this, dungeonModeManage);
        paint = new Paint();
        paint.setColor(Color.BLUE);

        battle_unit_admin = new BattleUnitAdmin();
        text_box_admin = new TextBoxAdmin(graphic);
        list_box_admin = new ListBoxAdmin();
        text_box_admin.init(dungeon_user_interface);
        list_box_admin.init(dungeon_user_interface, graphic);
        //map_plate_admin = new MapPlateAdmin(graphic, dungeon_user_interface, this);

        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);

        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, my_database_admin);

        GlobalData globalData = (GlobalData)(dungeon_activity.getApplication());
        equipmentInventry = globalData.getEquipmentInventry();
        expendInventry = globalData.getExpendItemInventry();

        palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipmentInventry, expendInventry);
        //palette_admin = new PaletteAdmin(battle_user_interface, graphic, equipment_item_data_admin);


        PlayerStatus playerStatus = globalData.getPlayerStatus();
        battleUnitDataAdmin = new BattleUnitDataAdmin(_myDatabaseAdmin, graphic); // TODO : 一度読み出せばいいので、GlobalData管理が良いかもしれない
        battle_unit_admin.init(graphic, battle_user_interface, dungeon_activity, battleUnitDataAdmin, playerStatus, palette_admin, dungeonModeManage, my_database_admin, map_plate_admin);

        backGround = graphic.searchBitmap("e51-0");

    }

    public void update() {

        switch (dungeonModeManage.getMode()) {

            case MAP:
                //map_object_admin.update(is_displaying_menu, is_touching_outside_menu);
                //map_plate_admin.update(is_displaying_menu);
                map_object_admin.update();
                map_plate_admin.update();
                break;

            case BUTTLE_INIT:
                battle_unit_admin.spawnEnemy();
                dungeonModeManage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE);

            case BUTTLE:
                battle_user_interface.update();
                battle_unit_admin.update();
                break;
        }
    }

    public void draw() {

        graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);

        switch (dungeonModeManage.getMode()) {
            case MAP:
                map_admin.drawMap_for_autotile_light();
                map_object_admin.draw();
                map_plate_admin.draw();
                //graphic.bookingDrawCircle(0,0,10,paint);
                break;

            case BUTTLE:
                battle_unit_admin.draw();
                break;
        }
        graphic.draw();
    }

    public void setIsDisplayingMenu(boolean _is_displaying_menu){
        is_displaying_menu = _is_displaying_menu;
    }

    public void setIsTouchingOutsideMenu(boolean _is_touching_outside_menu){
        is_touching_outside_menu = _is_touching_outside_menu;
    }

    public DungeonGameSystem(){}

}


