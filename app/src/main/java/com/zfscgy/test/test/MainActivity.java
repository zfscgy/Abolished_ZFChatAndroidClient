package com.zfscgy.test.test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    private TextView outputText;
    private TextView inputText;
    private Button button_connect;
    private Button button_send;
    private Button button_clear;
    private ScrollView scrollView;
    private Client client;
    private SocketReceiveThread socketReceiveThread;
    private Handler sender;
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message message)
        {
            outputText.append(message.obj.toString() + "\r\n");
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        outputText = (TextView) findViewById(R.id.textView);
        inputText =  (TextView) findViewById(R.id.textView_2);
        button_connect = (Button) findViewById(R.id.button_connect);
        button_send = (Button) findViewById(R.id.button_send);
        button_clear = (Button) findViewById(R.id.button_clear);
        button_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect();
            }
        });
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send();
            }
        });
        button_clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                outputText.setText("");
            }
        });

    }

    private void Connect()
    {
        if(client!= null && client.socket.isConnected())
        {
            return;
        }
        socketReceiveThread = new SocketReceiveThread(handler);
        client = new Client(socketReceiveThread);
        new Thread(client).start();
    }

    private void Send()
    {
        if(socketReceiveThread == null)
        {
            return;
        }
        byte[] sendingBytes = inputText.getText().toString().getBytes(StandardCharsets.UTF_8);
        socketReceiveThread.Send(sendingBytes);
        inputText.setText("");
    }
}
