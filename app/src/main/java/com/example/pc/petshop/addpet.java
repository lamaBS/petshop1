package com.example.pc.petshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addpet  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
     static String city1;
     static String ty;
    DatabaseReference UDB;
    DatabaseReference myRef;
    //constant to track image chooser intent
    private static final int IMAGE_REQUEST = 2;

    //view objects
    private ImageButton imageButton;
    private ImageButton buttonUpload;
    private EditText editTextName;
    private EditText editTextDesc;
    private Uri uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    FirebaseAuth mAuth;
    private DatabaseReference databaseOWner;
    private FirebaseUser CurrentOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpet);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Spinner spinner=findViewById(R.id.cityspinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.city,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner tp=findViewById(R.id.tp);
        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this,R.array.animaltype,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tp.setAdapter(adapter2);
        tp.setOnItemSelectedListener(this);

        myRef = database.getReference();


    }


    public void addpet(View view) {
        EditText color=(EditText) findViewById(R.id.color);
        EditText type=(EditText) findViewById(R.id.type);
        EditText price=(EditText) findViewById(R.id.price);
        EditText age=(EditText) findViewById(R.id.age);

      final  String c=color.getText().toString().trim();
    //
        buttonUpload = (ImageButton) findViewById(R.id.Pimage);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = database.getInstance().getReference().child("Pet");
        mAuth= FirebaseAuth.getInstance();
        CurrentOwner=mAuth.getCurrentUser();

        databaseOWner= FirebaseDatabase.getInstance().getReference().child("seller").child(CurrentOwner.getUid());


        //
       final String pr=price.getText().toString().trim();
       final String g=age.getText().toString().trim();

        if ( !TextUtils.isEmpty(c) && !TextUtils.isEmpty(pr)&& !TextUtils.isEmpty(g)){
            StorageReference filepath= storageReference.child("pet").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   final Task<Uri> downloadurl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    //    final Uri downloadurl=taskSnapshot.getDownloadUrl();
                  //  final Uri downloadurl=taskSnapshot.getDownloadUrl();
                    Toast.makeText(addpet.this, "Upload completed", Toast.LENGTH_LONG).show();
                    final DatabaseReference newPost= databaseReference.push();
                  final  String tid =myRef.push().getKey();
                    databaseOWner.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //
                            Pet t1= new Pet (CurrentOwner.getUid(),g,ty,pr,tid,downloadurl.toString(),city1,c);
                            myRef.child("Pet").child(tid).setValue(t1);
                            //
                            Toast.makeText(addpet.this, "تم اضافة الحيوان", Toast.LENGTH_LONG).show();
                            myRef.child("Pet").child(tid).child("username").setValue(dataSnapshot.child("cfirstName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent mIntent = new Intent(addpet.this, viewPets.class);
                                        startActivity(mIntent);
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
             }
        });


    }    else
            Toast.makeText(addpet.this, "يجب تعبئة كافه الحقول", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.cityspinner)
        {
            city1 =parent.getItemAtPosition(position).toString();
           // Toast.makeText(addpet.this, ""+city1+"", Toast.LENGTH_LONG).show(); //do this
        }
        else if(spinner.getId() == R.id.tp)
        {
            ty =parent.getItemAtPosition(position).toString();
          //  Toast.makeText(addpet.this, ""+ty+"", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void imageButtonClicked(View view){
        Intent gallaryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        gallaryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(gallaryIntent, "Select Picture"), IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
          //  imageButton = (ImageButton) findViewById(R.id.Pimage);
          //  imageButton.setImageURI(uri);
            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}