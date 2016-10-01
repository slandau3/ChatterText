package edu.rit.steven_conorcs.chattertext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
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

        if (intent.getAction().equals(SMS_RECEIVED) || intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Bundle intentExtras = intent.getExtras();
            if (intentExtras != null) {
                Object[] sms = (Object[]) intentExtras.get("pdus");
                assert sms != null;
                for (int i = 0; i < sms.length; ++i) {
                    String format = intentExtras.getString("format");
                    SmsMessage smsMessage = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);
                    }

                    assert smsMessage != null;
                    String contents = smsMessage.getMessageBody();
                    String sender = smsMessage.getOriginatingAddress();
                    if (!MainActivity.NUMBER.equals(sender) || MainActivity.USE_BOT == 0) {
                        break;
                    }

                    try {
                        clyde.msg_received = contents;
                        clyde.nmbr_sender = sender;
                        thoughtMessage = clyde.think(contents);
                        sendMessage(MainActivity.NUMBER, thoughtMessage);
                        // have bot send message. then add something to the interface to toggle the bot and have it set the number.. etc...

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public void update() {
        clyde = MainActivity.clyde;
    }
    public void sendMessage(String number, String msg) throws InterruptedException {
        wait(20);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, msg, null, null);
    }
}
