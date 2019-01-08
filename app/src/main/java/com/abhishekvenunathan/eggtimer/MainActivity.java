package com.abhishekvenunathan.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    SeekBar seekBar;
    Button button;
    boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void updateTimer(int time){

        int minutes = time / 60;
        int seconds = time - (minutes * 60);

        if(seconds<10){
            timer.setText(Integer.toString(minutes)+":0"+Integer.toString(seconds));
        }
        else{
            timer.setText(Integer.toString(minutes)+":"+Integer.toString(seconds));
        }

    }

    public void newTimer(){
        button.setText("Start");
        seekBar.setProgress(30);
        updateTimer(seekBar.getProgress());
        seekBar.setEnabled(true);
        counterIsActive=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        timer = findViewById(R.id.timerText);
        button = findViewById(R.id.button);

        seekBar.setMax(720);

        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    public void timerStart(View view) {

        if (counterIsActive) {

            countDownTimer.cancel();
            newTimer();

        } else {

            counterIsActive = true;
            button.setText("STOP");
            seekBar.setEnabled(false);

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    newTimer();

                }
            }.start();
        }
    }
}
