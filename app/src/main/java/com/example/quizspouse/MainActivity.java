package com.example.quizspouse;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    MaterialCardView mommycard, malumecard, tshiamocard, addcard, deletecard, aboutcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Database creation
        // Initialize the DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // This will create the database and call the onCreate() if it doesn't already exist.
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        mommycard = findViewById(R.id.mommyCard);
        malumecard = findViewById(R.id.malumeCard);
        tshiamocard = findViewById(R.id.tshiamoCard);
        addcard = findViewById(R.id.addCard);
        deletecard = findViewById(R.id.deleteCard);
        aboutcard = findViewById(R.id.aboutCard);


        mommycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MommyQuiz.class));
                finish();
            }
        });

        malumecard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MalumeQuiz.class));
                finish();
            }
        });
        tshiamocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TshiamoQuiz.class));
                finish();
            }
        });

        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                finish();
            }
        });

        deletecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeleteActivity.class));
                finish();
            }
        });

        aboutcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this).setTitle("Quiz Spouse").
                setMessage("Are you sure you want to exit the application?").
                setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();

    }


}