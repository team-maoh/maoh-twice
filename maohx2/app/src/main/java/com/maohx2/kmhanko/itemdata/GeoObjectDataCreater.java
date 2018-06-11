package com.maohx2.kmhanko.itemdata;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_NORMAL;
import com.maohx2.ina.Constants.Item.GEO_PARAM_KIND_RATE;

import java.lang.Math;
import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;

/**
 * Created by user on 2018/04/22.
 */

public class GeoObjectDataCreater {
    static Graphic graphic;

    private GeoObjectDataCreater() {
    }

    ;

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

    /*
    public static GeoObjectData getGeoObjectData(int[] status1, double[] status2) {

        //String[] names = getNamesNormal(parameterKind, calcParam);
        //String name = names[0];
        //String imageName = names[1];


        //GeoObjectData newGeoObjectData = new GeoObjectData(name, graphic.searchBitmap(imageName),status1, status2);
        //newGeoObjectData.setImageName(imageName);
        //newGeoObjectData.setPrice(0);

        //return newGeoObjectData;
        return null;
    }
    */
    public static GeoObjectData getGeoObjectData(int parameter, Constants.Item.GEO_KIND_ALL parameterKind) {
        switch(parameterKind) {
            case HP:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_NORMAL.HP);
            case HP_RATE:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_RATE.HP_RATE);
            case ATTACK:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_NORMAL.ATTACK);
            case ATTACK_RATE:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_RATE.ATTACK_RATE);
            case DEFENCE:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_NORMAL.DEFENCE);
            case DEFENCE_RATE:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_RATE.DEFENCE_RATE);
            case LUCK:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_NORMAL.LUCK);
            case LUCK_RATE:
                return getGeoObjectData(parameter, GEO_PARAM_KIND_RATE.LUCK_RATE);
        }
        return null;
    }


    public static GeoObjectData getGeoObjectData(int parameter, GEO_PARAM_KIND_NORMAL parameterKind) {
        Random random = new Random();// by fuusya
        return getGeoObjectData(parameter, parameterKind, true);
    }


    public static GeoObjectData getGeoObjectData(int parameter, GEO_PARAM_KIND_NORMAL parameterKind, boolean randFlag) {
        int[] status1 = new int[GEO_PARAM_KIND_NORMAL.NUM.ordinal()];
        double[] status2 = new double[GEO_PARAM_KIND_RATE.NUM.ordinal()];
        status2[0] = 1.0;
        status2[1] = 1.0;
        status2[2] = 1.0;
        status2[3] = 1.0;

        int calcParam = 0;

        /*
        if (randFlag) {
//            calcParam = status1[parameterKind.ordinal()] = parameter / 2 + (int) ((double) parameter * Math.random());//
            switch (parameterKind) {
                case HP:
                    parameter = parameter / 4;
                    //この時点で param = 100 だと思う
                    parameter = parameter * 40000 / 5000;
                    break;
                case ATTACK:
                    //この時点で param = 100 だと思う
                    parameter = parameter * 200 / 5000;
                    break;
                case DEFENCE:
                    parameter = parameter * 2;
                    //この時点で param = 100 だと思う
                    parameter = parameter * 10000 / 5000;
                    break;
                default:
                    //TODO LUCK
                    break;
            }
            double random_num = sqrt(-2 * log(Math.random())) * cos(2 * PI * Math.random()) / 20.0;// だいたい[-1, +1]の範囲の正規分布
            calcParam = status1[parameterKind.ordinal()] = (int) (parameter * (1.0 + random_num) / 50);
        } else {
            calcParam = status1[parameterKind.ordinal()] = parameter;
        }*/


        if (randFlag) {
//            calcParam = status1[parameterKind.ordinal()] = parameter / 2 + (int) ((double) parameter * Math.random());//
//            switch (parameterKind) {
//                case HP:
//                    parameter = parameter / 4;
//                    //この時点で param = 100 だと思う
//                    parameter = parameter * 40000 / 5000;
//                    break;
//                case ATTACK:
//                    //この時点で param = 100 だと思う
//                    parameter = parameter * 200 / 5000;
//                    break;
//                case DEFENCE:
//                    parameter = parameter * 2;
//                    //この時点で param = 100 だと思う
//                    parameter = parameter * 10000 / 5000;
//                    break;
//                default:
//                    //TODO LUCK
//                    break;
//            }
            double random_num = sqrt(-2 * log(Math.random())) * cos(2 * PI * Math.random());// だいたい[-1, +1]の範囲の正規分布
//            calcParam = status1[parameterKind.ordinal()] = (int) (parameter * (1.0 + random_num) / 50);
            calcParam = status1[parameterKind.ordinal()] = (int) (parameter * (1.0 + random_num / 20.0) * 15.0 / 1000.0);//20180610に調整
        } else {
            calcParam = status1[parameterKind.ordinal()] = parameter;
        }


        String[] names = getNamesNormal(parameterKind, calcParam);
        String name = names[0];
        String imageName = names[1];

        Constants.Item.GEO_KIND_ALL geoKind = null;
        switch (parameterKind) {
            case HP:
                geoKind = Constants.Item.GEO_KIND_ALL.HP;
                break;
            case ATTACK:
                geoKind = Constants.Item.GEO_KIND_ALL.ATTACK;
                break;
            case DEFENCE:
                geoKind = Constants.Item.GEO_KIND_ALL.DEFENCE;
                break;
            case LUCK:
                geoKind = Constants.Item.GEO_KIND_ALL.LUCK;
                break;
        }

        GeoObjectData newGeoObjectData = new GeoObjectData(name, graphic.searchBitmap(imageName), status1, status2);
        newGeoObjectData.setImageName(imageName);
        newGeoObjectData.setPrice(calcParam);
        newGeoObjectData.setGeoKind(geoKind);
        //newGeoObjectData.setItemKind(Constants.Item.ITEM_KIND.GEO);
        //newGeoObjectData.setPrice(parameter);

        return newGeoObjectData;
    }

    public static GeoObjectData getGeoObjectData(int[] status1, double[] status2) {
        if (status1[0] != 0) {
            return getGeoObjectData(status1[0], GEO_PARAM_KIND_NORMAL.HP, false);
        }
        if (status1[1] != 0) {
            return getGeoObjectData(status1[1], GEO_PARAM_KIND_NORMAL.ATTACK, false);
        }
        if (status1[2] != 0) {
            return getGeoObjectData(status1[2], GEO_PARAM_KIND_NORMAL.DEFENCE, false);
        }
        if (status1[3] != 0) {
            return getGeoObjectData(status1[3], GEO_PARAM_KIND_NORMAL.LUCK, false);
        }
        if (status1[0] != 1.0) {
            return getGeoObjectData(status2[0], GEO_PARAM_KIND_RATE.HP_RATE, false);
        }
        if (status1[1] != 1.0) {
            return getGeoObjectData(status2[1], GEO_PARAM_KIND_RATE.ATTACK_RATE, false);
        }
        if (status1[2] != 1.0) {
            return getGeoObjectData(status2[2], GEO_PARAM_KIND_RATE.DEFENCE_RATE, false);
        }
        if (status1[3] != 1.0) {
            return getGeoObjectData(status2[3], GEO_PARAM_KIND_RATE.LUCK_RATE, false);
        }
        throw new Error("☆タカノ : GeoObjectDataCreater#getNames : 数値が不適切");
    }


    public static GeoObjectData getGeoObjectData(double parameter, GEO_PARAM_KIND_RATE parameterKind) {
        return getGeoObjectData(parameter, parameterKind, true);
    }

    public static GeoObjectData getGeoObjectData(double parameter, GEO_PARAM_KIND_RATE parameterKind, boolean randFlag) {
        int[] status1 = new int[GEO_PARAM_KIND_NORMAL.NUM.ordinal()];
        double[] status2 = new double[GEO_PARAM_KIND_RATE.NUM.ordinal()];
        status2[0] = 1.0;
        status2[1] = 1.0;
        status2[2] = 1.0;
        status2[3] = 1.0;
        double calcParam;
/*
        double calcParam;
        if (randFlag) {
//            calcParam = status2[parameterKind.ordinal()] = 1.0 + ((double) parameter / 2 + (double) parameter * Math.random()) / 100.0;
            switch (parameterKind) {
                case HP_RATE:
                    parameter = parameter / 4;
                    //この時点で param = 100 だと思う
                    break;
                case ATTACK_RATE:
                    //この時点で param = 100 だと思う
                    break;
                case DEFENCE_RATE:
                    parameter = parameter * 2;
                    //この時点で param = 100 だと思う
                    break;
                default:
                    break;
            }
            double random_num = sqrt(-2 * log(Math.random())) * cos(2 * PI * Math.random()) / 20.0;// だいたい[-1, +1]の範囲の正規分布
            calcParam = status2[parameterKind.ordinal()] = parameter * (1.0 + random_num) / 100.0;
        } else {
            calcParam = status2[parameterKind.ordinal()] = parameter;
        }*/

        if (randFlag) {
//            double random_num = sqrt(-2 * log(Math.random())) * cos(2 * PI * Math.random()) / 20.0;// だいたい[-1, +1]の範囲の正規分布
//            calcParam = status2[parameterKind.ordinal()] = parameter * (1.0 + random_num) / 100.0;
            calcParam = status2[parameterKind.ordinal()] = (1.1 + 0.9 * Math.random());// * 2.0;//1.1 ~ 2.0倍
        } else {
            calcParam = status2[parameterKind.ordinal()] = parameter;
        }

        String[] names = getNamesRate(parameterKind, calcParam);
        String name = names[0];
        String imageName = names[1];

        Constants.Item.GEO_KIND_ALL geoKind = null;
        switch (parameterKind) {
            case HP_RATE:
                geoKind = Constants.Item.GEO_KIND_ALL.HP_RATE;
                break;
            case ATTACK_RATE:
                geoKind = Constants.Item.GEO_KIND_ALL.ATTACK_RATE;
                break;
            case DEFENCE_RATE:
                geoKind = Constants.Item.GEO_KIND_ALL.DEFENCE_RATE;
                break;
            case LUCK_RATE:
                geoKind = Constants.Item.GEO_KIND_ALL.LUCK_RATE;
                break;
        }

        GeoObjectData newGeoObjectData = new GeoObjectData(name, graphic.searchBitmap(imageName), status1, status2);
        newGeoObjectData.setImageName(imageName);
        newGeoObjectData.setPrice((int) calcParam * 100);
        newGeoObjectData.setGeoKind(geoKind);
        //newGeoObjectData.setItemKind(Constants.Item.ITEM_KIND.GEO);
        //newGeoObjectData.setPrice(parameter);

        return newGeoObjectData;
    }

    public static boolean compare(GeoObjectData geoObjectData1, GeoObjectData geoObjectData2) {
        if (
                        geoObjectData1.getHp() >= geoObjectData2.getHp() &&
                        geoObjectData1.getAttack() >= geoObjectData2.getAttack() &&
                        geoObjectData1.getDefence() >= geoObjectData2.getDefence() &&
                        geoObjectData1.getLuck() >= geoObjectData2.getLuck() &&
                        geoObjectData1.getHpRate() >= geoObjectData2.getHpRate() &&
                        geoObjectData1.getAttackRate() >= geoObjectData2.getAttackRate() &&
                        geoObjectData1.getDefenceRate() >= geoObjectData2.getDefenceRate() &&
                        geoObjectData1.getLuckRate() >= geoObjectData2.getLuckRate()
                ) {
            return true;
        } else {
            return false;
        }
    }

    public static GEO_PARAM_KIND_NORMAL getRandKindNormal() {
        return GEO_PARAM_KIND_NORMAL.toEnum((int) (GEO_PARAM_KIND_NORMAL.NUM.ordinal() * Math.random()));
    }

    public static GEO_PARAM_KIND_RATE getRandKindRate() {
        return GEO_PARAM_KIND_RATE.toEnum((int) (GEO_PARAM_KIND_RATE.NUM.ordinal() * Math.random()));
    }


    private static String[] getNamesNormal(GEO_PARAM_KIND_NORMAL parameterKind, int calcParam) {

        int imageNum = 1;
        for (int i = 7; i >= 0; i--) {
            if (calcParam > Math.pow(10, i)) {
                imageNum = i + 1;
                break;
            }
        }

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

        return new String[]{name, imageName};
    }

    private static String[] getNamesRate(GEO_PARAM_KIND_RATE parameterKind, double calcParam) {

        int imageNum = 1;
        for (int i = 2; i >= 0; i--) {
            if (calcParam > 1.1f + (float)i * 0.3f) {
                imageNum = i + 1;
                break;
            }
        }

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
        imageName += "1" + String.valueOf(imageNum);
        name += String.format("%.2f", calcParam);


        return new String[]{name, imageName};
    }
}
