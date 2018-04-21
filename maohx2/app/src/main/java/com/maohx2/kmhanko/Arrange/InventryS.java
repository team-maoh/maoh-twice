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

    public InventryS(UserInterface _user_interface, Graphic graphic, InventrySaver _inventrySaver){
        super(_user_interface, graphic);
        inventrySaver = _inventrySaver;
        inventrySaver.setInventry(this);
    }

    public void save() {
        inventrySaver.save();
    }

    public void load() {
        inventrySaver.load();
    }

    @Override
    public void addItemData(ItemData _itemData) {
        super.addItemData(_itemData);
        inventrySaver.save();
    }

    @Override
    public void subItemData(ItemData _itemData) {
        super.subItemData(_itemData);
        inventrySaver.save();
    }

}
