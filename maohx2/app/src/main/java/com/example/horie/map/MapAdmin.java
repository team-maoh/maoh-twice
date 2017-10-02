package com.example.horie.map;


import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by horie on 2017/08/30.
 */

public class MapAdmin {
    int map_data_int[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };
    int[][] t_map_data_int = new int[map_data_int[0].length][map_data_int.length];

    Chip map_data[][];

    final int MAX_MAP_SIZE_X = 1024;
    final int MAX_MAP_SIZE_Y = 1024;
    int map_size_x = map_data_int[0].length;
    int map_size_y = map_data_int.length;
    int magnification = 128;
    Paint paint = new Paint();
    Point display_size = new Point(0, 0);
    Point map_offset = new Point(0, 0);
    SurfaceHolder holder;
    Camera camera = new Camera();


    public int getMap_size_x(){
        return map_size_x;
    }

    public int getMap_size_y(){
        return map_size_y;
    }

    public void setMap_data(int i,int j,boolean isWall){
        map_data[i][j].setWallFlag(isWall);
    }

    public MapAdmin(SurfaceHolder m_holder, Activity activity, Point m_display_size){
        holder = m_holder;
        //ディスプレイサイズ取得
        display_size.x = m_display_size.x;
        display_size.y = m_display_size.y;
        camera.setDisplaySize(display_size);
        map_data = new Chip[map_size_x][map_size_y];
        for(int i = 0;i < map_size_x;i++) {
            for (int j = 0; j < map_size_y; j++) {
                map_data[i][j] = new Chip();
            }
        }
        //System.out.println("size i = "+map_data_int.length+",j = "+map_data_int[0].length);
        //createMap();//自動生成
        transportMatrix();//デバッグ用
        intToChip(t_map_data_int);//デバッグ用
        //intToChip(map_data_int);//デバッグ用x, yが反転する
        //DisplaySizeCheck display_size_check = new DisplaySizeCheck();
        //point = display_size_check.getDisplaySize(activity);
        //System.out.println("display width:x = "+display_size.x+",y = "+display_size.y);
    }

    //壁かどうかマップ座標で判定
    private boolean isWall(int x,int y){
        try {
            return map_data[x][y].isWall();
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("配列の要素数をこえています。");
            System.out.println(e + "クラスの例外が発生しました。");
            return false;
        }

    }

    //壁かどうかワールドマップで判定
    private boolean isWallWorld(int world_x, int world_y, int magnification_x, int magnification_y){
        return map_data[worldToMap(world_x)][worldToMap(world_y)].isWall();
    }

    //マップの自動生成
    private void createMap(){
        Section section = new Section();
        section.setAll(0, map_size_x - 1, 0, map_size_y - 1);
        section.divideSection(10);
        section.updateMapData(map_data);
    }

    public int detectWallDirection(double player_world_x_double, double player_world_y_duoble, double next_player_world_x_double, double next_player_world_y_double){
        int player_world_x = (int)player_world_x_double;
        int player_world_y = (int)player_world_y_duoble;
        int next_player_world_x = (int)next_player_world_x_double;
        int next_player_world_y = (int)next_player_world_y_double;
        int direction = 0;//0 : 壁なし, 1 : 水平, 2 : 垂直
        int player_map_x = worldToMap(player_world_x);//移動前のマップ座標x
        int player_map_y = worldToMap(player_world_y);//移動前のマップ座標y
        int next_player_map_x = worldToMap(next_player_world_x);//移動後のマップ座標x
        int next_player_map_y = worldToMap(next_player_world_y);//移動後のマップ座標y
        //プレイヤー座標と移動座標が同じマス
        if(player_map_x == next_player_map_x && player_map_y == next_player_map_y){
            direction = 0;
        }
        //左右のマスに移動
        else if(player_map_y == next_player_map_y){
            //次の移動先のマスが壁である
            if(map_data[next_player_map_x][next_player_map_y].isWall()) {
                direction = 2;
            }
            //移動先のマスが壁でない
            else{
                direction = 0;
            }
        }
        //上下のマスに移動
        else if(player_map_x == next_player_map_x){
            //移動先のマスが壁である
            if(map_data[next_player_map_x][next_player_map_y].isWall()) {
                direction = 1;
            }
            //移動先のマスが壁でない
            else{
                direction = 0;
            }
        }
        //斜めのマスに移動
        else{
            double a, b, x_up, y_up, x_down, y_down, x_left, y_left, x_right, y_right, dst2_left, dst2_right, dst2_up, dst2_down;
            //1次関数y = ax + bを求める
            a = (next_player_world_y - player_world_y)/(next_player_world_x - player_world_x);
            b = player_world_y - a * player_world_x;
            x_left = magnification * (player_map_x - 1);
            y_left = a * x_left + b;  //x = 100 * (player_map_x - 1)の時のy座標(左の垂直壁のy座標)
            x_right = magnification * (player_map_x);
            y_right = a * x_right + b;     //x = 100 * (player_map_x)の時のy座標(右の垂直壁のy座標)
            y_up = magnification * (player_map_y - 1);
            x_up = (y_up - b) / a;  //y = 100 * (player_map_y - 1)の時のx座標(上の水平壁のx座標)
            y_down = magnification * (player_map_y);
            x_down = (y_down - b) / a;    //y = 100 * (player_map_y)の時のx座標(下の水平壁のx座標)
            //それぞれの壁の交点までの距離の2乗
            dst2_left = Math.pow(x_left - player_world_x, 2) + Math.pow(y_left - player_world_y, 2);
            dst2_right = Math.pow(x_right - player_world_x, 2) + Math.pow(y_right - player_world_y, 2);
            dst2_up = Math.pow(x_up - player_world_x, 2) + Math.pow(y_up - player_world_y, 2);
            dst2_down = Math.pow(x_down - player_world_x, 2) + Math.pow(y_down - player_world_y, 2);
            //移動先が左上
            if(next_player_map_x == player_map_x - 1 && next_player_map_y == player_map_y - 1){
                if(dst2_left >= dst2_up){
                    if(map_data[player_map_x][player_map_y - 1].isWall()){
                        direction = 1;
                    }
                    else if(map_data[player_map_x - 1][player_map_y - 1].isWall()){
                        direction = 2;
                    }
                }
                else if(dst2_left < dst2_up){
                    if(map_data[player_map_x - 1][player_map_y].isWall()){
                        direction = 2;
                    }
                    else if(map_data[player_map_x - 1][player_map_y - 1].isWall()){
                        direction = 1;
                    }
                }
            }
            //移動先が右上
            if(next_player_map_x == player_map_x + 1 && next_player_map_y == player_map_y - 1){
                if(dst2_right >= dst2_up){
                    if(map_data[player_map_x][player_map_y - 1].isWall()){
                        direction = 1;
                    }
                    else if(map_data[player_map_x + 1][player_map_y - 1].isWall()){
                        direction = 2;
                    }
                }
                else if(dst2_right < dst2_up){
                    if(map_data[player_map_x + 1][player_map_y].isWall()){
                        direction = 2;
                    }
                    else if(map_data[player_map_x + 1][player_map_y - 1].isWall()){
                        direction = 1;
                    }
                }
            }
            //移動先が左下
            if(next_player_map_x == player_map_x - 1 && next_player_map_y == player_map_y + 1){
                if(dst2_left >= dst2_down){
                    if(map_data[player_map_x][player_map_y + 1].isWall()){
                        direction = 1;
                    }
                    else if(map_data[player_map_x - 1][player_map_y + 1].isWall()){
                        direction = 2;
                    }
                }
                else if(dst2_left < dst2_down){
                    if(map_data[player_map_x - 1][player_map_y].isWall()){
                        direction = 2;
                    }
                    else if(map_data[player_map_x - 1][player_map_y + 1].isWall()){
                        direction = 1;
                    }
                }
            }
            //移動先が右下
            if(next_player_map_x == player_map_x + 1 && next_player_map_y == player_map_y + 1){
                if(dst2_right >= dst2_down){
                    if(map_data[player_map_x][player_map_y + 1].isWall()){
                        direction = 1;
                    }
                    else if(map_data[player_map_x + 1][player_map_y + 1].isWall()){
                        direction = 2;
                    }
                }
                else if(dst2_right < dst2_down){
                    if(map_data[player_map_x + 1][player_map_y].isWall()){
                        direction = 2;
                    }
                    else if(map_data[player_map_x + 1][player_map_y + 1].isWall()){
                        direction = 1;
                    }
                }
            }
        }
        return direction;
    }

    //マップ描画
    public void drawMap(Canvas canvas) {
        //int chip_height = 10;
        //int chip_width  = 10;
        map_offset.set(camera.getCameraOffset(1500,2000).x, camera.getCameraOffset(1500,2000).y);
        for(int i = 0;i < this.getMap_size_x();i++){
            for(int j = 0;j < this.getMap_size_y();j++){
                if(this.isWall(i,j) == false){
                    paint.setColor(Color.BLUE);//部屋は青
                }
                else {
                    paint.setColor(Color.RED);//壁は
                }
                canvas.drawRect(magnification*i-map_offset.x,magnification*j-map_offset.y,magnification*(i+1)-map_offset.x,magnification*(j+1)-map_offset.y,paint);
            }
        }
        //デバッグ用
        /*paint.setColor(Color.RED);
        canvas.drawRect(0,0,1080,1794,paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(1,1,1079,1703,paint);*/
    }

    //ワールドマップ座標からマップ座標に変更
    private int worldToMap(int world_coordinate){
        return world_coordinate / magnification;
    }

    //int配列をChip配列に変換
    public void intToChip(int[][] m_map_data) {
        for(int i = 0;i < m_map_data.length;i++) {
            for (int j = 0; j < m_map_data[i].length; j++) {
                if(m_map_data[i][j] == 0){
                    map_data[i][j].setWallFlag(false);
                }
                else{
                    map_data[i][j].setWallFlag(true);
                }
            }
        }
    }

    //map_data_intを転置行列に変換
    public void transportMatrix(){
        for(int i = 0;i < map_data_int.length;i++){
            for(int j = 0;j < map_data_int[0].length;j++){
                t_map_data_int[j][i] = map_data_int[i][j];
            }
        }
    }
}
