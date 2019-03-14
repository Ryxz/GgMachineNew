package com.example.advertisingmachine.qtapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.example.administrator.qtapplication.R;


/**
 * Created by Ryx on 2019/2/13.
 */
public class WelcomeActivity extends Activity {

    public final int START_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        handler.sendEmptyMessageDelayed(0,START_TIME);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome(){
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }








}
