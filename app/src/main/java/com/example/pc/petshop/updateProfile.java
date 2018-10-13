package com.example.pc.petshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class updateProfile extends AppCompatActivity {

    DatabaseReference databaseref;
    DatabaseReference wh;
    EditText fname;
    EditText lname;
    EditText mail;
    EditText phone,c;
    int phonum=0;
    String email1="";
    EditText passwor;
    FirebaseAuth firebaseAuth;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        context = this;
        Intent intent;


        databaseref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//easy to refer
        // Read from the database
        databaseref.child("seller").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String fnameP = dataSnapshot.child("cfirstName").getValue(String.class);
                String lnameP = dataSnapshot.child("clastName").getValue(String.class);
                phonum = dataSnapshot.child("cponeNoumber").getValue(int.class);
                email1 = dataSnapshot.child("cemail").getValue(String.class);
                String city = dataSnapshot.child("city").getValue(String.class);


                fname = (EditText) findViewById(R.id.fnameP);
                fname.setText(fnameP);

                lname = (EditText) findViewById(R.id.lnamP);
                lname.setText(lnameP);

                mail = (EditText) findViewById(R.id.emailP);
                  mail.setText(""+email1);

                phone = (EditText) findViewById(R.id.phoneP);
                 phone.setText("" + phonum);
                c = (EditText) findViewById(R.id.cityP);
                c.setText("" + city);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(updateProfile.this, "الرجاء التحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

        // Read from the database
        //end
    }


    public void chickInfo(View view) {
        //final String pass = passwor.getText().toString().trim();
        final String phoneN = phone.getText().toString().trim();
        final String emailpp = mail.getText().toString().trim();
        final String fnameP = fname.getText().toString().trim();
        final String lnameP = lname.getText().toString().trim();
        final String cc =c.getText().toString().trim();
        // final double x = 12.321;
        //  final double y = 13.1;
        FirebaseUser seller = FirebaseAuth.getInstance().getCurrentUser();
        String idSeller = seller.getUid();//customer id is the same as rating id to make it easy to refer
        if (!TextUtils.isEmpty(emailpp) && !TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(fnameP)&& !TextUtils.isEmpty(lnameP) && !TextUtils.isEmpty(cc)) {
            DatabaseReference owner = FirebaseDatabase.getInstance().getReference("seller").child(idSeller);

            //  PublicFoodTruckOwner owner = new PublicFoodTruckOwner();
            owner.child("cfirstName").setValue(fnameP);
            owner.child("clastName").setValue(lnameP);
            owner.child("cponeNoumber").setValue(Integer.parseInt(phoneN));
            owner.child("cemail").setValue(emailpp);
            owner.child("city").setValue(cc);

            Toast.makeText(updateProfile.this, "تم حفظ التغييرات", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(updateProfile.this, "الرجاء تعبئة كافة الحقول", Toast.LENGTH_SHORT).show();

    }
}