package ch.blinkenlights.android.vanilla;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Ray on 15/7/31.
 */
public class testService extends Service {

    private Handler objHandler = new Handler();
    private int intCounter = 0;
    private static final String TAG = "testService";
    private NotificationManager notificationManager;
    private Runnable mTasks = new Runnable() {
        public void run() {
            intCounter++;

            Log.i("HIPPO", "Counter:" + Integer.toString(intCounter));

            objHandler.postDelayed(mTasks, 1000);
        }
    };

    public void onCreate() {
        Log.d(TAG, "============> TService.onCreate");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();
        super.onCreate();
    }

    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "============> TService.onStart");
        objHandler.postDelayed(mTasks, 1000);
        super.onStart(intent, startId);
    }

    public IBinder onBind(Intent intent) {
        Log.i(TAG, "============> TService.onBind");
        return null;
    }

//    public class LocalBinder extends Binder {
//        public TService getService() {
//            return TService.this;
//        }
//    }

    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "============> TService.onUnbind");
        return false;
    }

    public void onRebind(Intent intent) {
        Log.i(TAG, "============> TService.onRebind");
    }

    public void onDestroy() {
        Log.i(TAG, "============> TService.onDestroy");
        notificationManager.cancel(1);
        objHandler.removeCallbacks(mTasks);
        super.onDestroy();
    }

    private void showNotification() {
        Notification notification = new Notification(R.drawable.icon,
                "SERVICE START", System.currentTimeMillis());

        Intent intent = new Intent(this, testService.class);
        intent.putExtra("FLG", 1);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        notification.setLatestEventInfo(this, "SERVICE", "SERVICE START",
                contentIntent);
        notificationManager.notify(1, notification);
    }
}
