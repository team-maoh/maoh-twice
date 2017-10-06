package com.example.ina.maohx2;

/**
 * Created by ina on 2017/09/22.
 */

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.opengl.GLSurfaceView.Renderer;
import android.view.SurfaceHolder;


/**
 * レンダラ―
 * <p>
 * このクラスがアプリケーションの実質の描画担当者になります。
 * 何もしなくても勝手にonDrawFrameメソッドが呼ばれます。
 * 描画したいものをonDrawFrameメソッドに指定すればよいです。
 */

public class MyRenderer implements Renderer {

    int n;

    /**
     * アプリケーションの幅(pixel)
     */
    //private static final int APP_SIZE_W = 480;
    int APP_SIZE_W;
    /**
     * アプリケーションの高さ(pixel)
     */
    //private static final int APP_SIZE_H = 800;
    int APP_SIZE_H;
    /**
     * アプリケーションコンテキスト
     */
    private final Context context;
    /**
     * 板ポリで実現したスプライト(キャラクター)
     */
    private MySprite neco = new MySprite();
    private MySprite apple = new MySprite();
    private MySprite banana = new MySprite();
    private MySprite slime = new MySprite();


    int touch_x;
    int touch_y;
    int touch_state;





    //画像読み込み
    GameSystem game_system;
    ImageAdmin image_admin;







    /**
     * コンストラクタ
     *
     * @param context:アプリケーションコンテキスト
     */
    public MyRenderer(Context context,int _APP_SIZE_W, int _APP_SIZE_H) {
        n = 0;
        this.context = context;
        touch_x = 0;
        touch_y = 0;
        touch_state = 2;

        APP_SIZE_W = _APP_SIZE_W;
        APP_SIZE_H = _APP_SIZE_H;

        game_system = new GameSystem();
        image_admin = new ImageAdmin();
    }

    /**
     * 生成(初期化)
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 画面のクリア時の色の指定
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // 黒
//gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // 白

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ちょっぴり高速化対策（効果の程は知らないがたぶんそれなりに）
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ディザ使いません(デフォルトでOFFになってると思うけど念のため)
        gl.glDisable(GL10.GL_DITHER);
// ポリゴンの面に陰影をつけません
        gl.glShadeModel(GL10.GL_FLAT);
// ポリゴンの輪郭がジャギジャギしてもかまいません
        gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_FASTEST);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// スプライト用のビットマップ(キャラクター画像)をロードします
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        this.neco.load(gl, this.context.getResources(), R.drawable.neco );
        this.apple.load(gl, this.context.getResources(), R.drawable.apple );
        this.banana.load(gl, this.context.getResources(), R.drawable.banana );
        this.slime.load(gl, this.context.getResources(), R.drawable.slime );

        image_admin.setImage(0, neco);
        image_admin.setImage(1, slime);
        image_admin.setImage(2, apple);
        image_admin.setImage(3, banana);
        game_system.init(image_admin);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)
    }

    /**
     * 向き変更
     * このメソッド内でアプリケーションの描画スクリーンを設定する
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ビューポート(表示領域)の指定
//
// 【引数の説明】
// 開始X座標, 開始Y座標, 幅, 高
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glViewport(0, 0, w, h);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// プロジェクターに投影するような感じにする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// プロジェクションモードに設定
        gl.glMatrixMode(GL10.GL_PROJECTION);
// スクリーン座標を初期化
        gl.glLoadIdentity();

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ビューポート内の座標系を左上(0, 0)に設定
// こうしないと左下が(0, 0)になってややこしくなる
//
// 【引数の説明】
// 左X, 右X, 下Y, 上Y, 手前Z, 奥Z
// ２Ｄゲームなので手前Zと奥Zは気にしなくてよい
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glOrthof(0.0f, APP_SIZE_W, APP_SIZE_H, 0.0f, -1.0f, 1.0f);
    }

    public void setTouchParam(double _touch_x, double _touch_y, int _touch_state){

        touch_x = (int)_touch_x;
        touch_y = (int)_touch_y;
        touch_state = _touch_state;
    }



    /**
     * 描画
     * これは毎フレーム勝手に呼び出されます（メインループに相当する）
     */
    @Override
    public void onDrawFrame(GL10 gl) {
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// キャラクターをお好きなように描画します
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



        image_admin.setGL10(gl);


        game_system.update(touch_x, touch_y, touch_state);
        game_system.draw(gl);






        //        n++;
// x=0, y=0の座標に普通に描画
//        this.ninja.draw(gl, touch_x, touch_y);

// x=0, y=64の座標にデブッチョ(横幅２倍)に描画
//        this.ninja.drawEx(gl, 0, 64 + n, 2, 1);

// x=64, y=64の座標に左右反転に描画
  //      this.ninja.drawTurnLR(gl, 64, 128 + n);

// x=200, y=200の座標に40度傾けて描画
    //    this.ninja.drawRota(gl, 200, 200 + n, 40);
    }
}