package com.example.pc.petshop;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.*;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.List;
public class viewPets extends AppCompatActivity {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        String id = "";
        String cid = "";
        String mid = "";
        private FirebaseAuth mAuth;
        ListView listViewArtists;
        List<Pet> artists;
        FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.viewpet);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            listViewArtists = (ListView) findViewById(R.id.listViewTracks);
            //list to store artists
            artists = new ArrayList<>();

            listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
               //     Pet artist = artists.get(position);
                    //   String
              //      AlertDialog diaBox = AskOption(artist.getId());
               //     diaBox.show();
                }
            });
            listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Pet artist = artists.get(i);
                    showUpdateDeleteDialog(artist.getId(),artist.getAge(),artist.getCity(),artist.getPrice(),artist.getType(),artist.getColor(),artist.getImg());
                    return true;
                }
            });
        }

        private void showUpdateDeleteDialog(final String id,final String age,final String city ,final String price, final String type,final String color,final String imgg) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.updatepet, null);
            dialogBuilder.setView(dialogView);

            final EditText editTextName = (EditText) dialogView.findViewById(R.id.type);
         //   final EditText editTextdes = (EditText) dialogView.findViewById(R.id.city);
            final EditText editTextprice = (EditText) dialogView.findViewById(R.id.price);
            final EditText editTextage = (EditText) dialogView.findViewById(R.id.age);
            final EditText editTextcity=(EditText)  dialogView.findViewById(R.id.city);
            final EditText editTextcolor=(EditText)  dialogView.findViewById(R.id.color);
            //
            editTextName.setText(type);
          //  editTextdes.setText(city);
            editTextprice.setText(price);
            editTextage.setText(age);
            editTextcity.setText(city);
            editTextcolor.setText(color);
            //

            final Button buttonUpdate = (Button) dialogView.findViewById(R.id.addbutton);
            final Button buttndelete = (Button) dialogView.findViewById(R.id.delete);
            //
            dialogBuilder.setTitle("تعديل معلومات الحيوان");
            final AlertDialog b = dialogBuilder.create();
            b.show();

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = editTextName.getText().toString().trim();
                  //  String des = editTextdes.getText().toString().trim();
                    String price = editTextprice.getText().toString().trim();
                    String agee= editTextage.getText().toString().trim();
                    String city= editTextcity.getText().toString().trim();
                    String color= editTextcolor.getText().toString().trim();

                    if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(price) && !TextUtils.isEmpty(agee) && !TextUtils.isEmpty(city)&& !TextUtils.isEmpty(color)) {
                        updateItem(color,id,name,price,city,agee,imgg);
                        b.dismiss();
                    }
                    else{
                        b.dismiss();
                        Toast.makeText(getApplicationContext(), "الرجاء تعبئة جميع الخانات ", Toast.LENGTH_LONG).show();

                    }
                }
            });
            buttndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog diaBox = AskOption(id);
                    b.dismiss();
                    diaBox.show();
                }

            });
        }

        private void updateItem(final String colo,final String tid,final String typ,final String price,final String city,final String agee,final String imgg) {

            final double pr;
            try{
                pr = Double.parseDouble(price);}
            catch (Exception e){
                Toast.makeText(this, "الرجاء ادخال السعر بشكل صحيح ", Toast.LENGTH_LONG).show();
                return;
            }
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            String id = user.getUid();//customer id is the same as rating id to make it easy to refer
                    Pet t=new Pet();
                    t.setAge(agee);
                    t.setType(typ);
                    t.setImg("here url");
                    t.setPrice(pr+"");
                    t.setCity(city);
                    t.setId(tid);
                    t.setImg(imgg);
                    t.setOwnerid(id);
                    t.setColor(colo);
                    myRef.child("Pet").child(tid).setValue(t); }

        public void addpet(View view) {
            Intent intent = new Intent(this, addpet.class);
            startActivity(intent);
        }

        @Override
        protected void onStart() {
            super.onStart();
            myRef = FirebaseDatabase.getInstance().getReference();
            // databaseReferences.orderByChild("uid").equalTo(user1);
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer
            myRef.child("Pet").orderByChild("ownerid").equalTo(user1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    artists.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Pet artist =new Pet(ds.getValue(Pet.class));
                        artists.add(artist);
                    }


                    //creating adapter
                    int posts= artists.size();
                    arrayPet artistAdapter = new arrayPet(viewPets.this, artists);
                    //attaching adapter to the listview
                    listViewArtists.setAdapter(artistAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        private AlertDialog AskOption(final String item) {
            AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setTitle("حذف حيوان")
                    .setMessage("هل تريد حذف الحيوان")
                    .setIcon(R.drawable.delete)

                    .setPositiveButton("حذف", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            //your deleting code
                            deleteitem(item);
                            dialog.dismiss();
                        }

                    })


                    .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    })
                    .create();
            return myQuittingDialogBox;

        }

        private void deleteitem(String item) {
            //getting the specified artist reference
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Pet").child(item);
            //removing artist
            dR.removeValue();
            Toast.makeText(this,"تم الحـــذف",Toast.LENGTH_LONG).show();
        }



}
    //start

