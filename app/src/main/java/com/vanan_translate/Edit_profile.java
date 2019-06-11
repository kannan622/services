package com.vanan_translate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.hbb20.CountryCodePicker;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

import io.fabric.sdk.android.Fabric;

public class Edit_profile extends AppCompatActivity {

    public static BottomSheetDialog dialog;
    static SessionManager session;
    static EditText name_, mobile_, email_;
    static int statuscode;
    static String macAddress, os_type;
    static String access_token;
    static String country_code_ = "";
    static String dial_code_;
    Dialog dialog1;
    CountryCodePicker ccp;
    ImageView close_;
    RelativeLayout rel_sub;
    ProgressDialog csprogress;
    String status, message;

    public static String POST_flag_launch(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPut httpPost = new HttpPut(url);


            httpPost.setHeader("platform", "android");
            httpPost.setHeader("Authorization", access_token);


            String json = "";


            // int jjson;

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            // JSONArray jsonarr = new JSONArray();


            jsonObject.accumulate("user_name", name_.getText().toString().trim());
            jsonObject.accumulate("email", email_.getText().toString().trim());
            jsonObject.accumulate("phone_number", mobile_.getText().toString().trim());
            jsonObject.accumulate("dial_code", dial_code_);
            jsonObject.accumulate("country_code", country_code_);
            jsonObject.accumulate("device_id", macAddress);

            jsonObject.accumulate("os_type", os_type);


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
        setContentView(R.layout.edit_profile);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Fabric.with(this, new Crashlytics());

        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
            }
        }

        Log.d("OS", "OS: " + builder.toString());

        os_type = builder.toString();


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress = wInfo.getMacAddress();

        session = new SessionManager(getApplicationContext());
        csprogress = new ProgressDialog(Edit_profile.this);
        name_ = findViewById(R.id.name);
        email_ = findViewById(R.id.email);
        mobile_ = findViewById(R.id.phone);

        rel_sub = findViewById(R.id.rel_submit);
        dialog1 = new Dialog(Edit_profile.this);
        access_token = session.getUserDetails().get(SessionManager.KEY_access_token);

        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        if ((session.getUserDetails().get(SessionManager.KEY_first_name) != null) && (!session.getUserDetails().get(SessionManager.KEY_first_name).matches(""))) {
            String session_user_name = (session.getUserDetails().get(SessionManager.KEY_first_name));
            String session_email = (session.getUserDetails().get(SessionManager.KEY_email));


            name_.setText(session_user_name);
            name_.setSelection(name_.getText().length());
            email_.setText(session_email);

            if ((session.getUserDetails().get(SessionManager.KEY_mobile) != null) && (!session.getUserDetails().get(SessionManager.KEY_mobile).matches(""))) {
                String session_mobile = (session.getUserDetails().get(SessionManager.KEY_mobile));
                country_code_ = (session.getUserDetails().get(SessionManager.KEY_country_code));

                Log.d("code", country_code_);
                dial_code_ = (session.getUserDetails().get(SessionManager.KEY_dial_code));
                mobile_.setText(session_mobile);
                ccp.setCountryForNameCode(country_code_);

            } else {
                mobile_.setHint("Enter Mobile no.");


            }

            if ((session.getUserDetails().get(SessionManager.KEY_country_code) != null) && (!session.getUserDetails().get(SessionManager.KEY_country_code).matches(""))) {
                country_code_ = (session.getUserDetails().get(SessionManager.KEY_country_code));
                ccp.setCountryForNameCode(country_code_);
            }

        } else {

        }


        close_ = findViewById(R.id.close);

        close_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
            }
        });


        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {


                // Toast.makeText(getApplicationContext(), "Updated " + ccp.getSelectedCountryCodeWithPlus() + ":::" + ccp.getSelectedCountryName() + ":::" + ccp.getSelectedCountryNameCode(), Toast.LENGTH_SHORT).show();

                dial_code_ = ccp.getSelectedCountryCodeWithPlus();
                // String country_name = ccp.getSelectedCountryName();
                country_code_ = ccp.getSelectedCountryNameCode();
            }
        });
        rel_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network_config.is_Network_Connected_flag(getApplicationContext())) {

                    if (name_.getText().toString().trim().matches("")) {
                        Toast.makeText(getApplicationContext(), "User name required", Toast.LENGTH_SHORT).show();
                    } else if (email_.getText().toString().trim().matches("")) {
                        Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT).show();
                    } else if (!email_.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                        Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();

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

                        new HttpAsyncTask().execute(Iconstant.update);
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
        finish();
        overridePendingTransition(R.anim.slide_down, R.anim.slide_down);
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


                            JSONObject job = jsonObj.getJSONObject("result");

                            String username = job.getString("user_name");
                            String email = job.getString("email");
                            String phone_number = job.getString("phone_number");
                            String coun_code = job.getString("android_country_code");
                            String dia_code = job.getString("dial_code");


                            session.createLoginSession(username, email, phone_number, access_token, coun_code, dia_code);

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

                        if (status.matches("401")) {


                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


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