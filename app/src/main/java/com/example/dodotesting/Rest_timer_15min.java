package com.example.dodotesting;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Rest_timer_15min extends AppCompatActivity {

    private static final long start_time = 900000;
    private TextView mTextView_time_counter;
    private Button mbtn_start_pause;
    private Button mbtn_reset;

    private CountDownTimer mCountDownTimer;
    private boolean checking_timer;
    private long mTimeleft = start_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_timer);

        mTextView_time_counter = findViewById(R.id.timer_count);
        mbtn_start_pause = findViewById(R.id.start_pause_count);
        mbtn_reset = findViewById(R.id.Reset_count);


        mbtn_start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checking_timer){
                    pausetimer();
                }else{
                    starttimer();
                }

            }
        });

        mbtn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resettimer();

            }
        });
        updatecount_text();


    }
    private void starttimer(){
        mCountDownTimer = new CountDownTimer(mTimeleft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeleft = millisUntilFinished;
                updatecount_text();

            }

            @Override
            public void onFinish() {
                checking_timer = false;
                mbtn_start_pause.setText("Start");
                mbtn_start_pause.setVisibility(View.INVISIBLE);
                mbtn_reset.setVisibility(View.VISIBLE);
                Toast.makeText(Rest_timer_15min.this, "Times up!", Toast.LENGTH_LONG).show();
            }
        }.start();

        checking_timer = true;
        mbtn_start_pause.setText("Pause");
        mbtn_reset.setVisibility(View.INVISIBLE);



    }

    private void pausetimer(){
        mCountDownTimer.cancel();
        checking_timer = false;
        mbtn_start_pause.setText("Start");
        mbtn_reset.setVisibility(View.VISIBLE);

    }

    private void resettimer(){
        mTimeleft = start_time;
        updatecount_text();
        mbtn_reset.setVisibility(View.INVISIBLE);
        mbtn_start_pause.setVisibility(View.VISIBLE);

    }
    private void updatecount_text(){
        int minutes = (int) (mTimeleft / 1000 )/ 60;
        int seconds = (int) (mTimeleft / 1000 ) % 60;

        String timeleft_informat = String.format(Locale.getDefault(),"%02d:%02d", minutes,seconds);
        mTextView_time_counter.setText(timeleft_informat);


    }

}