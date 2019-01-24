package com.maohx2.ina.Activity;

import com.maohx2.ina.Arrange.PaletteAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.itemdata.ExpendItemData;

/**
 * Created by user on 2019/01/24.
 */

public class DemoManager {

    boolean startGameDemoEndFlag = false;
    boolean worldGameDemoEndFlag = false;
    boolean dungeonGameDemoEndFlag = false;
    Graphic graphic;

    public DemoManager() {}

    public void startGameDemo() {
        if (startGameDemoEndFlag) {
            return;
        }
        startGameDemoEndFlag = true;
    }
    public void worldGameDemo(Graphic graphic, InventryS equipmentInventry, PaletteAdmin paletteAdmin, InventryS expendItemInventry, ItemDataAdminManager itemDataAdminManager) {
        if (worldGameDemoEndFlag) {
            return;
        }
        worldGameDemoEndFlag = true;

        EquipmentItemData tmpEquipmentItemData = new EquipmentItemData();

        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("剣");
        tmpEquipmentItemData.setImageName("剣");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("剣"));
        tmpEquipmentItemData.setPrice(6713);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.SWORD);
        tmpEquipmentItemData.setUseNum(80);
        tmpEquipmentItemData.setRadius(20);
        tmpEquipmentItemData.setDecayRate(0.92f);
        tmpEquipmentItemData.setTouchFrequency(8);
        tmpEquipmentItemData.setAutoFrequencyRate(1.3f);
        tmpEquipmentItemData.setAttack(959);
        tmpEquipmentItemData.setDefence(0);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,0);


        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("槍");
        tmpEquipmentItemData.setImageName("槍");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("槍"));
        tmpEquipmentItemData.setPrice(10003);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.SPEAR);
        tmpEquipmentItemData.setUseNum(60);
        tmpEquipmentItemData.setRadius(40);
        tmpEquipmentItemData.setDecayRate(0.94f);
        tmpEquipmentItemData.setTouchFrequency(4);
        tmpEquipmentItemData.setAutoFrequencyRate(1.4f);
        tmpEquipmentItemData.setAttack(1429);
        tmpEquipmentItemData.setDefence(0);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,1);


        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("銃");
        tmpEquipmentItemData.setImageName("銃");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("銃"));
        tmpEquipmentItemData.setPrice(8778);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.GUN);
        tmpEquipmentItemData.setUseNum(30);
        tmpEquipmentItemData.setRadius(30);
        tmpEquipmentItemData.setDecayRate(0.94f);
        tmpEquipmentItemData.setTouchFrequency(10);
        tmpEquipmentItemData.setAutoFrequencyRate(999999);
        tmpEquipmentItemData.setAttack(1254);
        tmpEquipmentItemData.setDefence(2);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,2);


        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("ナックル");
        tmpEquipmentItemData.setImageName("ナックル");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("ナックル"));
        tmpEquipmentItemData.setPrice(5698);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.BARE);
        tmpEquipmentItemData.setUseNum(80);
        tmpEquipmentItemData.setRadius(30);
        tmpEquipmentItemData.setDecayRate(0.93f);
        tmpEquipmentItemData.setTouchFrequency(10);
        tmpEquipmentItemData.setAutoFrequencyRate(999999);
        tmpEquipmentItemData.setAttack(814);
        tmpEquipmentItemData.setDefence(0);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,3);



        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("弓");
        tmpEquipmentItemData.setImageName("弓");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("弓"));
        tmpEquipmentItemData.setPrice(7987);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.BOW);
        tmpEquipmentItemData.setUseNum(50);
        tmpEquipmentItemData.setRadius(30);
        tmpEquipmentItemData.setDecayRate(0.95f);
        tmpEquipmentItemData.setTouchFrequency(6);
        tmpEquipmentItemData.setAutoFrequencyRate(1.1f);
        tmpEquipmentItemData.setAttack(1141);
        tmpEquipmentItemData.setDefence(2);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,4);



        tmpEquipmentItemData = new EquipmentItemData();
        tmpEquipmentItemData.setItemKind(Constants.Item.ITEM_KIND.EQUIPMENT);
        tmpEquipmentItemData.setName("杖");
        tmpEquipmentItemData.setImageName("杖");
        tmpEquipmentItemData.setItemImage(graphic.searchBitmap("杖"));
        tmpEquipmentItemData.setPrice(11732);
        tmpEquipmentItemData.setEquipmentKind(Constants.Item.EQUIPMENT_KIND.WAND);
        tmpEquipmentItemData.setUseNum(10);
        tmpEquipmentItemData.setRadius(70);
        tmpEquipmentItemData.setDecayRate(0.99f);
        tmpEquipmentItemData.setTouchFrequency(2);
        tmpEquipmentItemData.setAutoFrequencyRate(999999);
        tmpEquipmentItemData.setAttack(1676);
        tmpEquipmentItemData.setDefence(1);
        equipmentInventry.addItemData(tmpEquipmentItemData);
        paletteAdmin.setDebugEquipment(tmpEquipmentItemData,5);


        equipmentInventry.save();



        ExpendItemData tempExpendItemData = ((ExpendItemData)(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ポーション")));
        paletteAdmin.setDebugExpend(tempExpendItemData, 0);
        paletteAdmin.setDebugExpend(tempExpendItemData, 3);
        paletteAdmin.setDebugExpend(tempExpendItemData, 4);
        paletteAdmin.setDebugExpend(tempExpendItemData, 5);
        paletteAdmin.setDebugExpend(tempExpendItemData, 6);
        for(int j = 0; j < 7; j++){
            expendItemInventry.addItemData(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ポーション"));
        }

        tempExpendItemData = ((ExpendItemData)(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ハイポーション")));
        paletteAdmin.setDebugExpend(tempExpendItemData, 1);
        for(int j = 0; j < 3; j++){
            expendItemInventry.addItemData(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("ハイポーション"));
        }

        tempExpendItemData = ((ExpendItemData)(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("エクスポーション")));
        paletteAdmin.setDebugExpend(tempExpendItemData, 2);
        for(int j = 0; j < 1; j++){
            expendItemInventry.addItemData(itemDataAdminManager.getExpendItemDataAdmin().getOneDataByName("エクスポーション"));
        }


    }

    public void dungeonGameDemo() {
        if (dungeonGameDemoEndFlag) {
            return;
        }
        dungeonGameDemoEndFlag = true;
    }


    // プレイヤーステータス 書き換え
    public void playerStatusDemo() {
    }

    // 所持アイテム(消費)

    // アイテムスロットへのセット

    // 所持武器〇

    // 武器スロットへのセット〇

    // 所持ジオ〇

    // ジオスロットへのセット〇

    // マップ解放〇

    // ジオスロットの解放〇

    // 会話イベントのフラグ〇

    // 魔王討伐数

    // 献上ポイント

}
