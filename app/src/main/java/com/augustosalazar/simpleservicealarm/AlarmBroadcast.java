package com.augustosalazar.simpleservicealarm;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MyService.service != null &&
                MyService.service.isRunning())
            MyService.service.doSomething();
    }
}
