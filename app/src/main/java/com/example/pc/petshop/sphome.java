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


public class sphome  extends AppCompatActivity {

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
    Switch available;
    FirebaseAuth firebaseAuth;
    private TextView location;
    DatabaseReference database;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomesp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        //
        databaseref = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        databaseref.child("serviceprovider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer

                String name = dataSnapshot.child(user1).child("cfirstName").getValue(String.class);
                String last = dataSnapshot.child(user1).child("clastName").getValue(String.class);
                int phonnum = dataSnapshot.child(user1).child("cponeNoumber").getValue(int.class);
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
                Toast.makeText(sphome.this, "لايوجد اتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });


    }


    public void logout(View view) {
        mAuth.signOut();
        if(mAuth.getCurrentUser() == null) {
            Toast.makeText(this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, home.class));

        }
    }
}
