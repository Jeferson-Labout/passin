package com.jlabout.passin.controllers.eventController;

import com.jlabout.passin.dto.attendee.AttendeeIdDTO;
import com.jlabout.passin.dto.attendee.AttendeeListResponseDTO;
import com.jlabout.passin.dto.attendee.AttendeeRequestDTO;
import com.jlabout.passin.dto.event.EventIdDTO;
import com.jlabout.passin.dto.event.EventRequestDTO;
import com.jlabout.passin.dto.event.EventResponseDTO;
import com.jlabout.passin.services.AttendeeService;
import com.jlabout.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
private final EventService eventService;
private final AttendeeService attendeeService;
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id){
        EventResponseDTO event =  this.eventService.getEventDetail(id);
        return  ResponseEntity.ok(event);
    }


    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
    EventIdDTO eventIdDTO = this.eventService.createEvent(body);

    var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();

    return  ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);

        var uri = uriComponentsBuilder.path("/attendees/{attendeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return  ResponseEntity.created(uri).body(attendeeIdDTO);
    }


    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String id){
        AttendeeListResponseDTO attendeeListResponse =  this.attendeeService.getEventsAttendee(id);
        return  ResponseEntity.ok(attendeeListResponse);
    }


}
