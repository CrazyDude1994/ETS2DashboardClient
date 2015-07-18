package com.crazydude.truckdashboard;

import android.util.JsonReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
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
        void onDataReceived(Data data);
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
        while (mSocket != null && mSocket.isConnected()) {
            if (mSocket != null && mSocket.isConnected()) {
                try {
                    DataInputStream dataInputStream = new DataInputStream(mSocket.getInputStream());
                    int length = dataInputStream.readInt();
                    byte[] data = new byte[length];
                    dataInputStream.read(data, 0, length);
                    String json = new String(data);
                    Gson gson = new GsonBuilder()
                            .create();
                    Data dataObject = gson.fromJson(json, Data.class);
                    if (mOnDataReceivedListener != null) {
                        mOnDataReceivedListener.onDataReceived(dataObject);
                    }
                } catch (EOFException e) {
                    mSocket = null;
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
            } catch (EOFException e) {
                mSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
