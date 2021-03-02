package com.selcukseltug.alarmclockactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView timeTextView, timeDataTextView;
    Handler handle = null;
    Runnable runnable = null;
    String time;
    Button alarmButton;
    Calendar alarmCalender;
    TextClock txtAlarmClock;
    private TimePickerDialog timePickerDialog;

    final static int operation_code = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeTextView = findViewById(R.id.timeTextView);
        timeDataTextView = findViewById(R.id.timeDataTextView);
        alarmButton = findViewById(R.id.alarmButton);
        alarmButton.setOnClickListener(this);

        final SimpleDateFormat form = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        time = form.format(new Date());
        timeTextView.setText(time);

        handle = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {


                time = form.format(new Date());
                timeTextView.setText(time);
                handle.postDelayed(runnable, 1000);

            }
        };
        runnable.run();


    }

    @Override
    public void onClick(View view) {
        fddd(true);


    }


    private void fddd(boolean dayhaour) {

        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(MainActivity.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), dayhaour);
        timePickerDialog.setTitle(getApplicationContext().getString(R.string.set_alarm));
        timePickerDialog.show();
    }


    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calNow.set(Calendar.MILLISECOND, 0);
            if (calSet.compareTo(calNow) <= 0) {

                calNow.add(Calendar.DATE, 1);
            }
            setAlarm(calSet);

        }
    };

    private void setAlarm(Calendar alarmCalender) {
        Toast.makeText(getApplicationContext(), "Alarm Ayarlandı!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), operation_code, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);


    }
}