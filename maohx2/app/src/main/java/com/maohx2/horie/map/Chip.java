package com.maohx2.horie.map;

/**
 * Created by horie on 2017/09/03.
 */

public class Chip {
    private boolean isWall;
    private boolean isStairs;
    private boolean isRoom;
    private boolean isDisp;
    private boolean isEntrance;
    private boolean isMine;
    private boolean isGate;
    private boolean isAccessory;

    private int[] accsessory;
    Chip(){
        isWall = false;
        isStairs = false;
        isRoom = true;
        isDisp = false;
        isEntrance = false;
        isMine = false;
        isGate = false;
        accsessory = new int[4];
    }

    public void initializeChip(){
        isWall = false;
        isStairs = false;
        isRoom = true;
        isDisp = false;
        isEntrance = false;
        isMine = false;
        isGate = false;
        for (int i = 0; i < accsessory.length; i++) {
            accsessory[i] = -1;
        }
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
    public boolean isEntrance(){
        return isEntrance;
    }
    public boolean isMine(){
        return isMine;
    }
    public boolean isGate(){
        return isGate;
    }
    public boolean isAccessory() { return isAccessory; }
    public int[] getAccessory() { return accsessory; }

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
    public void setEntranceFlag(boolean m_entrance_flag){
        isEntrance = m_entrance_flag;
    }
    public void setMineFlag(boolean m_mine_flag){
        isMine = m_mine_flag;
    }
    public void setGateFlag(boolean m_gate_flag){
        isGate = m_gate_flag;
    }
    public void setAccessoryFlag(boolean m_accessory_flag){
        isAccessory = m_accessory_flag;
    }
    public void setAccessory(int[] m_accsessory) { accsessory = m_accsessory; }
    public void setAccessory(int id, int m_accsessory) { accsessory[id] = m_accsessory; }
}
