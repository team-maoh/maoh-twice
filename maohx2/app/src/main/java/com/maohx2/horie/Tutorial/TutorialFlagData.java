package com.maohx2.horie.Tutorial;

/**
 * Created by horie on 2018/07/31.
 */

public class TutorialFlagData {
    String flag_name[];
    int is_tutorial_finished[];

    public TutorialFlagData(){
        flag_name = new String[9];
        is_tutorial_finished = new int[9];
    }

    //getter
    public String getFlag_name(int num){
        return flag_name[num];
    }
    public int getIs_tutorial_finished(int num){
        return is_tutorial_finished[num];
    }

    public boolean getIsTutorialFinishedByName(String name) {
        for(int i = 0; i < flag_name.length; i++) {
            if (name.equals(flag_name[i])) {
                return is_tutorial_finished[i] == 1;
            }
        }
        return false;
    }

    //setter
    public void setFlag_name(String _flag_name, int num){//0:equip, 1:shop, 2:sell, 3:geo
        flag_name[num] = _flag_name;
    }
    public void setIs_tutorial_finished(int _flag, int flag_num){
        is_tutorial_finished[flag_num] = _flag;
    }
    public boolean searchTutorialName(String name) {
        for (int i = 0; i < flag_name.length; i++) {
            if (flag_name[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void setIsTutorialFinishedByName(int flag, String name) {
        for(int i = 0; i < flag_name.length; i++) {
            if (flag_name[i].equals(name)) {
                is_tutorial_finished[i] = flag;
                return;
            }
        }
        System.out.println("TutorialFlagData: setIsTutorialFinishedByName: 記録失敗: " + name);
        for(int i = 0; i < flag_name.length; i++) {
            System.out.println("TutorialFlagData: setIsTutorialFinishedByName: " + flag_name[i]);
        }
    }

    public void tutorialResetAll() {
        for (int i = 0; i < is_tutorial_finished.length; i++) {
            is_tutorial_finished[i] = 0;
        }

    }

    public String[] getSaveStatuses(int i) {
        return new String[] {
                flag_name[i],
                String.valueOf(is_tutorial_finished[i])
        };
    }

    public void release() {
        for(int i = 0; i < flag_name.length; i++) {
            if (flag_name[i] != null) {
                flag_name[i] = null;
            }
        }
        is_tutorial_finished = null;
    }
}
