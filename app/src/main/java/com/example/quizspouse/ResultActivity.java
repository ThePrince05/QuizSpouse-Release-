package com.example.quizspouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ResultActivity extends AppCompatActivity {

    MaterialCardView home;
    TextView correctTotal, wrongTotal, resultInfo, resultsScore;
    ImageView resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        // Get userName
        GlobalVariables globalVariables = (GlobalVariables) getApplicationContext();
        String userName = globalVariables.getUserName();

        home = findViewById(R.id.returnHome);
        correctTotal = findViewById(R.id.correctScore);
        wrongTotal = findViewById(R.id.wrongScore);
        resultInfo = findViewById(R.id.resultInfo);
        resultsScore = findViewById(R.id.resultScore);
        resultImage = findViewById(R.id.resultImage);

        int correct = getIntent().getIntExtra("correct",0);
        int wrong = getIntent().getIntExtra("wrong",0);
        int total = correct + wrong;
        int score = Integer.parseInt(String.valueOf(Math.round((double)correct / total * 100)));

        correctTotal.setText("" + correct);
        wrongTotal.setText("" + wrong);
        resultsScore.setText("" + score + "%");

        if(score <= 20){
            resultInfo.setText("Are you sure you know " + userName + "?");
            resultInfo.setTextColor(getResources().getColor(R.color.red));
            resultImage.setImageResource(R.drawable.heartbroken);

        }
        else if(score <= 49){
            resultInfo.setText("Listen to "+ userName + " more.");
            resultInfo.setTextColor(getResources().getColor(R.color.red));
            resultImage.setImageResource(R.drawable.heartbandage);
        }
        else if(score <= 79){
            resultInfo.setText("That's what I'm talking about, not bad.");
            resultInfo.setTextColor(getResources().getColor(R.color.blue));
            resultImage.setImageResource(R.drawable.heart2);
        }
        else{
            resultInfo.setText("Congragulations, you know " + userName +  " very well.");
            resultInfo.setTextColor(getResources().getColor(R.color.green));
            resultImage.setImageResource(R.drawable.congratulation);
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultActivity.this, MainActivity.class));
        finish();
    }
}