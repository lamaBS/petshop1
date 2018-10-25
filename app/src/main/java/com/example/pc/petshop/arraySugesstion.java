package com.example.pc.petshop;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;


import java.util.List;

public class arraySugesstion extends ArrayAdapter<suggestion>{
    private Activity context;
    List<suggestion> artists;

    public arraySugesstion(Activity context, List<suggestion> artists) {
        super(context, R.layout.layout_suggestion, artists);
        this.context = context;
        this.artists = artists; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_suggestion, null, true);
        TextView sugesstionText = (TextView) listViewItem.findViewById(R.id.TextID);
        suggestion artist = artists.get(position);
        sugesstionText.setText(""+artist.getSuggest());

        return listViewItem;
    }

}


