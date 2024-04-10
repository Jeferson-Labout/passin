package com.jlabout.passin.services;

import com.jlabout.passin.domain.attendee.Attendee;
import com.jlabout.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import com.jlabout.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.jlabout.passin.domain.checkin.CheckIn;
import com.jlabout.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.jlabout.passin.dto.attendee.AttendeeDetails;
import com.jlabout.passin.dto.attendee.AttendeeListResponseDTO;
import com.jlabout.passin.dto.attendee.AttendeeBadgeDTO;
import com.jlabout.passin.repositories.attendeeRepository.AttendeeRepository;
import com.jlabout.passin.repositories.checkinRepository.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
    return  this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map( attendee -> {
          Optional <CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(),attendee.getCreatedAt(), checkedInAt);
        }).toList();
        return new AttendeeListResponseDTO(attendeeDetailsList);
    }


    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
    }

    public Attendee registerAttendee(Attendee newAttendee){
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId,  UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = this.getAttendee(attendeeId);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/ check-in").buildAndExpand(attendeeId).toUri();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri.toString(), attendee.getEvent().getId());
        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }


    public void checkInAttedee(String attendeeId) {

        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId){
        return  this.attendeeRepository.findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));
    }
}
