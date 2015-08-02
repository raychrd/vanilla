package ch.blinkenlights.android.vanilla;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Ray on 15/8/1.
 */
public class TimeTask implements Comparable<Object> {
    public Date date;
    public QueryTask queryTask;
    public String title;

    TimeTask(){}
    TimeTask(Date d,QueryTask q,String t){
        date = d;
        queryTask = q;
        title = t;
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
