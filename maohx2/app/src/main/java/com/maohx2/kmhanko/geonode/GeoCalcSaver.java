package com.maohx2.kmhanko.geonode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/10/20.
 */

public class GeoCalcSaver {
    String name;
    int param;
    int param_temp;
    boolean is_bonus_start;

    List<Double> param_rate = new ArrayList<Double>();

    public GeoCalcSaver(String _name) {
        setName(_name);
        param = 0;
        clearTempParam();
        addParamRate(1.0);
        is_bonus_start = true;
    }

    public String getName() {
        return name;
    }
    public int getParam() {
        return param;
    }
    public int getTempParam() {
        return param_temp;
    }
    public boolean isBonusStart() { return is_bonus_start; }
    public boolean isBonusContinue() { return !is_bonus_start; }

    public void setName(String _name) {
        name = _name;
    }
    public void setParam(int _param) {
        param = _param;
    }
    public void setPlusParam(int _param) {
        param += _param;
    }
    public void setTempParam(int _param_temp) {
        param_temp = _param_temp;
    }
    public void setBonusStart(boolean _is_bonus_start) {
        is_bonus_start = _is_bonus_start;
    }
    public void setBonusContinue(boolean _is_bonus_continue) {
        is_bonus_start = !_is_bonus_continue;
    }

    public void clearTempParam() {
        param_temp = 0;
    }

    public void setPlusTempParam(int _param_temp) {
        param_temp += _param_temp;
    }
    public void setMagniTempParam(double _param_rate) {
        param_temp = (int)((double)param_temp * _param_rate);
    }


    public void applyTempParamToParam() {
        param += param_temp;
    }

    public List<Double> getParamRate() {
        return param_rate;
    }

    public double getParamRateEnd() {
        return param_rate.get(param_rate.size() - 1);
    }

    public void addParamRate(double _param) {
        param_rate.add(_param);
    }

    public void setParamRate(int i, double _param) {
        param_rate.set(i, _param);
    }

    public void setPlusParamRate(int i, double _param) {
        param_rate.set(i, param_rate.get(i) + _param);
    }

    public void setPlusParamRateEnd(double _param) {
        param_rate.set(param_rate.size() - 1, getParamRateEnd() + _param);
    }

    public void calc(int _param, double _param_rate) {
        if (_param <= 0 && _param_rate <= 1.0) { //ボーナス終了
            is_bonus_start = true;
            finalCalc();
        } else {
            if (is_bonus_start) {//これが一つ目である
                is_bonus_start = false;
            } else {
                setPlusParamRateEnd(0.10);
            }
        }

        setPlusTempParam(_param);
        setMagniTempParam(_param_rate);
    }

    public void finalCalc() {
        setMagniTempParam(getParamRateEnd());
        applyTempParamToParam();
        clearTempParam();
        addParamRate(1.0);
    }


}

/*
基本ボーナスありとして、
その値がないノードに来た段階でボーナスの清算をする

合体するときは、
 */