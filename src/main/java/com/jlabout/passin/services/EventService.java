package com.jlabout.passin.services;

import com.jlabout.passin.domain.attendee.Attendee;
import com.jlabout.passin.domain.event.Event;
import com.jlabout.passin.dto.event.EventIdDTO;
import com.jlabout.passin.dto.event.EventRequestDTO;
import com.jlabout.passin.dto.event.EventResponseDTO;
import com.jlabout.passin.repositories.attendeeRepository.AttendeeRepository;
import com.jlabout.passin.repositories.eventRepository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private  final AttendeeRepository attendeeRepository;
 public EventResponseDTO getEventDetail(String eventId){
    Event event = this.eventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));

     List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
     return new EventResponseDTO(event, attendeeList.size());
 }


 public EventIdDTO createEvent(EventRequestDTO eventDTO) {
     Event newEvent = new Event();
     newEvent.setTitle(eventDTO.title());
     newEvent.setDetails(eventDTO.details());
     newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
     newEvent.setSlug(this.createSlug(eventDTO.title()));
     this.eventRepository.save(newEvent);
     return new EventIdDTO(newEvent.getId());

 }


 private String createSlug(String text){
     String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
     return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]","")
             .replaceAll("[^\\w\\s]", "")
             .replaceAll("\\s+","-")
             .toLowerCase();
 }
}