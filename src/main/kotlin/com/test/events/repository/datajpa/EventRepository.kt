package com.test.events.repository.datajpa

import com.test.events.model.event.Event
import org.springframework.data.repository.CrudRepository

interface EventRepository: CrudRepository<Event, Long> {
}