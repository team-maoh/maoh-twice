package com.maohx2.kmhanko.myavail;


import java.lang.Math;
/**
 * Created by user on 2017/10/15.
 */

public class MyAvail {
    static public void errorMes(Exception e) {
        StackTraceElement[] ste = e.getStackTrace();
        throw new Error(e.getClass().getName() + ": "+ e.getMessage() + "\tat "+ ste[ste.length-1]);
    }

    static public double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    static public int maxFromIntArrays(int x[]) {
        int max = x[0];
        int maxID = 0;
        for(int i = 1; i < x.length; i++) {
            if (x[i] > max) {
                max = x[i];
                maxID = i;
            }
        }
        return maxID;
    }
}
