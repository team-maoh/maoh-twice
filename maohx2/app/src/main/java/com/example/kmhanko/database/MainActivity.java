package com.kmhanko.database;

import android.app.Activity;
import android.content.Context;

import android.graphics.Paint;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.kmhanko.sound.SoundAdmin;

import android.database.Cursor;

/**
 * Created by user on 2017/09/03.
 */

public class MainActivity extends Activity {
    SoundAdmin sa;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("dg_mes:" + "MainActivity.onCreate");
        super.onCreate(savedInstanceState);

        MyDatabaseAdmin database_admin = new MyDatabaseAdmin(this);
        //database_admin.addMyDatabase("testdb","testdb.db",1,"r");
        database_admin.addMyDatabase("soundDB","soundDB.db",1,"r");

//        database_admin.addMyDatabase("tempdb2","tempdb2.db",1,"w");

        //database_admin.addMyDatabase("maketest2","maketest2.db",1,"w");

        database_admin.addMyDatabase("testdb","testdb.db",1,"w");

        sa = new SoundAdmin(this);
        sa.setDatabase(database_admin.getMyDatabase("soundDB"));
        sa.loadSoundPack("sound_pack_map");

        MyDatabase db = database_admin.getMyDatabase("testdb");

        //db.execSQL("CREATE TABLE test2(name TEXT,skill TEXT,field TEXT);");

        //db.execSQL("INSERT INTO test2(name,skill,field) VALUES ('Kana','baseball','science');");

        db.insert("test",new String[] { "name","age" },new String[] { "Saburo","50" });


/*
        Cursor c = db.getCursor("SELECT * FROM sqlite_master");

        List<String> buf = new ArrayList<String>();
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getString(0));
            isEof = c.moveToNext();
        }*/

        db.rewrite("test",new String[] {"age"},new String[] {"99"}, "name = 'Saburo'");

        //List<Integer> buf = db.getInt("test","age",null);
        //System.out.println("dg_mes:"+ buf.get(1) + " "+ buf.size());

        List<String> buf = db.getOneLine("test",2);
        System.out.println("dg_mes:"+ buf.get(0) +" "+buf.get(1));

        /*
        String text = "";
        Cursor c = db.query("products", new String[] { "name", "price" }, null, null, null, null, null, null);
        boolean isEof = c.moveToFirst();
        while (isEof) {
            text += String.format("%s : %d円\r\n", c.getString(0), c.getInt(1));
            isEof = c.moveToNext();
        }
        System.out.println("dg_mes:"+text);
*/


        setContentView(new CustomSurfaceView(this,sa));

        //db.close();


        //database_admin.getMyDatabase("tempdb2").close();
        //database_admin.getMyDatabase("testdb").close();

    }

    /*
    @Override
    protected void onStart();
    @Override
    protected void onRestart();
    @Override
    protected void onResume();
    @Override
    protected void onPause();
    @Override
    protected void onStop();
    @Override
    protected void onDestroy();
    */
}

class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    //メンバ変数
    private SurfaceHolder holder;
    private Thread thread;
    private Paint paint = new Paint();
    //メンバ変数ここまで

    private SoundAdmin sa;


    //コンストラクタ
    public CustomSurfaceView(Context context, SoundAdmin _sa) {
        super(context);
        this.init();
        sa = _sa;
    }

    public void init() {
        setZOrderOnTop(true);//最前面に描画
        //ホルダー関係初期処理
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
        // SurfaceViewが変化（画面の大きさ，ピクセルフォーマット）した時のイベントの処理を記述
        //特になし
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //スレッド関係の初期処理
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceViewが廃棄された時の処理を記述
    }
    @Override
    //Runnable
    public void run() {

        sa.play("walk");

    }

    //スレッドを初期化して実行

    private void doThread() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doThread();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }

}
