package com.maohx2.kmhanko.sound;

/**
 * Created by user on 2018/06/03.
 */

public class SoundData {
    String name;
    int id;

    public SoundData() {
        name = "";
        id = -1;
    }

    public SoundData(int _id, String _name) {
        name = _name;
        id = _id;
    }

    public int getID() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String x) {
        name = x;
    }

    public void setID(int x) {
        id = x;
    }


}
