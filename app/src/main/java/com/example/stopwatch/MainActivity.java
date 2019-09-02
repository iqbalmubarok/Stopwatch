package com.example.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv_waktu;
    Button start, pause, reset, lap;
    long MilliSecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSecond;
    ListView listView;

    String[] ListElements = new String[] { };

    List<String> ListElementsArrayList;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_waktu = findViewById(R.id.tv_waktu);
        start = findViewById(R.id.buttonMulai);
        pause = findViewById(R.id.buttonStop);
        reset = findViewById(R.id.buttonReset);
        lap = findViewById(R.id.buttonLap);
        listView = findViewById(R.id.listView);

        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ListElementsArrayList);

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MilliSecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MilliSecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                MilliSecond = 0;

                tv_waktu.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListElementsArrayList.add(tv_waktu.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MilliSecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MilliSecondTime;
            Seconds = (int) (UpdateTime/1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;

            MilliSecond = (int) (UpdateTime % 1000);

            tv_waktu.setText("" + Minutes + ":" + String.format("%02d", Seconds) + ":"
                                                + String.format("%03d", MilliSecond));

            handler.postDelayed(this, 0);
        }
    };
}
