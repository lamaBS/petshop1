package com.example.pc.petshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class orderadapter extends ArrayAdapter<order> {
    private Activity context;
    List<order> artists;

    public orderadapter(Activity context, List<order> artists) {
        super(context, R.layout.order_card, artists);
        this.context = context;
        this.artists = artists; }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.order_card, null, true);
        //ImageView img = (ImageView) listViewItem.findViewById(R.id.img);
        TextView itemprice = (TextView) listViewItem.findViewById(R.id.price);
        TextView type = (TextView) listViewItem.findViewById(R.id.type);
        TextView q = (TextView) listViewItem.findViewById(R.id.quatity);
        order artist = artists.get(position);

        // img.setImageURI(artist.getImg());
        itemprice.setText(""+artist.getPrice());
        type.setText(""+artist.getType());
        q.setText(""+artist.getQuantity());
        return listViewItem;
    }

}