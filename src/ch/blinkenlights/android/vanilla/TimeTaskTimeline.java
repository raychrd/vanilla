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
        timeTaskList = new LinkedList<TimeTask>() {
            @Override
            public void add(int location, TimeTask object) {

            }

            @Override
            public boolean add(TimeTask object) {
                return false;
            }

            @Override
            public boolean addAll(int location, Collection<? extends TimeTask> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends TimeTask> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object object) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public TimeTask get(int location) {
                return null;
            }

            @Override
            public int indexOf(Object object) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<TimeTask> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object object) {
                return 0;
            }

            @Override
            public ListIterator<TimeTask> listIterator() {
                return null;
            }

            @Override
            public ListIterator<TimeTask> listIterator(int location) {
                return null;
            }

            @Override
            public TimeTask remove(int location) {
                return null;
            }

            @Override
            public boolean remove(Object object) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public TimeTask set(int location, TimeTask object) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public List<TimeTask> subList(int start, int end) {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] array) {
                return null;
            }
        };
    }


    public boolean add(TimeTask t) {
        if (timeTaskList.size() == 0) {
            timeTaskList = new LinkedList<TimeTask>();
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
        }
        return false;
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

}
