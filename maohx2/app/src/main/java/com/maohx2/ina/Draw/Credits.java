package com.maohx2.ina.Draw;

import android.graphics.Paint;

import static com.maohx2.ina.ItemData.ItemDataAdmin.graphic;

/**
 * Created by ina on 2018/06/12.
 */

public class Credits {

    Graphic graphic;
    int creditNum = 42;
    Credit credits[] = new Credit[creditNum];
    Paint paint;

    public Credits(Graphic _graphic){
        graphic = _graphic;

        paint = new Paint();
        paint.setTextSize(70);
        paint.setARGB(255,255,255,255);

        paint.setTextSize(70);
        credits[0] = new Credit(graphic, paint, "制作",200,900);

        paint.setTextSize(40);
        credits[1] = new Credit(graphic, paint, "ina",220,1000);
        credits[2] = new Credit(graphic, paint, "kmhanko",220,1100);
        credits[3] = new Credit(graphic, paint, "センパイ",220,1200);
        credits[4] = new Credit(graphic, paint, "fuusya",220,1300);

        paint.setTextSize(70);
        credits[5] = new Credit(graphic, paint, "素材提供",200,1800);

        paint.setTextSize(40);
        credits[6] = new Credit(graphic, paint, "ぴぽや(ぴぽ)",220,1900);
        credits[7] = new Credit(graphic, paint, "素材屋Rosa",220,2000);
        credits[8] = new Credit(graphic, paint, "CounterClockWise(苗代)",220,2100);
        credits[9] = new Credit(graphic, paint, "Un Almacen(からし)",220,2200);

        credits[10] = new Credit(graphic, paint, "三日月アルペジオ(忠藤いづる)",220,2300);
        credits[11] = new Credit(graphic, paint, "ほらぱれっと",220,2400);
        credits[12] = new Credit(graphic, paint, "Krachware(しののめ)",220,2500);
        credits[13] = new Credit(graphic, paint, "ねくら(ねくら)",220,2600);
        credits[14] = new Credit(graphic, paint, "空想曲線(こ・ぱんだ)",220,2700);
        credits[15] = new Credit(graphic, paint, "TEDDY-PLAZA(瀬尾辰也)",220,2800);
        credits[16] = new Credit(graphic, paint, "ぴぽや(ぴぽ)",220,2900);
        credits[17] = new Credit(graphic, paint, "誰得・らくだのブログ(誰得・らくだ)",220,3000);
        credits[18] = new Credit(graphic, paint, "サイバーなゲーム制作ページ",220,3100);
        credits[19] = new Credit(graphic, paint, "A-realm(ロク)",220,3200);

        credits[20] = new Credit(graphic, paint, "game*(YS)",220,3300);
        credits[21] = new Credit(graphic, paint, "NERAD(メガネ)",220,3400);
        credits[22] = new Credit(graphic, paint, "藤宮 翔流のひきだし(藤宮 翔流)",220,3500);
        credits[23] = new Credit(graphic, paint, "白螺子屋",220,3600);
        credits[24] = new Credit(graphic, paint, "王国興亡記(そーいち)",220,3700);
        credits[25] = new Credit(graphic, paint, "moyasiエフェクト館(tktk)",220,3800);
        credits[26] = new Credit(graphic, paint, "ビデオテープの工作部屋(みとねーとぅ)",220,3900);
        credits[27] = new Credit(graphic, paint, "つちのこ",220,4000);
        credits[28] = new Credit(graphic, paint, "Red Blob Games(Amit Patel)",220,4100);
        credits[29] = new Credit(graphic, paint, "マカネスク",220,4200);

        credits[30] = new Credit(graphic, paint, "MusMus(watson)",220,4300);
        credits[31] = new Credit(graphic, paint, "甘茶の音楽工房(甘茶)",220,4400);
        credits[32] = new Credit(graphic, paint, "Wingless Seraph(ユーフルカ)",220,4500);
        credits[33] = new Credit(graphic, paint, "効果音ラボ",220,4600);
        credits[34] = new Credit(graphic, paint, "ザ・マッチメィカァズ(OSA)",220,4700);
        credits[35] = new Credit(graphic, paint, "G-Sound(G-MIYA)",220,4800);
        credits[36] = new Credit(graphic, paint, "M-ART(Napi)",220,4900);
        credits[37] = new Credit(graphic, paint, "DEAD END WONDER(いなだ)",220,5000);
        credits[38] = new Credit(graphic, paint, "魔王魂(KOUICHI)",220,5100);

        credits[39] = new Credit(graphic, paint, "ここまでプレイしてくれてありがとうございます．",200,5600);
        credits[40] = new Credit(graphic, paint, "本編は終わりましたが，まだ魔王は強化されます",200,5700);
        credits[41] = new Credit(graphic, paint, "引き続きプレイをお楽しみできます．",200,5800);
    }

    public void update(){

        for(int i = 0; i <creditNum; i++){
            credits[i].update();
        }
    }

    public void draw(){
        for(int i = 0; i <creditNum; i++){
            credits[i].draw();
        }
    }

    public boolean endCheck() {
        return credits[creditNum-1].endCheck();
    }
}
