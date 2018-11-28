package com.example.pc.petshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class viewsupllies extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String id = "";
    String cid = "";
    String mid = "";
    private FirebaseAuth mAuth;
    ListView listViewArtists;
    List<Supplies> artists;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewsupplies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewArtists = (ListView) findViewById(R.id.listViewTracks);
        //list to store artists
        artists = new ArrayList<>();

        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //     Pet artist = artists.get(position);
                //   String
                //      AlertDialog diaBox = AskOption(artist.getId());
                //     diaBox.show();
            }
        });
        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Supplies artist = artists.get(i);
                showUpdateDeleteDialog(artist.getId(),artist.getCity(),artist.getPrice(),artist.getType(),artist.getImg());
                return true;
            }
        });
    }

    private void showUpdateDeleteDialog(final String id,final String city ,final String price, final String type, final String imgg) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.editsupplies, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.type);
        final EditText editTextprice = (EditText) dialogView.findViewById(R.id.price);
        final EditText editTextcity=(EditText)  dialogView.findViewById(R.id.city);

        //
        editTextName.setText(type);
        editTextprice.setText(price);
        editTextcity.setText(city);
        //
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.addbutton);
        final Button buttndelete = (Button) dialogView.findViewById(R.id.delete);
        //
        dialogBuilder.setTitle("تعديل معلومات المستلزم");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = editTextName.getText().toString().trim();
                String price = editTextprice.getText().toString().trim();
                String city= editTextcity.getText().toString().trim();

                if (!TextUtils.isEmpty(t)&& !TextUtils.isEmpty(price) && !TextUtils.isEmpty(city)) {
                    updateItem(id,t,price,city,imgg);
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

    private void updateItem(final String tid,final String tt,final String price,final String city, final String imgg) {

        final double pr;
        try{
            pr = Double.parseDouble(price);}
        catch (Exception e){
            Toast.makeText(this, "الرجاء ادخال السعر بشكل صحيح ", Toast.LENGTH_LONG).show();
            return;
        }
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();//customer id is the same as rating id to make it easy to refer
        Supplies t=new Supplies();
        t.setType(tt);
        t.setImg(imgg);
        t.setPrice(pr+"");
        t.setCity(city);
        t.setId(tid);
        t.setOwnerid(id);
        myRef.child("Supplies").child(tid).setValue(t); }


    @Override
    protected void onStart() {
        super.onStart();
        myRef = FirebaseDatabase.getInstance().getReference();
        // databaseReferences.orderByChild("uid").equalTo(user1);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer
        myRef.child("Supplies").orderByChild("ownerid").equalTo(user1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artists.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Supplies artist =new Supplies(ds.getValue(Supplies.class));
                    artists.add(artist);
                }


                //creating adapter
                int posts= artists.size();
                arraysupplies artistAdapter = new arraysupplies(viewsupllies.this, artists);
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
                .setTitle("حذف مستلزم")
                .setMessage("هل تريد حذف المستلزم")
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
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Supplies").child(item);
        //removing artist
        dR.removeValue();
        Toast.makeText(this,"تم الحـــذف",Toast.LENGTH_LONG).show();
    }


    public void addsupplies(View view) {
        Intent intent = new Intent(this, addsupplies.class);
        startActivity(intent);
    }
}
//start

