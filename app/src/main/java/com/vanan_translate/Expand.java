package com.vanan_translate;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class Expand extends AppCompatActivity {

    ImageView close_;
    Spinner countrycode_;
    ArrayAdapter<country_code> adapter;
    String from_string;


    String expand_text;
    TextView text_expand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.expand);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Fabric.with(getApplicationContext(), new Crashlytics());
        Intent intent = this.getIntent();
        if (intent != null)// to avoid the NullPointerException

            expand_text = intent.getStringExtra("text");

        text_expand = findViewById(R.id.text1);
        close_ = findViewById(R.id.close);

        close_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        text_expand.setText(expand_text);
    }
}
