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

        ChatterBot notQuiteClyde;
        if (MainActivity.BotType == 0) {
           notQuiteClyde = factory.create(ChatterBotType.CLEVERBOT);
        } else if (MainActivity.BotType == 1) {
            notQuiteClyde = factory.create(ChatterBotType.PANDORABOTS);
        } else {
            notQuiteClyde = factory.create(ChatterBotType.JABBERWACKY);
        }

        clyde = notQuiteClyde.createSession();
    }

    public String think(String think) throws Exception {
        // TODO: The link is depricated so I need to fix this bot nonsense.
        return clyde.think(think);
    }
}