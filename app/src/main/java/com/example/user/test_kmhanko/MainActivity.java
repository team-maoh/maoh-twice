package com.example.user.test_kmhanko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.SurfaceHolder.Callback;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(new CustomSurfaceView(this));
    }
}
class CustomSurfaceView extends SurfaceView implements Callback {


    Resources res = this.getContext().getResources();
    Bitmap hukurou = BitmapFactory.decodeResource(res, R.drawable.hukurou);

    public CustomSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
        // SurfaceViewが変化（画面の大きさ，ピクセルフォーマット）した時のイベントの処理を記述
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // SurfaceViewが作成された時の処理（初期画面の描画等）を記述
        Canvas canvas = holder.lockCanvas();

        //描画コード
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(100,100,200,200,paint);

        canvas.drawBitmap(hukurou,300,300,paint);


        //描画コードここまで

        holder.unlockCanvasAndPost(canvas);

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //廃棄時の処理
    }


}
