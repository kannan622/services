package com.vanan_translate;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;

public class Splash extends AppCompatActivity {

    public static Dialog dialog;
    private static int SPLASH_TIME_OUT = 2200;
    SessionManager session;
    String version;
    String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        session = new SessionManager(getApplicationContext());
        dialog = new Dialog(Splash.this);
        Fabric.with(this, new Crashlytics());


        if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
            new Handler().postDelayed(new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    new Getversion().execute();


                }
            }, SPLASH_TIME_OUT);

        } else {
            Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                    getResources().getString(R.string.connection_message));

        }


        //


    }

    public void check() {

        if (session.getUserDetails().get(SessionManager.KEY_from_status) != null && session.getUserDetails().get(SessionManager.KEY_to_status) != null) {

        } else if (session.getUserDetails().get(SessionManager.KEY_from_status) != null) {
            session.create_to_lang("313", "Spanish", "es", "spain.png");
        } else if (session.getUserDetails().get(SessionManager.KEY_to_status) != null) {
            session.create_from_lang("90", "English", "en", "united-states-of-america.png");
        } else {
            session.create_from_lang("90", "English", "en", "united-states-of-america.png");
            session.create_to_lang("313", "Spanish", "es", "spain.png");
        }

        Intent i = new Intent(this, MainActivity.class);


        startActivity(i);

        finish();
    }

    private class Getversion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Handler_ sh = new Handler_(getApplicationContext());

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(Iconstant.versioncheck);


            Log.e("data1", "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONObject job = jsonObj.getJSONObject("android");


                    version = job.getString("version");
                    priority = job.getString("priority");


                } catch (final JSONException e) {

                    e.printStackTrace();
                }
            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            try {

                if (version.matches(Iconstant.versioncode)) {
                    check();

                } else {
                    if (priority.matches("low")) {
                        Toast.makeText(getApplicationContext(), "Please Update to latest Version, Priority:Low", Toast.LENGTH_SHORT).show();
                        check();
                    } else if (priority.matches("medium")) {
                        Toast.makeText(getApplicationContext(), "Please Update to latest Version, Priority:Medium", Toast.LENGTH_SHORT).show();
                        check();
                    } else if (priority.matches("high")) {
                        Toast.makeText(getApplicationContext(), "Please Update to latest Version, Priority:High", Toast.LENGTH_SHORT).show();

                        final String appPackageName = getApplicationContext().getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}

