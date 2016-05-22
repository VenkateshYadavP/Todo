package com.example.venkatesh.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.venkatesh.project1.Adapter.TaskAdapter;
import com.example.venkatesh.project1.Helper.DBHelper;
import com.example.venkatesh.project1.model.Task;

import java.util.ArrayList;

/**
 * Created by venkatesh on 5/22/16.
 */
public class completedActivity extends AppCompatActivity {
    ListView list;
    DBHelper mDBHelper;
    ArrayList<Task> mTasks;
    TaskAdapter mTaskAdapetr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_activity);
        list = (ListView) findViewById(R.id.listView2);
        mDBHelper = new DBHelper(getApplicationContext());
        mTasks = mDBHelper.fetchCompletedTasks();
        mTaskAdapetr = new TaskAdapter(getApplicationContext(), R.layout.item_task, mTasks);
        list.setAdapter(mTaskAdapetr);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int positionData = position;
                AlertDialog.Builder dialog = new AlertDialog.Builder(completedActivity.this);
                dialog.setTitle("Do you want to delete it ?");
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(completedActivity.this, "Task deleted", Toast.LENGTH_LONG).show();
                        mDBHelper.deleteTask(mTasks.get(positionData).id);
                        mTasks = mDBHelper.fetchCompletedTasks();
                        mTaskAdapetr.refreshTasks(mTasks);
                        list.invalidateViews();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }
}
