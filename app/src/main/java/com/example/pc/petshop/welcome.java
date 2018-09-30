package com.example.pc.petshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class welcome  extends AppCompatActivity {

    DatabaseReference UDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
//Button b=(Button) findViewById(R.id.aa);
      //  UDB=FirebaseDatabase.getInstance().getReference("test");
       // UDB.child("1").setValue("hello");

    }

public void showtext(){
   // Intent intent = new Intent(welcome.this, signup.class);
     //startActivity(intent);


}



}
