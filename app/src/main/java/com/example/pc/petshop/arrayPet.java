package com.example.pc.petshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
public class arrayPet extends ArrayAdapter<Pet> {
        private Activity context;
        List<Pet> artists;

        public arrayPet(Activity context, List<Pet> artists) {
            super(context, R.layout.layout_pet, artists);
            this.context = context;
            this.artists = artists; }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_pet, null, true);
          //  ImageView img = (ImageView) listViewItem.findViewById(R.id.img);
            TextView itemprice = (TextView) listViewItem.findViewById(R.id.price);
            TextView type = (TextView) listViewItem.findViewById(R.id.type);
            Pet artist = artists.get(position);

           // img.setImageURI(artist.getImg());
            itemprice.setText(""+artist.getPrice());
             type.setText(""+artist.getType());

            return listViewItem;
        }

    }

