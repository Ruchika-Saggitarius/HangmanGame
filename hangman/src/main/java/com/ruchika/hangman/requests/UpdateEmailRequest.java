package com.ruchika.hangman.requests;

public class UpdateEmailRequest {

    private String newEmail;

    public UpdateEmailRequest(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
    
}
