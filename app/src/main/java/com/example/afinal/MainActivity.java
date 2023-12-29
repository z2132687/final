package com.example.afinal;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /************1.定义变量、对象、洞穴坐标******************/
    private static final int GAME_DURATION = 30000; // 30 seconds
    private Runnable timerRunnable;
    private int score = 0;
    /***********/
    private int i=0;//记录打到的地鼠个数
    private ImageView mouse;//定义 mouse 对象

    private ImageView threemouse;//定义 mouse 对象

    private TextView info1; //定义 info1 对象（用于查看洞穴坐标）
    private Handler handler;//声明一个 Handler 对象
    public int[][] position=new int[][]{
            {277, 200}, {535, 200}, {832, 200},
            {1067,200}, {1328, 200}, {285, 360},
            {645, 360}, {1014,360}, {1348, 360},{319, 600},{764, 600},{1229,600}
    };//创建一个表示地鼠位置的数组 @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/**************************************************/
        handler = new Handler();

        // Set up timer
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                endGame();
            }
        };
        handler.postDelayed(timerRunnable, GAME_DURATION);
/**************************************************/

            // 其他初始化部分...

            // 在這裡加入 CountDownTimer
        TextView timerTextView = findViewById(R.id.timerTextView);

        long GAME_DURATION_MILLIS = 30000; // 30 seconds in milliseconds
        new CountDownTimer(GAME_DURATION_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int remainingTime = (int) (millisUntilFinished / 1000);
                timerTextView.setText("剩餘時間：" + remainingTime + "秒");
            }

            @Override
            public void onFinish() {
                // 遊戲時間結束的處理邏輯
                timerTextView.setText("剩餘時間：0秒");

                // 結束遊戲的相應處理

            }
        }.start();

            // 開啟地鼠產生的線程...

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置不显示顶部栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置横屏模式

/************2.绑定控件*****************/

        threemouse = (ImageView) findViewById(R.id.imageView2);
        mouse = (ImageView) findViewById(R.id.imageView1);
        info1 = findViewById(R.id.info);
/************获取洞穴位置*****************/
//通过 logcat 查看 【注】：getRawY()：触摸点距离屏幕上方的长度（此长度包括程序项目名栏的）
        info1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        float x = event.getRawX();
                        float y = event.getRawY();
                        Log.i("x:" + x, "y:" + y);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
/************3.实现地鼠随机出现*****************/
//创建 Handler 消息处理机制
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
//需要处理的消息
                int index;
                if (msg.what == 0x101) {
                    index = msg.arg1;//// 获取位置索引值
                    mouse.setX(position[index][0]);//设置 X 轴坐标
                    mouse.setY(position[index][1]);//设置 Y 轴坐标（原点为屏幕左上角（不包括程序名称栏））
                    mouse.setVisibility(View.VISIBLE);//设置地鼠显示
                }
                super.handleMessage(msg);

                if (msg.what == 0x102) {
                    index = msg.arg2;//// 获取位置索引值
                    threemouse.setX(position[index][0]);//设置 X 轴坐标
                    threemouse.setY(position[index][1]);//设置 Y 轴坐标（原点为屏幕左上角（不包括程序名称栏））
                    threemouse.setVisibility(View.VISIBLE);//设置地鼠显示
                }
                super.handleMessage(msg);
            }
        };
// 创建线程
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;// 定义一个记录地鼠位置的索引值
                while (!Thread.currentThread().isInterrupted()) {
                    index = new Random().nextInt(position.length);// 产生一个随机整数（范围：0<=index<数组长度）
                    Message m = handler.obtainMessage();//创建消息对象
                    m.what = 0x101;//设置消息标志
                    m.arg1 = index;// 保存地鼠标位置的索引值
                    handler.sendMessage(m);// 发送消息通知 Handler 处理
                    try {
                        Thread.sleep(new Random().nextInt(500) + 800); // 休眠一段时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();




        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;// 定义一个记录地鼠位置的索引值
                while (!Thread.currentThread().isInterrupted()) {
                    index = new Random().nextInt(position.length);// 产生一个随机整数（范围：0<=index<数组长度）
                    Message m = handler.obtainMessage();//创建消息对象
                    m.what = 0x102;//设置消息标志
                    m.arg2 = index;// 保存地鼠标位置的索引值
                    handler.sendMessage(m);// 发送消息通知 Handler 处理
                    try {
                        Thread.sleep(new Random().nextInt(500) + 200); // 休眠一段时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        a.start();




/************4.实现点击地鼠后的事件：让地鼠不显示&显示消息*****************/
// 添加触摸 mouse 后的事件
        mouse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.INVISIBLE);//设置地鼠不显示
                i++;
                score = score + 1;
                Toast.makeText(MainActivity.this , "打到[ " + i + " ]只地鼠！",
                        Toast.LENGTH_SHORT).show(); // 显示消息提示框
                return false;
            }
        });



        threemouse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.INVISIBLE);//设置地鼠不显示
                i = i + 3;
                score = score + 3;
                Toast.makeText(MainActivity.this , "打到[ " + i + " ]只地鼠！",
                        Toast.LENGTH_SHORT).show(); // 显示消息提示框
                return false;
            }
        });

    }
    public void increaseScore() {
        score++;
        // TODO: Update UI to reflect the current score
    }

    private void endGame() {
        Intent intent = new Intent(this, Score.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any callbacks to prevent memory leaks
        handler.removeCallbacks(timerRunnable);
    }
}



