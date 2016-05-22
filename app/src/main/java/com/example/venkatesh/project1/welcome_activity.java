package com.example.venkatesh.project1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
/**
 * Created by venkatesh on 5/23/16.
 */
public class welcome_activity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    SharedPreferences msharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         msharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        getSupportActionBar().hide();
        setContentView(R.layout.welcome_activity);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor editor = msharedPreferences.edit();
        if (msharedPreferences.getBoolean("FIRST_TIME", true)) {
            // Do first run stuff here then set 'firstrun' as false
            //strat  DataActivity beacuase its your app first run
            // using the following line to edit/commit prefs
            editor.putBoolean("FIRST_TIME", false);
            editor.apply();
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(welcome_activity.this, MainActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        else {
            startActivity(new Intent(welcome_activity.this , MainActivity.class));
            finish();
        }
    }
}
