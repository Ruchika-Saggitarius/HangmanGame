package com.ruchika.hangman.responses;


public class DeleteWordResponse {

    private String deletedWordId;

    public DeleteWordResponse(String deletedWordId) {
        this.deletedWordId = deletedWordId;
    }

    public String getDeletedWordId() {
        return deletedWordId;
    }

    public void setDeletedWordId(String deletedWordId) {
        this.deletedWordId = deletedWordId;
    }
    
    
}
