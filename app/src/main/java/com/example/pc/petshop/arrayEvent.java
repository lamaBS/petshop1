package com.example.pc.petshop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class arrayEvent extends ArrayAdapter<event>{
    private Activity context;
    List<event> artists;

    public arrayEvent(Context context, int resource, List<event> objects) {
        super(context, resource, objects);
    }

    public arrayEvent(Activity context, List<event> artists) {
        super(context, R.layout.event_layout, artists);
        this.context = context;
        this.artists = artists; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.event_layout, null, true);
        TextView adrss = (TextView) listViewItem.findViewById(R.id.adress);
        TextView name = (TextView) listViewItem.findViewById(R.id.name);
        TextView des = (TextView) listViewItem.findViewById(R.id.descrp);
        event artist = artists.get(position);

      // des.setText(artist.getDescription());
        adrss.setText(""+artist.getAdress());
        name.setText(""+artist.getEventName());

        return listViewItem;
    }
}





//arrayEvent