package com.example.pc.petshop;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.List;

public class arrayHotel extends ArrayAdapter<Hotel> {
    private Activity context;
    List<Hotel> hotels;

    public arrayHotel(Activity context, List<Hotel> hotels) {
        super(context, R.layout.layout_hotel, hotels);
        this.context = context;
        this.hotels = hotels; }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_hotel, null, true);

        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView address = (TextView) listViewItem.findViewById(R.id.address);
        TextView price = (TextView) listViewItem.findViewById(R.id.price);
        TextView details = (TextView) listViewItem.findViewById(R.id.details);
        Hotel h = hotels.get(position);

        // img.setImageURI(artist.getImg());
        name.setText(""+h.getName());
        address.setText(""+h.getAddress());
        price.setText(""+h.getPrice());
        details.setText(""+h.getDetails());



        return listViewItem;
    }



}
