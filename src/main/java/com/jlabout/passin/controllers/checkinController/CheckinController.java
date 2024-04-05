package com.jlabout.passin.controllers.checkinController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkins")
public class CheckinController {
    @GetMapping
    public ResponseEntity<String> getTeste(){
        return  ResponseEntity.ok("sucesso!");
    }
}
