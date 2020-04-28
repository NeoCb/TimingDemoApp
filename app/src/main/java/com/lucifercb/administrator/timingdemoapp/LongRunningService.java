package com.lucifercb.administrator.timingdemoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class LongRunningService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在这里写逻辑
            }
        }).start();

        /**
         * TODO:Alarm机制,具有唤醒CPU的功能
         *
         * 需要注意的是从Android4.4系统开始,Alarm任务的出发时间将会变得不准确,有可能会延迟一段时间,后任务才能得到执行。
         * 这并不是Bug,而是系统在耗电性能方面进行的优化。
         * 系统会自动检测目前有多少Alarm任务存在,然后将触发时间相近的几个任务放在一起执行,
         * TODO：这就可以大大减少CPU被唤醒的次数,从而有效延长电池的使用时间
         *
         * TODO:如果要求Alarm的执行时间必须准确无误。将AlarmManager的setExact()方法来替代set()方法就可以了
         */
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000; //这是一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour; //elapsedRealtime()
        // 系统开机至今所经历的时间的毫秒
        Intent i = new Intent(this, LongRunningService.class);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerAtTime, pi);

        /* AlarmManager
         * ELAPSED_REALTIME_WAKEUP //表示让定时任务的出发时间从系统开机开始算起,但会唤醒CPU
         * ELAPSED_REALTIME //表示让定时任务的出发时间从系统开机开始算起,但不会唤醒CPU
         * */
        return super.onStartCommand(intent, flags, startId);
    }

    //--End Page
}
