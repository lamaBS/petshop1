package com.example.pc.petshop;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
public class buyerhome extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    static String city1,serv;
    DatabaseReference databaseref;
    DatabaseReference wh;
    EditText fname;
    EditText lname;
    DatabaseReference databaseann;
    EditText phone,c;
    int phonum=0;
    static String email1="";
    EditText passwor;
    private final String CHANNEL_ID ="Notification";
    private final int NOTIFICATION_ID= 001;
    FirebaseAuth firebaseAuth;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        context = this;
        Intent intent;

        databaseann = FirebaseDatabase.getInstance().getReference("announcement");
        databaseref = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//easy to refer


        final Spinner spinner=findViewById(R.id.cityspinner);
        final ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        // Read from the database
        databaseref.child("buyer").child(id).addValueEventListener(new ValueEventListener() {
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

                // mail = (EditText) findViewById(R.id.emailP);
                //     mail.setText(""+email1);

                phone = (EditText) findViewById(R.id.phoneP);
                phone.setText("" + phonum);
                // c = (EditText) findViewById(R.id.cityP);
                //  c.setText("" + city);
                spinner.setSelection(adapter.getPosition(city));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(buyerhome.this, "الرجاء التحقق من الاتصال بالانترنت", Toast.LENGTH_LONG).show();
            }
        });

        // Read from the database
        //end

        databaseann.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot artistSnapshot :dataSnapshot.getChildren() ){

                    announcement save1=artistSnapshot.getValue(announcement.class);

                    if(user.getEmail().equals(save1.getName())){

                        notification(save1.getAnn());
                        databaseann= FirebaseDatabase.getInstance().getReference("announcement").child(save1.getId());
                        //save the value to the

                        databaseann.removeValue();

                    }

                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext()
                        ,"Check your Internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void chickInfo(View view) {
        //final String pass = passwor.getText().toString().trim();
        final String phoneN = phone.getText().toString().trim();
        //final String emailpp = mail.getText().toString().trim();
        final String fnameP = fname.getText().toString().trim();
        final String lnameP = lname.getText().toString().trim();
        //   final String cc =c.getText().toString().trim();
        // final double x = 12.321;
        //  final double y = 13.1;
        FirebaseUser seller = FirebaseAuth.getInstance().getCurrentUser();
        String idSeller = seller.getUid();//customer id is the same as rating id to make it easy to refer
        if (!TextUtils.isEmpty(phoneN) && !TextUtils.isEmpty(fnameP)&& !TextUtils.isEmpty(lnameP)) {
            DatabaseReference owner = FirebaseDatabase.getInstance().getReference("seller").child(idSeller);

            //  PublicFoodTruckOwner owner = new PublicFoodTruckOwner();
            owner.child("cfirstName").setValue(fnameP);
            owner.child("clastName").setValue(lnameP);
            owner.child("cponeNoumber").setValue(Integer.parseInt(phoneN));
            owner.child("cemail").setValue(email1);
            owner.child("city").setValue(city1);

            Toast.makeText(buyerhome.this, "تم حفظ التغييرات", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(buyerhome.this, buysupplies.class);
            startActivity(intent);
        } else
            Toast.makeText(buyerhome.this, "الرجاء تعبئة كافة الحقول", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.cityspinner)
        {
            city1 =parent.getItemAtPosition(position).toString();
            // Toast.makeText(addpet.this, ""+city1+"", Toast.LENGTH_LONG).show(); //do this
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void notification(String notif){

        createNotificationChannel();

        //onclick the notification open the activity
        Intent intent = new Intent(getApplicationContext()
                ,welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_ONE_SHOT);
        //

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_audiotrack_light);
        builder.setContentTitle("إشعار جديد");
        builder.setContentText(notif);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);//auto delete the notification when click it
        builder.setContentIntent(pendingIntent);//onclick the notification open the activity

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }


    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            CharSequence name = "Personal Notification";
            String description = "Include all the personal notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

}