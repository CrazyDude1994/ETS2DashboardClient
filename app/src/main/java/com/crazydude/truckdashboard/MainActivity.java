package com.crazydude.truckdashboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements ControlsProtocol.OnDataReceivedListener {

    @Bean
    ControlsProtocol mClient;

    @ViewById(R.id.activity_main_seekbar)
    SeekBar mSeekBar;

    @ViewById(R.id.activity_main_button)
    Button mButton;

    @ViewById(R.id.activity_main_speed)
    TextView mSpeedText;

    @AfterViews
    void initViews() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mClient != null) {
                    mClient.sendSeekbarSignal(progress, ControlsProtocol.Sliders.HID_USAGE_X);
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
        mClient.setOnDataReceivedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @UiThread
    @Override
    public void onDataReceived(TruckInfo data) {
        mSpeedText.setText("Speed: " + Float.toString(data.getSpeed()));
    }
}
