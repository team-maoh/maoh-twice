package com.example.horie.map;

/**
 * Created by horie on 2017/09/03.
 */

public class Chip {
    private boolean isWall;
    Chip(){
        isWall = false;
    }

    //getter
    public boolean isWall(){
        return isWall;
    }

    //setter
    public void setWallFlag(boolean m_wall_flag){
        isWall = m_wall_flag;
    }

}
