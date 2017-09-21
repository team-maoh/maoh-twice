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
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,1, 1, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,1, 1, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 ,0, 0, 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1, 1 ,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1, 1},
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
        int direction = 0;//0 = 壁なし, 1 = 水平, 2 = 垂直
        //プレイヤー座標と移動座標が同じマス
        if(worldToMap(player_world_x) == worldToMap(next_player_world_x) && worldToMap(player_world_y) == worldToMap(next_player_world_y)){
            direction = 0;
        }
        //左右のマスに移動
        else if(worldToMap(player_world_y) == worldToMap(next_player_world_y)){
            if()
            direction = 2;
        }
        //上下のマスに移動
        else if(worldToMap(player_world_x) == worldToMap(next_player_world_x)){
            direction = 1;
        }
        //斜めのマスに移動
        else{
            double a, b;//y = ax + b
            a = (next_player_world_y - player_world_y)/(next_player_world_x - player_world_x);
            b = player_world_y - a * player_world_x;

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

}
