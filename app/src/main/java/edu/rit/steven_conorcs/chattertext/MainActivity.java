package edu.rit.steven_conorcs.chattertext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.test_button); // creates the main button we are going to use
        // SMS send stuff. Implement in a seperate function later
        SmsManager sms = SmsManager.getDefault();
    }
}
