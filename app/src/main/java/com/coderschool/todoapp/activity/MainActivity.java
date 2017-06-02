package com.coderschool.todoapp.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.coderschool.todoapp.R;
import com.coderschool.todoapp.adapter.TaskAdapter;
import com.coderschool.todoapp.database.DatabaseHandler;
import com.coderschool.todoapp.dialog.AddTaskDialog;
import com.coderschool.todoapp.dialog.UpdateTaskDialog;
import com.coderschool.todoapp.entity.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.AddTaskDialogListener, UpdateTaskDialog.UpdateTaskDialogListener, TaskAdapter.TaskCallback {

    ArrayList<Task> taskArrayList;
    TaskAdapter taskAdapter;
    ListView lvTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DatabaseHandler db = new DatabaseHandler(this);

        lvTasks = (ListView) findViewById(R.id.lvTasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });

        taskArrayList = db.getAllTasks();
        updateListView();
    }


    private void showAddItemDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddTaskDialog addTaskDialog = new AddTaskDialog();
        addTaskDialog.show(fm, "fragment_add_task");
    }

    private void showUpdateItemDialog(Task task){
        FragmentManager fm = getSupportFragmentManager();
        UpdateTaskDialog updateTaskDialog = UpdateTaskDialog.newInstance(1, task);
        updateTaskDialog.show(fm, "fragment_edit_task");
    }


    @Override
    public void onFinishAddTaskDialog(Task task) {
        DatabaseHandler db = new DatabaseHandler(this);
        db.addTask(task);
        taskArrayList = db.getAllTasks();
        Toast.makeText(getBaseContext(), task.getTitle() + " has been added!", Toast.LENGTH_SHORT).show();
        updateListView();
    }

    private void updateListView(){
        taskAdapter = new TaskAdapter(this, taskArrayList, this);
        lvTasks.setAdapter(taskAdapter);
    }

    @Override
    public void onCheckboxChecked(Task task, int position) {
        DatabaseHandler db = new DatabaseHandler(this);
        db.updateTask(task);
        //taskArrayList = db.getAllTasks();
        taskArrayList.set(position, task);
        taskAdapter.notifyDataSetChanged();
        //updateListView();
    }

    @Override
    public void onClickUpdateButton(Task task) {
        showUpdateItemDialog(task);
    }

    @Override
    public void onClickDeleteButton(Task task) {
        DatabaseHandler db = new DatabaseHandler(this);
        db.deleteTask(task);
        taskArrayList = db.getAllTasks();
        Toast.makeText(getBaseContext(), task.getTitle() + " has been deleted!", Toast.LENGTH_SHORT).show();
        updateListView();
    }

    @Override
    public void onItemClick(Task task) {
        Toast.makeText(getBaseContext(), "ItemClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishEditTaskDialog(Task task) {
        DatabaseHandler db = new DatabaseHandler(this);
        db.updateTask(task);
        Toast.makeText(getBaseContext(), task.getTitle() + " has been updated!", Toast.LENGTH_SHORT).show();
        taskArrayList = db.getAllTasks();
        updateListView();
    }
}
