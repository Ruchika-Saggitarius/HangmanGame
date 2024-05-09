package com.ruchika.hangman.responses;

import com.ruchika.hangman.model.RequestStatus;

public class ResetPasswordResponse {

    private RequestStatus requestStatus;


    public ResetPasswordResponse(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

}
