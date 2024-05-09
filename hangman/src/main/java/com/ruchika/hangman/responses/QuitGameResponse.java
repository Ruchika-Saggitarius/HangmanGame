package com.ruchika.hangman.responses;

import com.ruchika.hangman.model.RequestStatus;

public class QuitGameResponse {

    private RequestStatus requestStatus;


    public QuitGameResponse(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
    
}
