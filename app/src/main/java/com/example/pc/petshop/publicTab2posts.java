package com.example.pc.petshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


/**
 * Created by hadeel on 2/10/18.
 */

public class publicTab2posts extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private RecyclerView mPostList;
    static DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
     static Query mQueryCurrentOwner;
    static String serv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_tab2_posts);

        mPostList= findViewById(R.id.insta_list);
        mPostList.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(publicTab2posts.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mPostList.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        String currentUID= mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pet");
        mQueryCurrentOwner = databaseReference;
                //.orderByChild("uid").equalTo(currentUID);
        Spinner spinner=findViewById(R.id.cityspinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.cityall,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Pet,PostHolder> FBRA =new FirebaseRecyclerAdapter<Pet, PostHolder>(
                Pet.class,
                R.layout.public_card,
                PostHolder.class,
                mQueryCurrentOwner

        ) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Pet model, int position) {
                final String postID=getRef(position).getKey();
               // Toast.makeText(publicTab2posts.this, "id="+postID+" ", Toast.LENGTH_LONG).show();
                viewHolder.setName(model.getPrice());
                viewHolder.setDesc(model.getCity());
                viewHolder.setUserName(model.getUsername());
                viewHolder.setImage(publicTab2posts.this, model.getImg());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Toast.makeText(publicTab2posts.this, "id="+postID+" ", Toast.LENGTH_LONG).show();
                     Intent intent =new Intent(publicTab2posts.this,SinglePostView.class);
                     intent.putExtra("post_id",postID);
                     startActivity(intent);
                    }
                });

            }
        };
        mPostList.setAdapter(FBRA);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        serv =parent.getItemAtPosition(position).toString();
        if(serv.equals("الكل")){
        mQueryCurrentOwner=FirebaseDatabase.getInstance().getReference().child("Pet");
        onStart();}
      else {
            mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Pet").orderByChild("city").equalTo(serv);
            onStart();
        }
    }

    private void dofilter(String serv) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static class PostHolder extends RecyclerView.ViewHolder{

        public PostHolder(View itemView) {
            super(itemView);
            View mView= itemView;
        }

        public void setName(String name){
            TextView postName= (TextView) itemView.findViewById(R.id.imageTitle);
            postName.setText(name+".SR");
        }

        public void setDesc(String desc){
            TextView postDesc= (TextView) itemView.findViewById(R.id.imageDesc);
            postDesc.setText(desc);
        }

        public void setImage(Context ctx,String image){
            ImageView post_image=(ImageView) itemView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }

        public void setUserName(String UserName){
            TextView post_username=(TextView) itemView.findViewById(R.id.userName);
            post_username.setText(UserName);
        }


    }



}




