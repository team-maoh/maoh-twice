package com.maohx2.ina;


import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ina on 2017/09/22.
 */

public class ImageAdmin {

    MySprite[] images = new MySprite[4];
    GL10 gl;

    public void init() {
    }

    public MySprite getImage(int id) {
        return images[id];
    }

    public void setImage(int id, MySprite add_image) {
        images[id] = add_image;
    }

    public GL10 getGL10(){
        return  gl;
    }

    public void setGL10(GL10 _gl){
        gl = _gl;
    }


    public ImageAdmin(){}

}
