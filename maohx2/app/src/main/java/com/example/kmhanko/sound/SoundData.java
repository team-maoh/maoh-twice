package com.kmhanko.sound;

/**
 * Created by user on 2017/09/10.
 */

public class SoundData {
    private int soundID;
    private String name;
    private int streamID;
    private String table_name;

    SoundData(){
        streamID = 0;
    }

    public int getStreamID() {
        return streamID;
    }
    public int getSoundID() {
        return soundID;
    }
    public String getName() {
        return name;
    }
    public String getTableName() { return table_name; }

    public void setStreamID(int _streamID) { streamID = _streamID; }
    public void setSoundID(int _soundID) {
        soundID = _soundID;
    }
    public void setName(String _name) { name = _name; }
    public void setTableName(String _table_name) { table_name = _table_name; }
}
