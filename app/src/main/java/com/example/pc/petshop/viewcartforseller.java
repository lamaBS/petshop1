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

public class viewcartforseller extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    ListView listViewArtists;
    List<carto> artists;
    FirebaseAuth firebaseAuth;
    DatabaseReference name;
    private FirebaseUser CurrentOwner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcartforbuyer);
        firebaseAuth= FirebaseAuth.getInstance();
        CurrentOwner=firebaseAuth.getCurrentUser();
        listViewArtists = (ListView) findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();


        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                carto artist = artists.get(position);
                String ownerid=artist.getId();
                String buyerid=artist.getBuyerid();
                Intent intent = new Intent(viewcartforseller.this,recevedorders.class);
                Bundle b=new Bundle();
                b.putString("oid",ownerid);
                b.putString("bid",buyerid);
                intent.putExtras(b);
                startActivity(intent);

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("buyers").child(CurrentOwner.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artists.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    carto artist = new carto(ds.getValue(carto.class));
                    artists.add(artist);
                }
                //creating adapter
                cartadapter2 artistAdapter = new cartadapter2(viewcartforseller.this, artists);
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