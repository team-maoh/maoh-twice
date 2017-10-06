package com.maohx2.ina;


/**
 * Created by ina on 2017/09/22.
 */
import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;
/**
 * スプライトクラス
 *
 * ビットマップ(つまり画像)を、矩形の板ポリゴンにテクスチャとして貼りつけるクラスです。
 * loadメソッドを呼んで初期化しておけば、いつでもdraw●●メソッドで自由に描画できます。
 * ビットマップは、タテヨコが必ず２の２乗の正方形でなければいけません。
 *
 */

public class MySprite {
    /**
     * テクスチャID
     */
    private Integer textureID = null;
    /**
     * 板ポリゴン(矩形)
     */
    private MyPorigon porigon = null;

    /**
     * リソースのビットマップをロードしてテクスチャ登録します
     *
     * @param gl:GL10
     * @param res:コンテキストリソース
     * @param id:ビットマップのリソースID
     * @return true:成功
     */
    public boolean load(GL10 gl, Resources res, int id) {
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ビットマップをメモリにロード
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Bitmap bitmap = BitmapFactory.decodeResource(res, id);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ビットマップのイレギュラーチェック
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ロード正常チェック
        if(bitmap == null) {
            Log.e("Error!", "ビットマップロードに失敗ですドン！");
        }
// サイズが２の２乗の正方形かどうかチェック
        if(this.isNumberPowerOfTwo(bitmap.getWidth()) == false ||
                this.isNumberPowerOfTwo(bitmap.getHeight()) == false) {
            Log.e("Error!", "ビットマップが２の２乗の正方形じゃないですドン！");
        }

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 板ポリゴンを生成
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        this.porigon = new MyPorigon(bitmap.getWidth(), bitmap.getHeight());

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ビットマップをテクスチャとして登録
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        this.registTexture(gl, bitmap);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ビットマップをメモリから破棄
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 上でテクスチャ登録してるのでもう要らない
        if(bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }

// いまのうちにガベージコレクト
        System.gc();

        return true;
    }
    /**
     * 数値が2の乗数かどうかを返します
     * @param n:数値
     * @return true:2の乗数
     */
    private boolean isNumberPowerOfTwo(int n) {

        double check = Math.pow(n,1/2);

        if(check - (int)check != 0)
            return true;

        return false;
            //return (n & (n – 1)) == 0;
    }
    /**
     * ビットマップをテクスチャとして登録します
     * @param gl:GL10
     * @param bitmap:ビットマップ
     */
    private void registTexture(GL10 gl, Bitmap bitmap) {
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 使用可能なテクスチャ識別番号を取得
// テクスチャ識別番号はOpenGLが管理しているので
// プログラマはOpenGLに問合わせて空き番号をもらいます
// glGenTexturesが問合せメソッド
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
// テクスチャ管理番号の保存
        this.textureID = textures[0];

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// テクスチャ識別番号とビットマップの紐付
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// まず今回のテクスチャ管理番号をバインドする
        gl.glBindTexture(GL10.GL_TEXTURE_2D, this.textureID);
// そのバインドした状態でビットマップを与えることで紐づく
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// テクスチャを拡大・縮小する方法の指定
//
// 【引数の説明】
// GL_TEXTURE_2D(固定), 拡大時の指定 or 縮小時の指定, 伸縮方法
//
// 拡大時の指定 or 縮小時の指定は、
// GL_TEXTURE_MAG_FILTERで拡大時の指定
// GL_TEXTURE_MIN_FILTERで縮小時の指定
//
// 伸縮方法は、
// GL_NEARESTは軽いけど見た目が荒くなる
// GL_LINEARは重いけど見た目が滑らかになる
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// テクスチャの繰り返し方法の指定
//
// 【引数の説明】
// GL_TEXTURE_2D(固定), X方向の指定 or Y方向の指定, 繰り返し方法
//
// X方向の指定 or Y方向の指定は、
// GL_TEXTURE_WRAP_S:X方向の指定
// GL_TEXTURE_WRAP_T:Y方向の指定
//
// 繰り返し方法は、
// GL_REPEAT:繰り返しする
// GL_CLAMP:繰り返ししない(OpenGL ESでは使えないみたい)
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
    }

    /**
     * 描画します
     * @param gl:GL10
     * @param posX:X座標
     * @param posY:Y座標
     */
    public void draw(GL10 gl, float posX, float posY) {
// マトリックスを設定
        this.porigon.setMatrix(gl, posX, posY, 1.0f, 1.0f, 0.0f, false, false);
// テクスチャを描画
        this.porigon.draw(gl, this.textureID);
    }
    /**
     * 左右反転描画します
     * @param gl:GL10
     * @param posX:X座標
     * @param posY:Y座標
     */
    public void drawTurnLR(GL10 gl, float posX, float posY) {
// マトリックスを設定
        this.porigon.setMatrix(gl, posX, posY, 1.0f, 1.0f, 0.0f, true, false);
// テクスチャを描画
        this.porigon.draw(gl, this.textureID);
    }
    /**
     * 上下反転描画します
     * @param gl:GL10
     * @param posX:X座標
     * @param posY:Y座標
     */
    public void drawTurnTB(GL10 gl, float posX, float posY) {
// マトリックスを設定
        this.porigon.setMatrix(gl, posX, posY, 1.0f, 1.0f, 0.0f, false, true);
// テクスチャを描画
        this.porigon.draw(gl, this.textureID);
    }
    /**
     * 拡大縮小描画します
     * @param gl:GL10
     * @param posX:X座標
     * @param posY:Y座標
     * @param exRateX:X方向拡大縮小率(1.0で等倍)
     * @param exRateY:Y方向拡大縮小率(1.0で等倍)
     */
    public void drawEx(GL10 gl, float posX, float posY, float exRateX, float exRateY) {
// マトリックスを設定
        this.porigon.setMatrix(gl, posX, posY, exRateX, exRateY, 0.0f, false, false);
// テクスチャを描画
        this.porigon.draw(gl, this.textureID);
    }
    /**
     * 回転描画します
     * @param gl:GL10
     * @param posX:X座標
     * @param posY:Y座標
     * @param degree:角度(0～360)
     */
    public void drawRota(GL10 gl, float posX, float posY, float degree) {
// マトリックスを設定
        this.porigon.setMatrix(gl, posX, posY, 1.0f, 1.0f, degree, false, false);
// テクスチャを描画
        this.porigon.draw(gl, this.textureID);
    }
}