package com.example.blanka.runpath.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.blanka.runpath.R;

public class CountDown_Activity extends AppCompatActivity {

    private int recLen = 4;
    private TextView countDown;
    private String TAG = "CountDown_Activity";
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        /**
         * 去状态栏
         * */
        if(Build.VERSION.SDK_INT >= 21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        countDown = (TextView) findViewById(R.id.tv_count_down);
        startCountDownTime(recLen);
    }

    private void startCountDownTime(final long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
         timer = new CountDownTimer(time * 1000, 1000) {
            int reclen = (int) time-1 ;

            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                countDown.setText(String.valueOf(reclen));
                reclen --;
                Log.d(TAG, "onTick  " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                countDown.setText("GO!");
                Intent intent = new Intent(CountDown_Activity.this, Running_Activity.class);
                startActivity(intent);
                Log.d(TAG, "onFinish -- 倒计时结束");
            }
        };
        timer.start();// 开始计时
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        timer.cancel();// 取消
        super.onDestroy();
    }
}
