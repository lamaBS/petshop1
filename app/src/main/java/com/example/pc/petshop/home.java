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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class home extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
   // private FirebaseAuth mAuth;
   private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference appUse=db.getReference("type");
    String type="hi";
    //Buttons  ;
    TextView rigister ;
    //EditText username ,password ;
    private FirebaseAuth mAuth;
    private TextView forgotPassword;
static String serv;
    private EditText textEmail;
    private EditText textPass;
    private Button btnLogin,btnLogin2,btnLogin3,btnLogin4;
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

        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener mAuthListener;
/*
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin2();
            }
        });
        btnLogin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin3();
            }
        });
        btnLogin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin4();
            }
        });
        */
        Spinner spinner=findViewById(R.id.userr);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.user,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, PasswordActivity.class ));
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
                              appUse.child(id).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                      type=dataSnapshot.getValue(String.class).trim();

                                        if(type.equals("seller")){
                                            Toast.makeText(home.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(home.this,welcome.class);
                                            startActivity(intent);}
                                       else{
                                           Toast.makeText(home.this, "الرجاء التأكد من نوع الدخول", Toast.LENGTH_SHORT).show();

                                    } }

                                   @Override
                                   public void onCancelled(DatabaseError databaseError) {
///
                                    }
                                });

                         } // Singed in successfull
                            if (!task.isSuccessful()) {
                                Toast.makeText(home.this, "خطأ في ادخال البريد الالكتروني أو كلمة المرور", Toast.LENGTH_LONG).show();
                            }



                        } // On Complete
                    }); // OnComplete listener

        } // Felids not empty

    } // Do login
    private void doLogin2() {

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
                                appUse.child(id).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        type=dataSnapshot.getValue(String.class).trim();

                                        if(type.equals("buyer")){
                                            Toast.makeText(home.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(home.this,buysupplies.class);
                                            startActivity(intent);}
                                        else{
                                            Toast.makeText(home.this, "الرجاء التأكد من نوع الدخول", Toast.LENGTH_SHORT).show();

                                        } }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
///
                                    }
                                });

                            } // Singed in successfull
                            if (!task.isSuccessful()) {
                                Toast.makeText(home.this, "خطأ في ادخال البريد الالكتروني أو كلمة المرور", Toast.LENGTH_LONG).show();
                            }



                        } // On Complete
                    }); // OnComplete listener

        } // Felids not empty

    } // Do login
    private void doLogin3() {

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
                                appUse.child(id).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        type=dataSnapshot.getValue(String.class).trim();

                                        if(type.equals("admin")){
                                            Toast.makeText(home.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(home.this,adminhomepage.class);
                                            startActivity(intent);}
                                        else{
                                            Toast.makeText(home.this, "الرجاء التأكد من نوع الدخول", Toast.LENGTH_SHORT).show();

                                        } }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
///
                                    }
                                });

                            } // Singed in successfull
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
    private void doLogin4() {
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
                                appUse.child(id).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        type=dataSnapshot.getValue(String.class).trim();

                                        if(type.equals("sp")){
                                            Toast.makeText(home.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(home.this,sphome.class);
                                            startActivity(intent);}
                                        else{
                                            Toast.makeText(home.this, "الرجاء التأكد من نوع الدخول", Toast.LENGTH_SHORT).show();

                                        } }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
///
                                    }
                                });

                            } // Singed in successfull
                            if (!task.isSuccessful()) {
                                Toast.makeText(home.this, "خطأ في ادخال البريد الالكتروني أو كلمة المرور", Toast.LENGTH_LONG).show();
                            }



                        } // On Complete
                    }); // OnComplete listener

        } // Felids not empty

    }

    public void LOG(View view) {

        if(serv.equals("بائع"))
            doLogin();
            else
        if(serv.equals("مشتري"))
            doLogin2();
                else
        if(serv.equals("مدير"))
            doLogin3();
         else
        if(serv.equals("مزود خدمة"))
            doLogin4();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        serv =parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
