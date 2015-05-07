package com.augustosalazar.simpleservicealarm;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class MyService  extends Service {

    private AlarmManager alarmManager;
    private PendingIntent pendingAlarm;
    private int freqSeconds;
    public static MyService service;
    private static boolean isRunning = false;
    private NotificationManager nm;
    private Notification notification;
    private String TAG = MyService.class.getSimpleName();
    private int counter = 0;



    @Override
    public void onCreate() {
        super.onCreate();

        showNotification();

        MyService.service = this;

        freqSeconds = 2;

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        pendingAlarm = PendingIntent.getBroadcast(this, 0, new Intent(this, AlarmBroadcast.class), 0);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), freqSeconds * 1000, pendingAlarm);

        isRunning = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (pendingAlarm != null)
            alarmManager.cancel(pendingAlarm);

        isRunning = false;

        nm.cancelAll();
    }

    public static boolean isRunning() {
        return isRunning;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void doSomething() {

        Log.d(TAG, "doSomething "+counter);

        counter += 1;
    }

    private void showNotification() {
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(R.mipmap.ic_launcher,
                "Simple Service Alarm", System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        notification.setLatestEventInfo(this, "Simple Service Alarm",
                "Update every 3 secs ", contentIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        nm.notify(1, notification);
    }
}
