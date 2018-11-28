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

public class SinglePostView2 extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String post_id=null;
    private ImageView postImage;
    private TextView post_title,color,city;
    private TextView post_dec;
    private TextView quatity;
    private TextView post_OwnerName;
    private Button buyy;
    static int q;
    private DatabaseReference databaseOWner;
    DatabaseReference myRef,tst;

    private DatabaseReference cartowner;
    FirebaseAuth mAuth;

    private FirebaseUser CurrentOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_view2);
        q=1;
        mAuth= FirebaseAuth.getInstance();
        CurrentOwner=mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Supplies");
        post_id = getIntent().getExtras().getString("post_id");
        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        tst = database.getReference();
        cartowner = database.getReference();
        postImage=(ImageView)findViewById(R.id.single_post_image);
        post_title=(TextView)findViewById(R.id.single_imageTitle);
        post_dec=(TextView)findViewById(R.id.single_imageDesc);
        post_OwnerName=(TextView)findViewById(R.id.single_userName);
        city=(TextView)findViewById(R.id.city);
        buyy=(Button) findViewById(R.id.cart);
        Button min=(Button) findViewById(R.id.min);
        Button max=(Button) findViewById(R.id.max);
        quatity=(TextView) findViewById(R.id.quatity);
        mDatabase.child(post_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               final String postTitle=(String) dataSnapshot.child("price").getValue();
                final String postDesc=(String) dataSnapshot.child("type").getValue();
                final String postOwnerID=(String) dataSnapshot.child("ownerid").getValue();
                final String post_image=(String) dataSnapshot.child("img").getValue();
                final String postUsername=(String) dataSnapshot.child("username").getValue();
               final String cit =(String) dataSnapshot.child("city").getValue();


                post_title.setText("السعر:"+postTitle+".SR");
                post_dec.setText("النوع:"+postDesc+"");
                post_OwnerName.setText("اسم البائع:"+postUsername+"");
                city.setText("المدينة:"+cit+"");

                // timeDate.setText(time);
                Picasso.with(SinglePostView2.this).load(post_image).into(postImage);

                buyy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseOWner= FirebaseDatabase.getInstance().getReference().child("buyer").child(CurrentOwner.getUid());
                        databaseOWner.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final  String tid =myRef.push().getKey();
                                order t1= new order ();
                                t1.setBuyerid(CurrentOwner.getUid());
                                t1.setCity(cit);
                                t1.setId(tid);
                                t1.setOwnerid(postOwnerID);
                                int price=Integer.parseInt(postTitle);
                                t1.setPrice(""+price*q);
                                t1.setType(postDesc);
                                t1.setQuantity(q);
                                t1.setStatus("في الانتظار");
                                t1.setUsername(postUsername);
                                myRef.child("order").child(CurrentOwner.getUid()).child(tid).setValue(t1);
                                Toast.makeText(SinglePostView2.this, "تم اضافة الطلب للسلة", Toast.LENGTH_LONG).show();
                                carto o=new carto(postOwnerID,postUsername,CurrentOwner.getUid());
                              //  final  String j =cartowner.push().getKey();
                                 cartowner.child("carto").child(CurrentOwner.getUid()).child(postOwnerID).setValue(o);


                                myRef.child("order").child(CurrentOwner.getUid()).child(tid).child("buyername").setValue(dataSnapshot.child("cfirstName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                        }
                                    }
                                });
                                tst.child("carto").child(CurrentOwner.getUid()).child(postOwnerID).child("buyername").setValue(dataSnapshot.child("cfirstName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Intent mIntent = new Intent(SinglePostView2.this, buysupplies.class);
                                            startActivity(mIntent);
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });
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
        min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          if(q==1) return;
          else {
              q--;
              quatity.setText(q+"");
          }
            }

        });
        //
        max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               q++;
                quatity.setText(q+"");

            }    });
        //
    }

    @Override
    public void onStart() {
        super.onStart();

    }




}
