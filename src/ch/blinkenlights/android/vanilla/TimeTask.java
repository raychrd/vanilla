package ch.blinkenlights.android.vanilla;

import java.security.PublicKey;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Ray on 15/8/1.
 */
public class TimeTask implements Comparable<Object> {
    public Date date;
    public QueryTask queryTask;
    public String title;

    public final static int TIMETASK_MODE_NEW = 1;
    public final static int TIMETASK_MODE_EDIT = 2;
    public final static String DELETE_TIMETASK = "removetimetask";


    TimeTask() {
    }

    TimeTask(Date d, QueryTask q, String t) {
        date = d;
        queryTask = q;
        title = t;
    }

    @Override
    public int compareTo(Object another) {
        if (another != null && another instanceof TimeTask) {
            TimeTask mTimeTask = (TimeTask) another;
            int result = PlaybackService.dateCompare(date, mTimeTask.date);
            if (result == 1) {
                return 1;
            } else if (result == -1) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
