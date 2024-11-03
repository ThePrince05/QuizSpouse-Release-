package com.example.quizspouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText question, answerA, answerB, answerC, answerD;
    Spinner user, questionAnswer;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        // setting up editText
        question = findViewById(R.id.question);
        answerA = findViewById(R.id.a);
        answerB = findViewById(R.id.b);
        answerC = findViewById(R.id.c);
        answerD = findViewById(R.id.d);

        //setting up button
        save = findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveQuestion();
            }
        });

        //Setting up user Spinner
        user = findViewById(R.id.spinnerUser);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.userList, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        user.setAdapter(adapter);

        //Setting up question Spinner
        questionAnswer = findViewById(R.id.spinnerQuestion);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> questionAdapter = ArrayAdapter.createFromResource(this,
                R.array.questionList, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        questionAnswer.setAdapter(questionAdapter);

        // Database creation
        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

    }

    private boolean validateForm(){
        String userName = user.getSelectedItem().toString();
        String QuestionAnswer = questionAnswer.getSelectedItem().toString();
        String Question = question.getText().toString().trim();
        String AnswerA = answerA.getText().toString().trim();
        String AnswerB = answerB.getText().toString().trim();
        String AnswerC = answerC.getText().toString().trim();
        String AnswerD = answerD.getText().toString().trim();

        String correctAnswer = "None";
        if(QuestionAnswer.contains("A")){
            correctAnswer = AnswerA;
        }
        else if(QuestionAnswer.contains("B")){
            correctAnswer = AnswerB;
        }
        else if(QuestionAnswer.contains("C")){
            correctAnswer = AnswerC;
        }
        else{
            correctAnswer = AnswerD;
        }

        int QuestionCount = 0;
        if(TextUtils.isEmpty(question.getText()) || Question.isEmpty()){
            question.setError("Please enter a question.");
            QuestionCount = 1;

        }
        else{
            question.setError(null);
            QuestionCount = 0;
        }

        int AnswerACount = 0;
        if(TextUtils.isEmpty(answerA.getText())  || AnswerA.isEmpty()){
            answerA.setError("Please enter answer A.");
            AnswerACount = 1;

        }
        else{
            answerA.setError(null);
            AnswerACount = 0;
        }

        int AnswerBCount = 0;
        if(TextUtils.isEmpty(answerB.getText())  || AnswerB.isEmpty()){
            answerB.setError("Please enter answer B.");
            AnswerBCount = 1;

        }
        else{
            answerB.setError(null);
            AnswerBCount = 0;
        }

        int AnswerCCount = 0;
        if(TextUtils.isEmpty(answerC.getText())   || AnswerC.isEmpty()){
            answerC.setError("Please enter answer C.");
            AnswerCCount = 1;

        }
        else{
            answerC.setError(null);
            AnswerCCount = 0;
        }

        int AnswerDCount = 0;
        if(TextUtils.isEmpty(answerD.getText())   || AnswerD.isEmpty()){
            answerD.setError("Please enter answer D.");
            AnswerDCount = 1;

        }
        else{
            answerD.setError(null);
            AnswerDCount = 0;
        }

        int spinnerUser = 0;
        if(userName.contains("Select an option")){
            Toast.makeText(AddActivity.this, "Select a user", Toast.LENGTH_SHORT).show();
            spinnerUser = 1;
        }
        else{
            spinnerUser = 0;
        }

        int spinnerAnswer = 0;
        if(QuestionAnswer.contains("Select an option")){
            Toast.makeText(AddActivity.this, "Select a correct answer", Toast.LENGTH_SHORT).show();
            spinnerAnswer = 1;
        }
        else{
            spinnerAnswer = 0;
        }

        int total = QuestionCount + AnswerACount + AnswerBCount + AnswerCCount + AnswerDCount + spinnerAnswer + spinnerUser;

        if(total == 0){
            return true;
        }
        else{
            return false;
        }
    }


    private void SaveQuestion() {

        if(validateForm()) {
            String userName = CapitalizeFirstLetter(user.getSelectedItem().toString());
            String QuestionAnswer = CapitalizeFirstLetter(questionAnswer.getSelectedItem().toString());
            String Question = CapitalizeFirstLetter(question.getText().toString().trim());
            String AnswerA = CapitalizeFirstLetter(answerA.getText().toString().trim());
            String AnswerB = CapitalizeFirstLetter(answerB.getText().toString().trim());
            String AnswerC = CapitalizeFirstLetter(answerC.getText().toString().trim());
            String AnswerD = CapitalizeFirstLetter(answerD.getText().toString().trim());


            String correctAnswer = "None";
            if (QuestionAnswer.contains("A")) {
                correctAnswer = AnswerA;
            } else if (QuestionAnswer.contains("B")) {
                correctAnswer = AnswerB;
            } else if (QuestionAnswer.contains("C")) {
                correctAnswer = AnswerC;
            } else {
                correctAnswer = AnswerD;
            }


            String[] questionList =
                    {Question, AnswerA, AnswerB, AnswerC, AnswerD, correctAnswer};


            if (dbHelper.InsertQuestion(userName, questionList)) {
                new MaterialAlertDialogBuilder(this).setTitle("Successful").
                        setMessage("Question saved.").
                        setNegativeButton("Okay", null).show();

                        //clear fields
                        question.setText("");
                        answerA.setText("");
                        answerB.setText("");
                        answerC.setText("");
                        answerD.setText("");

                        user.setSelection(0);
                        questionAnswer.setSelection(0);
            } else {
                new MaterialAlertDialogBuilder(this).setTitle("Failed").
                        setMessage("Question failed to save.").
                        setNegativeButton("Okay", null).show();
            }

        }

    }
public static String CapitalizeFirstLetter(String str){
      if(str == null || str.isEmpty()){
          return str;
      }
      return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
}

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddActivity.this, MainActivity.class));
        finish();
    }
}