package com.example.testapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventListAdoptor extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int resourceID;



    public EventListAdoptor(@NonNull Context context, int resource, @NonNull List<Event> items) {
        super(context, resource, items);
        this.resourceID = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = inflater.inflate(resourceID,parent,false);

        TextView eventName = rowView.findViewById(R.id.tvEventName);
        TextView dateTime = rowView.findViewById(R.id.tvDateName);
        TextView PlaceName = rowView.findViewById(R.id.EventPlaceName);

        Event e = (Event) this.getItem(position);

        eventName.setText(e.title);
        PlaceName.setText(e.place);
        dateTime.setText(e.dateTime);

        return rowView;
    }
}
