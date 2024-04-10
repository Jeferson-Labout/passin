package com.jlabout.passin.domain.checkin;

public class CheckInAlreadyExistsException extends RuntimeException{



    public CheckInAlreadyExistsException(String message){
        super(message);

    }
}
