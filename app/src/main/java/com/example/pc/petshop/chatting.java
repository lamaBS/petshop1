package com.example.pc.petshop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.petshop.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wafa0 on 20/03/18.
 */

public class chatting extends AppCompatActivity{
    ListView listViewArtists;
    List<Chat> artists;
String room;
String typ="";
    DatabaseReference chat;
    Button button;
    DatabaseReference myRef;
    DatabaseReference type;
    FirebaseAuth mAuth;
    String name="  ";
    String me="me";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);
        Bundle b = getIntent().getExtras();
        room = b.getString("room");
        me = b.getString("me");
        Toast.makeText(getApplicationContext(),me, Toast.LENGTH_LONG).show();
        if (room == null) {
       return;
        }
        artists = new ArrayList<>();
        //getting views
        listViewArtists = (ListView)findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();

Button send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
       // Long tsLong = System.currentTimeMillis()/1000;
        //String ts = tsLong.toString();
      //  Toast.makeText(this, " "+ts+" time", Toast.LENGTH_LONG).show();
    }
        @Override
        public void onStart() {
            super.onStart();

            myRef= FirebaseDatabase.getInstance().getReference();
            myRef.child("Chat").child(room).orderByChild("date").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                            artists.clear();

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                Chat artist =new Chat(ds.getValue(Chat.class));
                                artists.add(artist);
                            }


                            //creating adapter
                            Chat_list artistAdapter = new Chat_list(chatting.this, artists);
                            //attaching adapter to the listview
                            listViewArtists.setAdapter(artistAdapter);
                        }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
        }

    public void send() {
        final String ms;
         EditText msg=findViewById(R.id.txt);
           ms = msg.getText().toString();
           if(TextUtils.isEmpty(ms))
               return;

        mAuth = FirebaseAuth.getInstance();
        final String user2=mAuth.getCurrentUser().getUid();

        chat= FirebaseDatabase.getInstance().getReference("Chat");
        String tid =chat.push().getKey();
        Chat ch =new Chat();
        ch.setName(me);
        ch.setMsg(ms);
        ch.setUser(user2);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        ch.setDate(ts);
        chat.child(room).child(tid).setValue(ch);

/*
        //to get the type and name
        type= FirebaseDatabase.getInstance().getReference("APPUsers");
        type.child(user2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String t= dataSnapshot.child("type").getValue(String.class);
                typ=t;
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
        DatabaseReference d=FirebaseDatabase.getInstance().getReference();
        DatabaseReference d1=FirebaseDatabase.getInstance().getReference();
        DatabaseReference d2=FirebaseDatabase.getInstance().getReference();
        if(typ.equalsIgnoreCase("Customer")){
            d.child("Customer").child(user2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    chat= FirebaseDatabase.getInstance().getReference("Chat");
                    String n= dataSnapshot.child("cfirstName").getValue(String.class);
                    String tid =chat.push().getKey();
                    Chat ch =new Chat();
                    ch.setMsg(ms);
                    ch.setUser(user2);
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    ch.setDate(ts);
                    ch.setName(n);
                    chat.child(room).child(tid).setValue(ch);
                  }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
        }
        else

        if(typ.equalsIgnoreCase("PrivateOwner")){
            d1.child("PrivateFoodTruckOwner").child(user2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String n= dataSnapshot.child("fusername").getValue(String.class);

                    chat= FirebaseDatabase.getInstance().getReference("Chat");
                    String tid =chat.push().getKey();
                    Chat ch =new Chat();
                    ch.setMsg(ms);
                    ch.setUser(user2);
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    ch.setDate(ts);
                    ch.setName(n);
                    chat.child(room).child(tid).setValue(ch);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
        }
        else
        if(typ.equalsIgnoreCase("PublicOwner")){
            d2.child("PublicFoodTruckOwner").child(user2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String n= dataSnapshot.child("fusername").getValue(String.class);

                    chat= FirebaseDatabase.getInstance().getReference("Chat");
                    String tid =chat.push().getKey();
                    Chat ch =new Chat();
                    ch.setMsg(ms);
                    ch.setUser(user2);
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    ch.setDate(ts);
                    ch.setName(n);
                    chat.child(room).child(tid).setValue(ch);
                }
                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });
        }
        //to get the type and name
        */
        msg.setText("");






    }
}
// chat= FirebaseDatabase.getInstance().getReference("Chat");
//  String tid =chat.push().getKey();
//  Chat ch1 =new Chat("تم بدءالدردشه","truckStation","truckStation",(System.currentTimeMillis()/1000)+"");
//   chat.child(room).child(tid).setValue(ch1);
//attaching value event listener
//  FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
// String id = user.getUid();//customer id is the same as rating id to make it easy to refer
     /*  chat= FirebaseDatabase.getInstance().getReference("Chat");
        String tid =chat.push().getKey();
        Chat ch =new Chat();
        ch.setMsg(msg.getText().toString());
        ch.setUser(user2);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        ch.setDate(ts);
        chat.child(room).child(tid).setValue(ch);
        Toast.makeText(getApplicationContext(), " "+name+"تم اضافه ", Toast.LENGTH_LONG).show();
        */
//
//   String a=getname();
//  ch.setName(a);


