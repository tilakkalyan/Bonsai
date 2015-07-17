package com.bonsai.activities;

import com.example.bonsai.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_splash_screen);


		Thread background = new Thread() {
			public void run() {

				try {
					sleep(1*1000);

					Intent mainIntent = new Intent(getBaseContext(),MainActivity.class);
					startActivity(mainIntent);
					finish();

				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();

		//METHOD 2  

		/*
        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent i = new Intent(MainSplashScreen.this, FirstScreen.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 5*1000); // wait for 5 seconds
		 */
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

	}

}
