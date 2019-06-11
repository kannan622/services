package com.vanan_translate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class Social_media extends AppCompatActivity {

    String type;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Fabric.with(this, new Crashlytics());

        Intent intent = this.getIntent();
        if (intent != null)// to avoid the NullPointerException

            type = intent.getStringExtra("media");

        if (type.matches("fb")) {
            url = "https://www.facebook.com/vananservice";
        } else if (type.matches("twitter")) {
            url = "https://twitter.com/vananservices?lang=en";
        } else if (type.matches("linkedin")) {
            url = "https://in.linkedin.com/company/vananservicesinc";
        } else if (type.matches("youtube")) {
            url = "https://www.youtube.com/channel/UCL_A6msybYYE_8jqM03zRDw";
        } else if (type.matches("privacy")) {
            url = "https://vananservices.com/privacy-policy.php";
        } else if (type.matches("terms")) {
            url = "https://vananservices.com/Terms-And-Conditions.php";
        } else if (type.matches("aboutus")) {
            url = "https://vananservices.com/";
        } else if (type.matches("faqs")) {
            url = "https://vananservices.com/Faq.php";
        }


        WebView mywebview = (WebView) findViewById(R.id.webview);

        mywebview.clearCache(true);
        mywebview.clearHistory();
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mywebview.setWebViewClient(new WebViewClient());
        // mywebview.loadUrl("https://www.javatpoint.com/");

        /*String data = "<html><body><h1>Hello, Javatpoint!</h1></body></html>";
        mywebview.loadData(data, "text/html", "UTF-8"); */

        mywebview.loadUrl(url);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mywebview, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        mywebview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                ProgressBar progressbar = (ProgressBar) findViewById(R.id.progress_bar);
                progressbar.setVisibility(View.GONE);
            }
        });
    }


}