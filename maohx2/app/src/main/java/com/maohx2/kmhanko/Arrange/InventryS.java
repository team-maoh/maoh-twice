package com.maohx2.kmhanko.Arrange;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.Saver.InventrySaver;
import com.maohx2.kmhanko.Saver.SaveManager;
import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by user on 2018/04/08.
 */

public class InventryS extends Inventry {

    InventrySaver inventrySaver;

    public InventryS(InventrySaver _inventrySaver){
        inventrySaver = _inventrySaver;
        inventrySaver.setInventry(this);
    }

    public void init(UserInterface _user_interface, Graphic graphic, int left, int top, int right, int bottom, int _contentNum){
        super.init(_user_interface, graphic, left, top, right, bottom, _contentNum);
    }

    public void save() {
        inventrySaver.save();
    }

    public void load() {
        inventrySaver.load();
    }

    /*
    @Override
    public void addItemData(ItemData _itemData) {
        super.addItemData(_itemData);
        //inventrySaver.save();
    }

    @Override
    public void subItemData(ItemData _itemData) {
        super.subItemData(_itemData);
        //inventrySaver.save();
    }
    */

}
