package edu.rit.steven_conorcs.chattertext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Steven Landau on 9/27/2016.
 */
public class TextReceiver extends BroadcastReceiver{
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public String thoughtMessage;
    private Bot clyde;
    public TextReceiver() throws Exception {
        clyde = new Bot();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();

            SmsMessage[] messages = null;
            String sender;
            if (bundle != null) { // We must make sure the bundle is not null
                Object[] pdus = (Object[]) bundle.get("pdus"); // Should probably figure out what pdus is
                messages = new SmsMessage[pdus.length]; // Might need to surround all this stuff with a try/catch block
                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]); // Why is createFromPdu crossed out?
                    sender = messages[i].getOriginatingAddress();
                    String contents = messages[i].getMessageBody();
                    if (sender.equals(MainActivity.NUMBER) && MainActivity.USE_BOT == 1) {
                        try {
                            clyde.msg_received = contents;
                            clyde.nmbr_sender = sender;
                            String thoughts = clyde.think("");
                            thoughtMessage = thoughts;
                            // have bot send message. then add something to the interface to toggle the bot and have it set the number.. etc...
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
