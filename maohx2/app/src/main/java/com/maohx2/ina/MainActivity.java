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
import android.widget.RelativeLayout;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.ina.UI.MapUserInterface;
import com.maohx2.ina.UI.UserInterface;

import static com.maohx2.ina.Constants.Touch.TouchState;



public class MainActivity extends BaseActivity {
    DungeonSurfaceView dungeon_surface_view;
    boolean game_system_flag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dungeon_surface_view = new DungeonSurfaceView(this);
        setContentView(dungeon_surface_view);
        //setImage();
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


    GameSystem game_system;

    public DungeonSurfaceView(Context m_context) {
        super(m_context);
        setZOrderOnTop(true);
        /// このビューの描画内容がウインドウの最前面に来るようにする。
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        context = m_context;
    }

    public void runGameSystem(int display_width,int display_height){
        display_size.x = display_width;
        display_size.y = display_height;
        game_system = new GameSystem();
        dungeon_user_interface = new DungeonUserInterface();
        game_system.init(holder, dungeon_user_interface, neco, apple, banana, grape, watermelon, slime,(Activity)context,display_size);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)
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

            game_system.update(touch_x, touch_y, touch_state);
            game_system.draw(touch_x, touch_y, touch_state);
        }
    }

    //目標地点を記録するリスト
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





/*

public class MainActivity extends Activity {

    RelativeLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new RelativeLayout(this);
        layout.setBackgroundColor(Color.WHITE);
//        layer_admin = new LayerAdmin(getApplicationContext());
//        TouchSV touch = new TouchSV(this);
//        BaseSurfaceView[] layer = new BaseSurfaceView[7];
//        layer[0] = new CustomSurfaceView(this,layer_admin);
//        for (int i = 1; i < 7; i++)
//            layer[i] = new BaseSurfaceView(this);
//        layer_admin.setLayer(0, layer[0]);
//        for (int i = 1; i < 7; i++){
//            layer_admin.setLayer(i, layer[i]);
//        }
//        for(int i = 0; i < 7; i++) {
//            layout.addView(layer_admin.getLayer(i));
//        }
        layout.addView(new BattleSurfaceView(this));
        setContentView(layout);
//        layer_admin.startThread();
//        layout.addView(new CustomSurfaceView(this));
//        setContentView(layout);
    }
}





class BattleSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback, Runnable {

    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;

    double touch_x = 0;
    double touch_y = 0;

    TouchState touch_state = TouchState.AWAY;


    BattleUserInterface battle_user_interface;
    GameSystem game_system;

    public BattleSurfaceView(Context context) {
        super(context);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);

        battle_user_interface = new BattleUserInterface();
        battle_user_interface.init();

        game_system = new GameSystem();
        game_system.init(holder, battle_user_interface);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void run() {


        while (true) {
            battle_user_interface.updateTouchState(touch_x, touch_y, touch_state);
            game_system.updata();
            game_system.draw();
        }

    }


    //なぞられた点を記録するリスト
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


*/


/*
public class MainActivity extends Activity {

    RelativeLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new RelativeLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        layout.addView(new MapSurfaceView(this));
        setContentView(layout);

    }
}


class MapSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback, Runnable {

    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;

    double touch_x = 0;
    double touch_y = 0;

    TouchState touch_state = TouchState.AWAY;


    UserInterface map_user_interface;
    GameSystem game_system;

    public MapSurfaceView(Context context) {
        super(context);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);

        map_user_interface = new UserInterface();
        map_user_interface.init();

        game_system = new GameSystem();
        game_system.init(holder, map_user_interface);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void run() {


        while (true) {
            map_user_interface.updateTouchState(touch_x, touch_y, touch_state);
            game_system.updata();
            game_system.draw();
        }

    }


    //なぞられた点を記録するリスト
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
*/


/*
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GLSurfaceViewを設定します
        setContentView(new MyGLView(this));
    }

}
*/
