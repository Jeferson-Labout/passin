package com.jlabout.passin.config;

import com.jlabout.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.jlabout.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.jlabout.passin.domain.checkin.CheckInAlreadyExistsException;
import com.jlabout.passin.domain.event.exceptions.EventFullException;
import com.jlabout.passin.domain.event.exceptions.EventNotFoundException;
import com.jlabout.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNOtFound(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNOtFound(AttendeeNotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AttendeeAlreadyExistException.class)
    public ResponseEntity handleAttendeeAlreadyExistException(AttendeeAlreadyExistException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExistsException(CheckInAlreadyExistsException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO>handleEventFullException(EventFullException exception){
        return ResponseEntity.badRequest().body( new ErrorResponseDTO(exception.getMessage()));
    }
}
