package com.example.lampara;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.annotation.Nullable;

public class TimerService extends Service {
    public static final String ACTION_UPDATE_TIMER = "com.example.lampara.UPDATE_TIMER";
    public static final String EXTRA_TIME_REMAINING = "com.example.lampara.TIME_REMAINING";

    private Handler handler = new Handler();
    private long endTime;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = SystemClock.elapsedRealtime();
            long timeRemaining = endTime - currentTime;

            if (timeRemaining > 0) {
                Intent intent = new Intent(ACTION_UPDATE_TIMER);
                intent.putExtra(EXTRA_TIME_REMAINING, timeRemaining);
                sendBroadcast(intent);
                handler.postDelayed(this, 1000); // Repite cada segundo
            } else {
                stopSelf();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int hours = intent.getIntExtra("hours", 0);
        int minutes = intent.getIntExtra("minutes", 0);
        int seconds = intent.getIntExtra("seconds", 0);

        long duration = (hours * 3600 + minutes * 60 + seconds) * 1000;
        endTime = SystemClock.elapsedRealtime() + duration;

        handler.post(timerRunnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(timerRunnable);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
