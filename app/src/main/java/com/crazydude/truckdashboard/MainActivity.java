package com.crazydude.truckdashboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crazydude.androidgauge.GaugeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements ControlsProtocol.OnDataReceivedListener {

    @Bean
    ControlsProtocol mClient;

    @ViewById(R.id.gaugage_speed)
    GaugeView mSpeedometer;

    @ViewById(R.id.gaugage_rpm)
    GaugeView mRPM;

    @ViewById(R.id.gaugage_fuel)
    GaugeView mFuel;

    private boolean mIsMaxRPMSet = false;
    private boolean mIsMaxFuelSet = false;


    @AfterViews
    void initMainView() {

/*        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });*/
        mClient.connect("192.168.1.2", 8845);
        mClient.setOnDataReceivedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @UiThread
    @Override
    public void onDataReceived(TruckInfo data) {
//        mSpeedText.setText("Speed: " + Float.toString(data.getSpeed()));
        float kmh = (data.getSpeed() / 1000f) * 3600f;
        mSpeedometer.setValue(kmh);
        if (!mIsMaxRPMSet) {
            mRPM.setMaxValue(data.getRpmMax());
        }
        if (!mIsMaxFuelSet) {
            mFuel.setMaxValue(data.getFuelCapacity());
        }
        mRPM.setValue(data.getRpm());
        mFuel.setValue(data.getFuel());
    }
}
