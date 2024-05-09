package com.ruchika.hangman.responses;


import com.ruchika.hangman.model.RequestStatus;

public class RegisterUserResponse {

    private RequestStatus requestStatus;

    public RegisterUserResponse(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
    
}
