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

    @Background
    public void connect(String host, int port) {
        try {
            mSocket = SocketFactory.getDefault().createSocket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background
    public void sendSeekbarSignal(int position) {
        if (mSocket != null && mSocket.isConnected()) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(mSocket.getOutputStream());
                dataOutputStream.writeInt(position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
