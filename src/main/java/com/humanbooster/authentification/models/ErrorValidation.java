package com.humanbooster.authentification.models;

import java.util.Date;

public class ErrorValidation {

    private String input;

    private String error;

    private Date date;

    public ErrorValidation(String input, String error) {
        this.input = input;
        this.error = error;
        this.date = new Date();
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
