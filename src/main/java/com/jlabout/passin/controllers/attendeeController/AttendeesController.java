package com.jlabout.passin.controllers.attendeeController;

import com.jlabout.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.jlabout.passin.services.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeesController {
    private final AttendeeService attendeeService;

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId,  UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO responseDTO =  this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return  ResponseEntity.ok(responseDTO);
    }



}
