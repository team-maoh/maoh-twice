package com.maohx2.horie.map;

import android.app.Activity;
import android.graphics.Point;

public class Camera {
    Point display_size = new Point(0, 0);
    Point camera_offset = new Point(0, 0);

    public void Camera(){}

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
