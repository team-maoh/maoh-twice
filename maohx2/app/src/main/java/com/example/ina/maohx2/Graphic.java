package com.example.ina.maohx2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import java.util.ArrayList;
import java.util.*;
import android.view.MotionEvent;

/**
 * Created by ryomasenda on 2017/09/03.
 */

public class Graphic extends SurfaceView {

    public CustomSurfaceView(Context context) {
        super(context);

        setZOrderOnTop(true);
        /// このビューの描画内容がウインドウの最前面に来るようにする。
    }

}
