package ch.blinkenlights.android.vanilla;

import android.animation.TimeAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Ray on 15/8/1.
 */
public class TimeSetting extends Activity {
    public static String SEND_TIME_SETTING = "ch.ray.sendtime";
    TimePicker timePicker;
    Button button;
    int hours;
    int minutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeHelper.setTheme(this, R.style.BackActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_setting);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        button = (Button) findViewById(R.id.button_start_timer);

        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hours = hourOfDay;
                minutes = minute;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(SEND_TIME_SETTING);
                intent.putExtra("hour", hours);
                intent.putExtra("minute", minutes);
                sendBroadcast(intent);
                Toast.makeText(TimeSetting.this,"设置成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


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
