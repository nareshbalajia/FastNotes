package com.naresh.fastnotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class ApplicationAdaptor extends ArrayAdapter<String> {
    private List<String> notesList = null;
    private Context context;

    public ApplicationAdaptor(Context context, int textViewResourceId,
                              List<String> notesList) {
        super(context, textViewResourceId, notesList);
        this.context = context;
        this.notesList =notesList ;

    }

    @Override
    public int getCount() {
        return ((null != notesList) ? notesList.size() : 0);
    }

    @Override
    public String getItem(int position) {
        return ((null != notesList) ? notesList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.notes_list_adaptor_layout, null);
        }
        String noteList=notesList.get(position);
        if(null!= notesList)
        {
            TextView appName=(TextView) view.findViewById(R.id.noteTitle);
            appName.setText(noteList);

        }
        return  view;
    }

}
