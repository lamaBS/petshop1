package com.example.pc.petshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {
   // private FirebaseAuth mAuth;
   private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference appUse=db.getReference("type");
    String type="hi";
    //Buttons  ;
    TextView rigister ;
    //EditText username ,password ;
    private FirebaseAuth mAuth;
    private EditText textEmail;
    private EditText textPass;
    private Button btnLogin;
    DatabaseReference UDB;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView mTextReg ;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initAuthStateListener();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rigister = (TextView) findViewById(R.id.CustomerSignup);
        progressDialog = new ProgressDialog(this);
        textEmail = (EditText) findViewById(R.id.CustomerEmail);
        textPass = (EditText) findViewById(R.id.CustomerPassword);
        btnLogin = (Button) findViewById(R.id.CustomerBtnLogin);
        mAuth = FirebaseAuth.getInstance();
        UDB=FirebaseDatabase.getInstance().getReference("test");
        UDB.child("1").setValue("hello");
        FirebaseAuth.AuthStateListener mAuthListener;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });


    }
    private void initAuthStateListener (){


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }



    // These two methods save user's login


    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }





    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void doLogin() {

        final String email = textEmail.getText().toString().trim();
        password = textPass.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "فضلًا ادخل البريد الالكتروني", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "فضلًا ادخل كلمة المرور", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            progressDialog.setMessage("انتظر من فصلك, جاري تسجيل الدخول..");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {

                                String id=mAuth.getCurrentUser().getUid();
                            //    appUse.child(id).child("tp").addListenerForSingleValueEvent(new ValueEventListener() {
                             //       @Override
                               //     public void onDataChange(DataSnapshot dataSnapshot) {
                                 //       type=dataSnapshot.getValue(String.class).trim();

                                  //      if(type.equals("seller")){
                                            Toast.makeText(home.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(home.this,welcome.class);
                                            startActivity(intent);}
                                  //      else{
                                  //          Toast.makeText(home.this, "الرجاء التأكد من نوع الدخول", Toast.LENGTH_SHORT).show();

                                 //       }
                                //    }

                                //    @Override
                             //       public void onCancelled(DatabaseError databaseError) {
///
                               //     }
                             //   });




                        //    } // Singed in successfull
                            if (!task.isSuccessful()) {
                                Toast.makeText(home.this, "خطأ في ادخال البريد الالكتروني أو كلمة المرور", Toast.LENGTH_LONG).show();
                            }



                        } // On Complete
                    }); // OnComplete listener

        } // Felids not empty

    } // Do login

    public void regester(View view) {
        Intent intent = new Intent(home.this,signup.class);
        startActivity(intent);
    }
}
