package com.selcukseltug.alarmclockactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Uri alarMelodie = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarMelodie == null)
        {
            alarMelodie = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context,alarMelodie);
        ringtone.play();
    }
}
