package edu.rit.steven_conorcs.chattertext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity { // Program crashes on launch. Need to add some tags.
    public static final String NUMBER = "2243256933"; //need to decide who to test this on.
    public static final String MSG = "test";
    public static int USE_BOT = 0; // 0 for no, 1 for yes
    public static final String TAG = "WALK-THROUGH";
    public static Bot clyde = null;
    private CheckBox check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // Not safe but works
        StrictMode.setThreadPolicy(policy);  // Overrides the default network thread.
        try {
            clyde = new Bot();
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
        setContentView(R.layout.activity_main);

        getPermissions();

        Button b1 = (Button) findViewById(R.id.test_button); // creates the main button we are going to use
        assert b1 != null;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(NUMBER, MSG);
            }
        });
        check = (CheckBox) findViewById(R.id.checkBox);
        assert check != null;
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    USE_BOT = 1;
                } else {
                    USE_BOT = 0;
                }
            }
        });
    }

    private void getPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }

    }
    public void sendMessage(String number, String msg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, msg, null, null);
        Log.i(TAG, "send message OK");
    }
}
