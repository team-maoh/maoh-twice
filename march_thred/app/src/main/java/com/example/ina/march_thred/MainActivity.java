package com.example.ina.march_thred;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}


public class Cells extends Applet
{
    //アプリケーションでの動作テスト用main
    //アプレットとして動くときは使われない
    static public void main(String args[])
    {
        Frame frame=new Frame();
        Cells test=new Cells();
        frame.add("Center",test);
        frame.setSize(300,100);
        frame.setVisible(true);
        frame.addWindowListener(test.new Control());
    }

    public Cells()//テストの組み立て
    {
        setLayout(new GridLayout(1,5));
        for(int i=0;i<5;i++){
            Cell cell=new Cell(i);
            add(cell);
            cell.start();
        }

    }
    class Control extends WindowAdapter{
        public void windowClosing(WindowEvent e){System.exit(0);}
    }
}

/*
スレッドを使って実行されるオブジェクトのクラス
*/
class Cell extends Button implements Runnable
{

    //スレッドの休み時間
    int sleepTime;

    int n=0;

    Cell(int n)
    {
        sleepTime=200;
        for(int i=0;i<n;i++)
            sleepTime*=2;//休み時間の設定
    }

    //スレッドを実行するオブジェクト
    Thread thread=null;

    //Threadを作成しスタート
    //下のrunがスレッドから1回だけ呼ばれる
    void start()
    {
        if(thread==null){//startを2回呼ぶときの用心
            thread=new Thread(this);//Threadを作成
            thread.start();//Threadをスタート
        }

    }

    //スレッドを終了する
    //変数threadをnullにするとrunのwhileループが終了する
    void stop()
    {
        thread=null;
    }

    //Runnableの実装部分

    //スレッドから1回だけ呼ばれる。
    //実行がrunがら戻ると終了する。
    public void run()
    {
        while(thread!=null){  //thread!=nullの間は繰り返す

            //ここにスレッドにさせたい仕事を記述
            n++;
            setLabel(""+n);
            repaint();

            //他のスレッドを実行する時間を与える為にここで少し休む
            //CPU時間を必要以上に独占しないようにする。
            try{Thread.sleep(sleepTime);}catch(Exception e){}
        }

    }
}