package com.example.shbae.mythread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ValueHandler handler = new ValueHandler();

    Handler handler2 = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //BackgroundThread thread = new BackgroundThread();
                //thread.start();

                new Thread(new Runnable() {
                    boolean runing = false;
                    int value = 0;

                    @Override
                    public void run() {
                        runing = true;
                        while(runing) {
                            value += 1;

                            handler2.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText("현재 값; " + value);
                                }
                            });

                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {}
                        }
                    }
                }).start();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //textView.setText("현재 값; " + value);
            }
        });

    }

    class BackgroundThread extends Thread {
        boolean runing = false;
        int value = 0;

        public void run() {
            runing = true;
            while(runing) {
                value += 1;
                //textView.setText("현재 값; " + value);
                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle);
                handler.sendMessage(message);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
            }
        }
    }

    class ValueHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("현재 값; " + value);
        }
    }
}
