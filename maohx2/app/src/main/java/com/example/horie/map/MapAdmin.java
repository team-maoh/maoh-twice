package com.example.horie.map;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by horie on 2017/08/30.
 */

public class MapAdmin {
    /*int map_data_int[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1},

    };*/

    Chip map_data[][];

    final int MAX_MAP_SIZE_X = 1024;
    final int MAX_MAP_SIZE_Y = 1024;
    int map_size_x = 100;
    int map_size_y = 150;
    int magnification = 10;
    Paint paint = new Paint();
    SurfaceHolder holder;


    public int getMap_size_x(){
        return map_size_x;
    }

    public int getMap_size_y(){
        return map_size_y;
    }

    public void setMap_data(int i,int j,boolean isWall){
        map_data[i][j].setWallFlag(isWall);
    }

    public MapAdmin(SurfaceHolder m_holder){
        holder = m_holder;
        map_data = new Chip[map_size_x][map_size_y];
        for(int i = 0;i < map_size_x;i++) {
            for (int j = 0; j < map_size_y; j++) {
                map_data[i][j] = new Chip();
            }
        }
        //createMap();//自動生成
        createMapForDebug();//デバッグ用マップ
    }

    /*public void intToChip(int[][] m_map_data) {
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
    }*/

    //壁かどうかマップ座標で判定
    public boolean isWall(int x,int y){
        try {
            return map_data[x][y].isWall();
        }catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("配列の要素数をこえています。");
            System.out.println(e + "クラスの例外が発生しました。");
            return false;
        }

    }

    //壁かどうかワールドマップで判定
    public boolean isWallWorld(int world_x, int world_y, int magnification_x, int magnification_y){
        return map_data[worldToMap(world_x)][worldToMap(world_y)].isWall();
    }

    //マップの自動生成
    public void createMap(){
        Section section = new Section();
        section.setAll(0, map_size_x - 1, 0, map_size_y - 1);
        section.divideSection(10);
        section.updateMapData(this.map_data);
    }

    //デバッグ用マップ
    public void createMapForDebug(){
        for(int i = 0;i < map_size_x;i++){
            map_data[i][0].setWallFlag(true);
            map_data[i][map_size_y - 1].setWallFlag(true);
        }
        for(int j = 0;j < map_size_y;j++){
            map_data[0][j].setWallFlag(true);
            map_data[map_size_x - 1][j].setWallFlag(true);
        }
        for(int i = 0;i < 66;i++){
            map_data[i][30].setWallFlag(true);
        }
        for(int j = 30;j < 50;j++) {
            map_data[66][j].setWallFlag(true);
            map_data[33][j].setWallFlag(true);
        }
        for(int i = 40;i < 100;i++){
            map_data[i][120].setWallFlag(true);
        }
        for(int i = 40;i < 80;i++){
            map_data[i][80].setWallFlag(true);
        }
        for(int i = 60;i < 80;i++){
            map_data[i][100].setWallFlag(true);
        }
        for(int j = 80;j < 120;j++){
            map_data[40][j].setWallFlag(true);
        }
        for(int j = 80;j <= 100;j++){
            map_data[80][j].setWallFlag(true);
        }
    }

    public int detectWallDirection(int player_world_x, int player_world_y, int next_player_world_x, int next_player_world_y){
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

    public void drawMap(Canvas canvas) {
        //マップ描画
        int chip_height = 10;
        int chip_width  = 10;
        for(int i = 0;i < this.getMap_size_x();i++){
            for(int j = 0;j < this.getMap_size_y();j++){
                if(this.isWall(i,j) == false){
                    paint.setColor(Color.BLUE);//部屋は青
                }
                else {
                    paint.setColor(Color.RED);//壁は
                }
                canvas.drawRect(chip_width*i,chip_height*j,chip_width*(i+1),chip_height*(j+1),paint);
            }
        }
    }

    //ワールドマップ座標からマップ座標に変更
    public int worldToMap(int world_coordinate){
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

}
