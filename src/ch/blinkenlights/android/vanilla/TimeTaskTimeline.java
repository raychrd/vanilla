package ch.blinkenlights.android.vanilla;

import android.util.Log;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Ray on 15/8/1.
 */
public class TimeTaskTimeline {
    LinkedList<TimeTask> timeTaskList = null;
    TimeTaskTimeline(){
        timeTaskList = new LinkedList<TimeTask>();
    }


    public boolean add(TimeTask t) {
        if (timeTaskList.size() == 0) {
//            timeTaskList = new LinkedList<TimeTask>();
            timeTaskList.addFirst(t);
            Log.i("timetasktimeline", "addfirst");
            return true;
        } else {
            for (int i = 0;i < timeTaskList.size();i++) {
                if (t.compareTo(timeTaskList.getFirst()) == 1) {
                    if (i < timeTaskList.size()-1) {
                        continue;
                    } else {
                        timeTaskList.addLast(t);
                        return true;
                    }
                } else {
                    timeTaskList.add(i,t);
                    return true;
                }
            }
            return false;
        }

    }

    boolean isEmpty() {
        if (timeTaskList.size() != 0) {
            return false;
        } else {
            return true;
        }
    }

    Date getFirstDate() {
        return timeTaskList.getFirst().date;
    }

    QueryTask getFirstQueryTask() {
        return timeTaskList.getFirst().queryTask;
    }

    void removeFirstTimeTask() {
        timeTaskList.removeFirst();
    }

    int getSize() {
        return timeTaskList.size();
    }

    public TimeTask get(int i) {
        return timeTaskList.get(i);
    }

}
