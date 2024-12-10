package com.example.todoapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TASKS_KEY = "tasks_data";
    private static final String PREFS = "task_preferences";

    private ArrayList<Task> taskList;
    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private EditText titleEditText, descriptionEditText;
    private Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        taskList = loadTasksFromPreferences();
        taskAdapter = new TaskAdapter(taskList, this);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);


        titleEditText = findViewById(R.id.titleInput);
        descriptionEditText = findViewById(R.id.descriptionInput);
        addTaskButton = findViewById(R.id.addButton);


        addTaskButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            } else {
                String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                Task newTask = new Task(title, description, false, currentTime);

                taskList.add(newTask);
                taskAdapter.notifyDataSetChanged();
                saveTasksToPreferences();

                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveTasksToPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String tasksJson = gson.toJson(taskList);
        editor.putString(TASKS_KEY, tasksJson);


        for (int i = 0; i < taskList.size(); i++) {
            editor.putBoolean("Task_" + i + "_Status", taskList.get(i).isCompleted());
        }

        editor.apply();
    }


    private ArrayList<Task> loadTasksFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String tasksJson = sharedPreferences.getString(TASKS_KEY, null);

        Gson gson = new Gson();
        Type taskListType = new TypeToken<ArrayList<Task>>() {}.getType();
        ArrayList<Task> loadedTaskList = gson.fromJson(tasksJson, taskListType);

        if (loadedTaskList != null) {

            for (int i = 0; i < loadedTaskList.size(); i++) {
                boolean taskStatus = sharedPreferences.getBoolean("Task_" + i + "_Status", false);
                loadedTaskList.get(i).setCompleted(taskStatus);
            }
        } else {
            loadedTaskList = new ArrayList<>();
        }

        return loadedTaskList;
    }
}


