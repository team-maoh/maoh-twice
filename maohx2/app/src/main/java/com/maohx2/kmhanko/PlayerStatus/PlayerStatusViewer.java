package com.maohx2.kmhanko.PlayerStatus;


import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.Text.Plate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by user on 2018/04/23.
 */

public class PlayerStatusViewer extends StatusViewer {
    static final int TEXT_NUM = 5;

    static public final float EXPRESS_RATE = 222.22f;
    static final float EXPRESS_RATE2 = 222.2f;

    //毎回フレームの監視用
    float hp = 0;
    float attack = 0;
    float defence = 0;
    float luck = 0;
    float money = 0;

    PlayerStatus playerStatus;

    public PlayerStatusViewer(Graphic _graphic, UserInterface _userInterface, PlayerStatus _playerStatus) {
        super(_graphic, _userInterface, TEXT_NUM);
        playerStatus = _playerStatus;

        hp = playerStatus.getHP();
        attack = playerStatus.getAttack();
        defence = playerStatus.getDefence();
        luck = playerStatus.getLuck();
        money = playerStatus.getMoney();
    }

    public void initPosition() {
        posX1 = 0;
        posX2 = 1600;
        posY1 = 850;
        posY2 = 900;

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
        statusIcon[3] = graphic.searchBitmap("status_luck");
        statusIcon[4] = graphic.searchBitmap("status_money");

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
                                            statusFigure = String.format("%.1f",playerStatus.getHP()/EXPRESS_RATE);
                                            paint.setARGB(255,128,255,255);
                                            break;
                                        case 1:
                                            statusName = "Atk";
                                            statusFigure = String.format("%.1f",playerStatus.getAttack()/EXPRESS_RATE);
                                            paint.setARGB(255,255,128,128);
                                            break;
                                        case 2:
                                            statusName = "Def";
                                            statusFigure = String.format("%.1f",playerStatus.getDefence()/EXPRESS_RATE2);
                                            paint.setARGB(255,128,128,255);
                                            break;
                                        case 3:
                                            statusName = "Luc";
                                            statusFigure = String.format("%.1f",playerStatus.getLuck()/EXPRESS_RATE);
                                            paint.setARGB(255,255,128,255);
                                            break;
                                        case 4:
                                            statusName = String.valueOf(playerStatus.getMoney() + " Maon");
                                            statusFigure = "";
                                            paint.setARGB(255,255,255,255);
                                            break;
                                    }

                                    //graphic.bookingDrawText(statusName,posX1 + TEXT_X_OFFSET_LEFT1, posY1 + sizeY * i + sizeY, paint);
                                    //graphic.bookingDrawText(statusFigure,posX1 + TEXT_X_OFFSET_LEFT1 + TEXT_X_OFFSET_LEFT2, posY1 + sizeY * i + sizeY, paint);

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
        if (playerStatus.getEffectFlag()) {
            System.out.println("StatusEffectFlag = True");
            if ((parameter = playerStatus.getHP() - hp) != 0 && hp != 0) {
                makeStatusEffect(0, parameter/EXPRESS_RATE, true);
            }
            if ((parameter = playerStatus.getAttack() - attack) != 0 && attack != 0) {
                makeStatusEffect(1, parameter/EXPRESS_RATE, true);
            }
            if ((parameter = playerStatus.getDefence() - defence) != 0 && defence != 0) {
                makeStatusEffect(2, parameter/EXPRESS_RATE2, true);
            }
            if ((parameter = playerStatus.getLuck() - luck) != 0 && luck != 0) {
                makeStatusEffect(3, parameter/EXPRESS_RATE, true);
            }
            if ((parameter = playerStatus.getMoney() - money) != 0 && money != 0) {
                makeStatusEffect(4, parameter, false);
            }
            hp = playerStatus.getHP();
            attack = playerStatus.getAttack();
            defence = playerStatus.getDefence();
            luck = playerStatus.getLuck();
            money = playerStatus.getMoney();

            playerStatus.setEffectFlag(false);
        }


        for (int i = 0; i < statusEffect.length; i++) {
            statusEffect[i].update();
        }
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
