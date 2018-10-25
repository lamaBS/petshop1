package com.example.pc.petshop;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sendSuggestion extends AppCompatActivity {
    DatabaseReference UDB;
    DatabaseReference myRef;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestionseller);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Button b=findViewById(R.id.btnSugesstion);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText color=(EditText) findViewById(R.id.sugestionID);
                String c=color.getText().toString().trim();
                if (!TextUtils.isEmpty(c)){
                    String tid =myRef.push().getKey(); //get random id from firebase
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer
                    suggestion t1= new suggestion (c,user1);
                    myRef.child("Sugesstion").child(tid).setValue(t1);
                    Toast.makeText(sendSuggestion.this, "تم الإرسال ", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(sendSuggestion.this, "يجب تعبئة الحقل", Toast.LENGTH_LONG).show();

            }

        });
    }

}
