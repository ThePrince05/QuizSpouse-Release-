package com.example.quizspouse;

public class Question {
    private String username;
    private String question;

    public Question(String username, String question) {
        this.username = username;
        this.question = question;
    }

    public String getUsername() {
        return username;
    }

    public String getQuestion() {
        return question;
    }
}
