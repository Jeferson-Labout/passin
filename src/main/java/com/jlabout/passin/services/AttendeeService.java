package com.jlabout.passin.services;

import com.jlabout.passin.domain.attendee.Attendee;
import com.jlabout.passin.domain.checkin.CheckIn;
import com.jlabout.passin.dto.attendee.AttendeeDetails;
import com.jlabout.passin.dto.attendee.AttendeeListResponseDTO;
import com.jlabout.passin.repositories.attendeeRepository.AttendeeRepository;
import com.jlabout.passin.repositories.checkinRepository.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckinRepository checkinRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
    return  this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map( attendee -> {
          Optional <CheckIn> checkIn = this.checkinRepository.findByAttendeeId(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(),attendee.getCreatedAt(), checkedInAt);
        }).toList();
        return new AttendeeListResponseDTO(attendeeDetailsList);
    }
}
