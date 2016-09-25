package edu.rit.steven_conorcs.chattertext;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1); // Request permission to send sms messages
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1); // Request permission to receive sms

        Button b1 = (Button) findViewById(R.id.test_button); // creates the main button we are going to use


    }


    public void sendMessage(String number, String msg) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, msg, null, null);
    }
}
