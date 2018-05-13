package com.zfscgy.test.test;
import android.os.Handler;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
/**
 * Created by HP on 2018/3/18.
 */

public class Client implements Runnable
{
    private String ipString = "116.85.54.118";
    private int port = 5050;
    private SocketReceiveThread socketReceiveThread;
    public Socket socket = new Socket();
    public Client(SocketReceiveThread _socketReceiveThread)
    {
        socketReceiveThread = _socketReceiveThread;
    }
    @Override
    public void run()
    {
        try
        {
            socket.connect(new InetSocketAddress(ipString, port));
            socketReceiveThread.SetSocket(socket);
        }
        catch (Exception e)
        {
            Log.e("error",e.toString());
            return;
        }
        new Thread(socketReceiveThread).start();
    }
}
