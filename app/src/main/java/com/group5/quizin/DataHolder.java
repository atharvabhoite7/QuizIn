package com.group5.quizin;

public class DataHolder {
    String Email;
    int Result;

    public DataHolder(String Email, int Result) {
        this.Email = Email;
        this.Result = Result;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int Result) {
        this.Result = Result;
    }
}
