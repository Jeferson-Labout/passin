package com.jlabout.passin.controllers.attendeeController;

import com.jlabout.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.jlabout.passin.services.AttendeeService;
import com.jlabout.passin.services.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<Object> registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){

        this.attendeeService.checkInAttedee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();
        return ResponseEntity.created(uri).build();
    }


}
