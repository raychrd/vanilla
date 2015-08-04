package ch.blinkenlights.android.vanilla;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ray on 15/8/2.
 */
public class TimeTaskListActivity extends ListActivity {

    public  static final int MENU_OPTION_EDIT = 1;
    public  static final int MENU_OPTION_DELETE = 2;
    public  static final String TIME_TASK_DELETE_COMPLETED = "timetaskdeletecompleted";
//    PlaybackService mPlaybackService = null;
    TimeTaskTimeline mTimwTaskline = null;
    SimpleDateFormat sp = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
    List<Map<String, String>> list ;
    ListView listView;
    private int  currentItemNumber = 0;
    BaseAdapter adapter;
    MyReceiver myReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setTheme(this, R.style.BackActionBar);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.timetask_list);

        getTimeTaskList();
        list = getData();
        adapter = new SimpleAdapter(this,list,R.layout.timetasklistitem,new String[] {"title","date"},new int[] {R.id.ItemTitle,R.id.ItemDate});
//        adapter =
        setListAdapter(adapter);

        listView = getListView();
        registerForContextMenu(listView);

        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TimeSettingActivity.EDIT_TIME_SETTING);
        filter.addAction(TIME_TASK_DELETE_COMPLETED);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = PlaybackService.getSettings(this);
        Window window = getWindow();

        if (prefs.getBoolean(PrefKeys.DISABLE_LOCKSCREEN, false))
           window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
        Map<String, String> map ;
        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        int size = mTimwTaskline==null?0:mTimwTaskline.getSize();
        for (int i = 0; i < size ;i++){
            map = new HashMap<String, String>();
            map.put("title",mTimwTaskline.get(i).title);
            String date = sp.format(mTimwTaskline.get(i).date);
            map.put("date","开始时间:"+date);
            mList.add(map);
        }
        return mList;
    }

    void getTimeTaskList() {
        mTimwTaskline = PlaybackService.get();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String title = (list.get(info.position)).get("title");
        menu.setHeaderTitle(title==null?"选项...":title+"...");
        menu.add(0, MENU_OPTION_EDIT, 0, "修改");
        menu.add(0,MENU_OPTION_DELETE,0,"删除");
        currentItemNumber = info.position ;//maybe
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case MENU_OPTION_EDIT:
                Intent timeSettingIntent = new Intent();
                timeSettingIntent.setClass(this, TimeSettingActivity.class);
                timeSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                timeSettingIntent.putExtra("mode", TimeTask.TIMETASK_MODE_EDIT);
                timeSettingIntent.putExtra("number", currentItemNumber);
                startActivity(timeSettingIntent);

                break;
            case MENU_OPTION_DELETE:
                Intent intent = new Intent();
                intent.setAction(TimeTask.DELETE_TIMETASK);
                intent.putExtra("number", currentItemNumber);
                sendBroadcast(intent);
                Toast.makeText(TimeTaskListActivity.this, "已删除", Toast.LENGTH_SHORT).show();
//                finish();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(TimeSettingActivity.EDIT_TIME_SETTING)) {
                getTimeTaskList();
                list = getData();
                adapter = new SimpleAdapter(TimeTaskListActivity.this, list, R.layout.timetasklistitem, new String[]{"title", "date"}, new int[]{R.id.ItemTitle, R.id.ItemDate});
                getListView().setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else if (intent.getAction().equals(TIME_TASK_DELETE_COMPLETED)) {
                getTimeTaskList();
                list = getData();
                adapter = new SimpleAdapter(TimeTaskListActivity.this,list,R.layout.timetasklistitem,new String[] {"title","date"},new int[] {R.id.ItemTitle,R.id.ItemDate});
                getListView().setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(myReceiver);
        } catch (IllegalArgumentException e) {

        }
    }
}
