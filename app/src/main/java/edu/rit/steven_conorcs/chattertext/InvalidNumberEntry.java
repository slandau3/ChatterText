package edu.rit.steven_conorcs.chattertext;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by Steven Landau on 9/30/2016.
 */

public class InvalidNumberEntry extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * This class is not currently in use.
         */
        super.onCreate(savedInstanceState);

        setContentView(R.layout.invalidnumwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int) (width*.8),(int) (height*.2));

    }
}
