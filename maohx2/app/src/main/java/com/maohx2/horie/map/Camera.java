package com.maohx2.horie.map;

import android.graphics.Point;

public class Camera {
    Point map_size = new Point(0, 0);
    Point camera_offset = new Point(0, 0);
    Point normalize_size = new Point(1600, 900);
    int map_magnification;

    Camera(Point m_map_size, int m_map_magnification){
        map_size.set(m_map_size.x,m_map_size.y);
        map_magnification = m_map_magnification;
    }

    //移動用
    public int convertToWorldCoordinateX(int norm_x){
//        if(norm_x < 0){
//            throw new Error("%☆ホリエ:x座標が負 norm_x = "+norm_x+"(Camera:convertToWorldCoordinateX)");
//        }
        int world_x = norm_x + camera_offset.x;
        return world_x;
    }

    //移動用
    public int convertToWorldCoordinateY(int norm_y){
//        if(norm_y < 0){
//            throw new Error("%☆ホリエ:y座標が負 norm_y = "+norm_y+"(Camera:convertToWorldCoordinateY)");
//        }
        int world_y = norm_y + camera_offset.y;
        return world_y;
    }

    public int convertToNormCoordinateX(int world_x){
        if(world_x < 0){
            throw new Error("%☆ホリエ:x座標が負 world_x = "+world_x+"(Camera:convertToNormCoordinateX)");
        }
        if(world_x > map_size.x*map_magnification){
            throw new Error("%☆ホリエ:x座標がワールド座標外 world_x = "+world_x+"(Camera:convertToNormCoordinateX)");
        }
        int norm_x = world_x - camera_offset.x;
        if(norm_x < 0 || norm_x > normalize_size.x){
            norm_x = -1000;
        }
        return norm_x;
    }

    public int convertToNormCoordinateY(int world_y){
        if(world_y < 0){
            throw new Error("%☆ホリエ:y座標が負 world_y = "+world_y+"(Camera:convertToNormCoordinateY)");
        }
        if(world_y > map_size.y*map_magnification){
            throw new Error("%☆ホリエ:y座標がワールド座標外 world_y = "+world_y+"(Camera:convertToNormCoordinateY)");
        }
        int norm_y = world_y - camera_offset.y;
        if(norm_y < 0 || norm_y > normalize_size.y){
            norm_y = -1000;
        }
        return norm_y;
    }

    //マップ表示用
    public int convertToNormCoordinateXForMap(int world_x){
        int norm_x = world_x - camera_offset.x;
        if(norm_x < -1 * map_magnification || norm_x > normalize_size.x + map_magnification){
            norm_x = -1000;
        }
        return norm_x;
    }

    //マップ表示用
    public int convertToNormCoordinateYForMap(int world_y){
        int norm_y = world_y - camera_offset.y;
        if(norm_y < -1 * map_magnification || norm_y > normalize_size.y + map_magnification){
            norm_y = -1000;
        }
        return norm_y;
    }

    public void setCameraOffset(double world_x, double world_y){
        if(world_x < 0){
            throw new Error("%☆ホリエ:x座標が負 world_x = "+world_x+"(Camera:setCameraOffset)");
        }
        if(world_x > map_size.x*map_magnification){
            throw new Error("%☆ホリエ:x座標がワールド座標外 world_x = "+world_x+"(Camera:setCameraOffset)");
        }
        if(world_y < 0){
            throw new Error("%☆ホリエ:y座標が負 world_y = "+world_y+"(Camera:setCameraOffset)");
        }
        if(world_y > map_size.y*map_magnification){
            throw new Error("%☆ホリエ:y座標がワールド座標外 world_y = "+world_y+"(Camera:setCameraOffset)");
        }
        camera_offset.set((int)world_x - normalize_size.x/2, (int)world_y - normalize_size.y/2);
    }

    public Point getCameraOffset(){
        return camera_offset;
    }

    public Point getNowPoint(){
        Point now_point = new Point(camera_offset.x + normalize_size.x/2, camera_offset.y + normalize_size.y/2);
        return now_point;
    }
}
