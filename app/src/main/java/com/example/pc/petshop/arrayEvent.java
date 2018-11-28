package com.example.pc.petshop;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;


import java.util.List;

public class arrayEvent extends ArrayAdapter<event>{
    private Activity context;
    List<event> artists;

    public arrayEvent(Activity context, List<event> artists) {
        super(context, R.layout.event_layout, artists);

        this.context = context;
        this.artists = artists; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.event_layout, null, true);
        TextView eventInfo = (TextView) listViewItem.findViewById(R.id.eventName);
        event artist = artists.get(position);
        eventInfo.setText(""+artist.getEventName());
        TextView eventInfo1 = (TextView) listViewItem.findViewById(R.id.eventAdress);
        eventInfo1.setText(""+artist.getAdress());


        return listViewItem;
    }

}






//arrayEvent   event_layout