package com.example.pc.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wafa0 on 27/03/18.
 */

public class ownerchatlist extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    ListView listViewArtists;
    List<Chatroom> artists;
    FirebaseAuth firebaseAuth;
    DatabaseReference name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ownerchatlist);


        listViewArtists = (ListView) findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();


        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
               final String id1 = user.getUid();//customer id is the
                final int pos=position;
                name = FirebaseDatabase.getInstance().getReference();
                name.child("seller").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child(id1).child("fusername").getValue(String.class);
                        Intent intent = new Intent(ownerchatlist.this, chatting.class);
                        Chatroom artist = artists.get(pos);
                        Bundle b=new Bundle();
                        b.putString("room",artist.getroom());
                        b.putString("me",name);
                        b.putString("recever",artist.getCID());
                        intent.putExtras(b);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                    }
                });

                //   String


            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("Chatroom").orderByChild("fid").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                        artists.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Chatroom artist =new Chatroom(ds.getValue(Chatroom.class));
                            artists.add(artist);
                        }
                        //creating adapter
                        chatroom_adapter artistAdapter = new chatroom_adapter(ownerchatlist.this, artists);
                        //attaching adapter to the listview
                        listViewArtists.setAdapter(artistAdapter);
                    }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }








}
