package com.maohx2.horie.Tutorial;

/**
 * Created by horie on 2018/07/31.
 */

public class TutorialFlagData {
    String flag_name[];
    int is_tutorial_finished[];

    public TutorialFlagData(){
        flag_name = new String[3];
        is_tutorial_finished = new int[3];
    }

    //getter
    public String getFlag_name(int num){
        return flag_name[num];
    }
    public int getIs_tutorial_finished(int num){
        return is_tutorial_finished[num];
    }


    //setter
    public void setFlag_name(String _flag_name, int num){
        flag_name[num] = _flag_name;
    }
    public void setIs_tutorial_finished(int _flag, int flag_num){
        is_tutorial_finished[flag_num] = _flag;
    }

    public String[] getSaveStatuses(int i) {
        return new String[] {
                flag_name[i],
                String.valueOf(is_tutorial_finished[i])
        };
    }
}
