package com.maohx2.kmhanko.myavail;

/**
 * Created by user on 2017/10/15.
 */

public class MyAvail {
    static public void errorMes(Exception e) {
        StackTraceElement[] ste = e.getStackTrace();
        throw new Error(e.getClass().getName() + ": "+ e.getMessage() + "\tat "+ ste[ste.length-1]);
    }
}
