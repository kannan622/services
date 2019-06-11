package com.vanan_translate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.File;
import java.util.HashMap;


/**
 * Created by kannan.pannerselvam on 5/9/2018.
 */

public class SessionManager {
    /*
     * private static final String KEY_name = "name"; private static final
     * String KEY_image = "image"; public static final String KEY_email =
     * "email";
     */
    public static final String KEY_SERVICE_FIREBASE_UPDATE = "firebase_update_service";
    public static final String KEY_SERVICE_LOCATION = "location_update_service";
    public static final String KEY_firebase_token = "firebase_token";
    public static final String KEY_mobile = "phone_no";
    public static final String KEY_punchin = "punchin";
    public static final String KEY_lat = "lat";
    public static final String KEY_lng = "lng";
    public static final String KEY_access_token = "access_token";
    public static final String KEY_unique_id = "unique_id";
    public static final String KEY_photourl = "photourl";
    public static final String KEY_name = "name";
    public static final String KEY_first_name = "Firstname";
    public static final String KEY_last_name = "Lastname";

    public static final String KEY_email = "Email";
    public static final String KEY_role = "role";
    public static final String KEY_user_id = "user_id";
    public static final String KEY_designation = "designation";
    public static final String KEY_firstname_filter = "firstname_filter";


    public static final String KEY_from_flag_id = "from_flag_id";
    public static final String KEY_from_lang_name = "from_lang_name";
    public static final String KEY_from_lang_code = "from_lang_code";
    public static final String KEY_from_flag = "from_flag";

    public static final String KEY_service_type = "service_type";

    public static final String KEY_pagecount_session = "pagecount_session";


    public static final String KEY_from_status = "from_status";
    public static final String KEY_to_string = "to_string";
    // public static final String KEY_to_string_first = "to_string_first";


    public static final String KEY_from_string = "from_string";


    public static final String KEY_country_code = "country_code";
    public static final String KEY_dial_code = "dial_code";


    public static final String KEY_to_status = "to_status";


    public static final String KEY_to_flag_id = "to_flag_id";
    public static final String KEY_to_lang_name = "to_lang_name";
    public static final String KEY_to_lang_code = "to_lang_code";
    public static final String KEY_to_flag = "to_flag";


    public static final String KEY_userid_filter = "userid_filter";

    public static final String KEY_cardid_filter = "cardid_filter";
    public static final String KEY_cardno_filter = "cardno_filter";


    public static final String KEY_category_name = "category_name";
    public static final String KEY_category_id = "category_id";
    public static final String KEY_card_no = "card_no";
    public static final String KEY_card_id = "card_id";
    public static final String KEY_country_name = "country_name";
    public static final String KEY_country_id = "country_id";

    public static final String KEY_currency_name = "currency_name";
    public static final String KEY_currency_id = "currency_id";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String PREF_NAME = "Android";
    private static final String FIREBASE_PREF_NAME = "Android_Firebase";
    private static final String SERVICE_PREF_NAME = "Android_Service";
    // public static final String KEY_mail_copy_county_id = "transl_mail_copy_country_id";
    static Context _context;
    static SharedPreferences.Editor editor;
    static SharedPreferences.Editor firebase_editor;
    static SharedPreferences.Editor service_editor;
    int PRIVATE_MODE = 0;
    int flag = 0;
    SharedPreferences pref;
    SharedPreferences firebase_pref;
    SharedPreferences service_pref;

    public SessionManager(Context paramContext) {
        _context = paramContext;
        this.pref = _context.getSharedPreferences(PREF_NAME,
                this.PRIVATE_MODE);
        editor = this.pref.edit();
        this.firebase_pref = _context.getSharedPreferences(FIREBASE_PREF_NAME,
                this.PRIVATE_MODE);
        firebase_editor = this.firebase_pref.edit();
        this.service_pref = _context.getSharedPreferences(SERVICE_PREF_NAME,
                this.PRIVATE_MODE);
        service_editor = this.service_pref.edit();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    public void createLoginSession(String user_name, String email, String phone_no, String auth_code, String country_code, String dial_code) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */
        editor.putString(KEY_first_name, user_name);
        editor.putString(KEY_email, email);
        editor.putString(KEY_mobile, phone_no);
        editor.putString(KEY_access_token, auth_code);
        editor.putString(KEY_country_code, country_code);
        editor.putString(KEY_dial_code, dial_code);


        // Storing email in pref

        // commit changes
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        /*
         * user.put(KEY_name, pref.getString(KEY_name, null));
         *
         * user.put(KEY_image, pref.getString(KEY_image, null));
         * user.put(KEY_email, pref.getString(KEY_email, null));
         */

        user.put(KEY_mobile, pref.getString(KEY_mobile, null));


        user.put(KEY_first_name, pref.getString(KEY_first_name, null));

        user.put(KEY_email, pref.getString(KEY_email, null));
        user.put(KEY_access_token, pref.getString(KEY_access_token, null));

        user.put(KEY_from_flag_id, pref.getString(KEY_from_flag_id, null));
        user.put(KEY_from_lang_name, pref.getString(KEY_from_lang_name, null));
        user.put(KEY_from_lang_code, pref.getString(KEY_from_lang_code, null));
        user.put(KEY_from_flag, pref.getString(KEY_from_flag, null));

        user.put(KEY_to_flag_id, pref.getString(KEY_to_flag_id, null));
        user.put(KEY_to_lang_name, pref.getString(KEY_to_lang_name, null));
        user.put(KEY_to_lang_code, pref.getString(KEY_to_lang_code, null));
        user.put(KEY_to_flag, pref.getString(KEY_to_flag, null));


        user.put(KEY_from_status, pref.getString(KEY_from_status, null));
        user.put(KEY_to_status, pref.getString(KEY_to_status, null));

        user.put(KEY_to_string, pref.getString(KEY_to_string, null));

        user.put(KEY_country_code, pref.getString(KEY_country_code, null));
        user.put(KEY_dial_code, pref.getString(KEY_dial_code, null));
        user.put(KEY_from_string, pref.getString(KEY_from_string, null));

        user.put(KEY_service_type, pref.getString(KEY_service_type, null));


        user.put(KEY_pagecount_session, pref.getString(KEY_pagecount_session, null));

        //user.put(KEY_to_string_first, pref.getString(KEY_to_string_first, null));


        // user email id

        // return user
        return user;
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void clear_session_values() {
        // Clearing all data from Shared Preferences
        // editor.clear();
        // editor.commit();


        editor.remove(KEY_category_name);
        editor.remove(KEY_category_id);
        editor.remove(KEY_card_no);
        editor.remove(KEY_card_id);
        editor.remove(KEY_country_name);
        editor.remove(KEY_country_id);
        editor.remove(KEY_currency_name);
        editor.remove(KEY_currency_id);


        editor.apply();

        // After logout redirect user to Loing Activity
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

        // Add new Flag to start new Activity
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

    }


    public void create_from_lang(String id, String lang_name, String lang_code, String flag) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_from_flag_id, id);
        editor.putString(KEY_from_lang_name, lang_name);
        editor.putString(KEY_from_lang_code, lang_code);
        editor.putString(KEY_from_flag, flag);


        // Storing email in pref

        // commit changes
        editor.commit();
    }


    public void create_pagecount_session(String pagecount_session) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_pagecount_session, pagecount_session);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void clear_pagecount_session() {
        // Clearing all data from Shared Preferences
        // editor.clear();
        // editor.commit();


        editor.remove(KEY_pagecount_session);


        editor.apply();

        // After logout redirect user to Loing Activity
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

        // Add new Flag to start new Activity
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

    }


    public void create_service_type(String type) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_service_type, type);


        // Storing email in pref

        // commit changes
        editor.commit();
    }


    public void from_status_check(String from_status) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_from_status, from_status);


        // Storing email in pref

        // commit changes
        editor.commit();
    }


    public void to_string(String text) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_to_string, text);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

   /* public void to_string_first(String text) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        *//*
     * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
     * editor.putString(KEY_email, email);
     *//*

        editor.putString(KEY_to_string_first, text);


        // Storing email in pref

        // commit changes
        editor.commit();
    }*/

    public void from_string(String text) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);


        editor.putString(KEY_from_string, text);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void to_status_check(String to_status) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_to_status, to_status);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void create_to_lang(String id, String lang_name, String lang_code, String flag) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_to_flag_id, id);
        editor.putString(KEY_to_lang_name, lang_name);
        editor.putString(KEY_to_lang_code, lang_code);
        editor.putString(KEY_to_flag, flag);


        // Storing email in pref

        // commit changes
        editor.commit();
    }


    public void createmy_card_filter(String filter_cardno, String filter_card_id) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_cardno_filter, filter_cardno);
        editor.putString(KEY_cardid_filter, filter_card_id);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void clear_to_string_values() {
        // Clearing all data from Shared Preferences
        // editor.clear();
        // editor.commit();


        editor.remove(KEY_to_string);


        editor.apply();

        // After logout redirect user to Loing Activity
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

        // Add new Flag to start new Activity
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

    }

    public void clear_service_type_values() {
        // Clearing all data from Shared Preferences
        // editor.clear();
        // editor.commit();


        editor.remove(KEY_service_type);


        editor.apply();

        // After logout redirect user to Loing Activity
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

        // Add new Flag to start new Activity
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

    }

    public void clear_from_string_values() {
        // Clearing all data from Shared Preferences
        // editor.clear();
        // editor.commit();


        editor.remove(KEY_from_string);


        editor.apply();

        // After logout redirect user to Loing Activity
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

        // Add new Flag to start new Activity
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring LoginActivity Activity

    }


    public void createmy_category(String category_name, String category_id) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_category_name, category_name);
        editor.putString(KEY_category_id, category_id);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void createmy_card(String card_no, String card_id) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_card_no, card_no);
        editor.putString(KEY_card_id, card_id);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void createmy_country_code(String country_name, String country_id) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_country_name, country_name);
        editor.putString(KEY_country_id, country_id);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void createmy_currency_code(String currency_name, String currency_id) {
        // Storing login value as TRUE
        editor.putBoolean("IsLoggedIn", true);

        // Storing name in prefs
        // s editor.putString(Tag_pass, pass);
        // editor.putString(KEY_driver_id, driverid);
        /*
         * editor.putString(KEY_name, name); editor.putString(KEY_image, image);
         * editor.putString(KEY_email, email);
         */

        editor.putString(KEY_currency_name, currency_name);
        editor.putString(KEY_currency_id, currency_id);


        // Storing email in pref

        // commit changes
        editor.commit();
    }

    public void logout_punchout() {
        // Clearing all data from Shared Preferences
        // editor.clear();
        // editor.commit();


        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);


    }

}
