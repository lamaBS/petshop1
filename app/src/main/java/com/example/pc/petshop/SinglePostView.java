package com.example.pc.petshop;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SinglePostView extends AppCompatActivity {
 private DatabaseReference mDatabase;
 private String post_id=null;
 private ImageView postImage;
 private TextView post_title,color,city;
 private TextView post_dec;
 private TextView post_OwnerID;
   static String postOwnerID;
 private TextView post_OwnerName;
 private Button chat;
    DatabaseReference chatre;
    DatabaseReference name;
    DatabaseReference chatref;
    FirebaseAuth mAuth;
    String name1;
    static String postUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_view);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Pet");
        post_id = getIntent().getExtras().getString("post_id");
        mAuth = FirebaseAuth.getInstance();
     //   databaseReferenceComment = FirebaseDatabase.getInstance().getReference().child("commentTest");
   //     mQueryCurrentPost = databaseReferenceComment.orderByChild("postID").equalTo(post_id);



        postImage=(ImageView)findViewById(R.id.single_post_image);
        post_title=(TextView)findViewById(R.id.single_imageTitle);
        post_dec=(TextView)findViewById(R.id.single_imageDesc);
        post_OwnerName=(TextView)findViewById(R.id.single_userName);
        color=(TextView)findViewById(R.id.color);
        city=(TextView)findViewById(R.id.city);
       chat=(Button) findViewById(R.id.chat);
        mDatabase.child(post_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String postTitle=(String) dataSnapshot.child("price").getValue();
                String postDesc=(String) dataSnapshot.child("age").getValue();
             postOwnerID=(String) dataSnapshot.child("ownerid").getValue();
                String post_image=(String) dataSnapshot.child("img").getValue();
                postUsername=(String) dataSnapshot.child("username").getValue();
                String c =(String) dataSnapshot.child("color").getValue();
                String cit =(String) dataSnapshot.child("city").getValue();


                //  String time=(String) dataSnapshot.child("date").getValue();
                post_title.setText("السعر:"+postTitle+".SR");
                post_dec.setText("العمر بالاشهر:"+postDesc+"");
                post_OwnerName.setText("اسم البائع:"+postUsername+"");
                city.setText("المدينة:"+cit+"");
                color.setText("اللون:"+c+"");
               // timeDate.setText(time);
                Picasso.with(SinglePostView.this).load(post_image).into(postImage);
/*
                if(mAuth.getCurrentUser().getUid().equals(postOwnerID)){
                    removeButton.setVisibility(View.VISIBLE);
                }
*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                gochat();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

private void gochat() {
    mAuth = FirebaseAuth.getInstance();
    final String user2=mAuth.getCurrentUser().getUid();

//get name
    name = FirebaseDatabase.getInstance().getReference();
    name.child("buyer").addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String name = dataSnapshot.child(user2).child("cfirstName").getValue(String.class);
            name1=name;
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Toast.makeText(SinglePostView.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
        }
    });

    // Toast.makeText(SinglePostView.this,user1+"_"+user2, Toast.LENGTH_LONG).show();
    chatref= FirebaseDatabase.getInstance().getReference();
    chatref.child("Chatroom").child(postOwnerID+"_"+user2).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            String room = dataSnapshot.child("room").getValue(String.class);

            if(room!=null){
                // Toast.makeText(SinglePostView.this, room+"room", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SinglePostView.this, chatting.class);
                Bundle b=new Bundle();
                b.putString("room",room);
                b.putString("me",name1);
                intent.putExtras(b);
                startActivity(intent);
            }
            else{
                name = FirebaseDatabase.getInstance().getReference();
                name.child("buyer").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child(user2).child("cfirstName").getValue(String.class);
                        String last = dataSnapshot.child(user2).child("clastName").getValue(String.class);
                        chatre= FirebaseDatabase.getInstance().getReference();
                        Chatroom r =new Chatroom();
                        r.setCID(user2);
                        r.setFname(postUsername);
                        r.setFID(postOwnerID);
                        r.setCname(name+" "+last);
                        String room2=postOwnerID+"_"+user2;
                        r.setroom(room2);
                        chatre.child("Chatroom").child(postOwnerID+"_"+user2).setValue(r);
                        Intent intent = new Intent(SinglePostView.this, chatting.class);
                        Bundle b=new Bundle();
                        b.putString("room",room2);
                        b.putString("me",name);
                        intent.putExtras(b);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(SinglePostView.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Toast.makeText(SinglePostView.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
        }
    });

}










}