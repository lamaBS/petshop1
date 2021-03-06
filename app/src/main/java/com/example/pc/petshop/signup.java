package com.example.pc.petshop;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    static String city1,serv;
    String address;
    Context context;
    int PLACE_PICKER_REQUEST = 1;
    TextView location;
    EditText password, phone, email, Lname, Fname,cityo;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    Button rigister;
    DatabaseReference fdb,fdb1,fdb2;
    DatabaseReference UDB;
    RadioGroup rb;
    double x = 0, y = 0;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regesteration);

        context = this;
        mAuth = FirebaseAuth.getInstance();
        Spinner spinner=findViewById(R.id.cityspinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        password = (EditText) findViewById(R.id.pass);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        Lname = (EditText) findViewById(R.id.lastname);
        Fname = (EditText) findViewById(R.id.firatname);
      //  cityo = (EditText) findViewById(R.id.city);
         rb = (RadioGroup) findViewById(R.id.rg);

        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Spinner spinner=findViewById(R.id.ser);
                RadioButton r = (RadioButton) findViewById(checkedId);
                String tp = r.getText().toString();
                if(tp.equals("serviceprovider")){
                    ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(signup.this,R.array.service,android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(signup.this);
                    spinner.setVisibility(View.VISIBLE);
                }
                else {
                    spinner.setVisibility(View.GONE);
                }

            }
        });
        rigister = (Button) findViewById(R.id.singup);
        mProgress = new ProgressDialog(this);
        fdb = FirebaseDatabase.getInstance().getReference("seller");
        fdb1 = FirebaseDatabase.getInstance().getReference("buyer");
        fdb2 = FirebaseDatabase.getInstance().getReference("serviceprovider");
        UDB = FirebaseDatabase.getInstance().getReference("type");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(signup.this, home.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public void chickInfo(View view) {

        final String pass = password.getText().toString().trim();
        final String phoneN = phone.getText().toString().trim();
        final String emailc = email.getText().toString().trim();
        final String fname = Fname.getText().toString().trim();
        final String lname = Lname.getText().toString().trim();
       // final String cityy = cityo.getText().toString().trim();
        int id = rb.getCheckedRadioButtonId();
        RadioButton r = (RadioButton) findViewById(id);
        String tp = r.getText().toString();

       // Toast.makeText(signup.this, tp, Toast.LENGTH_SHORT).show();
        if (tp.equals("seller")) {
            if (!TextUtils.isEmpty(emailc) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname) ) {
                if (pass.matches("^(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,10}$")) {
                    if ((!fname.matches("[$@$!%*#?&1-9]*")) && (!lname.matches("[$@$!%*#?&1-9 ]*"))) {
                        mProgress.setMessage("انتظر من فضلك....");
                        mProgress.show();

                        mAuth.createUserWithEmailAndPassword(emailc, pass)  // This method is inside firebaseauth class
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() { // to tell me if the method create.. is done
                                    // onComplete will be called when create method fineshed
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        mProgress.dismiss();  //End showing msg

                                        if (task.isSuccessful()) { // If we registerd the user
///<<<<<<< Updated upstream
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            String uid = user.getUid();
                                            // String id = fdb.push().getKey();
                                            Seller s = new Seller(pass, emailc, fname, lname, Integer.parseInt(phoneN), uid,city1);
                                            fdb.child(uid).setValue(s);

                                            //to know the type of the user
                                            String type = "seller";
                                            type user1 = new type(type);
                                            UDB.child(uid).setValue(user1);
                                            //Customer customer = new Customer(pass, emailc, fname, lname, Integer.parseInt(phoneN), x, y);
                                            //fdb.setValue(customer);

                                            Toast.makeText(signup.this, "تم التسجل بنجاح!!", Toast.LENGTH_SHORT).show();
                                            // Intent intent = new Intent(GoTOCustomerRegisterPage.this, .class);
                                            // startActivity(intent);
                                            finish();
                                        } else
                                            Toast.makeText(signup.this, "البريد الالكتروني  غير صحيح او مستخدم مسبقا", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    } else {
                        Toast.makeText(signup.this, "يجب ان لا يحتوي الاسم الاول والاخير والمدينة ع ارقام او حروف خاصة", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(signup.this, "الرقم السري يجب ان يحتوي على رقم واحد على الاقل و حرف خاص واحد على الاقل وطوله ثمانية حروف ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(signup.this, "تأكد من تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
            }

        }else
        if (tp.equals("buyer")) {
            if (!TextUtils.isEmpty(emailc) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname)) {
                if (pass.matches("^(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,10}$")) {
                    if ((!fname.matches("[$@$!%*#?&1-9]*")) && (!lname.matches("[$@$!%*#?&1-9 ]*"))) {
                        mProgress.setMessage("انتظر من فضلك....");
                        mProgress.show();

                        mAuth.createUserWithEmailAndPassword(emailc, pass)  // This method is inside firebaseauth class
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() { // to tell me if the method create.. is done
                                    // onComplete will be called when create method fineshed
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        mProgress.dismiss();  //End showing msg

                                        if (task.isSuccessful()) { // If we registerd the user
///<<<<<<< Updated upstream
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            String uid = user.getUid();
                                            // String id = fdb.push().getKey();
                                            Buyer s = new Buyer(pass, emailc, fname, lname, Integer.parseInt(phoneN), uid,city1);
                                            fdb1.child(uid).setValue(s);

                                            //to know the type of the user
                                            String type = "buyer";
                                            type user1 = new type(type);
                                            UDB.child(uid).setValue(user1);
                                            //Customer customer = new Customer(pass, emailc, fname, lname, Integer.parseInt(phoneN), x, y);
                                            //fdb.setValue(customer);

                                            Toast.makeText(signup.this, "تم التسجل بنجاح!!", Toast.LENGTH_SHORT).show();
                                            // Intent intent = new Intent(GoTOCustomerRegisterPage.this, .class);
                                            // startActivity(intent);
                                            finish();
                                        } else
                                            Toast.makeText(signup.this, "البريد الالكتروني  غير صحيح او مستخدم مسبقا", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    } else {
                        Toast.makeText(signup.this, "يجب ان لا يحتوي الاسم الاول والاخير ع ارقام او حروف خاصة", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(signup.this, "الرقم السري يجب ان يحتوي على رقم واحد على الاقل و حرف خاص واحد على الاقل وطوله ثمانية حروف ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(signup.this, "تأكد من تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
            }

        }else
        if (tp.equals("serviceprovider")) {
//serviceprovider
            if (!TextUtils.isEmpty(emailc) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(fname) && !TextUtils.isEmpty(lname)) {
                if (pass.matches("^(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,10}$")) {
                    if ((!fname.matches("[$@$!%*#?&1-9]*")) && (!lname.matches("[$@$!%*#?&1-9 ]*"))) {
                        mProgress.setMessage("انتظر من فضلك....");
                        mProgress.show();

                        mAuth.createUserWithEmailAndPassword(emailc, pass)  // This method is inside firebaseauth class
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() { // to tell me if the method create.. is done
                                    // onComplete will be called when create method fineshed
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        mProgress.dismiss();  //End showing msg

                                        if (task.isSuccessful()) { // If we registerd the user
///<<<<<<< Updated upstream
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            String uid = user.getUid();
                                            // String id = fdb.push().getKey();
                                            serviceprovider s = new serviceprovider(pass, emailc, fname, lname, Integer.parseInt(phoneN), uid,city1,serv);
                                            fdb2.child(uid).setValue(s);
                                            //to know the type of the user
                                            String type = "sp";
                                            type user1 = new type(type);
                                            UDB.child(uid).setValue(user1);
                                            //Customer customer = new Customer(pass, emailc, fname, lname, Integer.parseInt(phoneN), x, y);
                                            //fdb.setValue(customer);

                                            Toast.makeText(signup.this, "تم التسجل بنجاح!!", Toast.LENGTH_SHORT).show();
                                            // Intent intent = new Intent(GoTOCustomerRegisterPage.this, .class);
                                            // startActivity(intent);
                                            finish();
                                        } else
                                            Toast.makeText(signup.this, "البريد الالكتروني  غير صحيح او مستخدم مسبقا", Toast.LENGTH_SHORT).show();

                                    }
                                });

                    } else {
                        Toast.makeText(signup.this, "يجب ان لا يحتوي الاسم الاول والاخير والمدينة ع ارقام او حروف خاصة", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(signup.this, "الرقم السري يجب ان يحتوي على رقم واحد على الاقل و حرف خاص واحد على الاقل وطوله ثمانية حروف ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(signup.this, "تأكد من تعبئة جميع الحقول", Toast.LENGTH_SHORT).show();
            }

        }


        //

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.cityspinner)
        {
            city1 =parent.getItemAtPosition(position).toString();
            // Toast.makeText(addpet.this, ""+city1+"", Toast.LENGTH_LONG).show(); //do this
        }
        else if(spinner.getId() == R.id.ser)
        {
            serv =parent.getItemAtPosition(position).toString();
            //  Toast.makeText(addpet.this, ""+ty+"", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}