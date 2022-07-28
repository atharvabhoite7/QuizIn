package com.group5.admin_quizin;

public class ScoreModel {
    String email;
    int result;

    public ScoreModel(String email, int result) {
        this.email = email;
        this.result = result;
    }

    public ScoreModel() {
    }

    @Override
    public String toString() {
        return "ScoreModel{" +
                "email='" + email + '\'' +
                ", result=" + result +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}