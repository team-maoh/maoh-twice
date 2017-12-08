package com.maohx2.kmhanko.animation;

/**
 * Created by user on 2017/10/15.
 */

import java.util.ArrayList;
import java.util.List;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.myavail.MyAvail;

public class AnimationData {
    String name;
    int steps;
    List<Integer> id = new ArrayList<Integer>();
    List<Integer> x = new ArrayList<Integer>();
    List<Integer> y = new ArrayList<Integer>();
    List<Float> extend_x = new ArrayList<Float>();
    List<Float> extend_y = new ArrayList<Float>();
    List<Integer> angle = new ArrayList<Integer>();
    List<Integer> alpha = new ArrayList<Integer>();
    List<Integer> time = new ArrayList<Integer>();
    List<Boolean> switch_gr = new ArrayList<Boolean>();

    public AnimationData(MyDatabase database, String t_name) {
        loadDatabase(database, t_name);
    }

    public void loadDatabase(MyDatabase database, String t_name) {
        name = t_name;
        id = database.getInt(t_name, "id");
        x = database.getInt(t_name, "x");
        y = database.getInt(t_name, "y");
        extend_x = database.getFloat(t_name, "extend_x");
        extend_y = database.getFloat(t_name, "extend_y");
        angle = database.getInt(t_name, "angle");
        alpha = database.getInt(t_name, "alpha");
        time = database.getInt(t_name, "time");
        switch_gr = database.getBoolean(t_name, "switch_option");
        steps = database.getSize(t_name);
    }

    public String getName() {
        return name;
    }


    public int getID(int i) {
        try {
            return id.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return 0;
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

    public int getAngle(int i) {
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
}
