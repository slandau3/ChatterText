package edu.rit.steven_conorcs.chattertext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Steven Landau on 9/27/2016.
 */
public class TextReceiver extends BroadcastReceiver{
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public String thoughtMessage;
    private Bot clyde = MainActivity.clyde;




    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("12345" + intent.getAction());
        if (intent.getAction().equals(SMS_RECEIVED) || intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Bundle intentExtras = intent.getExtras();
            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get("pdus");
                for (int i = 0; i < sms.length; ++i) {
                    String format = intentExtras.getString("format");
                    SmsMessage smsMessage = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);
                    }

                    assert smsMessage != null;
                    String contents = smsMessage.getMessageBody();
                    String sender = smsMessage.getOriginatingAddress();



                    try {
                        Log.i(MainActivity.TAG, "about to call the bot OK");
                        clyde.msg_received = contents;
                        clyde.nmbr_sender = sender;
                        String thoughts = clyde.think("");
                        thoughtMessage = thoughts;
                        System.out.println("12345" + thoughts);
                        Log.i(MainActivity.TAG, thoughtMessage);
                        Log.i(MainActivity.TAG, "Thought message received OK");
                        // have bot send message. then add something to the interface to toggle the bot and have it set the number.. etc...
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
