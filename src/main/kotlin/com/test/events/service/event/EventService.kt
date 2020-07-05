package com.test.events.service.event

import com.test.events.dto.event.NearbyEventsResponseDTO
import com.test.events.dto.event.NearbyEventsSearchDTO
import com.test.events.dto.event.NewEventDTO
import com.test.events.model.event.Event
import com.test.events.model.user.User

interface EventService {
    fun saveEvent(currentUser: User, newEventDTO: NewEventDTO): Event
    fun findEventById(eventId: Long): Event
    fun updateEvent(currentUser: User, eventId: Long, updateEventDTO: String): Event
    fun deleteEvent(currentUser: User, eventId: Long)
    fun findEventsNearby(nearbyEventsSearchDTO: NearbyEventsSearchDTO): NearbyEventsResponseDTO
}