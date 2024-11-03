package com.example.quizspouse;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class DeleteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewQuestions;
    private QuestionsAdapter questionsAdapter;
    private List<Question> questionList;
    private DatabaseHelper dbHelper;
    private Spinner spinnerUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete);

        recyclerViewQuestions = findViewById(R.id.recyclerViewQuestions);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));

        spinnerUsername = findViewById(R.id.spinnerUsername);

        dbHelper = new DatabaseHelper(this);
        questionList = new ArrayList<>();

        loadUsernamesIntoSpinner();
        loadQuestionsFromDatabase(null); // Load all questions initially

        questionsAdapter = new QuestionsAdapter(this,questionList);
        recyclerViewQuestions.setAdapter(questionsAdapter);

        // Add listener for spinner selection
        spinnerUsername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedUsername = parent.getItemAtPosition(position).toString();
                if (selectedUsername.equals("All")) {
                    loadQuestionsFromDatabase(null); // Load all questions
                } else {
                    loadQuestionsFromDatabase(selectedUsername); // Load filtered questions
                }
                questionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Message on how to delete a question
        MaterialAlertDialogBuilder MaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(DeleteActivity.this);
        MaterialAlertDialogBuilder.setTitle("Note.");
        MaterialAlertDialogBuilder.setMessage("To delete a question you must hold on one of the questions from the list.");
        MaterialAlertDialogBuilder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        MaterialAlertDialogBuilder.show();
    }

    private void loadUsernamesIntoSpinner() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            List<String> usernames = new ArrayList<>();
            usernames.add("All"); // Add an option to select all questions

            String query = "SELECT DISTINCT username FROM Questions";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    String username = cursor.getString(cursor.getColumnIndex("username"));
                    usernames.add(username);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

            // Set up the spinner with usernames
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usernames);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUsername.setAdapter(spinnerAdapter);
        }
        catch (Exception ex){
            Toast.makeText(DeleteActivity.this, "Oops, something went wrong on: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("Oops, something went wrong on:" + ex.getMessage());
        }
    }

    private void loadQuestionsFromDatabase(@Nullable String username) {
        try {
            questionList.clear();
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query;
            String[] selectionArgs = null;

            if (username == null || username.equals("All")) {
                // Load all questions
                query = "SELECT username, question FROM Questions";
            } else {
                // Load questions filtered by username
                query = "SELECT username, question FROM Questions WHERE username = ?";
                selectionArgs = new String[]{username};
            }

            Cursor cursor = db.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                do {
                    String retrievedUsername = cursor.getString(cursor.getColumnIndex("username"));
                    String question = cursor.getString(cursor.getColumnIndex("question"));

                    questionList.add(new Question(retrievedUsername, question));
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        }
        catch (Exception ex){
            Toast.makeText(DeleteActivity.this, "Oops, something went wrong on: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("Oops, something went wrong on:" + ex.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(DeleteActivity.this, MainActivity.class));
        finish();
    }
}