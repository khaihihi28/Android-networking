package com.poly.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("zzz", "find... ");
        resultTextView = findViewById(R.id.resultTextView);

        Handler handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String inputString = msg.getData().getString("input_string");
                if (inputString != null) {
                    Log.d("finded", "Found: " + inputString);
                    resultTextView.setText("Found input string: " + inputString);
                } else {
                    Log.d("fail", "fail: ");

                    resultTextView.setText("Input string not found.");
                }
            }
        };

        HashingTask hashingTask = new HashingTask(handler);
        hashingTask.execute("a918c858d2dd1a3c69163267468804bdcd67daf50de8899183efe63e8412438a");
    }
}