package com.ruchika.hangman.responses;

import com.ruchika.hangman.model.RequestStatus;

public class UpdateEmailOfUserResponse {

    private RequestStatus requestStatus;

    public UpdateEmailOfUserResponse(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
    
}
