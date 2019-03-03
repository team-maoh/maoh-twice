package com.maohx2.ina.Battle;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;

import java.util.Random;

public class DamageEffect {


    boolean exist;
    int position_x;
    int position_y;
    int REST_COUNT = 20;
    int count;
    int damage;
    double directionX = 0;
    double directionY = 0;

    Random rnd = new Random();

    Graphic graphic;
    Paint paint;
    Paint paintEdge;

    DamageEffect(Graphic _graphic){
        graphic = _graphic;

        exist = false;
        position_x = 0;
        position_y = 0;
        count = 0;

        paint = new Paint();
        paint.setARGB(255,255,0,0);
        paint.setTextSize(35);

        paintEdge = new Paint();
        paintEdge.setTextSize(paint.getTextSize());
        paintEdge.setColor(Color.WHITE);
        paintEdge.setAntiAlias(true);
        paintEdge.setTextAlign(Paint.Align.LEFT);
        paintEdge.setStrokeWidth(6.0f);
        paintEdge.setStrokeJoin(Paint.Join.ROUND);
        paintEdge.setStrokeCap(Paint.Cap.ROUND);
        paintEdge.setStyle(Paint.Style.STROKE);


    }

    void generate(int _position_x, int _position_y, int _damage){
        exist = true;

        position_x = _position_x;
        position_y = _position_y;
        damage = _damage;

        directionX = 300*(rnd.nextDouble() - 0.5);
        directionY = 300*(rnd.nextDouble() - 0.5);
    }

    void update(){

        if(exist == true) {
            if (count < REST_COUNT) {
                count++;
                position_x += directionX * Math.pow(2,-count);
                position_y += directionY * Math.pow(2,-count);
            } else { clear(); }
        }
    }

    boolean isExist(){
        return exist;
    }

    void draw(){

        if(exist == true){
            graphic.bookingDrawText(String.valueOf(damage),position_x,position_y,paint);
            graphic.bookingDrawText(String.valueOf(damage),position_x,position_y,paintEdge);
        }
    }

    private void clear(){
        exist = false;
        count = 0;
        position_x = 0;
        position_y = 0;
    }

    //TODO 配備
    public void release() {
        System.out.println("takanoRelease : DamageEffect");
        paint = null;
    }

}
