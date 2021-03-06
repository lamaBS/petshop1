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

public class addsupplies  extends AppCompatActivity {
    DatabaseReference UDB;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsupplies);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }


    public void addsupplies(View view) {
        EditText type=(EditText) findViewById(R.id.type);
        EditText city=(EditText) findViewById(R.id.city);
        EditText price=(EditText) findViewById(R.id.price);

        String t=type.getText().toString().trim();
        String cit=city.getText().toString().trim();
        String pr=price.getText().toString().trim();
        if ( !TextUtils.isEmpty(t)&& !TextUtils.isEmpty(cit)&& !TextUtils.isEmpty(pr)){
            String tid =myRef.push().getKey(); //get random id from firebase

            //get my id from firebase login id
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String user1 = user.getUid();//seller id
            Supplies t1= new Supplies (user1,t,pr,tid,"",cit);
            myRef.child("Supplies").child(tid).setValue(t1);
            Toast.makeText(addsupplies.this, "تمت اللإضافة", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(addsupplies.this, "يجب تعبئة كافه الحقول", Toast.LENGTH_LONG).show();

    }
}
