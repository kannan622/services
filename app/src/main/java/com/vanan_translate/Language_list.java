package com.vanan_translate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class Language_list extends AppCompatActivity {
    static String type,point_;
    public ArrayList<offers_model> countries_lang = new ArrayList<offers_model>();
    public ArrayList<offer_model1> countries_lang1 = new ArrayList<offer_model1>();
    String status;
    String jsonStr;
    ProgressDialog csprogress;
    Adapter_card_listview adapt;
    Adapter_card_listview1 adapt1;
    ListView lv, lv1;
    TextView from_head;
    TextView to_head;
    TextView done_;
    EditText search_, search_2;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.language_selection);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Fabric.with(this, new Crashlytics());
        csprogress = new ProgressDialog(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        Intent intent = this.getIntent();
        if (intent != null)// to avoid the NullPointerException

            type = intent.getStringExtra("select");
        point_ = intent.getStringExtra("point");

        done_ = findViewById(R.id.done);

        done_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  String ss1 = session.getUserDetails().get(SessionManager.KEY_from_string);*/
                if(point_.matches("main"))
                {
                    new Free_translation.EnglishToTagalog().execute();



                    Intent i = new Intent(Language_list.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(point_.matches("second"))
                {
                    Intent i = new Intent(Language_list.this, GetQuote.class);
                    startActivity(i);
                    finish();
                }





            }
        });
        lv = findViewById(R.id.mylistview);
        lv1 = findViewById(R.id.mylistview1);
        final RelativeLayout rel_f_lang = findViewById(R.id.rel_from_lang);
        final RelativeLayout rel_t_lang = findViewById(R.id.rel_to_lang);

        from_head = findViewById(R.id.from_lang_head);
        to_head = findViewById(R.id.to_lang_head);

        search_ = findViewById(R.id.search);
        search_2 = findViewById(R.id.search1);


        if (type.matches("from")) {
            from_head.setBackgroundResource(R.drawable.rect_color);
            to_head.setBackgroundResource(R.drawable.rect_whiteborder);

            from_head.setTextColor(Color.parseColor("#FFFFFF"));


            rel_f_lang.setVisibility(View.VISIBLE);

            new GetContacts_offers().execute();
            new GetContacts_offers1().execute();
        } else if (type.matches("to")) {
            search_.setVisibility(View.GONE);
            search_2.setVisibility(View.VISIBLE);
            from_head.setBackgroundResource(R.drawable.rect_whiteborder);
            to_head.setBackgroundResource(R.drawable.rect_color);

            from_head.setTextColor(Color.parseColor("#514bc5"));
            to_head.setTextColor(Color.parseColor("#FFFFFF"));
            rel_f_lang.setVisibility(View.INVISIBLE);

            rel_t_lang.setVisibility(View.VISIBLE);

            new GetContacts_offers1().execute();
            new GetContacts_offers().execute();
        }


        from_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                from_head.setBackgroundResource(R.drawable.rect_color);
                to_head.setBackgroundResource(R.drawable.rect_whiteborder);

                from_head.setTextColor(Color.parseColor("#FFFFFF"));
                to_head.setTextColor(Color.parseColor("#514bc5"));

                rel_f_lang.setVisibility(View.VISIBLE);
                rel_t_lang.setVisibility(View.INVISIBLE);
                search_.setVisibility(View.VISIBLE);
                search_2.setVisibility(View.GONE);


            }
        });

        to_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_.setVisibility(View.GONE);
                search_2.setVisibility(View.VISIBLE);
                from_head.setBackgroundResource(R.drawable.rect_whiteborder);
                to_head.setBackgroundResource(R.drawable.rect_color);

                from_head.setTextColor(Color.parseColor("#514bc5"));
                to_head.setTextColor(Color.parseColor("#FFFFFF"));
                rel_f_lang.setVisibility(View.INVISIBLE);

                rel_t_lang.setVisibility(View.VISIBLE);
            }
        });

        search_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                search_.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        System.out.println("Text [" + s + "]");

                        // adapt.getFilter().filter(s.toString());


                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // adapt.getFilter().filter(s.toString());


                        adapt.filter(s.toString());
                    }
                });


            }
        });
        search_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                search_2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        System.out.println("Text [" + s + "]");

                        // adapt.getFilter().filter(s.toString());


                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // adapt.getFilter().filter(s.toString());


                        try {
                            adapt1.filter(s.toString());
                        } catch (Exception e) {

                        }


                    }
                });


            }

        });


    }


    private class GetContacts_offers extends AsyncTask<Void, Void, Void> {

        String rupees_flag, dollers_flag;
        String total_cost_ruppes, total_cost_dollers;

        @Override
        protected void onPreExecute() {

            csprogress = new ProgressDialog(Language_list.this);
            csprogress.setMessage("Loading...");
            csprogress.show();
            csprogress.setCanceledOnTouchOutside(false);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Handler_ sh = new Handler_(getApplicationContext());

            // Making a request to url and getting response


            jsonStr = sh.makeServiceCall_accesstoken_gettransaction(Iconstant.language_list);


            Log.e("data", "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray jar = jsonObj.getJSONArray("result");


                    // looping through All Contacts
                    for (int i = 0; i < jar.length(); i++) {
                        offers_model lan;

                        JSONObject c = jar.getJSONObject(i);


                        String id = c.getString("id");
                        String language_name = c.getString("language_name");

                        String language_code = c.getString("language_code");
                        String flag = c.getString("flag");


                        lan = new offers_model(id, language_name, language_code, flag);


                        countries_lang.add(lan);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node


                // Getting JSON Array node

            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            try {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


                adapt = new Adapter_card_listview(getApplicationContext(), countries_lang);

                // offers_count.setText("We have " + countries_lang.size() + " offers for you!");

                lv.setAdapter(adapt);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


    private class GetContacts_offers1 extends AsyncTask<Void, Void, Void> {

        String rupees_flag, dollers_flag;
        String total_cost_ruppes, total_cost_dollers;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Handler_ sh = new Handler_(getApplicationContext());

            // Making a request to url and getting response


            jsonStr = sh.makeServiceCall_accesstoken_gettransaction(Iconstant.language_list);


            Log.e("data", "Response from url: " + jsonStr);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray jar = jsonObj.getJSONArray("result");


                    // looping through All Contacts
                    for (int i = 0; i < jar.length(); i++) {
                        offer_model1 lan;

                        JSONObject c = jar.getJSONObject(i);


                        String id = c.getString("id");
                        String language_name = c.getString("language_name");

                        String language_code = c.getString("language_code");
                        String flag = c.getString("flag");

                        lan = new offer_model1(id, language_name, language_code, flag);


                        countries_lang1.add(lan);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Getting JSON Array node


                // Getting JSON Array node

            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            try {

                if (csprogress.isShowing()) {
                    csprogress.dismiss();
                }


                adapt1 = new Adapter_card_listview1(getApplicationContext(), countries_lang1);

                // offers_count.setText("We have " + countries_lang.size() + " offers for you!");

                lv1.setAdapter(adapt1);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}
