package com.example.pc.petshop;
import android.content.Intent;
import android.os.Bundle;
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

    DatabaseReference UDB;
    private FirebaseAuth mAuth;
    Button button;
    DatabaseReference databaseref;
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
            Intent intent = new Intent(welcome.this, postActivityPublic.class);
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

        } else if (id == R.id.nav_request) {
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
}
