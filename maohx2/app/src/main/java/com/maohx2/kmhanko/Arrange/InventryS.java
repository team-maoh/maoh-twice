package com.maohx2.kmhanko.Arrange;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.InventrySaver;
import com.maohx2.kmhanko.database.SaveManager;

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

}
