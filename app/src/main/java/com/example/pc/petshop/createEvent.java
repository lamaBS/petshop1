package com.example.pc.petshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class createEvent  extends AppCompatActivity {

    DatabaseReference UDB;
    DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addevent);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

    }


    public void addpet(View view) {
        EditText name=(EditText) findViewById(R.id.name);
        EditText adress=(EditText) findViewById(R.id.adress);
        EditText descrp=(EditText) findViewById(R.id.descrp);


        String c=name.getText().toString().trim();
        String t=descrp.getText().toString().trim();
        String cit=adress.getText().toString().trim();


        if ( !TextUtils.isEmpty(c) && !TextUtils.isEmpty(t)&& !TextUtils.isEmpty(cit)){
            String tid =myRef.push().getKey(); //get random id from firebase

            //get my id from firebase login id
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String user1 = user.getUid();//seller id
            event t1= new event (c,t,cit);
            myRef.child("Event").child(tid).setValue(t1);
            Toast.makeText(createEvent.this, "تمت اللإضافة", Toast.LENGTH_LONG).show();
           // startActivity(new Intent(this, viewPets.class));
        }
        else
            Toast.makeText(createEvent.this, "يجب تعبئة كافه الحقول", Toast.LENGTH_LONG).show();

    }

}