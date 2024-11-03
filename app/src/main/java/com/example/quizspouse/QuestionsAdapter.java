package com.example.quizspouse;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

    private List<Question> questionList;
    private Context context;
    private DatabaseHelper dbHelper;

    public QuestionsAdapter(Context context, List<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
        this.dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.textViewUsername.setText(question.getUsername());
        holder.textViewQuestion.setText("Username: " +question.getQuestion());

        // Set long click listener to delete a question
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Question")
                    .setMessage("Are you sure you want to delete this question?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete the question from the database
                        deleteQuestionFromDatabase(question);
                        // Remove from list and notify the adapter
                        questionList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, questionList.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername, textViewQuestion;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.tvUsername);
            textViewQuestion = itemView.findViewById(R.id.tvQuestion);
        }
    }

    private void deleteQuestionFromDatabase(Question question) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check how many questions are left for the specific user
        String countQuery = "SELECT COUNT(*) FROM Questions WHERE username = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[]{question.getUsername()});
        int questionCount = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                questionCount = cursor.getInt(0);
            }
            cursor.close();
        }

        // If more than one question exists, proceed to delete
        if (questionCount > 1) {
            String whereClause = "username = ? AND question = ?";
            String[] whereArgs = {question.getUsername(), question.getQuestion()};
            int deletedRows = db.delete("Questions", whereClause, whereArgs);

            if (deletedRows > 0) {
                Log.d("DB_SUCCESS", "Question deleted successfully.");
            } else {
                Log.e("DB_ERROR", "Failed to delete the question.");
            }
        } else {
            // Refuse to delete if only one question is left
            Log.w("DB_WARNING", "Cannot delete the last remaining question for user: " + question.getUsername());

        }

        db.close();
    }

}
