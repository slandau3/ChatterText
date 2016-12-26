/**
 * @file MainActivity.java
 * @author Steven Landau
 * @brief Have you ever wanted to not talk to someone but are too nice to just ignore them?
 * This android 6.0 (Marshmellow) program detects when your phone receives a textmessage
 * from the specified person. The program will then pass the contents of that message over to
 * the selected chat bot api. The chat bots response will be sent back to the original sender.
 */
package edu.rit.steven_conorcs.chattertext;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    public static String NUMBER = "";
    public static int USE_BOT = 0; // 0 for no, 1 for yes
    public static final String TAG = "WALK-THROUGH";
    public static Bot clyde = null;
    public static int BotType = 0; // defaults to Cleverbot
    public static int delay = 0;
    private CheckBox check;
    private TextView phone;
    private RadioGroup bots;
    private EditText startTime, endTime;
    private AlertDialog.Builder dlgAlert;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // Not safe but works. I will do networking the proper way at some point.
        StrictMode.setThreadPolicy(policy);  // Overrides the default network thread.
        try {
            clyde = new Bot();
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
        setContentView(R.layout.activity_main);

        getPermissions();
        dlgAlert = new AlertDialog.Builder(this);


        // Create Radio Buttons/group
        bots = (RadioGroup) findViewById(R.id.rg1);
        assert bots != null;
        bots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bots.getCheckedRadioButtonId() == R.id.clevBot) {
                    BotType = 0;
                    try {
                        clyde = new Bot();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (bots.getCheckedRadioButtonId() == R.id.panBot) {
                    BotType = 1;
                    try {
                        clyde = new Bot();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (bots.getCheckedRadioButtonId() == R.id.jabBot) {
                    BotType = 2;
                    try {
                        clyde = new Bot();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // Create Phone bar
        phone = (TextView) findViewById(R.id.phoneNumber);

        // Create Time Delay
        startTime = (EditText) findViewById(R.id.startTime);


        endTime = (EditText) findViewById(R.id.endTime);

        // Create and set check box
        check = (CheckBox) findViewById(R.id.checkBox);
        assert check != null;
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (phone.getText().toString().length() == 0) {  // I know it's not the best thing to check but ill get to it later.
                    check.setChecked(false);
                    //startActivity(new Intent(MainActivity.this, InvalidNumberEntry.class));
                    // Alert the user that a phone # is required
                }
                else if (isChecked) {
                    USE_BOT = 1;
                    NUMBER = phone.getText().toString();
                } else {
                    USE_BOT = 0;
                }

                // Configure the time delay
                int minWait = 0;
                try {
                    String givenWait = startTime.getText().toString();
                    if (!givenWait.equals("")) {
                        minWait = Integer.parseInt(givenWait);
                    }
                } catch (NullPointerException npe) {
                    // Leave minWait as 0
                }

                int maxWait = 0;
                try {
                    String givenWait = endTime.getText().toString();
                    if (!givenWait.equals("")) {
                        maxWait = Integer.parseInt(givenWait);
                    }
                } catch (NullPointerException npe) {
                    // Leave maxWait as 0
                }

                if (minWait > maxWait) {
                    dlgAlert.setMessage("\"From\" time must be greater than or equal to \"To\"");
                    dlgAlert.setTitle("Input Error");
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    USE_BOT = 0;
                    check.setChecked(false);
                } else {
                    int secondsDelay = ThreadLocalRandom.current().nextInt(minWait, maxWait + 1);
                    delay = secondsDelay * 1000;  // Milliseconds delay
                }

            }
        });

        // Time Delay



    }

    /**
     * Checks to see if the app has been granted several permissions.
     * All permissions are necessary in order to function properly.
     */
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

    /**
     * Deprecated. This method is now used in TextReceiver. It may one day be used again in MainActivity.
     * @param number The phone number to send the SMS message
     * @param msg The content of the SMS message.
     */
    public void sendMessage(String number, String msg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, msg, null, null);
        Log.i(TAG, "send message OK");
    }
}
