package com.maohx2.horie.map;

/**
 * Created by horie on 2017/09/03.
 */

public class Chip {
    private boolean isWall;
    private boolean isStairs;
    private boolean isRoom;
    private boolean isDisp;
    Chip(){
        isWall = false;
        isStairs = false;
        isRoom = true;
        isDisp = false;
    }

    public void initializeChip(){
        isWall = false;
        isStairs = false;
        isRoom = true;
        isDisp = false;
    }

    //getter
    public boolean isWall(){
        return isWall;
    }
    public boolean isStairs(){
        return isStairs;
    }
    public boolean isRoom(){
        return isRoom;
    }
    public boolean isDisp(){
        return isDisp;
    }

    //setter
    public void setWallFlag(boolean m_wall_flag){
        isWall = m_wall_flag;
    }
    public void setStairsFlag(boolean m_stairs_flag){
        isStairs = m_stairs_flag;
    }
    public void setRoomFlag(boolean m_room_flag){
        isRoom = m_room_flag;
    }
    public void setDispFlag(boolean m_disp_flag){
        isDisp = m_disp_flag;
    }
}
