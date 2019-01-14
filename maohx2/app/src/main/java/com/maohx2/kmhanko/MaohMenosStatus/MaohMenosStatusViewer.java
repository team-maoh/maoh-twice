package com.maohx2.kmhanko.MaohMenosStatus;

import android.graphics.Paint;

import com.maohx2.fuusya.MapPlateAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;

import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatusEffect;
import com.maohx2.kmhanko.PlayerStatus.StatusViewer;

/**
 * Created by user on 2019/01/14.
 */

public class MaohMenosStatusViewer extends StatusViewer {
    static final int TEXT_NUM = 3;

    static public final float EXPRESS_RATE = 222.22f;
    static final float EXPRESS_RATE2 = 222.2f;

    //毎回フレームの監視用
    float hp = 1;
    float attack = 1;
    float defence = 1;

    MaohMenosStatus maohMenosStatus;

    BitmapData maohIcon;

    int maohIconPosX = 170;
    int maohIconPosY = 780;
    float maohIconExtend  = 1.8f;


    public MaohMenosStatusViewer(Graphic _graphic, UserInterface _userInterface, MaohMenosStatus _maohMenosStatus) {
        super(_graphic, _userInterface, TEXT_NUM);
        maohMenosStatus = _maohMenosStatus;

        hp = maohMenosStatus.getGeoHP();
        attack = maohMenosStatus.getGeoAttack();
        defence = maohMenosStatus.getGeoDefence();

        maohIcon = graphic.searchBitmap("魔王ボタン");
    }

    public void initPosition() {
        posX1 = 230;
        posX2 = 1000;
        posY1 = 780;
        posY2 = 830;

        textXOffsetLeft1 = 65;
        textXOffsetLeft2 = 125;
        textSizeRate = 0.6f;

        sizeX = (posX2 - posX1)/textNum;
        sizeY = (posY2 - posY1);

        isExist = true;
        paint = new Paint();
        paint.setTextSize(sizeY * textSizeRate);
        boxPaint = new Paint();
        boxPaint.setARGB(128,0,0,0);

        statusIcon[0] = graphic.searchBitmap("status_hp");
        statusIcon[1] = graphic.searchBitmap("剣");
        statusIcon[2] = graphic.searchBitmap("盾");
        //statusIcon[3] = graphic.searchBitmap("status_luck");

    }

    public void initStatusPlate() {
        statusPlate = new PlateGroup<BoxPlate>(
                new BoxPlate[] {
                        new BoxPlate(
                                graphic,
                                userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                posX1, posY1, posX2, posY2
                        ) {
                            @Override
                            public void draw() {
                                for (int i = 0; i < TEXT_NUM ; i++) {
                                    String statusName = "";
                                    String statusFigure = "";

                                    switch(i) {
                                        case 0:
                                            statusName = "HP";
                                            statusFigure = String.format("-%.1f",maohMenosStatus.getGeoHP()/EXPRESS_RATE);
                                            paint.setARGB(255,128,255,255);
                                            break;
                                        case 1:
                                            statusName = "Atk";
                                            statusFigure = String.format("-%.1f",maohMenosStatus.getGeoAttack()/EXPRESS_RATE);
                                            paint.setARGB(255,255,128,128);
                                            break;
                                        case 2:
                                            statusName = "Def";
                                            statusFigure = String.format("-%.1f",maohMenosStatus.getGeoDefence()/EXPRESS_RATE2);
                                            paint.setARGB(255,128,128,255);
                                            break;
                                    }
                                    graphic.bookingDrawBitmapData(statusIcon[i],posX1 + sizeX * i , posY1, 1.5f, 1.5f,0,255, true);
                                    graphic.bookingDrawText(statusName,posX1 + sizeX * i + textXOffsetLeft1, posY1 + (int)((sizeY + sizeY * textSizeRate)/2.0f), paint);
                                    graphic.bookingDrawText(statusFigure,posX1 + sizeX * i + textXOffsetLeft2, posY1 + (int)((sizeY + sizeY * textSizeRate)/2.0f), paint);
                                }
                            }
                        }
                }
        );
    }

    public void update() {
        if (!isExist) {
            return;
        }
        //statusPlate.update();あえて呼ばない
        float parameter;
        if (maohMenosStatus.getEffectFlag()) {
            System.out.println("StatusEffectFlag = True");
            if ((parameter = maohMenosStatus.getGeoHP() - hp) != 0 && hp != 1) {
                makeStatusEffect(0, parameter/EXPRESS_RATE, true);
            }
            if ((parameter = maohMenosStatus.getGeoAttack() - attack) != 0 && attack != 1) {
                makeStatusEffect(1, parameter/EXPRESS_RATE, true);
            }
            if ((parameter = maohMenosStatus.getGeoDefence() - defence) != 0 && defence != 1) {
                makeStatusEffect(2, parameter/EXPRESS_RATE2, true);
            }
            hp = maohMenosStatus.getGeoHP();
            attack = maohMenosStatus.getGeoAttack();
            defence = maohMenosStatus.getGeoDefence();

            maohMenosStatus.setEffectFlag(false);
        }


        for (int i = 0; i < statusEffect.length; i++) {
            statusEffect[i].update();
        }
    }

    @Override
    public void draw() {
        super.draw();
        graphic.bookingDrawBitmapData(maohIcon, maohIconPosX, maohIconPosY, maohIconExtend, maohIconExtend, 0, 255, false);
    }

    public void Existis(boolean f) {
        isExist = f;
    }

    public boolean isExist() {
        return isExist;
    }

    @Override
    public void release() {
        super.release();
    }
}
