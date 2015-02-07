package com.example.user.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 06/02/2015.
 */


public class TodoListAdapter extends ArrayAdapter<Task> {
    Context mContext;
    List<Task> mTaskList;
    public  TodoListAdapter(Context context, List<Task> taskList)
    {
        super(context,R.layout.list_item_todo,taskList);
        mContext = context;
        mTaskList=taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView !=null)
        {
            rowView = convertView;
        }
        else
        {  LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_todo,parent,false);
        }

        if (rowView !=null)
        {
            TextView textViewTitle = (TextView)rowView.findViewById(R.id.textTitle);
            textViewTitle.setText(mTaskList.get(position).getTitle());
            TextView textViewDescription = (TextView)rowView.findViewById(R.id.textDescription);
            textViewDescription.setText(mTaskList.get(position).getDescription());
        }
        return  rowView;


    }
}
