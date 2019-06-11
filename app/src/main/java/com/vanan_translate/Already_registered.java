package com.vanan_translate;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class Already_registered extends AppCompatActivity {

    ImageView close_;

    TextView name_top, name_description;
    SessionManager session;

    RelativeLayout done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.already_signedin);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Fabric.with(this, new Crashlytics());
        close_ = findViewById(R.id.close);

        done = findViewById(R.id.rel_done);


        session = new SessionManager(getApplicationContext());
        name_top = findViewById(R.id.message);

        if ((session.getUserDetails().get(SessionManager.KEY_first_name) != null) && (!session.getUserDetails().get(SessionManager.KEY_first_name).matches(""))) {
            String session_user_name = (session.getUserDetails().get(SessionManager.KEY_first_name));

            String session_email = (session.getUserDetails().get(SessionManager.KEY_email));


            name_description = findViewById(R.id.text1);

            String next = "<font color='#514bc5'>" + session_email + "</font>";


            name_top.setText("Hello! " + session_user_name);

            name_description.setText(Html.fromHtml("Hi " + session_user_name + ", you are registered under " + next + ".We will contact you soon!"));
        } else {
            name_top.setText("Vanan Account login");
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Already_registered.this, MainActivity.class);
                startActivity(i);
                finish();

              //  overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
            }
        });

        close_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Already_registered.this, MainActivity.class);
                startActivity(i);
                finish();

                overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
            }
        });
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
    }
}