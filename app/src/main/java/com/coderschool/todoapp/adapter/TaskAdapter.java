package com.coderschool.todoapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.coderschool.todoapp.R;
import com.coderschool.todoapp.dialog.AddTaskDialog;
import com.coderschool.todoapp.entity.Task;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Admin on 5/31/2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> implements AddTaskDialog.AddTaskDialogListener {
    private List<Task> taskList;
    Context context;
    private WeakReference<TaskCallback> taskCallback;

    public TaskAdapter(Context context, List<Task> objects, TaskCallback taskCallback) {
        super(context, -1);
        taskList = objects;
        this.context = context;
        this.taskCallback = new WeakReference<TaskCallback>(taskCallback);
    }

    @Override
    public void onFinishAddTaskDialog(Task task) {

    }

    public interface TaskCallback {
        public void onCheckboxChecked(Task task, int position);
        public void onClickUpdateButton(Task task);
        public void onClickDeleteButton(Task task);
        public void onItemClick(Task task);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    private class ViewHolder {
        ImageView imageViewStatus;
        TextView txtTitle;
        TextView txtDate;
        CheckBox cbxStatus;
        Button btnUpdate;
        Button btnDelete;


        public ViewHolder() {
        }

        public ViewHolder(ImageView imageViewStatus, TextView txtTitle, TextView txtDate, CheckBox cbxStatus, Button btnUpdate, Button btnDelete) {
            this.imageViewStatus = imageViewStatus;
            this.txtTitle = txtTitle;
            this.txtDate = txtDate;
            this.cbxStatus = cbxStatus;
            this.btnUpdate = btnUpdate;
            this.btnDelete = btnDelete;
        }
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //Inflate xml to view
        View view = convertView;
        final ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
            ImageView imageViewStatus = (ImageView) view.findViewById(R.id.imageStatus);
            TextView txtTitle = (TextView) view.findViewById(R.id.tvItemTitle);
            TextView txtDate = (TextView) view.findViewById(R.id.tvItemDate);
            CheckBox cbxStatus = (CheckBox) view.findViewById(R.id.chkStatus);
            Button btnUpdate = (Button) view.findViewById(R.id.btnEditTaskItem);
            Button btnDelete = (Button) view.findViewById(R.id.btnDeleteTaskItem);
            viewHolder = new ViewHolder(imageViewStatus, txtTitle, txtDate, cbxStatus, btnUpdate, btnDelete);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        final Task task = taskList.get(position);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        if (task.getPriority().equals("LOW"))imageLoader.displayImage("drawable://" + R.drawable.low, viewHolder.imageViewStatus);
        if (task.getPriority().equals("MED"))imageLoader.displayImage("drawable://" + R.drawable.med, viewHolder.imageViewStatus);
        if (task.getPriority().equals("HIGH"))imageLoader.displayImage("drawable://" + R.drawable.high, viewHolder.imageViewStatus);
        viewHolder.txtTitle.setText(task.getTitle());

        viewHolder.txtDate.setText(task.getDeadline());

        if (!task.isStatus()){
            viewHolder.cbxStatus.setChecked(false);
        }else{
            viewHolder.cbxStatus.setChecked(true);
        }
        viewHolder.cbxStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, task.getTitle(), Toast.LENGTH_SHORT).show();
                if (task.isStatus()){
                    task.setStatus(false);
                }else{
                    task.setStatus(true);
                }
                taskCallback.get().onCheckboxChecked(task, position);
            }
        });


        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskCallback.get().onClickDeleteButton(task);
            }
        });

        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskCallback.get().onClickUpdateButton(task);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}
