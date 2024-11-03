package com.example.quizspouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MalumeQuiz extends AppCompatActivity {

    TextView quiztext, aanswer, banswer, canswer, danswer;
    List<QuestionsItem> questionsItems;
    int currentQuestions = 0;
    int correct = 0, wrong = 0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        // Set up userName Variable
        GlobalVariables globalVariables = (GlobalVariables) getApplicationContext();
        globalVariables.setUserName("Malume");

        quiztext = findViewById(R.id.quizText);
        aanswer = findViewById(R.id.aanswer);
        banswer = findViewById(R.id.banswer);
        canswer = findViewById(R.id.canswer);
        danswer = findViewById(R.id.danswer);

        loadAllQuestions();
        Collections.shuffle(questionsItems);
        setQuestionScreen(currentQuestions);

        aanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionsItems.get(currentQuestions).getAnswer1().equals(questionsItems.get(currentQuestions).getCorrect())) {
                    correct++;
                    aanswer.setBackgroundResource(R.color.green);
                    aanswer.setTextColor(getResources().getColor(R.color.white));
                } else {
                    wrong++;
                    aanswer.setBackgroundResource(R.color.red);
                    aanswer.setTextColor(getResources().getColor(R.color.white));

                }

                if (currentQuestions < questionsItems.size() - 1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            aanswer.setBackgroundResource(R.color.card_background);
                            aanswer.setTextColor(getResources().getColor(R.color.black));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(MalumeQuiz.this, ResultActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                    finish();
                }
            }
        });

        banswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionsItems.get(currentQuestions).getAnswer2().equals(questionsItems.get(currentQuestions).getCorrect())) {
                    correct++;
                    banswer.setBackgroundResource(R.color.green);
                    banswer.setTextColor(getResources().getColor(R.color.white));
                } else {
                    wrong++;
                    banswer.setBackgroundResource(R.color.red);
                    banswer.setTextColor(getResources().getColor(R.color.white));

                }

                if (currentQuestions < questionsItems.size() - 1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            banswer.setBackgroundResource(R.color.card_background);
                            banswer.setTextColor(getResources().getColor(R.color.black));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(MalumeQuiz.this, ResultActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                    finish();
                }
            }
        });

        canswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionsItems.get(currentQuestions).getAnswer3().equals(questionsItems.get(currentQuestions).getCorrect())) {
                    correct++;
                    canswer.setBackgroundResource(R.color.green);
                    canswer.setTextColor(getResources().getColor(R.color.white));
                } else {
                    wrong++;
                    canswer.setBackgroundResource(R.color.red);
                    canswer.setTextColor(getResources().getColor(R.color.white));

                }

                if (currentQuestions < questionsItems.size() - 1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            canswer.setBackgroundResource(R.color.card_background);
                            canswer.setTextColor(getResources().getColor(R.color.black));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(MalumeQuiz.this, ResultActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                    finish();
                }
            }
        });

        danswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questionsItems.get(currentQuestions).getAnswer4().equals(questionsItems.get(currentQuestions).getCorrect())) {
                    correct++;
                    danswer.setBackgroundResource(R.color.green);
                    danswer.setTextColor(getResources().getColor(R.color.white));
                } else {
                    wrong++;
                    danswer.setBackgroundResource(R.color.red);
                    danswer.setTextColor(getResources().getColor(R.color.white));

                }

                if (currentQuestions < questionsItems.size() - 1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            danswer.setBackgroundResource(R.color.card_background);
                            danswer.setTextColor(getResources().getColor(R.color.black));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(MalumeQuiz.this, ResultActivity.class);
                    intent.putExtra("correct", correct);
                    intent.putExtra("wrong", wrong);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void setQuestionScreen(int currentQuestions) {
        quiztext.setText(questionsItems.get(currentQuestions).getQuestions());
        aanswer.setText(questionsItems.get(currentQuestions).getAnswer1());
        banswer.setText(questionsItems.get(currentQuestions).getAnswer2());
        canswer.setText(questionsItems.get(currentQuestions).getAnswer3());
        danswer.setText(questionsItems.get(currentQuestions).getAnswer4());
    }

    private void loadAllQuestions() {

            questionsItems = new ArrayList<>();

            // Open the database
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Query to select all questions
            String query = "SELECT question, answer1, answer2, answer3, answer4, correct FROM Questions Where username = 'Malume' ";
            Cursor cursor = db.rawQuery(query, null);

            try {
                // Loop through the results
                if (cursor.moveToFirst()) {
                    do {
                        // Fetch the data using getColumnIndexOrThrow to ensure the column exists
                        String questionString = cursor.getString(cursor.getColumnIndexOrThrow("question"));
                        String answer1String = cursor.getString(cursor.getColumnIndexOrThrow("answer1"));
                        String answer2String = cursor.getString(cursor.getColumnIndexOrThrow("answer2"));
                        String answer3String = cursor.getString(cursor.getColumnIndexOrThrow("answer3"));
                        String answer4String = cursor.getString(cursor.getColumnIndexOrThrow("answer4"));
                        String correctString = cursor.getString(cursor.getColumnIndexOrThrow("correct"));

                        // Add each question to the list
                        questionsItems.add(new QuestionsItem(questionString, answer1String, answer2String, answer3String, answer4String, correctString));
                    } while (cursor.moveToNext());
                }

            }
            catch (Exception ex){
                Toast.makeText(MalumeQuiz.this, "Oops, something went wrong on: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Oops, something went wrong on:" + ex.getMessage());
            }
            finally {
                // Close the cursor and database
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            }


    }

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder MaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(MalumeQuiz.this);
        MaterialAlertDialogBuilder.setTitle("Quiz quit");
        MaterialAlertDialogBuilder.setMessage("Are you sure you want to return to home?");
        MaterialAlertDialogBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        MaterialAlertDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent( MalumeQuiz.this, MainActivity.class));
                finish();
            }
        });
        MaterialAlertDialogBuilder.show();
    }

}