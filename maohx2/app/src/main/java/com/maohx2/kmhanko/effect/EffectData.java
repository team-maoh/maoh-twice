package com.maohx2.kmhanko.effect;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.myavail.MyAvail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/01/19.
 */

public class EffectData {
    String name; //エフェクトの名前。tableの名前と一致する
    int steps; //ステップの回数
    List<Integer> imageID = new ArrayList<Integer>();//登録されている画像の何番目か
    List<Integer> x = new ArrayList<Integer>(); //x座標。これは基準位置からのx座標で有ることに注意
    List<Integer> y = new ArrayList<Integer>(); //y座標。同上
    List<Float> extend_x = new ArrayList<Float>(); //拡大率x
    List<Float> extend_y = new ArrayList<Float>(); //拡大率y
    List<Float> angle = new ArrayList<Float>(); //回転角度
    List<Integer> alpha = new ArrayList<Integer>(); //透明度
    List<Integer> time = new ArrayList<Integer>(); //そのステップの状態でどの程度待機するか.
    List<Boolean> switch_gr = new ArrayList<Boolean>(); //ステップが次のステップに変化する時、徐々に変化する場合はTRUE
    List<Boolean> upLeft = new ArrayList<Boolean>(); //BookingDrawになげるためのもの
    List<String> soundName = new ArrayList<String>(); //登録されている鳴らす効果音name
    List<Integer> nextID = new ArrayList<Integer>(); //次のIDを示す。-1の場合は現在のID+1,

    public void release(){
        System.out.println("takanoRelease : EffectData");
        name = null;
        imageID = null;
        x = null;
        y = null;
        extend_x = null;
        extend_y = null;
        angle = null;
        alpha = null;
        time = null;
        switch_gr = null;
        upLeft = null;
        soundName = null;
        nextID = null;
    }

    public EffectData(MyDatabase database, String t_name) {
        loadDatabase(database, t_name);
    }

    public void loadDatabase(MyDatabase database, String t_name) {
        name = t_name;

        imageID = database.getInt(t_name, "imageID");
        nextID = database.getInt(t_name, "nextID");
        x = database.getInt(t_name, "x");
        y = database.getInt(t_name, "y");
        extend_x = database.getFloat(t_name, "extend_x");
        extend_y = database.getFloat(t_name, "extend_y");
        angle = database.getFloat(t_name, "angle");
        alpha = database.getInt(t_name, "alpha");
        time = database.getInt(t_name, "time");
        switch_gr = database.getBoolean(t_name, "switch_gr");
        upLeft = database.getBoolean(t_name, "is_up_left");
        soundName = database.getString(t_name, "soundName");
        steps = database.getSize(t_name);
    }

    public String getName() {
        return name;
    }


    public int getImageID(int i) {
        try {
            return imageID.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return -1;
        }
    }

    public int getX(int i) {
        try {
            return x.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public int getY(int i) {
        try {
            return y.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public float getExtendX(int i) {
        try {
            return extend_x.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public float getExtendY(int i) {
        try {
            return extend_y.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public float getAngle(int i) {
        try {
            return angle.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public int getAlpha(int i) {
        try {
            return alpha.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public int getTime(int i) {
        try {
            return time.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public int getNextID(int i) {
        try {
            return nextID.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public int getNextID(int i, int nowID) {
        int _nextID = nextID.get(i);
        if (_nextID == -1) {
            _nextID = nowID + 1;
        } else {
            _nextID--;
        }
        try {
            return _nextID;
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
        }
    }

    public int getSteps() {
        return steps;
    }

    public boolean isSwitchGr(int i) {
        try {
            return switch_gr.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return false;
        }
    }

    public boolean isUpLeft(int i) {
        try {
            return upLeft.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return false;
        }
    }

    public String getSoundName(int i) {
        if (soundName == null) {
            return null;
        }
        try {
            return soundName.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return null;
        }
    }

}
