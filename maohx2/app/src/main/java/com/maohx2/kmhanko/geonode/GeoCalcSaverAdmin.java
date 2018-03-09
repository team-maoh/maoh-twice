package com.maohx2.kmhanko.geonode;

/**
 * Created by user on 2017/10/20.
 */

import java.util.ArrayList;
import java.util.List;

public class GeoCalcSaverAdmin {
    List<GeoCalcSaver> geo_calc_saver = new ArrayList<GeoCalcSaver>();

    public GeoCalcSaverAdmin() {
        geo_calc_saver.add(new GeoCalcSaver("HP"));
        geo_calc_saver.add(new GeoCalcSaver("Attack"));
        geo_calc_saver.add(new GeoCalcSaver("Defence"));
        geo_calc_saver.add(new GeoCalcSaver("Luck"));
    }

    public GeoCalcSaver getGeoCalcSaver(String name) {
        for(int i = 0; i<geo_calc_saver.size(); i++) {
            if(geo_calc_saver.get(i).getName().equals(name)) {
                return geo_calc_saver.get(i);
            }
        }
        return null;
    }

    public List<GeoCalcSaver> getGeoCalcSaver() {
        return geo_calc_saver;
    }

    public void union(GeoCalcSaverAdmin _geo_calc_saver_admin) {
        //this;
        //_geo_calc_saver_admin;

        List<GeoCalcSaver> _geo_calc_saver = _geo_calc_saver_admin.getGeoCalcSaver();

        for(int i = 0; i < _geo_calc_saver.size(); i++) {
            geo_calc_saver.get(i).setPlusParam( _geo_calc_saver.get(i).getParam() );
            geo_calc_saver.get(i).setPlusTempParam( _geo_calc_saver.get(i).getTempParam() );
            geo_calc_saver.get(i).setPlusParamRateEnd( _geo_calc_saver.get(i).getParamRateEnd() - 1.0 );
            if (geo_calc_saver.get(i).isBonusContinue() && _geo_calc_saver.get(i).isBonusContinue()) {
                geo_calc_saver.get(i).setPlusParamRateEnd(0.10);
                geo_calc_saver.get(i).setBonusContinue(true);
            }
            if (_geo_calc_saver.get(i).isBonusContinue()) {
                geo_calc_saver.get(i).setBonusContinue(true);
            }
        }

    }

    public void finalCalc() {
        for(int i = 0; i<geo_calc_saver.size(); i++) {
            if(geo_calc_saver.get(i) != null) {
                geo_calc_saver.get(i).finalCalc();
            }
        }
    }

    //public String getParam(String name) { return name + " : " + getGeoCalcSaver(name).getParam(); }

    public int getParam(String name) {
        return getGeoCalcSaver(name).getParam();
    }

}