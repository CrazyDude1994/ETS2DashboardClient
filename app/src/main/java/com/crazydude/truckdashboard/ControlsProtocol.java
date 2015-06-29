package com.crazydude.truckdashboard;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

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

    enum Signals {
        BUTTON_PRESS, SLIDER_CHANGE;
    }

    enum Sliders {

        HID_USAGE_X(0x30);

        private int number;

        Sliders(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

    @Background
    public void connect(String host, int port) {
        try {
            mSocket = SocketFactory.getDefault().createSocket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSeekbarSignal(int position, int controlId) {
        sendSignal(Signals.SLIDER_CHANGE.ordinal(), controlId, false, position);
    }

    public void sendButtonSignal(int id, boolean isPressed) {
        sendSignal(Signals.BUTTON_PRESS.ordinal(), id, isPressed, 0);
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
