package com.example.pc.petshop;

import android.app.Activity;
import android.drm.DrmStore;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by wafa0 on 20/03/18.
 */

public class Chat_list extends ArrayAdapter<Chat> {
    private Activity context;
    List<Chat> artists;
    DatabaseReference type;
    FirebaseAuth mAuth;
    public Chat_list(Activity context, List<Chat> artists) {
        super(context, R.layout.chat_layout, artists);
        this.context = context;
        this.artists = artists;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.chat_layout, null, true);
        TextView name = (TextView) listViewItem.findViewById(R.id.user);
        TextView msg = (TextView) listViewItem.findViewById(R.id.msg);
        ConstraintLayout tint=listViewItem.findViewById(R.id.tint);
        Chat artist = artists.get(position);
        name.setText(artist.getName());
        msg.setText(artist.getMsg());
        mAuth = FirebaseAuth.getInstance();
        String user2=mAuth.getCurrentUser().getUid();
        if(artist.getUser().equalsIgnoreCase(user2)) {
           // tint.setBackground(Drawable.createFromPath("@drawable/fromme"));
            tint.setBackgroundColor(Color.parseColor("#ffffff") );
            msg.setGravity(Gravity.START);
//ma@gmail.com
        }

        return listViewItem;
    }
}