package com.lucifercb.administrator.timingdemoapp;

import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeUtils {
    private static String TAG = "<<<";
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private Handler mHandler = null;
    private static long count = 0;
    private boolean isPause = false;
    private static int delay = 1000;  //1s
    private static int period = 1000;  //1s
    private static final int UPDATE_TEXTVIEW = 0;
    TextView mTextView;

    public TimeUtils(TextView mTextView) {
        this.mTextView = mTextView;
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXTVIEW:
                        updateTextView();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    /**
     * 暂停时间
     */
    public void pauseTimer() {
        isPause = !isPause;
    }

    /**
     * 更新控件时间
     */
    private void updateTextView() {
        int i = 1000;
        long time = count * i;
        //需要导入import android.text.format.DateFormat;
//        CharSequence sysTimeStr = DateFormat.format("mm:ss", time);
        Log.d("TAG", "time -> " + time);

        //方法一,格式转换为HH:mm:ss
//        CharSequence sysTimeStr = formatTimeS(count);

        //方法二,格式转换为
        CharSequence sysTimeStr = formatDuring(time);
        mTextView.setText(String.valueOf(sysTimeStr));
    }

    /**
     * 开始计时
     */
    public void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "count: " + String.valueOf(count));
                    sendMessage(UPDATE_TEXTVIEW);

                    do {
                        try {
                            Log.i(TAG, "sleep(1000)...");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    } while (isPause);

                    count++;
                }
            };
        }

        if (mTimer != null && mTimerTask != null)
            mTimer.schedule(mTimerTask, delay, period);
    }

    /**
     * 结束计时
     */
    public void stopTimer() {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        count = 0;
    }

    public void sendMessage(int id) {
        if (mHandler != null) {
            Message message = Message.obtain(mHandler, id);
            mHandler.sendMessage(message);
        }
    }

    public String formatTimeS(long seconds) {
        int temp = 0;
        StringBuffer sb = new StringBuffer();
//        if (seconds > 3600) {
//            temp = (int) (seconds / 3600);
//            sb.append((seconds / 3600) < 10 ? "0" + temp + ":" : temp + ":");
//            temp = (int) (seconds % 3600 / 60);
//            changeSeconds(seconds, temp, sb);
//        } else {
//            temp = (int) (seconds % 3600 / 60);
//            changeSeconds(seconds, temp, sb);
//        }

        temp = (int) (seconds / 3600);
        sb.append((seconds / 3600) < 10 ? "0" + temp + ":" : temp + ":");
        temp = (int) (seconds % 3600 / 60);
        changeSeconds(seconds, temp, sb);
        Log.d("TAG", "seconds ->" + seconds);
        Log.d("TAG", sb.toString());
        return sb.toString();
    }

    private void changeSeconds(long seconds, int temp, StringBuffer sb) {
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
        temp = (int) (seconds % 3600 % 60);
        sb.append((temp < 10) ? "0" + temp : "" + temp);
    }


    /**
     * 要转换的毫秒数
     * @param mss
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }
    /**
     *
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return  输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }



    //--End Page
}
