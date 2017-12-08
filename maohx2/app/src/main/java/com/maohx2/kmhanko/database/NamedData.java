package com.maohx2.kmhanko.database;

/**
 * Created by user on 2017/11/05.
 */

public abstract class NamedData {
    String name;
    //Getter
    public String getName() { return name; }
    //Setter
    public void setName(String _name) {
        name = _name;
    }
}
