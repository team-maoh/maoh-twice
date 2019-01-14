package com.maohx2.ina.GameSystem;


import android.app.Activity;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Activity.UnitedActivity;
import com.maohx2.ina.Activity.UnitedSurfaceView;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Arrange.PaletteCenter;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Credit;
import com.maohx2.ina.Draw.Credits;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentInventrySaver;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.ina.GlobalData;

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
    int alpha = 0;
    int up_down = 5;

    InventryS equipmentInventry;
    InventryS expendInventry;

    EquipmentItemData tmpEquipmentItemData;
    TextBoxAdmin textBoxAdmin;
    int itemExplainTextBoxID;

    BitmapData backGround;

    int count = 0;
    int messageNum = 0;
    //StartActivity start_activity;
    UnitedActivity unitedActicity;

    Credits credis;

    public void init(SurfaceHolder _holder, Graphic _graphic, BattleUserInterface _start_user_interface, UnitedActivity _unitedActicity, MyDatabaseAdmin my_database_admin) {

        holder = _holder;
        graphic = _graphic;
        start_user_interface = _start_user_interface;
        unitedActicity = _unitedActicity;

        PaletteCenter.initStatic(graphic);
        PaletteElement.initStatic(graphic);


        GlobalData globalData = (GlobalData)(unitedActicity.getApplication());
        equipmentInventry = globalData.getEquipmentInventry();
        expendInventry = globalData.getExpendItemInventry();

        equipment_item_data_admin = new EquipmentItemDataAdmin(graphic, my_database_admin);
        //palette_admin = new PaletteAdmin(_start_user_interface, graphic, equipmentInventry, expendInventry, equipment_item_data_admin, soundAdmin);

        paint = new Paint();
        paint.setTextSize(70);
        paint.setARGB(255,0,0,0);

        backGround = graphic.searchBitmap("firstBackground");

        credis = new Credits(graphic);

        /*
        textBoxAdmin = new TextBoxAdmin(graphic);
        textBoxAdmin.init(start_user_interface);
        textBoxAdmin.setTextBoxExists(0,false);
        textBoxAdmin.setTextBoxExists(1,false);
        itemExplainTextBoxID = textBoxAdmin.createTextBox(50,50,500,600,6);
        textBoxAdmin.setTextBoxUpdateTextByTouching(itemExplainTextBoxID, false);
        //textBoxAdmin.setTextBoxExists(itemExplainTextBoxID, false);
        textBoxAdmin.bookingDrawText(itemExplainTextBoxID, "ジオオブジェクト");
        textBoxAdmin.bookingDrawText(itemExplainTextBoxID, "MOP");
        textBoxAdmin.updateText(itemExplainTextBoxID);
        */

    }


    public void updata() {
        if (updateStopFlag) {
            return;
        }
        n++;


        if(n == 100) {
            /*
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
            expendInventry.save();
            */

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
        expendInventry.updata();
        //palette_admin.update(false);

        //textBoxAdmin.update();

    }

    public void draw() {
        if (drawStopFlag) {
            return;
        }

        graphic.bookingDrawBitmapData(backGround,0,0,1,1,0,255,true);
        /*
        equipmentInventry.draw();
        expendInventry.draw();
        palette_admin.draw();
        start_user_interface.draw();
        */
        graphic.bookingDrawText("Please Touch!",550,750, paint);
        //textBoxAdmin.draw();
        graphic.draw();
    }

    public void openingUpdate(){
        if (updateStopFlag) {
            return;
        }

        paint.setARGB(255,0,0,0);
        graphic.bookingDrawRect(0,0,1600,900,paint);


        paint.setARGB(alpha,255,255,255);
        alpha += up_down;
        if(alpha < 0 || alpha > 255){
            if(alpha < 0){
                messageNum++;
                if(messageNum == 6){
                    unitedActicity.getUnitedSurfaceView().setDownCount(1);
                    unitedActicity.getUnitedSurfaceView().setTouchWaitcount(0);
                }
            }
            up_down *= -1;
            alpha += up_down*2;
        }
        count++;

        switch (messageNum) {
            case 0:
                graphic.bookingDrawText("地脈を流れるエネルギー，ジオエネルギー．",100,480, paint);
                break;
            case 1:
                graphic.bookingDrawText("その加護を受ける者がいた．",350,480, paint);
                break;
            case 2:
                graphic.bookingDrawText("いま，地上は異次元からやってくる",100,440, paint);
                graphic.bookingDrawText("魔王によって平穏が脅かされていた．",350,520, paint);
                break;
            case 3:
                graphic.bookingDrawText("際限なく強くなって地上を支配しにくる魔王．",75,480, paint);
                break;
            case 4:
                graphic.bookingDrawText("これは，ジオをつかさどる女神",30,320, paint);
                graphic.bookingDrawText("ガイアと結託し，魔王の脅威を退け，",55,480, paint);
                graphic.bookingDrawText("平穏を取り戻すことを決意した者の物語である．",80,640, paint);
                break;
        }

        graphic.draw();

    }

    public void release() {
        System.out.println("takanoRelease : StartSur");
        paint = null;
        if (credis != null) {
            credis.release();
        }
    }


    boolean updateStopFlag = false;
    public void updateStop() {
        updateStopFlag = true;
    }

    boolean drawStopFlag = false;
    public void drawStop() {
        drawStopFlag = true;
    }
}