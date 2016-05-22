package com.example.venkatesh.project1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.venkatesh.project1.Adapter.TaskAdapter;
import com.example.venkatesh.project1.Helper.DBHelper;
import com.example.venkatesh.project1.model.Task;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ListView list;
    DBHelper mDBHelper;
    ArrayList<Task> mTasks;
    TaskAdapter mTaskAdapetr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listView);
        mDBHelper = new DBHelper(getApplicationContext());
//        mDBHelper.drop();
//        insertData();
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int positionData = position;
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Do you want to mark it as completed ?");
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDBHelper.updateStatus(mTasks.get(positionData).id);
                        refreshList();
                        Toast.makeText(MainActivity.this, "Task Marked as completed", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.show();
                return true;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Task taskData = mTasks.get(position);
                final AlertDialog.Builder dialog= new AlertDialog.Builder(MainActivity.this);
                LayoutInflater mLayoutInflater = LayoutInflater.from(MainActivity.this);
                View dialogView = mLayoutInflater.inflate(R.layout.task_activity, null, false);
                dialog.setTitle("ADD TASK");
                final EditText title = (EditText) dialogView.findViewById(R.id.title);
                final EditText description = (EditText) dialogView.findViewById(R.id.description);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
                Button save = (Button) dialogView.findViewById(R.id.save);
                Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                dialog.setView(dialogView);
                final AlertDialog alertDialog = dialog.create();
                alertDialog.setCancelable(false);
                title.setText(taskData.title);
                description.setText(taskData.description);
                String[] dateValues = taskData.date.split("/");
                datePicker.updateDate(Integer.parseInt(dateValues[2]), Integer.parseInt(dateValues[1]) - 1, Integer.parseInt(dateValues[0]));
                //datePicker.updateDate(2015,11,11);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String titleText = title.getText().toString();
                        String descriptionText = description.getText().toString();
                        String dateText = datePicker.getMonth() +"/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear();
                        if(titleText.length() < 6)
                        {
                            Toast.makeText(MainActivity.this,"Title length is very small",Toast.LENGTH_LONG).show();
                        }
                        else if(descriptionText.length() < 15)
                        {
                            Toast.makeText(MainActivity.this,"description length is very small",Toast.LENGTH_LONG).show();
                        }
                        else {
                            mDBHelper.updateTask(taskData.id,titleText,descriptionText,dateText);
                            refreshList();
                            alertDialog.dismiss();
                            Toast.makeText(MainActivity.this,"Task updated",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTasks = mDBHelper.fetchIncompleteTasks();
        mTaskAdapetr = new TaskAdapter(getApplicationContext(),R.layout.item_task,mTasks);
        list.setAdapter(mTaskAdapetr);
    }
    void refreshList()
    {

        mTasks = mDBHelper.fetchIncompleteTasks();
        mTaskAdapetr.refreshTasks(mTasks);
    }
    private void insertData() {
        mDBHelper.insertTask(new Task(0,"Bill","Pay Bill","19/11/2014",0));
        mDBHelper.insertTask(new Task(0,"Bill","Pay TV Bill","19/11/2015",0));
        mDBHelper.insertTask(new Task(0,"TAX","fill TAX","19/12/2014",1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.add:
                final AlertDialog.Builder dialog= new AlertDialog.Builder(this);
                LayoutInflater mLayoutInflater = LayoutInflater.from(this);
                View dialogView = mLayoutInflater.inflate(R.layout.task_activity, null, false);
                dialog.setTitle("ADD TASK");
                final EditText title = (EditText) dialogView.findViewById(R.id.title);
                final EditText description = (EditText) dialogView.findViewById(R.id.description);
                final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datePicker);
                Button save = (Button) dialogView.findViewById(R.id.save);
                Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                dialog.setView(dialogView);
                final AlertDialog alertDialog = dialog.create();
                alertDialog.setCancelable(false);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String titleText = title.getText().toString();
                        String descriptionText = description.getText().toString();
                        Integer month =  datePicker.getMonth() + 1;
                        String dateText = datePicker.getDayOfMonth() +"/" + month  + "/" + datePicker.getYear();
                        if(titleText.length() < 6)
                        {
                            Toast.makeText(MainActivity.this,"Title length is very small",Toast.LENGTH_LONG).show();
                        }
                        else if(descriptionText.length() < 15)
                        {
                            Toast.makeText(MainActivity.this,"description length is very small",Toast.LENGTH_LONG).show();
                        }
                        else {
                            mDBHelper.insertTask(new Task(0, titleText, descriptionText, dateText, 0));
                            refreshList();
                            alertDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
                break;
            case R.id.completed:
                Intent intent = new Intent(getApplicationContext(),completedActivity.class);
                startActivity(intent);
                break;
            default:break;
        }
        //noinspection SimplifiableIfStatement
        return true;
    }
}
