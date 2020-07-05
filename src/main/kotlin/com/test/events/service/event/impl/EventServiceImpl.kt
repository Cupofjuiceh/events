package com.test.events.service.event.impl

import com.test.events.dto.event.*
import com.test.events.exception.GenericException
import com.test.events.model.event.Event
import com.test.events.model.user.Roles
import com.test.events.model.user.User
import com.test.events.repository.datajpa.EventRepository
import com.test.events.repository.jdbctemplate.JdbcTemplateEventRepository
import com.test.events.service.event.EventService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class EventServiceImpl (
        private val eventRepository: EventRepository,
        private val eventJdbcTemplateEventRepository: JdbcTemplateEventRepository
): EventService {

    private fun convertNewEventDTO(newEventDTO: NewEventDTO): Event =
            Event(
                    title = newEventDTO.title,
                    category = newEventDTO.category,
                    description = newEventDTO.description,
                    lat = newEventDTO.lat,
                    lng = newEventDTO.lng,
                    date = newEventDTO.date
            )

    override fun saveEvent(currentUser: User, newEventDTO: NewEventDTO): Event {
        val event = convertNewEventDTO(newEventDTO)
        event.author = currentUser

        return eventRepository.save(event)
    }

    override fun findEventById(eventId: Long): Event =
            eventRepository.findById(eventId).orElseThrow { throw GenericException(HttpStatus.NOT_FOUND, "Event not found") }

    override fun updateEvent(currentUser: User, eventId: Long, updateEventDTO: String): Event {
        TODO("Not yet implemented")
    }

    override fun deleteEvent(currentUser: User, eventId: Long) {
        val event = eventRepository.findById(eventId).orElseThrow { throw GenericException(HttpStatus.NOT_FOUND, "Event not found") }

        if (event.author == currentUser || currentUser.roles.contains(Roles.ADMIN)) {
            eventRepository.delete(event)
        } else {
            throw GenericException(HttpStatus.UNAUTHORIZED, "You have no rights to delete this event.")
        }
    }

    override fun findEventsNearby(nearbyEventsSearchDTO: NearbyEventsSearchDTO): NearbyEventsResponseDTO {
        val eventList = mutableListOf<Event>()
        val totalEvents = eventJdbcTemplateEventRepository.nearbySearch(nearbyEventsSearchDTO, eventList)

        return NearbyEventsResponseDTO(totalEvents!!, eventList.map { EventResponseDTO(it) })
    }
}