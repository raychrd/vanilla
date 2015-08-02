package ch.blinkenlights.android.vanilla;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ray on 15/8/2.
 */
public class TimeTaskListActivity extends ListActivity {
//    PlaybackService mPlaybackService = null;
    TimeTaskTimeline mTimwTaskline = null;
    SimpleDateFormat sp = new SimpleDateFormat("yyyy年MM月dd日hh时mm分");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setTheme(this, R.style.BackActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetask_list);

        getTimeTaskList();

        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.timetasklistitem,new String[] {"title","date"},new int[] {R.id.ItemTitle,R.id.ItemDate});
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public List<Map<String,String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map ;

        int size = mTimwTaskline==null?0:mTimwTaskline.getSize();
        for (int i = 0; i < size ;i++){
            map = new HashMap<String, String>();
            map.put("title","名称:"+mTimwTaskline.get(i).title);
            String date = sp.format(mTimwTaskline.get(i).date);
            map.put("date","时间:"+date);
            list.add(map);
        }
        return list;
    }

    void getTimeTaskList() {
        mTimwTaskline = PlaybackService.get();
    }
}
