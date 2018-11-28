package com.example.pc.petshop;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class welcome  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private final String CHANNEL_ID ="Notification";
    private final int NOTIFICATION_ID= 001;
    DatabaseReference UDB;
    private FirebaseAuth mAuth;
    Button button;
    DatabaseReference databaseref;
    DatabaseReference databaseann;
    private FirebaseUser user;

    DatabaseReference databaseReferences;
    TextView username;
    TextView mail;
    TextView phone;
    TextView city1;
    TextView wwh;
    Switch allow;
    int phonnum=0;
    Switch available;
    FirebaseAuth firebaseAuth;
    private TextView location;
    DatabaseReference database;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
     Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
       //
        databaseann = FirebaseDatabase.getInstance().getReference("announcement");
        user = mAuth.getCurrentUser();
        databaseref = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        databaseref.child("seller").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                String name = dataSnapshot.child(user1).child("cfirstName").getValue(String.class);
                String last = dataSnapshot.child(user1).child("clastName").getValue(String.class);
                phonnum = dataSnapshot.child(user1).child("cponeNoumber").getValue(int.class);
                String email = dataSnapshot.child(user1).child("cemail").getValue(String.class);
                String city = dataSnapshot.child(user1).child("city").getValue(String.class);

                TextView profilename = (TextView) findViewById(R.id.user);
                profilename.setText(name+" "+last);
                mail = (TextView) findViewById(R.id.email);
                mail.setText(" " + email);
                phone = (TextView) findViewById(R.id.phone);
                phone.setText(" " + phonnum + " ");
                city1 = (TextView) findViewById(R.id.city);
                city1.setText(" " + city + " ");
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(welcome.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

       //
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

public void showtext(){
    Intent intent = new Intent(welcome.this, addpet.class);
     startActivity(intent);


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

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(welcome.this, updateProfile.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.chat) {
            Intent intent = new Intent(welcome.this, ownerchatlist.class);
            startActivity(intent);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }   else if (id == R.id.sugesstionmenu) {
            Intent intent = new Intent(welcome.this, sendSuggestion.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.newPasswordd) {
            Intent intent = new Intent(welcome.this, PasswordActivity.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
// newPasswordd
        }
        else if (id == R.id.nav_request) {
            Intent intent = new Intent(welcome.this, addpet.class);
            startActivity(intent);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

        }
        else if (id == R.id.nav_preorder) {
            Intent intent = new Intent(welcome.this, addsupplies.class);
            startActivity(intent);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        else if (id == R.id.orders) {
            Intent intent = new Intent(welcome.this, viewcartforseller.class);
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

    public void viewsupllies(View view) {
        Intent intent = new Intent(welcome.this, viewsupllies.class);
        startActivity(intent);

    }
    public void viewpets(View view) {
        Intent intent = new Intent(welcome.this, viewPets.class);
        startActivity(intent);



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



