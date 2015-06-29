package com.crazydude.truckdashboard;

import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    ControlsProtocol mClient;

    private SeekBar mSeekBar;

    @AfterViews
    void initViews() {
        mSeekBar = (SeekBar) findViewById(R.id.activity_main_seekbar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mClient != null) {
                    mClient.sendSeekbarSignal(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mClient.connect("192.168.56.1", 8844);
    }
}
