package com.vanan_translate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class Iconstant {


    // Live

//   public static String baseurl = "http://54.203.88.135/crm-api/api/v1/";

    //11111111111111111111111111111111111111111111111111111111111


    //<<<<<<<<<<<<<<<<<LIVE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // public static String baseurl = "http://34.215.32.32:3000/api/";
//<<<<<<<<<<<<<<<<<LIVE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //public static String baseurl = "https://vananonlineservices.us/api/";

    //<<<<<<<<<<<<<<<<<<<<TESTING local >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static String baseurl = "https://vananappapi.newyorkmultiserviceshub.com/";

    public static String api_url = "api/v1/";
    public static String flag_url = "public/flag/";


    // 1111111111111111111111111111111111111111111111111111111111111111


    public static String register = baseurl + api_url + "register";

    public static String update = baseurl + api_url + "update";


    public static String language_list = baseurl + api_url + "languages";

    public static String versioncheck = baseurl + api_url + "version";

    public static String getquote = baseurl + api_url + "get-quote";

    public static String versioncode = "2";


    public static String red = "holo_gray_dark";

    public static String green = "holo_gray_dark";


    public static void setBackgroundColor(View view, Context mContext, String color) {
        int identifier = mContext.getResources().getIdentifier(color, "color", mContext.getPackageName());
        if (identifier != 0) {
            view.setBackgroundColor(mContext.getResources().getColor(identifier));
        }
    }

    public static void createSnackBarView(Context context, Snackbar snackbar) {
        final View snackBarView = snackbar.getView();
        setBackgroundColor(snackBarView, context, red);
        //To hide soft keyboard When snackbar displays
        final InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        snackBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            /**
             * This method is used to hide soft keyboard When snackbar displays
             * @return Nothing.
             */
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                snackBarView.getWindowVisibleDisplayFrame(r);
                int screenHeight = snackBarView.getRootView().getHeight();
                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    im.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } else {
                    // keyboard is closed
                }
            }
        });
        TextView tv = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.parseColor("#ffff00"));

        snackbar.show();
    }


    public static void createSnackBarView1(Context context, Snackbar snackbar) {
        final View snackBarView = snackbar.getView();
        setBackgroundColor(snackBarView, context, green);
        //To hide soft keyboard When snackbar displays
        final InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        snackBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            /**
             * This method is used to hide soft keyboard When snackbar displays
             * @return Nothing.
             */
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                snackBarView.getWindowVisibleDisplayFrame(r);
                int screenHeight = snackBarView.getRootView().getHeight();
                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;
                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    im.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } else {
                    // keyboard is closed
                }
            }
        });
        TextView tv = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.parseColor("#ffff00"));
        snackbar.show();
    }


}
