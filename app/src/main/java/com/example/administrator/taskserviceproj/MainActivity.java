package com.example.administrator.taskserviceproj;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.*;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button start, stop;
    String flagStr;
    boolean check;
    boolean abort = false;
    public void initViews() {
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        if (check) {
            System.out.println("Flag String = " + flagStr);
            if (flagStr.equals("false")) {
                stop.setEnabled(true);
                start.setEnabled(false);
            }
            else
                stop.setEnabled(false);
        }
        else stop.setEnabled(false);
    }
    public void initService() {
        final Timer timer = new Timer();
        final Intent intent = new Intent(MainActivity.this,TaskService.class);
        final TimerTask task = new MyTimerTask(intent);;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop.setEnabled(true);
                start.setEnabled(false);
                timer.schedule(task, 500L, 500L);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setEnabled(true);
                stop.setEnabled(false);
                task.cancel();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            System.out.println("false!");
            check = false;
        }
        else {
            flagStr = savedInstanceState.getString("flag");
            check = true;
        }
        setContentView(R.layout.activity_main);
        initViews();
        initService();
    }

    public class MyTimerTask extends TimerTask{
        Intent it;
        public MyTimerTask(Intent intent) {
            it = intent;
        }
        public void run() {
            startService(it);
        }
    }
}
