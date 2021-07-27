package com.risuplabs.ureport.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.risuplabs.ureport.R;
import com.risuplabs.ureport.ui.dashboard.DashBoardActivity;
import com.risuplabs.ureport.ui.opinions.flow.RunFlowActivity;
import com.risuplabs.ureport.ui.opinions.flow_list.FlowListActivity;
import com.risuplabs.ureport.utils.IntentConstant;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class ReminderBroadcast extends BroadcastReceiver {

    Intent intent1;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    public void onReceive(Context context, Intent intent) {


        setAlarm(context);

        int response = intent.getIntExtra("intent",0);

        if(response == 1){
            showCustomNotification(context);
        }else if(response == 2){
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(522);
        }
    }


    public void showCustomNotification(Context context){

        Intent intent = new Intent(context, FlowListActivity.class);
        intent.putExtra(IntentConstant.SUBMISSION_INTENT,0);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissIntent = new Intent(context, ReminderBroadcast.class);
        dismissIntent.putExtra("intent",2);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        view.setOnClickPendingIntent(R.id.submit,clickPendingIntent);
        view.setOnClickPendingIntent(R.id.dismiss,dismissPendingIntent);

        Notification notification = new NotificationCompat.Builder(context,"notify")
                .setSmallIcon(R.drawable.v1_ureport_icon)
                .setCustomContentView(view)
                .setCustomBigContentView(view)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(522, notification);

    }

    public void setAlarm(Context context){
        intent1 = new Intent(context,ReminderBroadcast.class);
        intent1.putExtra("intent",1);
        pendingIntent = PendingIntent.getBroadcast(context,422,intent1,0);

        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        long timeAtButtonClick = System.currentTimeMillis();
        long alarmTime = timeAtButtonClick + 20000;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,16);
        calendar.set(Calendar.MINUTE,41);
        calendar.set(Calendar.SECOND,0);

        Log.d("Calender", "setReminder: "+calendar.getTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent), pendingIntent);
            alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(alarmTime, pendingIntent), pendingIntent);
            Log.d("AlarmActivity", "first");
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("AlarmActivity", "second");
        }
    }



}
