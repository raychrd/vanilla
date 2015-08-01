package ch.blinkenlights.android.vanilla;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Ray on 15/7/31.
 */
public class testServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, testService.class);
        // To put activity on the top of the stack since activity is launched from context outside activity
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // EDITED
        i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.startService(i);
    }
}
