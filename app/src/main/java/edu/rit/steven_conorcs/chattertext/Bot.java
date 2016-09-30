package edu.rit.steven_conorcs.chattertext;

import android.util.Log;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

/**
 * Created by Steven Landau on 9/27/2016.
 */
public class Bot {
    public String msg_received;
    public String nmbr_sender;


    private ChatterBotSession clyde;

    public Bot() throws Exception {
        ChatterBotFactory factory = new ChatterBotFactory();
        Log.i(MainActivity.TAG, "here8");
        ChatterBot notQuiteClyde = factory.create(ChatterBotType.CLEVERBOT);
        Log.i(MainActivity.TAG, "Here9");
        clyde = notQuiteClyde.createSession();
        Log.i(MainActivity.TAG, "here10");
    }

    public String think(String think) throws Exception { // think will default to null. I only included it in case anyone wanted to call it outside of TextReceiver.
        if (think.length() == 0) {
            return clyde.think(msg_received);
        } else {
            return clyde.think(think);
        }
    }
}