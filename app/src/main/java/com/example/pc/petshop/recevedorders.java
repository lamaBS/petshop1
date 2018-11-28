package com.example.pc.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class recevedorders extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference myRef2 = database.getReference();
    DatabaseReference myRef3 = database.getReference();
    DatabaseReference tst= database.getReference();
    ListView listViewArtists;
    List<order> artists;
    FirebaseAuth firebaseAuth;
    int total=0;
    DatabaseReference name;
    private String oid,bid;
    DatabaseReference not;
    static String e="";
    DatabaseReference databaseref;
    Button send;
    private FirebaseUser CurrentOwner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleorder2);
        Bundle b = getIntent().getExtras();
        oid = b.getString("oid");
        bid = b.getString("bid");
        firebaseAuth= FirebaseAuth.getInstance();
        CurrentOwner=firebaseAuth.getCurrentUser();
        not= FirebaseDatabase.getInstance().getReference("announcement");
        listViewArtists = (ListView) findViewById(R.id.listViewTracks);

        //list to store artists
        artists = new ArrayList<>();
        send=(Button)findViewById(R.id.send);

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept();
            }
        });
    }

    private void accept() {

        myRef.child("buyers").child(CurrentOwner.getUid()).child(bid).child("status").setValue("مقبول");
        myRef.child("carto").child(bid).child(CurrentOwner.getUid()).child("status").setValue("مقبول");
        databaseref = FirebaseDatabase.getInstance().getReference();
        databaseref.child("buyer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                e = dataSnapshot.child(bid).child("cemail").getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(recevedorders.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

        final  String tid1 =not.push().getKey();
        final  String tid2 =not.push().getKey();
        //   Toast.makeText(chatting.this,"تم اضافة "+e, Toast.LENGTH_LONG).show();
        announcement sms = new announcement(tid1,e,"تم قبول طلبك رقم"+tid2+"");
        //notification(ms);
        sms.setOb(sms);
        sms.setAnn("تم قبول طلبك رقم"+tid2+"");
        not.child(tid1).setValue(sms);


    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("recivedorder").child(CurrentOwner.getUid()).orderByChild("buyerid").equalTo(bid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artists.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    order artist = new order(ds.getValue(order.class));
                    int price=Integer.parseInt( artist.getPrice());
                    total+=price;
                    artists.add(artist);
                }
                //creating adapter
                orderadapter artistAdapter = new orderadapter(recevedorders.this, artists);
                //attaching adapter to the listview
                listViewArtists.setAdapter(artistAdapter);
                send.setText("قبول الطلب "+total+" RS ");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }



}