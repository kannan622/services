package com.vanan_translate;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class Pro_Services extends Fragment {


    static SessionManager session;

    static String type;

    Button btn_translation, btn_transcription, btn_voiceover, btn_captioning, btn_typing, btn_subtitle;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        View recordView = inflater.inflate(R.layout.pro_services, container, false);

        session = new SessionManager(getActivity());
        Fabric.with(getActivity(), new Crashlytics());

        btn_translation = recordView.findViewById(R.id.translation_button);
        btn_transcription = recordView.findViewById(R.id.transcription_button);
        btn_voiceover = recordView.findViewById(R.id.voiceover_btn);
        btn_captioning = recordView.findViewById(R.id.captioning_btn);
        btn_typing = recordView.findViewById(R.id.typing_btn);
        btn_subtitle = recordView.findViewById(R.id.subtitle_btn);


        btn_translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {

                    type = "translation";
                    session.create_service_type(type);

                    Intent intent = new Intent(getActivity(), GetQuote.class);
                    startActivity(intent);

                } else {

                    type = "translation";
                    session.create_service_type(type);
                    Intent i = new Intent(getActivity(), Login.class);

                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                }


            }
        });


        btn_transcription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {

                    type = "transcription";
                    session.create_service_type(type);


                    Intent intent = new Intent(getActivity(), GetQuote.class);
                    startActivity(intent);

                } else {
                    type = "transcription";
                    session.create_service_type(type);
                    Intent i = new Intent(getActivity(), Login.class);

                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                }


            }
        });


        btn_voiceover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {

                    type = "voiceover";
                    session.create_service_type(type);

                    Intent intent = new Intent(getActivity(), GetQuote.class);
                    startActivity(intent);

                } else {
                    type = "voiceover";
                    session.create_service_type(type);
                    Intent i = new Intent(getActivity(), Login.class);

                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                }


            }
        });

        btn_captioning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {

                    type = "captioning";
                    session.create_service_type(type);

                    Intent intent = new Intent(getActivity(), GetQuote.class);
                    startActivity(intent);

                } else {
                    type = "captioning";
                    session.create_service_type(type);
                    Intent i = new Intent(getActivity(), Login.class);

                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                }


            }
        });

        btn_typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {

                    type = "typing";
                    session.create_service_type(type);

                    Intent intent = new Intent(getActivity(), GetQuote.class);
                    startActivity(intent);

                } else {
                    type = "typing";
                    session.create_service_type(type);
                    Intent i = new Intent(getActivity(), Login.class);

                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                }


            }
        });

        btn_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if ((session.getUserDetails().get(SessionManager.KEY_email) != null) && (!session.getUserDetails().get(SessionManager.KEY_email).matches(""))) {

                    type = "subtitle";
                    session.create_service_type(type);


                    Intent intent = new Intent(getActivity(), GetQuote.class);
                    startActivity(intent);

                } else {
                    type = "subtitle";
                    session.create_service_type(type);
                    Intent i = new Intent(getActivity(), Login.class);

                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                }


            }
        });

        return recordView;
    }


}
