package com.jlabout.passin.repositories.eventRepository;

import com.jlabout.passin.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {

}
