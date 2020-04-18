package com.example.to_do_list_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ThreeColumn_ListAdapter1 extends ArrayAdapter<Todo> {
    private LayoutInflater mInflater;
    private ArrayList<Todo> todolist;
    private int mViewresourceId;
    public ThreeColumn_ListAdapter1(Context context, int textViewResourceId, ArrayList<Todo> todolist) {
        super(context, textViewResourceId, todolist);
        this.todolist = todolist;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewresourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflater.inflate(mViewresourceId,null);
        Todo student = todolist.get(position);

        if(student != null){
            TextView subid = (TextView) convertView.findViewById(R.id.texts1);
            TextView subname = (TextView) convertView.findViewById(R.id.texts2);

            if(subid != null){
                subid.setText(student.getId());
            }
            if(subname != null){
                subname.setText(student.getDescription());
            }

        }
        return convertView;
    }
}
