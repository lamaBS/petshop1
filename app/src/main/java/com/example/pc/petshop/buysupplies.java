package com.example.pc.petshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class buysupplies extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{

        private RecyclerView mPostList;
        static DatabaseReference databaseReference;
        private FirebaseAuth mAuth;
    static String serv;
        private FirebaseAuth.AuthStateListener mAuthListener;
       static Query mQueryCurrentOwner;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.buyerhome);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            mPostList= findViewById(R.id.insta_list);
            mPostList.setHasFixedSize(true);
            LinearLayoutManager layoutManager=new LinearLayoutManager(com.example.pc.petshop.buysupplies.this);
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
            mPostList.setLayoutManager(layoutManager);

            mAuth = FirebaseAuth.getInstance();
            String currentUID= mAuth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Supplies");
            mQueryCurrentOwner = databaseReference;
            Spinner spinner=findViewById(R.id.cityspinner);
            ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.type1,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            navigationView.setNavigationItemSelectedListener(this);

        }

        @Override
        public void onStart() {
            super.onStart();
            FirebaseRecyclerAdapter<Supplies,com.example.pc.petshop.buysupplies.PostHolder> FBRA =new FirebaseRecyclerAdapter<Supplies, com.example.pc.petshop.buysupplies.PostHolder>(
                    Supplies.class,
                    R.layout.public_card,
                    com.example.pc.petshop.buysupplies.PostHolder.class,
                    mQueryCurrentOwner

            ) {
                @Override
                protected void populateViewHolder(com.example.pc.petshop.buysupplies.PostHolder viewHolder, Supplies model, int position) {
                    final String postID=getRef(position).getKey();
                    // Toast.makeText(buysupplies.this, "id="+postID+" ", Toast.LENGTH_LONG).show();
                    viewHolder.setName(model.getPrice());
                    viewHolder.setDesc(model.getCity());
                    viewHolder.setUserName(model.getUsername());
                    viewHolder.setImage(com.example.pc.petshop.buysupplies.this, model.getImg());

                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Toast.makeText(buysupplies.this, "id="+postID+" ", Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(com.example.pc.petshop.buysupplies.this,SinglePostView2.class);
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
            mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Supplies");
            onStart();}
        else {
            mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Supplies").orderByChild("type").equalTo(serv);
            onStart();
        }
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

            public void setImage(Context ctx, String image){
                ImageView post_image=(ImageView) itemView.findViewById(R.id.post_image);
                Picasso.with(ctx).load(image).into(post_image);
            }

            public void setUserName(String UserName){
                TextView post_username=(TextView) itemView.findViewById(R.id.userName);
                post_username.setText(UserName);
            }


        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_public_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.hotel) {
            Intent intent = new Intent(buysupplies.this, viewHotel.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.pets) {
            Intent intent = new Intent(buysupplies.this, publicTab2posts.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }   else if (id == R.id.sugesstionmenu) {
            Intent intent = new Intent(buysupplies.this, sendSuggestion.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.chat) {
            Intent intent = new Intent(buysupplies.this, buyerchatlist.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.newPasswordd) {
            Intent intent = new Intent(buysupplies.this, PasswordActivity.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
// newPasswordd
        }
        else if (id == R.id.sup) {
            Intent intent = new Intent(buysupplies.this, buysupplies.class);
            startActivity(intent);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_logout) {

            mAuth.signOut();
            if(mAuth.getCurrentUser() == null){
                Toast.makeText(this , "تم تسجيل الخروج بنجاح" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent (this , home.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            }}
        return false;
    }
    }


