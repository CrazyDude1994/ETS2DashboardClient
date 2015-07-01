package com.crazydude.truckdashboard;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.net.SocketFactory;

/**
 * Created by kartavtsev.s on 29.06.2015.
 */
@EBean
public class ControlsProtocol {

    private Socket mSocket;
    private OnDataReceivedListener mOnDataReceivedListener;

    enum Signals {
        BUTTON_PRESS, SLIDER_CHANGE;
    }

    enum Sliders {

        HID_USAGE_X(0x30),
        HID_USAGE_Y(0x31),
        HID_USAGE_Z(0x32),
        HID_USAGE_RX(0x33),
        HID_USAGE_RY(0x34),
        HID_USAGE_RZ(0x35),
        HID_USAGE_SL0(0x36),
        HID_USAGE_SL1(0x37),
        HID_USAGE_WHL(0x38);

        private int number;

        Sliders(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

    public interface OnDataReceivedListener {
        void onDataReceived(TruckInfo data);
    }

    @Background
    public void connect(String host, int port) {
        try {
            mSocket = SocketFactory.getDefault().createSocket(host, port);
            recvSignal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSeekbarSignal(int position, Sliders controlId) {
        sendSignal(Signals.SLIDER_CHANGE.ordinal(), controlId.getNumber(), false, position);
    }

    public void sendButtonSignal(int id, boolean isPressed) {
        sendSignal(Signals.BUTTON_PRESS.ordinal(), id, isPressed, 0);
    }

    public void setOnDataReceivedListener(OnDataReceivedListener listener) {
        mOnDataReceivedListener = listener;
    }

    @Background
    protected void recvSignal() {
        TruckInfo truckInfo = new TruckInfo();
        while (mSocket.isConnected()) {
            if (mSocket != null && mSocket.isConnected()) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(mSocket.getInputStream());
                    truckInfo.setEngineEnabled(dataInputStream.readBoolean());
                    truckInfo.setTrailerAttached(dataInputStream.readBoolean());
                    truckInfo.setSpeed(dataInputStream.readFloat());
                    truckInfo.setGear(dataInputStream.readInt());
                    truckInfo.setGears(dataInputStream.readInt());
                    truckInfo.setRpm(dataInputStream.readFloat());
                    truckInfo.setRpmMax(dataInputStream.readFloat());
/*                    truckInfo.setFuel(dataInputStream.readFloat());
                    truckInfo.setFuelCapacity(dataInputStream.readFloat());
                    truckInfo.setFuelRate(dataInputStream.readFloat());
                    truckInfo.setFuelAvgConsumption(dataInputStream.readFloat());*/
                    if (mOnDataReceivedListener != null) {
                        mOnDataReceivedListener.onDataReceived(truckInfo);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Background
    protected void sendSignal(int commandId, int controlId, boolean controlState, int controlPosition) {
        if (mSocket != null && mSocket.isConnected()) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(mSocket.getOutputStream());
                dataOutputStream.writeByte(commandId);
                dataOutputStream.writeInt(controlId);
                dataOutputStream.writeBoolean(controlState);
                dataOutputStream.writeInt(controlPosition);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
