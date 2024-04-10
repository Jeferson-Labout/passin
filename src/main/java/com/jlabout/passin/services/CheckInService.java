package com.jlabout.passin.services;

import com.jlabout.passin.domain.attendee.Attendee;
import com.jlabout.passin.domain.checkin.CheckIn;
import com.jlabout.passin.domain.checkin.CheckInAlreadyExistsException;
import com.jlabout.passin.repositories.checkinRepository.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee){
        this.verifyCheckInExistes(attendee.getId());
        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());
        this.checkinRepository.save(newCheckIn);

    }

    private void verifyCheckInExistes(String attendeeId){
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);

        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee already checked in.");

    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
    return  this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
