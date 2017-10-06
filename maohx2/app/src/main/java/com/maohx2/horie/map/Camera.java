package com.example.horie.map;

import android.app.Activity;
import android.graphics.Point;

public class Camera {
    Point display_size = new Point(0,0);

    public void setDisplaySize(Point m_display_size){
        display_size.x = m_display_size.x;
        display_size.y = m_display_size.y;
    }
    public Point getCameraOffset(double world_x, double world_y){
        Point map_offset = new Point(0,0);
        map_offset.x = (int)world_x - display_size.x;
        map_offset.y = (int)world_y - display_size.y;
        return map_offset;
    }
}
