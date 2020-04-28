package com.lucifercb.administrator.timingdemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "<<<";
    private TextView mTextView = null;
    private Button mButton_start = null;
    private Button mButton_pause = null;
    private Button mButton_stop = null;
    TimeUtils mTimeUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView =  findViewById(R.id.myTextView);
        mButton_start = findViewById(R.id.myButton_Start);
        mButton_pause = findViewById(R.id.myButton_Pause);
        mButton_stop =  findViewById(R.id.myButton_Stop);
        mTimeUtils = new TimeUtils(mTextView);

        mButton_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeUtils.startTimer();
            }
        });

        mButton_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeUtils.pauseTimer();
            }
        });
        mButton_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeUtils.stopTimer();
            }
        });
    }


    //--End Page
}
