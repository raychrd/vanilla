package ch.blinkenlights.android.vanilla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Ray on 15/8/1.
 */
public class TimeSettingActivity extends Activity {
    public static String SEND_TIME_SETTING = "senddatesetting";
    public static String EDIT_TIME_SETTING = "editdatesetting";
    TimePicker timePicker;
    Date now;
    Button button;
    int hours;
    int minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setTheme(this, R.style.BackActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_setting);

        now = new Date(System.currentTimeMillis());
        now.setSeconds(0);


        timePicker = (TimePicker) findViewById(R.id.timePicker);
        button = (Button) findViewById(R.id.button_start_timer);
        hours = now.getHours();
        minutes = now.getMinutes();

        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hours = hourOfDay;
                minutes = minute;
            }
        });

        int mode = getIntent().getIntExtra("mode",0);
        switch (mode) {
            case TimeTask.TIMETASK_MODE_EDIT:
                button.setText("修改");
                final int itemNumber = getIntent().getIntExtra("number",-2);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //待修改
                        if ((hours < now.getHours()) || (hours == now.getHours() && minutes < now.getMinutes())) {
                            Toast.makeText(TimeSettingActivity.this,"设定时间不能早于当前时间",Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(SEND_TIME_SETTING);
                            intent.putExtra("hour", hours);
                            intent.putExtra("minute", minutes);
                            intent.putExtra("number", itemNumber);
                            intent.setAction(EDIT_TIME_SETTING);
                            sendBroadcast(intent);
//                            sendBroadcast(EditIntent);
                            Toast.makeText(TimeSettingActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
                break;
            case TimeTask.TIMETASK_MODE_NEW:
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //待修改
                        if ((hours < now.getHours()) || (hours == now.getHours() && minutes < now.getMinutes())) {
                            Toast.makeText(TimeSettingActivity.this,"设定时间不能早于当前时间",Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent();
                            intent.setAction(SEND_TIME_SETTING);
                            intent.putExtra("hour", hours);
                            intent.putExtra("minute", minutes);
                            intent.putExtra("number",-1);
                            sendBroadcast(intent);
                            Toast.makeText(TimeSettingActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
                break;
            default:
                break;
        }











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


}
