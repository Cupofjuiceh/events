package com.test.events.repository.jdbctemplate

import com.test.events.dto.event.NearbyEventsSearchDTO
import com.test.events.model.event.Event

interface JdbcTemplateEventRepository {
    fun nearbySearch(nearbyEventsSearchDTO: NearbyEventsSearchDTO, eventCollection: MutableCollection<Event>): Long?
}