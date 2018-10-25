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
public class view_all_suggestion extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String id = "";
    String cid = "";
    String mid = "";
    private FirebaseAuth mAuth;
    ListView listViewArtists;
    List<suggestion> artists;
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewsuggestion);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            listViewArtists = (ListView) findViewById(R.id.listViewTracks);
            //list to store artists
            artists = new ArrayList<>();

            listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                    suggestion s= artists.get(position);
                    AlertDialog diaBox = AskOption(s.getID());
                    diaBox.show();
                }
            });
            listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    suggestion artist = artists.get(i);
                   // showUpdateDeleteDialog(artist.getSuggest());
                    return true;
                }
            });
        }





        @Override
        protected void onStart() {
            super.onStart();
            myRef = FirebaseDatabase.getInstance().getReference();
            // databaseReferences.orderByChild("uid").equalTo(user1);
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String user1 = user.getUid();//customer id is the same as rating id to make it easy to refer
            myRef.child("Sugesstion").orderByChild("id").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    artists.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        suggestion artist =new suggestion(ds.getValue(suggestion.class));
                        artists.add(artist);
                    }


                    //creating adapter
                    int posts= artists.size();
                    arraySugesstion artistAdapter = new arraySugesstion(view_all_suggestion.this, artists);
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
                    .setTitle("حذف الاقتراح")
                    .setMessage("هل تريد حذف الاقتراح")
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
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Suggestion").child(item);
            //removing artist
            dR.removeValue();
            Toast.makeText(this,"تم الحـــذف",Toast.LENGTH_LONG).show();
        }



    }


