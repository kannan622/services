package com.vanan_translate;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

import static android.app.Activity.RESULT_OK;

public class Free_translation extends Fragment implements
        TextToSpeech.OnInitListener {

    public static EditText from_text;
    public static TextView to_text;
    public static GoogleTranslate translator;
    public static SessionManager session;
    public static String session_from_langcode, session_to_langcode;
    public static String session_default_from_langcode, session_default_to_langcode, session_default_from_langname, session_default_to_langname;
    static ImageView play_image;
    static ImageView pause_image;
    private static TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    RelativeLayout relat_to;
    FloatingActionButton fabb;
    ImageView clear_text;
    BottomSheetDialog dialog;
    Dialog dialog1;
    TextView from_lang_text, to_lang_text;
    ListView lv;
    RelativeLayout from_lang, to_lang;
    ImageView from_flag_img, to_flag_img;

    private UtteranceProgressListener mProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {


        } // Do nothing

        @Override
        public void onError(String utteranceId) {

        } // Do nothing.

        @Override
        public void onDone(String utteranceId) {


            mymethod();

        }
    };


    public static void mymethod() {
      /*  if (tts == null) {
            tts.stop();
            tts.shutdown();
            play_image.setVisibility(View.VISIBLE);
            pause_image.setVisibility(View.GONE);
        } else {
            play_image.setVisibility(View.GONE);
            pause_image.setVisibility(View.VISIBLE);
        }*/
        play_image.setVisibility(View.VISIBLE);
        pause_image.setVisibility(View.GONE);
    }

    public static void translated() {


        String translatetotagalog = from_text.getText().toString();//get the value of text

        String text;
        if (session_from_langcode != null && !session_from_langcode.matches("") && session_to_langcode != null && !session_to_langcode.matches("")) {
            text = translator.translte(translatetotagalog, session_from_langcode, session_to_langcode);
        } else {


            text = translator.translte(translatetotagalog, session_default_from_langcode, session_default_to_langcode);


        }


        to_text.setText(text);
        to_text.setMovementMethod(new ScrollingMovementMethod());
        session.to_string(to_text.getText().toString());

        if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {


            play_image.setVisibility(View.VISIBLE);


        } else {

            play_image.setVisibility(View.GONE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        View recordView = inflater.inflate(R.layout.free_transaltion, container, false);
        Fabric.with(getActivity(), new Crashlytics());

        tts = new TextToSpeech(getActivity(), this);

        dialog1 = new Dialog(getActivity());
        relat_to = recordView.findViewById(R.id.to_rel2);

        from_lang = recordView.findViewById(R.id.from_lang_rel);

        to_lang = recordView.findViewById(R.id.to_lang_rel);

        from_lang_text = recordView.findViewById(R.id.file_name_text);
        to_lang_text = recordView.findViewById(R.id.to_text);
        from_flag_img = recordView.findViewById(R.id.flag_from);
        to_flag_img = recordView.findViewById(R.id.flag_to);


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        session = new SessionManager(getActivity());

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

            Glide.with(getActivity()).load(flag_).apply(options).into(from_flag_img);

        }

        if (session.getUserDetails().get(SessionManager.KEY_to_lang_name) != null) {
            String to_langname = session.getUserDetails().get(SessionManager.KEY_to_lang_name);

            session_to_langcode = session.getUserDetails().get(SessionManager.KEY_to_lang_code);

            to_lang_text.setText("To : " + to_langname);

            String flag_ = Iconstant.baseurl + Iconstant.flag_url + session.getUserDetails().get(SessionManager.KEY_to_flag);

            Glide.with(getActivity()).load(flag_).apply(options).into(to_flag_img);

        }


        fabb = recordView.findViewById(R.id.fab);

        from_text = recordView.findViewById(R.id.from_text);
        to_text = recordView.findViewById(R.id.translatedtext);

        to_text.setMovementMethod(new ScrollingMovementMethod());

        clear_text = recordView.findViewById(R.id.cleartext);

        play_image = recordView.findViewById(R.id.play);
        pause_image = recordView.findViewById(R.id.pause);

        from_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Intent i = new Intent(getActivity(), Language_list.class);
                    i.putExtra("select", "from");
                    i.putExtra("point", "main");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }

            }
        });

        to_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    Intent i = new Intent(getActivity(), Language_list.class);
                    i.putExtra("select", "to");
                    i.putExtra("point", "main");
                    startActivity(i);
                } else {
                    Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


            }
        });

        if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {


            play_image.setVisibility(View.VISIBLE);
        } else {
            play_image.setVisibility(View.GONE);
        }




       /* if (session.getUserDetails().get(SessionManager.KEY_from_string) != null) {

            if (Network_config.is_Network_Connected_flag(getActivity())) {
                String ss = session.getUserDetails().get(SessionManager.KEY_from_string);

                String ss1 = session.getUserDetails().get(SessionManager.KEY_to_string);
                from_text.setText(ss);
                to_text.setText(ss1);

                session.clear_to_string_values();

                int position = from_text.length();
                Editable etext = from_text.getText();
                Selection.setSelection(etext, position);
                //  new EnglishToTagalog().execute();
            } else {
                Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                        getResources().getString(R.string.connection_message));
            }


        }*/

        if (session.getUserDetails().get(SessionManager.KEY_to_string) != null) {

            if (Network_config.is_Network_Connected_flag(getActivity())) {
                String ss1 = session.getUserDetails().get(SessionManager.KEY_from_string);
                String ss = session.getUserDetails().get(SessionManager.KEY_to_string);
                from_text.setText(ss1);
                to_text.setText(ss);

                //  session.clear_to_string_values();

                int position = from_text.length();
                Editable etext = from_text.getText();
                Selection.setSelection(etext, position);
                //  new EnglishToTagalog().execute();
            } else {
                Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                        getResources().getString(R.string.connection_message));
            }


        }


        play_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {
                    tts.setOnUtteranceProgressListener(mProgressListener);


                    speakOut();


                    pause_image.setVisibility(View.VISIBLE);
                    play_image.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "Text Not Avaliable", Toast.LENGTH_SHORT).show();
                }


            }
        });

        pause_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                }
                pause_image.setVisibility(View.GONE);
                play_image.setVisibility(View.VISIBLE);

            }
        });

        clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                from_text.setText("");
            }
        });


        fabb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput_doctype();
            }
        });


        relat_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                view = inflater.inflate(R.layout.bottom_layout, null);


                final RelativeLayout rel_copy = view.findViewById(R.id.rel2);
                final RelativeLayout rel_share = view.findViewById(R.id.rel3);
                final RelativeLayout rel_delete = view.findViewById(R.id.rel4);
                final RelativeLayout rel_expand = view.findViewById(R.id.rel5);
                final RelativeLayout rel_switch = view.findViewById(R.id.rel6);
                final TextView f_copy = view.findViewById(R.id.copy);
                final TextView f_share = view.findViewById(R.id.share);
                final TextView f_delete = view.findViewById(R.id.delete);
                final TextView f_expand = view.findViewById(R.id.expand);
                final TextView f_cancel = view.findViewById(R.id.switch_lang);


                rel_switch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (session.getUserDetails().get(SessionManager.KEY_from_lang_name) != null && session.getUserDetails().get(SessionManager.KEY_to_lang_name) != null) {


                            String switch_langid_from = session.getUserDetails().get(SessionManager.KEY_from_flag_id);
                            String switch_langname_from = session.getUserDetails().get(SessionManager.KEY_from_lang_name);
                            String switch_langcode_from = session.getUserDetails().get(SessionManager.KEY_from_lang_code);
                            String switch_langflag_from = session.getUserDetails().get(SessionManager.KEY_from_flag);

                            String switch_langid_to = session.getUserDetails().get(SessionManager.KEY_to_flag_id);
                            String switch_langname_to = session.getUserDetails().get(SessionManager.KEY_to_lang_name);
                            String switch_langcode_to = session.getUserDetails().get(SessionManager.KEY_to_lang_code);
                            String switch_langflag_to = session.getUserDetails().get(SessionManager.KEY_to_flag);

                            session.create_from_lang(switch_langid_to, switch_langname_to, switch_langcode_to, switch_langflag_to);
                            session.create_to_lang(switch_langid_from, switch_langname_from, switch_langcode_from, switch_langflag_from);

                            String from_check = "from_yes";

                            session.from_status_check(from_check);

                            String to_check = "to_yes";

                            session.to_status_check(to_check);

                            if (to_text.getText().toString() != null) {


                                session.to_string(to_text.getText().toString());
                            }


                           /* Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);*/

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher_round);

                            if (session.getUserDetails().get(SessionManager.KEY_from_lang_name) != null) {
                                String from_langname = session.getUserDetails().get(SessionManager.KEY_from_lang_name);

                                session_from_langcode = session.getUserDetails().get(SessionManager.KEY_from_lang_code);

                                from_lang_text.setText("From : " + from_langname);

                                String flag_ = Iconstant.baseurl + Iconstant.flag_url + session.getUserDetails().get(SessionManager.KEY_from_flag);

                                Glide.with(getActivity()).load(flag_).apply(options).into(from_flag_img);

                            }

                            if (session.getUserDetails().get(SessionManager.KEY_to_lang_name) != null) {
                                String to_langname = session.getUserDetails().get(SessionManager.KEY_to_lang_name);

                                session_to_langcode = session.getUserDetails().get(SessionManager.KEY_to_lang_code);

                                to_lang_text.setText("To : " + to_langname);

                                String flag_ = Iconstant.baseurl + Iconstant.flag_url + session.getUserDetails().get(SessionManager.KEY_to_flag);

                                Glide.with(getActivity()).load(flag_).apply(options).into(to_flag_img);

                            }
                            if (Network_config.is_Network_Connected_flag(getActivity())) {
                                String ss = session.getUserDetails().get(SessionManager.KEY_to_string);
                                from_text.setText(ss);
                                session.from_string(from_text.getText().toString());
                                session.clear_to_string_values();

                                int position = from_text.length();
                                Editable etext = from_text.getText();
                                Selection.setSelection(etext, position);
                                new EnglishToTagalog().execute();
                            } else {
                                Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                                        getResources().getString(R.string.connection_message));
                            }


                        } else {


                        }
                    }
                });


                rel_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        from_text.getText().clear();
                        dialog.dismiss();
                    }
                });
                rel_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        try {

                            if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {

                                String cpy = to_text.getText().toString();
                                Toast.makeText(getActivity(), "Text Copied", Toast.LENGTH_SHORT).show();

                                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("TEXT", cpy);
                                clipboard.setPrimaryClip(clip);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Text not avaliable", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                rel_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {
                            shareIt();

                        } else {
                            Toast.makeText(getActivity(), "Text not avaliable", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                rel_expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {
                            Intent i = new Intent(getActivity(), Expand.class);

                            i.putExtra("text", to_text.getText().toString());


                            startActivity(i);

                        } else {
                            Toast.makeText(getActivity(), "Text not avaliable", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(view);
                dialog.show();


            }
        });

        to_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                view = inflater.inflate(R.layout.bottom_layout, null);


                final RelativeLayout rel_copy = view.findViewById(R.id.rel2);
                final RelativeLayout rel_share = view.findViewById(R.id.rel3);
                final RelativeLayout rel_delete = view.findViewById(R.id.rel4);
                final RelativeLayout rel_expand = view.findViewById(R.id.rel5);
                final RelativeLayout rel_switch = view.findViewById(R.id.rel6);
                final TextView f_copy = view.findViewById(R.id.copy);
                final TextView f_share = view.findViewById(R.id.share);
                final TextView f_delete = view.findViewById(R.id.delete);
                final TextView f_expand = view.findViewById(R.id.expand);
                final TextView f_cancel = view.findViewById(R.id.switch_lang);


                rel_switch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (session.getUserDetails().get(SessionManager.KEY_from_lang_name) != null && session.getUserDetails().get(SessionManager.KEY_to_lang_name) != null) {


                            String switch_langid_from = session.getUserDetails().get(SessionManager.KEY_from_flag_id);
                            String switch_langname_from = session.getUserDetails().get(SessionManager.KEY_from_lang_name);
                            String switch_langcode_from = session.getUserDetails().get(SessionManager.KEY_from_lang_code);
                            String switch_langflag_from = session.getUserDetails().get(SessionManager.KEY_from_flag);

                            String switch_langid_to = session.getUserDetails().get(SessionManager.KEY_to_flag_id);
                            String switch_langname_to = session.getUserDetails().get(SessionManager.KEY_to_lang_name);
                            String switch_langcode_to = session.getUserDetails().get(SessionManager.KEY_to_lang_code);
                            String switch_langflag_to = session.getUserDetails().get(SessionManager.KEY_to_flag);

                            session.create_from_lang(switch_langid_to, switch_langname_to, switch_langcode_to, switch_langflag_to);
                            session.create_to_lang(switch_langid_from, switch_langname_from, switch_langcode_from, switch_langflag_from);

                            String from_check = "from_yes";

                            session.from_status_check(from_check);

                            String to_check = "to_yes";

                            session.to_status_check(to_check);

                            if (to_text.getText().toString() != null) {


                                session.to_string(to_text.getText().toString());
                            }


                          /*  Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);*/

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .error(R.mipmap.ic_launcher_round);

                            if (session.getUserDetails().get(SessionManager.KEY_from_lang_name) != null) {
                                String from_langname = session.getUserDetails().get(SessionManager.KEY_from_lang_name);

                                session_from_langcode = session.getUserDetails().get(SessionManager.KEY_from_lang_code);

                                from_lang_text.setText("From : " + from_langname);

                                String flag_ = Iconstant.baseurl + Iconstant.flag_url + session.getUserDetails().get(SessionManager.KEY_from_flag);

                                Glide.with(getActivity()).load(flag_).apply(options).into(from_flag_img);

                            }

                            if (session.getUserDetails().get(SessionManager.KEY_to_lang_name) != null) {
                                String to_langname = session.getUserDetails().get(SessionManager.KEY_to_lang_name);

                                session_to_langcode = session.getUserDetails().get(SessionManager.KEY_to_lang_code);

                                to_lang_text.setText("To : " + to_langname);

                                String flag_ = Iconstant.baseurl + Iconstant.flag_url + session.getUserDetails().get(SessionManager.KEY_to_flag);

                                Glide.with(getActivity()).load(flag_).apply(options).into(to_flag_img);

                            }
                            if (Network_config.is_Network_Connected_flag(getActivity())) {
                                String ss = session.getUserDetails().get(SessionManager.KEY_to_string);
                                from_text.setText(ss);
                                session.from_string(from_text.getText().toString());

                                session.clear_to_string_values();

                                int position = from_text.length();
                                Editable etext = from_text.getText();
                                Selection.setSelection(etext, position);
                                new EnglishToTagalog().execute();
                            } else {
                                Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                                        getResources().getString(R.string.connection_message));
                            }

                            dialog.dismiss();

                        } else {


                        }
                    }
                });


                rel_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        from_text.getText().clear();
                        dialog.dismiss();
                    }
                });
                rel_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        try {

                            if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {

                                String cpy = to_text.getText().toString();
                                Toast.makeText(getActivity(), "Text Copied", Toast.LENGTH_SHORT).show();

                                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip = ClipData.newPlainText("TEXT", cpy);
                                clipboard.setPrimaryClip(clip);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Text not avaliable", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                rel_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {
                            shareIt();

                        } else {
                            Toast.makeText(getActivity(), "Text not avaliable", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                rel_expand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (to_text.getText().toString() != null && !to_text.getText().toString().matches("")) {
                            Intent i = new Intent(getActivity(), Expand.class);

                            i.putExtra("text", to_text.getText().toString());


                            startActivity(i);

                        } else {
                            Toast.makeText(getActivity(), "Text not avaliable", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(view);
                dialog.show();


            }
        });

        from_text.addTextChangedListener(new TextWatcher() {

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

                if (Network_config.is_Network_Connected_flag(getActivity())) {
                    int position = from_text.length();
                    Editable etext = from_text.getText();
                    Selection.setSelection(etext, position);
                    new EnglishToTagalog().execute();


                } else {
                    Network_config.customAlert(dialog1, getActivity(), getResources().getString(R.string.app_name),
                            getResources().getString(R.string.connection_message));
                }


                session.from_string(from_text.getText().toString());
                // TODO Auto-generated method stub
            }
        });


        return recordView;
    }

    private void promptSpeechInput_doctype() {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        if (session_from_langcode != null && !session_from_langcode.matches("")) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, session_from_langcode);
        } else {

            if (session_default_from_langcode != null) {
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, session_default_from_langcode);
            }

        }

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1000);
       /* intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 7000);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 7000);*/

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);


        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));


        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {


                if (resultCode == RESULT_OK && null != data) {


                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    from_text.setText(result.get(0));


                    Log.d("PAGE>>>>>", result.get(0));


                }


                break;
            }

        }
    }

    private void shareIt() {
//sharing implementation here
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Translated Text");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, to_text.getText().toString());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        try {
            if (status == TextToSpeech.SUCCESS) {


                int result = tts.setLanguage(new Locale(session_to_langcode));


                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED) {


                    Log.e("TTS", "This Language is not supported");
                } else {
                /*play_image.setEnabled(true);
                speakOut();
           */
                }

            } else {
                Log.e("TTS", "Initilization Failed!");
            }

        } catch (Exception e) {

        }


    }

    private void speakOut() {


        Locale loc = tts.getLanguage();
        tts.setLanguage(new Locale(session_to_langcode));


  /*      Log.v(TAG, "default engine: " + tts.getDefaultEngine());
        Log.v(TAG, "current language/locale: " + loc.getDisplayName());
        Log.v(TAG, "current ISO3 language: " + loc.getISO3Language());
        Log.v(TAG, "current ISO3 country: " + loc.getISO3Country());
        Log.v(TAG, "current language: " + loc.getLanguage());
        Log.v(TAG, "current country: " + loc.getCountry());*/

        String text = to_text.getText().toString();

        HashMap<String, String> params;
        params = new HashMap<String, String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "Message");
        //  tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, params);


        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {
                mymethod();

            }
        });


    }

    public static class EnglishToTagalog extends AsyncTask<Void, Void, Void> {


        protected void onError(Exception ex) {


        }

        @Override

        protected Void doInBackground(Void... params) {


            try {

                translator = new GoogleTranslate("AIzaSyD7LLMujHvSPStAu6-9B9fGjNURaszWkeM");


                Thread.sleep(1000);


            } catch (Exception e) {

                // TODO Auto-generated catch block

                e.printStackTrace();

            }

            return null;


        }

        @Override

        protected void onCancelled() {

            super.onCancelled();

        }


        @Override
        protected void onPreExecute() {

            //start the progress dialog


            super.onPreExecute();

        }

        @Override

        protected void onPostExecute(Void result) {


            super.onPostExecute(result);

            translated();

        }


        @Override

        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);

        }


    }

}