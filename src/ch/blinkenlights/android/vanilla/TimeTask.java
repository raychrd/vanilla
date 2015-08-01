package ch.blinkenlights.android.vanilla;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Ray on 15/8/1.
 */
public class TimeTask implements Comparable<Object> {
    public Date date;
    public QueryTask queryTask;

    TimeTask(){}
    TimeTask(Date d,QueryTask q){
        date = d;
        queryTask = q;
    }

    @Override
    public int compareTo(Object another) {
        if (another != null && another instanceof TimeTask) {
            TimeTask mTimeTask = (TimeTask) another;
            int result = date.compareTo(mTimeTask.date);
            if (result == 1) {
                return 1;
            } else {
                return  -1;
            }
        } else {
            return -1;
        }
    }
}
