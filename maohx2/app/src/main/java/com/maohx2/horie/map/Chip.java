package com.maohx2.horie.map;

/**
 * Created by horie on 2017/09/03.
 */

public class Chip {
    private boolean isWall;
    private boolean isStairs;
    Chip(){
        isWall = false;
        isStairs = false;
    }

    //getter
    public boolean isWall(){
        return isWall;
    }
    public boolean isStairs(){
        return isStairs;
    }

    //setter
    public void setWallFlag(boolean m_wall_flag){
        isWall = m_wall_flag;
    }
    public void setStairsFlag(boolean m_stairs_flag){
        isStairs = m_stairs_flag;
    }
}
