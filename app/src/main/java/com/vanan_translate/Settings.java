package com.vanan_translate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class Settings extends Fragment {

    public static Dialog dialog;
    RelativeLayout privacy, terms, aboutus, contactus, edit_profile, rel_faqs_, logout, rel_shareapp_;
    SessionManager session;
    TextView profile_email;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        View recordView = inflater.inflate(R.layout.settings, container, false);
        Fabric.with(getActivity(), new Crashlytics());
        session = new SessionManager(getActivity());
        profile_email = recordView.findViewById(R.id.editprofile);

        if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {
            String session_email = (session.getUserDetails().get(SessionManager.KEY_email));


            profile_email.setText(session_email);
        } else {
            profile_email.setText("Vanan Account Login");
        }


        dialog = new Dialog(getActivity());
        edit_profile = recordView.findViewById(R.id.rel1);
        privacy = recordView.findViewById(R.id.rel2);
        terms = recordView.findViewById(R.id.rel3);
        aboutus = recordView.findViewById(R.id.rel4);
        contactus = recordView.findViewById(R.id.rel5);
        logout = recordView.findViewById(R.id.rel6);
        rel_faqs_ = recordView.findViewById(R.id.rel_faqs);

        rel_shareapp_ = recordView.findViewById(R.id.rel_shareapp);


        if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {
            logout.setVisibility(View.VISIBLE);

        } else {

            logout.setVisibility(View.GONE);
        }


        rel_faqs_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Intent i = new Intent(getActivity(), Social_media.class);
                    i.putExtra("media", "faqs");
                    startActivity(i);

                } else {
                    Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });

        rel_shareapp_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Vanan Translate");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {
                        Intent i = new Intent(getActivity(), Edit_profile.class);
                        startActivity(i);

                    } else {
                        Intent i = new Intent(getActivity(), Login.class);
                        startActivity(i);

                    }
                } else {
                    Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Intent i = new Intent(getActivity(), Social_media.class);
                    i.putExtra("media", "privacy");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Intent i = new Intent(getActivity(), Social_media.class);
                    i.putExtra("media", "terms");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Intent i = new Intent(getActivity(), Social_media.class);
                    i.putExtra("media", "aboutus");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Intent i = new Intent(getActivity(), Contactus.class);

                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Logout...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to Logout?");

                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.logo);


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // Write your code here to invoke YES event
                        try {
                            session.logout_punchout();
                            getActivity().finish();
                        } catch (Exception e) {

                        }


                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event

                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();


            }
        });

        return recordView;
    }
}
