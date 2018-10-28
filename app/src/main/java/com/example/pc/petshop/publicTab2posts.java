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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


/**
 * Created by hadeel on 2/10/18.
 */

public class publicTab2posts extends AppCompatActivity {

    private RecyclerView mPostList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Query mQueryCurrentOwner;
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
      //  mQueryCurrentOwner = databaseReference;
                //.orderByChild("uid").equalTo(currentUID);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Pet,PostHolder> FBRA =new FirebaseRecyclerAdapter<Pet, PostHolder>(
                Pet.class,
                R.layout.public_card,
                PostHolder.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(PostHolder viewHolder, Pet model, int position) {
                final String postID=getRef(position).getKey();
                viewHolder.setName(model.getPrice());
                viewHolder.setDesc(model.getCity());
                viewHolder.setUserName(model.getUsername());
                viewHolder.setImage(publicTab2posts.this, model.getImg());

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(publicTab2posts.this,welcome.class);
                        intent.putExtra("post_id",postID);
                        startActivity(intent);
                    }
                });

            }
        };
        mPostList.setAdapter(FBRA);
    }


    public static class PostHolder extends RecyclerView.ViewHolder{

        public PostHolder(View itemView) {
            super(itemView);
            View mView= itemView;
        }

        public void setName(String name){
            TextView postName= (TextView) itemView.findViewById(R.id.imageTitle);
            postName.setText(name);
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




