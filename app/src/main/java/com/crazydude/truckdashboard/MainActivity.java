package com.crazydude.truckdashboard;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    ControlsProtocol mClient;

    @ViewById(R.id.activity_main_seekbar)
    SeekBar mSeekBar;

    @ViewById(R.id.activity_main_button)
    Button mButton;

    @AfterViews
    void initViews() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mClient != null) {
                    mClient.sendSeekbarSignal(progress, 48);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mClient.sendButtonSignal(1, true);
                        break;
                    case MotionEvent.ACTION_UP:
                        mClient.sendButtonSignal(1, false);
                        break;
                }
                return true;
            }
        });
        mClient.connect("192.168.1.4", 8844);
    }
}
