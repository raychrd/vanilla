package ch.blinkenlights.android.vanilla;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ray on 15/8/4.
 */
public class AuthaenticationActivity extends Activity {
    Button copyButton;
    Button loginButton;
    Button paste;
    EditText macAddress;
    EditText password;
    private static String KEY = "200920012988.8201030219614.1200930204379.8";
    private static String SALT = "6921168558018";
    private static String TAG = "GENENCRYCOMPLETED";
    private String encrypted = null;
    private String decrypted = null;

    MyReceiver myReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.setTheme(this, R.style.BackActionBar);
        setContentView(R.layout.authentication_activity);

        copyButton = (Button) findViewById(R.id.button_copy);
        loginButton = (Button) findViewById(R.id.button_login);
        paste = (Button) findViewById(R.id.paste);
        macAddress = (EditText) findViewById(R.id.edittext_macaddress);
        password = (EditText) findViewById(R.id.edittext_password);

        final String address = getLocalMacAddress();
        macAddress.setText(address);

        final ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TAG);
        registerReceiver(myReceiver, filter);

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clip.setText(address);
            }
        });

        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText(clip.getText().toString().trim());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setEnabled(false);
                loginButton.setText("验证中");
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Encryption encryption = Encryption.getDefault(KEY, SALT, new byte[16]);
                        decrypted = encryption.decryptOrNull(password.getText().toString());
                        while (decrypted == null) {

                        }
                        Intent intent = new Intent();
                        intent.setAction(TAG);
                        sendBroadcast(intent);
                    }
                };
                Timer timer = new Timer(true);
                timer.schedule(timerTask, 1);

            }
        });
    }

    public String getLocalMacAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getMacAddress();
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = password.getText().toString();
            if (decrypted.equals(macAddress.getText().toString())) {
                SharedPreferences sp = getSharedPreferences("ps", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("pass", true);
                editor.commit();
                Toast.makeText(AuthaenticationActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                Intent library = new Intent();
                library.setClass(AuthaenticationActivity.this, LibraryActivity.class);
                startActivity(library);
                finish();
            } else {
                Toast.makeText(AuthaenticationActivity.this, "验证失败", Toast.LENGTH_LONG).show();
            }
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        return super.onKeyDown(keyCode, event);
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
