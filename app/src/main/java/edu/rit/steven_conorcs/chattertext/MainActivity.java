package edu.rit.steven_conorcs.chattertext;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity { // Program crashes on launch. Need to add some tags.
    public static final String NUMBER = "2154993429"; //need to decide who to test this on.
    public static final String MSG = "test";
    public static int USE_BOT = 1; // 0 for no, 1 for yes
    public static final String TAG = "WALK-THROUGH";
    public static Bot clyde = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "going in try");
        try {
            clyde = new Bot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Creation OK");
        setContentView(R.layout.activity_main);
        /*int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int receivePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        if (receivePermission != PackageManager.PERMISSION_GRANTED || writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS},
                    1);
        }*/
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, 1); // Request permission to send and receive sms messages
        Log.i(TAG, "Permissions OK");
        Button b1 = (Button) findViewById(R.id.test_button); // creates the main button we are going to use
        assert b1 != null;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(NUMBER, MSG);
            }
        });
        Log.i(TAG, "Button creation and set OK");
        CheckBox check = (CheckBox) findViewById(R.id.checkBox);
        assert check != null;
        if (check.isChecked()) {
            USE_BOT = 1;
        } else {
            USE_BOT = 0;
        }
        Log.i(TAG, "checkbox creation and set OK");


        // TODO: need to make it check for a thoughtMessage
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        Log.i(TAG, "End of onCreate");
    }


    public void sendMessage(String number, String msg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, msg, null, null);
        Log.i(TAG, "send message OK");
    }
}
