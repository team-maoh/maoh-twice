package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;



public class DungeonActivity extends BaseActivity {
    DungeonSurfaceView dungeon_surface_view;
    boolean game_system_flag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dungeon_surface_view = new DungeonSurfaceView(this);
        setContentView(dungeon_surface_view);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!game_system_flag) {
            dungeon_surface_view.runGameSystem(dungeon_surface_view.getWidth(), dungeon_surface_view.getHeight());
            dungeon_surface_view.runThread();
            game_system_flag = true;
        }
    }





    //ここでメインループを止める
    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("onStop!!");
    }

    //ここでメインループを再開する
    @Override
    public void onResume(){
        super.onResume();
    }

}

class DungeonSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap neco = BitmapFactory.decodeResource(res, R.drawable.neco);
    Bitmap apple = BitmapFactory.decodeResource(res, R.drawable.apple);
    Bitmap banana = BitmapFactory.decodeResource(res, R.drawable.banana);
    //藤原追加
    Bitmap grape = BitmapFactory.decodeResource(res, R.drawable.grape);
    Bitmap watermelon = BitmapFactory.decodeResource(res, R.drawable.watermelon);

    Bitmap slime = BitmapFactory.decodeResource(res, R.drawable.slime);

    Paint paint = new Paint();
    Point display_size = new Point(0, 0);
    private SurfaceHolder holder;
    private Thread thread;
    Context context;

    DungeonUserInterface dungeon_user_interface;
    double touch_x = 0;
    double touch_y = 0;
    TouchState touch_state = TouchState.AWAY;
    SoundAdmin sound_admin;
    MyDatabaseAdmin my_database_admin;

    DungeonGameSystem game_system;

    Graphic graphic;


    public DungeonSurfaceView(Context m_context) {
        super(m_context);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        context = m_context;
    }

    public void runGameSystem(int display_width,int display_height) {
        display_size.x = display_width;
        display_size.y = display_height;
        game_system = new DungeonGameSystem();
        dungeon_user_interface = new DungeonUserInterface();
        graphic = new Graphic(display_width, display_height);
        sound_admin = new SoundAdmin(context);
        my_database_admin = new MyDatabaseAdmin(context);
        dungeon_user_interface.init();
        my_database_admin.addMyDatabase("soundDB", "soundDB.db", 1, "r");//データベースのコピーしMySQLiteのdbを扱いやすいMyDataBase型にしている
        MyDatabase database = my_database_admin.getMyDatabase("soundDB");
        my_database_admin.addMyDatabase("local_image_DB", "local_image.db", 1, "r");
        sound_admin.setDatabase(database);//扱いやすいやつをセットしている
        sound_admin.loadSoundPack("sound_pack_map");
        graphic.init(context, holder, my_database_admin.getMyDatabase("local_image_DB"));
        game_system.init(context, holder, dungeon_user_interface, sound_admin, neco, apple, banana, grape, watermelon, slime,(Activity)context,display_size, graphic);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)
    }

    public void runThread(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void run() {
        while (thread!=null) {

            dungeon_user_interface.updateTouchState(touch_x, touch_y, touch_state);
            game_system.update(touch_x, touch_y, touch_state);
            game_system.draw(touch_x, touch_y, touch_state);
            if(dungeon_user_interface.getTouchState() == TouchState.DOWN){
                thread = null;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_state = TouchState.DOWN;
                touch_x = event.getX();
                touch_y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_state = TouchState.MOVE;
                touch_x = event.getX();
                touch_y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touch_state = TouchState.UP;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }
}
