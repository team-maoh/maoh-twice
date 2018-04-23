package com.maohx2.ina;


import android.app.Activity;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentInventrySaver;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;


/**
 * Created by ina on 2017/10/15.
 */




public class StartGameSystem {

    BattleUnitAdmin battle_unit_admin;
    SurfaceHolder holder;
    BattleUserInterface start_user_interface;
    Graphic graphic;
    EquipmentItemDataAdmin equipment_item_data_admin;
    PaletteAdmin palette_admin;
    int n = 0;
    int m = 0;
    Paint paint;
    int alpha = 255;
    int up_down = 5;

    EquipmentInventrySaver equipmentInventrySaver;
    InventryS equipmentInventry;

    EquipmentItemData tmpEquipmentItemData;

    public void init(SurfaceHolder _holder, Graphic _graphic, BattleUserInterface _start_user_interface, Activity start_activity, MyDatabaseAdmin my_database_admin) {

        holder = _holder;
        graphic = _graphic;
        start_user_interface = _start_user_interface;

        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);


        GlobalData globalData = (GlobalData)(start_activity.getApplication());
        equipmentInventry = globalData.getEquipmentInventry();
        equipmentInventrySaver = globalData.getEquipmentInventrySaver();


        palette_admin = new PaletteAdmin(_start_user_interface, graphic);

        //equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, my_database_admin);
        //palette_admin = new PaletteAdmin(start_user_interface, graphic, equipment_item_data_admin);

        paint = new Paint();
        paint.setTextSize(70);



    }


    public void updata() {
        n++;

        if(n == 100) {
            tmpEquipmentItemData = new EquipmentItemData();

            tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
            tmpEquipmentItemData.setName("デバッグ剣"+String.valueOf(m));
            tmpEquipmentItemData.setImageName("剣");
            tmpEquipmentItemData.setItemImage(graphic.searchBitmap("剣"));
            tmpEquipmentItemData.setPrice(100);
            tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.SWORD);
            tmpEquipmentItemData.setUseNum(100);
            tmpEquipmentItemData.setRadius(100);
            tmpEquipmentItemData.setDecayRate(0.99f);
            tmpEquipmentItemData.setTouchFrequency(2);
            tmpEquipmentItemData.setAutoFrequencyRate(0.7f);
            tmpEquipmentItemData.setAttack(100);
            tmpEquipmentItemData.setDefence(50);


            equipmentInventry.addItemData(tmpEquipmentItemData);

            equipmentInventry.save();

            n = 0;
            m++;
        }

        paint.setARGB(alpha,255,255,255);

        alpha += up_down;
        if(alpha < 0 || alpha > 255){
            up_down *= -1;
            alpha += up_down*2;
        }

        equipmentInventry.updata();
        palette_admin.update(false);
    }


    public void draw() {
        equipmentInventry.draw();
        palette_admin.draw();
        start_user_interface.draw();
        graphic.bookingDrawText("Please Touch!",550,750, paint);
        graphic.draw();
    }
}