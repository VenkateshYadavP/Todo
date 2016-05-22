package com.example.venkatesh.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.venkatesh.project1.R;
import com.example.venkatesh.project1.model.Task;

import java.util.ArrayList;

/**
 * Created by venkatesh on 5/22/16.
 */
public class TaskAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Task> mTasks;
    private LayoutInflater mLayoutInflater;
    public TaskAdapter(Context context, int resource,ArrayList<Task> mTasks) {
        super(context, resource);
        this.mTasks = mTasks;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return mTasks.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {

            convertView = mLayoutInflater.inflate(R.layout.item_task,parent,false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView cellTitle = (TextView) convertView.findViewById(R.id.celltitle);
        Task mtask = (Task) getItem(position);
        title.setText(mtask.title);
        description.setText(mtask.description);
        date.setText(mtask.date);
        cellTitle.setText(mtask.date);
        return convertView;
    }
    public void refreshTasks(ArrayList<Task> tasks) {
        this.mTasks.clear();
        this.mTasks.addAll(tasks);
        notifyDataSetChanged();
    }
}
