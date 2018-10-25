package com.example.pc.petshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

public class postActivityPublic extends AppCompatActivity {



    //constant to track image chooser intent
    private static final int IMAGE_REQUEST = 2;

    //view objects
    private ImageButton imageButton;
    private Button buttonUpload;
    private EditText editTextName;
    private EditText editTextDesc;

    //uri to store file
    private Uri uri;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseDatabase database;
    FirebaseAuth mAuth;
    private DatabaseReference databaseOWner;
    private FirebaseUser CurrentOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_post);


        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        editTextName = (EditText) findViewById(R.id.editName);
        editTextDesc=(EditText) findViewById(R.id.editDesc);


        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = database.getInstance().getReference().child("postsTest6");
        mAuth= FirebaseAuth.getInstance();
        CurrentOwner=mAuth.getCurrentUser();

        databaseOWner= FirebaseDatabase.getInstance().getReference().child("seller").child(CurrentOwner.getUid());

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
            imageButton = (ImageButton) findViewById(R.id.imageButton);
            imageButton.setImageURI(uri);
            /*try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }

    public void submitButtonClicked(View view){
        final String imageName=editTextName.getText().toString().trim();
        final String imageDesc=editTextDesc.getText().toString().trim();
        if(!TextUtils.isEmpty(imageName) && !TextUtils.isEmpty(imageDesc)){
            StorageReference filepath= storageReference.child("Posts").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Task<Uri> downloadurl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                //    final Uri downloadurl=taskSnapshot.getDownloadUrl();
                    Toast.makeText(postActivityPublic.this, "Upload completed", Toast.LENGTH_LONG).show();
                   final DatabaseReference newPost= databaseReference.push();
            databaseOWner.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Date currentDate= Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String day=simpleDateFormat.format(currentDate);
                    String dateTime=currentDate.toString();
                    newPost.child("date").setValue(day);
                    newPost.child("name").setValue(imageName);
                    newPost.child("desc").setValue(imageDesc);
                    newPost.child("image").setValue(downloadurl.toString());
                    newPost.child("uid").setValue(CurrentOwner.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("cfirstName").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent mIntent = new Intent(postActivityPublic.this, welcome.class);
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
        }
    }

}
