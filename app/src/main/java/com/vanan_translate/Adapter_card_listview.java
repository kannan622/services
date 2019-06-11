package com.vanan_translate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Adapter_card_listview extends BaseAdapter {

    Context c;
    List<offers_model> players = null;
    // CustomFilter filter;
    ArrayList<offers_model> filterList;
    boolean isSet;
    SessionManager session;
    Adapter_card_listview myAdapter;
    private boolean isSelected[];
    private int selectedPosition = -1;
    private ArrayList<offers_model> arraylist;


    public Adapter_card_listview(Context ctx, List<offers_model> players) {
        // TODO Auto-generated constructor stub

        this.c = ctx;
        this.players = players;

        this.arraylist = new ArrayList<offers_model>();
        this.arraylist.addAll(players);


        session = new SessionManager(c);


        try {
            if (players.isEmpty()) {
                Toast.makeText(c, "No data Found", Toast.LENGTH_LONG)
                        .show();
            }
        } catch (Exception e) {

        }

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return players.size();
    }

    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return players.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub


        return players.indexOf(getItem(pos));
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        myAdapter = new Adapter_card_listview(c, players);

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_view, null);
        }


        final ImageView iv = convertView.findViewById(R.id.flag_from);


        //SET DATA TO THEM


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        String flag_ = Iconstant.baseurl + Iconstant.flag_url + players.get(pos).getflag();
        Glide.with(c).load(flag_).apply(options).into(iv);
        final TextView textView = convertView.findViewById(R.id.country_name);


        //   final ImageView checkedImage = convertView.findViewById(R.id.row_list_checkbox_image);

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check);

        RelativeLayout rel_val = convertView.findViewById(R.id.rel_name);


        rel_val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkBox.isChecked()) {
                    selectedPosition = pos;
                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();

                int i = pos;
                String from_check = "from_yes";

                session.from_status_check(from_check);

                session.create_from_lang(players.get(pos).getid(), players.get(pos).getlanguage_name(), players.get(pos).getlanguage_code(), players.get(pos).getflag());

                checkBox.setTag(pos);
                //SET DATA TO THEM


                if (pos == selectedPosition) {
                    checkBox.setChecked(true);
                } else checkBox.setChecked(false);

                checkBox.setOnClickListener(onStateChangedListener(checkBox, pos));
                notifyDataSetChanged();
            }
        });

        checkBox.setTag(pos);
        //SET DATA TO THEM
        textView.setText((players.get(pos).getlanguage_name()));

        if (pos == selectedPosition) {
            checkBox.setChecked(true);
        } else checkBox.setChecked(false);


        if (session.getUserDetails().get(SessionManager.KEY_from_flag_id) != null) {

            String ss = session.getUserDetails().get(SessionManager.KEY_from_flag_id);


            String myString = ss;


            //  Log.d("session value ", ""  + "::::" + pos + "::::::" + selectedPosition);
            if (players.get(pos).getid().matches(myString)) {
                checkBox.setChecked(true);
            } else checkBox.setChecked(false);

        } else {
            checkBox.setChecked(false);
        }


        checkBox.setOnClickListener(onStateChangedListener(checkBox, pos));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {

                if (checkBox.isChecked()) {
                    selectedPosition = pos;
                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();

                int i = pos;
                String from_check = "from_yes";

                session.from_status_check(from_check);

                session.create_from_lang(players.get(pos).getid(), players.get(pos).getlanguage_name(), players.get(pos).getlanguage_code(), players.get(pos).getflag());

                checkBox.setTag(pos);
                //SET DATA TO THEM


                if (pos == selectedPosition) {
                    checkBox.setChecked(true);
                } else checkBox.setChecked(false);

                checkBox.setOnClickListener(onStateChangedListener(checkBox, pos));
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

    private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    selectedPosition = position;
                } else {
                    selectedPosition = -1;
                }
                notifyDataSetChanged();

                int i = position;
                String from_check = "from_yes";

                session.from_status_check(from_check);

                session.create_from_lang(players.get(position).getid(), players.get(position).getlanguage_name(), players.get(position).getlanguage_code(), players.get(position).getflag());

                //Log.d("sess", "" + position + "::::" + players.get(position).getlanguage_name() + "::::" + players.get(position).getlanguage_code() + "::::" + players.get(position).getflag());

            }
        };
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        players.clear();
        if (charText.length() == 0) {
            players.addAll(arraylist);
        } else {
            for (offers_model wp : arraylist) {
                if (wp.getlanguage_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    players.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
