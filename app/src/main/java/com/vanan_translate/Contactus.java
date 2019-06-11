package com.vanan_translate;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class Contactus extends AppCompatActivity {

    ImageView close_;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.contactus);
        Fabric.with(this, new Crashlytics());

        dialog = new Dialog(Contactus.this);
        ImageView call = (ImageView) findViewById(R.id.mobile_image1);
        ImageView mail = (ImageView) findViewById(R.id.mobile_image);
        ImageView fb_ = (ImageView) findViewById(R.id.fb);
        ImageView twitter_ = (ImageView) findViewById(R.id.twitter);
        ImageView linkedin_ = (ImageView) findViewById(R.id.linkedin);
        ImageView youtube_ = (ImageView) findViewById(R.id.youtube);
        close_ = findViewById(R.id.close);


        fb_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    Intent i = new Intent(Contactus.this, Social_media.class);
                    i.putExtra("media", "fb");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });

        twitter_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    Intent i = new Intent(Contactus.this, Social_media.class);
                    i.putExtra("media", "twitter");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });

        linkedin_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    Intent i = new Intent(Contactus.this, Social_media.class);
                    i.putExtra("media", "linkedin");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }

            }
        });
        youtube_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    Intent i = new Intent(Contactus.this, Social_media.class);
                    i.putExtra("media", "youtube");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialContactPhone("18885355668");

            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@vananservices.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "");
                    i.putExtra(Intent.EXTRA_TEXT, "");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(Contactus.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Network_config.customAlert(dialog, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });


        close_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
            }
        });


    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}