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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by wafa0 on 20/03/18.
 */

public class cartadapter extends ArrayAdapter<carto> {
    private Activity context;
    List<carto> artists;
    DatabaseReference type;

    public cartadapter(Activity context, List<carto> artists) {
        super(context, R.layout.carto_layout, artists);
        this.context = context;
        this.artists = artists;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.carto_layout, null, true);
        //TextView name = (TextView) listViewItem.findViewById(R.id.user);

        carto artist = artists.get(position);

        TextView Name = (TextView) listViewItem.findViewById(R.id.user);
        TextView st = (TextView) listViewItem.findViewById(R.id.st);
       Name.setText(artist.getName());
       st.setText(artist.getStatus());

        return listViewItem;
    }
}