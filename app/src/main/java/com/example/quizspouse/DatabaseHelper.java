package com.example.quizspouse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name and Version
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_NAME = "Questions";

    // Constructor
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating the table

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE Questions ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "username TEXT, "
                    + "question TEXT, "
                    + "answer1 TEXT, "
                    + "answer2 TEXT, "
                    + "answer3 TEXT, "
                    + "answer4 TEXT, "
                    + "correct TEXT)";
            db.execSQL(CREATE_TABLE);

            // Insert the questions when the database is created
            InsertHerQuestions(db);
            InsertHisQuestions(db);

        }
        catch (Exception ex){
            System.out.println("Oops something went wrong on: " + ex.getMessage());
        }

    }

    //Upgrading the database (called when the version number changes)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void InsertHerQuestions(SQLiteDatabase db) {

        // Username to associate the questions with
        String username = "Her";

        // Example batch of questions
        String[][] questions = {
                {"What is her favourite colour?", "A) Red", "B) Green", "C) Pink", "D) Royal Blue", "C) Pink"},
                {"What is her biggest discomfort?", "A) Hugh Spiders", "B) Heights", "C) Public Speaking", "D) Venomous Snakes", "C) Public Speaking"},
                {"What is her favourite childhood memory?", "A) Climbing tree to run away from people", "B) Riding bike", "C) Spending time with father and mother", "D) Cooking with father", "D) Cooking with father"},
                {"What is her favourite song?", "A) Sweet Child o' Mine by Guns N' Roses", "B) Shape of You by Ed Sheeran", "C) You'll Never Find by Lou Rawls", "D) Why I Love You by Major Johnson Finley", "D) Why I Love You by Major Johnson Finley"},
                {"What is her dream vacation destination?", "A) Paris", "B) Florida, Miami", "C) Switzerland", "D) Mozambique", "A) Paris"},
                {"What is her favourite dessert?", "A) Chocolate Cake", "B) Ice Cream", "C) Cheesecake", "D) Tiramisu Cake", "D) Tiramisu Cake"},
                {"What is her biggest pet peeve (annoyance)?", "A) People chewing loudly", "B) Messy people", "C) Drivers who overtake people on the road", "D) People who don't have public decency", "D) People who don't have public decency"},
                {"What is her biggest fear?", "A) Losing a loved one", "B) Being Bedridden", "C) Separating with loved ones", "D) Jealousy over close family being close with other people", "C) Separating with loved ones"},
                {"What is her biggest accomplishment?", "A) Graduating college", "B) Getting a promotion at work", "C) Starting a hair salon", "D) Starting a family", "D) Starting a family"},
                {"What is her biggest regret?", "A) Not travelling more", "B) Not spending more time with family", "C) Being a risk-taker", "D) Getting easily attached with people", "D) Getting easily attached with people"}
        };

        InsertQuestions(db, username, questions);
    }

    public void InsertHisQuestions(SQLiteDatabase db) {

        // Username to associate the questions with
        String username = "His";

        // Array of questions
        String[][] questions = {
                {"What is his favourite sports team?", "A) Supersport United", "B) Mamelodi Sundowns", "C) Kazer Chefs", "D) Orlando Pirates", "D) Orlando Pirates"},
                {"What is his biggest fear?", "A) Flying", "B) Frogs", "C) Snakes", "D) Heights", "D) Heights"},
                {"What is his favourite childhood memory?", "A) Playing arcade games", "B) Riding his bike", "C) Being a Soccer Captain", "D) Being a Volleyball Captain", "D) Being a Volleyball Captain"},
                {"What is his favourite song?", "A) No Woman No Cry by Bob Marley", "B) Stairway to Heaven by Led Zeppelin", "C) Don't you Worry Child by Avicii", "D) You'll Never Find by Lou Rawls", "D) You'll Never Find by Lou Rawls"},
                {"What is his dream vacation destination?", "A) Hawaii", "B) America", "C) China", "D) Jamaica", "D) Jamaica"},
                {"What is his favourite food?", "A) Pap and Chicken", "B) Steak", "C) Sushi", "D) Pasta and Mince", "B) Steak"},
                {"What is his biggest pet peeve (annoyance)?", "A) Slow drivers", "B) Jay-Walking", "C) People who talk on the phone while driving", "D) People who left over-take on the road", "B) Jay-Walking"},
                {"What is the worst thing he had to overcome?", "A) Being sent to prison", "B) Getting into a car accident", "C) Getting accused of starting a fire which burned down a house", "D) Getting accused of splashing a partner with an acidic substance", "B) Getting into a car accident"},
                {"What is his biggest accomplishment?", "A) Building a house", "B) Starting a business", "C) Winning an award", "D) Buying a sports car", "A) Building a house"},
                {"What is the biggest amount he ever got in his account?", "A) R1 000 000", "B) R284 000", "C) R470 000", "D) R157 000", "D) R157 000"}
        };

        InsertQuestions(db, username, questions);
    }



    // Method to insert questions into the database
    private void InsertQuestions(SQLiteDatabase db, String username, String[][] questions) {
        for (String[] question : questions) {
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("question", question[0]);
            values.put("answer1", question[1]);
            values.put("answer2", question[2]);
            values.put("answer3", question[3]);
            values.put("answer4", question[4]);
            values.put("correct", question[5]);

            long id = db.insert("Questions", null, values);
            if (id == -1) {
                Log.e("DatabaseHelper", "Failed to insert question: " + question[0]);
            }
        }
    }

    public boolean InsertQuestion(String username, String[] question) {
        SQLiteDatabase db = this.getWritableDatabase();  // Open the database here

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("question", question[0]);
        values.put("answer1", question[1]);
        values.put("answer2", question[2]);
        values.put("answer3", question[3]);
        values.put("answer4", question[4]);
        values.put("correct", question[5]);

        long id = db.insert("Questions", null, values);
        if (id == -1) {
            Log.e("DatabaseHelper", "Failed to insert question: " + question[0]);
            return false;
        }

        db.close();
        return true;// Close the database
    }


}
