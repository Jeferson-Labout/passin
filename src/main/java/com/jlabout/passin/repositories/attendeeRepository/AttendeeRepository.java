package com.jlabout.passin.repositories.attendeeRepository;

import com.jlabout.passin.domain.attendee.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeRepository extends JpaRepository<Attendee, String> {
}
