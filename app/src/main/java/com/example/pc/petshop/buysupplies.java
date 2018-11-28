package com.example.pc.petshop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class buysupplies extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{

        private RecyclerView mPostList;
        static DatabaseReference databaseReference;
        private FirebaseAuth mAuth;
   TextView button;
    private final String CHANNEL_ID ="Notification";
    private final int NOTIFICATION_ID= 001;
    DatabaseReference databaseann;
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
            button = findViewById(R.id.cart);
            databaseann = FirebaseDatabase.getInstance().getReference("announcement");
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(buysupplies.this, viewcartforbuyer.class);
                    startActivity(intent);
                }
            });
            mAuth = FirebaseAuth.getInstance();
            String currentUID= mAuth.getCurrentUser().getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Supplies");
            mQueryCurrentOwner = databaseReference;
            Spinner spinner=findViewById(R.id.cityspinner);
            ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.type1,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            Spinner spinner2=findViewById(R.id.cityspinner2);
            ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this,R.array.price,android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);
            spinner2.setOnItemSelectedListener(this);


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            navigationView.setNavigationItemSelectedListener(this);
            databaseann.addValueEventListener(new ValueEventListener() {
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

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.cityspinner) {
            serv = parent.getItemAtPosition(position).toString();

            if (serv.equals("الكل")) {
                mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Supplies");
                onStart();
            } else {
                mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Supplies").orderByChild("type").equalTo(serv);
                onStart();
            }
        }
        else
        if(spinner.getId() == R.id.cityspinner2) {
            if (serv.equals("الكل")) {
                mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Supplies");
                onStart();
            }
            else
            if (serv.equals("من الاعلى الى الاقل سعرا")) {
                mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Supplies").orderByChild("price");
                onStart();
            } else {
                mQueryCurrentOwner = FirebaseDatabase.getInstance().getReference().child("Supplies");
                onStart();
            }
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
        if (id == R.id.prof) {
            Intent intent = new Intent(buysupplies.this, buyerhome.class);
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
        else if (id == R.id.cart) {
            Intent intent = new Intent(buysupplies.this, viewcartforbuyer.class);
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
    public void notification(String notif){

        createNotificationChannel();

        //onclick the notification open the activity
        Intent intent = new Intent(getApplicationContext()
                ,welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);
        //

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_audiotrack_light);
        builder.setContentTitle("إشعار جديد");
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
    }


