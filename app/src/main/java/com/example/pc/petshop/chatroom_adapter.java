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

public class chatroom_adapter extends ArrayAdapter<Chatroom> {
    private Activity context;
    List<Chatroom> artists;
    private String first="";
    private String last="";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference type;
    FirebaseAuth mAuth;
    public chatroom_adapter(Activity context, List<Chatroom> artists) {
        super(context, R.layout.owner_chat_layout, artists);
        this.context = context;
        this.artists = artists;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
       final View listViewItem = inflater.inflate(R.layout.owner_chat_layout, null, true);
        //TextView name = (TextView) listViewItem.findViewById(R.id.user);

        Chatroom artist = artists.get(position);


        myRef.child("buyer").child(artist.getCID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String f = dataSnapshot.child("cfirstName").getValue(String.class);
                String l= dataSnapshot.child("clastName").getValue(String.class);
                first=f;
                last=l;

                TextView Name = (TextView) listViewItem.findViewById(R.id.user);

                Name.setText(first+" "+last);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       // mAuth = FirebaseAuth.getInstance();
        /*
        String user2=mAuth.getCurrentUser().getUid();
        if(artist.getUser().equalsIgnoreCase(user2)) {
            // tint.setBackground(Drawable.createFromPath("@drawable/fromme"));
            tint.setBackgroundColor(181818);
            msg.setGravity(Gravity.END | Gravity.BOTTOM);
        }
        else
            msg.setGravity(Gravity.START | Gravity.BOTTOM);
*/
        return listViewItem;
    }
}