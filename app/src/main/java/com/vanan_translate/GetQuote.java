package com.vanan_translate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.fabric.sdk.android.Fabric;

public class GetQuote extends AppCompatActivity {

    public static String session_from_langcode, session_to_langcode;
    static int statuscode;
    static EditText pagecount_;
    static String session_service_type;
    static String access_token;
    SessionManager session;
    RelativeLayout from_lang, to_lang;
    ImageView from_flag_img, to_flag_img;
    TextView from_lang_text, to_lang_text;
    Dialog dialog1;
    RelativeLayout rel_submition;
    ProgressDialog csprogress;
    String status, message;
    String session_pagecount;
    ImageView close_;

    public static String POST_flag_launch(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);


            httpPost.setHeader("platform", "android");
            httpPost.setHeader("Authorization", access_token);


            String json = "";


            // int jjson;

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            // JSONArray jsonarr = new JSONArray();

            if (session_service_type.matches("translation")) {
                jsonObject.accumulate("page_count", pagecount_.getText().toString().trim());

            } else {
                jsonObject.accumulate("file_duration", pagecount_.getText().toString().trim());
            }

            jsonObject.accumulate("source_language", session_from_langcode);
            jsonObject.accumulate("target_language", session_to_langcode);
            jsonObject.accumulate("service_type", session_service_type);


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            Log.d("Create Response", json.toString());

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            Log.d("STEP 111111111111111", "STEPPPPPPPPP111111");

            // 7. Set some headers to inform server about the type of the
            // content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            Log.d("STEP 2222222222", "22222222222");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            Log.d("STEP 3333333333", "33333333333333333");

            httpResponse.getStatusLine().getStatusCode();
            statuscode = httpResponse.getStatusLine().getStatusCode();

            Log.d("Statsucode", "" + statuscode);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            Log.d("STEP 444444444444", "44444444444444444444");

            // 10. convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            // LogUtils.LOGD("InputStream", e.getLocalizedMessage());
        }//

        // 11. return result
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.quote);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Fabric.with(this, new Crashlytics());
        csprogress = new ProgressDialog(GetQuote.this);
        pagecount_ = findViewById(R.id.pagecount);
        TextView second_text = findViewById(R.id.text2);


        rel_submition = findViewById(R.id.rel_submit);
        from_lang = findViewById(R.id.from_lang_rel);

        to_lang = findViewById(R.id.to_lang_rel);

        from_lang_text = findViewById(R.id.file_name_text);
        to_lang_text = findViewById(R.id.to_text);
        from_flag_img = findViewById(R.id.flag_from);
        to_flag_img = findViewById(R.id.flag_to);
        dialog1 = new Dialog(getApplicationContext());


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        session = new SessionManager(getApplicationContext());

        access_token = session.getUserDetails().get(SessionManager.KEY_access_token);
        close_ = findViewById(R.id.close);

        close_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
            }
        });


        if (session.getUserDetails().get(SessionManager.KEY_service_type) != null && !session.getUserDetails().get(SessionManager.KEY_service_type).matches("")) {

            session_service_type = session.getUserDetails().get(SessionManager.KEY_service_type);

            Log.d("yes", session_service_type);

            second_text.setText("professional human " + session_service_type + " services");


        }


        if (session.getUserDetails().get(SessionManager.KEY_pagecount_session) != null && !session.getUserDetails().get(SessionManager.KEY_pagecount_session).matches("")) {

            session_pagecount = session.getUserDetails().get(SessionManager.KEY_pagecount_session);

            pagecount_.setText(session_pagecount);

            pagecount_.setSelection(pagecount_.getText().length());

        } else if (session_service_type.matches("translation")) {

            pagecount_.setHint("Enter Page Count");
        } else {
            pagecount_.setHint("Enter File Time Duration (in minutes)");
        }


        if (session.getUserDetails().get(SessionManager.KEY_from_status) != null && session.getUserDetails().get(SessionManager.KEY_to_status) != null) {

        } else if (session.getUserDetails().get(SessionManager.KEY_from_status) != null) {
            session.create_to_lang("313", "Spanish", "es", "spain.png");
        } else if (session.getUserDetails().get(SessionManager.KEY_to_status) != null) {
            session.create_from_lang("90", "English", "en", "united-states-of-america.png");
        } else {
            session.create_from_lang("90", "English", "en", "united-states-of-america.png");
            session.create_to_lang("313", "Spanish", "es", "spain.png");
        }


        if (session.getUserDetails().get(SessionManager.KEY_from_lang_name) != null) {
            String from_langname = session.getUserDetails().get(SessionManager.KEY_from_lang_name);

            session_from_langcode = session.getUserDetails().get(SessionManager.KEY_from_lang_code);

            from_lang_text.setText("From : " + from_langname);

            String flag_ = Iconstant.baseurl + Iconstant.flag_url + session.getUserDetails().get(SessionManager.KEY_from_flag);

            Glide.with(getApplicationContext()).load(flag_).apply(options).into(from_flag_img);

        }

        if (session.getUserDetails().get(SessionManager.KEY_to_lang_name) != null) {
            String to_langname = session.getUserDetails().get(SessionManager.KEY_to_lang_name);

            session_to_langcode = session.getUserDetails().get(SessionManager.KEY_to_lang_code);

            to_lang_text.setText("To : " + to_langname);

            String flag_ = Iconstant.baseurl + Iconstant.flag_url + session.getUserDetails().get(SessionManager.KEY_to_flag);

            Glide.with(getApplicationContext()).load(flag_).apply(options).into(to_flag_img);

        }


        from_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    Intent i = new Intent(getApplicationContext(), Language_list.class);
                    i.putExtra("select", "from");
                    i.putExtra("point", "second");
                    startActivity(i);
                    finish();
                } else {
                    Network_config.customAlert(dialog1, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }

            }
        });

        to_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {
                    Intent i = new Intent(getApplicationContext(), Language_list.class);
                    i.putExtra("select", "to");
                    i.putExtra("point", "second");
                    startActivity(i);
                    finish();
                } else {
                    Network_config.customAlert(dialog1, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });

        pagecount_.addTextChangedListener(new TextWatcher() {

            boolean isOnTextChanged = false;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                isOnTextChanged = true;
                // TODO Auto-generated method stub


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (isOnTextChanged) {

                  /*  if (Network_config.is_Network_Connected_flag(getActivity())) {
                        int position = from_text.length();
                        Editable etext = from_text.getText();
                        Selection.setSelection(etext, position);
                        new EnglishToTagalog().execute();


                    } else {
                        Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                                getResources().getString(R.string.connection_message));
                    }
*/

                }


                isOnTextChanged = false;

                if (pagecount_.getText().toString().length() <= 0) {
                    if (session_service_type.matches("translation")) {
                        pagecount_.setHint("Enter Page Count");
                    } else {
                        pagecount_.setHint("Enter File Time Duration (in minutes)");
                    }
                }


                session.create_pagecount_session(pagecount_.getText().toString());
                // TODO Auto-generated method stub
            }
        });


        rel_submition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {

                    if (pagecount_.getText().toString().trim().matches("")) {
                        Toast.makeText(getApplicationContext(), "All Fields Mandatory", Toast.LENGTH_SHORT).show();
                    } else {


                        csprogress.setMessage("Loading...");
                        csprogress.show();
                        csprogress.setCanceledOnTouchOutside(false);


                       /* if (checkbox_.isChecked()) {
                            checkbox_value = "1";
                        } else {
                            checkbox_value = "0";
                        }*/


                        new android.os.Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                //csprogress.dismiss();
//whatever you want just you have to launch overhere.


                            }
                        }, 1000);//just mention the time when you want to launch your action
                        //new Gettransaction_form_send().execute();

                        new HttpAsyncTask().execute(Iconstant.getquote);
                    }

                } else {
                    Network_config.customAlert(dialog1, getApplicationContext(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });

    }

    @Override
    public void onBackPressed() {
        session.clear_service_type_values();
        finish();

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return POST_flag_launch(urls[0]);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Log.d("Flag Response", result);


            try {

                if (result != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(result);


                        status = jsonObj.getString("statusCode");
                        message = jsonObj.getString("message");


                        if (status.matches("200")) {

                           /* JSONObject job = jsonObj.getJSONObject("result");

                            String username = job.getString("user_name");
                            String email = job.getString("email");
                            String phone_number = job.getString("phone_number");
                            String coun_code = job.getString("android_country_code");
                            String dia_code = job.getString("dial_code");*/


                            session.clear_pagecount_session();
                            session.clear_service_type_values();

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        if (status.matches("422")) {


                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                        }
                        if (status.matches("500")) {


                            Toast.makeText(getApplicationContext(), "Internal Server error", Toast.LENGTH_SHORT).show();


                        }

                        // Getting JSON Array node


                        // Getting JSON Array node

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to load data,please try again later.", Toast.LENGTH_SHORT).show();
                }


                if (csprogress.isShowing()) {
                    csprogress.dismiss();

                }


                if (status.matches("426")) {

                    Toast.makeText(getApplicationContext(), "Please Update to latest Version", Toast.LENGTH_SHORT).show();

                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }


                }


            } catch (Exception e) {

            }

        }
    }
}
