package com.maohx2.kmhanko.itemdata;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_NORMAL;
import com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_RATE;
import java.lang.Math;

/**
 * Created by user on 2018/04/22.
 */

public class GeoObjectDataCreater {
    static Graphic graphic;

    private GeoObjectDataCreater() {};

    public static void setGraphic(Graphic _graphic) {
        graphic = _graphic;
    }
    public static GeoObjectData getGeoObjectData(int parameter) {
        if (Math.random() < 0.5) {
            return getGeoObjectData(parameter, getRandKindNormal());
        } else {
            return getGeoObjectData(parameter, getRandKindRate());
        }
    }
    public static GeoObjectData getGeoObjectData(int parameter, GEO_PARAM_KIND_NORMAL parameterKind) {
        int[] status1 = new int[GEO_PARAM_KIND_NORMAL.NUM.ordinal()];
        double[] status2 = new double[GEO_PARAM_KIND_RATE.NUM.ordinal()];

        int calcParam = status1[parameterKind.ordinal()] = parameter/2 + (int)((double)parameter * Math.random());

        int imageNum = 1;
        for (int i = 6; i >= 2; i--) {
            if (calcParam > Math.pow(10,i)) {
                imageNum = i - 1;
                break;
            }
        }

        GeoObjectData newGeoObjectData = new GeoObjectData(status1, status2);
        String imageName = null;
        String name = null;
        switch (parameterKind) {
            case HP:
                imageName = "HpGeo";
                name = "体力ジオ";
                break;
            case ATTACK:
                imageName = "AttackGeo";
                name = "攻撃ジオ";
                break;
            case DEFENCE:
                imageName = "DefenceGeo";
                name = "防御ジオ";
                break;
            case LUCK:
                imageName = "LuckGeo";
                name = "運命ジオ";
                break;
        }
        imageName += "0" + String.valueOf(imageNum);
        name += calcParam;

        newGeoObjectData.setName(name);
        newGeoObjectData.setItemImage(graphic.searchBitmap(imageName));

        return newGeoObjectData;
    }

    public static GeoObjectData getGeoObjectData(int parameter, GEO_PARAM_KIND_RATE parameterKind) {
        int[] status1 = new int[GEO_PARAM_KIND_NORMAL.NUM.ordinal()];
        double[] status2 = new double[GEO_PARAM_KIND_RATE.NUM.ordinal()];

        double calcParam = status2[parameterKind.ordinal()] = 1.0 + ((double)parameter/2 + (double)parameter * Math.random())/ 20.0;

        int imageNum = 6;

        GeoObjectData newGeoObjectData = new GeoObjectData(status1, status2);
        String imageName = null;
        String name = null;
        switch (parameterKind) {
            case HP_RATE:
                imageName = "HpGeo";
                name = "体力倍加ジオ";
                break;
            case ATTACK_RATE:
                imageName = "AttackGeo";
                name = "攻撃倍加ジオ";
                break;
            case DEFENCE_RATE:
                imageName = "DefenceGeo";
                name = "防御倍加ジオ";
                break;
            case LUCK_RATE:
                imageName = "LuckGeo";
                name = "運命倍加ジオ";
                break;
        }
        imageName += "0" + String.valueOf(imageNum);
        name += calcParam;

        newGeoObjectData.setName(name);
        newGeoObjectData.setItemImage(graphic.searchBitmap(imageName));

        return newGeoObjectData;
    }

    private static GEO_PARAM_KIND_NORMAL getRandKindNormal() {
        return GEO_PARAM_KIND_NORMAL.toEnum((int)(GEO_PARAM_KIND_NORMAL.NUM.ordinal() * Math.random()));
    }

    private static GEO_PARAM_KIND_RATE getRandKindRate() {
        return GEO_PARAM_KIND_RATE.toEnum((int)(GEO_PARAM_KIND_RATE.NUM.ordinal() * Math.random()));
    }

}
