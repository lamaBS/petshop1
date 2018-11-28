package com.example.pc.petshop;

import android.app.NotificationChannel;
import com.google.firebase.auth.FirebaseUser;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    DatabaseReference databaseann;
    private FirebaseUser user;
    static String e="";
    private DatabaseReference appUse;
    private final String CHANNEL_ID ="Notification";
    private final int NOTIFICATION_ID= 001;
String room;
String typ="";
    DatabaseReference chat;
    Button button;
    DatabaseReference databaseref;
    DatabaseReference myRef,not;
    String type="hi";
    FirebaseAuth mAuth;
    String name="  ";
    String me="me";
    String recever="him";
    ////////////








   ///////////////////////////////////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        databaseann = FirebaseDatabase.getInstance().getReference("announcement");
        appUse=FirebaseDatabase.getInstance().getReference("type");
        mAuth= FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);

        Bundle b = getIntent().getExtras();
        room = b.getString("room");
        me = b.getString("me");
        recever = b.getString("recever");
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

     /*   databaseann.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot artistSnapshot :dataSnapshot.getChildren() ){

                    announcement save1=artistSnapshot.getValue(announcement.class);

                    if(user.getEmail().equals(save1.getName())){

                        notification(save1.getAnn());
                        databaseann= FirebaseDatabase.getInstance().getReference("announcement").child(save1.getId());
                        //save the value to the

                        databaseann.removeValue();

                    }

                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext()
                        ,"Check your Internet connection", Toast.LENGTH_LONG).show();
            }
        });

*/





       // Long tsLong = System.currentTimeMillis()/1000;
        //String ts = tsLong.toString();
      //  Toast.makeText(this, " "+ts+" time", Toast.LENGTH_LONG).show();
    }

    /////


                                              //////

    @Override
    protected void onStart() {
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
        not= FirebaseDatabase.getInstance().getReference("announcement");
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
       // notification(ms);
//get email


        databaseref = FirebaseDatabase.getInstance().getReference();

        appUse.child(recever).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    type=dataSnapshot.getValue(String.class).trim();

                    if(type.equals("buyer")){
                        // Read from the database
                        databaseref.child("buyer").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                 e = dataSnapshot.child(recever).child("cemail").getValue(String.class);
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Toast.makeText(chatting.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
                            }
                        });


                        //

                    }
                    else{
// Read from the database
                        databaseref.child("seller").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                e = dataSnapshot.child(recever).child("cemail").getValue(String.class);
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Toast.makeText(chatting.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
                            }
                        });


                        //

                    } }

                @Override
                public void onCancelled(DatabaseError databaseError) {
///
                }
            });

        // Singed in successfull
        //get email

        final  String tid1 =not.push().getKey();
     //   Toast.makeText(chatting.this,"تم اضافة "+e, Toast.LENGTH_LONG).show();
        announcement sms = new announcement(tid1,e,ms);
        //notification(ms);
        sms.setOb(sms);
        sms.setAnn(ms);
        not.child(tid1).setValue(sms);
        msg.setText("");




    }

/*
    public void notification(String notif){

        createNotificationChannel();

        //onclick the notification open the activity
        Intent intent = new Intent(getApplicationContext()
                ,chatting.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);
        //

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_audiotrack_light);
        builder.setContentTitle("محادثه جديده");
        builder.setContentText(notif);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);//auto delete the notification when click it
        builder.setContentIntent(pendingIntent);//onclick the notification open the activity

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }


    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            CharSequence name = "Personal Notification";
            String description = "Include all the personal notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }


*/

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




}
