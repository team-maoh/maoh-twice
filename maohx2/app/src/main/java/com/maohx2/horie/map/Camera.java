package com.maohx2.horie.map;

import android.graphics.Point;

public class Camera {
    Point display_size = new Point(0, 0);
    Point camera_offset = new Point(0, 0);
    Point normalize_size = new Point(900, 1600);
    int map_magnification;

    public void Camera(){}

    public int convertToNormCoordinateX(int world_x){
        int norm_x = world_x - camera_offset.x;
        if(norm_x < 0 || norm_x > 900){
            norm_x = -1000;
        }
        return norm_x;
    }

    public int convertToNormCoordinateY(int world_y){
        int norm_y = world_y - camera_offset.y;
        if(norm_y < 0 || norm_y > 1600){
            norm_y = -1000;
        }
        return norm_y;
    }

    public int convertToNormCoordinateXForMap(int world_x){
        int norm_x = world_x - camera_offset.x;
        if(norm_x < -1 * map_magnification || norm_x > normalize_size.x + map_magnification){
            norm_x = -1000;
        }
        return norm_x;
    }

    public int convertToNormCoordinateYForMap(int world_y){
        int norm_y = world_y - camera_offset.y;
        if(norm_y < -1 * map_magnification || norm_y > normalize_size.y + map_magnification){
            norm_y = -1000;
        }
        return norm_y;
    }

    public void setMapMagnification(int magnification){
        map_magnification = magnification;
    }

    public void setDisplaySize(Point m_display_size){
        display_size.set(m_display_size.x, m_display_size.y);
    }

    public void setCameraOffset(double world_x, double world_y){
        camera_offset.set((int)world_x - display_size.x/2, (int)world_y - display_size.y/2);
    }

    public Point getCameraOffset(){
        return camera_offset;
    }
}
